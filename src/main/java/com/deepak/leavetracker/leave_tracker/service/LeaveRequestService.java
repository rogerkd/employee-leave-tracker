package com.deepak.leavetracker.leave_tracker.service;

import com.deepak.leavetracker.leave_tracker.dto.ApiResponse;
import com.deepak.leavetracker.leave_tracker.entity.LeaveRequest;

import java.util.List;
import java.util.Map;

public interface LeaveRequestService {

    LeaveRequest applyLeave(LeaveRequest leaveRequest);

    LeaveRequest approveLeave(Integer empId, Integer leaveId);

    List<LeaveRequest> findAllLeaveRequests();

    LeaveRequest findLeaveRequestById(Integer leaveId);

    LeaveRequest updateLeaveRequest(Integer leaveId, LeaveRequest leaveRequest);

    LeaveRequest partialUpdateLeaveRequest(Integer leaveId, Map<String, Object> patchPayload);

    ApiResponse deleteLeaveRequest(Integer leaveId);

    
}
