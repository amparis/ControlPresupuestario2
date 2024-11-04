package com.springmvc.ControlPresupuestario.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springmvc.ControlPresupuestario.model.Scale;
import com.springmvc.ControlPresupuestario.service.ScaleService;

@Controller
@RequestMapping("/scales")
public class ScaleController {

	@Autowired
	private ScaleService scaleService;
	
	   // Guardar un nuevo Beneficiario
    @PostMapping("/guardar-cargo/{id}")
    public String saveCargo(@PathVariable("id") Long id,
    		@ModelAttribute Scale scale, 
    		RedirectAttributes redirectAttributes) {
        try {
        	scaleService.saveScale(scale);
            redirectAttributes.addFlashAttribute("message", "Scale saved successfully!");
            redirectAttributes.addFlashAttribute("messageType", "success");
            return (id > 0) ? "redirect:/expenses/registro-egreso/" + id : "redirect:/scales/lista-cargos";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Error saving scale: " + e.getMessage());
            redirectAttributes.addFlashAttribute("messageType", "error");
            return (id > 0) ? "redirect:/expenses/registro-egreso/" + id : "redirect:/scale/registro-cargos";
        }
    }
}
