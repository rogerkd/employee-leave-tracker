package com.deepak.leavetracker.leave_tracker.service;

import com.deepak.leavetracker.leave_tracker.dto.response.ApiResponse;
import com.deepak.leavetracker.leave_tracker.entity.Employee;
import com.deepak.leavetracker.leave_tracker.entity.UserAccount;

import java.util.List;
import java.util.Map;

public interface EmployeeService {

    List<Employee> fetchAllEmployees();

    Employee fetchEmployee(String username);

    Employee createEmployeeProfile(Employee theEmployee, String username);

    Employee updateEmployeeProfile(Employee theEmployee, String username);

    Employee partialUpdateEmployeeProfile(String username, Map<String, Object> patchPayload);

    void deleteEmployeeProfile(String username);

    UserAccount upgradeToManager(Integer empId, String username);

    void upgradeManagerToApprover(Integer managerId, String username);

    // upgrade to director
}
