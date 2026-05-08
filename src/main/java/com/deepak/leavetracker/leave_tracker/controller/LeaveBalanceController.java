package com.deepak.leavetracker.leave_tracker.controller;

import com.deepak.leavetracker.leave_tracker.dto.response.ApiResponse;
import com.deepak.leavetracker.leave_tracker.dto.response.EmployeeResponseDTO;
import com.deepak.leavetracker.leave_tracker.dto.response.LeaveBalanceResponseDTO;
import com.deepak.leavetracker.leave_tracker.entity.LeaveBalance;
import com.deepak.leavetracker.leave_tracker.mapper.LeaveBalanceMapper;
import com.deepak.leavetracker.leave_tracker.service.LeaveBalanceService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/employees/me/leave-balances")
public class LeaveBalanceController {

    private final LeaveBalanceService leaveBalanceService;
    private final LeaveBalanceMapper leaveBalanceMapper;

    public LeaveBalanceController(LeaveBalanceService leaveBalanceService,
                                  LeaveBalanceMapper leaveBalanceMapper){
        this.leaveBalanceService = leaveBalanceService;
        this.leaveBalanceMapper = leaveBalanceMapper;
    }

    @GetMapping
    public ApiResponse<List<LeaveBalanceResponseDTO>> fetchAllLeaveBalancesByEmployee(Authentication auth){
        String username = auth.getName();

        List<LeaveBalance> balanceList = leaveBalanceService.fetchAllLeaveBalancesByEmployee(username);

        return new ApiResponse<List<LeaveBalanceResponseDTO>>("Leave balances list fetched successfully", leaveBalanceMapper.toDTOList(balanceList));
    }

    @GetMapping("/{leaveTypeId}")
    public ApiResponse<LeaveBalanceResponseDTO> fetchLeaveBalanceByType(@PathVariable Integer leaveTypeId, Authentication auth){
        String username = auth.getName();

        LeaveBalance balance = leaveBalanceService.fetchLeaveBalanceByType(username, leaveTypeId);

        return new ApiResponse<LeaveBalanceResponseDTO>("Leave balance fetched successfully", leaveBalanceMapper.toDTO(balance));
    }
}
