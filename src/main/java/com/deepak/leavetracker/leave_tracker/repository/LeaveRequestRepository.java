package com.deepak.leavetracker.leave_tracker.repository;

import com.deepak.leavetracker.leave_tracker.entity.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Integer> {
}
