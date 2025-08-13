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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        if (user.getPasswordHash() == null || user.getPasswordHash().isEmpty()) {
            throw new UsernameNotFoundException("User password is null for username: " + username);
        }
        return new CustomUserDetails(user);
    }
}
