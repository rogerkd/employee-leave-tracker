package com.deepak.leavetracker.leave_tracker.controller;

import com.deepak.leavetracker.leave_tracker.dto.ApiResponse;
import com.deepak.leavetracker.leave_tracker.entity.LeaveRequest;
import com.deepak.leavetracker.leave_tracker.service.LeaveRequestService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class LeaveRequestController {

    private final LeaveRequestService leaveRequestService;

    public LeaveRequestController(LeaveRequestService leaveRequestService){
        this.leaveRequestService = leaveRequestService;
    }

    @GetMapping("/requestLeaves")
    public List<LeaveRequest> fetchAllLeaveRequests(){
        return leaveRequestService.findAllLeaveRequests();
    }

    @GetMapping("/requestLeaves/{leaveId}")
    public LeaveRequest fetchLeaveRequestById(@PathVariable Integer leaveId) {
        return leaveRequestService.findLeaveRequestById(leaveId);
    }

    @PostMapping("/requestLeaves")
    public ApiResponse applyLeave(@RequestBody LeaveRequest theLeaveRequest){

        LeaveRequest saved = leaveRequestService.applyLeave(theLeaveRequest);

        return new ApiResponse("Leave request applied successfully", saved);

    }

    @PutMapping("/requestLeaves/{empId}/{leaveId}/approve")
    public ApiResponse approveLeave(@PathVariable Integer empId, @PathVariable Integer leaveId){

        LeaveRequest saved = leaveRequestService.approveLeave(empId, leaveId);

        return new ApiResponse("Leave request successfully approved by manager(empId : " + empId + ")", saved);
    }

    @PutMapping("/requestLeaves/{leaveId}")
    public ApiResponse updateLeaveRequest(@PathVariable Integer leaveId, @RequestBody LeaveRequest theLeaveRequest){

        LeaveRequest saved = leaveRequestService.updateLeaveRequest(leaveId, theLeaveRequest);

        return new ApiResponse("Leave request - " + leaveId + ", details has been updated successfully!!!", saved);
    }

    @PatchMapping("/requestLeaves/{leaveId}")
    public ApiResponse partialUpdateLeaveRequest(@PathVariable Integer leaveId,
                                             @RequestBody Map<String, Object> patchPayload){

        LeaveRequest saved = leaveRequestService.partialUpdateLeaveRequest(leaveId, patchPayload);

        return new ApiResponse("Leave request - " + leaveId + ", details has been updated successfully!!!", saved);
    }


    @DeleteMapping("/requestLeaves/{leaveId}")
    public ApiResponse deleteLeaveRequest(@PathVariable Integer leaveId){

        return leaveRequestService.deleteLeaveRequest(leaveId);

    }
}
