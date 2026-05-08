package com.deepak.leavetracker.leave_tracker.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name="leave_requests")
public class LeaveRequest {

    // fields

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="leave_id")
    private Integer leaveId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="emp_id")
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="leave_type_id")
    private LeaveType leaveType;

    @Column(name="start_date")
    private LocalDate startDate;

    @Column(name="end_date")
    private LocalDate endDate;

    @Column(name="status")
    private String status;

    @Column(name="applied_on")
    private LocalDate appliedOn;

    @JoinColumn(name="approver_id")
    private Employee approver;

    // constructors

    public LeaveRequest(){}

    public LeaveRequest(Integer leaveId, Employee approver, LocalDate appliedOn, String status, LocalDate endDate, LocalDate startDate, LeaveType leaveType, Employee employee) {
        this.leaveId = leaveId;
        this.approver = approver;
        this.appliedOn = appliedOn;
        this.status = status;
        this.endDate = endDate;
        this.startDate = startDate;
        this.leaveType = leaveType;
        this.employee = employee;
    }

    // getters and setters


    public Integer getLeaveId() {
        return leaveId;
    }

    public void setLeaveId(Integer leaveId) {
        this.leaveId = leaveId;
    }

    public Employee getApprover() {
        return approver;
    }

    public void setApprover(Employee approver) {
        this.approver = approver;
    }

    public LocalDate getAppliedOn() {
        return appliedOn;
    }

    public void setAppliedOn(LocalDate appliedOn) {
        this.appliedOn = appliedOn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public LeaveType getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(LeaveType leaveType) {
        this.leaveType = leaveType;
    }

    // define toString()


    @Override
    public String toString() {
        return "LeaveRequest{" +
                "leaveId=" + leaveId +
                ", employee=" + employee +
                ", leaveType=" + leaveType +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", status='" + status + '\'' +
                ", appliedOn=" + appliedOn +
                ", approver=" + approver +
                '}';
    }
}
