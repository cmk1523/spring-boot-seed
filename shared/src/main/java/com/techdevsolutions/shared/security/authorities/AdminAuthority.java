package com.techdevsolutions.shared.security.authorities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;


public class AdminAuthority implements GrantedAuthority {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public String getAuthority() {
        return "ROLE_ADMIN";
    }
}