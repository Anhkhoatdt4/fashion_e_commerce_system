package com.pbl6.fashion_web_be.service;

import com.pbl6.fashion_web_be.dto.request.CustomUserDetails;
import com.pbl6.fashion_web_be.entity.User;
import com.pbl6.fashion_web_be.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return loadUserByEmail(email);
    }
    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmailWithRoles(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        if (user.getPasswordHash() == null || user.getPasswordHash().isEmpty()) {
            throw new UsernameNotFoundException("User password is null for email: " + email);
        }

        return new CustomUserDetails(user);
    }
}
