package com.test.project.dao.api;

import com.test.project.core.Employee;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by Arty on 21.02.2016.
 */
public interface EmployeeDao {
    public void setDataSource(DataSource dataSource);

    public Long addEmployee(Employee employee);
    public Employee getEmployee(Long id);
    public void updateEmployee(Employee employee);
    public void deleteEmployee(Long id);
    public List<Employee> getAllEmployees();
}
