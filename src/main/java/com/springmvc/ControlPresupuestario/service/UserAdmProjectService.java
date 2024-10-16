package com.springmvc.ControlPresupuestario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springmvc.ControlPresupuestario.model.UserAdmProject;
import com.springmvc.ControlPresupuestario.repository.UserAdmProjectRepository;

@Service
public class UserAdmProjectService {

	@Autowired
    private UserAdmProjectRepository userAdmProjectRepository;
    @Autowired
    UserAdmService userService;
    @Autowired
    IMyUserDetailsService userDetailsService;
	
    public List<UserAdmProject> getUserAdmProjects(){
    	return userAdmProjectRepository.findAll();
    }
    
    //public Beneficiary getBeneficiaryById(Integer id);
    
    public UserAdmProject getUserAdmProjectById(Long id) {
    	return userAdmProjectRepository.findById(id).get();
    }
    
	public UserAdmProject saveUserAdmProject(UserAdmProject newUserAdmProject) {
		//Verificar si ya existe
		newUserAdmProject.setEstado("V");
		return userAdmProjectRepository.save(newUserAdmProject);
	}
	
	//public void updateUserAdmProject(long id, UserAdmProject newUserAdmProject);
	
	public void deleteUserAdmProject(long id) {
		userAdmProjectRepository.deleteById(id);
	}

}
