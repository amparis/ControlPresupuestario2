package com.springmvc.ControlPresupuestario.controller;

import com.springmvc.ControlPresupuestario.model.Beneficiary;
import com.springmvc.ControlPresupuestario.model.Project;
import com.springmvc.ControlPresupuestario.model.ProjectHistory;
import com.springmvc.ControlPresupuestario.model.UserAdmProject;
import com.springmvc.ControlPresupuestario.service.BeneficiaryService;
import com.springmvc.ControlPresupuestario.service.CountryService;
import com.springmvc.ControlPresupuestario.service.IMyUserDetailsService;
import com.springmvc.ControlPresupuestario.service.PefilService;
import com.springmvc.ControlPresupuestario.service.ProjectHistoryService;
import com.springmvc.ControlPresupuestario.service.ProjectService;
import com.springmvc.ControlPresupuestario.service.RolMenuService;
import com.springmvc.ControlPresupuestario.service.UserAdmService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/proyectos")
public class ProjectController {

	@Autowired
	ProjectService projectService;
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
    BeneficiaryService beneficiaryService;
    
    @Autowired
    ProjectHistoryService projectHistoryService;
	
	@Autowired
	private CountryService countryService;
	
    //-------- mapping para PROYECTOS
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
    
    @GetMapping("/ver-proyecto/{id}")
    public String getProyect(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("loginUser",
                this.userService.getUser(userDetailsService.getUserDetailsService().getId()));

        model.addAttribute("project", projectService.getProject(id));  	
        model.addAttribute("menuRoles2",this.rolMenuService.getRolMenusByRoleIdPermisos(userService.getUser(id).getPerfil().getId()));// Para mostrar el menu de acuerdo al ID del usuario seleccionado
        model.addAttribute("projectHistory",projectHistoryService.getProjectHistory(id));
        return "ver_proyecto";
    }
    
   
    // Mostrar el formulario de registro de un nuevo proyecto
    //@GetMapping("/registro-proyecto")
    @GetMapping("/registro-proyecto")
    public String showRegisterFormProject(@RequestParam(required = false) Long id, Model model) {
        model.addAttribute("loginUser", this.userService.getUser(userDetailsService.getUserDetailsService().getId()));

    	    // Obtener todos los beneficiarios (para lista de selección en el combo)
    	    List<Beneficiary> supervisors = beneficiaryService.getAllBeneficiariesEstadoAndTipo("V","Personal");  // Lista de beneficiarios supervisores
    	    List<Beneficiary> responsables = beneficiaryService.getAllBeneficiariesEstadoAndTipo("V","Persona Natural");  // Lista de beneficiarios responsables

    	    model.addAttribute("project", new Project());
    	    model.addAttribute("beneficiarySupervisor", supervisors);
    	    model.addAttribute("beneficiaryResponsable", responsables);
    	    model.addAttribute("paises", countryService.getAllCountries()); 
    	    return "registro_proyecto"; // Nombre de la vista Thymeleaf

    }
    
