package com.springmvc.ControlPresupuestario.controller;


import com.springmvc.ControlPresupuestario.model.Project;
import com.springmvc.ControlPresupuestario.model.UserAdm;
import com.springmvc.ControlPresupuestario.service.UserAdmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/userAdm")
public class UserController {

	@Autowired
	UserAdmService userService;
	/*
    @PostMapping("")
    public RedirectView postUser(@ModelAttribute UserAdm newUser) {

        this.userService.saveUser(newUser);

        return new RedirectView("/lista-usuarios");
    }*/
    @PostMapping("")
    public RedirectView postProject(@ModelAttribute UserAdm userAdm, RedirectAttributes redirectAttributes) {
    	try {
            // Intentar guardar el usuario
    		userService.saveUser(userAdm);
            // Mensaje de éxito
            redirectAttributes.addFlashAttribute("message", "El usuario fue guardado exitosamente.");
            redirectAttributes.addFlashAttribute("messageType", "success");
            
            return new RedirectView("/lista-usuarios");
	        } catch (IllegalStateException e) {
	            // Capturar el error y redirigir a la página de registro con el mensaje de error
	            redirectAttributes.addFlashAttribute("message", "Hubo un error al registrar/modificar el usuario: " + e.getMessage());
	            redirectAttributes.addFlashAttribute("messageType", "error");
	            return new RedirectView("/registro-usuario"); // Redirigir de nuevo al formulario
	        }
    }

}
