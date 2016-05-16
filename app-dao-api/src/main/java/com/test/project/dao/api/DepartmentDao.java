package com.test.project.dao.api;



import com.test.project.core.Department;
import com.test.project.core.Employee;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by Arty on 21.02.2016.
 */
public interface DepartmentDao {
    Long addDepartment(Department department);
    Department getDepartment(Long id);
    void updateDepartment(Department department);
    void deleteDepartment(Long id);
    List<Department> getAllDepartments();
    List<Employee> getAllEmployeesFromDepartment(Long departmentId);
}
