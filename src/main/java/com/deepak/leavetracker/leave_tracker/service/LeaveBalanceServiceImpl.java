package com.deepak.leavetracker.leave_tracker.service;

import com.deepak.leavetracker.leave_tracker.entity.*;
import com.deepak.leavetracker.leave_tracker.repository.*;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class LeaveBalanceServiceImpl implements LeaveBalanceService{

    private final LeaveBalanceRepository leaveBalanceRepository;
    private final LeaveTypeRepository leaveTypeRepository;
    private final EmployeeRepository employeeRepository;
    private final LeaveRequestRepository leaveRequestRepository;
    private final UserAccountRepository userAccountRepository;

    public LeaveBalanceServiceImpl(LeaveBalanceRepository leaveBalanceRepository,
                                   LeaveTypeRepository leaveTypeRepository,
                                   EmployeeRepository employeeRepository,
                                   LeaveRequestRepository leaveRequestRepository,
                                   UserAccountRepository userAccountRepository){
        this.leaveBalanceRepository = leaveBalanceRepository;
        this.leaveTypeRepository = leaveTypeRepository;
        this.employeeRepository = employeeRepository;
        this.leaveRequestRepository = leaveRequestRepository;
        this.userAccountRepository = userAccountRepository;
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
    public List<LeaveBalance> fetchAllLeaveBalancesByEmployee(String username){

        // check if user account exists
        UserAccount userAccount = userAccountRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Employee existingEmployee = employeeRepository.findByUserAccount(userAccount)
                .orElseThrow(() -> new RuntimeException("Employee with username : "+username+" doesn't exist."));

        List<LeaveBalance> balances = leaveBalanceRepository.findByEmployee(existingEmployee);
        if(balances.isEmpty()){
            throw new RuntimeException("Leave balances for employee - (" + existingEmployee.getEmpId() + ") doesn't exist.");
        }
        return balances;
    }

    @Override
    public LeaveBalance fetchLeaveBalanceByType(String username, Integer leaveTypeId){

        // check if user account exists
        UserAccount userAccount = userAccountRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Employee existingEmployee = employeeRepository.findByUserAccount(userAccount)
                .orElseThrow(() -> new RuntimeException("Employee with username : "+username+" doesn't exist."));

        LeaveType theLeaveType = leaveTypeRepository.findById(leaveTypeId)
                .orElseThrow(() -> new RuntimeException("Leave type with Id : "+leaveTypeId+" doesn't exist."));

        LeaveBalance balance = leaveBalanceRepository.findByEmployeeAndLeaveType(existingEmployee, theLeaveType);
        if(balance == null){
            throw new RuntimeException("Leave balance for employee - (" + existingEmployee.getEmpId() + ") with leave type - (" + leaveTypeId +") doesn't exist.");
        }
        return balance;
    }

    @Override
    public void deleteLeaveBalanceByEmployee(Employee employee){

        leaveBalanceRepository.deleteByEmployee(employee);
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
