package com.deepak.leavetracker.leave_tracker.repository;

import com.deepak.leavetracker.leave_tracker.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}
