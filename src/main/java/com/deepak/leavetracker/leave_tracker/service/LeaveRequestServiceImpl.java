package com.deepak.leavetracker.leave_tracker.service;

import com.deepak.leavetracker.leave_tracker.dto.response.ApiResponse;
import com.deepak.leavetracker.leave_tracker.entity.Employee;
import com.deepak.leavetracker.leave_tracker.entity.LeaveRequest;
import com.deepak.leavetracker.leave_tracker.entity.LeaveType;
import com.deepak.leavetracker.leave_tracker.entity.UserAccount;
import com.deepak.leavetracker.leave_tracker.repository.EmployeeRepository;
import com.deepak.leavetracker.leave_tracker.repository.LeaveRequestRepository;
import com.deepak.leavetracker.leave_tracker.repository.LeaveTypeRepository;
import com.deepak.leavetracker.leave_tracker.repository.UserAccountRepository;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class LeaveRequestServiceImpl implements LeaveRequestService{

    private final LeaveRequestRepository leaveRequestRepository;
    private final EmployeeRepository employeeRepository;
    private final LeaveTypeRepository leaveTypeRepository;
    private final UserAccountRepository userAccountRepository;
    private final LeaveBalanceService leaveBalanceService;
    private final ObjectMapper objectMapper;

    public LeaveRequestServiceImpl(LeaveRequestRepository leaveRequestRepository,
                                   EmployeeRepository employeeRepository,
                                   LeaveTypeRepository leaveTypeRepository,
                                   UserAccountRepository userAccountRepository,
                                   LeaveBalanceService leaveBalanceService,
                                   ObjectMapper objectMapper) {
        this.leaveRequestRepository = leaveRequestRepository;
        this.employeeRepository = employeeRepository;
        this.leaveTypeRepository = leaveTypeRepository;
        this.userAccountRepository = userAccountRepository;
        this.leaveBalanceService = leaveBalanceService;
        this.objectMapper = objectMapper;
    }

    @Override
    public LeaveRequest applyLeave(String username, LeaveRequest theLeaveRequest){

        // check if user account exists
        UserAccount userAccount = userAccountRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Employee existingEmployee = employeeRepository.findByUserAccount(userAccount)
                .orElseThrow(() -> new RuntimeException("Employee with username : "+username+" doesn't exist."));

        if (theLeaveRequest.getLeaveType().getLeaveTypeId() == null) {
            throw new RuntimeException("Leave Type ID is required");
        }

        theLeaveRequest.setLeaveId(null); // force insert

        // set employee
        theLeaveRequest.setEmployee(existingEmployee);

        // set appliedOn
        theLeaveRequest.setAppliedOn(LocalDate.now());

        return leaveRequestRepository.save(theLeaveRequest);
    }

    @Override
    public LeaveRequest approveLeave(String username, Integer leaveId){

        // check if user account exists
        UserAccount userAccount = userAccountRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Employee existingEmployee = employeeRepository.findByUserAccount(userAccount)
                .orElseThrow(() -> new RuntimeException("Employee with username : "+username+" doesn't exist."));

        // fetch leave request
        LeaveRequest leave = fetchLeaveRequestById(leaveId);
        if(leave==null){
            throw new RuntimeException("Leave request with leave Id : " + leaveId + " doesn't exist.");
        }

        // check if employee is manager
        if(!Objects.equals(existingEmployee.getEmpId(), leave.getApprover().getEmpId())){
            throw new RuntimeException("Only a Manager can approve the leaves.");
        }

        // update status
        leave.setStatus("APPROVED");

        // deduct leaves
        leaveBalanceService.deductLeave(leaveId);

        return leaveRequestRepository.save(leave);
    }

    @Override
    public List<LeaveRequest> fetchAllLeaveRequests(){
        return leaveRequestRepository.findAll();
    }

    @Override
    public List<LeaveRequest> fetchAllLeaveRequestsByEmployee(String username){

        // check if user account exists
        UserAccount userAccount = userAccountRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Employee existingEmployee = employeeRepository.findByUserAccount(userAccount)
                .orElseThrow(() -> new RuntimeException("Employee with username : "+username+" doesn't exist."));

        return leaveRequestRepository.findAllByEmployee(existingEmployee);
    }

    @Override
    public LeaveRequest fetchLeaveRequestById(Integer leaveId){
        return leaveRequestRepository.findById(leaveId)
                .orElseThrow(() -> new RuntimeException("Did not find any leave request : "+ leaveId));

    }

    @Override
    public LeaveRequest updateLeaveRequest(Integer leaveId, LeaveRequest theLeaveRequest){
        LeaveRequest existingLeaveRequest = fetchLeaveRequestById(leaveId);

        if(existingLeaveRequest == null){
            throw new RuntimeException("Leave request not found - " + leaveId);
        }

        // if leaveId is null, then explicitly set it
        theLeaveRequest.setLeaveId(leaveId);

        return leaveRequestRepository.save(theLeaveRequest);
    }

    @Override
    public LeaveRequest partialUpdateLeaveRequest(Integer leaveId, Map<String, Object> patchPayload){
        LeaveRequest existingLeaveRequest = fetchLeaveRequestById(leaveId);

        // throw exception if null
        if(existingLeaveRequest == null){
            throw new RuntimeException("Leave request not found - " + leaveId);
        }

        // throw exception if request body contains leaveId (Primary key can't be changed)
        if(patchPayload.containsKey("leaveId")) {
            throw new RuntimeException("Leave request Id not allowed in request body - " + leaveId);
        }

        // apply patch on the given leaveRequest
        LeaveRequest patchedLeaveRequest = objectMapper.updateValue(existingLeaveRequest, patchPayload);

        return leaveRequestRepository.save(patchedLeaveRequest);
    }

    @Override
    public void deleteLeaveRequest(Integer leaveId){

        LeaveRequest theLeaveRequest =  fetchLeaveRequestById(leaveId);
        if(theLeaveRequest == null){
            throw new RuntimeException("Leave Request not found - " + leaveId);
        }

        leaveRequestRepository.deleteById(leaveId);

    }
}
