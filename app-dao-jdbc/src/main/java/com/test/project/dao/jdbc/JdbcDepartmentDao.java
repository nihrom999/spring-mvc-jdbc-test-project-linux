package com.test.project.dao.jdbc;

import com.test.project.core.Department;
import com.test.project.core.Employee;
import com.test.project.dao.api.DepartmentDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private JdbcTemplate jdbcTemplateObject;
    private DataSource dataSource;

    private static final Logger LOGGER = LogManager.getLogger();

    private final String GET_ALL_SQL = "select department_id as id, name from department";
    private final String GET_SQL = "select department_id as id, name from department where department_id = ?";
    private final String INSERT_SQL = "insert into department (name) values (?)";
    private final String UPDATE_SQL = "update department set name = ? where department_id = ?";
    private final String DELETE_SQL = "delete from department where department_id = ?";
    private final String EMPLOYEE_SQL = "select employee_id as id, first_name, last_name, salary, date_of_birth from employee where department_id = ?";

    public JdbcDepartmentDao(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplateObject = new JdbcTemplate(dataSource);
    }


    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplateObject = new JdbcTemplate(dataSource);
    }


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
