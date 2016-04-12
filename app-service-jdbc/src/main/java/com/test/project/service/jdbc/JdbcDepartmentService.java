package com.test.project.service.jdbc;

import com.test.project.dao.api.DepartmentDao;
import com.test.project.dao.jdbc.JdbcDepartmentDao;
import com.test.project.service.api.DepartmentService;
import com.test.project.core.Department;
import com.test.project.core.Employee;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import javax.sql.DataSource;

/**
 * Created by arty on 27.3.16.
 */


@Transactional
public class JdbcDepartmentService implements DepartmentService{

    private DepartmentDao departmentDao;

    public JdbcDepartmentService(DepartmentDao departmentDao) {
        this.departmentDao = departmentDao;
    }

    private static final Logger LOGGER = LogManager.getLogger();

    public Long addDepartment(Department department) {
        LOGGER.info("SERVICE: Add new Department");
        return departmentDao.addDepartment(department);
    }

    public Department getDepartment(Long id) {
        LOGGER.info("SERVICE: Get Department with id = " + id);
        return departmentDao.getDepartment(id);
    }

    public void deleteDepartment(Long id) {
        LOGGER.info("SERVICE: Delete Department with id = " + id);
        departmentDao.deleteDepartment(id);
    }

    public List<Department> getAllDepartments() {
        LOGGER.info("SERVICE: Get all Departments");
        return departmentDao.getAllDepartments();
    }

    public List<Employee> getAllEmployeesFromDepartment(Long departmentId) {
        LOGGER.info("SERVICE: Get all Employees from Department with id = " + departmentId);
        return departmentDao.getAllEmployeesFromDepartment(departmentId);
    }

    public void updateDepartment(Department department) {
        LOGGER.info("SERVICE: Update Department with id = " + department.getId());
        departmentDao.updateDepartment(department);
    }
}
