package com.test.project.service.jdbc.test.context;

import com.test.project.dao.api.DepartmentDao;
import com.test.project.dao.api.EmployeeDao;
import com.test.project.dao.jdbc.JdbcDepartmentDao;
import com.test.project.dao.jdbc.JdbcEmployeeDao;
import com.test.project.service.api.DepartmentService;
import com.test.project.service.api.EmployeeService;
import com.test.project.service.jdbc.JdbcDepartmentService;
import com.test.project.service.jdbc.JdbcEmployeeService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * Created by arty on 27.3.16.
 */

@Configuration
@EnableTransactionManagement
@TransactionConfiguration(defaultRollback = true)
@ComponentScan(basePackages="com.test.project")
@PropertySource("database.properties")
public class JdbcServiceTestContext {

    @Resource
    private Environment env;

    @Bean
    public DataSource getTestDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("db.driver"));
        dataSource.setUrl(env.getProperty("db.database.test"));
        dataSource.setUsername(env.getProperty("db.user"));
        dataSource.setPassword(env.getProperty("db.password"));

        return dataSource;
    }

    @Bean
    public EmployeeService getEmployeeService(){
        return new JdbcEmployeeService(getEmployeeDao());
    }

    @Bean
    public DepartmentService getDepartmentService(){
        return new JdbcDepartmentService(getDepartmentDao());
    }

    @Bean
    public DataSourceTransactionManager getTransactionManager() {
        return new DataSourceTransactionManager(getTestDataSource());
    }

    @Bean
    public DepartmentDao getDepartmentDao() {
        return new JdbcDepartmentDao(getTestDataSource());
    }

    @Bean
    public EmployeeDao getEmployeeDao() {
        return new JdbcEmployeeDao(getTestDataSource());
    }

}
