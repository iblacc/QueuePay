package com.dev.QueuePay.user.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.UUID;

public interface CustomUserDetailsService {
    UserDetails loadUserByUsername(String email) throws UsernameNotFoundException;

    UserDetails loadUserById(UUID id);
}
