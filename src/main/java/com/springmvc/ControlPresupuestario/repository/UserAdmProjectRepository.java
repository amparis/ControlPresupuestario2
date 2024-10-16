package com.springmvc.ControlPresupuestario.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springmvc.ControlPresupuestario.model.UserAdm;
import com.springmvc.ControlPresupuestario.model.UserAdmProject;

public interface UserAdmProjectRepository extends JpaRepository<UserAdmProject, Long > {
	 //@Query("SELECT rm FROM RolMenu rm JOIN FETCH rm.menu m WHERE rm.id.roleId = :roleId order by m.orden")

	  
}
