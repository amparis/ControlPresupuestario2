package com.springmvc.ControlPresupuestario.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.springmvc.ControlPresupuestario.model.ExpenseDisclaimers;
import com.springmvc.ControlPresupuestario.model.Phase;
import com.springmvc.ControlPresupuestario.model.Project;

@Repository
public interface ExpenseDisclaimersRepository extends JpaRepository<ExpenseDisclaimers,Long>{

	  @Query("SELECT ed FROM ExpenseDisclaimers ed  WHERE  ed.egreso.id = :expenseId")
	  public List<ExpenseDisclaimers> findExpenseDisclaimersByExpenseId(long expenseId);
	  
	  @Query("SELECT ed FROM ExpenseDisclaimers ed  WHERE  ed.egreso.proyectoFase.proyecto.id = :projectId")
	  public List<ExpenseDisclaimers> findExpenseDisclaimersByProjectId(Long projectId);
	  

	  @Query("SELECT ed FROM ExpenseDisclaimers ed  WHERE  ed.egreso.proyectoFase.proyecto.id = :projectId and ed.egreso.beneficiario.id = :beneficiaryId")
	  public List<ExpenseDisclaimers> findExpenseDisclaimersByProjectIdAndBeneficiaryId(Long projectId, Integer beneficiaryId);
	  
	  @Query("SELECT SUM(ed.totalUSD) FROM ExpenseDisclaimers ed WHERE ed.egreso.proyectoFase.proyecto.id = :projectId and ed.egreso.beneficiario.id = :beneficiaryId")
	  public BigDecimal findSummaryExpenseDisclaimersByProjectIdAndBeneficiaryId(Long projectId, Integer beneficiaryId);
	  
	    @Query(value = "SELECT * FROM fn_get_beneficiary_data(:proyecto_id)", nativeQuery = true)
	    public List<Object[]> findSummaryExpenseVoucherByProyectoId(@Param("proyecto_id") Integer proyecto_id);

}
