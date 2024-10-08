package com.springmvc.ControlPresupuestario.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.springmvc.ControlPresupuestario.model.Perfil;
import com.springmvc.ControlPresupuestario.model.RolMenu;
import com.springmvc.ControlPresupuestario.service.PefilService;
import com.springmvc.ControlPresupuestario.service.RolMenuService;

@RestController
@RequestMapping("/perfil")
public class PerfilController {

	@Autowired
	PefilService perfilService;
	
	@Autowired
    private RolMenuService rolMenuService;
	
	
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
    public RedirectView guardarPermisos(//@RequestParam("rolId") Long rolId, 
							    		@RequestParam Map<String, String[]> permisos,
							    		@RequestParam(name = "_csrf", required = false) String csrfToken) {
        // Eliminar permisos previos asociados a este rol
        ///////rolMenuService.eliminarPermisosPorRol(rolId);
		 try {
        // Procesar los nuevos permisos seleccionados y guardarlos en la base de datos
        for (Map.Entry<String, String[]> entry : permisos.entrySet()) {
        	
            String key = entry.getKey();

            // Ignorar el campo _csrf
            if (key.equals("_csrf")) {
                continue;
            }
        	
            Long menuId = Long.parseLong(entry.getKey());
            String[] valoresPermisos = entry.getValue();

            // Crear un objeto RolMenu para cada permiso seleccionado
            RolMenu rolMenu = new RolMenu();
            rolMenu.setRoleId((long) 3);//rolId);
            	//System.out.println(rolId);
            rolMenu.setMenuId(menuId);
            	System.out.println(menuId);
            rolMenu.setEstado("V");
            // Asignar los permisos
		            for (String permiso : valoresPermisos) {
		                switch (permiso) {
		                    case "CREATE":
		                        rolMenu.setAcreate(true);
		                        break;
		                    case "READ":
		                        rolMenu.setAread(true);
		                        break;
		                    case "UPDATE":
		                        rolMenu.setAupdate(true);
		                        break;
		                    case "DELETE":
		                        rolMenu.setAdelete(true);
		                        break;
		                }
		            }

            // Guardar los permisos en la base de datos
            rolMenuService.save(rolMenu);
        }

        // Redirigir a la lista de roles después de guardar los permisos
        return new RedirectView("/lista-roles");
    
	 } catch (Exception e) {
	        // Log de la excepción
	        e.printStackTrace();
	        System.out.println(e);
	        // Redirigir a una página de error personalizada
	        //return new RedirectView("/error");
	        return new RedirectView("/lista-roles");
	    }
}
}
