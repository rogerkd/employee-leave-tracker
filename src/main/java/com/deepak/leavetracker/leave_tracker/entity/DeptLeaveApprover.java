package com.deepak.leavetracker.leave_tracker.entity;

import jakarta.persistence.*;

@Entity
@Table(name="dept_leave_approvers")
public class DeptLeaveApprover {

    // fields

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="dept_approver_id")
    private Integer deptApproverId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="dept_id")
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="approver_id")
    private Employee approver;

    @Column(name="active")
    private Boolean active;

    // constructor

    public DeptLeaveApprover(){};

    public DeptLeaveApprover(Integer deptApproverId, Boolean active, Employee approver, Department department) {
        this.deptApproverId = deptApproverId;
        this.active = active;
        this.approver = approver;
        this.department = department;
    }

    // getter and setter


    public Integer getDeptApproverId() {
        return deptApproverId;
    }

    public void setDeptApproverId(Integer deptApproverId) {
        this.deptApproverId = deptApproverId;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Employee getApprover() {
        return approver;
    }

    public void setApprover(Employee approver) {
        this.approver = approver;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
