package com.deepak.leavetracker.leave_tracker.mapper;

import com.deepak.leavetracker.leave_tracker.dto.response.LeaveBalanceResponseDTO;
import com.deepak.leavetracker.leave_tracker.entity.LeaveBalance;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LeaveBalanceMapper {

    LeaveBalanceResponseDTO toDTO(LeaveBalance leaveBalance);

    List<LeaveBalanceResponseDTO> toDTOList(List<LeaveBalance> leaveBalances);
}
