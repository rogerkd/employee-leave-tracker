package com.deepak.leavetracker.leave_tracker.controller;

import com.deepak.leavetracker.leave_tracker.dto.ApiResponse;
import com.deepak.leavetracker.leave_tracker.entity.Employee;
import com.deepak.leavetracker.leave_tracker.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }

    // fetch list of employees
    @GetMapping("/employees")
    public List<Employee> fetchAllEmployee(){
        return employeeService.fetchAllEmployee();
    }

    // fetch employee by empId
    @GetMapping("/employees/{empId}")
    public Employee fetchEmployeeById(@PathVariable Integer empId) {
        return employeeService.fetchEmployeeById(empId);
    }

    @PostMapping("/employees")
    public ApiResponse addEmployee(@RequestBody Employee theEmployee){
        return new ApiResponse("Employee added successfully", employeeService.addEmployee(theEmployee));
    }

    // update employee (full update)
    @PutMapping("/employees/{empId}")
    public ApiResponse updateEmployee(@PathVariable Integer empId, @RequestBody Employee theEmployee){

        Employee saved = employeeService.updateEmployee(empId, theEmployee);

        return new ApiResponse(saved.getFirstName()+"'s" + " details has been updated successfully!!!", saved);
    }

    // update employee (partial update)
    @PatchMapping("/employees/{empId}")
    public ApiResponse partialUpdateEmployee(@PathVariable Integer empId,
                                        @RequestBody Map<String, Object> patchPayload){

        Employee saved = employeeService.partialUpdateEmployee(empId, patchPayload);

        return new ApiResponse(saved.getFirstName()+"'s" + " details has been updated successfully!!!", saved);
    }

    // delete employee
    @DeleteMapping("/employees/{empId}")
    public ApiResponse deleteEmployee(@PathVariable Integer empId){
        return employeeService.deleteEmployee(empId);
    }


}
