package com.deepak.leavetracker.leave_tracker.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="roles")
public class Role {

    // fields

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="role_id")
    private Integer roleId;

    @Column(name="role_name")
    private String roleName;

    @OneToMany(mappedBy = "role")
    private List<UserRole> userRoles;

    // constructor

    public Role(){};

    public Role(Integer roleId, String roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }

    // getter and setter

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

}
