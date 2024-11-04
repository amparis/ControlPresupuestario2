package com.springmvc.ControlPresupuestario.service;

import java.sql.Timestamp;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springmvc.ControlPresupuestario.model.Income;
import com.springmvc.ControlPresupuestario.model.PaymentPlan;
import com.springmvc.ControlPresupuestario.model.Account;
import com.springmvc.ControlPresupuestario.model.AccountBalanceDTO;
import com.springmvc.ControlPresupuestario.model.Expense;
import com.springmvc.ControlPresupuestario.model.Project;
import com.springmvc.ControlPresupuestario.model.ProjectHistory;
import com.springmvc.ControlPresupuestario.model.UserAdm;
import com.springmvc.ControlPresupuestario.repository.ExpenseRepository;
import com.springmvc.ControlPresupuestario.repository.IncomeRepository;

@Service
public class IncomeService {

	@Autowired
	IncomeRepository incomeRepository;
	@Autowired
	ExpenseRepository expenseRepository;
	@Autowired
	AccountService accountService;
    @Autowired
    UserAdmService userService;
    @Autowired
    IMyUserDetailsService userDetailsService;
    
	@Autowired
	ProjectService projectService;
	@Autowired
	PaymentPlanService paymentPlanService;
	
    public List<Income> getIncomes(){
    	
    	return this.incomeRepository.findAll();
    }
    public List<Income> getIncomesByProjectId(long projectId){
    	
    	return this.incomeRepository.findAllIncomesByProjectId(projectId);
    }
    
    public List<Income> getIncomesVigentesByProjectId(long projectId){
    	
    	return this.incomeRepository.findAllIncomesVigentesByProjectId(projectId);
    }
    
	public Income getIncome(Integer id) {
		
		return this.incomeRepository.findById(id).get();
	}
	
    public Long getAllAccountOriginByProjectId(long projectId){
    	List<Income> incomes = incomeRepository.findFirstAccountOriginByProjectId(projectId);
        return incomes.isEmpty() ? null : incomes.get(0).getAccount().getId();
    	//return this.incomeRepository.findFirstAccountOriginByProjectId(projectId).getAccount().getId();//findAllAccountOriginByProjectId(projectId);
    }
    public List<Object[]> getAccountsByProjectId(long projectId){
    	return incomeRepository.findAccountsByProjectId(projectId);
    }
    //public Income findFirstAccountOrigin(Long projectId) {
        //List<Income> incomes = incomeRepository.findFirstAccountOriginByProjectId(projectId);
        //return incomes.isEmpty() ? null : incomes.get(0).getAccount().getId();
    //}

    
    //OBTENER SALDOS DE PROYECTOS, AGRUPADO POR PROYECTO Y CUENTA
    public  List<Income> getAllSaldosByProjectIdAndAccount(long projectId, long accountId){
    	
    	return this.incomeRepository.findAllSaldosByProjectIdAndAccount(projectId,accountId);
    }
    
    //OBTENER SALDOS DE PROYECTOS, AGRUPADO POR PROYECTO
    public  List<Object[]> getAllSaldosAndAccountByProjectId(long projectId){
    	
    	return this.incomeRepository.findAllSaldosAndAccountByProjectId(projectId);
    }
    //OBTENER LISTA DE PRESTAMOS RECIBIDOS POR PROYECTO
    public  List<Income> getLoansReceivedByProjectId(long projectId){
    	
    	return this.incomeRepository.findLoansReceivedByProjectId(projectId);
    }
    public List<AccountBalanceDTO> getAllSaldosAndAccountByProjectId(Long projectId) {
        List<Object[]> results = incomeRepository.findAllSaldosAndAccountByProjectId(projectId);
        List<AccountBalanceDTO> saldosCuenta = new ArrayList<>();

        for (Object[] row : results) {
            AccountBalanceDTO dto = new AccountBalanceDTO();
            dto.setAccountId((Long) row[0]);
            dto.setNameBank((String) row[1]);
            dto.setAccountNumber((String) row[2]);
            dto.setTotalAmount((Double) row[3]);
            dto.setTotalRecurringAmount((Double) row[4]);
            dto.setTotalNonRecurringAmount((Double) row[5]);
            saldosCuenta.add(dto);
        }

        return saldosCuenta;
    }
	//public Income getIncomeById(Long id);
	
