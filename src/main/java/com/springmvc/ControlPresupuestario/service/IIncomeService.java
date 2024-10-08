package com.springmvc.ControlPresupuestario.service;

import java.util.List;

import com.springmvc.ControlPresupuestario.model.Income;

public interface IIncomeService {

	public List<Income> getIncomes();
	
	public Income getIncome(long id);
	
	//public Income getIncomeById(Long id);
	
	public Income saveIncome(Income newIncome);
	
	public void updateIncome(long id, Income newIncome);
	
	public void deleteIncome(long id);
}
