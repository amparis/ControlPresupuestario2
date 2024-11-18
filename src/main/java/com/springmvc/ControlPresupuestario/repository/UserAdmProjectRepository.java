package com.springmvc.ControlPresupuestario.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springmvc.ControlPresupuestario.model.UserAdm;
import com.springmvc.ControlPresupuestario.model.UserAdmProject;

public interface UserAdmProjectRepository extends JpaRepository<UserAdmProject, Long > {
	  
	  @Query("SELECT uap FROM UserAdmProject uap  WHERE uap.project.id= :projectId and uap.userAdm.id= :usId")
	  public UserAdmProject findByProyectoIdAndUsuarioId(Long projectId,Long usId);
	  
}
