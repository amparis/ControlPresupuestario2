package com.springmvc.ControlPresupuestario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springmvc.ControlPresupuestario.model.PaymentPlan;
import com.springmvc.ControlPresupuestario.model.ProjectPhase;
import com.springmvc.ControlPresupuestario.repository.PaymentPlanRepository;
import com.springmvc.ControlPresupuestario.repository.ProjectRepository;

@Service
public class PaymentPlanService {

	@Autowired
    private PaymentPlanRepository paymentPlanRepository;
    
	@Autowired
	ProjectRepository projectRepository;
    
	@Autowired
	ProjectService projectService;
	@Autowired
	PhaseService phaseService;
	@Autowired
	ProjectPhaseService projectPhaseService;
	
    public List<PaymentPlan> getAllPaymentPlan() {
        return paymentPlanRepository.findAll();
    }
    public PaymentPlan getPaymentPlanById(long id) {
        return paymentPlanRepository.findById(id).get();
    }
    public List<PaymentPlan>  getPaymentPlanByProjectId(Long id) {
        return paymentPlanRepository.findPaymentPlanByProjectId(id);
    }
    public PaymentPlan savePaymentPlan(PaymentPlan newPaymentPlan) {
	    
    	
    	return paymentPlanRepository.save(newPaymentPlan);
    }
   public void savePaymentPlanX(List<String>  paymentPlan,
		   List<String>  phasesList) {
	    
	   for (String payment : paymentPlan) {
	        // Dividir cada elemento del paymentPlan por ';' para obtener cada pago
	        String[] payments = payment.split(";"); // Dividir por punto y coma

	        for (String singlePayment : payments) {
	            // Dividir cada elemento en descripción y monto usando '->'
	            String[] parts = singlePayment.split("->", 2); // Limitar a dos partes
	            if (parts.length == 2) {
	                String descripcion = parts[0].trim();  // Parte antes de '->'
	                String amountStr = parts[1].trim();    // Parte después de '->'
	                double amount;
	                try {
	                    amount = Double.parseDouble(amountStr); // Convertir a double
	                } catch (NumberFormatException e) {
	                    throw new IllegalArgumentException("El monto no es un número válido: " + amountStr, e);
	                }

	                // Crear un nuevo objeto PaymentPlan
	                PaymentPlan savedPaymentPlan = new PaymentPlan();
	                savedPaymentPlan.setProyecto(projectService.getProject(1L)); // Cambia 1L por el ID real del proyecto
	                savedPaymentPlan.setDescripcion(descripcion); // Guardar la descripción
	                savedPaymentPlan.setMonto(amount); // Guardar el monto

	                // Guardar el PaymentPlan
	                paymentPlanRepository.save(savedPaymentPlan);
	            } else {
	                throw new IllegalArgumentException("Formato de plan de pago incorrecto: " + singlePayment);
	            }
	        }
	    }
 //////////////
	   for (String phase : phasesList) {
	        // Dividir cada elemento del paymentPlan por ';' para obtener cada pago
	        String[] phases = phase.split(";"); // Dividir por punto y coma

	        for (String singlePhase : phases) {
	            // Dividir cada elemento en descripción y monto usando '->'
	            String[] parts = singlePhase.split("-> Amount = ", 2); // Limitar a dos partes
	            if (parts.length == 2) {
	                String descripcion = parts[0].trim();  // Parte antes de '-> Amount = '
	                String amountStr = parts[1].trim();    // Parte después de '-> Amount = '
	                double amount;
	                try {
	                    amount = Double.parseDouble(amountStr); // Convertir a double
	                } catch (NumberFormatException e) {
	                    throw new IllegalArgumentException("El monto no es un número válido: " + amountStr, e);
	                }

	                // Crear un nuevo objeto PaymentPlan
	                ProjectPhase savedProjectPhase = new ProjectPhase();
	                savedProjectPhase.setProyecto(projectService.getProject(1L)); // Cambia 1L por el ID real del proyecto
	                savedProjectPhase.setFase(phaseService.getPhaseByNombre(descripcion)); // Guardar la descripción
	                savedProjectPhase.setPresupuesto(amount); // Guardar el monto

	                // Guardar el PaymentPlan
	                projectPhaseService.saveProjectPhase(savedProjectPhase);
	            } else {
	                throw new IllegalArgumentException("Formato de phases incorrecto: " + singlePhase);
	            }
	        }
	    }
	   //return paymentPlanRepository.save(savedPaymentPlan);
    }

    public void deletePhase(Long id) {
    	paymentPlanRepository.deleteById(id);
    }

}
