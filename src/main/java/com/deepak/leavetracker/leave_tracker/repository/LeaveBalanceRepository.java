package com.deepak.leavetracker.leave_tracker.repository;

import com.deepak.leavetracker.leave_tracker.entity.Employee;
import com.deepak.leavetracker.leave_tracker.entity.LeaveBalance;
import com.deepak.leavetracker.leave_tracker.entity.LeaveType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeaveBalanceRepository extends JpaRepository<LeaveBalance, Integer> {

    List<LeaveBalance> findByEmployee(Employee employee);

    LeaveBalance findByEmployeeAndLeaveType(Employee employee,
                                            LeaveType leaveType);
}
