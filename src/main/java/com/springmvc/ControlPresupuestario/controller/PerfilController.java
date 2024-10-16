package com.springmvc.ControlPresupuestario.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.springmvc.ControlPresupuestario.model.Menu;
import com.springmvc.ControlPresupuestario.model.Perfil;
import com.springmvc.ControlPresupuestario.model.RolMenu;
import com.springmvc.ControlPresupuestario.service.MenuService;
import com.springmvc.ControlPresupuestario.service.PefilService;
import com.springmvc.ControlPresupuestario.service.RolMenuService;

@RestController
@RequestMapping("/perfil")
public class PerfilController {

	@Autowired
	PefilService perfilService;
	
	@Autowired
    private RolMenuService rolMenuService;
	
	@Autowired
    private MenuService menuService;
	
	@PostMapping("")
	public RedirectView postPerfil(@ModelAttribute Perfil newPerfil) {
		
		/*
		 * this.perfilService.saveRol(newPerfil);		
		return new RedirectView("/lista-roles");*/
		
        // Guardar el nuevo rol
        Perfil savedPerfil = perfilService.saveRol(newPerfil);

        // Redirigir a la página de registro con el ID del rol creado
        return new RedirectView("/registro-rol?rolId=" + savedPerfil.getId());
	}

   @PostMapping("/guardar-permisos")
	public RedirectView  guardarPermisos(@RequestParam("rolId") Long rolId,
									@RequestParam Map<String, String> permisos) {
		System.out.println("*** **** ENTRANDO EN EL METODO guardarPermisos con rolId: " + rolId);
        
		try {
		// Obtener el perfil (rol) por el rolId

		Perfil perfil = perfilService.findById(rolId);
	    if (perfil == null) {
	        System.out.println("El perfil es nulo para el rolId: " + rolId);
	        return new RedirectView("/lista-roles"); // o maneja el error como consideres
	    }

	    // Procesar los permisos del rol
	    permisos.forEach((key, value) -> {
	    	System.out.println("Clave procesada: " + key + ", Valor: " + value);
	    	/////////////////////////////////////
	    	String[] parts = key.split("\\[|\\]");
	    	System.out.println("Parts: " + Arrays.toString(parts));
	    	
	    	if (parts.length > 1) {
	    	    try {
	    	        Long menuId = Long.parseLong(parts[1]);
	    	        String permiso = parts[3];
	    	        System.out.println("** PERMISO>>:   " + parts[3]);
	    	        
		            // Asignar los permisos correspondientes
		            RolMenu rolMenu = new RolMenu();
		            rolMenu.setRoleId(rolId);//.setRole(perfil);
		            
		            Menu menu = menuService.getMenu(menuId);
		            rolMenu.setMenuId(menuId);//m.setMenu(menu);
		         // Inicializa todos los permisos a false por defecto
		            rolMenu.setAcreate(false);
		            rolMenu.setAread(false);
		            rolMenu.setAupdate(false);
		            rolMenu.setAdelete(false);
		            // Asignar los permisos a cada acción (CREATE, READ, UPDATE, DELETE)
		         // Asignar los permisos a cada acción (CREATE, READ, UPDATE, DELETE)
		            if ("CREATE".equals(permiso)) {
		                rolMenu.setAcreate("on".equals(value));
		                if ("on".equals(value)) {
		                    //System.out.println("permiso create: " + value);
		                }
		            }

		            if ("READ".equals(permiso)) {
		                rolMenu.setAread("on".equals(value));
		                if ("on".equals(value)) {
		                   // System.out.println("permiso READ: " + value);
		                }
		            }

		            if ("UPDATE".equals(permiso)) {
		                rolMenu.setAupdate("on".equals(value));
		                if ("on".equals(value)) {
		                    //System.out.println("permiso UPDATE: " + value);
		                }
		            }

		            if ("DELETE".equals(permiso)) {
		                rolMenu.setAdelete("on".equals(value));
		                if ("on".equals(value)) {
		                    //System.out.println("permiso DELETE: " + value);
		                }
		            }
		            
		            rolMenu.setEstado("V");
		            // Guardar los permisos asignados al rol y menú
		            rolMenuService.save(rolMenu);
	    	    } catch (NumberFormatException e) {
	    	        System.out.println("Error al parsear menuId: " + parts[1]);
	    	    }
	    	}
///////////////////////
	    });

	    // Redirigir a la vista después de guardar los permisos
	    return new RedirectView("/lista-roles");
	    
	    }catch(Exception e) {
	    	System.out.println("el error al guardar permisos  "+e);
	    	return new RedirectView("/lista-roles");
	    }
	}

}
