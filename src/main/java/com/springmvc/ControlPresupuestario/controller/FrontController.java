package com.springmvc.ControlPresupuestario.controller;


import com.springmvc.ControlPresupuestario.model.Employee;
import com.springmvc.ControlPresupuestario.model.Income;
import com.springmvc.ControlPresupuestario.model.Transaction;
import com.springmvc.ControlPresupuestario.model.Project;
import com.springmvc.ControlPresupuestario.model.RolMenu;
import com.springmvc.ControlPresupuestario.model.UserAdm;
import com.springmvc.ControlPresupuestario.model.Perfil;
import com.springmvc.ControlPresupuestario.service.*;
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
    EmployeeService employeeService;
    @Autowired
    TransactionService transactionService;
    @Autowired
    PefilService pefilService;
    @Autowired
    IMyUserDetailsService userDetailsService;
    
    //AGREGANDO PROYECTO
    @Autowired
    ProjectService projectService;
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
    IncomeService incomeService;
    

    @GetMapping(value = {"/login"})
    public String login(Model model) {

        return "login";
    }

    @GetMapping(value = {"/", "/home"})
    public String home(Model model) {

        model.addAttribute("loginUser",
                this.userService.getUser(userDetailsService.getUserDetailsService().getId()));

        //model.addAttribute("enterprises",this.enterpriseService.getEnterprises());
        //model.addAttribute("menuRoles",this.rolMenuService.getAllRolMenusByRoleId());
        //model.addAttribute("menuRoles",this.rolMenuService.getAllRolMenus());
        model.addAttribute("menuRoles2",this.rolMenuService.getRolMenusByRoleId());// Para desplegar el menu asignado
        return "index";
    }

    //-------- mapping para empleados en front

    @GetMapping("/lista-empleados")
    public String getEmployees(Model model) {

        //model.addAttribute("loginEmployee",
    	//this.employeeService.getEmployee(userDetailsService.getUserDetailsService().getId()));
        model.addAttribute("loginUser",
                this.userService.getUser(userDetailsService.getUserDetailsService().getId()));
        model.addAttribute("menuRoles",this.rolMenuService.getRolMenusByRoleId());    
        model.addAttribute("employees", this.employeeService.getEmployees());

        return "lista_empleados";
    }

    @GetMapping("/registro-empleado")
    public String postEmployee(Model model) {

        model.addAttribute("loginUser",userDetailsService.getUserDetailsService());

        model.addAttribute("employee", new Employee());


        model.addAttribute("roles",this.pefilService.getRoles());

        return "registro_empleado";
    }

    @GetMapping("/modificar-empleado/{id}")
    public String patchEmployee(@PathVariable("id") long id, Model model) {

        model.addAttribute("employee", employeeService.getEmployee(id));

        model.addAttribute("roles",this.pefilService.getRoles());

        this.employeeService.updateEmployee(id, employeeService.getEmployee(id));

        return "registro_empleado";
    }

    @GetMapping("/eliminar-empleado/{id}")
    public String deleteEmployee(@PathVariable("id") long id, Model model) {

        this.employeeService.deleteEmployee(id);

        model.addAttribute("employees", this.employeeService.getEmployees());

        return "lista-empresas";
    }


    //-------- mapping para movimientos en front

    @GetMapping("/registro-movimiento")
    public String postTransactions(Model model){

        model.addAttribute("loginUser", this.userService.getUser(userDetailsService.getUserDetailsService().getId()));
        
        model.addAttribute("transaction",new Transaction());

        return "registro_transaccion";
    }

    @GetMapping("/lista-movimientos")
    public String getTransactions(Model model){
        model.addAttribute("loginUser",
                this.userService.getUser(userDetailsService.getUserDetailsService().getId()));
        
        model.addAttribute("transactions",this.transactionService.getTransactions());
        return "lista_transacciones";
    }

    @GetMapping("/modificar-movimiento/{id}")
    public String patchTransaction(@PathVariable("id") long id,Model model){

        model.addAttribute("transaction",this.transactionService.getTransaction(id));

        this.transactionService.updateTransaction(id,this.transactionService.getTransaction(id));

        return "registro_transaccion";
    }

    @GetMapping("/eliminar-movimiento/{id}")
    public String deleteTransaction(@PathVariable("id") long id, Model model){

        this.transactionService.deleteTransaction(id);

        model.addAttribute("transaction",this.transactionService.getTransactions());
        return "lista_transacciones";
    }
    
    //-------- mapping para PROYECTOS en front

    @GetMapping("/lista-proyectos")
    public String getProjects(Model model) {

        model.addAttribute("loginUser", this.userService.getUser(userDetailsService.getUserDetailsService().getId()));
        //model.addAttribute("menuRoles",this.rolMenuService.getAllRolMenusByRoleId());
       // model.addAttribute("menuRoles",this.rolMenuService.getAllRolMenus());
        model.addAttribute("menuRoles",this.rolMenuService.getAllRolMenusByRoleId());
        model.addAttribute("menuRoles2",this.rolMenuService.getRolMenusByRoleId());
        model.addAttribute("projects", this.projectService.getProjects());

        return "lista_proyectos";
    }

    @GetMapping("/registro-proyecto")
    public String postProject(Model model) {
        model.addAttribute("loginUser", this.userService.getUser(userDetailsService.getUserDetailsService().getId()));
        model.addAttribute("project", new Project());

        // Si hay un mensaje de error, lo añadimos al modelo
        if (model.containsAttribute("error")) {
            model.addAttribute("error", model.asMap().get("error"));
        }
        
        return "registro_proyecto";
    }
