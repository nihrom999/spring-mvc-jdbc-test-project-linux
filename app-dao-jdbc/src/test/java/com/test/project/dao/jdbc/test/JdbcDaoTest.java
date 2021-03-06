package com.test.project.dao.jdbc.test;

import com.test.project.core.Department;
import com.test.project.core.Employee;
import com.test.project.dao.api.DepartmentDao;
import com.test.project.dao.api.EmployeeDao;
import com.test.project.dao.jdbc.test.context.JdbcDaoTestContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Date;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = JdbcDaoTestContext.class)
@ComponentScan(basePackages = "com.test.project")
@PropertySource("database.properties")
@Transactional
public class JdbcDaoTest {

    @Autowired
    private DepartmentDao departmentDao;

    @Autowired
    private EmployeeDao employeeDao;

    @Resource
    private Environment env;

    private static final Logger LOGGER = LogManager.getLogger();

    @Test
    public void TestGetAllDepartments() {
        LOGGER.info("Test DAO: run TestGetAllDepartments");
        int size = departmentDao.getAllDepartments().size();
        Assert.assertTrue(Long.parseLong(env.getProperty("db.database.test.size")) == size);
    }

    @Test
    public void TestAddDepartment() {
        LOGGER.info("Test DAO: run TestAddDepartment");
        int size = departmentDao.getAllDepartments().size();
        long id = departmentDao.addDepartment(new Department("test"));
        Assert.assertTrue(size + 1 == departmentDao.getAllDepartments().size());
    }

    @Test
    public void TestDeleteDepartment() {
        LOGGER.info("Test DAO: run TestDeleteDepartment");
        List<Department> departmentList = departmentDao.getAllDepartments();
        int size = departmentList.size();
        Department department = departmentList.get(size - 1);

        departmentDao.deleteDepartment(department.getId());

        Assert.assertTrue(size - 1 == departmentDao.getAllDepartments().size());
    }

    @Test
    public void TestGetDepartment() {
        LOGGER.info("Test DAO: run TestGetDepartment");
        long id = departmentDao.addDepartment(new Department("test"));

        Department department = departmentDao.getDepartment(id);

        Assert.assertEquals("test", department.getName());
    }

    @Test
    public void TestUpdateDepartment() {
        LOGGER.info("Test DAO: run TestUpdateDepartment");
        long id = departmentDao.addDepartment(new Department("test"));
        Department department = departmentDao.getDepartment(id);
        department.setName("test2");

        departmentDao.updateDepartment(department);

        Assert.assertEquals("test2", departmentDao.getDepartment(id).getName());
    }

    @Test
    public void TestGetAllEmployeesFromDepartment() {
        LOGGER.info("Test DAO: run TestGetAllEmployeesFromDepartment");
        long idDepartment = departmentDao.addDepartment(new Department("test"));
        long idEmployee1 = employeeDao.addEmployee(
                new Employee.Builder()
                        .firstName("bob")
                        .lastName("borson")
                        .dateOfBirth(new Date(1241))
                        .departmentId(idDepartment)
                        .build()
        );

        long idEmployee2 = employeeDao.addEmployee(
                new Employee.Builder()
                        .firstName("rob")
                        .lastName("robson")
                        .dateOfBirth(new Date(1241))
                        .departmentId(idDepartment)
                        .build()
        );

        Assert.assertEquals(2, departmentDao.getAllEmployeesFromDepartment(idDepartment).size());
    }

    @Test
    public void TestGetAllEmployees() {
        LOGGER.info("Test DAO: run TestGetAllEmployees");
        int size = employeeDao.getAllEmployees().size();

        Assert.assertTrue(Long.parseLong(env.getProperty("db.database.test.size")) == size);
    }

    @Test
    public void TestAddEmployee() {
        LOGGER.info("Test DAO: run TestAddEmployee");
        int size = employeeDao.getAllEmployees().size();
        long id = employeeDao.addEmployee(
                new Employee.Builder()
                        .firstName("bob")
                        .lastName("borson")
                        .dateOfBirth(new Date(1241))
                        .departmentId(1L)
                        .build()
        );

        Assert.assertEquals(size + 1, employeeDao.getAllEmployees().size());
    }

