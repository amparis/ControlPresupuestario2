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
	  
	 // @Query("SELECT DISTINCT i.account.id FROM Income i WHERE i.proyecto.id = :projectId AND i.categoria = 'DESEMBOLSO' AND i.estado = 'V'")
	  //public Long findAllAccountOriginByProjectId(Long projectId);//si no existe desembolso? AJUSTAR con otra categoria
	  //@Query("SELECT i FROM Income i WHERE i.proyecto.id = :projectId AND i.categoria = 'DESEMBOLSO' AND i.estado = 'V' ORDER BY i.fecha ASC LIMIT 1")
	  //public Income findFirstAccountOriginByProjectId(Long projectId);
	  @Query("SELECT i FROM Income i WHERE i.proyecto.id = :projectId AND i.categoria = 'DESEMBOLSO' AND i.estado = 'V' ORDER BY i.fecha ASC")
	  public List<Income> findFirstAccountOriginByProjectId(Long projectId);


	  
//	  @Query("SELECT DISTINCT i.account.id FROM Income i WHERE i.proyecto.id = :projectId AND i.account.id <> :accountId AND i.estado = 'V'")
//	  public Integer findAllAccountDestinyByProjectId(Long projectId, Integer accountId);

	  @Query("SELECT i FROM Income i WHERE i.proyecto.id = :projectId AND i.account.id = :accountId AND i.estado = 'V'")
	  public  List<Income> findAllSaldosByProjectIdAndAccount(Long projectId, Long accountId);//si no existe desembolso? AJUSTAR con otra categoria

}
