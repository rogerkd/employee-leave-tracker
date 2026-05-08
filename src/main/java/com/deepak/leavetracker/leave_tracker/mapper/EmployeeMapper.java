package com.deepak.leavetracker.leave_tracker.mapper;

import com.deepak.leavetracker.leave_tracker.dto.request.EmployeeRequestDTO;
import com.deepak.leavetracker.leave_tracker.dto.response.EmployeeResponseDTO;
import com.deepak.leavetracker.leave_tracker.entity.Employee;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    Employee toEntity(EmployeeRequestDTO dto);

    EmployeeResponseDTO toDTO(Employee employee);

    List<EmployeeResponseDTO> toDTOList(List<Employee> employees);

}
