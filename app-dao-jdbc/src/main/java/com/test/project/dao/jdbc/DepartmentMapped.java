package com.test.project.dao.jdbc;

import com.test.project.core.Department;
import com.test.project.core.Employee;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class DepartmentMapped implements RowMapper<Department> {

    public Department mapRow(ResultSet rs, int rowNum) throws SQLException {
        Department department = new Department();
        department.setId(rs.getLong("department_id"));
        department.setName(rs.getString("name"));
        return department;
    }
}

