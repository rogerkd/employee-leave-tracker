package com.deepak.leavetracker.leave_tracker.security.user;

import com.deepak.leavetracker.leave_tracker.entity.UserAccount;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomUserDetails implements UserDetails {

    private final UserAccount userAccount;

    public CustomUserDetails(UserAccount userAccount){
        this.userAccount = userAccount;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userAccount.getUserRoles().stream()
                .map(ur -> new SimpleGrantedAuthority(ur.getRole().getRoleName()))
                .toList();
    }

    @Override
    public String getPassword() {
        return userAccount.getPassword();
    }

    @Override
    public String getUsername() {
        return userAccount.getUsername(); // email
    }

    @Override
    public boolean isEnabled() {
        return userAccount.isEnabled();
    }

    //
    public boolean isAccountNonExpired() { return true; }
    public boolean isAccountNonLocked() { return true; }
    public boolean isCredentialsNonExpired() { return true; }

    public UserAccount getUserAccount() {
        return userAccount;
    }
}
