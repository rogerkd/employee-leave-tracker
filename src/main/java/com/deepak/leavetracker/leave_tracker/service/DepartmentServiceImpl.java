package com.deepak.leavetracker.leave_tracker.service;

import com.deepak.leavetracker.leave_tracker.dto.ApiResponse;
import com.deepak.leavetracker.leave_tracker.entity.Department;
import com.deepak.leavetracker.leave_tracker.repository.DepartmentRepository;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

@Service
public class DepartmentServiceImpl implements DepartmentService{

    private final DepartmentRepository departmentRepository;
    private final ObjectMapper objectMapper;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository,
                                 ObjectMapper objectMapper){
        this.departmentRepository = departmentRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public List<Department> fetchAllDept(){
        return departmentRepository.findAll();
    }

    @Override
    public Department fetchByDeptId(Integer deptId){
        return departmentRepository.findById(deptId)
                .orElseThrow(() -> new RuntimeException("Department with Id : "+deptId+" doesn't exist."));
    }

    @Override
    public Department addDepartment(Department theDept){
        theDept.setDeptId(null); // force insert

        return departmentRepository.save(theDept);
    }

    @Override
    public Department updateDepartment(Integer deptId, Department theDept){
        Department existingDept = fetchByDeptId(deptId);
        if(existingDept == null){
            throw new RuntimeException("Department not found - " + deptId);
        }

        // if deptId is null, then explicitly set it
        theDept.setDeptId(deptId);

        return departmentRepository.save(theDept);
    }

    @Override
    public Department partialUpdateDepartment(Integer deptId, Map<String, Object> patchPayload){

        Department theDept = fetchByDeptId(deptId);
        if(theDept == null){
            throw new RuntimeException("Department not found - " + deptId);
        }

        // throw exception if request body contains deptId (Primary key can't be changed)
        if(patchPayload.containsKey("deptId")) {
            throw new RuntimeException("Department Id not allowed in request body - " + deptId);
        }

        // apply patch on the given department
        Department patchedDepartment = objectMapper.updateValue(theDept, patchPayload);

        return departmentRepository.save(patchedDepartment);
    }

    @Override
    public ApiResponse deleteDepartment(Integer deptId){
        Department theDept = fetchByDeptId(deptId);
        if(theDept == null){
            throw new RuntimeException("Department not found - " + deptId);
        }

        departmentRepository.deleteById(deptId);

        return new ApiResponse("Department details removed", theDept);
    }
}
