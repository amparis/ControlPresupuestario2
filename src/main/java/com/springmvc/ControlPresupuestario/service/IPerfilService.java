package com.springmvc.ControlPresupuestario.service;

import com.springmvc.ControlPresupuestario.model.Perfil;

import java.util.List;

public interface IPerfilService {

    public List<Perfil> getRoles();
    
    public Perfil getRol();
    
    public Perfil findById(Long id);
    
	public Perfil saveRol(Perfil newRol);
	
	public void updateRol(long id, Perfil newRol);
	
	public void deleteRol(long id);
}
