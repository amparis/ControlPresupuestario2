package com.springmvc.ControlPresupuestario.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.springmvc.ControlPresupuestario.model.BeneficiaryExpenseSummaryDTO;
import com.springmvc.ControlPresupuestario.model.Expense;
import com.springmvc.ControlPresupuestario.model.Project;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense,Long>  {

	  @Query("SELECT e FROM Expense e WHERE e.proyectoFase.proyecto.id = :projectId")
	  public List<Expense> findAllExpensesByProjectId(Long projectId);
	  
	  @Query("SELECT e FROM Expense e WHERE e.proyectoFase.proyecto.id = :projectId and estado='V'")
	  public List<Expense> findAllExpensesVigentesByProjectId(Long projectId);
	  
	  @Query("SELECT e.beneficiario.id, e.beneficiario.nombres, e.beneficiario.apellidos,e.beneficiario.documento, e.beneficiario.tipo,"
	  		+ "SUM(e.montoTotal) AS sumatoriaDescargo FROM Expense e "
	  		+ "WHERE e.proyectoFase.proyecto.id = :projectId and e.estado='V' and e.descargo = true and e.beneficiario.id IS NOT NULL "
	  		+ "GROUP BY e.beneficiario.id, e.beneficiario.nombres, e.beneficiario.apellidos,e.beneficiario.documento, e.beneficiario.tipo")
	  public List<Object[]> findAllExpensesVigentesWithDescargoByProjectIdGroupBeneficiary(Long projectId);//para admin
	  
	  @Query("SELECT e FROM Expense e WHERE e.proyectoFase.proyecto.id = :projectId and e.beneficiario.id = :beneficiaryId and e.descargo= true and estado='V'")
	  public List<Expense> findAllExpensesVigentesWithDescargoByProjectIdAndBeneficiaryId(Long projectId, Integer beneficiaryId);//para beneficiary
	  
	  @Query("SELECT e FROM Expense e WHERE e.proyectoFase.proyecto.id = :projectId and e.descargo= true and estado='V'")
	  public List<Expense> findAllExpensesVigentesWithDescargoByProjectId(Long projectId);//para beneficiary
	  
	  @Query("SELECT e.proyectoFase.proyecto FROM Expense e  WHERE  e.beneficiario.id = :beneficiaryId")
	  public List<Project> findExpenseByBeneficiaryId(Integer beneficiaryId);
	  
	  
}
