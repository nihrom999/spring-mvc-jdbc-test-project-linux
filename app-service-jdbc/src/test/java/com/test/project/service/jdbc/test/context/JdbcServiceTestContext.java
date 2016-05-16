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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@TransactionConfiguration(defaultRollback = true)
@PropertySource("database.properties")
public class JdbcServiceTestContext {

    @Bean(destroyMethod = "shutdown")
    public EmbeddedDatabase dataSource() {
        return new EmbeddedDatabaseBuilder().
                setType(EmbeddedDatabaseType.H2).
                addScript("create-db.sql").
                addScript("insert-data.sql").
                build();
    }

    @Bean
    public EmployeeService getEmployeeService(){
        return new JdbcEmployeeService();
    }

    @Bean
    public DepartmentService getDepartmentService(){
        return new JdbcDepartmentService();
    }

    @Bean
    public DataSourceTransactionManager getTransactionManager() {
        return new DataSourceTransactionManager(dataSource());
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
        return new JdbcTemplate(dataSource());
    }
}
