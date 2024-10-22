package com.springmvc.ControlPresupuestario.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springmvc.ControlPresupuestario.model.Income;

public interface IncomeRepository extends JpaRepository<Income, Integer> {
	  
	  @Query("SELECT i FROM Income i WHERE i.proyecto.id = :projectId")
	  public List<Income> findAllIncomesByProjectId(Long projectId);
	  
	  @Query("SELECT i FROM Income i WHERE i.proyecto.id = :projectId and estado='V'")
	  public List<Income> findAllIncomesVigentesByProjectId(Long projectId);
}
