package com.springmvc.ControlPresupuestario.repository;

import com.springmvc.ControlPresupuestario.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {

    public Employee findByEmail(String email);


}
