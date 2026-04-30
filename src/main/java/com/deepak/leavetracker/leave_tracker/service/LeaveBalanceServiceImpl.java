package com.deepak.leavetracker.leave_tracker.service;

import com.deepak.leavetracker.leave_tracker.entity.Employee;
import com.deepak.leavetracker.leave_tracker.entity.LeaveBalance;
import com.deepak.leavetracker.leave_tracker.entity.LeaveRequest;
import com.deepak.leavetracker.leave_tracker.entity.LeaveType;
import com.deepak.leavetracker.leave_tracker.repository.EmployeeRepository;
import com.deepak.leavetracker.leave_tracker.repository.LeaveBalanceRepository;
import com.deepak.leavetracker.leave_tracker.repository.LeaveRequestRepository;
import com.deepak.leavetracker.leave_tracker.repository.LeaveTypeRepository;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class LeaveBalanceServiceImpl implements LeaveBalanceService{

    private final LeaveBalanceRepository leaveBalanceRepository;
    private final LeaveTypeRepository leaveTypeRepository;
    private final EmployeeRepository employeeRepository;
    private final LeaveRequestRepository leaveRequestRepository;

    public LeaveBalanceServiceImpl(LeaveBalanceRepository leaveBalanceRepository,
                                   LeaveTypeRepository leaveTypeRepository,
                                   EmployeeRepository employeeRepository,
                                   LeaveRequestRepository leaveRequestRepository){
        this.leaveBalanceRepository = leaveBalanceRepository;
        this.leaveTypeRepository = leaveTypeRepository;
        this.employeeRepository = employeeRepository;
        this.leaveRequestRepository = leaveRequestRepository;
    }

    // when employee is created, then leave balance will be initialized
    @Override
    public void initializeBalance(Integer empId){

        List<LeaveType> leaveTypes = leaveTypeRepository.findAll();

        for(LeaveType type : leaveTypes){

            LeaveBalance leaveBalance = new LeaveBalance();

            Employee theEmployee = employeeRepository.findById(empId)
                    .orElseThrow(() -> new RuntimeException("Employee with Id : "+empId+" doesn't exist."));

            leaveBalance.setEmployee(theEmployee);
            leaveBalance.setLeaveType(type);
            leaveBalance.setTotLeaves(type.getDefaultLeaves());
            leaveBalance.setUsedLeaves(0);
            leaveBalance.setRemLeaves(type.getDefaultLeaves());

            leaveBalanceRepository.save(leaveBalance);
        }
    }

    @Override
    public List<LeaveBalance> fetchAllLeaveBalances(Integer empId){

        Employee theEmployee = employeeRepository.findById(empId)
                .orElseThrow(() -> new RuntimeException("Employee with Id : "+empId+" doesn't exist."));

        List<LeaveBalance> balances = leaveBalanceRepository.findByEmployee(theEmployee);
        if(balances.isEmpty()){
            throw new RuntimeException("Leave balances for employee - (" + empId + ") doesn't exist.");
        }
        return balances;
    }

    @Override
    public LeaveBalance fetchLeaveBalanceByType(Integer empId, Integer leaveTypeId){
        Employee theEmployee = employeeRepository.findById(empId)
                .orElseThrow(() -> new RuntimeException("Employee with Id : "+empId+" doesn't exist."));

        LeaveType theLeaveType = leaveTypeRepository.findById(leaveTypeId)
                .orElseThrow(() -> new RuntimeException("Leave type with Id : "+leaveTypeId+" doesn't exist."));

        LeaveBalance balance = leaveBalanceRepository.findByEmployeeAndLeaveType(theEmployee, theLeaveType);
        if(balance == null){
            throw new RuntimeException("Leave balance for employee - (" + empId + ") with leave type - (" + leaveTypeId +") doesn't exist.");
        }
        return balance;
    }

    // only when manager approves the leave
    @Override
    public void deductLeave(Integer leaveId){
        LeaveRequest leave = leaveRequestRepository.findById(leaveId)
                .orElseThrow(() -> new RuntimeException("No leave request exists with leaveId - " + leaveId));

        // calculate no. of leave days
        long days = ChronoUnit.DAYS.between(
                leave.getStartDate(),
                leave.getEndDate()
        ) + 1;

        // fetch leave balance
        LeaveBalance balance = leaveBalanceRepository.findByEmployeeAndLeaveType(leave.getEmployee(), leave.getLeaveType());
        if(balance==null){
            throw new RuntimeException("Leave balance for employee - (" + leave.getEmployee().getEmpId() + ") with leave type - (" + leave.getLeaveType().getLeaveTypeId() +") doesn't exist.");
        }

        // check sufficient balance
        if(balance.getRemLeaves() < days){
            throw new RuntimeException("Insufficient leave balance");
        }

        // update balance
        balance.setUsedLeaves(balance.getUsedLeaves() + (int) days);
        balance.setRemLeaves(balance.getRemLeaves() - (int) days);

        // save balance
        leaveBalanceRepository.save(balance);

    }
}
