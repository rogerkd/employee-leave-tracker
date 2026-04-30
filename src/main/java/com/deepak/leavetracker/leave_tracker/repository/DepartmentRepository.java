package com.deepak.leavetracker.leave_tracker.repository;

import com.deepak.leavetracker.leave_tracker.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
}
