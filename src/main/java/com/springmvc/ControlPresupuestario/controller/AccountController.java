package com.springmvc.ControlPresupuestario.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.springmvc.ControlPresupuestario.model.Account;
import com.springmvc.ControlPresupuestario.service.AccountService;

@RestController
@RequestMapping("/account")
public class AccountController {

	@Autowired
	AccountService accountService;

    @PostMapping("")
    //public String saveProject(@ModelAttribute Project project, RedirectAttributes redirectAttributes) {
    public RedirectView postAccount(@ModelAttribute Account account, RedirectAttributes redirectAttributes) {
            try {
            // Intentar guardar la cuenta de banco
            	accountService.saveAccount(account);
            // Redirigir a la lista de cuentas de banco o a donde desees
            // Agregamos el mensaje de éxito
            redirectAttributes.addFlashAttribute("message", "La cuenta de banco fue guardada exitosamente.");
            redirectAttributes.addFlashAttribute("messageType", "success");
            
            return new RedirectView("/lista-cuentas");// Cambia esto a tu ruta deseada
	        } catch (IllegalStateException e) {
	            // Capturar el error y redirigir a la página de registro con el mensaje de error
	            redirectAttributes.addFlashAttribute("message", "Hubo un error al registrar/modificar la cuenta de banco: " + e.getMessage());
	            redirectAttributes.addFlashAttribute("messageType", "error");
	            return new RedirectView("/registro-cuenta"); // Redirigir de nuevo al formulario
	        }
    }

}
