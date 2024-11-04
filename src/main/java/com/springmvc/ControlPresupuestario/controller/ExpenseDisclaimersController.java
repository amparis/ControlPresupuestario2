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
import com.springmvc.ControlPresupuestario.model.ExpenseDisclaimers;
import com.springmvc.ControlPresupuestario.model.Scale;
import com.springmvc.ControlPresupuestario.service.AccountService;
import com.springmvc.ControlPresupuestario.service.ExpenseCategoryService;
import com.springmvc.ControlPresupuestario.service.ExpenseDisclaimersService;
import com.springmvc.ControlPresupuestario.service.ExpenseService;
import com.springmvc.ControlPresupuestario.service.IMyUserDetailsService;
import com.springmvc.ControlPresupuestario.service.PefilService;
import com.springmvc.ControlPresupuestario.service.ProjectPhaseService;
import com.springmvc.ControlPresupuestario.service.ProjectService;
import com.springmvc.ControlPresupuestario.service.RolMenuService;
import com.springmvc.ControlPresupuestario.service.UnitOfMeasurementService;
import com.springmvc.ControlPresupuestario.service.UserAdmService;

@Controller
@RequestMapping("/gastos")
public class ExpenseDisclaimersController {

	@Autowired
	ExpenseService expenseService;
    @Autowired
    PefilService pefilService;
    @Autowired
    UserAdmService userService;
    @Autowired
    IMyUserDetailsService userDetailsService;
    @Autowired
    RolMenuService rolMenuService;
    @Autowired
    AccountService accountService;
    @Autowired
    ProjectService projectService;
    
	@Autowired
	ProjectPhaseService projectPhaseService;
	
	@Autowired
	ExpenseDisclaimersService expenseDisclaimersService;
	@Autowired
	UnitOfMeasurementService unitOfMeasurementService;
	@Autowired
	ExpenseCategoryService expenseCategoryService;

	   @GetMapping("/lista-descargos/{id}")
	    public String showListDescargo( @PathVariable("id") long expenseId, Model model) {
	        model.addAttribute("loginUser", this.userService.getUser(userDetailsService.getUserDetailsService().getId()));
	        model.addAttribute("menuRoles",this.rolMenuService.getAllRolMenusByRoleId());
	        model.addAttribute("menuRoles2",this.rolMenuService.getRolMenusByRoleId());
	        
	        model.addAttribute("project", projectService.getProject(expenseService.getExpense(expenseId).getProyectoFase().getProyecto().getId())); 
	        model.addAttribute("expenseSelected", expenseService.getExpense(expenseId));
	        model.addAttribute("fasesProyecto",projectPhaseService.getProjectPhasesByProyectoId(expenseService.getExpense(expenseId).getProyectoFase().getProyecto().getId()));
	        model.addAttribute("cuentas", accountService.getAccounts());
	        model.addAttribute("gastoDescargos", expenseDisclaimersService.getExpenseDisclaimersByExpenseId(expenseId));
	        model.addAttribute("descargo", new ExpenseDisclaimers());
               

	          return "lista_descargos";
	    }
	   
	   @GetMapping("/registro-descargo/{id}")
	    public String showRegisterFormDescargo( @PathVariable("id") long expenseId, Model model) {
	        model.addAttribute("loginUser", this.userService.getUser(userDetailsService.getUserDetailsService().getId()));
	        model.addAttribute("menuRoles",this.rolMenuService.getAllRolMenusByRoleId());
	        model.addAttribute("menuRoles2",this.rolMenuService.getRolMenusByRoleId());
	        model.addAttribute("expenseSelected",expenseService.getExpense(expenseId));
	        model.addAttribute("project", projectService.getProject(expenseService.getExpense(expenseId).getProyectoFase().getProyecto().getId())); 
	        model.addAttribute("fasesProyecto",projectPhaseService.getProjectPhasesByProyectoId(expenseService.getExpense(expenseId).getProyectoFase().getProyecto().getId()));
	        model.addAttribute("expenseCategories",expenseCategoryService.getExpenseCategoryByPhase(expenseService.getExpense(expenseId).getProyectoFase().getFase().getId()));
	        model.addAttribute("unitOfMeasurements", unitOfMeasurementService.getUnitOfMeasurements());
	       // model.addAttribute("gastoDescargos", expenseDisclaimersService.getExpenseDisclaimersByExpenseId(expenseId));
	        model.addAttribute("expenseDisclaimer", new ExpenseDisclaimers());
              

	          return "registro_descargo";
	    }
	   
	    @PostMapping("/guardar-descargo")
	    public String saveExpense(@ModelAttribute ExpenseDisclaimers expenseDisclaimer,
	            @RequestParam(required = false) Long id,
	            @RequestParam(value = "inputexpenseId", required = false) long expenseId,
	            RedirectAttributes redirectAttributes) {
	    	
	    	System.out.println(" print  expense   "+ expenseId);
	        // Intentar guardar el proyecto si todas las validaciones pasaron
	        try {
	        	
	        	expenseDisclaimersService.saveExpenseDisclaimers(expenseDisclaimer,expenseId);

	            redirectAttributes.addFlashAttribute("message", "Expense Disclaimer saved success.");
	            redirectAttributes.addFlashAttribute("messageType", "success");
	            return "redirect:/gastos/lista-descargos/"+expenseId;
	        } catch (Exception e) {
	            redirectAttributes.addFlashAttribute("message", "Error saving expense disclaimer: " + e.getMessage());
	            redirectAttributes.addFlashAttribute("messageType", "error");
	            return "redirect:/gastos/registro-descargo/"+expenseId;
	        }
	    }
}
