package com.test.project.service.jdbc.test;

import com.test.project.core.Department;
import com.test.project.core.Employee;
import com.test.project.service.api.DepartmentService;
import com.test.project.service.api.EmployeeService;
import com.test.project.service.jdbc.test.context.JdbcServiceTestContext;
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
@ContextConfiguration(classes = JdbcServiceTestContext.class)
@ComponentScan(basePackages="com.test.project")
@PropertySource("database.properties")
@Transactional
public class JdbcServiceTest {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private EmployeeService employeeService;

    @Resource
    private Environment env;

    private static final Logger LOGGER = LogManager.getLogger();

    @Test
    public void TestGetAllDepartments() {
        LOGGER.info("Test SERVICE: run TestGetAllDepartments");
        int size = departmentService.getAllDepartments().size();
        Assert.assertTrue(Long.parseLong(env.getProperty("db.database.test.size")) == size);
    }

    @Test
    public void TestAddDepartment() {

        LOGGER.info("Test SERVICE: run TestAddDepartment");
        int size = departmentService.getAllDepartments().size();
        long id = departmentService.addDepartment(new Department("test"));
        Assert.assertTrue(size + 1 == departmentService.getAllDepartments().size());
    }

    @Test
    public void TestDeleteDepartment() {

        LOGGER.info("Test SERVICE: run TestDeleteDepartment");
        List<Department> departmentList = departmentService.getAllDepartments();
        int size = departmentList.size();
        Department department = departmentList.get(size - 1);

        departmentService.deleteDepartment(department.getId());

        Assert.assertTrue(size - 1 == departmentService.getAllDepartments().size());
    }

    @Test
    public void TestGetDepartment() {

        LOGGER.info("Test SERVICE: run TestGetDepartment");
        long id = departmentService.addDepartment(new Department("test"));

        Department department = departmentService.getDepartment(id);

        Assert.assertEquals("test", department.getName());
    }

    @Test
    public void TestUpdateDepartment() {

        LOGGER.info("Test SERVICE: run TestUpdateDepartment");
        long id = departmentService.addDepartment(new Department("test"));
        Department department = departmentService.getDepartment(id);
        department.setName("test2");

        departmentService.updateDepartment(department);

        Assert.assertEquals("test2", departmentService.getDepartment(id).getName());
    }

    @Test
    public void TestGetAllEmployeesFromDepartment() {

        LOGGER.info("Test SERVICE: run TestGetAllEmployeesFromDepartment");
        long idDepartment = departmentService.addDepartment(new Department("test"));

        long idEmployee1 = employeeService.addEmployee(
                new Employee.Builder()
                        .firstName("bob")
                        .lastName("borson")
                        .dateOfBirth(new Date(1241))
                        .departmentId(idDepartment)
                        .build()
        );

        long idEmployee2 = employeeService.addEmployee(
                new Employee.Builder()
                        .firstName("rob")
                        .lastName("robson")
                        .dateOfBirth(new Date(1241))
                        .departmentId(idDepartment)
                        .build()
        );

        Assert.assertEquals(2, departmentService.getAllEmployeesFromDepartment(idDepartment).size());
    }

    @Test
    public void TestGetAllEmployees() {

        LOGGER.info("Test SERVICE: run TestGetAllEmployees");
        int size = employeeService.getAllEmployees().size();
        int realSize = Integer.parseInt(env.getProperty("db.database.test.size"));
        Assert.assertTrue(realSize == size);
    }

    @Test
    public void TestAddEmployee() {

        LOGGER.info("Test SERVICE: run TestAddEmployee");
        int size = employeeService.getAllEmployees().size();
        long id = employeeService.addEmployee(
                new Employee.Builder()
                        .firstName("bob")
                        .lastName("borson")
                        .dateOfBirth(new Date(1241))
                        .departmentId(1L)
                        .build()
        );

        Assert.assertEquals(size + 1, employeeService.getAllEmployees().size());
    }

    @Test
    public void TestDeleteEmployee() {

        LOGGER.info("Test SERVICE: run TestDeleteEmployee");
        List<Employee> employeeList = employeeService.getAllEmployees();
        int size = employeeList.size();
        Employee employee = employeeList.get(size - 1);

        employeeService.deleteEmployee(employee.getId());

        Assert.assertTrue(size - 1 == employeeService.getAllEmployees().size());
    }

