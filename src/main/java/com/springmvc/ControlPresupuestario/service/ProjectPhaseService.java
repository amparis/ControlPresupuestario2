package com.springmvc.ControlPresupuestario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springmvc.ControlPresupuestario.model.ProjectPhase;
import com.springmvc.ControlPresupuestario.repository.ProjectPhaseRepository;

@Service
public class ProjectPhaseService {

	@Autowired
	ProjectPhaseRepository projectPhaseRepository;
	
	public List<ProjectPhase> getProjectPhases(){
		//return this.projectRepository.findAll();//deberia listar solo los vigentes
        return this.projectPhaseRepository.findAll();  // Listar solo proyectos con estado 'v'
	}
	
	public List<ProjectPhase> getProjectPhasesByProyectoId(long projectId){
		//return this.projectRepository.findAll();//deberia listar solo los vigentes
        return this.projectPhaseRepository.findAllByProyectoId(projectId);
	}
	public ProjectPhase getProjectPhase(long id) {
		
		return this.projectPhaseRepository.findById(id).get();
	}
	
	//public Project getProjectById(Long id);
	
	public ProjectPhase saveProjectPhase(ProjectPhase newProjectPhase) {
		 return projectPhaseRepository.save(newProjectPhase);
	} 
			

	public void deleteProjectPhase(long id) {
				
			this.projectPhaseRepository.deleteById(id);
	}
}
