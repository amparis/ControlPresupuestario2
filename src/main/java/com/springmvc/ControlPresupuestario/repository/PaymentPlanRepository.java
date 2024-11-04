package com.springmvc.ControlPresupuestario.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.springmvc.ControlPresupuestario.model.PaymentPlan;


@Repository
public interface PaymentPlanRepository extends JpaRepository<PaymentPlan, Long>{

	  @Query("SELECT pp FROM PaymentPlan pp  WHERE  pp.proyecto.id = :projectId order by pp.id")
	  public List<PaymentPlan> findPaymentPlanByProjectId(Long projectId);	
}