    @Test
    public void TestGetEmployee() {
        LOGGER.info("Test SERVICE: run TestGetEmployee");
        long id = employeeService.addEmployee(
                new Employee.Builder()
                        .firstName("bob")
                        .lastName("bobson")
                        .dateOfBirth(new Date(1241))
                        .departmentId(1L)
                        .build()
        );

        Employee employee = employeeService.getEmployee(id);

        Assert.assertEquals("bob", employee.getFirstName());
    }

    @Test
    public void TestUpdateEmployee() {
        LOGGER.info("Test SERVICE: run TestUpdateEmployee");
        long id = employeeService.addEmployee(
                new Employee.Builder()
                        .firstName("bob")
                        .lastName("bobson")
                        .dateOfBirth(new Date(1241))
                        .departmentId(1L)
                        .build()
        );
        Employee employee = employeeService.getEmployee(id);
        employee.setFirstName("Rob");
        employeeService.updateEmployee(employee);

        Assert.assertEquals("Rob", employeeService.getEmployee(id).getFirstName());
    }

    @Test
    public void TestGetEmployeeDepartment(){
        LOGGER.info("Test SERVICE: run TestGetEmployeesDepartment");
        long idDep = departmentService.addDepartment(new Department("test department"));
        long idEmp = employeeService.addEmployee(
                new Employee.Builder()
                        .firstName("bob")
                        .lastName("bobson")
                        .dateOfBirth(new Date(1241))
                        .departmentId(idDep)
                        .build()
        );

        Assert.assertTrue(idDep == employeeService.getEmployeesDepartment(idEmp).getId());
    }

    @Test
    public void TestGetAverageSalaryInDepartment() {
        LOGGER.info("Test DAO: run TestGetAverageSalaryInDepartment");

        long idDep = departmentService.addDepartment(new Department("avgTestDep"));

        long idEmp1 = employeeService.addEmployee(
                new Employee.Builder()
                        .firstName("bob")
                        .lastName("bobson")
                        .dateOfBirth(Date.valueOf("2000-01-01"))
                        .departmentId(idDep)
                        .salary(100.0)
                        .build()
        );

        long idEmp2 = employeeService.addEmployee(
                new Employee.Builder()
                        .firstName("bob")
                        .lastName("borson")
                        .dateOfBirth(Date.valueOf("2000-01-01"))
                        .departmentId(idDep)
                        .salary(200.0)
                        .build()
        );

        long idEmp3 = employeeService.addEmployee(
                new Employee.Builder()
                        .firstName("bob")
                        .lastName("bodson")
                        .dateOfBirth(Date.valueOf("2000-01-01"))
                        .departmentId(idDep)
                        .salary(300.0)
                        .build()
        );

        Assert.assertTrue(departmentService.getAverageSalaryInDepartment(idDep).equals(200.0));
    }

    @Test
    public void TestEmployeesWithDateOfBirthInInterval() {
        LOGGER.info("Test DAO: run TestEmployeesWithDateOfBirthInInterval");

        long idDep = departmentService.addDepartment(new Department("testDep"));

        long idEmp1 = employeeService.addEmployee(
                new Employee.Builder()
                        .firstName("bob")
                        .lastName("bobson")
                        .dateOfBirth(Date.valueOf("2000-01-01"))
                        .departmentId(idDep)
                        .build()
        );

        long idEmp2 = employeeService.addEmployee(
                new Employee.Builder()
                        .firstName("bob")
                        .lastName("borson")
                        .dateOfBirth(Date.valueOf("2000-01-05"))
                        .departmentId(idDep)
                        .build()
        );

        long idEmp3 = employeeService.addEmployee(
                new Employee.Builder()
                        .firstName("bob")
                        .lastName("bodson")
                        .dateOfBirth(Date.valueOf("2000-01-06"))
                        .departmentId(idDep)
                        .build()
        );

        Assert.assertTrue(employeeService.getEmployeesWithDateOfBirth(Date.valueOf("2000-01-04"), Date.valueOf("2000-01-07")).size() == 2);
    }

    @Test
    public void TestEmployeesWithDateOfBirth() {
        LOGGER.info("Test DAO: run TestEmployeesWithDateOfBirth");

        long idDep = departmentService.addDepartment(new Department("testDep"));

        long idEmp1 = employeeService.addEmployee(
                new Employee.Builder()
                        .firstName("bob")
                        .lastName("bodson")
                        .dateOfBirth(Date.valueOf("2000-01-06"))
                        .departmentId(idDep)
                        .build()
        );

        Assert.assertTrue(employeeService.getEmployeesWithDateOfBirth(Date.valueOf("2000-01-06")).size() == 1);
    }
}
