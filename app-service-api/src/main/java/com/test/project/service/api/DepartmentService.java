package com.test.project.service.api;

import com.test.project.core.Department;
import com.test.project.core.Employee;
import com.test.project.dao.api.DepartmentDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by arty on 27.3.16.
 */
public interface DepartmentService {

    Long addDepartment(Department department);
    Department getDepartment(Long id);
    void updateDepartment(Department department);
    void deleteDepartment(Long id);
    List<Department> getAllDepartments();
    List<Employee> getAllEmployeesFromDepartment(Long departmentId);
}
