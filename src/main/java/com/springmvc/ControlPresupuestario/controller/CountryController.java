package com.springmvc.ControlPresupuestario.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.springmvc.ControlPresupuestario.service.CountryService;
import com.springmvc.ControlPresupuestario.service.IMyUserDetailsService;
import com.springmvc.ControlPresupuestario.service.PefilService;
import com.springmvc.ControlPresupuestario.service.RolMenuService;
import com.springmvc.ControlPresupuestario.service.UserAdmService;

@Controller
@RequestMapping("/paises")
public class CountryController {

	@Autowired
	private CountryService countryService;
	
    @Autowired
    PefilService pefilService;
    @Autowired
    IMyUserDetailsService userDetailsService;  
    @Autowired
    UserAdmService userService;
    @Autowired
    RolMenuService rolMenuService;
    
    // Listar paises
    @GetMapping("/lista-paises")
    public String getBeneficiaries(Model model) {
        model.addAttribute("loginUser", this.userService.getUser(userDetailsService.getUserDetailsService().getId()));
        model.addAttribute("menuRoles",this.rolMenuService.getAllRolMenusByRoleId());
        model.addAttribute("menuRoles2",this.rolMenuService.getRolMenusByRoleId());//para jalar el MENU
        
        
        
        model.addAttribute("paises", countryService.getAllCountries());
        
        return "lista_paises"; // Nombre de la vista Thymeleaf
    }
}
