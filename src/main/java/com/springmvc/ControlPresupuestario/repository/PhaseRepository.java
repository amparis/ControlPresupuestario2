package com.springmvc.ControlPresupuestario.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.springmvc.ControlPresupuestario.model.Phase;

@Repository
public interface PhaseRepository extends JpaRepository<Phase, Long> {
	  @Query("SELECT p FROM Phase p ORDER BY p.id")
	  public List<Phase> findAll();
	
	  @Query("SELECT p FROM Phase p  WHERE  p.estado = :estado")
	  public List<Phase> findAllByEstado(String estado);	
	  
	  @Query("SELECT p FROM Phase p  WHERE  p.nombre = :nombre")
	  public Phase findPhaseByNombre(String nombre);	
}
