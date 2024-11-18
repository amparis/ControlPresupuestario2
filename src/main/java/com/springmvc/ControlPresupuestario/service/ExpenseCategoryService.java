package com.springmvc.ControlPresupuestario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springmvc.ControlPresupuestario.model.ExpenseCategory;

import com.springmvc.ControlPresupuestario.repository.ExpenseCategoryRepository;

@Service
public class ExpenseCategoryService {

	@Autowired
	ExpenseCategoryRepository expenseCategoryRepository;
	
	public List<ExpenseCategory> getExpenseCategories(){
		
		return this.expenseCategoryRepository.findAll();
	}

	public ExpenseCategory getExpenseCategory(long id) {
		
		return this.expenseCategoryRepository.findById(id).get();
		
	}
	public List<ExpenseCategory> getExpenseCategoryByPhase(long id) {
		
		return this.expenseCategoryRepository.findByPhaseId(id);
		
	}	
	
	public List<ExpenseCategory> getExpenseCategoryByListPhases(List<Long> phasesId) {
		
		return this.expenseCategoryRepository.findByListPhaseId(phasesId);
		
	}	
	
	
	//public ExpenseCategory getExpenseCategoryByNombre(String nombre) {
		
		//return this.expenseCategoryRepository.findAllExpenseCategoryByNombre(nombre);
		
	//}
	
	public ExpenseCategory saveExpenseCategory(ExpenseCategory newExpenseCategory) {


		return this.expenseCategoryRepository.save(newExpenseCategory);
	}
	public void updateExpenseCategory(long id, ExpenseCategory newExpenseCategory) {
		
	}
	
	public void deleteExpenseCategory(long id) {
		this.expenseCategoryRepository.deleteById(id);
	}


}
