package com.deepak.leavetracker.leave_tracker.controller;

import com.deepak.leavetracker.leave_tracker.entity.LeaveBalance;
import com.deepak.leavetracker.leave_tracker.service.LeaveBalanceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LeaveBalanceController {

    private final LeaveBalanceService leaveBalanceService;

    public LeaveBalanceController(LeaveBalanceService leaveBalanceService){
        this.leaveBalanceService = leaveBalanceService;
    }

    @GetMapping("/leaveBalances/{empId}")
    public List<LeaveBalance> fetchAllLeaveBalances(@PathVariable Integer empId){
        return leaveBalanceService.fetchAllLeaveBalances(empId);
    }

    @GetMapping("/leaveBalances/{empId}/{leaveTypeId}")
    public LeaveBalance fetchLeaveBalanceByType(@PathVariable Integer empId, @PathVariable Integer leaveTypeId){
        return leaveBalanceService.fetchLeaveBalanceByType(empId, leaveTypeId);
    }
}
