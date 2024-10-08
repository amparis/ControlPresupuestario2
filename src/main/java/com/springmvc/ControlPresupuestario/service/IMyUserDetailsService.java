package com.springmvc.ControlPresupuestario.service;

//import com.springmvc.contabilidad.model.Employee;
import com.springmvc.ControlPresupuestario.model.UserAdm;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IMyUserDetailsService extends UserDetailsService {

    //public Employee getUserDetailsService();
	public UserAdm getUserDetailsService();
}
