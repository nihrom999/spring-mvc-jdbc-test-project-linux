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

    public Long addDepartment(Department department);
    public Department getDepartment(Long id);
    public void updateDepartment(Department department);
    public void deleteDepartment(Long id);
    public List<Department> getAllDepartments();
    public List<Employee> getAllEmployeesFromDepartment(Long departmentId);
}
