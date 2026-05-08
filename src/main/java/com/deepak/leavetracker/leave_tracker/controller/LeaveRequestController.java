package com.deepak.leavetracker.leave_tracker.controller;

import com.deepak.leavetracker.leave_tracker.dto.request.LeaveReqRequestDTO;
import com.deepak.leavetracker.leave_tracker.dto.response.ApiResponse;
import com.deepak.leavetracker.leave_tracker.dto.response.LeaveReqResponseDTO;
import com.deepak.leavetracker.leave_tracker.entity.LeaveRequest;
import com.deepak.leavetracker.leave_tracker.mapper.LeaveRequestMapper;
import com.deepak.leavetracker.leave_tracker.service.LeaveRequestService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/me/request-leaves")
public class LeaveRequestController {

    private final LeaveRequestService leaveRequestService;
    private final LeaveRequestMapper leaveRequestMapper;

    public LeaveRequestController(LeaveRequestService leaveRequestService,
                                  LeaveRequestMapper leaveRequestMapper){
        this.leaveRequestService = leaveRequestService;
        this.leaveRequestMapper = leaveRequestMapper;
    }

    @GetMapping("/list")
    public ApiResponse<List<LeaveReqResponseDTO>> fetchAllLeaveRequests(){
        List<LeaveRequest> requestList = leaveRequestService.fetchAllLeaveRequests();

        return new ApiResponse<List<LeaveReqResponseDTO>>("Leave requests fetched successfully", leaveRequestMapper.toDTOList(requestList));
    }

    @GetMapping
    public ApiResponse<List<LeaveReqResponseDTO>> fetchAllLeaveRequestsByEmployee(Authentication auth){
        String username = auth.getName();

        List<LeaveRequest> requestList = leaveRequestService.fetchAllLeaveRequestsByEmployee(username);

        return new ApiResponse<List<LeaveReqResponseDTO>>("Leave requests of employee fetched successfully", leaveRequestMapper.toDTOList(requestList));
    }

    @GetMapping("/{leaveId}")
    public ApiResponse<LeaveReqResponseDTO> fetchLeaveRequestById(@PathVariable Integer leaveId) {

        LeaveRequest leaveRequest = leaveRequestService.fetchLeaveRequestById(leaveId);

        return new ApiResponse<LeaveReqResponseDTO>("leave request (Id-"+leaveId+") fetched successfully", leaveRequestMapper.toDTO(leaveRequest));
    }

    @PostMapping("/apply")
    public ApiResponse<LeaveReqResponseDTO> applyLeave(@RequestBody LeaveReqRequestDTO dto, Authentication auth){
        String username = auth.getName();

        LeaveRequest leaveRequest = leaveRequestService.applyLeave(username, leaveRequestMapper.toEntity(dto));

        return new ApiResponse<LeaveReqResponseDTO>("Leave request applied successfully", leaveRequestMapper.toDTO(leaveRequest));

    }

    @PutMapping("/{leaveId}/approve")
    public ApiResponse<LeaveReqResponseDTO> approveLeave(@PathVariable Integer leaveId, Authentication auth){
        String username = auth.getName();

        LeaveRequest leaveRequest = leaveRequestService.approveLeave(username, leaveId);

        return new ApiResponse<LeaveReqResponseDTO>("Leave request successfully approved by manager(empId : " + leaveRequest.getApprover() + ")", leaveRequestMapper.toDTO(leaveRequest));
    }

    @PutMapping("/{leaveId}")
    public ApiResponse<LeaveReqResponseDTO> updateLeaveRequest(@PathVariable Integer leaveId, @RequestBody LeaveReqRequestDTO dto){

        LeaveRequest leaveRequest = leaveRequestService.updateLeaveRequest(leaveId, leaveRequestMapper.toEntity(dto));

        return new ApiResponse<LeaveReqResponseDTO>("Leave request - " + leaveId + ", details has been updated successfully", leaveRequestMapper.toDTO(leaveRequest));
    }

    @PatchMapping("/{leaveId}")
    public ApiResponse<LeaveReqResponseDTO> partialUpdateLeaveRequest(@PathVariable Integer leaveId,
                                             @RequestBody Map<String, Object> patchPayload){

        LeaveRequest leaveRequest = leaveRequestService.partialUpdateLeaveRequest(leaveId, patchPayload);

        return new ApiResponse<LeaveReqResponseDTO>("Leave request - " + leaveId + ", details has been updated successfully!!!", leaveRequestMapper.toDTO(leaveRequest));
    }


    @DeleteMapping("/{leaveId}")
    public ApiResponse<LeaveReqResponseDTO> deleteLeaveRequest(@PathVariable Integer leaveId){

        leaveRequestService.deleteLeaveRequest(leaveId);

        return new ApiResponse<LeaveReqResponseDTO>("Leave request deleted successfully", null);
    }
}
