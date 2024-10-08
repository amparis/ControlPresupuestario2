package com.springmvc.ControlPresupuestario.repository;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.springmvc.ControlPresupuestario.model.RolMenu;
import com.springmvc.ControlPresupuestario.model.RolMenuId;

public interface RolMenuRepository extends JpaRepository<RolMenu,RolMenuId>{

	  //public RolMenu findByRoleId(Long roleId);
	 @Query("SELECT rm FROM RolMenu rm JOIN FETCH rm.menu m WHERE rm.id.roleId = :roleId ORDER BY m.orden")
	  public List<RolMenu> findAllByRoleId(Long roleId);
	  
	  @Query("SELECT rm FROM RolMenu rm JOIN FETCH rm.menu m WHERE rm.id.roleId = :roleId order by m.orden")
	  public List<RolMenu> findAllByRoleIdWithMenu(Long roleId);	
	  
	  public void deleteByRoleIdAndMenuId(Long roleId, Long menuId);
	 
	  public void deleteByRoleId(Long roleId);

}
