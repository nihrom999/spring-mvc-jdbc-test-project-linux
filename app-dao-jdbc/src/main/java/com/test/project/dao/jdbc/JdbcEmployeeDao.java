package com.test.project.dao.jdbc;

import com.test.project.core.Employee;
import com.test.project.dao.api.EmployeeDao;
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
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class JdbcEmployeeDao implements EmployeeDao {

    @Autowired
    private JdbcTemplate jdbcTemplateObject;

    private static final Logger LOGGER = LogManager.getLogger();

    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('/sql/employee_insert.sql')).inputStream)}")
    private String INSERT_SQL;

    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('/sql/employee_get.sql')).inputStream)}")
    private String GET_SQL;

    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('/sql/employee_delete.sql')).inputStream)}")
    private String DELETE_SQL;

    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('/sql/employee_update.sql')).inputStream)}")
    private String UPDATE_SQL;

    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('/sql/employee_get_all.sql')).inputStream)}")
    private String GET_ALL_SQL;

    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('/sql/employee_get_with_specific_dob.sql')).inputStream)}")
    private String GET_WITH_DATE_OF_BIRTH;

    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('/sql/employee_get_with_dob_in_interval.sql')).inputStream)}")
    private String GET_WITH_DATE_OF_BIRTH_IN_PERIOD;

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

    public List<Employee> getEmployeesWithDateOfBirth(Date date){
        LOGGER.info("DAO: Get Employees with date of birth = " + date.toString());
        List<Employee> employeeList = this.jdbcTemplateObject.query(
                GET_WITH_DATE_OF_BIRTH, new BeanPropertyRowMapper(Employee.class),date.toString());

        return employeeList;
    }

    public List<Employee> getEmployeesWithDateOfBirth(Date fromDate, Date toDate){
        LOGGER.info("DAO: Get Employees with date of birth from = " + fromDate.toString() + " to " + toDate.toString());
        List<Employee> employeeList = this.jdbcTemplateObject.query(
                GET_WITH_DATE_OF_BIRTH_IN_PERIOD, new BeanPropertyRowMapper(Employee.class),fromDate.toString(), toDate.toString());

        return employeeList;
    }
}
