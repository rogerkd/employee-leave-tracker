package com.deepak.leavetracker.leave_tracker.service;

import com.deepak.leavetracker.leave_tracker.entity.LeaveRequest;
import com.deepak.leavetracker.leave_tracker.entity.LeaveType;
import com.deepak.leavetracker.leave_tracker.repository.LeaveRequestRepository;
import com.deepak.leavetracker.leave_tracker.repository.LeaveTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LeaveTypeServiceImpl implements LeaveTypeService{

    private final LeaveTypeRepository leaveTypeRepository;

    public LeaveTypeServiceImpl(LeaveTypeRepository leaveTypeRepository) {
        this.leaveTypeRepository = leaveTypeRepository;
    }

    @Override
    public LeaveType saveLeaveType(LeaveType leaveType){
        return leaveTypeRepository.save(leaveType);
    }

    @Override
    public List<LeaveType> findAllLeaveTypes(){
        return leaveTypeRepository.findAll();
    }

    @Override
    public LeaveType findLeaveTypeById(Integer leaveTypeId){
        return leaveTypeRepository.findById(leaveTypeId)
                .orElseThrow(() -> new RuntimeException("Did not find any leave Type : "+ leaveTypeId));
    }

    @Override
    public void deleteLeaveType(Integer leaveTypeId){
        leaveTypeRepository.deleteById(leaveTypeId);
    }
}
