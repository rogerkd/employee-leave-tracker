package com.deepak.leavetracker.leave_tracker.controller;

import com.deepak.leavetracker.leave_tracker.dto.response.ApiResponse;
import com.deepak.leavetracker.leave_tracker.entity.Department;
import com.deepak.leavetracker.leave_tracker.service.DepartmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService){
        this.departmentService = departmentService;
    }

    // fetch list of departments
    @GetMapping("/departments")
    public List<Department> fetchAllDept(){
        return departmentService.fetchAllDept();
    }

    // fetch department by deptId
    @GetMapping("/departments/{deptId}")
    public Department fetchDeptById(@PathVariable Integer deptId) {
        return departmentService.fetchByDeptId(deptId);
    }

    @PostMapping("/departments")
    public ApiResponse addDepartment(@RequestBody Department theDept){

        Department saved = departmentService.addDepartment(theDept);

        return new ApiResponse("Department added successfully", saved);
    }

    // update department (full update)
    @PutMapping("/departments/{deptId}")
    public ApiResponse updateDepartment(@PathVariable Integer deptId, @RequestBody Department theDept){

        Department saved = departmentService.updateDepartment(deptId, theDept);

        return new ApiResponse(saved.getDeptName()+"'s" + " details has been updated successfully!!!", saved);
    }

    // update department (partial update)
    @PatchMapping("/departments/{deptId}")
    public ApiResponse partialUpdateDepartment(@PathVariable Integer deptId,
                                             @RequestBody Map<String, Object> patchPayload){

        Department saved = departmentService.partialUpdateDepartment(deptId, patchPayload);

        return new ApiResponse(saved.getDeptName()+"'s" + " details has been updated successfully!!!", saved);

    }

    // delete department
    @DeleteMapping("/departments/{deptId}")
    public ApiResponse deleteDepartment(@PathVariable Integer deptId){
        return departmentService.deleteDepartment(deptId);
    }
}
