package com.embl.techtest.security.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtUserDetailsService.class);

    @Value("${jwt.username}")
    private String username;

    @Value("${jwt.password}")
    private String password;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LOGGER.info("Loading User by Username");
        if (this.username.equals(username)) {
            return new User(this.username, this.password,
                    new ArrayList<>());
        } else {
            LOGGER.error("User not found with username: " + username);
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}