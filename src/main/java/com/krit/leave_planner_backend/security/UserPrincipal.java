package com.krit.leave_planner_backend.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserPrincipal implements UserDetails {

    private final Long userId;
    private final String email;
    private final boolean manager;

    public UserPrincipal(Long userId, String email, boolean manager) {
        this.userId = userId;
        this.email = email;
        this.manager = manager;
    }

    public Long getUserId() {
        return userId;
    }

    public boolean isManager() {
        return manager;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (manager) {
            return List.of(new SimpleGrantedAuthority("ROLE_MANAGER"));
        }
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
