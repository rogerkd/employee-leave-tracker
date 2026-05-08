package com.deepak.leavetracker.leave_tracker.entity;

import jakarta.persistence.*;

@Entity
@Table(name="employees")
public class Employee {

    // fields

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="emp_id")
    private Integer empId;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="email")
    private String email;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private UserAccount userAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="department_id")
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="leave_approver_id")
    private Employee leaveApprover;

    // constructor

    public Employee(){}

    public Employee(Integer empId, String firstName, String lastName, String email, UserAccount userAccount, Department department, Employee leaveApprover) {
        this.empId = empId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userAccount = userAccount;
        this.department = department;
        this.leaveApprover = leaveApprover;
    }

    // getters and setters


    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Employee getLeaveApprover() {
        return leaveApprover;
    }

    public void setLeaveApprover(Employee leaveApprover) {
        this.leaveApprover = leaveApprover;
    }

    // define toString()

    @Override
    public String toString() {
        return "Employee{" +
                "empId=" + empId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", userAccount='" + userAccount + '\'' +
                ", department=" + department + '\'' +
                ", leaveApprover=" + leaveApprover +
                '}';
    }
}
