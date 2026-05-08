package com.deepak.leavetracker.leave_tracker.dto.response;

import com.deepak.leavetracker.leave_tracker.entity.UserRole;

import java.util.List;

public class UserAccountResponseDTO {

    private Integer userId;

    private String username;

    private Boolean enabled;

    private List<String> roles;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
