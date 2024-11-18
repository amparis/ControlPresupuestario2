package com.springmvc.ControlPresupuestario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springmvc.ControlPresupuestario.model.Beneficiary;
import com.springmvc.ControlPresupuestario.model.Phase;
import com.springmvc.ControlPresupuestario.repository.PhaseRepository;

@Service
public class PhaseService {

	@Autowired
    private PhaseRepository phaseRepository;
   
    public List<Phase> getAllPhases() {
        return phaseRepository.findAll();
    }
    
    public List<Phase> getAllPhasesEstado(String estado) {

    	return phaseRepository.findAllByEstado(estado);
    }

    public Phase getPhaseByNombre(String nombrePhase) {
        return phaseRepository.findPhaseByNombre(nombrePhase);
    }
    

    public Phase getPhaseById(Long id) {
        return phaseRepository.findById(id).get();
    }
    public Phase savePhase(Phase newPhase) {
	    
        return phaseRepository.save(newPhase);
    }

    public void deletePhase(Long id) {
    	phaseRepository.deleteById(id);
    }
    
    public List<Phase> getPhaseByListId(List<Long> phasesId) {

    	return phaseRepository.findPhaseByListId(phasesId);
    }
}
