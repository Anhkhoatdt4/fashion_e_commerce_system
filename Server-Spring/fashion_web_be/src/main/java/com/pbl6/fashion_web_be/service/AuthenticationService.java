package com.pbl6.fashion_web_be.service;

import com.pbl6.fashion_web_be.dto.request.LogoutRequest;
import com.pbl6.fashion_web_be.dto.response.TokenResponse;
import com.pbl6.fashion_web_be.entity.InvalidatedToken;
import com.pbl6.fashion_web_be.entity.User;
import com.pbl6.fashion_web_be.repository.InvalidatedTokenRepository;
import com.pbl6.fashion_web_be.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.CollectionUtils;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.StringJoiner;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    @Value("${spring.jwt.signerKey}")
    private String SIGNER_KEY;

    @Value("${spring.jwt.issuer}")
    private String ISSUER;

    @Value("${spring.jwt.expiration}")
    private int EXPIRATION_TIME;

    @Value("${spring.jwt.refreshable-duration}")
    private int REFRESH_EXPIRATION_TIME;

    private final UserRepository userRepository;
    private final InvalidatedTokenRepository invalidatedTokenRepository;

    public String generateToken(String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return Jwts.builder().issuer(ISSUER)
                .subject(email)
                .issuedAt(new Date())
                .expiration(generateExpirationDate())
                .signWith(getSigningKey())
                .claim("email", email)
                .claim("username", user.getUsername())
                .claim("userId", user.getUserId())
                .claim("roles", user.getRoles().stream().map(role -> "ROLE_" + role.getRoleName()).toList())
                .claim("scope", buildScope(user))
                .compact();
    }

    private String buildScope(User user){
        StringJoiner stringJoiner = new StringJoiner(",");
        if (!CollectionUtils.isEmpty(user.getRoles())){
            user.getRoles().forEach(role -> {
                var userPermissions = role.getPermissions();
                stringJoiner.add(role.getRoleName());
                if(!CollectionUtils.isEmpty(userPermissions)){
                    userPermissions.forEach(permission -> {
                        stringJoiner.add(permission.getName());
                    });
                } else {
                    log.warn("No permissions found for role: {}", role.getRoleName());
                }
            });
        }
        return stringJoiner.toString();
    }

    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + EXPIRATION_TIME * 1000L);
    }

    private Date getExpirationDate(String token){
        try{
            Claims claims = getAllClaimsFromToken(token);
            if (claims != null) {
                return claims.getExpiration();
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public long getExpirationTime(String token) {
        Date expirationDate = getExpirationDate(token);
        if (expirationDate != null) {
            return expirationDate.getTime() - System.currentTimeMillis();
        }
        return 0;
    }

    private Key getSigningKey() {
        byte[] keyBytes = SIGNER_KEY.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getToken(HttpServletRequest request){
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }

    public String getEmailFromToken(String token){
        String email = null;
        try {
            Claims claims = getAllClaimsFromToken(token);
            if(claims != null){
                email = claims.getSubject();
                log.debug("Email from token: {}", email);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return email;
    }

    @Deprecated
    public String getUsernameFromToken(String token){
        return getEmailFromToken(token);
    }

    public String getUsernameFromTokenClaims(String token){
        String username = null;
        try {
            Claims claims = getAllClaimsFromToken(token);
            if(claims != null){
                username = (String) claims.get("username");
                log.debug("Username from token claims: {}", username);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return username;
    }

    private Claims getAllClaimsFromToken(String token) {
        Claims claims;
        try{
            claims = Jwts.parser()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            log.debug("Claims from token: {}", claims);
        }
        catch (Exception e){
            e.printStackTrace();
            claims = null;
        }
        return claims;
    }

    public boolean validateToken(String token) {
        try {
            Claims claims = getAllClaimsFromToken(token);
            boolean isTokenExpired = claims.getExpiration().before(new Date());
            boolean isTokenInvalidated = invalidatedTokenRepository.existsById(token);
            return !isTokenExpired && !isTokenInvalidated;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isTokenExpired(String token) {
        Date expiration = getExpirationDate(token);
        return expiration.before(new Date());
    }

    public boolean isTokenValid(String authToken, UserDetails userDetails){
        System.out.println("userDetails: " + userDetails.getUsername());
        String username = getUsernameFromTokenClaims(authToken);
        log.debug("Validating token for email: {} - {}", username , userDetails.getUsername());
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(authToken));
    }

    public String generateRefreshToken(String email){
        return Jwts.builder()
                .issuer(ISSUER)
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + REFRESH_EXPIRATION_TIME * 1000L))
                .signWith(getSigningKey())
                .compact();
    }

    public TokenResponse generateTokenAndRefreshToken(String email) {
        String token = generateToken(email);
        String refreshToken = generateRefreshToken(email);
        return new TokenResponse(token, refreshToken,
                getExpirationDate(token), getExpirationDate(refreshToken));
    }

    public void logOut(LogoutRequest logoutRequest){
        String token = logoutRequest.getToken();
        if (token != null && !token.isEmpty()) {
            Date expirationDate = getExpirationDate(token);
            if (expirationDate != null) {
                InvalidatedToken invalidatedToken = new InvalidatedToken();
                invalidatedToken.setId(token);
                invalidatedToken.setExpiryTime(expirationDate);
                invalidatedTokenRepository.save(invalidatedToken);
                log.info("Token invalidated: {}", token);
            } else {
                log.warn("Invalid token: {}", token);
            }
        } else {
            log.warn("Logout request does not contain a valid token.");
        }
    }
}