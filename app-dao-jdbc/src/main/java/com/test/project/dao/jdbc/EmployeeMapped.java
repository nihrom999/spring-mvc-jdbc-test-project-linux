package com.test.project.dao.jdbc;

import com.test.project.core.Employee;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public final class EmployeeMapped implements RowMapper<Employee> {

    public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
        Employee employee = new Employee();
        employee.setId(rs.getLong("employee_id"));
        employee.setFirstName(rs.getString("first_name"));
        employee.setLastName(rs.getString("last_name"));
        employee.setDatoOfBirth(rs.getDate("date_of_birth"));
        employee.setSalary(rs.getDouble("salary"));
        employee.setDepartmentId(rs.getLong("department_id"));

        return employee;
    }
}