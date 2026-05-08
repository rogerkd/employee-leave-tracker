package com.deepak.leavetracker.leave_tracker.mapper;

import com.deepak.leavetracker.leave_tracker.dto.request.LeaveReqRequestDTO;
import com.deepak.leavetracker.leave_tracker.dto.response.LeaveReqResponseDTO;
import com.deepak.leavetracker.leave_tracker.entity.LeaveRequest;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LeaveRequestMapper {

    LeaveRequest toEntity(LeaveReqRequestDTO dto);

    LeaveReqResponseDTO toDTO(LeaveRequest leaveRequest);

    List<LeaveReqResponseDTO> toDTOList(List<LeaveRequest> leaveRequests);

}
