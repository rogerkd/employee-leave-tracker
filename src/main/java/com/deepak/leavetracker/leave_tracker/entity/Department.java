package com.deepak.leavetracker.leave_tracker.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.AnyDiscriminatorImplicitValues;

@Entity
@Table(name="departments")
public class Department {

    // fields

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="dept_id")
    private Integer deptId;

    @Column(name="dept_name")
    private String deptName;

    // constructors

    public Department(){}

    public Department(Integer deptId, String deptName){
        this.deptId = deptId;
        this.deptName = deptName;
    }

    // setters and getters


    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
}