	public Income saveIncome(Income newIncome, long projectId,Double montoTask, long pagoSelected ) {
		long millis = System.currentTimeMillis();
		   
		Project proyecto = projectService.getProject(projectId);
		newIncome.setProyecto(proyecto);
		newIncome.setCategoria(newIncome.getCategoria().toUpperCase());
		newIncome.setConcepto(newIncome.getConcepto().toUpperCase());
		newIncome.setFechaRegistro(new Timestamp(millis));
		newIncome.setEstado("V");
		newIncome.setProyIdPrestamo(null);
	    UserAdm loginUser = userService.getUser(userDetailsService.getUserDetailsService().getId());
	    newIncome.setUsuarioId((int) loginUser.getId());
	    newIncome.setPlanPago(paymentPlanService.getPaymentPlanById(pagoSelected));
		
	    PaymentPlan montoPago = paymentPlanService.getPaymentPlanById(pagoSelected);
	   
	    
	    if((montoTask+ newIncome.getMonto()+montoPago.getMontoPagado())== montoPago.getMonto())
	    {
	    	montoPago.setEstado("PAID");
	    }
	    else {
	    	//montoPago.setMontoPagado(montoPago.getMonto()-(newIncome.getMonto()+montoTask));
	    	montoPago.setEstado("PARTIAL");
	    }
	    montoPago.setMontoPagado(newIncome.getMonto()+montoTask+montoPago.getMontoPagado());
	    paymentPlanService.savePaymentPlan(montoPago);
	    
	    if (montoTask>0) {
	    //save TASK en Expense
        Expense savedExpenseTask= new Expense();
        //savedExpenseTask.setProyecto(proyecto);
        savedExpenseTask.setTipo("");
        savedExpenseTask.setObjeto(newIncome.getConcepto().toUpperCase());
        savedExpenseTask.setCargoItem("BANK CHARGE");
        savedExpenseTask.setCantidad(1);
        savedExpenseTask.setFechaInicio(newIncome.getFecha());
        savedExpenseTask.setFechaFin(newIncome.getFecha());
        savedExpenseTask.setTiempo(0);
        savedExpenseTask.setCostoUnitario(montoTask);
        savedExpenseTask.setMontoTotal(montoTask);
        savedExpenseTask.setDescargo(false);
        savedExpenseTask.setAguinaldo(0);
        savedExpenseTask.setEstado("V");
        savedExpenseTask.setUsuarioId((int) loginUser.getId());
        expenseRepository.save(savedExpenseTask);
	    }
	    return incomeRepository.save(newIncome);
    
	}
	
