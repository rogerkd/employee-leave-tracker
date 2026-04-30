package com.deepak.leavetracker.leave_tracker.service;

import com.deepak.leavetracker.leave_tracker.dto.ApiResponse;
import com.deepak.leavetracker.leave_tracker.entity.Employee;
import com.deepak.leavetracker.leave_tracker.entity.LeaveRequest;
import com.deepak.leavetracker.leave_tracker.entity.LeaveType;
import com.deepak.leavetracker.leave_tracker.repository.EmployeeRepository;
import com.deepak.leavetracker.leave_tracker.repository.LeaveRequestRepository;
import com.deepak.leavetracker.leave_tracker.repository.LeaveTypeRepository;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class LeaveRequestServiceImpl implements LeaveRequestService{

    private final LeaveRequestRepository leaveRequestRepository;
    private final EmployeeRepository employeeRepository;
    private final LeaveTypeRepository leaveTypeRepository;
    private final LeaveBalanceService leaveBalanceService;
    private final ObjectMapper objectMapper;

    public LeaveRequestServiceImpl(LeaveRequestRepository leaveRequestRepository,
                                   EmployeeRepository employeeRepository,
                                   LeaveTypeRepository leaveTypeRepository,
                                   LeaveBalanceService leaveBalanceService,
                                   ObjectMapper objectMapper) {
        this.leaveRequestRepository = leaveRequestRepository;
        this.employeeRepository = employeeRepository;
        this.leaveTypeRepository = leaveTypeRepository;
        this.leaveBalanceService = leaveBalanceService;
        this.objectMapper = objectMapper;
    }

    @Override
    public LeaveRequest applyLeave(LeaveRequest theLeaveRequest){
        if (theLeaveRequest.getEmployee().getEmpId() == null) {
            throw new RuntimeException("Employee ID is required");
        }

        if (theLeaveRequest.getLeaveType().getLeaveTypeId() == null) {
            throw new RuntimeException("Leave Type ID is required");
        }

        theLeaveRequest.setLeaveId(null); // force insert

        // set employee
        Integer empId = theLeaveRequest.getEmployee().getEmpId();
        Employee theEmployee = employeeRepository.findById(empId)
                .orElseThrow(() -> new RuntimeException("Employee with Id : "+empId+" doesn't exist."));
        theLeaveRequest.setEmployee(theEmployee);

        // set leave type
        Integer leaveTypeId = theLeaveRequest.getLeaveType().getLeaveTypeId();
        LeaveType theLeaveType = leaveTypeRepository.findById(leaveTypeId)
                .orElseThrow(() -> new RuntimeException("Leave Type with Id : "+leaveTypeId+" doesn't exist."));
        theLeaveRequest.setLeaveType(theLeaveType);

        return leaveRequestRepository.save(theLeaveRequest);
    }

    @Override
    public LeaveRequest approveLeave(Integer empId, Integer leaveId){

        // fetch employee
        Employee employee = employeeRepository.findById(empId)
                .orElseThrow(() -> new RuntimeException("Employee with Id : "+empId+" doesn't exist."));

        // fetch leave request
        LeaveRequest leave = findLeaveRequestById(leaveId);
        if(leave==null){
            throw new RuntimeException("Leave request with leave Id : " + leaveId + " doesn't exist.");
        }

        // check if employee is manager
        if(!Objects.equals(employee.getEmpId(), leave.getApprover())){
            throw new RuntimeException("Only a Manager can approve the leaves.");
        }

        // update status
        leave.setStatus("APPROVED");

        // deduct leaves
        leaveBalanceService.deductLeave(leaveId);

        return leaveRequestRepository.save(leave);
    }

    @Override
    public List<LeaveRequest> findAllLeaveRequests(){
        return leaveRequestRepository.findAll();
    }

    @Override
    public LeaveRequest findLeaveRequestById(Integer leaveId){
        return leaveRequestRepository.findById(leaveId)
                .orElseThrow(() -> new RuntimeException("Did not find any leave request : "+ leaveId));

    }

    @Override
    public LeaveRequest updateLeaveRequest(Integer leaveId, LeaveRequest theLeaveRequest){
        LeaveRequest existingLeaveRequest = findLeaveRequestById(leaveId);

        if(existingLeaveRequest == null){
            throw new RuntimeException("Leave request not found - " + leaveId);
        }

        // if leaveId is null, then explicitly set it
        theLeaveRequest.setLeaveId(leaveId);

        return leaveRequestRepository.save(theLeaveRequest);
    }

    @Override
    public LeaveRequest partialUpdateLeaveRequest(Integer leaveId, Map<String, Object> patchPayload){
        LeaveRequest existingLeaveRequest = findLeaveRequestById(leaveId);

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
    public ApiResponse deleteLeaveRequest(Integer leaveId){

        LeaveRequest theLeaveRequest =  findLeaveRequestById(leaveId);

        if(theLeaveRequest == null){
            throw new RuntimeException("Leave Request not found - " + leaveId);
        }

        leaveRequestRepository.deleteById(leaveId);

        return new ApiResponse("Leave request removed - ", theLeaveRequest);

    }
}
