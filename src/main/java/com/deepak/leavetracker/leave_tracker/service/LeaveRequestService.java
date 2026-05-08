package com.deepak.leavetracker.leave_tracker.service;

import com.deepak.leavetracker.leave_tracker.dto.response.ApiResponse;
import com.deepak.leavetracker.leave_tracker.entity.LeaveRequest;

import java.util.List;
import java.util.Map;

public interface LeaveRequestService {

    LeaveRequest applyLeave(String username, LeaveRequest leaveRequest);

    LeaveRequest approveLeave(String username, Integer leaveId);

    List<LeaveRequest> fetchAllLeaveRequests();

    List<LeaveRequest> fetchAllLeaveRequestsByEmployee(String username);

    LeaveRequest fetchLeaveRequestById(Integer leaveId);

    LeaveRequest updateLeaveRequest(Integer leaveId, LeaveRequest leaveRequest);

    LeaveRequest partialUpdateLeaveRequest(Integer leaveId, Map<String, Object> patchPayload);

    void deleteLeaveRequest(Integer leaveId);

    // sort leave request by asc or desc order

    // fetch leave request by empId and type

    // fetch leave request by applied date

    
}