/*
    @PostMapping("/project")
    public String saveProject(@ModelAttribute Project project, RedirectAttributes redirectAttributes) {
        try {
            // Intentar guardar el proyecto
            projectService.saveProject(project);
            // Redirigir a la lista de proyectos o a donde desees
            return "redirect:/lista-proyectos"; // Cambia esto a tu ruta deseada
        } catch (IllegalStateException e) {
            // Capturar el error y redirigir a la página de registro con el mensaje de error
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/registro-proyecto"; // Redirigir de nuevo al formulario
        }
    }*/

    @GetMapping("/modificar-proyecto/{id}")
    public String patchProject(@PathVariable("id") long id, Model model,RedirectAttributes redirectAttributes) {
    	try {
    		/*Project project = projectService.getProject(id);
            projectService.updateProject(id, project);
            model.addAttribute("project", project);*/
	        model.addAttribute("project", projectService.getProject(id));
	
	        this.projectService.updateProject(id, projectService.getProject(id));
	        // Agregar el mensaje de éxito
	        redirectAttributes.addFlashAttribute("message", "El proyecto fue modificado exitosamente.");
	        redirectAttributes.addFlashAttribute("messageType", "success");
	        
	        return "registro_proyecto";
        
    	}catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Error al modificar el proyecto: " + e.getMessage());
            redirectAttributes.addFlashAttribute("messageType", "error");
            return "redirect:/registro-proyecto";
        }
    }
    

    @GetMapping("/eliminar-proyecto/{id}")
    public String deleteProject(@PathVariable("id") long id, Model model,RedirectAttributes redirectAttributes) {
    	 try {
	        this.projectService.deleteProject(id);
	
	        model.addAttribute("projects", this.projectService.getProjects());
	        
	     // Agregar el mensaje de éxito
	        redirectAttributes.addFlashAttribute("message", "El proyecto fue eliminado exitosamente.");
	        redirectAttributes.addFlashAttribute("messageType", "success");
	
	        return "redirect:/lista-proyectos";
    	 } catch (Exception e) {
    	        redirectAttributes.addFlashAttribute("message", "Error al eliminar el proyecto: " + e.getMessage());
    	        redirectAttributes.addFlashAttribute("messageType", "error");
    	        return "redirect:/lista-proyectos";
    	    }
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

    /*@GetMapping("/registro-rol")
    public String postRol(Model model) {
       
    	model.addAttribute("loginUser",
        		this.userService.getUser(userDetailsService.getUserDetailsService().getId()));
        
        model.addAttribute("menus",this.menuService.getMenus());
        //model.addAttribute("rolMenu",new RolMenu());

        model.addAttribute("perfil", new Perfil());

        return "registro_rol";
    }*/
    @GetMapping("/registro-rol")
    public String postRol(@RequestParam(value = "rolId", required = false) Long rolId, Model model) {
        // Obtener el usuario logueado
        model.addAttribute("loginUser", this.userService.getUser(userDetailsService.getUserDetailsService().getId()));

        // Cargar los menús
        model.addAttribute("menus", this.menuService.getMenus());

        // Si se ha guardado un rol, obtener el rol guardado para mostrarlo
        if (rolId != null) {
            Perfil perfilGuardado = pefilService.findById(rolId);
            model.addAttribute("perfilGuardado", perfilGuardado);
        }

        // Preparar un nuevo objeto Perfil para el formulario
        model.addAttribute("perfil", new Perfil());
        // Agregar rolId al modelo para que esté disponible en la vista
        model.addAttribute("rolId", rolId);

        return "registro_rol";
    }

    @GetMapping("/modificar-rol/{id}")
    public String patchRol(@PathVariable("id") long id, Model model) {

//        model.addAttribute("userAdm", userService.getUser(id));

        model.addAttribute("roles", pefilService.getRol(id));

        this.pefilService.updateRol(id, pefilService.getRol(id));

        return "modificar_rol";
    }

    @GetMapping("/eliminar-rol/{id}")
    public String deleteRol(@PathVariable("id") long id, Model model) {

        this.pefilService.deleteRol(id);

        model.addAttribute("roles", this.pefilService.getRoles());
        //model.addAttribute("menuRoles",this.rolMenuService.getAllRolMenusByRoleId());
        model.addAttribute("menuRoles",this.rolMenuService.getAllRolMenus());
        
        return "lista_roles";
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
