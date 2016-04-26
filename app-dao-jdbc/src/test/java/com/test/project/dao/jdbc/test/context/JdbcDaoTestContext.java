package com.test.project.dao.jdbc.test.context;

/**
 * Created by Arty on 09.03.2016.
 */

import com.test.project.dao.api.DepartmentDao;
import com.test.project.dao.api.EmployeeDao;
import com.test.project.dao.jdbc.JdbcDepartmentDao;
import com.test.project.dao.jdbc.JdbcEmployeeDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;

@Configuration
@EnableTransactionManagement
@TransactionConfiguration(defaultRollback = true)
@PropertySource("database.properties")
public class JdbcDaoTestContext {

    @Resource
    private Environment env;

    @Bean(destroyMethod = "shutdown")
    public EmbeddedDatabase dataSource() {
        return new EmbeddedDatabaseBuilder().
                setType(EmbeddedDatabaseType.H2).
                addScript("create-db.sql").
                addScript("insert-data.sql").
                build();
    }

    @Bean
    public DepartmentDao getDepartmentDao() {
        return new JdbcDepartmentDao(dataSource());
    }

    @Bean
    public EmployeeDao getEmployeeDao() {
        return new JdbcEmployeeDao(dataSource());
    }

    @Bean
    public DataSourceTransactionManager getTransactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }
}
