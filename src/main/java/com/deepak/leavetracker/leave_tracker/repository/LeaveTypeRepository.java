package com.deepak.leavetracker.leave_tracker.repository;

import com.deepak.leavetracker.leave_tracker.entity.LeaveType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaveTypeRepository extends JpaRepository<LeaveType, Integer> {
}
