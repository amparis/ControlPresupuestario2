package com.springmvc.ControlPresupuestario.service;

import com.springmvc.ControlPresupuestario.model.Project;
import com.springmvc.ControlPresupuestario.model.UserAdm;
import com.springmvc.ControlPresupuestario.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class ProjectService {
	
	@Autowired
	ProjectRepository projectRepository;
	
    @Autowired
    UserAdmService userService;
    @Autowired
    IMyUserDetailsService userDetailsService;
	
	public List<Project> getProjects(){
		
		//return this.projectRepository.findAll();//deberia listar solo los vigentes
        return this.projectRepository.findByEstado("V");  // Listar solo proyectos con estado 'v'

	}
	
	public Project getProject(long id) {
		
		return this.projectRepository.findById(id).get();
	}
	
	//public Project getProjectById(Long id);
	
	public Project saveProject(Project newProject) {
	    // Verificar si ya existe un proyecto con el mismo nombre, fecha inicio y fecha fin
	    
		boolean exists = projectRepository.existsByNombreAndFechaInicialAndFechaFin(
	            newProject.getNombre(), newProject.getFechaInicial(), newProject.getFechaFin());

	    if (exists) {
	        throw new IllegalStateException("Ya existe un proyecto con el mismo nombre, fecha de inicial y fecha de fin.");
	    }
		
		long millis = System.currentTimeMillis();
		newProject.setFechaCreacion(new Date(millis));// proy_fechacreacion
		newProject.setEstado("V");
	    // Obtener el usuario logueado
	    UserAdm loginUser = userService.getUser(userDetailsService.getUserDetailsService().getId());
	    // Asignar el id del usuario logueado al campo us_id del Project
	    newProject.setUs_id(loginUser.getId());
   
		return this.projectRepository.save(newProject);
	}
	
	public void updateProject(long id, Project newProject) {
		
		Project ProjectUpdate = this.projectRepository.findById(id).get();
		
		if (this.projectRepository.findById(id).isPresent()) {
			
			long  millis = System.currentTimeMillis();
			
			if(newProject.getNombre() != null) ProjectUpdate.setNombre(newProject.getNombre());
			
			//if(newProject.getDescripcion()!= null) ProjectUpdate.setDescripcion(newProject.getDescripcion());
			
			//if(newProject.getMontoContrato()!= null) ProjectUpdate.setMontoContrato(newProject.getMontoContrato());
			
			//newProject.setFechaActualizacion(new Date(millis));
			
		    // Obtener el usuario logueado
		    UserAdm loginUser = userService.getUser(userDetailsService.getUserDetailsService().getId());
		    // Asignar el id del usuario logueado al campo us_id del Project
		    newProject.setUs_id(loginUser.getId());

			this.projectRepository.save(ProjectUpdate);
		}
		
		
		
	}
	
	public void deleteProject(long id) {
		
		this.projectRepository.deleteById(id);
	}
	

}
