package com.springmvc.ControlPresupuestario.service;

import com.springmvc.ControlPresupuestario.model.Beneficiary;
import com.springmvc.ControlPresupuestario.model.PaymentPlan;
import com.springmvc.ControlPresupuestario.model.Project;
import com.springmvc.ControlPresupuestario.model.ProjectHistory;
import com.springmvc.ControlPresupuestario.model.ProjectPhase;
import com.springmvc.ControlPresupuestario.model.UserAdm;
import com.springmvc.ControlPresupuestario.model.UserAdmProject;
import com.springmvc.ControlPresupuestario.repository.BeneficiaryRepository;
import com.springmvc.ControlPresupuestario.repository.PaymentPlanRepository;
import com.springmvc.ControlPresupuestario.repository.PerfilRepository;
import com.springmvc.ControlPresupuestario.repository.ProjectRepository;
import com.springmvc.ControlPresupuestario.repository.UserAdmProjectRepository;
import com.springmvc.ControlPresupuestario.repository.UserAdmRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {
	
	@Autowired
	ProjectRepository projectRepository;
	@Autowired
	UserAdmProjectRepository userAdmProjectRepository;
	
	@Autowired
	PerfilRepository perfilRepository;
    @Autowired
    UserAdmService userService;
    @Autowired
    UserAdmRepository userRepository;
    @Autowired
    BeneficiaryRepository beneficiaryRepository;
	@Autowired
    private PaymentPlanRepository paymentPlanRepository;
	@Autowired
	ProjectPhaseService projectPhaseService;
	@Autowired
	PhaseService phaseService;
    @Autowired
    IMyUserDetailsService userDetailsService;
    
    @Autowired
    ProjectHistoryService projectHistoryService;
    //@Autowired
   // PaymentPlanService paymentPlanService;
	
	public List<Project> getProjects(){
		//return this.projectRepository.findAll();//deberia listar solo los vigentes
        return this.projectRepository.findByEstado("V");  // Listar solo proyectos con estado 'v'
	}
	
	public Project getProject(long id) {
		
		return this.projectRepository.findById(id).get();
	}
	
	//public Project getProjectById(Long id);
 	public Project saveProject(Project newProject, 
			Integer beneficiaryIdSupervisor,Integer beneficiaryIdResponsable,
			Long projectId, 
			List<String>  paymentPlan,
			List<String>  phasesList
			) {
	    if (projectId == null) {
			// Verificar si ya existe un proyecto con el mismo nombre, fecha inicio y fecha fin    
			boolean exists = projectRepository.existsByNombreAndFechaInicialAndFechaFin(
		            newProject.getNombre(), newProject.getFechaInicial(), newProject.getFechaFin());
	
		    if (exists) {
		        throw new IllegalStateException("Ya existe un proyecto con el mismo nombre, fecha de inicial y fecha de fin.");
		    }
			
			newProject.setFechaCreacion(new Date(System.currentTimeMillis()));// proy_fechacreacion
			newProject.setEstado("V");
		    // Obtener el usuario logueado
		    UserAdm loginUser = userService.getUser(userDetailsService.getUserDetailsService().getId());
		    newProject.setUs_id(loginUser.getId());
		    
			    // Guardar el nuevo proyecto en la base de datos
			    Project savedProject = projectRepository.save(newProject);
			    
			    //Obtener los datos para historico de proyecto
		        ProjectHistory savedProjectHistory= new ProjectHistory();
		        savedProjectHistory.setAmount(newProject.getMontoContrato());
		        savedProjectHistory.setObservation(" "); // en el caso que sea primera vez, creacion del py
		        //caso contrario.. se registra el motivo y doc q avala la modificacion del contrato adenda
		    	long millis = System.currentTimeMillis();
		        savedProjectHistory.setRegistrationDate(new Timestamp(millis));
		        savedProjectHistory.setStartDate(newProject.getFechaInicial());
		        savedProjectHistory.setEndDate(newProject.getFechaFin());
		        savedProjectHistory.setStatus("V");
		        savedProjectHistory.setDetail("El usuario: "+ loginUser.getFullname()+", creo el proyecto.");
		        savedProjectHistory.setUs_id(loginUser.getId());
		        savedProjectHistory.setProject(newProject);
	            projectHistoryService.saveProjectHistory(savedProjectHistory);

		    System.out.println("valor de beneficiaryIdSupervisor  "+beneficiaryIdSupervisor);
		    System.out.println("valor de beneficiaryIdResponsable  "+beneficiaryIdResponsable);
	        // Vincular supervisor y responsable solo si fueron asignados
	        if (beneficiaryIdSupervisor != 0) {
	            Long rolSupervisor = 2L;  // Rol de supervisor
	            Long userIdSupervisor = getOrCreateUserForBeneficiary(beneficiaryIdSupervisor, rolSupervisor);
	            linkUserToProject(savedProject, userIdSupervisor);
	        }

	        if (beneficiaryIdResponsable != 0) {
	            Long rolResponsable = 3L;  // Rol de responsable
	            Long userIdResponsable = getOrCreateUserForBeneficiary(beneficiaryIdResponsable, rolResponsable);
	            linkUserToProject(savedProject, userIdResponsable);
	        }
	        // Procesar el paymentPlan
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
		                savedPaymentPlan.setProyecto(savedProject); // Cambia 1L por el ID real del proyecto
		                savedPaymentPlan.setDescripcion(descripcion); // Guardar la descripción
		                savedPaymentPlan.setMonto(amount); // Guardar el monto
		                savedPaymentPlan.setMontoPagado(0);
		                savedPaymentPlan.setEstado("PENDING");
		                // Guardar el PaymentPlan
		                paymentPlanRepository.save(savedPaymentPlan);
		            } else {
		                throw new IllegalArgumentException("Formato de plan de pago incorrecto: " + singlePayment);
		            }
		        }
		    }
	 	   //Procesar phase by project
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
		                savedProjectPhase.setProyecto(savedProject); // Cambia 1L por el ID real del proyecto
		                savedProjectPhase.setFase(phaseService.getPhaseByNombre(descripcion)); // Guardar la descripción
		                savedProjectPhase.setPresupuesto(amount); // Guardar el monto

		                // Guardar el PaymentPlan
		                projectPhaseService.saveProjectPhase(savedProjectPhase);
		            } else {
		                throw new IllegalArgumentException("Formato de phases incorrecto: " + singlePhase);
		            }
		        }
		    }
	    }
	    /*else {
		    // Guardar el nuevo proyecto en la base de datos
			Project ProjectUpdate = this.projectRepository.findById(projectId).get();
			
			if (this.projectRepository.findById(projectId).isPresent()) {
		    	 newProject.setEstado("V");
			    
			    if(newProject.getNombre() != null) ProjectUpdate.setNombre(newProject.getNombre());
				if(newProject.getDescripcion()!= null) ProjectUpdate.setDescripcion(newProject.getDescripcion());
				
				if(newProject.getMontoContrato()!= 0) ProjectUpdate.setMontoContrato(newProject.getMontoContrato());
    			if(newProject.getPorcentajeRecurrente()!= 0) ProjectUpdate.setPorcentajeRecurrente(newProject.getPorcentajeRecurrente());
				if(newProject.getMontoRecurrente()!=0) ProjectUpdate.setMontoContrato(newProject.getMontoRecurrente());
				if(newProject.getMontoNoRecurrente()!= 0) ProjectUpdate.setMontoNoRecurrente(newProject.getMontoNoRecurrente());
				if(newProject.getFechaCreacion()!= null) ProjectUpdate.setFechaCreacion(newProject.getFechaCreacion());
				if(newProject.getFechaInicial()!= null) ProjectUpdate.setFechaInicial(newProject.getFechaInicial());
				if(newProject.getFechaFin()!= null) ProjectUpdate.setFechaFin(newProject.getFechaFin());
			    UserAdm loginUser = userService.getUser(userDetailsService.getUserDetailsService().getId());
				if(newProject.getUs_id()!= 0) ProjectUpdate.setUs_id(newProject.getUs_id());
			
			// Verificar o crear un usuario asociado al beneficiario y enlazarlo al proyecto
	            // Actualizar vínculo con supervisor y responsable solo si fueron asignados
	            if (beneficiaryIdSupervisor != 0) {
	                Long rolSupervisor = 2L;  // Rol de supervisor
	                Long userIdSupervisor = getOrCreateUserForBeneficiary(beneficiaryIdSupervisor, rolSupervisor);
	                linkUserToProject(ProjectUpdate, userIdSupervisor);
	            }

	            if (beneficiaryIdResponsable != 0) {
	                Long rolResponsable = 3L;  // Rol de responsable
	                Long userIdResponsable = getOrCreateUserForBeneficiary(beneficiaryIdResponsable, rolResponsable);
	                linkUserToProject(ProjectUpdate, userIdResponsable);
	            }
	            
            
		        ProjectHistory savedProjectHistory= new ProjectHistory();
		        savedProjectHistory.setAmount(newProject.getMontoContrato());
		        savedProjectHistory.setObservation("cxcxc"); // en el caso que sea primera vez, creacion del py
		        //caso contrario.. se registra el motivo y doc q avala la modificacion del contrato adenda
		    	long millis = System.currentTimeMillis();
		        savedProjectHistory.setRegistrationDate(new Timestamp(millis));
		        savedProjectHistory.setStartDate(newProject.getFechaInicial());
		        savedProjectHistory.setEndDate(newProject.getFechaFin());
		        savedProjectHistory.setStatus("V");
		        savedProjectHistory.setDetail("El usuario: "+ loginUser.getFullname()+", EDITO el proyecto.");
		        savedProjectHistory.setUs_id(loginUser.getId());
		        savedProjectHistory.setProject(newProject);
	            projectHistoryService.saveProjectHistory(savedProjectHistory);
		    
			}
	    }
	    */
	    return projectRepository.save(newProject);
	}
	
	private Long getOrCreateUserForBeneficiary(Integer beneficiaryId, long rolId) {
	    Optional<UserAdm> userAdmOptional = userService.getUserByBeneficiaryId(beneficiaryId);

	    if (userAdmOptional.isPresent()) {
	        return userAdmOptional.get().getId();
	    } else {
	        // Obtener datos del beneficiario
	        Beneficiary beneficiary = beneficiaryRepository.findById(beneficiaryId)
	                .orElseThrow(() -> new IllegalStateException("Beneficiario no encontrado"));

	        // Crear el nuevo usuario
	        UserAdm newUser = new UserAdm();
	        newUser.setUsernamee(beneficiary.getDocumento());
	        newUser.setFullname(beneficiary.getNombres() +" "+ beneficiary.getApellidos());
	        newUser.setEmail(beneficiary.getEmail());
	        newUser.setPassword(beneficiary.getDocumento()); // Usa el documento como password temporal
	        newUser.setPerfil(perfilRepository.findById(rolId).orElseThrow()); // Asignar el rol con id 2 (SUPERVISOR)
	        newUser.setBeneficiario(beneficiary);
	        userService.saveUser(newUser);

	        return newUser.getId();
	    }
	}

	private void linkUserToProject(Project project, Long userId) {
		// Verificar que el proyecto no sea nulo
	    if (project == null) {
	        throw new IllegalArgumentException("El proyecto no puede ser nulo");
	    }
	    
	    // Intentar obtener el usuario
	    UserAdm userAdm = userService.getUser(userId);//.orElseThrow(() -> 
	       //////// new RuntimeException("No se encontró un usuario con ID: " + userId)
	   // );

	    // Crear el vínculo
	    UserAdmProject userAdmProject = new UserAdmProject();
	    userAdmProject.setProject(project);
	    userAdmProject.setUserAdm(userAdm);
	    userAdmProject.setEstado("V"); // Estado inicial del vínculo
	    userAdmProjectRepository.save(userAdmProject);
	}

	public void updateSimpleProject(long id, Project newProject) {
		
		Project ProjectUpdate = this.projectRepository.findById(id).get();
		
		if (this.projectRepository.findById(id).isPresent()) {
			
			if(newProject.getNombre() != null) ProjectUpdate.setNombre(newProject.getNombre());
			if(newProject.getDescripcion()!= null) ProjectUpdate.setDescripcion(newProject.getDescripcion());

			this.projectRepository.save(ProjectUpdate);
		}
		
	}
	
	public void updateCriticProject(long id, Project newProject,Double valorModificado,Double newMontoRecurrente,Double newMontoNoRecurrente, String justificacionEdicion ) {
		
		Project ProjectUpdate = this.projectRepository.findById(id).get();

		if (this.projectRepository.findById(id).isPresent()) {
			UserAdm loginUser = userService.getUser(userDetailsService.getUserDetailsService().getId());
	        StringBuilder detalleEdit = new StringBuilder();  // Usamos StringBuilder para concatenar detalles

			long  millis = System.currentTimeMillis();
			
	        // Verificando si la fecha inicial ha cambiado
	        if (newProject.getFechaInicial() != null && !newProject.getFechaInicial().equals(ProjectUpdate.getFechaInicial())) {
	            detalleEdit.append(String.format("La fecha inicial fue modificada de %s a %s. ", 
	            		ProjectUpdate.getFechaInicial(), newProject.getFechaInicial()));
	            ProjectUpdate.setFechaInicial(newProject.getFechaInicial());
	        }

	        // Verificando si la fecha final ha cambiado
	        if (newProject.getFechaFin() != null && !newProject.getFechaFin().equals(ProjectUpdate.getFechaFin())) {
	            detalleEdit.append(String.format("La fecha final fue modificada de %s a %s. ", 
	            		ProjectUpdate.getFechaFin(), newProject.getFechaFin()));
	            ProjectUpdate.setFechaFin(newProject.getFechaFin());
	        }

			if (valorModificado == 0) {
				
				if(newProject.getMontoContrato()!= 0) ProjectUpdate.setMontoContrato(newProject.getMontoContrato());
				if(newProject.getPorcentajeRecurrente()!= 0) ProjectUpdate.setPorcentajeRecurrente(newProject.getPorcentajeRecurrente());
				if(newProject.getMontoRecurrente()!=0) ProjectUpdate.setMontoContrato(newProject.getMontoRecurrente());
				if(newProject.getMontoNoRecurrente()!= 0) ProjectUpdate.setMontoNoRecurrente(newProject.getMontoNoRecurrente());
	
				this.projectRepository.save(ProjectUpdate);
			}
			else {
				ProjectUpdate.setMontoContrato(newProject.getMontoContrato()+valorModificado);
				if (valorModificado>0) {
		            detalleEdit.append(String.format("Se incrementó: %.2f, dando como resultado Monto Contrato nuevo=%.2f , con porcentaje=%.2f, Monto Recurrente= %.2f y Monto no Recurrente= %.2f. <br> ", 
	                		valorModificado,newProject.getMontoContrato()+valorModificado,newProject.getPorcentajeRecurrente() , newMontoRecurrente, newMontoNoRecurrente));					
	                }
				if (valorModificado < 0) {
				    detalleEdit.append(String.format("Se disminuyó: %.2f, dando como resultado Monto Contrato nuevo=%.2f , con porcentaje=%.2f, Monto Recurrente= %.2f y Monto no Recurrente= %.2f. <br>",
				    		valorModificado,newProject.getMontoContrato()+valorModificado,newProject.getPorcentajeRecurrente() , newMontoRecurrente, newMontoNoRecurrente));	
				}
				
				ProjectUpdate.setMontoRecurrente(newMontoRecurrente);
				ProjectUpdate.setMontoNoRecurrente(newMontoNoRecurrente);
				}
			// Guardar cambios en el proyecto
			this.projectRepository.save(ProjectUpdate);
			
		        ProjectHistory savedProjectHistory= new ProjectHistory();
		        savedProjectHistory.setAmount(valorModificado);
		        savedProjectHistory.setObservation(justificacionEdicion); //caso contrario.. se registra el motivo y doc q avala la modificacion del contrato adenda
		        savedProjectHistory.setRegistrationDate(new Timestamp(millis));
		        savedProjectHistory.setStartDate(newProject.getFechaInicial());
		        savedProjectHistory.setEndDate(newProject.getFechaFin());
		        savedProjectHistory.setStatus("V");
		        // Si hubo modificaciones en las fechas o montos, agregar ese detalle
		        if (detalleEdit.length() > 0) {
		            savedProjectHistory.setDetail(loginUser.getFullname() + ",Modificó lo siguiente: <br>" + detalleEdit.toString());
		        } else {
		            savedProjectHistory.setDetail(loginUser.getFullname() + ",No realizó cambios significativos.");
		        }
		        savedProjectHistory.setUs_id(loginUser.getId());
		        savedProjectHistory.setProject(newProject);
	            projectHistoryService.saveProjectHistory(savedProjectHistory);

		}
		 //else {
			//    throw new ProjectNotFoundException("Project with ID " + id + " not found.");
		//	}
		
	}

	public void updatePersonalProject(long id, 
										Integer SupervisorAsignadoId, Integer ResponsableAsignadoId,
										Integer beneficiaryIdSupervisorNew,Integer beneficiaryIdResponsableNew,Project newProject) {
		
		Project ProjectUpdate = this.projectRepository.findById(id).get();
		
		if (this.projectRepository.findById(id).isPresent()) {
			//System.out.println("entramos al service ....");
			//Preguntamos si hubo alguna seleccion de personal
			if(beneficiaryIdSupervisorNew >0 || beneficiaryIdResponsableNew >0) {

		        // Vincular supervisor y responsable solo si fueron asignados
		        if (beneficiaryIdSupervisorNew != 0) {//si quiero modificar supervisor
		        	if(SupervisorAsignadoId == 0) {//existia un supervisor antes, no, entonces inserto NUEVO supervisor
			            
			        	Long rolSupervisor = 2L;  // Rol de supervisor
			            Long userIdSupervisor = getOrCreateUserForBeneficiary(beneficiaryIdSupervisorNew, rolSupervisor);
			            linkUserToProject(ProjectUpdate, userIdSupervisor);
		        	}
		        	else {// MODIFICO el supervisor actual por el seleccionado
			        	//de UserAdm, quito ben_id
		        		Long rolSupervisor = 2L;  // Rol de supervisor
		                UserAdm userUpdate = this.userRepository.findByBeneficiario(SupervisorAsignadoId).get();

		                if (this.userRepository.findByBeneficiario(SupervisorAsignadoId).isPresent()) {
		                	//obtengo us_id
		                	long usIdSupervisor= userUpdate.getId();
		                	System.out.println("PRINT ...."+usIdSupervisor);
		                	long millis = System.currentTimeMillis();
		                    userUpdate.setDateUpdate(new Timestamp(millis));
		                    userUpdate.setBeneficiario(null);                  
		                    this.userRepository.save(userUpdate);
		                    
			        	    //de UserAdmProject, el supervisor actual, cambio estao C *	    	    
				    	    UserAdmProject userAdmProjectUpdate = this.userAdmProjectRepository.findByProyectoIdAndUsuarioId(id,usIdSupervisor);
				    	    userAdmProjectUpdate.setEstado("C"); // Estado Cesante
				    	    userAdmProjectRepository.save(userAdmProjectUpdate);
				    	    
			        	    //de UserAdmProject nuevo registro
				            Long userIdSupervisorNew = getOrCreateUserForBeneficiary(beneficiaryIdSupervisorNew, rolSupervisor);
				            linkUserToProject(ProjectUpdate, userIdSupervisorNew);

		                }
		        	}

		        }
		        if (beneficiaryIdResponsableNew != 0)
		        {//si quiero modificar responsable
		        	if(ResponsableAsignadoId == 0) {//existia un responsable antes, no, entonces inserto NUEVO responsable
		            
			        	Long rolResponsable = 3L;  // Rol de responsable
			            Long userIdResponsable = getOrCreateUserForBeneficiary(beneficiaryIdResponsableNew, rolResponsable);
			            linkUserToProject(ProjectUpdate, userIdResponsable);
		        	}
		        	else {// MODIFICO el responsable actual por el seleccionado
			        	//de UserAdm, quito ben_id
		        		Long rolResponsable = 3L;  // Rol de responsable
		                UserAdm userUpdate = this.userRepository.findByBeneficiario(ResponsableAsignadoId).get();

		                if (this.userRepository.findByBeneficiario(ResponsableAsignadoId).isPresent()) {
		                	//obtengo us_id
		                	long usIdResponsable= userUpdate.getId();
		                    
		                	long millis = System.currentTimeMillis();
		                    userUpdate.setDateUpdate(new Timestamp(millis));
		                    userUpdate.setBeneficiario(null);                  
		                    this.userRepository.save(userUpdate);
		                    
			        	    //de UserAdmProject, el responsable actual, cambio estao C *	    	    
				    	    UserAdmProject userAdmProjectUpdate = this.userAdmProjectRepository.findByProyectoIdAndUsuarioId(id,usIdResponsable);
				    	    userAdmProjectUpdate.setEstado("C"); // Estado Cesante
				    	    userAdmProjectRepository.save(userAdmProjectUpdate);
				    	    
			        	    //de UserAdmProject nuevo registro
				            Long userIdResponsableNew = getOrCreateUserForBeneficiary(beneficiaryIdResponsableNew, usIdResponsable);
				            linkUserToProject(ProjectUpdate, userIdResponsableNew);

		                }
		        	}

		        }

			}//fin de preguntar si existira sup y resp nuevo
		}//fin de if pregunta si existe el py
	}

	public void deleteProject(long id) {
		
		this.projectRepository.deleteById(id);
	}

}
