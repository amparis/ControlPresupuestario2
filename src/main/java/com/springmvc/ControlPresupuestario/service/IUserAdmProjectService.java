package com.springmvc.ControlPresupuestario.service;

import java.util.List;

import com.springmvc.ControlPresupuestario.model.UserAdmProject;

public interface IUserAdmProjectService {

    public List<UserAdmProject> getUserAdmProjects();
    
    //public Beneficiary getBeneficiaryById(Integer id);
    
    public UserAdmProject getUserAdmProjectById(Long id);
    
	public UserAdmProject saveUserAdmProject(UserAdmProject newUserAdmProject);
	
	public void updateUserAdmProject(long id, UserAdmProject newUserAdmProject);
	
	public void deleteUserAdmProject(long id);
}
