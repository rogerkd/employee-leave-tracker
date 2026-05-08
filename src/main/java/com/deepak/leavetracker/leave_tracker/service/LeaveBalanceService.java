package com.deepak.leavetracker.leave_tracker.service;

import com.deepak.leavetracker.leave_tracker.entity.Employee;
import com.deepak.leavetracker.leave_tracker.entity.LeaveBalance;

import java.util.List;

public interface LeaveBalanceService {

    void initializeBalance(Integer empId);

    List<LeaveBalance> fetchAllLeaveBalancesByEmployee(String username);

    LeaveBalance fetchLeaveBalanceByType(String username, Integer leaveTypeId);

    void deleteLeaveBalanceByEmployee(Employee employee);

    void deductLeave(Integer leaveId);
}


//Employee create → initializeBalance()
//
//Apply leave → no change
//
//Approve leave → deductLeave()
//
//Reject → nothing