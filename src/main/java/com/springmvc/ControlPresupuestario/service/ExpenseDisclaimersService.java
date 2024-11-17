package com.springmvc.ControlPresupuestario.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springmvc.ControlPresupuestario.model.Expense;
import com.springmvc.ControlPresupuestario.model.ExpenseDisclaimers;
import com.springmvc.ControlPresupuestario.model.LoanSummaryDTO;
import com.springmvc.ControlPresupuestario.model.SummaryExpenseVoucherDTO;
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

	public ExpenseDisclaimers getExpenseDisclaimer(Long pagoId){
		
		return this.expenseDisclaimersRepository.findById(pagoId).get();
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
	public BigDecimal getSummaryExpenseDisclaimersByProjectIdAndBeneficiaryId(Long projectId, Integer beneficiaryId) {
		
		return this.expenseDisclaimersRepository.findSummaryExpenseDisclaimersByProjectIdAndBeneficiaryId(projectId,beneficiaryId);		
	}

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
	
	   public List<SummaryExpenseVoucherDTO> getSummaryExpenseVoucherByProyectoId(Integer proyectoId) {
	        List<Object[]> resultList = expenseDisclaimersRepository.findSummaryExpenseVoucherByProyectoId(proyectoId);
	        List<SummaryExpenseVoucherDTO> expenseSummaryList = new ArrayList<>();

	        for (Object[] row : resultList) {
	            // Mapea las columnas de la función a variables
	            BigInteger beneficarioId = (BigInteger) row[0];
	            String fullname = (String) row[1];
	            BigDecimal  totalUsd = (BigDecimal ) row[2];
	            BigDecimal  totalLcu = (BigDecimal ) row[3];
	            BigDecimal  gtotalUsd = (BigDecimal ) row[4];
	            BigDecimal  gtotalLCU = (BigDecimal ) row[5];
	 	 
	            // Crear el DTO y establecer los datos
	            SummaryExpenseVoucherDTO summary = new SummaryExpenseVoucherDTO();
	            summary.setBen_id(beneficarioId);
	            summary.setFullname(fullname);
	            summary.setTotalUsd(totalUsd);
	            summary.setTotalLcu(totalLcu);
	            summary.setGtotalUsd(gtotalUsd);
	            summary.setGtotalLCU(gtotalLCU);
	            expenseSummaryList.add(summary);
	        }

	        return expenseSummaryList;
	    }
	public void updateExpenseDisclaimers(long id, ExpenseDisclaimers newExpenseDisclaimers) {
		
	}
	
	public void deleteExpenseDisclaimers(long id) {
		this.expenseDisclaimersRepository.deleteById(id);
	}

}
