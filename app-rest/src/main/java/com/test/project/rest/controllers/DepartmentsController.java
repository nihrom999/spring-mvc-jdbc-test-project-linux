package com.test.project.rest.controllers;

import com.sun.javafx.sg.prism.NGShape;
import com.test.project.core.Department;
import com.test.project.core.Employee;
import com.test.project.service.api.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.instrument.classloading.LoadTimeWeaver;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by arty on 30.3.16.
 */

@Controller
public class DepartmentsController {

    @Autowired
    private DepartmentService departmentService;


    @RequestMapping(value = {"/departmentList","/"}, method = RequestMethod.GET)
    public ModelAndView departments() {

        List<Department> departmentList = departmentService.getAllDepartments();

        ModelAndView model = new ModelAndView();
        model.addObject("departmentList", departmentList);

        model.setViewName("departmentList");
        return model;
    }

    @RequestMapping(value = "/department/delete", method = RequestMethod.POST)
    public String deleteDepartment(@RequestParam(value = "departmentToDeleteId") Long departmentId) {
        departmentService.deleteDepartment(departmentId);

        return "redirect:/departmentList";
    }

    @RequestMapping(value = "/department/{id}", method = RequestMethod.GET)
    public ModelAndView department(@PathVariable Long id) {

        ModelAndView model = new ModelAndView();
        model.setViewName("department");
        model.addObject("employeeList", departmentService.getAllEmployeesFromDepartment(id));
        model.addObject("department", departmentService.getDepartment(id));

        return model;
    }

    @RequestMapping(value = "/department/create", method = RequestMethod.GET)
    public ModelAndView createDepartment() {

        ModelAndView model = new ModelAndView();
        model.setViewName("createDepartment");

        return model;
    }

    @RequestMapping(value = "/department/create", method = RequestMethod.POST)
    public String processDepartmentCreating(@RequestParam(value = "departmentName") String departmentName){

        departmentService.addDepartment(new Department(departmentName));

        return "redirect:/departmentList";
    }

    @RequestMapping(value = "/department/edit/{id}", method = RequestMethod.GET)
    public ModelAndView editDepartment(@PathVariable(value = "id") Long departmentId){

        Department department = departmentService.getDepartment(departmentId);
        List<Employee> employeeList = departmentService.getAllEmployeesFromDepartment(departmentId);


        ModelAndView model = new ModelAndView();
        model.setViewName("editDepartment");
        model.addObject("departmentId", departmentId);
        model.addObject("departmentName", department.getName());
        model.addObject("employeeList", employeeList);

        return model;
    }

    @RequestMapping(value = "/department/edit", method = RequestMethod.POST)
    public String processDepartmentEditing(@RequestParam(value = "departmentId") Long id,@RequestParam(value = "departmentName") String departmentName){
        Department department = departmentService.getDepartment(id);
        department.setName(departmentName);
        departmentService.updateDepartment(department);

        return "redirect:/departmentList";
    }

}
