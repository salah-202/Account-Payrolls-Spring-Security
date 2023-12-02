package com.example.accountpayrolls.entities;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

public class AppUserAdapter implements UserDetails {
    private final AppUser appUser;
    public AppUserAdapter(AppUser appUser){
        this.appUser = appUser;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return appUser.getRoles().stream().map(
                role -> new SimpleGrantedAuthority(role.getName())).toList();
    }

    @Override
    public String getPassword() {
        return appUser.getPassword();
    }

    @Override
    public String getUsername() {
        return appUser.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return appUser.isNonLocked();
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
