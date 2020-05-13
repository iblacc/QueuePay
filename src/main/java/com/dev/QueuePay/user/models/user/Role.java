package com.dev.QueuePay.user.models.user;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_ADMIN, ROLE_MERCHANT;

    public String getAuthority() {
        return name();
    }
}
