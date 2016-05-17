package com.test.project.dao.jdbc.context;

import com.test.project.dao.api.DepartmentDao;
import com.test.project.dao.api.EmployeeDao;
import com.test.project.dao.jdbc.JdbcDepartmentDao;
import com.test.project.dao.jdbc.JdbcEmployeeDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * Created by Arty on 02.03.2016.
 */

@Configuration
@ComponentScan(basePackages="com.test.project")
@PropertySource("database.properties")
public class JdbcDaoContext {

    @Resource
    private Environment env;

    @Bean
    public DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("db.driver"));
        dataSource.setUrl(env.getProperty("db.database"));
        dataSource.setUsername(env.getProperty("db.user"));
        dataSource.setPassword(env.getProperty("db.password"));

        return dataSource;
    }

    @Bean
    public DepartmentDao getDepartmentDao() {
        return new JdbcDepartmentDao();
    }

    @Bean
    public EmployeeDao getEmployeeDao() {
        return new JdbcEmployeeDao();
    }

    @Bean
    public JdbcTemplate getJdbcTemplate(){
        return new JdbcTemplate(getDataSource());
    }
}
