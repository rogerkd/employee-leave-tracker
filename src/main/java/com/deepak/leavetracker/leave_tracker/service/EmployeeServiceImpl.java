package com.deepak.leavetracker.leave_tracker.service;

import com.deepak.leavetracker.leave_tracker.dto.ApiResponse;
import com.deepak.leavetracker.leave_tracker.entity.Department;
import com.deepak.leavetracker.leave_tracker.entity.Employee;
import com.deepak.leavetracker.leave_tracker.repository.DepartmentRepository;
import com.deepak.leavetracker.leave_tracker.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final LeaveBalanceService leaveBalanceService;
    private final ObjectMapper objectMapper;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository,
                               DepartmentRepository departmentRepository,
                               LeaveBalanceService leaveBalanceService,
                               ObjectMapper objectMapper){
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.leaveBalanceService = leaveBalanceService;
        this.objectMapper = objectMapper;
    }


    @Override
    public List<Employee> fetchAllEmployee(){

        List<Employee> employeeList = employeeRepository.findAll();
        if(employeeList.isEmpty()){
            throw new RuntimeException("No Employees exists");
        }

        return employeeList;
    }

    @Override
    public Employee fetchEmployeeById(Integer empId){
        return employeeRepository.findById(empId)
                .orElseThrow(() -> new RuntimeException("Employee with Id : "+empId+" doesn't exist."));
    }

    @Override
    public Employee addEmployee(Employee theEmployee){
        if (theEmployee.getDepartment() == null || theEmployee.getDepartment().getDeptId() == null) {
            throw new RuntimeException("Department ID is required");
        }

        Integer deptId = theEmployee.getDepartment().getDeptId();
        Department theDept = departmentRepository.findById(deptId)
                .orElseThrow(() -> new RuntimeException("Department with Id : "+deptId+" doesn't exist."));
        theEmployee.setDepartment(theDept);

        theEmployee.setEmpId(null); // force insert

        Employee savedEmployee = employeeRepository.save(theEmployee);

        // create leave balance account for new employee
        leaveBalanceService.initializeBalance(savedEmployee.getEmpId());

        return savedEmployee;
    }

    @Override
    public Employee updateEmployee(Integer empId, Employee theEmployee){
        Employee existingEmployee = fetchEmployeeById(empId);
        if(existingEmployee == null){
            throw new RuntimeException("Employee not found - " + empId);
        }

        // if empId is null, then explicitly set it
        theEmployee.setEmpId(empId);

        return employeeRepository.save(theEmployee);
    }

    @Override
    public Employee partialUpdateEmployee(Integer empId, Map<String, Object> patchPayload){
        Employee theEmployee = fetchEmployeeById(empId);

        // throw exception if null
        if(theEmployee == null){
            throw new RuntimeException("Employee not found - " + empId);
        }

        // throw exception if request body contains empId (Primary key can't be changed)
        if(patchPayload.containsKey("empId")) {
            throw new RuntimeException("Employee Id not allowed in request body - " + empId);
        }

        // apply patch on the given employee
        Employee patchedEmployee = objectMapper.updateValue(theEmployee, patchPayload);

        return employeeRepository.save(patchedEmployee);
    }

    @Override
    public ApiResponse deleteEmployee(Integer empId){
        Employee theEmployee =  fetchEmployeeById(empId);
        if(theEmployee == null){
            throw new RuntimeException("Employee not found - " + empId);
        }

        employeeRepository.deleteById(empId);

        return new ApiResponse("Employee details removed", theEmployee);
    }
}
