package com.deepak.leavetracker.leave_tracker.service;

import com.deepak.leavetracker.leave_tracker.entity.LeaveBalance;

import java.util.List;

public interface LeaveBalanceService {

    void initializeBalance(Integer empId);

    List<LeaveBalance> fetchAllLeaveBalances(Integer empId);

    LeaveBalance fetchLeaveBalanceByType(Integer empId, Integer leaveTypeId);

    void deductLeave(Integer leaveId);
}


//Employee create → initializeBalance()
//
//Apply leave → no change
//
//Approve leave → deductLeave()
//
//Reject → nothing