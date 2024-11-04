package com.springmvc.ControlPresupuestario.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springmvc.ControlPresupuestario.model.PaymentPlan;
import com.springmvc.ControlPresupuestario.service.PaymentPlanService;
import com.springmvc.ControlPresupuestario.service.PhaseService;

@Controller
@RequestMapping("/pp")
public class PaymentPlanController {

	@Autowired
	private PaymentPlanService paymentPlanService;	
	@Autowired
	private PhaseService phaseService;
	
    @GetMapping("/registro-pp")
    public String showRegisterFormProject(Model model) {
    		model.addAttribute("payment", new PaymentPlan());
    		model.addAttribute("phases",phaseService.getAllPhases());
    		
    	    return "registro_pp"; // Nombre de la vista Thymeleaf

    }
    	
    @PostMapping("/guardar-pp")
    public String saveProyecto(
           @RequestParam("paymentList") List<String> paymentList,
           @RequestParam("phaseList") List<String> phasesList,
           RedirectAttributes redirectAttributes) {

    	paymentPlanService.savePaymentPlanX(paymentList,phasesList);
    	
    	return "redirect:/pp/registro-pp";
    }

}
