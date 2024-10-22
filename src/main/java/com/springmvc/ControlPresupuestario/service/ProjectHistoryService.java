package com.springmvc.ControlPresupuestario.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springmvc.ControlPresupuestario.model.Project;
import com.springmvc.ControlPresupuestario.model.ProjectHistory;
import com.springmvc.ControlPresupuestario.model.UserAdm;
import com.springmvc.ControlPresupuestario.repository.ProjectHistoryRepository;
import com.springmvc.ControlPresupuestario.repository.ProjectRepository;
import com.springmvc.ControlPresupuestario.repository.UserAdmProjectRepository;
import com.springmvc.ControlPresupuestario.utility.CryptPasswordEncoder;

@Service
public class ProjectHistoryService {
	
	@Autowired
	ProjectHistoryRepository projectHistoryRepository;
	
	@Autowired
	ProjectRepository projectRepository;
	@Autowired
	UserAdmProjectRepository userAdmProjectRepository;
	
    @Autowired
    UserAdmService userService;
    
    @Autowired
    IMyUserDetailsService userDetailsService;
    
	public List<ProjectHistory> getProjectHistories(){
       return this.projectHistoryRepository.findAll();  
   }
	
	public List<ProjectHistory> getProjectHistory(long proyectId) {
		
		return this.projectHistoryRepository.findByProjectId(proyectId);
	}
	
    public ProjectHistory saveProjectHistory(ProjectHistory newProjectHistory) {
 	
    	long millis = System.currentTimeMillis();
    	newProjectHistory.setRegistrationDate(new Timestamp(millis));
    	UserAdm loginUser = userService.getUser(userDetailsService.getUserDetailsService().getId());
	    // Asignar el id del usuario logueado al campo us_id 
    	newProjectHistory.setUs_id(loginUser.getId());
    	newProjectHistory.setStatus("V");

        return this.projectHistoryRepository.save(newProjectHistory);
    }


}
