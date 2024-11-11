package com.springmvc.ControlPresupuestario.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springmvc.ControlPresupuestario.model.BeneficiaryExpenseSummaryDTO;
import com.springmvc.ControlPresupuestario.model.Expense;
import com.springmvc.ControlPresupuestario.model.Income;
import com.springmvc.ControlPresupuestario.model.LoanSummaryDTO;
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
	    
	    if(newExpense.getDescargo()) {
	    	//En caso de el que el Expense presente descargo, verificar si corresponde crear usuario,
	    	Long userId = projectService.getOrCreateUserForBeneficiary(newExpense.getBeneficiario().getId(), 3L);
	    	System.out.println("userId new "+userId);
	    	projectService.linkUserToProject(newExpense.getProyectoFase().getProyecto(), userId);// y asociarlo al proyecto
	    }
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

   // public List<BeneficiaryExpenseSummaryDTO> getExpensesVigentesWithDescargo(Long projectId) {
     //   return expenseRepository.findAllExpensesVigentesWithDescargoByProjectId(projectId);
   // }
    
    public List<BeneficiaryExpenseSummaryDTO> getExpensesVigentesWithDescargo(Long projectId) {
        List<Object[]> resultList = expenseRepository.findAllExpensesVigentesWithDescargoByProjectId(projectId);
        List<BeneficiaryExpenseSummaryDTO> loanSummaryList = new ArrayList<>();

        for (Object[] row : resultList) {
            // Mapea las columnas de la función a variables
        	Integer id = (Integer) row[0];
        	String nombres = (String ) row[1];
        	String apellidos = (String) row[2];
            String documento = (String) row[3];
            String tipo = (String) row[4];
            Double  monto = (Double ) row[5];
 
            // Crear el DTO y establecer los datos
            BeneficiaryExpenseSummaryDTO summary = new BeneficiaryExpenseSummaryDTO();
            summary.setId(id);
            summary.setNombres(nombres);
            summary.setApellidos(apellidos);
            summary.setTipo(tipo);
            summary.setDocumento(documento);
            summary.setSumatoriaDescargo(monto);

            
            loanSummaryList.add(summary);
        }

        return loanSummaryList;
    }
    
    public List<Expense> getExpensesVigentesWithDescargoByProjectIdAndBeneficiaryId(Long projectId, Integer beneficiaryId){
    	return this.expenseRepository.findAllExpensesVigentesWithDescargoByProjectIdAndBeneficiaryId(projectId,beneficiaryId);
    }
}
