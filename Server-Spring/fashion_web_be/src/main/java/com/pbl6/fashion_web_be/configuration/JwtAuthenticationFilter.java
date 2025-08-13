package com.pbl6.fashion_web_be.configuration;

import com.pbl6.fashion_web_be.service.AuthenticationService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final AuthenticationService authenticationService;
    private final UserDetailsService userDetailsService;
    private final String[] publicEndpoints;

    private boolean isPublicEndpoint(String path){
        AntPathMatcher matcher = new AntPathMatcher();
        log.debug("Checking if path '{}' is public endpoint", path);
        for (String endpoint : publicEndpoints) {
            log.debug("Comparing with endpoint pattern: '{}'", endpoint);
            if (matcher.match(endpoint, path)){
                log.info("Path '{}' matches public endpoint pattern '{}' - allowing access", path, endpoint);
                return true;
            }
        }
        log.info("Path '{}' does not match any public endpoint - JWT required", path);
        return false;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");
        final String jwtToken;
        final String username;

        log.info("Starting JWT filter for request URI: {}", request.getRequestURI());

        if (isPublicEndpoint(request.getRequestURI())){
            log.info("=== PUBLIC ENDPOINT DETECTED - BYPASSING JWT CHECK ===");
            filterChain.doFilter(request, response);
            log.info("=== AFTER FILTER CHAIN - PUBLIC ENDPOINT ===");
            return;
        }

        log.info("=== PRIVATE ENDPOINT - CHECKING JWT ===");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            log.warn("Authorization header missing or does not start with Bearer");
            filterChain.doFilter(request, response);
            return;
        }

        try {
            jwtToken = authenticationService.getToken(request);
            log.info("Extracted JWT token: {}", jwtToken);

            username = authenticationService.getUsernameFromToken(jwtToken);
            log.info("Extracted username from token: {}", username);

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (username != null && (authentication == null || !authentication.isAuthenticated())) {
                log.info("No authentication found in context, loading user details for username: {}", username);

                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                log.info("UserDetails loaded: {}", userDetails.getUsername());

                if (authenticationService.isTokenValid(jwtToken, userDetails)) {
                    log.info("Token is valid, setting authentication in context");

                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());

                    authenticationToken.setDetails(userDetails);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                } else {
                    log.warn("Token validation failed");
                }
            } else {
                log.info("Authentication already exists in context or username is null");
            }
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token expired");
        } catch (JwtException e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT token");
        }
    }

}