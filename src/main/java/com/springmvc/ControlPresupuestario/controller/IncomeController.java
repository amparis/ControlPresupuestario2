package com.springmvc.ControlPresupuestario.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.springmvc.ControlPresupuestario.model.Income;
import com.springmvc.ControlPresupuestario.service.IncomeService;

@RestController
@RequestMapping("/income")
public class IncomeController {

	@Autowired
	IncomeService incomeService;

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
}
