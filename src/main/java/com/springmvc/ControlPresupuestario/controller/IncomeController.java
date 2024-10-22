package com.springmvc.ControlPresupuestario.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.springmvc.ControlPresupuestario.model.Income;
import com.springmvc.ControlPresupuestario.model.Perfil;
import com.springmvc.ControlPresupuestario.model.Project;
import com.springmvc.ControlPresupuestario.service.AccountService;
import com.springmvc.ControlPresupuestario.service.IMyUserDetailsService;
import com.springmvc.ControlPresupuestario.service.IncomeService;
import com.springmvc.ControlPresupuestario.service.MenuService;
import com.springmvc.ControlPresupuestario.service.PefilService;
import com.springmvc.ControlPresupuestario.service.ProjectService;
import com.springmvc.ControlPresupuestario.service.RolMenuService;
import com.springmvc.ControlPresupuestario.service.UserAdmService;

@Controller
@RequestMapping("/incomes")
public class IncomeController {

	@Autowired
	IncomeService incomeService;
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
    @Autowired
    ProjectService projectService;

    @GetMapping("/lista-ingresos")
    public String getIngresos(Model model) {

        model.addAttribute("loginUser",
                this.userService.getUser(userDetailsService.getUserDetailsService().getId()));
        model.addAttribute("menuRoles",this.rolMenuService.getAllRolMenusByRoleId());
        model.addAttribute("menuRoles2",this.rolMenuService.getRolMenusByRoleId());
        model.addAttribute("incomes", this.incomeService.getIncomes());

        return "lista_ingresos";
    }


    @GetMapping("/registro-desembolso/{id}")
    public String showRegisterFormIngreso( @PathVariable("id") long projectId, Model model) {
        model.addAttribute("loginUser",
                this.userService.getUser(userDetailsService.getUserDetailsService().getId()));
        model.addAttribute("menuRoles",this.rolMenuService.getAllRolMenusByRoleId());
        model.addAttribute("menuRoles2",this.rolMenuService.getRolMenusByRoleId());
        //System.out.println("ID DEL PY "+projectId); 
        model.addAttribute("project", projectService.getProject(projectId)); 
        model.addAttribute("cuentas", accountService.getAccounts());
        model.addAttribute("income", new Income());

          return "registro_desembolso";
    }
    // Guardar
    @PostMapping("/guardar-desembolso")
    public String saveIncome(@ModelAttribute Income income,
            @RequestParam(required = false) Long id,
            @RequestParam(value = "inputproyectoId", required = false) long projectId,

            RedirectAttributes redirectAttributes) {
    	
    	System.out.println(" print   id  "+projectId);
        // Intentar guardar el proyecto si todas las validaciones pasaron
        try {
        	
        	incomeService.saveIncome(income, projectId);

            redirectAttributes.addFlashAttribute("message", "desembolso guardado exitosamente.");
            redirectAttributes.addFlashAttribute("messageType", "success");
            return "redirect:/ejecucion/registro-ejecucion/"+projectId;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Error al guardar el desembolso: " + e.getMessage());
            redirectAttributes.addFlashAttribute("messageType", "error");
            return "redirect:/incomes/registro-desembolso/"+projectId;
        }
    }
    


    @GetMapping("/eliminar-ingreso/{id}")
    public String deleteIngreso(@PathVariable("id") Integer id, Model model,RedirectAttributes redirectAttributes) {

   	 try {
	        this.incomeService.deleteIncome(id);
	
	        model.addAttribute("incomes", this.incomeService.getIncomes());
	        
	     // Agregar el mensaje de éxito
	        redirectAttributes.addFlashAttribute("message", "El ingreso fue eliminado exitosamente.");
	        redirectAttributes.addFlashAttribute("messageType", "success");
	
	        return "redirect:/lista-ingresos";
 	 } catch (Exception e) {
 	        redirectAttributes.addFlashAttribute("message", "Error al eliminar el ingreso: " + e.getMessage());
 	        redirectAttributes.addFlashAttribute("messageType", "error");
 	        return "redirect:/lista-ingresos";
 	    }
    }
}
