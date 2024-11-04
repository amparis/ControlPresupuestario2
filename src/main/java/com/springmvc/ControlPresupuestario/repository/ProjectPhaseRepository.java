package com.springmvc.ControlPresupuestario.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.springmvc.ControlPresupuestario.model.ProjectPhase;

@Repository
public interface ProjectPhaseRepository  extends JpaRepository<ProjectPhase, Long> {

	  @Query("SELECT fp FROM ProjectPhase fp WHERE fp.proyecto.id = :projectId ORDER BY fp.fase.id ASC")
	  public List<ProjectPhase> findAllByProyectoId(long projectId);

}