	public Income saveIncomeTransfer(Income newIncome, long projectId, Double montoTask ,Account accountOrigin) {
		long millis = System.currentTimeMillis();
		  //SAVE DATA ACCOUNT DESTINY 
		 UserAdm loginUser = userService.getUser(userDetailsService.getUserDetailsService().getId());
		Project proyecto = projectService.getProject(projectId);
		
		newIncome.setProyecto(proyecto);
		newIncome.setCategoria("DESEMBOLSO");
		newIncome.setConcepto(newIncome.getConcepto().toUpperCase());
		newIncome.setFechaRegistro(new Timestamp(millis));
		newIncome.setEstado("V");
		newIncome.setProyIdPrestamo(null);	   
	    newIncome.setUsuarioId((int) loginUser.getId());
	    incomeRepository.save(newIncome);
	    
	    //SAVE DATA ACCOUNT ORIGIN
	   
	    Income incomeOrigin = new Income();
	    incomeOrigin.setProyecto(proyecto);
	    incomeOrigin.setCategoria("TRANSFERENCIA");
	    incomeOrigin.setConcepto(newIncome.getConcepto().toUpperCase());
	    incomeOrigin.setFecha(newIncome.getFecha());
	    incomeOrigin.setAccount(accountOrigin);
	    incomeOrigin.setFechaRegistro(new Timestamp(millis));
	    incomeOrigin.setEstado("V");
	    incomeOrigin.setProyIdPrestamo(null);	   
	    incomeOrigin.setUsuarioId((int) loginUser.getId());
	    // Ajustar los montos de la cuenta origen con valores negativos
	    incomeOrigin.setMonto(newIncome.getMonto() * -1);
	    incomeOrigin.setMontoRecurrente(newIncome.getMontoRecurrente() * -1);
	    incomeOrigin.setMontoNoRecurrente(newIncome.getMontoNoRecurrente() * -1);
	    incomeRepository.save(incomeOrigin);
	    
		//SAVE amount task
	    if (montoTask>0) {
	    //save TASK en Expense
        Expense savedExpenseTask= new Expense();
        //savedExpenseTask.setProyecto(proyecto);
        savedExpenseTask.setTipo("");
        savedExpenseTask.setObjeto(newIncome.getConcepto().toUpperCase());
        savedExpenseTask.setCargoItem("BANK CHARGE");
        savedExpenseTask.setCantidad(1);
        savedExpenseTask.setFechaInicio(newIncome.getFecha());
        savedExpenseTask.setFechaFin(newIncome.getFecha());
        savedExpenseTask.setTiempo(0);
        savedExpenseTask.setCostoUnitario(montoTask);
        savedExpenseTask.setMontoTotal(montoTask);
        savedExpenseTask.setDescargo(false);
        savedExpenseTask.setAguinaldo(0);
        savedExpenseTask.setEstado("V");
        savedExpenseTask.setUsuarioId((int) loginUser.getId());
        expenseRepository.save(savedExpenseTask);
	    }
	    
	    return incomeRepository.save(incomeOrigin);
    
	}
	
	public Income saveIncomeLoan(Income newIncome, long projectId, Account accountOrigin, long projectIdDestiny, Double montoTask, Account accountDestiny) {
		long millis = System.currentTimeMillis();
		  //SAVE DATA PROJECT DESTINY 
		UserAdm loginUser = userService.getUser(userDetailsService.getUserDetailsService().getId());
		Project proyecto = projectService.getProject(projectIdDestiny);
		
		newIncome.setProyecto(proyecto);
		newIncome.setAccount(accountDestiny);
		newIncome.setCategoria("PRESTAMO");
		newIncome.setConcepto(newIncome.getConcepto().toUpperCase());
		newIncome.setFechaRegistro(new Timestamp(millis));
		newIncome.setEstado("V");
		newIncome.setProyIdPrestamo(null);	   
	    newIncome.setUsuarioId((int) loginUser.getId());
	    
	    newIncome.setMontoRecurrente(0);
	    newIncome.setMontoNoRecurrente(newIncome.getMonto());
	    incomeRepository.save(newIncome);
	    
	    //SAVE DATA PROYECT ORIGIN
	   
	    Income incomeOrigin = new Income();
	    Project proyectoOrigin = projectService.getProject(projectId);
	    incomeOrigin.setProyecto(proyectoOrigin);
	    incomeOrigin.setAccount(accountOrigin);
	    incomeOrigin.setCategoria("PRESTAMO");
	    incomeOrigin.setConcepto(newIncome.getConcepto().toUpperCase());
	    incomeOrigin.setFecha(newIncome.getFecha());
	    incomeOrigin.setAccount(accountOrigin);
	    incomeOrigin.setFechaRegistro(new Timestamp(millis));
	    incomeOrigin.setEstado("V");
	    incomeOrigin.setProyIdPrestamo(projectIdDestiny);	   
	    incomeOrigin.setUsuarioId((int) loginUser.getId());
	    // Ajustar los montos de la cuenta origen con valores negativos
	    
	    incomeOrigin.setMonto(newIncome.getMonto() * -1);
	    incomeOrigin.setMontoRecurrente(0);
	    incomeOrigin.setMontoNoRecurrente(newIncome.getMonto() * -1);
	    incomeRepository.save(incomeOrigin);
    
	    if (montoTask>0) {
	    //save TASK en Expense
        Expense savedExpenseTask= new Expense();
        //savedExpenseTask.setProyecto(proyecto); // CONFIRMAR SI ES PROYECTO ORIGEN O DESTINO
        savedExpenseTask.setTipo("");
        savedExpenseTask.setObjeto(newIncome.getConcepto().toUpperCase());
        savedExpenseTask.setCargoItem("BANK CHARGE");
        savedExpenseTask.setCantidad(1);
        savedExpenseTask.setFechaInicio(newIncome.getFecha());
        savedExpenseTask.setFechaFin(newIncome.getFecha());
        savedExpenseTask.setTiempo(0);
        savedExpenseTask.setCostoUnitario(montoTask);
        savedExpenseTask.setMontoTotal(montoTask);
        savedExpenseTask.setDescargo(false);
        savedExpenseTask.setAguinaldo(0);
        savedExpenseTask.setEstado("V");
        savedExpenseTask.setUsuarioId((int) loginUser.getId());
        expenseRepository.save(savedExpenseTask);
	    }
	    
	    
	    return incomeRepository.save(incomeOrigin);
    
	}

