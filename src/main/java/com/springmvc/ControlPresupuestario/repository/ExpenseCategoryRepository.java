package com.springmvc.ControlPresupuestario.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.springmvc.ControlPresupuestario.model.ExpenseCategory;
import com.springmvc.ControlPresupuestario.model.Phase;


@Repository
public interface ExpenseCategoryRepository extends JpaRepository<ExpenseCategory, Long> {
	
	  @Query("SELECT ec FROM ExpenseCategory ec  WHERE  ec.fase.id = :PhaseId")
	  public List<ExpenseCategory> findByPhaseId(Long PhaseId);
	  
	  @Query("SELECT ec FROM ExpenseCategory ec  WHERE  ec.fase.id IN :PhasesId")
	  public List<ExpenseCategory> findByListPhaseId(List<Long> PhasesId);

}
