package com.deepak.leavetracker.leave_tracker.service;

import com.deepak.leavetracker.leave_tracker.dto.response.ApiResponse;
import com.deepak.leavetracker.leave_tracker.entity.*;
import com.deepak.leavetracker.leave_tracker.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final UserAccountRepository userAccountRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final LeaveRequestRepository leaveRequestRepository;
    private final DeptLeaveApproverRepository deptLeaveApproverRepository;
    private final LeaveBalanceService leaveBalanceService;
    private final ObjectMapper objectMapper;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository,
                               DepartmentRepository departmentRepository,
                               UserAccountRepository userAccountRepository,
                               RoleRepository roleRepository,
                               UserRoleRepository userRoleRepository,
                               LeaveRequestRepository leaveRequestRepository,
                               DeptLeaveApproverRepository deptLeaveApproverRepository,
                               LeaveBalanceService leaveBalanceService,
                               ObjectMapper objectMapper){
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.userAccountRepository = userAccountRepository;
        this.roleRepository = roleRepository;
        this.userRoleRepository = userRoleRepository;
        this.leaveRequestRepository = leaveRequestRepository;
        this.deptLeaveApproverRepository = deptLeaveApproverRepository;
        this.leaveBalanceService = leaveBalanceService;
        this.objectMapper = objectMapper;
    }


    @Override
    public List<Employee> fetchAllEmployees(){

        List<Employee> employeeList = employeeRepository.findAll();
        if(employeeList.isEmpty()){
            throw new RuntimeException("No Employees exists");
        }

        return employeeList;
    }

    @Override
    public Employee fetchEmployee(String username){

        // check if user account exists
        UserAccount userAccount = userAccountRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return employeeRepository.findByUserAccount(userAccount)
                .orElseThrow(() -> new RuntimeException("Employee with username : "+username+" doesn't exist."));
    }

    @Override
    public Employee createEmployeeProfile(Employee theEmployee, String username){

        // check if user account exists
        UserAccount userAccount = userAccountRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // check if employee profile of user already exists
        if(employeeRepository.existsByUserAccount(userAccount)) {
            throw new RuntimeException("Employee profile already exists");
        }

        if (theEmployee.getDepartment() == null || theEmployee.getDepartment().getDeptId() == null) {
            throw new RuntimeException("Department ID is required");
        }

        theEmployee.setEmpId(null); // force insert

        theEmployee.setEmail(username);

        theEmployee.setUserAccount(userAccount);

        Integer deptId = theEmployee.getDepartment().getDeptId();
        Department theDept = departmentRepository.findById(deptId)
                .orElseThrow(() -> new RuntimeException("Department with Id : "+deptId+" doesn't exist."));
        theEmployee.setDepartment(theDept);


        Employee savedEmployee = employeeRepository.save(theEmployee);

        // set user role to Associate (entry-level role)
        Role empRole = roleRepository.findByRoleName("ROLE_ASSOCIATE")
                .orElseThrow(() -> new RuntimeException("Default role not found."));

        UserRole userRole = new UserRole();
        userRole.setUserAccount(userAccount);
        userRole.setRole(empRole);

        userRoleRepository.save(userRole);

        // create leave balance account for new employee
        leaveBalanceService.initializeBalance(savedEmployee.getEmpId());

        return savedEmployee;
    }

    @Override
    public Employee updateEmployeeProfile(Employee theEmployee, String username){

        // check if user account exists
        UserAccount userAccount = userAccountRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Employee existingEmployee = employeeRepository.findByUserAccount(userAccount)
                .orElseThrow(() -> new RuntimeException("Employee with username : "+username+" doesn't exist."));

        theEmployee.setEmpId(existingEmployee.getEmpId());

        return employeeRepository.save(theEmployee);

    }

    @Override
    public Employee partialUpdateEmployeeProfile(String username, Map<String, Object> patchPayload){

        // check if user account exists
        UserAccount userAccount = userAccountRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Employee existingEmployee = employeeRepository.findByUserAccount(userAccount)
                .orElseThrow(() -> new RuntimeException("Employee with username : "+username+" doesn't exist."));

        // throw exception if request body contains empId (Primary key can't be changed)
        if(patchPayload.containsKey("empId")) {
            throw new RuntimeException("Employee Id not allowed in request body");
        }

        // apply patch on the given employee
        Employee patchedEmployee = objectMapper.updateValue(existingEmployee, patchPayload);

        return employeeRepository.save(patchedEmployee);
    }

    @Transactional
    @Override
    public void deleteEmployeeProfile(String username){

        // check if user account exists
        UserAccount userAccount = userAccountRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Employee existingEmployee = employeeRepository.findByUserAccount(userAccount)
                .orElseThrow(() -> new RuntimeException("Employee with username : "+username+" doesn't exist."));

        String user_role = "";

        // check if employee is Director (cannot delete director profile)
        boolean isDirector = userAccount.getUserRoles().stream()
                .anyMatch(ur -> ur.getRole().getRoleName().equals("ROLE_DIRECTOR"));
        if (isDirector) {
            throw new RuntimeException("Director profile cannot be deleted");
        }

        //// check if employee is manager
        boolean isManager = userAccount.getUserRoles().stream()
                .anyMatch(ur -> ur.getRole().getRoleName().equals("ROLE_MANAGER"));
        if (isManager) {
            user_role = "ROLE_MANAGER";

            // check if manager is an 'active' leave approver



        } else {

            // check if employee is associate
            boolean isAssociate = userAccount.getUserRoles().stream()
                    .anyMatch(ur -> ur.getRole().getRoleName().equals("ROLE_ASSOCIATE"));
            if (isAssociate) {
                user_role = "ROLE_ASSOCIATE";
            }

        }





        //// Mandatory steps for both manager and associate:

        // delete leave requests of employee
        leaveBalanceService.deleteLeaveBalanceByEmployee(existingEmployee);

        // delete leave balance account of employee
        leaveRequestRepository.deleteByEmployee(existingEmployee);

        // delete user roles mapping
        Role empRole = roleRepository.findByRoleName(user_role)
                .orElseThrow(() -> new RuntimeException("Default role not found."));
        userRoleRepository.deleteByUserAccountAndRole(userAccount, empRole);

        // finally, delete employee profile
        employeeRepository.deleteById(existingEmployee.getEmpId());

    }

    // only director has the power
    @Override
    public UserAccount upgradeToManager(Integer empId, String username){

        // director validate
        UserAccount director = userAccountRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Director not found"));

        boolean isDirector = director.getUserRoles().stream()
                .anyMatch(ur -> ur.getRole().getRoleName().equals("ROLE_DIRECTOR"));
        if (!isDirector) {
            throw new RuntimeException("Only director can upgrade");
        }

        // user account to be upgraded
        Employee emp = employeeRepository.findById(empId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        UserAccount associate = emp.getUserAccount();

        // upgrade leave approver of employee (from manager to director)
        // for leave requests which are pending approval, we need to update the approver to director since manager will be upgraded to director
        // in dept_leave_approver table also, we need to re-map the approver from manager to director for the department of employee whose role is being upgraded

        // unassign ROLE_ASSOCIATE
        Role associateRole = roleRepository.findByRoleName("ROLE_ASSOCIATE")
                .orElseThrow(() -> new RuntimeException("Default role not found."));
        userRoleRepository.deleteByUserAccountAndRole(associate, associateRole);

        // assign ROLE_MANAGER
        Role managerRole = roleRepository.findByRoleName("ROLE_MANAGER")
                .orElseThrow();

        UserRole userRole = new UserRole();
        userRole.setUserAccount(associate);
        userRole.setRole(managerRole);

        userRoleRepository.save(userRole);

        return associate;
    }

    // only director has the power
    @Override
    @Transactional
    public void upgradeManagerToApprover(Integer managerId, String username){

        // director validate
        UserAccount director = userAccountRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Director not found"));

        boolean isDirector = director.getUserRoles().stream()
                .anyMatch(ur -> ur.getRole().getRoleName().equals("ROLE_DIRECTOR"));
        if (!isDirector) {
            throw new RuntimeException("Only director can upgrade");
        }

        // manager validate
        Employee manager = employeeRepository.findById(managerId)
                .orElseThrow(() -> new RuntimeException("Manager not found"));

        boolean isManager = manager.getUserAccount().getUserRoles().stream()
                .anyMatch(ur -> ur.getRole().getRoleName().equals("ROLE_MANAGER"));
        if (!isManager) {
            throw new RuntimeException("Only manager can be approver");
        }

        // find manager's department
        Department managerDept = manager.getDepartment();

        // max 3 approver check in department
        int count = deptLeaveApproverRepository.countByDepartmentAndActiveTrue(managerDept);
        if (count >= 3) {
            throw new RuntimeException("Max 3 approvers allowed per department");
        }

        // check whether manager is already approver
        boolean alreadyExists = deptLeaveApproverRepository
                .existsByDepartmentAndApprover(managerDept, manager);
        if (alreadyExists) {
            throw new RuntimeException("Already an approver for this department");
        }

        // save
        DeptLeaveApprover deptLeaveApprover = new DeptLeaveApprover();
        deptLeaveApprover.setDepartment(managerDept);
        deptLeaveApprover.setApprover(manager);
        deptLeaveApprover.setActive(true); // by default

        deptLeaveApproverRepository.save(deptLeaveApprover);
    }
}