	public Income saveIncomeRepayment(Income newIncome, long projectIdAcreedor, Account accountAcreedor,Account accountOrigin,  long projectId) {
		long millis = System.currentTimeMillis();
		  //SAVE DATA PROJECT ACREEDOR 
		UserAdm loginUser = userService.getUser(userDetailsService.getUserDetailsService().getId());
		Project proyectoAcreedor = projectService.getProject(projectIdAcreedor);
		
		newIncome.setProyecto(proyectoAcreedor);
		newIncome.setCategoria("DEVOLUCION");
		newIncome.setConcepto(newIncome.getConcepto().toUpperCase());
		newIncome.setFechaRegistro(new Timestamp(millis));
		newIncome.setAccount(accountAcreedor);///asumo q es el mismo
		newIncome.setEstado("V");
		newIncome.setProyIdPrestamo(null);	   
	    newIncome.setUsuarioId((int) loginUser.getId());
	    newIncome.setMontoRecurrente(0);
	    newIncome.setMontoNoRecurrente(newIncome.getMonto());
	    incomeRepository.save(newIncome);
	    
	    //SAVE DATA PROYECT DEUDOR
	   
	    Income incomeOrigin = new Income();
	    Project proyectoDeudor = projectService.getProject(projectId);
	    incomeOrigin.setProyecto(proyectoDeudor);
	    incomeOrigin.setCategoria("DEVOLUCION");
	    incomeOrigin.setConcepto(newIncome.getConcepto().toUpperCase());
	    incomeOrigin.setFecha(newIncome.getFecha());
	    incomeOrigin.setAccount(accountOrigin);///asumo q es el mismo
	    incomeOrigin.setFechaRegistro(new Timestamp(millis));
	    incomeOrigin.setEstado("V");
	    incomeOrigin.setProyIdPrestamo(projectIdAcreedor);	   
	    incomeOrigin.setUsuarioId((int) loginUser.getId());
	    // Ajustar los montos de la cuenta origen con valores negativos
	    incomeOrigin.setMonto(newIncome.getMonto() * -1);
	    incomeOrigin.setMontoRecurrente(0);
	    incomeOrigin.setMontoNoRecurrente(newIncome.getMonto() * -1);
	    incomeRepository.save(incomeOrigin);
    
	    return incomeRepository.save(incomeOrigin);
    
	}

	public void updateIncome(long id, Income newIncome) {
		
	}
	
	public void deleteIncome(Integer id) {
		
		//this.incomeRepository.deleteById(id);
		Income incomeDelete = incomeRepository.findById(id).get();
		incomeDelete.setEstado("C");
		this.incomeRepository.save(incomeDelete);
		
	}

}
