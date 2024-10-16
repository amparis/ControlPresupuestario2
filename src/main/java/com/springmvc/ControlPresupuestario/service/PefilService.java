package com.springmvc.ControlPresupuestario.service;

import com.springmvc.ControlPresupuestario.model.Perfil;
import com.springmvc.ControlPresupuestario.model.Project;
import com.springmvc.ControlPresupuestario.repository.PerfilRepository;
import com.springmvc.ControlPresupuestario.repository.RolMenuRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.springmvc.ControlPresupuestario.exception.CustomException;


import java.sql.Timestamp;
import java.util.List;


@Service
public class PefilService {

    @Autowired
    PerfilRepository perfilRepository;
    
    @Autowired
    RolMenuRepository rolMenuRepository;


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
	
	@Transactional
	public void deleteRol(long id) {
		 try { // Primero, elimine las entradas de RolMenu por id de rol
		//this.perfilRepository.deleteById(id);
	    // Elimina las relaciones de rol en la tabla rol_menu
		    rolMenuRepository.deleteByRoleId(id);   
		// Elimina el rol de la tabla tbl_rol
		    perfilRepository.deleteById(id); 
		} catch (DataIntegrityViolationException e) { 
			System.out.println(e);
			// Manejar excepciones específicas relacionadas con problemas de integridad de la base de datos 
			throw new CustomException("No se puede eliminar el rol porque aún se hace referencia a él en otras tablas", e); 
			
			} 
		 catch (Exception e) { // Manejar excepciones generales 
			 System.out.println(e);
			 throw new CustomException("Se produjo un error al intentar eliminar el rol", e); 
			 }
		 }	


}
