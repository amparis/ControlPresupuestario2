package com.springmvc.ControlPresupuestario.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springmvc.ControlPresupuestario.repository.MenuRepository;
import com.springmvc.ControlPresupuestario.repository.RolMenuRepository;
import com.springmvc.ControlPresupuestario.model.Menu;
import com.springmvc.ControlPresupuestario.model.Perfil;
import com.springmvc.ControlPresupuestario.model.Project;
import com.springmvc.ControlPresupuestario.model.RolMenu;
import com.springmvc.ControlPresupuestario.model.RolMenuId;
import com.springmvc.ControlPresupuestario.model.UserAdm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class RolMenuService {

	@Autowired
	RolMenuRepository rolMenuRepository;
	@Autowired
    MenuRepository menuRepository; // Repositorio para los menús
    @Autowired
    UserAdmService userService;
    @Autowired
    IMyUserDetailsService userDetailsService;
	
	public  List<RolMenu> getAllRolMenus(){
		
		return rolMenuRepository.findAll();
	}
	
	public  List<RolMenu> getAllRolMenusByRoleId(){
		
	    // Obtener el usuario logueado
	    UserAdm loginUser = userService.getUser(userDetailsService.getUserDetailsService().getId());
	    // Asignar el id del usuario logueado al campo us_id del Project
	     return  this.rolMenuRepository.findAllByRoleId(loginUser.getPerfil().getId());

	}
		
	public RolMenu saveRol(RolMenu newRolMenu) {
		
		return this.rolMenuRepository.save(newRolMenu);
	}
	
	public void updateRol(long id, RolMenu newRolMenu) {
		
		//RolMenu rolMenuUpdate = this.rolMenuReposiory.findAllByRoleId(id).get(id);//ojo pendiente
		//this.rolMenuReposiory.save(rolMenuUpdate);

	}
	
	public void deleteRol(long RolId, long MenuId) {
		this.rolMenuRepository.deleteByRoleIdAndMenuId(RolId, MenuId);
	}
	
    @Transactional
    public void eliminarPermisosPorRol(Long rolId) {
        rolMenuRepository.deleteByRoleId(rolId);
    }

    @Transactional
    public void save(RolMenu rolMenu) {
        rolMenuRepository.save(rolMenu);
    }
    // Para desplegar el menu asignado
    public List<RolMenu> getRolMenusByRoleId() { 
        // Obtener los RolMenu para un rol específico
    	 UserAdm loginUser = userService.getUser(userDetailsService.getUserDetailsService().getId());
        List<RolMenu> rolMenus = rolMenuRepository.findAllByRoleId(loginUser.getPerfil().getId());

        // Crear una lista para almacenar los menús de nivel superior
        List<RolMenu> parentMenus = new ArrayList<>();

        // Agrupar los menús en jerarquía
        for (RolMenu rolMenu : rolMenus) {
            if (rolMenu.getMenu() != null && rolMenu.getMenu().getSupMenu() == null) { // Verificar que el menú no sea null
                parentMenus.add(rolMenu);
            }
        }

        // Añadir submenús a los menús de nivel superior
        for (RolMenu parent : parentMenus) {
            List<Menu> subMenus = new ArrayList<>(); // Cambiar a List<Menu>
            for (RolMenu rolMenu : rolMenus) {
                if (rolMenu.getMenu() != null && rolMenu.getMenu().getSupMenu() != null && 
                    rolMenu.getMenu().getSupMenu().getId().equals(parent.getMenu().getId())) {
                    subMenus.add(rolMenu.getMenu()); // Agregar el menú en lugar de RolMenu
                }
            }
            if (parent.getMenu() != null) { // Verificar que parent.getMenu() no sea null
                parent.getMenu().setSubMenus(subMenus); // Agregar submenús al menú padre
            }
        }

        return parentMenus; // Retornar solo los menús de nivel superior
    }
    
    public List<RolMenu> getRolMenusByRoleIdPermisos(Long id) { 

        List<RolMenu> rolMenus = rolMenuRepository.findAllByRoleId(id);

        // Crear una lista para almacenar los menús de nivel superior
        List<RolMenu> parentMenus = new ArrayList<>();

        // Agrupar los menús en jerarquía
        for (RolMenu rolMenu : rolMenus) {
            if (rolMenu.getMenu() != null && rolMenu.getMenu().getSupMenu() == null) { // Verificar que el menú no sea null
                parentMenus.add(rolMenu);
            }
        }

        // Añadir submenús a los menús de nivel superior
        for (RolMenu parent : parentMenus) {
            List<Menu> subMenus = new ArrayList<>(); // Cambiar a List<Menu>
            for (RolMenu rolMenu : rolMenus) {
                if (rolMenu.getMenu() != null && rolMenu.getMenu().getSupMenu() != null && 
                    rolMenu.getMenu().getSupMenu().getId().equals(parent.getMenu().getId())) {
                    subMenus.add(rolMenu.getMenu()); // Agregar el menú en lugar de RolMenu
                }
            }
            if (parent.getMenu() != null) { // Verificar que parent.getMenu() no sea null
                parent.getMenu().setSubMenus(subMenus); // Agregar submenús al menú padre
            }
        }

        return parentMenus; // Retornar solo los menús de nivel superior
    }
    

}
