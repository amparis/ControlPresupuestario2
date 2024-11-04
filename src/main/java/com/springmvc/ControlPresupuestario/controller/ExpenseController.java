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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import com.springmvc.ControlPresupuestario.model.Beneficiary;
import com.springmvc.ControlPresupuestario.model.Expense;
import com.springmvc.ControlPresupuestario.model.Scale;
import com.springmvc.ControlPresupuestario.service.AccountService;
import com.springmvc.ControlPresupuestario.service.BeneficiaryService;
import com.springmvc.ControlPresupuestario.service.CountryService;
import com.springmvc.ControlPresupuestario.service.ExpenditureClassificationService;
import com.springmvc.ControlPresupuestario.service.ExpenseService;
import com.springmvc.ControlPresupuestario.service.IMyUserDetailsService;
import com.springmvc.ControlPresupuestario.service.IncomeService;
import com.springmvc.ControlPresupuestario.service.MenuService;
import com.springmvc.ControlPresupuestario.service.PefilService;
import com.springmvc.ControlPresupuestario.service.ProjectPhaseService;
import com.springmvc.ControlPresupuestario.service.ProjectService;
import com.springmvc.ControlPresupuestario.service.RolMenuService;
import com.springmvc.ControlPresupuestario.service.ScaleService;
import com.springmvc.ControlPresupuestario.service.UserAdmService;

@Controller
@RequestMapping("/expenses")
public class ExpenseController {

	@Autowired
	ExpenseService expenseService;
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
	@Autowired
	CountryService countryService; 
	@Autowired
	BeneficiaryService beneficiaryService;
	@Autowired
	ExpenditureClassificationService expenditureClassificationService;
	@Autowired
	ProjectPhaseService projectPhaseService;
	@Autowired
	ScaleService scaleService;
    
    @GetMapping("/registro-egreso/{id}")
    public String showRegisterFormIngreso( @PathVariable("id") long projectId, Model model) {
        model.addAttribute("loginUser", this.userService.getUser(userDetailsService.getUserDetailsService().getId()));

        model.addAttribute("menuRoles",this.rolMenuService.getAllRolMenusByRoleId());
        model.addAttribute("menuRoles2",this.rolMenuService.getRolMenusByRoleId());
        model.addAttribute("project", projectService.getProject(projectId)); 
        model.addAttribute("expense", new Expense());
        model.addAttribute("beneficiaries", beneficiaryService.getAllBeneficiariesEstado("V"));
        model.addAttribute("beneficiary", new Beneficiary());
        model.addAttribute("fasesProyecto",projectPhaseService.getProjectPhasesByProyectoId(projectId));
        model.addAttribute("cuentas", accountService.getAccounts());
        model.addAttribute("paises", countryService.getAllCountries()); 
        model.addAttribute("escalas", scaleService.getAllScalesEstado("V"));
        model.addAttribute("scale", new Scale());
        model.addAttribute("paisesBeneficiary", countryService.getAllCountries()); 
        model.addAttribute("expenditureClassifications",expenditureClassificationService.getExpenditureClassifications());
               

          return "registro_egreso";
    }
    // Guardar
    
    @PostMapping("/guardar-egreso")
    public String saveExpense(@ModelAttribute Expense expense,
            @RequestParam(required = false) Long id,
            @RequestParam(value = "inputproyectoId", required = false) long projectId,

            RedirectAttributes redirectAttributes) {
    	
    	System.out.println(" print   id  "+projectId);
        // Intentar guardar el proyecto si todas las validaciones pasaron
        try {
        	
        	expenseService.saveExpense(expense);

            redirectAttributes.addFlashAttribute("message", "desembolso guardado exitosamente.");
            redirectAttributes.addFlashAttribute("messageType", "success");
            return "redirect:/ejecucion/registro-ejecucion/"+projectId;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Error al guardar el desembolso: " + e.getMessage());
            redirectAttributes.addFlashAttribute("messageType", "error");
            return "redirect:/expenses/registro-egreso/"+projectId;
        }
    }

}