    // Guardar un nuevo Proyecto
    @PostMapping("/guardar-proyecto")
    public String saveProyecto(@ModelAttribute Project project,
            @RequestParam(value = "supervisor", required = false) Integer idSupervisor,
            @RequestParam(value = "responsable", required = false) Integer idResponsable,
            @RequestParam(name = "assignSupervisor", required = false) boolean assignSupervisor, // Captura el checkbox
            @RequestParam(name = "assignResponsable", required = false) boolean assignResponsable, // Captura el checkbox
            @RequestParam(required = false) Long id,
            RedirectAttributes redirectAttributes) {
    	
        // Validar si seleccionó el checkbox de supervisor y responsable
        if (assignSupervisor && (idSupervisor == null || idSupervisor == 0)) {
            redirectAttributes.addFlashAttribute("message", "Por favor, seleccione un supervisor válido.");
            redirectAttributes.addFlashAttribute("messageType", "error");
            return "redirect:/proyectos/registro-proyecto";
        }

        if (assignResponsable && (idResponsable == null || idResponsable == 0)) {
            redirectAttributes.addFlashAttribute("message", "Por favor, seleccione un responsable válido.");
            redirectAttributes.addFlashAttribute("messageType", "error");
            return "redirect:/proyectos/registro-proyecto";
        }

        // Validar que si se selecciona un responsable, también debe haberse seleccionado un supervisor
        if (assignResponsable && !assignSupervisor) {
            redirectAttributes.addFlashAttribute("message", "Debes asignar un supervisor si asignas un responsable.");
            redirectAttributes.addFlashAttribute("messageType", "error");
            return "redirect:/proyectos/registro-proyecto";
        }

        // Validar que el supervisor y el responsable no sean la misma persona
        if (idSupervisor != null && idResponsable != null && idSupervisor.equals(idResponsable)) {
            redirectAttributes.addFlashAttribute("message", "El supervisor y el responsable no pueden ser la misma persona.");
            redirectAttributes.addFlashAttribute("messageType", "error");
            return "redirect:/proyectos/registro-proyecto";
        }

        // Validar que la fecha inicial sea anterior a la fecha de finalización
        if (project.getFechaInicial().after(project.getFechaFin())) {
            redirectAttributes.addFlashAttribute("message", "La fecha inicial debe ser menor a la fecha de finalización.");
            redirectAttributes.addFlashAttribute("messageType", "error");
            return "redirect:/proyectos/registro-proyecto";
        }
        
        //*************************
        // Asignar null si no se seleccionó el checkbox, no se aplica cambio de personal
        Integer supervisorIdToSave = assignSupervisor ? idSupervisor : 0;
        Integer responsableIdToSave = assignResponsable ? idResponsable : 0;

        // Intentar guardar el proyecto si todas las validaciones pasaron
        try {
            projectService.saveProject(project, supervisorIdToSave, responsableIdToSave, id);

            redirectAttributes.addFlashAttribute("message", "Proyecto guardado exitosamente.");
            redirectAttributes.addFlashAttribute("messageType", "success");
            return "redirect:/proyectos/lista-proyectos";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Error al guardar el proyecto: " + e.getMessage());
            redirectAttributes.addFlashAttribute("messageType", "error");
            return "redirect:/proyectos/registro-proyecto";
        }
    }
    
    // Guardar ediccion datos simples Proyecto
    @PostMapping("/guardarDatosSimples-proyecto")
    public String saveEditSimpleProyecto(@ModelAttribute Project project,
            @RequestParam(required = false) Long id,
            RedirectAttributes redirectAttributes) {
    	
        // Intentar guardar el proyecto si todas las validaciones pasaron
        try {
            projectService.updateSimpleProject(id, project);

            redirectAttributes.addFlashAttribute("message", "Proyecto guardado exitosamente.");
            redirectAttributes.addFlashAttribute("messageType", "success");
            return "redirect:/proyectos/lista-proyectos";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Error al guardar el proyecto: " + e.getMessage());
            redirectAttributes.addFlashAttribute("messageType", "error");
            return "redirect:/proyectos/modificar-proyecto/"+id;
        }
    }
 
    // Guardar edicion de datos criticos Proyecto
    @PostMapping("/guardarDatosCriticos-proyecto")
    public String saveEditCriticProyecto(@ModelAttribute Project project,
            @RequestParam(required = false) Long id,
            @RequestParam(value = "inputValorModificacion", required = false) Double valorModificado,
            @RequestParam(value = "inputmontoRecurrenteNew", required = false) Double montoRecurrenteNew,
            @RequestParam(value = "inputmontoNoRecurrenteNew", required = false) Double montoNoRecurrenteNew,
            @RequestParam(name = "modificationReason", required = false) String justificacionEdicion,
            RedirectAttributes redirectAttributes) {
    	
        // Validar que la fecha inicial sea anterior a la fecha de finalización
        if (project.getFechaInicial().after(project.getFechaFin())) {
            redirectAttributes.addFlashAttribute("message", "La fecha inicial debe ser menor a la fecha de finalización.");
            redirectAttributes.addFlashAttribute("messageType", "error");
            return "redirect:/proyectos/modificar-proyecto/"+id;
        }
        
        // Intentar guardar el proyecto si todas las validaciones pasaron
        try {
        	System.out.println(valorModificado);
        	System.out.println(montoRecurrenteNew);
        	System.out.println(montoNoRecurrenteNew);
        	
        	if(valorModificado == null) valorModificado = 0.0;
        	if(montoRecurrenteNew == null) montoRecurrenteNew = 0.0;
        	if(montoNoRecurrenteNew == null) montoNoRecurrenteNew = 0.0;
            projectService.updateCriticProject(id,project,valorModificado,montoRecurrenteNew,montoNoRecurrenteNew,justificacionEdicion);

            redirectAttributes.addFlashAttribute("message", "Proyecto guardado exitosamente.");
            redirectAttributes.addFlashAttribute("messageType", "success");
            return "redirect:/proyectos/lista-proyectos";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Error al guardar el proyecto: " + e.getMessage());
            redirectAttributes.addFlashAttribute("messageType", "error");
            return "redirect:/proyectos/modificar-proyecto/"+id;
        }
    }

