package com.deepak.leavetracker.leave_tracker.service;

import com.deepak.leavetracker.leave_tracker.dto.ApiResponse;
import com.deepak.leavetracker.leave_tracker.entity.Employee;

import java.util.List;
import java.util.Map;

public interface EmployeeService {

    List<Employee> fetchAllEmployee();

    Employee fetchEmployeeById(Integer empId);

    Employee addEmployee(Employee theEmployee);

    Employee updateEmployee(Integer empId, Employee theEmployee);

    Employee partialUpdateEmployee(Integer empId, Map<String, Object> patchPayload);

    ApiResponse deleteEmployee(Integer empId);
}
