package com.test.project.service.api;

import com.test.project.core.Department;
import com.test.project.core.Employee;

import java.util.List;

/**
 * Created by arty on 27.3.16.
 */
public interface EmployeeService {

    Long addEmployee(Employee employee);
    Employee getEmployee(Long id);
    void updateEmployee(Employee employee);
    void deleteEmployee(Long id);
    List<Employee> getAllEmployees();
    Department getEmployeesDepartment(Long employeeId);
}