    // Guardar edicion de asignacion de personal Proyecto
    @PostMapping("/guardarAsignacionPersonal-proyecto")
    public String saveEditAsigPersonalProyecto(@ModelAttribute Project project,
    		@RequestParam(value = "inputSupervisorAsignado", required = false) Integer SupervisorAsignadoId,
    		@RequestParam(value = "inputResponsableAsignado", required = false) Integer ResponsableAsignadoId,
            @RequestParam(value = "supervisor", required = false) Integer idSupervisor,
            @RequestParam(value = "responsable", required = false) Integer idResponsable,
            @RequestParam(name = "assignSupervisor", required = false) boolean assignSupervisor, // Captura el checkbox
            @RequestParam(name = "assignResponsable", required = false) boolean assignResponsable, // Captura el checkbox

            @RequestParam(required = false) Long id,
            RedirectAttributes redirectAttributes) {
    	
        // Validar si seleccionó el checkbox de supervisor y responsable
        if (assignSupervisor && (idSupervisor == null || idSupervisor == 0)) {
            redirectAttributes.addFlashAttribute("message", "Por favor, seleccione un supervisor válido.");
            redirectAttributes.addFlashAttribute("messageType", "error");
            return "redirect:/proyectos/modificar-proyecto/"+id;
        }

        if (assignResponsable && (idResponsable == null || idResponsable == 0)) {
            redirectAttributes.addFlashAttribute("message", "Por favor, seleccione un responsable válido.");
            redirectAttributes.addFlashAttribute("messageType", "error");
            return "redirect:/proyectos/modificar-proyecto/"+id;
        }

        // Validar que si se selecciona un responsable, también debe haberse seleccionado un supervisor
        if (assignResponsable && !assignSupervisor) {
            redirectAttributes.addFlashAttribute("message", "Debes asignar un supervisor si asignas un responsable.");
            redirectAttributes.addFlashAttribute("messageType", "error");
            return "redirect:/proyectos/modificar-proyecto/"+id;
        }

        // Validar que el supervisor y el responsable no sean la misma persona
        if (idSupervisor != null && idResponsable != null && idSupervisor.equals(idResponsable)) {
            redirectAttributes.addFlashAttribute("message", "El supervisor y el responsable no pueden ser la misma persona.");
            redirectAttributes.addFlashAttribute("messageType", "error");
            return "redirect:/proyectos/modificar-proyecto/"+id;
        }

        // Asignar null si no se seleccionó el checkbox
    	System.out.println("new sup "+idSupervisor);
    	System.out.println("new resp "+idResponsable);
    	System.out.println("check sup "+assignSupervisor);
    	System.out.println("check resp "+assignResponsable);
        Integer supervisorIdToSave = assignSupervisor ? idSupervisor : 0;
        Integer responsableIdToSave = assignResponsable ? idResponsable : 0;

        // Intentar guardar el proyecto si todas las validaciones pasaron
        try {
        	
        	System.out.println("py "+id);
        	System.out.println("sup "+SupervisorAsignadoId);
        	System.out.println("resp "+ResponsableAsignadoId);
        	System.out.println("new sup "+supervisorIdToSave);
        	System.out.println("new resp "+responsableIdToSave);
            projectService.updatePersonalProject(id,SupervisorAsignadoId, ResponsableAsignadoId,supervisorIdToSave, responsableIdToSave, project);

            redirectAttributes.addFlashAttribute("message", "Proyecto guardado exitosamente.");
            redirectAttributes.addFlashAttribute("messageType", "success");
            return "redirect:/proyectos/lista-proyectos";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Error al guardar el proyecto: " + e.getMessage());
            redirectAttributes.addFlashAttribute("messageType", "error");
            return "redirect:/proyectos/modificar-proyecto/"+id;
        }
    }

    
    // Modificar un proyecto
    @GetMapping("/modificar-proyecto/{id}") 
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
    	model.addAttribute("project", projectService.getProject(id));  	
        model.addAttribute("loginUser", this.userService.getUser(userDetailsService.getUserDetailsService().getId()));

