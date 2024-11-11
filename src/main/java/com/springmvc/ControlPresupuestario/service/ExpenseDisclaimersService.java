package com.springmvc.ControlPresupuestario.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springmvc.ControlPresupuestario.model.Expense;
import com.springmvc.ControlPresupuestario.model.ExpenseDisclaimers;
import com.springmvc.ControlPresupuestario.model.UserAdm;
import com.springmvc.ControlPresupuestario.repository.ExpenseDisclaimersRepository;

@Service
public class ExpenseDisclaimersService {
	
	@Autowired
	ExpenseDisclaimersRepository expenseDisclaimersRepository;
    @Autowired
    UserAdmService userService;
    @Autowired
    IMyUserDetailsService userDetailsService;
    @Autowired
    ExpenseService expenseService;
    
	public List<ExpenseDisclaimers> getExpenseDisclaimers(){
		
		return this.expenseDisclaimersRepository.findAll();
	}

	public List<ExpenseDisclaimers> getExpenseDisclaimersByExpenseId(long expenseId) {
		
		return this.expenseDisclaimersRepository.findExpenseDisclaimersByExpenseId(expenseId);
		
	}
	public List<ExpenseDisclaimers> getExpenseDisclaimersByProjectId(Long projectId) {
		
		return this.expenseDisclaimersRepository.findExpenseDisclaimersByProjectId(projectId);
		
	}
	
	public List<ExpenseDisclaimers> getExpenseDisclaimersByProjectIdAndBeneficiaryId(Long projectId, Integer beneficiaryId) {
		
		return this.expenseDisclaimersRepository.findExpenseDisclaimersByProjectIdAndBeneficiaryId(projectId,beneficiaryId);
		
	}

	//public ExpenseCategory getExpenseCategoryByNombre(String nombre) {
		
		//return this.expenseCategoryRepository.findAllExpenseCategoryByNombre(nombre);
		
	//}
	/*
	public ExpenseDisclaimers saveExpenseDisclaimers(ExpenseDisclaimers newExpenseDisclaimers,long expenseId) {

		long millis = System.currentTimeMillis();
	    UserAdm loginUser = userService.getUser(userDetailsService.getUserDetailsService().getId());
	    Expense expenseSelected = expenseService.getExpense(expenseId);
	    newExpenseDisclaimers.setEgreso(expenseSelected);
		newExpenseDisclaimers.setUserId((int) loginUser.getId());
		newExpenseDisclaimers.setFechaRegistro(new Timestamp(millis));
		return this.expenseDisclaimersRepository.save(newExpenseDisclaimers);
	}*/
	public ExpenseDisclaimers saveExpenseDisclaimers(ExpenseDisclaimers newExpenseDisclaimers) {

		long millis = System.currentTimeMillis();
	    UserAdm loginUser = userService.getUser(userDetailsService.getUserDetailsService().getId());
	   // Expense expenseSelected = expenseService.getExpense(expenseId);
	    //newExpenseDisclaimers.setEgreso(expenseSelected);
		newExpenseDisclaimers.setUserId((int) loginUser.getId());
		newExpenseDisclaimers.setFechaRegistro(new Timestamp(millis));
		return this.expenseDisclaimersRepository.save(newExpenseDisclaimers);
	}
	public void updateExpenseDisclaimers(long id, ExpenseDisclaimers newExpenseDisclaimers) {
		
	}
	
	public void deleteExpenseDisclaimers(long id) {
		this.expenseDisclaimersRepository.deleteById(id);
	}

}
