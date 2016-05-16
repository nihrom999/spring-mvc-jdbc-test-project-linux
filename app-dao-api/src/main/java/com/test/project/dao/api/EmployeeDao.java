package com.test.project.dao.api;

import com.test.project.core.Employee;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by Arty on 21.02.2016.
 */
public interface EmployeeDao {
    Long addEmployee(Employee employee);
    Employee getEmployee(Long id);
    void updateEmployee(Employee employee);
    void deleteEmployee(Long id);
    List<Employee> getAllEmployees();
}
