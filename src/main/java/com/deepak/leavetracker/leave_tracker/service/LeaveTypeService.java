package com.deepak.leavetracker.leave_tracker.service;

import com.deepak.leavetracker.leave_tracker.entity.LeaveRequest;
import com.deepak.leavetracker.leave_tracker.entity.LeaveType;

import java.util.List;

public interface LeaveTypeService {

    LeaveType saveLeaveType(LeaveType leaveType);

    List<LeaveType> findAllLeaveTypes();

    LeaveType findLeaveTypeById(Integer leaveTypeId);

    void deleteLeaveType(Integer leaveTypeId);
}