    @Test
    public void TestDeleteEmployee() {
        LOGGER.info("Test DAO: run TestDeleteEmployee");
        List<Employee> employeeList = employeeDao.getAllEmployees();
        int size = employeeList.size();
        Employee employee = employeeList.get(size - 1);

        employeeDao.deleteEmployee(employee.getId());

        Assert.assertTrue(size - 1 == employeeDao.getAllEmployees().size());
    }

    @Test
    public void TestGetEmployee() {
        LOGGER.info("Test DAO: run TestGetEmployee");
        long id = employeeDao.addEmployee(
                new Employee.Builder()
                        .firstName("bob")
                        .lastName("bobson")
                        .dateOfBirth(new Date(1241))
                        .departmentId(1L)
                        .build()
        );

        Employee employee = employeeDao.getEmployee(id);

        Assert.assertEquals("bob", employee.getFirstName());
    }

    @Test
    public void TestUpdateEmployee() {
        LOGGER.info("Test DAO: run TestUpdateEmployee");
        long id = employeeDao.addEmployee(
                new Employee.Builder()
                        .firstName("bob")
                        .lastName("bobson")
                        .dateOfBirth(Date.valueOf("2000-01-01"))
                        .departmentId(1L)
                        .build()
        );

        Employee employee = employeeDao.getEmployee(id);
        employee.setFirstName("Rob");
        employeeDao.updateEmployee(employee);

        Assert.assertEquals("Rob", employeeDao.getEmployee(id).getFirstName());
    }

    @Test
    public void TestGetAverageSalaryInDepartment() {
        LOGGER.info("Test DAO: run TestGetAverageSalaryInDepartment");

        long idDep = departmentDao.addDepartment(new Department("avgTestDep"));

        long idEmp1 = employeeDao.addEmployee(
                new Employee.Builder()
                        .firstName("bob")
                        .lastName("bobson")
                        .dateOfBirth(Date.valueOf("2000-01-01"))
                        .departmentId(idDep)
                        .salary(100.0)
                        .build()
        );

        long idEmp2 = employeeDao.addEmployee(
                new Employee.Builder()
                        .firstName("bob")
                        .lastName("borson")
                        .dateOfBirth(Date.valueOf("2000-01-01"))
                        .departmentId(idDep)
                        .salary(200.0)
                        .build()
        );

        long idEmp3 = employeeDao.addEmployee(
                new Employee.Builder()
                        .firstName("bob")
                        .lastName("bodson")
                        .dateOfBirth(Date.valueOf("2000-01-01"))
                        .departmentId(idDep)
                        .salary(300.0)
                        .build()
        );

        Assert.assertTrue(departmentDao.getAverageSalaryInDepartment(idDep).equals(200.0));
    }

    @Test
    public void TestEmployeesWithDateOfBirthInInterval() {
        LOGGER.info("Test DAO: run TestEmployeesWithDateOfBirthInInterval");

        long idDep = departmentDao.addDepartment(new Department("testDep"));

        long idEmp1 = employeeDao.addEmployee(
                new Employee.Builder()
                        .firstName("bob")
                        .lastName("bobson")
                        .dateOfBirth(Date.valueOf("2000-01-01"))
                        .departmentId(idDep)
                        .build()
        );

        long idEmp2 = employeeDao.addEmployee(
                new Employee.Builder()
                        .firstName("bob")
                        .lastName("borson")
                        .dateOfBirth(Date.valueOf("2000-01-05"))
                        .departmentId(idDep)
                        .build()
        );

        long idEmp3 = employeeDao.addEmployee(
                new Employee.Builder()
                        .firstName("bob")
                        .lastName("bodson")
                        .dateOfBirth(Date.valueOf("2000-01-06"))
                        .departmentId(idDep)
                        .build()
        );

        Assert.assertTrue(employeeDao.getEmployeesWithDateOfBirth(Date.valueOf("2000-01-04"), Date.valueOf("2000-01-07")).size() == 2);
    }

    @Test
    public void TestEmployeesWithDateOfBirth() {
        LOGGER.info("Test DAO: run TestEmployeesWithDateOfBirth");

        long idDep = departmentDao.addDepartment(new Department("testDep"));

        long idEmp1 = employeeDao.addEmployee(
                new Employee.Builder()
                        .firstName("bob")
                        .lastName("bodson")
                        .dateOfBirth(Date.valueOf("2000-01-06"))
                        .departmentId(idDep)
                        .build()
        );

        Assert.assertTrue(employeeDao.getEmployeesWithDateOfBirth(Date.valueOf("2000-01-06")).size() == 1);
    }
}
