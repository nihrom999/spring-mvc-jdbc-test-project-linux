package com.test.project.dao.jdbc;

import com.test.project.core.Department;
import com.test.project.core.Employee;
import com.test.project.dao.api.DepartmentDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class JdbcDepartmentDao implements DepartmentDao {

    @Autowired
    private JdbcTemplate jdbcTemplateObject;

    private static final Logger LOGGER = LogManager.getLogger();

    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('/sql/department_get_all.sql')).inputStream)}")
    private String GET_ALL_SQL;

    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('/sql/department_get.sql')).inputStream)}")
    private String GET_SQL;

    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('/sql/department_insert.sql')).inputStream)}")
    private String INSERT_SQL;

    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('/sql/department_update.sql')).inputStream)}")
    private String UPDATE_SQL;

    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('/sql/department_delete.sql')).inputStream)}")
    private String DELETE_SQL;

    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('/sql/department_employee_list.sql')).inputStream)}")
    private String EMPLOYEE_SQL;

    public Long addDepartment(final Department department) {
        LOGGER.info("DAO: Add new Department");
        KeyHolder keyHolder = new GeneratedKeyHolder();
        this.jdbcTemplateObject.update(
                new PreparedStatementCreator() {
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[] {"department_id"});
                        ps.setString(1, department.getName());
                        return ps;
                    }
                },
                keyHolder);

        return keyHolder.getKey().longValue();
    }


    public Department getDepartment(Long id) {
        LOGGER.info("DAO: Get Department with id = " + id.toString());
        Department department = (Department)this.jdbcTemplateObject.queryForObject(
                GET_SQL, new BeanPropertyRowMapper(Department.class), id);

        return department;
    }

    public void updateDepartment(Department department) {
        LOGGER.info("DAO: Update Department with id = " + department.getId().toString());
        this.jdbcTemplateObject.update(UPDATE_SQL, department.getName(), department.getId());
    }

    /*
    * This method will delete the department and all it employees
    * due to cascade-delete in database
    * */
    public void deleteDepartment(Long id) {
        LOGGER.info("DAO: Delete Department with id = " + id.toString());
        this.jdbcTemplateObject.update(DELETE_SQL, id);
    }


    public List<Department> getAllDepartments() {
        LOGGER.info("DAO: Get all Departments");
        List<Department> departments = this.jdbcTemplateObject.query(
                GET_ALL_SQL, new BeanPropertyRowMapper(Department.class));

        return departments;
    }


    public List<Employee> getAllEmployeesFromDepartment(Long departmentId) {
        LOGGER.info("DAO: Get all Employees from Department with id = " + departmentId.toString());
        List<Employee> employees = this.jdbcTemplateObject.query(
                EMPLOYEE_SQL, new BeanPropertyRowMapper(Employee.class), departmentId);

        return employees;
    }

}
