package com.springmvc.ControlPresupuestario.controller;


import com.springmvc.ControlPresupuestario.model.Project;
import com.springmvc.ControlPresupuestario.model.RolMenu;
import com.springmvc.ControlPresupuestario.model.UserAdm;
import com.springmvc.ControlPresupuestario.model.Perfil;
import com.springmvc.ControlPresupuestario.service.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class FrontController {


    @Autowired
    PefilService pefilService;
    @Autowired
    IMyUserDetailsService userDetailsService;
    

    //AGREGANDO USUARIO
    @Autowired
    UserAdmService userService;
    
    @Autowired
    MenuService menuService;
    
    @Autowired
    RolMenuService rolMenuService;
    
    @Autowired
    AccountService accountService;

    

    @GetMapping(value = {"/login"})
    public String login(Model model) {

        return "login";
    }

    @GetMapping(value = {"/", "/home"})
    public String home(Model model) {

        model.addAttribute("loginUser",
                this.userService.getUser(userDetailsService.getUserDetailsService().getId()));

        model.addAttribute("menuRoles2",this.rolMenuService.getRolMenusByRoleId());// Para desplegar el menu asignado
        return "index";
    }
 

    //-------- mapping para usuarios en front

    @GetMapping("/lista-usuarios")
    public String getUsers(Model model) {

        model.addAttribute("loginUser",
                this.userService.getUser(userDetailsService.getUserDetailsService().getId()));
        model.addAttribute("menuRoles",this.rolMenuService.getAllRolMenusByRoleId());
        model.addAttribute("menuRoles2",this.rolMenuService.getRolMenusByRoleId());
        model.addAttribute("userAdms", this.userService.getUsers());

        return "lista_usuarios";
    }

    @GetMapping("/registro-usuario")
    public String postUser(Model model) {
        model.addAttribute("loginUser",userDetailsService.getUserDetailsService());
        model.addAttribute("userAdm", new UserAdm());
        model.addAttribute("roles",this.pefilService.getRoles());
        
        // Si hay un mensaje de error, lo añadimos al modelo
        if (model.containsAttribute("error")) {
            model.addAttribute("error", model.asMap().get("error"));
        }
        return "registro_usuario";
    }

    @GetMapping("/modificar-usuario/{id}")
    public String patchUser(@PathVariable("id") long id, Model model) {

        model.addAttribute("userAdm", userService.getUser(id));

        //model.addAttribute("enterprises", this.enterpriseService.getEnterprises());

        model.addAttribute("roles",this.pefilService.getRoles());

        this.userService.updateUser(id, userService.getUser(id));

        return "registro_usuario";
    }
    
    @GetMapping("/ver-usuario/{id}")
    public String getUser(@PathVariable("id") long id, Model model) {

        model.addAttribute("userAdm", userService.getUser(id));
        model.addAttribute("menuRoles2",this.rolMenuService.getRolMenusByRoleIdPermisos(userService.getUser(id).getPerfil().getId()));// Para mostrar el menu de acuerdo al ID del usuario seleccionado

        model.addAttribute("roles",this.pefilService.getRoles());

        this.userService.updateUser(id, userService.getUser(id));

        return "ver_usuario";
    }
    
    @GetMapping("/eliminar-usuario/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {

        this.userService.deleteUser(id);

        model.addAttribute("userAdm", this.userService.getUsers());

        return "lista_usuarios";
    }

    //-------- mapping para Roles en front

    @GetMapping("/lista-roles")
    public String getRoles(Model model) {

        model.addAttribute("loginUser",
                this.userService.getUser(userDetailsService.getUserDetailsService().getId()));
        model.addAttribute("menuRoles",this.rolMenuService.getAllRolMenusByRoleId());
        model.addAttribute("menuRoles2",this.rolMenuService.getRolMenusByRoleId());
        model.addAttribute("roles", this.pefilService.getRoles());

        return "lista_roles";
    }

    @GetMapping("/registro-rol")
    public String postRol(@RequestParam(value = "rolId", required = false) Long rolId, Model model) {
        // Obtener el usuario logueado
        model.addAttribute("loginUser", this.userService.getUser(userDetailsService.getUserDetailsService().getId()));

        // Cargar los menús
        //////List<Menu> menus = menuService.getMenusVigentes(); // or another method to get the menus
       ////// model.addAttribute("menus", menus); // Add it to the model
        model.addAttribute("menus", this.menuService.getMenusVigentes());

        // Si se ha guardado un rol, obtener el rol guardado para mostrarlo
        if (rolId != null) {
            Perfil perfilGuardado = pefilService.findById(rolId);
            model.addAttribute("perfilGuardado", perfilGuardado);
        }

        // Preparar un nuevo objeto Perfil para el formulario
        model.addAttribute("perfil", new Perfil());
        // Agregar rolId al modelo para que esté disponible en la vista
        model.addAttribute("rolId", rolId);
        // Si hay un mensaje de error, lo añadimos al modelo
        if (model.containsAttribute("error")) {
            model.addAttribute("error", model.asMap().get("error"));
        }
        return "registro_rol";
    }

    @GetMapping("/modificar-rol/{id}")
    public String patchRol(@PathVariable("id") long rolId, Model model) {

//        model.addAttribute("userAdm", userService.getUser(id));
        Perfil perfilGuardado = pefilService.findById(rolId);
        model.addAttribute("perfilGuardado", perfilGuardado);
        
        model.addAttribute("perfil", pefilService.getRol(rolId));
        model.addAttribute("menus", this.menuService.getMenusVigentes());
        // Agregar rolId al modelo para que esté disponible en la vista
        
        this.pefilService.updateRol(rolId, pefilService.getRol(rolId));

        return "registro_rol";
    }

    @GetMapping("/eliminar-rol/{id}")
    public String deleteRol(@PathVariable("id") long id, Model model) {
        model.addAttribute("loginUser", this.userService.getUser(userDetailsService.getUserDetailsService().getId()));
        this.pefilService.deleteRol(id);

        model.addAttribute("roles", this.pefilService.getRoles());
        //model.addAttribute("menuRoles",this.rolMenuService.getAllRolMenusByRoleId());
        model.addAttribute("menuRoles",this.rolMenuService.getAllRolMenus());
        
        return "redirect:/lista-roles";
    }

    





}
