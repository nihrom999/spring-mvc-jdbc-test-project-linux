package com.test.project.dao.api;



import com.test.project.core.Department;
import com.test.project.core.Employee;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by Arty on 21.02.2016.
 */
public interface DepartmentDao {
    public void setDataSource(DataSource dataSource);

    public Long addDepartment(Department department);
    public Department getDepartment(Long id);
    public void updateDepartment(Department department);
    public void deleteDepartment(Long id);
    public List<Department> getAllDepartments();
    public List<Employee> getAllEmployeesFromDepartment(Long departmentId);
}
