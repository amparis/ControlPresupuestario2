package com.springmvc.ControlPresupuestario.controller;

import com.springmvc.ControlPresupuestario.model.Project;
import com.springmvc.ControlPresupuestario.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;


@RestController
@RequestMapping("/project")
public class ProjectController {

	@Autowired
	ProjectService projectService;
	/*	
	@PostMapping("")
	public RedirectView postProject(@ModelAttribute Project newProject) {
		
		this.projectService.saveProject(newProject);
		
		return new RedirectView("/lista-proyectos");
	}*/
	
    @PostMapping("")
    //public String saveProject(@ModelAttribute Project project, RedirectAttributes redirectAttributes) {
    public RedirectView postProject(@ModelAttribute Project project, RedirectAttributes redirectAttributes) {
            
        // Validación de fechas
        if (project.getFechaInicial().after(project.getFechaFin())) {
            // Si la fecha inicial es mayor o igual que la fecha de finalización, mostrar error
            redirectAttributes.addFlashAttribute("message", "La fecha inicial debe ser menor a la fecha de finalización.");
            redirectAttributes.addFlashAttribute("messageType", "error");
            return new RedirectView("/registro-proyecto");  // Redirigir al formulario de registro
        }

    	try {
            // Intentar guardar el proyecto
            projectService.saveProject(project);
            // Mensaje de éxito
            redirectAttributes.addFlashAttribute("message", "El proyecto fue guardado exitosamente.");
            redirectAttributes.addFlashAttribute("messageType", "success");
            
            return new RedirectView("/lista-proyectos");
	        } catch (IllegalStateException e) {
	            // Capturar el error y redirigir a la página de registro con el mensaje de error
	            redirectAttributes.addFlashAttribute("message", "Hubo un error al registrar/modificar el proyecto: " + e.getMessage());
	            redirectAttributes.addFlashAttribute("messageType", "error");
	            return new RedirectView("/registro-proyecto"); // Redirigir de nuevo al formulario
	        }
    }

}
