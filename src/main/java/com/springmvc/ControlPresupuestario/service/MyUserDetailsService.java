package com.springmvc.ControlPresupuestario.service;

import com.springmvc.ControlPresupuestario.model.Perfil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.springmvc.ControlPresupuestario.model.UserAdm;
import com.springmvc.ControlPresupuestario.repository.UserAdmRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class MyUserDetailsService implements IMyUserDetailsService {

	/*
    @Autowired
    EmployeeRepository employeeRepository;

    private Employee employee;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        employee = employeeRepository.findByEmail(username);

        return new User(employee.getEmail(), employee.getPassword(), authoritiesToRoles(employee.getPerfil()));
    }
*/
    @Autowired
    UserAdmRepository userAdmRepository;

    private UserAdm user1;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //user1 = userAdmRepository.findByEmail(username);
        user1 = userAdmRepository.findByUsernamee(username);

        //return new User(user1.getEmail(), user1.getPassword(), authoritiesToRoles(user1.getPerfil()));
        return new User(user1.getUsernamee(), user1.getPassword(), authoritiesToRoles(user1.getPerfil()));
    }
    
    public Collection<? extends GrantedAuthority> authoritiesToRoles(Perfil role) {

        return Collections.singleton(new SimpleGrantedAuthority(role.getName()));

    }
    
    @Override
    public UserAdm getUserDetailsService() {
        return user1;
    }
    
/*
    @Override
    public Employee getUserDetailsService() {
        return employee;
    }
    
    */
}
