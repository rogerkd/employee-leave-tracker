package com.deepak.leavetracker.leave_tracker.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="user_accounts")
public class UserAccount {

    // fields

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Integer userId;

    @Column(name="username")
    private String username;

    @Column(name="password")
    private String password;

    @Column(name="enabled")
    private Boolean enabled;

    @OneToMany(mappedBy = "userAccount")
    private List<UserRole> userRoles;

    // constructor

    public UserAccount(){};

    public UserAccount(Integer userId, Boolean enabled, String password, String username, List<UserRole> userRoles) {
        this.userId = userId;
        this.enabled = enabled;
        this.password = password;
        this.username = username;
        this.userRoles = userRoles;
    }

    // getter and setter

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public List<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    // toString()

    @Override
    public String toString() {
        return "UserAccount{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                '}';
    }
}
