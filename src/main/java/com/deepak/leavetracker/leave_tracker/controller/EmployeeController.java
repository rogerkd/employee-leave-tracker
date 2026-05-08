package com.deepak.leavetracker.leave_tracker.controller;

import com.deepak.leavetracker.leave_tracker.dto.response.ApiResponse;
import com.deepak.leavetracker.leave_tracker.dto.request.EmployeeRequestDTO;
import com.deepak.leavetracker.leave_tracker.dto.response.EmployeeResponseDTO;
import com.deepak.leavetracker.leave_tracker.dto.response.UserAccountResponseDTO;
import com.deepak.leavetracker.leave_tracker.entity.Employee;
import com.deepak.leavetracker.leave_tracker.entity.UserAccount;
import com.deepak.leavetracker.leave_tracker.mapper.EmployeeMapper;
import com.deepak.leavetracker.leave_tracker.mapper.UserAccountMapper;
import com.deepak.leavetracker.leave_tracker.service.EmployeeService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeMapper employeeMapper;
    private final UserAccountMapper userAccountMapper;

    public EmployeeController(EmployeeService employeeService,
                              EmployeeMapper employeeMapper,
                              UserAccountMapper userAccountMapper){
        this.employeeService = employeeService;
        this.employeeMapper = employeeMapper;
        this.userAccountMapper = userAccountMapper;
    }

    // fetch list of employees
    @PreAuthorize("hasRole('DIRECTOR')")
    @GetMapping("/list")
    public ApiResponse<List<EmployeeResponseDTO>> fetchAllEmployees(){
        List<Employee> empList= employeeService.fetchAllEmployees();

        return new ApiResponse<List<EmployeeResponseDTO>>("Employee list fetched successfully", employeeMapper.toDTOList(empList));
    }

    // fetch employee by username
    @GetMapping("/me")
    public ApiResponse<EmployeeResponseDTO> fetchEmployee(Authentication auth) {
        String username = auth.getName();

        Employee emp = employeeService.fetchEmployee(username);

        return new ApiResponse<EmployeeResponseDTO>("Profile fetched successfully", employeeMapper.toDTO(emp));
    }

    // create employee profile
    @PostMapping("/me")
    public ApiResponse<EmployeeResponseDTO> createEmployeeProfile(@RequestBody EmployeeRequestDTO dto, Authentication auth){
        String username = auth.getName();

        Employee emp = employeeService.createEmployeeProfile(employeeMapper.toEntity(dto), username);

        return new ApiResponse<EmployeeResponseDTO>("Profile created successfully", employeeMapper.toDTO(emp));
    }

    // update profile (full update)
    @PutMapping("/me")
    public ApiResponse<EmployeeResponseDTO> updateEmployeeProfile(@RequestBody EmployeeRequestDTO dto, Authentication auth){
        String username = auth.getName();

        Employee emp = employeeService.updateEmployeeProfile(employeeMapper.toEntity(dto), username);

        return new ApiResponse<EmployeeResponseDTO>("Profile updated successfully", employeeMapper.toDTO(emp));
    }

    // update profile (partial update)
    @PatchMapping("/me")
    public ApiResponse<EmployeeResponseDTO> partialUpdateEmployeeProfile(@RequestBody Map<String, Object> patchPayload, Authentication auth){
        String username = auth.getName();

        Employee emp = employeeService.partialUpdateEmployeeProfile(username, patchPayload);

        return new ApiResponse<EmployeeResponseDTO>(emp.getFirstName()+"'s" + " details has been updated successfully", employeeMapper.toDTO(emp));
    }

    // delete profile
    @DeleteMapping("/me")
    public ApiResponse<String> deleteEmployeeProfile(Authentication auth){
        String username = auth.getName();

        employeeService.deleteEmployeeProfile(username);

        return new ApiResponse<>("Profile deleted successfully", null);
    }

    // upgrade associate to manager (only done by Director)
    @PreAuthorize("hasRole('DIRECTOR')")
    @PatchMapping("/{empId}/upgrade/manager")
    public ApiResponse<UserAccountResponseDTO> upgradeToManager(@PathVariable Integer empId, Authentication auth){
        String username = auth.getName();

        UserAccount manager = employeeService.upgradeToManager(empId, username);

        return new ApiResponse<UserAccountResponseDTO>("Employee role successfully upgraded to manager", userAccountMapper.toDTO(manager));
    }



}
