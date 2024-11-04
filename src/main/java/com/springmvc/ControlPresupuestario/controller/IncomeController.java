package com.springmvc.ControlPresupuestario.controller;

import java.util.List;

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
import com.springmvc.ControlPresupuestario.service.PaymentPlanService;
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
    @Autowired
    PaymentPlanService paymentPlanService;


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
    public String showRegisterFormDesembolso( @PathVariable("id") long projectId, Model model) {
        model.addAttribute("loginUser",
                this.userService.getUser(userDetailsService.getUserDetailsService().getId()));
        model.addAttribute("menuRoles",this.rolMenuService.getAllRolMenusByRoleId());
        model.addAttribute("menuRoles2",this.rolMenuService.getRolMenusByRoleId());
        //System.out.println("ID DEL PY "+projectId); 
        model.addAttribute("project", projectService.getProject(projectId)); 
        model.addAttribute("cuentas", accountService.getAccounts());
        model.addAttribute("planDePagos", paymentPlanService.getPaymentPlanByProjectId(projectId));
        model.addAttribute("income", new Income());

          return "registro_desembolso";
    }
    // Guardar
    @PostMapping("/guardar-desembolso")
    public String saveDesembolso(@ModelAttribute Income income,
            @RequestParam(required = false) Long id,
            @RequestParam(value = "inputproyectoId", required = false) long projectId,
            @RequestParam(value = "inputmontoTask", required = false) Double montoTask,
            @RequestParam(value = "pagos") long pagoSelected,
            RedirectAttributes redirectAttributes) {
    	
    	System.out.println(" print   id  "+projectId);
        // Intentar guardar el proyecto si todas las validaciones pasaron
        try {
        	
        	incomeService.saveIncome(income, projectId, montoTask, pagoSelected);

            redirectAttributes.addFlashAttribute("message", "desembolso guardado exitosamente.");
            redirectAttributes.addFlashAttribute("messageType", "success");
            return "redirect:/ejecucion/registro-ejecucion/"+projectId;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Error al guardar el desembolso: " + e.getMessage());
            redirectAttributes.addFlashAttribute("messageType", "error");
            return "redirect:/incomes/registro-desembolso/"+projectId;
        }
    }

    @GetMapping("/eliminar-ingreso/{id}/{projectId}")
    public String deleteIngreso(@PathVariable("id") Integer id,
    		@PathVariable("projectId") Long projectId,
    		Model model,RedirectAttributes redirectAttributes) {
    //Preguntar si el ingreso, fue afectado por un egreso, o sufrio algun movimiento	
    	
   	 try {
	        this.incomeService.deleteIncome(id);
	        //model.addAttribute("incomes", this.incomeService.getIncomes());
	        
	     // Agregar el mensaje de éxito
	        redirectAttributes.addFlashAttribute("message", "El ingreso fue eliminado exitosamente.");
	        redirectAttributes.addFlashAttribute("messageType", "success");
	
	        return "redirect:/ejecucion/registro-ejecucion/"+projectId;
 	 } catch (Exception e) {
 	        redirectAttributes.addFlashAttribute("message", "Error al eliminar el ingreso: " + e.getMessage());
 	        redirectAttributes.addFlashAttribute("messageType", "error");
 	       return "redirect:/ejecucion/registro-ejecucion/"+projectId;
 	    }
    }
    
    //METODOS DE TRANSFERENCIA
    @GetMapping("/registro-transferencia/{id}")
    public String showRegisterFormTransferencia( @PathVariable("id") long projectId, Model model) {
        model.addAttribute("loginUser",
                this.userService.getUser(userDetailsService.getUserDetailsService().getId()));
        model.addAttribute("menuRoles",this.rolMenuService.getAllRolMenusByRoleId());
        model.addAttribute("menuRoles2",this.rolMenuService.getRolMenusByRoleId());
        model.addAttribute("project", projectService.getProject(projectId)); 
        model.addAttribute("cuentas", accountService.getAccounts());
        model.addAttribute("cuentaOrigen", incomeService.getAllAccountOriginByProjectId(projectId));
        model.addAttribute("income", new Income());
        
	     // Supongamos que tienes una lista de 'incomesAccountOrigin' en tu controlador
        List<Income> incomesAccountOrigin = incomeService.getAllSaldosByProjectIdAndAccount(projectId,incomeService.getAllAccountOriginByProjectId(projectId));

        // Calcular sumas
        double totalAmountOrigin = incomesAccountOrigin.stream().mapToDouble(Income::getMonto).sum();
        double totalRecurringAmountOrigin = incomesAccountOrigin.stream().mapToDouble(Income::getMontoRecurrente).sum();
        double totalNonRecurringAmountOrigin = incomesAccountOrigin.stream().mapToDouble(Income::getMontoNoRecurrente).sum();

        // Añadir los totales al modelo, Cuenta Origin
        model.addAttribute("totalAmountOrigin", totalAmountOrigin);
        model.addAttribute("totalRecurringAmountOrigin", totalRecurringAmountOrigin);
        model.addAttribute("totalNonRecurringAmountOrigin", totalNonRecurringAmountOrigin);
        
        //System.out.println("totalAmountOrigin " + totalAmountOrigin);
        //System.out.println("totalRecurringAmountOrigin " + totalRecurringAmountOrigin);
        //System.out.println("totalNonRecurringAmountOrigin " + totalNonRecurringAmountOrigin);
        
	     // Supongamos que tienes una lista de 'incomesAccountOrigin' en tu controlador
        List<Income> incomesAccountDestiny = incomeService.getAllSaldosByProjectIdAndAccount(projectId,accountService.getAccountDestiny(incomeService.getAllAccountOriginByProjectId(projectId)));
        // Si no hay registros 
        if (incomesAccountDestiny == null || incomesAccountDestiny.isEmpty()) {
            model.addAttribute("totalAmountDestiny", 0);
            model.addAttribute("totalRecurringAmountDestiny", 0);
            model.addAttribute("totalNonRecurringAmountDestiny", 0);
        }
        else {
        // Calcular sumas
        double totalAmountDestiny = incomesAccountDestiny.stream().mapToDouble(Income::getMonto).sum();
        double totalRecurringAmountDestiny = incomesAccountDestiny.stream().mapToDouble(Income::getMontoRecurrente).sum();
        double totalNonRecurringAmountDestiny = incomesAccountDestiny.stream().mapToDouble(Income::getMontoNoRecurrente).sum();

        // Añadir los totales al modelo, Cuenta Origin
        model.addAttribute("totalAmountDestiny", totalAmountDestiny);
        model.addAttribute("totalRecurringAmountDestiny", totalRecurringAmountDestiny);
        model.addAttribute("totalNonRecurringAmountDestiny", totalNonRecurringAmountDestiny);
        }

          return "registro_transferencia";
    }
    // Guardar
    @PostMapping("/guardar-transferencia")
    public String saveTransferencia(@ModelAttribute Income income,
            @RequestParam(required = false) Long id,
            @RequestParam(value = "inputproyectoId", required = false) long projectId,
            @RequestParam(value = "inputmontoTask", required = false) Double montoTask,
            @RequestParam(value = "inputcuentaOrigen", required = false) long inputcuentaOrigen,
            RedirectAttributes redirectAttributes) {
    	
    	System.out.println(" print id transfer "+projectId);
        // Intentar guardar el proyecto si todas las validaciones pasaron
        try {
        	
        	incomeService.saveIncomeTransfer(income, projectId, montoTask, accountService.getAccount(inputcuentaOrigen));

            redirectAttributes.addFlashAttribute("message", "Transfer saved success.");
            redirectAttributes.addFlashAttribute("messageType", "success");
            return "redirect:/ejecucion/registro-ejecucion/"+projectId;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Error al guardar la transferencia: " + e.getMessage());
            redirectAttributes.addFlashAttribute("messageType", "error");
            return "redirect:/incomes/registro-transferencia/"+projectId;
        }
    }    
  
    //METODOS DE PRESTAMO
    @GetMapping("/registro-prestamo/{id}")
    public String showRegisterFormPrestamo( @PathVariable("id") long projectId, Model model) {
        model.addAttribute("loginUser",this.userService.getUser(userDetailsService.getUserDetailsService().getId()));
        model.addAttribute("menuRoles",this.rolMenuService.getAllRolMenusByRoleId());
        model.addAttribute("menuRoles2",this.rolMenuService.getRolMenusByRoleId());
        model.addAttribute("project", projectService.getProject(projectId)); 
        model.addAttribute("projectsDestiny", projectService.getProjects());    
        model.addAttribute("cuentasDestiny", accountService.getAccounts());    
        model.addAttribute("income", new Income());
        model.addAttribute("saldosCuenta",incomeService.getAllSaldosAndAccountByProjectId(projectId));
     


          return "registro_prestamo";
    }
    
    // Guardar prestamo
    @PostMapping("/guardar-prestamo")
    public String savePrestamo(@ModelAttribute Income income,
            @RequestParam(required = false) Long id,
            @RequestParam(value = "inputproyectoId", required = false) long projectId,
            @RequestParam(value = "inputmontoTask", required = false) Double montoTask,
            @RequestParam(value = "inputcuentaOrigen", required = false) long inputcuentaOrigen,
            @RequestParam(value = "inputProjectDestiny", required = false) long projectIdDestiny,
            @RequestParam(value = "inputAccountDestiny", required = false) long accountDestiny,
            RedirectAttributes redirectAttributes) {
    	
        // Intentar guardar el proyecto si todas las validaciones pasaron
        try {
        	
        	incomeService.saveIncomeLoan(income, projectId, accountService.getAccount(inputcuentaOrigen),projectIdDestiny,montoTask,accountService.getAccount(accountDestiny));

            redirectAttributes.addFlashAttribute("message", "Loan saved success.");
            redirectAttributes.addFlashAttribute("messageType", "success");
            return "redirect:/ejecucion/registro-ejecucion/"+projectId;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Error al guardar el prestamo: " + e.getMessage());
            redirectAttributes.addFlashAttribute("messageType", "error");
            return "redirect:/incomes/registro-prestamo/"+projectId;
        }
    }  
    
    //METODOS DE DEVOLUCION
    @GetMapping("/registro-devolucion/{id}")
    public String showRegisterFormDevolucion( @PathVariable("id") long projectId, Model model) {
        model.addAttribute("loginUser",this.userService.getUser(userDetailsService.getUserDetailsService().getId()));
        model.addAttribute("menuRoles",this.rolMenuService.getAllRolMenusByRoleId());
        model.addAttribute("menuRoles2",this.rolMenuService.getRolMenusByRoleId());
        model.addAttribute("project", projectService.getProject(projectId)); 
        model.addAttribute("loansReceived", incomeService.getLoansReceivedByProjectId(projectId));
        model.addAttribute("saldosCuenta",incomeService.getAllSaldosAndAccountByProjectId(projectId));
        model.addAttribute("income", new Income());
        
        List<Income> incomesAccountOrigin = incomeService.getAllSaldosByProjectIdAndAccount(projectId,incomeService.getAllAccountOriginByProjectId(projectId));

        // Calcular sumas
        double totalAmountDeudor = incomesAccountOrigin.stream().mapToDouble(Income::getMonto).sum();
        double totalRecurringAmountDeudor = incomesAccountOrigin.stream().mapToDouble(Income::getMontoRecurrente).sum();
        double totalNonRecurringAmountDeudor = incomesAccountOrigin.stream().mapToDouble(Income::getMontoNoRecurrente).sum();

        // Añadir los totales al modelo, Cuenta Origin
        model.addAttribute("totalAmountOrigin", totalAmountDeudor);
        model.addAttribute("totalRecurringAmountOrigin", totalRecurringAmountDeudor);
        model.addAttribute("totalNonRecurringAmountOrigin", totalNonRecurringAmountDeudor);

          return "registro_devolucion";
    }
    
    // Guardar devolucion
    @PostMapping("/guardar-devolucion")
    public String saveDevolucion(@ModelAttribute Income income,
            @RequestParam(required = false) Long id,
            @RequestParam(value = "inputproyectoId", required = false) long projectId,
            //@RequestParam(value = "inputmontoTask", required = false) Double montoTask,
            @RequestParam(value = "inputcuentaOrigen", required = false) long inputcuentaOrigen,
            @RequestParam(value = "inputcuentaAcreedor", required = false) long inputcuentaAcreedor,//cuenta acreedor
            @RequestParam(value = "inputproyectoAcreedor", required = false) long projectIdDestiny,//acreedor
            RedirectAttributes redirectAttributes) {
    	
        // Intentar guardar el proyecto si todas las validaciones pasaron
        try {
        	
        	incomeService.saveIncomeRepayment(income, projectIdDestiny, accountService.getAccount(inputcuentaAcreedor),accountService.getAccount(inputcuentaOrigen), projectId);

            redirectAttributes.addFlashAttribute("message", "Loan saved success.");
            redirectAttributes.addFlashAttribute("messageType", "success");
            return "redirect:/ejecucion/registro-ejecucion/"+projectId;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Error al guardar el prestamo: " + e.getMessage());
            redirectAttributes.addFlashAttribute("messageType", "error");
            return "redirect:/incomes/registro-devolucion/"+projectId;
        }
    }  
    
    
}
