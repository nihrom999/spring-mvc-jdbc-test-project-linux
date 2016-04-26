package com.test.project.dao.jdbc;

import com.test.project.core.Employee;
import com.test.project.dao.api.EmployeeDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class JdbcEmployeeDao implements EmployeeDao {

    private JdbcTemplate jdbcTemplateObject;
    private DataSource dataSource;

    private static final Logger LOGGER = LogManager.getLogger();

    private final String INSERT_SQL = "insert into employee " +
            "(first_name, last_name, date_of_birth, salary, department_id) " +
            "values (?, ?, ?, ?, ?)";
    private final String GET_SQL =
            "select employee_id as id, first_name, last_name, date_of_birth, salary, department_id " +
            "from employee where employee_id = ?";

    private final String DELETE_SQL = "delete from employee where employee_id = ?";
    private final String UPDATE_SQL = "update employee " +
            "set first_name = ?, last_name = ?, date_of_birth = ?, salary = ?, department_id = ? " +
            "where employee_id = ?";

    private final String GET_ALL_SQL =
            "select employee_id as id, first_name, last_name, date_of_birth, salary, department_id from employee";


    public JdbcEmployeeDao(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplateObject = new JdbcTemplate(dataSource);
    }


    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplateObject = new JdbcTemplate(dataSource);
    }


    public Long addEmployee(final Employee employee) {
        LOGGER.info("DAO: Add new Employee");
        KeyHolder keyHolder = new GeneratedKeyHolder();
        this.jdbcTemplateObject.update(
                new PreparedStatementCreator() {
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[] {"employee_id"});
                        ps.setString(1, employee.getFirstName());
                        ps.setString(2, employee.getLastName());
                        ps.setDate(3, employee.getDateOfBirth());
                        if(employee.getSalary() != null)
                            ps.setDouble(4, employee.getSalary());
                        else
                            ps.setNull(4, 0);
                        ps.setLong(5, employee.getDepartmentId());

                        return ps;
                    }
                },
                keyHolder);

        return keyHolder.getKey().longValue();
    }


    public Employee getEmployee(Long id) {
        LOGGER.info("DAO: Get Employee with id = " + id.toString());
        Employee employee = (Employee) this.jdbcTemplateObject.queryForObject(
                GET_SQL, new BeanPropertyRowMapper(Employee.class), id);

        return employee;
    }

    public void updateEmployee(Employee employee) {
        LOGGER.info("DAO: Update Employee with id = " + employee.getId().toString());
        double salary;
        Date dob;

        if(employee.getSalary() != null)
            salary = employee.getSalary();
        else
            salary = 0;

        if(employee.getDateOfBirth() != null)
            dob = employee.getDateOfBirth();
        else
            dob = Date.valueOf("2000-01-01");

        this.jdbcTemplateObject.update(UPDATE_SQL,
                employee.getFirstName(),
                employee.getLastName(),
                dob,
                salary,
                employee.getDepartmentId(),
                employee.getId()
                );
    }


    public void deleteEmployee(Long id) {
        LOGGER.info("DAO: Delete Employee with id = " + id.toString());
        this.jdbcTemplateObject.update(DELETE_SQL, id);
    }


    public List<Employee> getAllEmployees() {
        LOGGER.info("DAO: Get all Employees");
        List<Employee> employeeList = this.jdbcTemplateObject.query(
                GET_ALL_SQL, new BeanPropertyRowMapper(Employee.class));

        return employeeList;
    }
}
