package com.springmvc.ControlPresupuestario.controller;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springmvc.ControlPresupuestario.service.CalculoTiempoService;

@RestController
//@RequestMapping("/api")
public class CalculoTiempoController {

	@Autowired
    private CalculoTiempoService dateCalculationService;
	
    @PostMapping("/calculate/{fechaInicio}/{fechaFin}")
 public Integer calculateDays(
	        @PathVariable("fechaInicio") String fechaInicio,
	        @PathVariable("fechaFin") String fechaFin) {
		// @RequestParam String fechaInicio, @RequestParam String fechaFin) {
        System.out.println("Fecha Inicial: " + fechaInicio);
        System.out.println("Fecha Final: " +fechaFin);
        Date startDate = Date.valueOf(fechaInicio);
        Date endDate = Date.valueOf(fechaFin);
        return dateCalculationService.calculateDaysBetweenDates(startDate, endDate);
    }

}
