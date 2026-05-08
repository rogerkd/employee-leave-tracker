package com.deepak.leavetracker.leave_tracker.repository;

import com.deepak.leavetracker.leave_tracker.entity.Department;
import com.deepak.leavetracker.leave_tracker.entity.DeptLeaveApprover;
import com.deepak.leavetracker.leave_tracker.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeptLeaveApproverRepository extends JpaRepository<DeptLeaveApprover, Integer> {

    Integer countByDepartmentAndActiveTrue(Department department);

    Boolean existsByDepartmentAndApprover(Department department, Employee manager);

    DeptLeaveApprover findByDepartmentAndApprover(Department department, Employee manager);


}
