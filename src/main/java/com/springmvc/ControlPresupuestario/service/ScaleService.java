package com.springmvc.ControlPresupuestario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springmvc.ControlPresupuestario.model.Scale;
import com.springmvc.ControlPresupuestario.repository.ScaleRepository;

@Service
public class ScaleService {

	@Autowired
    private ScaleRepository scaleRepository;
   
    public List<Scale> getAllScales() {
        return scaleRepository.findAll();
    }
    
    public List<Scale> getAllScalesEstado(String estado) {

    	return scaleRepository.findAllByEstado(estado);
    }

    public Scale getScaleByNombre(String nombreScale) {
        return scaleRepository.findScaleByNombre(nombreScale);
    }
    

    public Scale getScaleById(Long id) {
        return scaleRepository.findById(id).get();
    }
    public Scale saveScale(Scale newScale) {
    	newScale.setEstado("V");
        return scaleRepository.save(newScale);
    }

    public void deleteScale(Long id) {
    	scaleRepository.deleteById(id);
    }
}
