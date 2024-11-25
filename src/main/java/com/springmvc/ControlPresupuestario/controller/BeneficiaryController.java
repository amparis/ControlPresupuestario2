package com.springmvc.ControlPresupuestario.controller;

import com.springmvc.ControlPresupuestario.model.Beneficiary;
import com.springmvc.ControlPresupuestario.service.BeneficiaryService;
import com.springmvc.ControlPresupuestario.service.CountryService;
import com.springmvc.ControlPresupuestario.service.IMyUserDetailsService;
import com.springmvc.ControlPresupuestario.service.PefilService;
import com.springmvc.ControlPresupuestario.service.RolMenuService;
import com.springmvc.ControlPresupuestario.service.UserAdmService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/beneficiarios")
public class BeneficiaryController {

	@Autowired
    private BeneficiaryService beneficiaryService;
    @Autowired
    PefilService pefilService;
    @Autowired
    IMyUserDetailsService userDetailsService;    
    //AGREGANDO USUARIO
    @Autowired
    UserAdmService userService;
    @Autowired
    RolMenuService rolMenuService;
	@Autowired
	private CountryService countryService;
	
    // Listar Beneficiarios
    @GetMapping("/lista-beneficiarios")
    public String getBeneficiaries(Model model) {
        model.addAttribute("loginUser", this.userService.getUser(userDetailsService.getUserDetailsService().getId()));
        model.addAttribute("menuRoles",this.rolMenuService.getAllRolMenusByRoleId());
        model.addAttribute("menuRoles2",this.rolMenuService.getRolMenusByRoleId());//para jalar el MENU
        
        
        
        model.addAttribute("beneficiaries", beneficiaryService.getAllBeneficiaries());
        
        return "lista_beneficiarios"; // Nombre de la vista Thymeleaf
    }
    
    @GetMapping("/ver-beneficiario/{id}")
    public String getBeneficiary(@PathVariable("id") Integer id, Model model) {
        //model.addAttribute("loginUser",
       //         this.userService.getUser(userDetailsService.getUserDetailsService().getId()));

        model.addAttribute("beneficiary", beneficiaryService.getBeneficiaryById(id));
        //model.addAttribute("menuRoles2",this.rolMenuService.getRolMenusByRoleIdPermisos(userService.getUser(id).getPerfil().getId()));// Para mostrar el menu de acuerdo al ID del usuario seleccionado
        model.addAttribute("projectsActivos", beneficiaryService.getProjectsByBeneficiaryId(id));
        return "ver_beneficiario";
    }
    // Mostrar el formulario de registro de un nuevo Beneficiario
    @GetMapping("/registro-beneficiario")
    public String showRegisterForm(Model model) {
       
        model.addAttribute("beneficiary", new Beneficiary());
	    model.addAttribute("paisesBeneficiary", countryService.getAllCountries()); 
        return "registro_beneficiario"; // Nombre de la vista Thymeleaf
    }

    // Guardar un nuevo Beneficiario
    @PostMapping("/guardar-beneficiario/{id}")
    public String saveBeneficiary(@PathVariable("id") Integer id,@ModelAttribute Beneficiary beneficiary, RedirectAttributes redirectAttributes) {
    	String nom = null;
    	String ap = null; String doc = null; String mail = null;
    	if(beneficiary.getTipo()=="Staff") {
        	// datos obligatorios documento, fisrt name, lastname, email
            if (beneficiary.getNombres()==null || beneficiary.getNombres()=="") {nom = " enter the firstname."; } 
            if (beneficiary.getApellidos()==null || beneficiary.getApellidos()=="") {ap = " enter the lastname." ;}
            if (beneficiary.getDocumento()==null || beneficiary.getDocumento()=="") {doc = " enter the document number." ;}
            if (beneficiary.getEmail()==null || beneficiary.getEmail()=="") {mail = " enter the email." ;}
            
                redirectAttributes.addFlashAttribute("message", "Please,"+nom+ap+doc+mail);
                redirectAttributes.addFlashAttribute("messageType", "error");
                return (id > 0) ? "redirect:/expenses/registro-egreso/" + id : "redirect:/beneficiarios/registro-beneficiario";
           
    	}
    	else if(beneficiary.getTipo()=="Legal Entity")   	{
    		// datos obligatorios numero de idetificacion, company name
            if (beneficiary.getRazonSocial()==null || beneficiary.getRazonSocial()=="") {nom = " enter the name of company."; } 
            if (beneficiary.getDocumento()==null || beneficiary.getDocumento()=="") {doc = " enter the document number." ;}
            
            redirectAttributes.addFlashAttribute("message", "Please,"+nom+doc);
            redirectAttributes.addFlashAttribute("messageType", "error");
            return (id > 0) ? "redirect:/expenses/registro-egreso/" + id : "redirect:/beneficiarios/registro-beneficiario";
    	}
    	else {
    		
    	}

    	try {
            beneficiaryService.saveBeneficiary(beneficiary);
            redirectAttributes.addFlashAttribute("message", "Beneficiary saved successfully!");
            redirectAttributes.addFlashAttribute("messageType", "success");
            return (id > 0) ? "redirect:/expenses/registro-egreso/" + id : "redirect:/beneficiarios/lista-beneficiarios";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Error al guardar el beneficiario: " + e.getMessage());
            redirectAttributes.addFlashAttribute("messageType", "error");
            return (id > 0) ? "redirect:/expenses/registro-egreso/" + id : "redirect:/beneficiarios/registro-beneficiario";
        }
    }

    // Modificar un Beneficiario
    @GetMapping("/modificar-beneficiario/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("beneficiary", beneficiaryService.getBeneficiaryById(id));
        model.addAttribute("paisesBeneficiary", countryService.getAllCountries()); 
        return "registro_beneficiario"; // Reutilizamos el formulario de registro
    }

    // Eliminar un Beneficiario
    @GetMapping("/eliminar-beneficiario/{id}")
    public String deleteBeneficiary(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            beneficiaryService.deleteBeneficiary(id);
            redirectAttributes.addFlashAttribute("message", "Beneficiario eliminado exitosamente.");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Error al eliminar el beneficiario: " + e.getMessage());
            redirectAttributes.addFlashAttribute("messageType", "error");
        }
        return "redirect:/beneficiarios/lista-beneficiarios";
    }
}
