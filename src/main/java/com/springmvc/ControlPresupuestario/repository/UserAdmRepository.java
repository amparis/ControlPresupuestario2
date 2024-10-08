package com.springmvc.ControlPresupuestario.repository;

import com.springmvc.ControlPresupuestario.model.UserAdm;

import java.sql.Date;

import org.springframework.data.jpa.repository.JpaRepository;


public interface UserAdmRepository extends JpaRepository<UserAdm,Long>{
	
    //public UserAdm findByEmail(String email);
    public UserAdm findByUsernamee(String usernamee);
	boolean existsByUsernamee(String usernamee);//Metodo para buscar por nombre


}
