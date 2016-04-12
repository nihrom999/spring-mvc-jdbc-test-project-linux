package com.test.project.service.api;

import com.test.project.core.Department;
import com.test.project.core.Employee;

import java.util.List;

/**
 * Created by arty on 27.3.16.
 */
public interface EmployeeService {

    public Long addEmployee(Employee employee);
    public Employee getEmployee(Long id);
    public void updateEmployee(Employee employee);
    public void deleteEmployee(Long id);
    public List<Employee> getAllEmployees();
    public Department getEmployeesDepartment(Long employeeId);
}
