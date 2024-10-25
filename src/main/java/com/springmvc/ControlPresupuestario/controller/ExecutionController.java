package com.springmvc.ControlPresupuestario.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.springmvc.ControlPresupuestario.model.Income;
import com.springmvc.ControlPresupuestario.service.ExpenseService;
import com.springmvc.ControlPresupuestario.service.IMyUserDetailsService;
import com.springmvc.ControlPresupuestario.service.IncomeService;
import com.springmvc.ControlPresupuestario.service.ProjectService;
import com.springmvc.ControlPresupuestario.service.RolMenuService;
import com.springmvc.ControlPresupuestario.service.UserAdmService;

@Controller
@RequestMapping("/ejecucion")
public class ExecutionController {

	    @Autowired
	    UserAdmService userService;
	    @Autowired
	    RolMenuService rolMenuService;
	    @Autowired
	    IMyUserDetailsService userDetailsService;    
	    @Autowired
	    IncomeService incomeService;
	    @Autowired
	    ExpenseService expenseService;
	    @Autowired
	    ProjectService projectService;
	    
	    // Listar Beneficiarios
	    @GetMapping("/registro-ejecucion/{id}")
	    public String getEjecucionProyecto(@PathVariable("id") long projectId, Model model) {
	        model.addAttribute("loginUser", this.userService.getUser(userDetailsService.getUserDetailsService().getId()));
	        model.addAttribute("menuRoles",this.rolMenuService.getAllRolMenusByRoleId());
	        model.addAttribute("menuRoles2",this.rolMenuService.getRolMenusByRoleId());
	        
	        model.addAttribute("project", projectService.getProject(projectId)); 
	        model.addAttribute("incomes", incomeService.getIncomesVigentesByProjectId(projectId));
	        model.addAttribute("expenses", expenseService.getExpensesVigentesByProjectId(projectId));
	        
	     // Supongamos que tienes una lista de 'incomes' en tu controlador
	        List<Income> incomes = incomeService.getIncomesVigentesByProjectId(projectId);

	        // Calcular sumas
	        double totalAmount = incomes.stream().mapToDouble(Income::getMonto).sum();
	        double totalRecurringAmount = incomes.stream().mapToDouble(Income::getMontoRecurrente).sum();
	        double totalNonRecurringAmount = incomes.stream().mapToDouble(Income::getMontoNoRecurrente).sum();

	        // Añadir los totales al modelo
	        model.addAttribute("totalAmount", totalAmount);
	        model.addAttribute("totalRecurringAmount", totalRecurringAmount);
	        model.addAttribute("totalNonRecurringAmount", totalNonRecurringAmount);
	        
	        
	        return "registro_ejecucion"; // Nombre de la vista Thymeleaf
	    }
}
