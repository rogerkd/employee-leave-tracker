package com.deepak.leavetracker.leave_tracker.service;

import com.deepak.leavetracker.leave_tracker.dto.response.ApiResponse;
import com.deepak.leavetracker.leave_tracker.entity.Department;

import java.util.List;
import java.util.Map;

public interface DepartmentService {

    List<Department> fetchAllDept();

    Department fetchByDeptId(Integer deptId);

    Department addDepartment(Department theDept);

    Department updateDepartment(Integer deptId, Department theDept);

    Department partialUpdateDepartment(Integer deptId, Map<String, Object> patchPayload);

    ApiResponse deleteDepartment(Integer deptId);

}
