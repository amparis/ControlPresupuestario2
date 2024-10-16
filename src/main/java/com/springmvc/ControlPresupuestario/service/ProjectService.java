package com.springmvc.ControlPresupuestario.service;

import com.springmvc.ControlPresupuestario.model.Beneficiary;
import com.springmvc.ControlPresupuestario.model.Project;
import com.springmvc.ControlPresupuestario.model.ProjectHistory;
import com.springmvc.ControlPresupuestario.model.UserAdm;
import com.springmvc.ControlPresupuestario.model.UserAdmProject;
import com.springmvc.ControlPresupuestario.repository.BeneficiaryRepository;
import com.springmvc.ControlPresupuestario.repository.PerfilRepository;
import com.springmvc.ControlPresupuestario.repository.ProjectRepository;
import com.springmvc.ControlPresupuestario.repository.UserAdmProjectRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    BeneficiaryRepository beneficiaryRepository;
    
    @Autowired
    IMyUserDetailsService userDetailsService;
    
    @Autowired
    ProjectHistoryService projectHistoryService;
	
	public List<Project> getProjects(){
		
		//return this.projectRepository.findAll();//deberia listar solo los vigentes
        return this.projectRepository.findByEstado("V");  // Listar solo proyectos con estado 'v'

	}
	
	public Project getProject(long id) {
		
		return this.projectRepository.findById(id).get();
	}
	
	//public Project getProjectById(Long id);
	
	public Project saveProject(Project newProject, Integer beneficiaryIdSupervisor,Integer beneficiaryIdResponsable,Long projectId) {
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
		    
	    }
	    else {
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
		    /*Long rol1=2L;
		    Long userIdSupervisor = getOrCreateUserForBeneficiary(beneficiaryIdSupervisor, rol1);
		    // Insertar en tbl_usuario_proyecto
		    linkUserToProject(ProjectUpdate, userIdSupervisor);
		    
		    Long rol2=3L;
		    Long userIdResponsable = getOrCreateUserForBeneficiary(beneficiaryIdResponsable, rol2);
		    // Insertar en tbl_usuario_proyecto
		    linkUserToProject(ProjectUpdate, userIdResponsable);
		    */
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
	        newUser.setFullname(beneficiary.getNombreCompleto());
	        newUser.setEmail(beneficiary.getEmail());
	        newUser.setPassword(beneficiary.getDocumento()); // Usa el documento como password temporal
	        newUser.setPerfil(perfilRepository.findById(rolId).orElseThrow()); // Asignar el rol con id 2 (SUPERVISOR)
	        newUser.setBeneficiario(beneficiary);
	        userService.saveUser(newUser);

	        return newUser.getId();
	    }
	}

	private void linkUserToProject(Project project, Long userId) {
	    /*UserAdmProject userAdmProject = new UserAdmProject();
	    userAdmProject.setProject(project);
	    userAdmProject.setUserAdm(userService.getUser(userId).orElseThrow());
	    userAdmProject.setEstado("V"); // Estado inicial del vínculo
	    */
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

	public void updateProject(long id, Project newProject) {
		
		Project ProjectUpdate = this.projectRepository.findById(id).get();
		
		if (this.projectRepository.findById(id).isPresent()) {
			
			long  millis = System.currentTimeMillis();
			
			//if(newProject.getNombre() != null) ProjectUpdate.setNombre(newProject.getNombre());
			
			//if(newProject.getDescripcion()!= null) ProjectUpdate.setDescripcion(newProject.getDescripcion());
			
			//if(newProject.getMontoContrato()!= null) ProjectUpdate.setMontoContrato(newProject.getMontoContrato());
			
			//newProject.setFechaActualizacion(new Date(millis));
			
		    // Obtener el usuario logueado
		    UserAdm loginUser = userService.getUser(userDetailsService.getUserDetailsService().getId());
		    // Asignar el id del usuario logueado al campo us_id del Project
		    newProject.setUs_id(loginUser.getId());

			this.projectRepository.save(ProjectUpdate);
		}
		
		
		
	}
	
	public void deleteProject(long id) {
		
		this.projectRepository.deleteById(id);
	}

}
