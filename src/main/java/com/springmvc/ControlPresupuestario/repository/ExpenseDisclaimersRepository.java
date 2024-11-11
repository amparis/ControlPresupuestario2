package com.springmvc.ControlPresupuestario.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.springmvc.ControlPresupuestario.model.ExpenseDisclaimers;
import com.springmvc.ControlPresupuestario.model.Phase;

@Repository
public interface ExpenseDisclaimersRepository extends JpaRepository<ExpenseDisclaimers,Long>{

	  @Query("SELECT ed FROM ExpenseDisclaimers ed  WHERE  ed.egreso.id = :expenseId")
	  public List<ExpenseDisclaimers> findExpenseDisclaimersByExpenseId(long expenseId);
	  
	  @Query("SELECT ed FROM ExpenseDisclaimers ed  WHERE  ed.egreso.proyectoFase.proyecto.id = :projectId")
	  public List<ExpenseDisclaimers> findExpenseDisclaimersByProjectId(Long projectId);
	  
	  @Query("SELECT ed FROM ExpenseDisclaimers ed  WHERE  ed.egreso.proyectoFase.proyecto.id = :projectId and ed.egreso.beneficiario.id = :beneficiaryId")
	  public List<ExpenseDisclaimers> findExpenseDisclaimersByProjectIdAndBeneficiaryId(Long projectId, Integer beneficiaryId);
	  
}
