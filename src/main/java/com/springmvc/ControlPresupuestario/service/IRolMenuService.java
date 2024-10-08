package com.springmvc.ControlPresupuestario.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.springmvc.ControlPresupuestario.model.RolMenuId;
import com.springmvc.ControlPresupuestario.model.Perfil;
import com.springmvc.ControlPresupuestario.model.RolMenu;

public interface IRolMenuService {
	
   
	public  List<RolMenu> getAllRolMenus();
	
	public  List<RolMenu> getAllRolMenusByRoleId();
	//public Optional<RolMenu> getRolMenuById(Long rolId, Long menuId);
	
	public RolMenu saveRolMenu(RolMenu newRolMenu);
	
	public void updateRolMenu(long id, RolMenu newRolMenu);
	
	public void deleteRolMenu(long RolId, long MenuId);
	
	public void eliminarPermisosPorRol(Long rolId);
}
