package com.springmvc.ControlPresupuestario.service;

import com.springmvc.ControlPresupuestario.model.Perfil;
import com.springmvc.ControlPresupuestario.model.Project;
import com.springmvc.ControlPresupuestario.repository.PerfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;


@Service
public class PefilService {

    @Autowired
    PerfilRepository perfilRepository;


    public List<Perfil> getRoles() {

        return this.perfilRepository.findAll();
    }
    
    public Perfil findById(Long id) {
        return perfilRepository.findById(id).orElse(null);
    }
    
    public Perfil getRol(long id) {
    	
    	return this.perfilRepository.findById(id).get();
    }
    
	public Perfil saveRol(Perfil newRol) {
		
	      long millis = System.currentTimeMillis();

	      newRol.setDateCreate(new Timestamp(millis));
	      newRol.setState("V");

		return this.perfilRepository.save(newRol);
	}
	
	public void updateRol(long id, Perfil newRol) {
		
		Perfil perfilUpdate = this.perfilRepository.findById(id).get();
		this.perfilRepository.save(perfilUpdate);

	}
	
	public void deleteRol(long id) {
		this.perfilRepository.deleteById(id);
	}

}
