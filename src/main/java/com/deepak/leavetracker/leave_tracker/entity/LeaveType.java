package com.deepak.leavetracker.leave_tracker.entity;

import jakarta.persistence.*;

@Entity
@Table(name="leave_types")
public class LeaveType {

    // fields

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="leave_type_id")
    private Integer leaveTypeId;

    @Column(name="leave_name")
    private String leaveName;

    @Column(name="leave_code")
    private String leaveCode;

    @Column(name="default_leaves")
    private Integer defaultLeaves;

    // constructors

    public LeaveType(){}

    public LeaveType(Integer leaveTypeId, String leaveName, String leaveCode, Integer defaultLeaves) {
        this.leaveTypeId = leaveTypeId;
        this.leaveName = leaveName;
        this.leaveCode = leaveCode;
        this.defaultLeaves = defaultLeaves;
    }

    // getters and setters

    public Integer getLeaveTypeId() {
        return leaveTypeId;
    }

    public void setLeaveTypeId(Integer leaveTypeId) {
        this.leaveTypeId = leaveTypeId;
    }

    public String getLeaveName() {
        return leaveName;
    }

    public void setLeaveName(String leaveName) {
        this.leaveName = leaveName;
    }

    public String getLeaveCode() {
        return leaveCode;
    }

    public void setLeaveCode(String leaveCode) {
        this.leaveCode = leaveCode;
    }

    public Integer getDefaultLeaves() {
        return defaultLeaves;
    }

    public void setDefaultLeaves(Integer defaultLeaves) {
        this.defaultLeaves = defaultLeaves;
    }
}
