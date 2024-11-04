package com.springmvc.ControlPresupuestario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springmvc.ControlPresupuestario.model.ExpenditureClassification;
import com.springmvc.ControlPresupuestario.repository.ExpenditureClassificationRepository;

@Service
public class ExpenditureClassificationService {

	@Autowired
	ExpenditureClassificationRepository expenditureClassificationRepository;
	
	public List<ExpenditureClassification> getExpenditureClassifications(){
		
		return this.expenditureClassificationRepository.findAll();
	}
	

	public ExpenditureClassification getExpenditureClassification(long id) {
		
		return this.expenditureClassificationRepository.findById(id).get();
		
	}
	
	public ExpenditureClassification getExpenditureClassificationByNombre(String nombre) {
		
		return this.expenditureClassificationRepository.findExpenditureClassificationByNombre(nombre);
		
	}
	
	public ExpenditureClassification saveExpenditureClassification(ExpenditureClassification newExpenditureClassification) {


		return this.expenditureClassificationRepository.save(newExpenditureClassification);
	}
	public void updateExpenditureClassification(long id, ExpenditureClassification newExpenditureClassification) {
		
	}
	
	public void deleteExpenditureClassification(long id) {
		this.expenditureClassificationRepository.deleteById(id);
	}

}
