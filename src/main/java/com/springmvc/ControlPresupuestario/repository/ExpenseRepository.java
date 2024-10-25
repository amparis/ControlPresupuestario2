package com.springmvc.ControlPresupuestario.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.springmvc.ControlPresupuestario.model.Expense;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense,Long>  {

	  @Query("SELECT e FROM Expense e WHERE e.proyecto.id = :projectId")
	  public List<Expense> findAllExpensesByProjectId(Long projectId);
	  
	  @Query("SELECT e FROM Expense e WHERE e.proyecto.id = :projectId and estado='V'")
	  public List<Expense> findAllExpensesVigentesByProjectId(Long projectId);
}
