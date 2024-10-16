package com.springmvc.ControlPresupuestario.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.springmvc.ControlPresupuestario.service.AccountService;
import com.springmvc.ControlPresupuestario.service.IMyUserDetailsService;
import com.springmvc.ControlPresupuestario.service.IncomeService;
import com.springmvc.ControlPresupuestario.service.MenuService;
import com.springmvc.ControlPresupuestario.service.PefilService;
import com.springmvc.ControlPresupuestario.service.RolMenuService;
import com.springmvc.ControlPresupuestario.service.UserAdmService;

@RestController
@RequestMapping("/income")
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

    @PostMapping("")
    //public String saveProject(@ModelAttribute Project project, RedirectAttributes redirectAttributes) {
    public RedirectView postIncome(@ModelAttribute Income income, RedirectAttributes redirectAttributes) {
            try {
            // Intentar guardar el ingreso
            	incomeService.saveIncome(income);
            // Redirigir a la lista de ingresos o a donde desees
            // Agregamos el mensaje de éxito
            redirectAttributes.addFlashAttribute("message", "El ingreso fue guardado exitosamente.");
            redirectAttributes.addFlashAttribute("messageType", "success");
            
            return new RedirectView("/lista-ingresos");// Cambia esto a tu ruta deseada
	        } catch (IllegalStateException e) {
	            // Capturar el error y redirigir a la página de registro con el mensaje de error
	            redirectAttributes.addFlashAttribute("message", "Hubo un error al registrar/modificar el ingreso: " + e.getMessage());
	            redirectAttributes.addFlashAttribute("messageType", "error");
	            return new RedirectView("/registro-ingreso"); // Redirigir de nuevo al formulario
	        }
    }

    //-------- mapping para Ingresos en front

    @GetMapping("/lista-ingresos")
    public String getIngresos(Model model) {

        model.addAttribute("loginUser",
                this.userService.getUser(userDetailsService.getUserDetailsService().getId()));
        model.addAttribute("menuRoles",this.rolMenuService.getAllRolMenusByRoleId());
        model.addAttribute("menuRoles2",this.rolMenuService.getRolMenusByRoleId());
        model.addAttribute("ingresos", this.incomeService.getIncomes());

        return "lista_ingresos";
    }


    @GetMapping("/registro-ingreso")
    public String postIngreso(@RequestParam(value = "rolId", required = false) Long rolId, Model model) {
        // Obtener el usuario logueado
        model.addAttribute("loginUser", this.userService.getUser(userDetailsService.getUserDetailsService().getId()));
        model.addAttribute("income", new Income());
        // Cargar los menús
        model.addAttribute("menus", this.menuService.getMenus());
        // Preparar un nuevo objeto Perfil para el formulario
        model.addAttribute("perfil", new Perfil());
        // Agregar rolId al modelo para que esté disponible en la vista
        model.addAttribute("rolId", rolId);
        // Agregar rolId al modelo para que esté disponible en la vista
        model.addAttribute("cuentas", this.accountService.getAccounts());


        return "registro_ingreso";
    }

    @GetMapping("/modificar-ingreso/{id}")
    public String patchIngreso(@PathVariable("id") long id, Model model ,RedirectAttributes redirectAttributes) {
       
    	try {
    		/*Project project = projectService.getProject(id);
            projectService.updateProject(id, project);
            model.addAttribute("project", project);*/
	        model.addAttribute("income", incomeService.getIncome(id));
	
	        this.incomeService.updateIncome(id,  incomeService.getIncome(id));
	        // Agregar el mensaje de éxito
	        redirectAttributes.addFlashAttribute("message", "El ingreso fue modificado exitosamente.");
	        redirectAttributes.addFlashAttribute("messageType", "success");
	        
	        return "registro_ingreso";
        
    	}catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Error al modificar el ingreso: " + e.getMessage());
            redirectAttributes.addFlashAttribute("messageType", "error");
            return "redirect:/registro-ingreso";
        }
    }

    @GetMapping("/eliminar-ingreso/{id}")
    public String deleteIngreso(@PathVariable("id") long id, Model model,RedirectAttributes redirectAttributes) {

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
