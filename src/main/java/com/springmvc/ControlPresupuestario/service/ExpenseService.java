package com.springmvc.ControlPresupuestario.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springmvc.ControlPresupuestario.model.Expense;
import com.springmvc.ControlPresupuestario.model.Income;
import com.springmvc.ControlPresupuestario.model.Project;
import com.springmvc.ControlPresupuestario.model.UserAdm;
import com.springmvc.ControlPresupuestario.repository.ExpenseRepository;
import com.springmvc.ControlPresupuestario.repository.IncomeRepository;

@Service
public class ExpenseService {
	
	@Autowired
	ExpenseRepository expenseRepository;
	@Autowired
	IncomeRepository incomeRepository;
	@Autowired
	ProjectService projectService;
	
	@Autowired
	AccountService accountService;
    @Autowired
    UserAdmService userService;
    @Autowired
    IMyUserDetailsService userDetailsService;

    public List<Expense> getExpenses(){
    	
    	return this.expenseRepository.findAll();
    }
    public List<Expense> getExpensesByProjectId(long projectId){
    	
    	return this.expenseRepository.findAllExpensesByProjectId(projectId);
    }
    
    public List<Expense> getExpensesVigentesByProjectId(long projectId){
    	
    	return this.expenseRepository.findAllExpensesVigentesByProjectId(projectId);
    }
    
	public Expense getExpense(Long id) {
		
		return this.expenseRepository.findById(id).get();
	}
	/*
	public Expense saveExpense(Expense newExpense,Integer incomeId, long projectId) {
		long millis = System.currentTimeMillis();
		   
		Project proyecto = projectService.getProject(projectId);
		
		newExpense.setTipo(null);
		newExpense.setObjeto(newIncome.getConcepto().toUpperCase());
		newExpense.setConcepto(newIncome.getConcepto().toUpperCase());
		newExpense.setFechaRegistro(new Timestamp(millis));
		newExpense.setEstado("V");
		newExpense.setProyIdPrestamo(null);
	    UserAdm loginUser = userService.getUser(userDetailsService.getUserDetailsService().getId());
	    newExpense.setUsuarioId((int) loginUser.getId());
		
	    return incomeRepository.save(newExpense);
	}
    */
}
