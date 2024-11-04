package com.springmvc.ControlPresupuestario.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.springmvc.ControlPresupuestario.model.Income;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Integer> {
	  
	  @Query("SELECT i FROM Income i WHERE i.proyecto.id = :projectId ORDER BY i.fecha DESC")
	  public List<Income> findAllIncomesByProjectId(Long projectId);
	  
	  @Query("SELECT i FROM Income i WHERE i.proyecto.id = :projectId and estado='V' ORDER BY i.fecha DESC")
	  public List<Income> findAllIncomesVigentesByProjectId(Long projectId);
	  
	  @Query("SELECT i FROM Income i WHERE i.proyecto.id = :projectId AND (i.categoria = 'DESEMBOLSO' OR i.categoria = 'PRESTAMO') AND i.estado = 'V' ORDER BY i.fecha ASC")
	  public List<Income> findFirstAccountOriginByProjectId(Long projectId);
	  
	  @Query("SELECT i.account.id, i.account.nameBank, i.account.accountNumber FROM Income i WHERE i.proyecto.id = :projectId AND (i.categoria = 'DESEMBOLSO') AND i.estado = 'V' ORDER BY i.account.id")
	  public List<Object[]> findAccountsByProjectId(Long projectId);

	  @Query("SELECT i FROM Income i WHERE i.proyecto.id = :projectId AND i.account.id = :accountId AND i.estado = 'V'")
	  public  List<Income> findAllSaldosByProjectIdAndAccount(Long projectId, Long accountId);
	  
	  @Query("SELECT i.proyecto.id,i.proyecto.nombre, SUM(i.montoNoRecurrente) FROM Income i WHERE i.estado = 'V' GROUP BY i.proyecto.id, i.proyecto.nombre HAVING SUM(i.montoNoRecurrente) > 0")
	  public  List<Object[]> findAllSaldosByProjectId();
	  
	  @Query("SELECT i FROM Income i WHERE i.proyIdPrestamo = :projectId AND i.categoria = 'PRESTAMO' AND i.estado = 'V' ORDER BY i.fecha ASC")
	  public List<Income> findLoansReceivedByProjectId(Long projectId);	  
	  
	  ///////
	  @Query("SELECT i.account.id, i.account.nameBank,i.account.accountNumber,  SUM(i.monto) ,  SUM(i.montoRecurrente) ,  SUM(i.montoNoRecurrente)   FROM Income i WHERE i.proyecto.id = :projectId AND i.estado = 'V' GROUP BY   i.account.id, i.account.nameBank,i.account.accountNumber  HAVING SUM(i.monto) > 0")
	  public  List<Object[]> findAllSaldosAndAccountByProjectId(Long projectId);
}


