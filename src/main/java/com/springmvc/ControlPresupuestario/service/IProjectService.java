package com.springmvc.ControlPresupuestario.service;

import com.springmvc.ControlPresupuestario.model.Project;

import java.util.List;

public interface IProjectService {

	public List<Project> getProjects();
	
	public Project getProject(long id);
	
	//public Project getProjectById(Long id);
	
	public Project saveProject(Project newProject);
	
	public void updateProject(long id, Project newProject);
	
	public void deleteProject(long id);
}
