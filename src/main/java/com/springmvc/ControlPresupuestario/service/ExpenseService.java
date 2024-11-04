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
	ExpenditureClassificationService expenditureClassificationService;
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
	
	public Expense saveExpense(Expense newExpense) {
		long millis = System.currentTimeMillis();
		   
		//Project proyecto = projectService.getProject(projectId);
		newExpense.setAguinaldo(0);
		newExpense.setFechaRegistro(new Timestamp(millis));
		newExpense.setEstado("V");
	    UserAdm loginUser = userService.getUser(userDetailsService.getUserDetailsService().getId());
	    newExpense.setUsuarioId((int) loginUser.getId());
		
	    expenseRepository.save(newExpense);
	    
	    if (newExpense.getFee()>0)
	    {
	    	Expense expenseFee= new Expense();
	    	expenseFee.setTipo("Non-recurring");
	    	expenseFee.setProyectoFase(newExpense.getProyectoFase());
	    	expenseFee.setClasificacionEgreso(expenditureClassificationService.getExpenditureClassificationByNombre("cargos bancarios"));//
	    	expenseFee.setObjeto("FEE/CARGO BANCARIO");
	    	expenseFee.setCargoItem("FEE/CARGO BANCARIO");
	    	expenseFee.setCantidad(1);
	    	expenseFee.setFechaInicio(newExpense.getFechaInicio());
	    	expenseFee.setFechaFin(newExpense.getFechaFin());
	    	expenseFee.setTiempo(1);
	    	expenseFee.setCostoUnitario(newExpense.getFee());
	    	expenseFee.setAguinaldo(0);
	    	expenseFee.setMontoTotal(newExpense.getFee());
	    	expenseFee.setEstado("V");
	    	expenseFee.setUsuarioId((int) loginUser.getId());
	    	expenseFee.setDescargo(false);
	    	expenseFee.setPais(newExpense.getPais());
	    	expenseFee.setFecha(newExpense.getFecha());
	    	expenseFee.setTipoTransferencia("TRANSFER");
	    	expenseFee.setFee(0);
	    	expenseFee.setCambio(newExpense.getCambio());
	    	expenseFee.setFechaRegistro(new Timestamp(millis));
	    	expenseFee.setTotalLCU(newExpense.getTotalLCU());
	    	expenseRepository.save(expenseFee);
	    }
	    
	    return expenseRepository.save(newExpense);
	}
}
