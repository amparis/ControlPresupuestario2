package com.springmvc.ControlPresupuestario.service;

import java.sql.Timestamp;
import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springmvc.ControlPresupuestario.model.Income;
import com.springmvc.ControlPresupuestario.model.Project;
import com.springmvc.ControlPresupuestario.model.UserAdm;
import com.springmvc.ControlPresupuestario.repository.IncomeRepository;

@Service
public class IncomeService {

	@Autowired
	IncomeRepository incomeRepository;
	@Autowired
	AccountService accountService;
    @Autowired
    UserAdmService userService;
    @Autowired
    IMyUserDetailsService userDetailsService;
    
	@Autowired
	ProjectService projectService;
	
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
	
	//public Income getIncomeById(Long id);
	
	public Income saveIncome(Income newIncome, long projectId) {
		long millis = System.currentTimeMillis();
		   
		Project proyecto = projectService.getProject(projectId);
		newIncome.setCategoria(newIncome.getCategoria().toUpperCase());
		newIncome.setConcepto(newIncome.getConcepto().toUpperCase());
		newIncome.setFechaRegistro(new Timestamp(millis));
		newIncome.setEstado("V");
		newIncome.setProyIdPrestamo(null);
	    UserAdm loginUser = userService.getUser(userDetailsService.getUserDetailsService().getId());
	    newIncome.setUsuarioId((int) loginUser.getId());
		
	    return incomeRepository.save(newIncome);
	}
	
	public void updateIncome(long id, Income newIncome) {
		
	}
	
	public void deleteIncome(Integer id) {
		
		this.incomeRepository.deleteById(id);
	}
	////
	/*
    public List<Income> getIncomesByProjectId(Long id){
    	
    	return this.incomeRepository.findAllIncomesByProjectId(id);
    }///
	*/
}
