package com.deepak.leavetracker.leave_tracker.entity;

import jakarta.persistence.*;

@Entity
@Table(name="leave_balances")
public class LeaveBalance {

    // fields

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "balance_id")
    private Integer balanceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emp_id")
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="leave_type_id")
    private LeaveType leaveType;

    @Column(name = "total_leaves")
    private Integer totLeaves;

    @Column(name = "used_leaves")
    private Integer usedLeaves;

    @Column(name = "remaining_leaves")
    private Integer remLeaves;

    // constructors

    public LeaveBalance(){}

    public LeaveBalance(Integer balanceId, Integer remLeaves, Integer usedLeaves, Integer totLeaves, LeaveType leaveType, Employee employee) {
        this.balanceId = balanceId;
        this.remLeaves = remLeaves;
        this.usedLeaves = usedLeaves;
        this.totLeaves = totLeaves;
        this.leaveType = leaveType;
        this.employee = employee;
    }

    // getters and setters


    public Integer getBalanceId() {
        return balanceId;
    }

    public void setBalanceId(Integer balanceId) {
        this.balanceId = balanceId;
    }

    public Integer getRemLeaves() {
        return remLeaves;
    }

    public void setRemLeaves(Integer remLeaves) {
        this.remLeaves = remLeaves;
    }

    public Integer getUsedLeaves() {
        return usedLeaves;
    }

    public void setUsedLeaves(Integer usedLeaves) {
        this.usedLeaves = usedLeaves;
    }

    public LeaveType getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(LeaveType leaveType) {
        this.leaveType = leaveType;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Integer getTotLeaves() {
        return totLeaves;
    }

    public void setTotLeaves(Integer totLeaves) {
        this.totLeaves = totLeaves;
    }
}
