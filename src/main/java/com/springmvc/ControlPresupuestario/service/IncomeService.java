package com.springmvc.ControlPresupuestario.service;

import java.sql.Timestamp;
import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springmvc.ControlPresupuestario.model.Income;
import com.springmvc.ControlPresupuestario.model.UserAdm;
import com.springmvc.ControlPresupuestario.repository.IncomeRepository;

@Service
public class IncomeService {

	@Autowired
	IncomeRepository incomeRepository;
	
    @Autowired
    UserAdmService userService;
    @Autowired
    IMyUserDetailsService userDetailsService;
    
    public List<Income> getIncomes(){
    	
    	return this.incomeRepository.findAll();
    }
    
	public Income getIncome(long id) {
		
		return this.incomeRepository.findById(id).get();
	}
	
	//public Income getIncomeById(Long id);
	
	public Income saveIncome(Income newIncome) {
		/* existira una VALIDACION, PREGUNTAR DE QUE TIPO
		boolean exists = projectRepository.existsByNombreAndFechaInicialAndFechaFin(
	            newProject.getNombre(), newProject.getFechaInicial(), newProject.getFechaFin());

	    if (exists) {
	        throw new IllegalStateException("Ya existe un proyecto con el mismo nombre, fecha de inicial y fecha de fin.");
	    }
		*/
		long millis = System.currentTimeMillis();
		newIncome.setFecha(new Timestamp(millis));
		newIncome.setEstado("V");
	    // Obtener el usuario logueado
	    UserAdm loginUser = userService.getUser(userDetailsService.getUserDetailsService().getId());
	    // Asignar el id del usuario logueado al campo us_id del Project
	    newIncome.setUsuarioId((int) loginUser.getId());
   
		return this.incomeRepository.save(newIncome);
	}
	
	public void updateIncome(long id, Income newIncome) {
		
	}
	
	public void deleteIncome(long id) {
		
		this.incomeRepository.deleteById(id);
	}

}
