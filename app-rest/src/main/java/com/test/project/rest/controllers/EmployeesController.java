package com.test.project.rest.controllers;

import com.test.project.core.Department;
import com.test.project.core.Employee;
import com.test.project.service.api.DepartmentService;
import com.test.project.service.api.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class EmployeesController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired DepartmentService departmentService;

    @RequestMapping(value = "/employee/delete", method = RequestMethod.POST)
    public String deleteEmployee(@RequestParam(value = "employeeToDeleteId") Long employeeId) {
        employeeService.deleteEmployee(employeeId);

        return "redirect:/employeeList";
    }

    @RequestMapping(value = "/employee/{id}", method = RequestMethod.GET)
    public ModelAndView employee(@PathVariable Long id) {

        Employee employee = employeeService.getEmployee(id);
        Department department = departmentService.getDepartment(employee.getDepartmentId());
        String departmentName = department.getName();

        ModelAndView model = new ModelAndView();
        model.setViewName("employee");
        model.addObject("employee", employee);
        model.addObject("departmentName", departmentName);

        return model;
    }

    @RequestMapping(value = "/employee/create", method = RequestMethod.GET)
    public ModelAndView createEmployee() {

        ModelAndView model = new ModelAndView();
        model.setViewName("createEmployee");

        return model;
    }

    @RequestMapping(value = "/employee/create", method = RequestMethod.POST)
    public String processDepartmentCreating(@ModelAttribute("employee") Employee employee){

        employeeService.addEmployee(employee);

        return "redirect:/employeeList";
    }

    @RequestMapping(value = "/employee/edit/{id}", method = RequestMethod.GET)
    public ModelAndView editEmployee(@PathVariable(value = "id") Long employeeId){

        Employee employee = employeeService.getEmployee(employeeId);
        List<Department> allDepartments = departmentService.getAllDepartments();

        List<String> departmentNames = new ArrayList<String>();
        for (Department dep: allDepartments) {
            departmentNames.add(dep.getName());
        }



        Department department = employeeService.getEmployeesDepartment(employeeId);
        String depName = department.getName();

        ModelAndView model = new ModelAndView();

        model.setViewName("editEmployee");
        model.addObject("employee", employee);
        model.addObject("department", department);
        model.addObject("departmentNames", departmentNames);
        model.addObject("depName", depName);

        return model;
    }

    @RequestMapping(value = "/employee/edit", method = RequestMethod.POST)
    public String processEmployeeEditing(
            @RequestParam(value = "id") Long id,
            @RequestParam(value = "firstName") String firstName,
            @RequestParam(value = "lastName") String lastName,
            @RequestParam(value = "dateOfBirth") String date,
            @RequestParam(value = "salary") Double salary,
            @RequestParam(value = "departmentId") Long departmentId){

        Employee employee = null;
        try {
            employee = new Employee(firstName, lastName, new Date(new SimpleDateFormat("yyyy-MM-dd").parse(date).getTime()), salary, departmentId);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        employee.setId(id);

        employeeService.updateEmployee(employee);

        return "redirect:/employeeList";
    }

    @RequestMapping(value = "/employeeList", method = RequestMethod.GET)
    public ModelAndView employees(){

        Map employeeMap = new HashMap();
        List<Employee> employeeList = employeeService.getAllEmployees();
        for (Employee e: employeeList) {
            employeeMap.put(e, employeeService.getEmployeesDepartment(e.getId()));
        }

        ModelAndView model = new ModelAndView();
        model.setViewName("employeeList");
        model.addObject("employeeMap", employeeMap);
        return model;
    }
}
