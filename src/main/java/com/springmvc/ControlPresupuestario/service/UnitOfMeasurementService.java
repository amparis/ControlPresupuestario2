package com.springmvc.ControlPresupuestario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springmvc.ControlPresupuestario.model.UnitOfMeasurement;
import com.springmvc.ControlPresupuestario.repository.UnitOfMeasurementRepository;

@Service
public class UnitOfMeasurementService {

	@Autowired
	UnitOfMeasurementRepository unitOfMeasurementRepository;
	
	public List<UnitOfMeasurement> getUnitOfMeasurements(){
		
		return this.unitOfMeasurementRepository.findAll();
	}
	

	public UnitOfMeasurement getUnitOfMeasurement(long id) {
		
		return this.unitOfMeasurementRepository.findById(id).get();
		
	}
	
	//public UnitOfMeasurement getUnitOfMeasurementByNombre(String nombre) {
		
		//return this.unitOfMeasurementRepository.findUnitOfMeasurementByNombre(nombre);
		
	//}
	
	public UnitOfMeasurement saveUnitOfMeasurement(UnitOfMeasurement newUnitOfMeasurement) {

		newUnitOfMeasurement.setEstado("V");
		return this.unitOfMeasurementRepository.save(newUnitOfMeasurement);
	}
	public void updateUnitOfMeasurement(long id, UnitOfMeasurement newUnitOfMeasurement) {
		
	}
	
	public void deleteUnitOfMeasurement(long id) {
		this.unitOfMeasurementRepository.deleteById(id);
	}

}
