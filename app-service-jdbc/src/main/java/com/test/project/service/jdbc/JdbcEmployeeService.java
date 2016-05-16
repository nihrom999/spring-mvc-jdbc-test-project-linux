package com.test.project.service.jdbc;

import com.test.project.core.Department;
import com.test.project.core.Employee;
import com.test.project.dao.api.DepartmentDao;
import com.test.project.dao.api.EmployeeDao;
import com.test.project.service.api.EmployeeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by arty on 27.3.16.
 */

@Transactional
public class JdbcEmployeeService implements EmployeeService{

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private DepartmentDao departmentDao;

    private static final Logger LOGGER = LogManager.getLogger();

    public Long addEmployee(Employee employee) {
        LOGGER.info("SERVICE: Add new Employee");
        return employeeDao.addEmployee(employee);
    }

    public Employee getEmployee(Long id) {
        LOGGER.info("SERVICE: Get Employee with id = " + id);
        return employeeDao.getEmployee(id);
    }

    public void updateEmployee(Employee employee) {
        LOGGER.info("SERVICE: Update Employee with id = " + employee.getId());
        employeeDao.updateEmployee(employee);
    }

    public void deleteEmployee(Long id) {
        LOGGER.info("SERVICE: Delete Employee with id = " + id);
        employeeDao.deleteEmployee(id);
    }

    public List<Employee> getAllEmployees() {
        LOGGER.info("SERVICE: Get all Employees");
        return employeeDao.getAllEmployees();
    }

    public Department getEmployeesDepartment(Long employeeId) {
        LOGGER.info("SERVICE: Get Department with the Emoloyee with id = " + employeeId);

        Employee employee = employeeDao.getEmployee(employeeId);

        if(employee.getDepartmentId() != null){
            return departmentDao.getDepartment(employee.getDepartmentId());
        }

        return null;
    }
}
