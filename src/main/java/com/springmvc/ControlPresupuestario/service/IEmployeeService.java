package com.springmvc.ControlPresupuestario.service;

import com.springmvc.ControlPresupuestario.model.Employee;

import java.util.List;

public interface IEmployeeService  {

    public List<Employee> getEmployees();

    public Employee getEmployee(long id);

    public Employee getEmployeeByEmail(String email);

    public Employee saveEmployee(Employee newEmployee) ;

    public void updateEmployee(long id, Employee newEmployee) ;

    public void deleteEmployee(long id) ;
}
