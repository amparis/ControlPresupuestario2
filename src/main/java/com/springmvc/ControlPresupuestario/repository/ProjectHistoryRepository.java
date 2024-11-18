package com.springmvc.ControlPresupuestario.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springmvc.ControlPresupuestario.model.ProjectHistory;
import com.springmvc.ControlPresupuestario.model.RolMenu;

public interface ProjectHistoryRepository extends JpaRepository<ProjectHistory,Long> {
	
	  @Query("SELECT ph FROM ProjectHistory ph  WHERE ph.project.id = :projectId order by ph.id desc")
	  public List<ProjectHistory> findByProjectId(Long projectId);	
}