    	        //project = projectService.getProject(id);
    	    	System.out.println("**** entro a EDITAR  "+ id);
     	    	if(beneficiaryService.getSupervisorByProject(id)==0 )
    	    	{
    	    		if(beneficiaryService.getResponsableByProject(id)==0)
    	    		{
    	    	        model.addAttribute("selectedSupervisor", "Ninguno");
    	    	        model.addAttribute("selectedResponsable", "Ninguno");
    	    	        model.addAttribute("selectedSupervisorId", 0);
    	    	        model.addAttribute("selectedResponsableId", 0);
    	    		}
    	    		else
    	    		{
    	    			model.addAttribute("selectedSupervisor", "Ninguno");
    	    	        model.addAttribute("selectedResponsable", beneficiaryService.getBeneficiaryById(beneficiaryService.getResponsableByProject(id)).getNombres()+" "+beneficiaryService.getBeneficiaryById(beneficiaryService.getResponsableByProject(id)).getApellidos());
    	    	        model.addAttribute("selectedSupervisorId", 0);
    	    	        model.addAttribute("selectedResponsableId", beneficiaryService.getBeneficiaryById(beneficiaryService.getResponsableByProject(id)).getId());
    	    		}
    	    	}
    	    	else
    	    	{
	    			
    	    		if(beneficiaryService.getResponsableByProject(id)==0)
    	    		{
    	    	        model.addAttribute("selectedSupervisor", beneficiaryService.getBeneficiaryById(beneficiaryService.getSupervisorByProject(id)).getNombres()+" "+beneficiaryService.getBeneficiaryById(beneficiaryService.getSupervisorByProject(id)).getApellidos());
    	    	        model.addAttribute("selectedResponsable","Ninguno");
    	    	        model.addAttribute("selectedSupervisorId", beneficiaryService.getBeneficiaryById(beneficiaryService.getSupervisorByProject(id)).getId());
    	    	        model.addAttribute("selectedResponsableId", 0);
    	    		}
    	    		else
    	    		{
    	    			model.addAttribute("selectedSupervisor", beneficiaryService.getBeneficiaryById(beneficiaryService.getSupervisorByProject(id)).getNombres()+" "+beneficiaryService.getBeneficiaryById(beneficiaryService.getSupervisorByProject(id)).getApellidos());
    	    	        model.addAttribute("selectedResponsable", beneficiaryService.getBeneficiaryById(beneficiaryService.getResponsableByProject(id)).getNombres()+" "+beneficiaryService.getBeneficiaryById(beneficiaryService.getResponsableByProject(id)).getApellidos());
    	    	        model.addAttribute("selectedSupervisorId", beneficiaryService.getBeneficiaryById(beneficiaryService.getSupervisorByProject(id)).getId());
    	    	        model.addAttribute("selectedResponsableId", beneficiaryService.getBeneficiaryById(beneficiaryService.getResponsableByProject(id)).getId());
    	    		}

    	    	}
System.out.println(">>>>****> supervisor "+ beneficiaryService.getSupervisorByProject(id));
System.out.println(">>>>****> RESP "+ beneficiaryService.getResponsableByProject(id));

    	    // Obtener todos los beneficiarios (para lista de selección en el combo)
			List<Beneficiary> supervisors = beneficiaryService.getAllBeneficiariesEstadoAndTipo("V","Personal");  // Lista de beneficiarios supervisores
			List<Beneficiary> responsables = beneficiaryService.getAllBeneficiariesEstadoAndTipo("V","Persona Natural");  // Lista de beneficiarios responsables
    	    model.addAttribute("beneficiarySupervisor", supervisors);
    	    model.addAttribute("beneficiaryResponsable", responsables);
    	    model.addAttribute("paises", countryService.getAllCountries());
      	return "editar_proyecto";
    }
    
    //Eliminar proyecto
    @GetMapping("/eliminar-proyecto/{id}")
    public String deleteProject(@PathVariable("id") long id, Model model,RedirectAttributes redirectAttributes) {
    	 try {
	        this.projectService.deleteProject(id);
	        model.addAttribute("projects", this.projectService.getProjects());
	        
	     // Agregar el mensaje de éxito
	        redirectAttributes.addFlashAttribute("message", "El proyecto fue eliminado exitosamente.");
	        redirectAttributes.addFlashAttribute("messageType", "success");
	
	        return "redirect:/proyectos/lista-proyectos";
    	 } catch (Exception e) {
    	        redirectAttributes.addFlashAttribute("message", "Error al eliminar el proyecto: " + e.getMessage());
    	        redirectAttributes.addFlashAttribute("messageType", "error");
    	        return "redirect:/proyectos/lista-proyectos";
    	    }
    }
}
