package com.springmvc.ControlPresupuestario.controller;


import com.springmvc.ControlPresupuestario.model.Employee;
import com.springmvc.ControlPresupuestario.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;


    @PostMapping("")
    public RedirectView postEmployee(@ModelAttribute Employee newEmployee) {

        this.employeeService.saveEmployee(newEmployee);

        return new RedirectView("/lista-empleados");
    }

}
