package com.springmvc.ControlPresupuestario.repository;

import com.springmvc.ControlPresupuestario.model.Project;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
	
	boolean existsByNombreAndFechaInicialAndFechaFin(String nombre, Date fechaInicio, Date fechaFin);//Metodo para buscar por nombre, fecha inicla y fecha final
	List<Project> findByEstado(String estado);  // Nuevo método para buscar por estado


}
