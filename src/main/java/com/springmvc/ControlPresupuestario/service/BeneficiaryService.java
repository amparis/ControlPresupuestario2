package com.springmvc.ControlPresupuestario.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springmvc.ControlPresupuestario.model.Beneficiary;
import com.springmvc.ControlPresupuestario.model.ExpenseCategory;
import com.springmvc.ControlPresupuestario.model.Project;
import com.springmvc.ControlPresupuestario.model.UserAdm;
import com.springmvc.ControlPresupuestario.repository.BeneficiaryRepository;

@Service
public class BeneficiaryService {

	@Autowired
    private BeneficiaryRepository beneficiaryRepository;
    @Autowired
    UserAdmService userService;
    @Autowired
    IMyUserDetailsService userDetailsService;

    public List<Beneficiary> getAllBeneficiaries() {
        return beneficiaryRepository.findAll();
    }
    
    public List<Beneficiary> getAllBeneficiariesEstado(String estado) {

    	return beneficiaryRepository.findAllByEstado(estado);
    }
    
    public List<Beneficiary> getAllBeneficiariesEstadoAndTipo(String estado, String tipo) {

    	return beneficiaryRepository.findAllByEstadoAndTipo(estado,tipo);
    }

    public Beneficiary getBeneficiaryById(Integer id) {
        return beneficiaryRepository.findById(id).get();
    }

    public Beneficiary saveBeneficiary(Beneficiary newBeneficiary) {
	    
	    // Verificar si ya existe un proyecto con el mismo nombre, fecha inicio y fecha fin
	    
		boolean exists = beneficiaryRepository.existsByNombresAndApellidosAndDocumento(
				newBeneficiary.getNombres(),newBeneficiary.getApellidos(), newBeneficiary.getDocumento());
	    System.out.println("id ben>> "+newBeneficiary.getId());
	    // Si es una actualización (id ya existe)
	    if (newBeneficiary.getId() != null && newBeneficiary.getId() != 0) {

		    System.out.println("EDIT BEN ");
		    System.out.println(newBeneficiary.getNombres());

	        // Cargar el beneficiario actual desde la base de datos
	        Beneficiary currentBeneficiary = beneficiaryRepository.findById(newBeneficiary.getId()).orElseThrow(() -> 
	            new IllegalArgumentException("Beneficiario no encontrado con ID: " + newBeneficiary.getId())
	        );

	        // Verificar si se han cambiado 'nombre' o 'apellido' o 'documento'
	        boolean nombreCambiado = !currentBeneficiary.getNombres().equalsIgnoreCase(newBeneficiary.getNombres());
	        boolean apellidoCambiado = !currentBeneficiary.getApellidos().equalsIgnoreCase(newBeneficiary.getApellidos());
	        boolean documentoCambiado = !currentBeneficiary.getDocumento().equals(newBeneficiary.getDocumento());

	        // Solo verificar duplicidad si alguno de estos dos campos ha cambiado
	        if (nombreCambiado || documentoCambiado) {
	            // Verificar si ya existe un beneficiario con el mismo nombre y documento
	            if (beneficiaryRepository.existsByNombresAndApellidosAndDocumento(newBeneficiary.getNombres(),newBeneficiary.getApellidos(), newBeneficiary.getDocumento())) {
	                throw new IllegalArgumentException("Ya existe un beneficiario con el mismo nombre completo y documento.");
	            }
	        }
	    } else {
		    System.out.println("NUEVO BEN ");
		    //Validamos si el tipo de beneficiario es Staff
		    String tipoBeneficiario = newBeneficiary.getTipo();
		    if (tipoBeneficiario == "Staff") {
		        // Si es una creación de nuevo beneficiario, hacer la verificación normal
		        if (beneficiaryRepository.existsByNombresAndApellidosAndDocumento(newBeneficiary.getNombres(),newBeneficiary.getApellidos() ,newBeneficiary.getDocumento())) {
		            throw new IllegalArgumentException("Ya existe un beneficiario con el mismo nombre completo y documento.");
		        }
			    newBeneficiary.setNombres(newBeneficiary.getNombres().toUpperCase());
			    newBeneficiary.setApellidos(newBeneficiary.getApellidos().toUpperCase());
		    }
		    else
		    {
		    	newBeneficiary.setRazonSocial(newBeneficiary.getRazonSocial().toUpperCase());
		    }
	    	UserAdm loginUser = userService.getUser(userDetailsService.getUserDetailsService().getId());
		    newBeneficiary.setUsuarioId((int) loginUser.getId());
		    newBeneficiary.setEstado("V");
	        

	    }
        return beneficiaryRepository.save(newBeneficiary);
    }

    public void deleteBeneficiary(Integer id) {
        beneficiaryRepository.deleteById(id);
    }

	public Integer getSupervisorByProject(Long projectId) {
	    // Buscar el supervisor del proyecto (ejemplo basado en tu tabla y entidad UserAdmProject)
		Integer result = beneficiaryRepository.findByProjectIdAndRole(projectId, 2L);
	    return (result != null) ? result : 0; 
	    //return beneficiaryRepository.findByProjectIdAndRole(projectId, 2L).orElse(0);//"SUPERVISOR"); // Ajustar el criterio según tu base de datos
	
	}

	public Integer getResponsableByProject(Long projectId) {
	    // Buscar el responsable del proyecto    
		Integer result = beneficiaryRepository.findByProjectIdAndRole(projectId, 3L);
	    return (result != null) ? result : 0; // Retornar 0 si el resultado es null
	    //return beneficiaryRepository.findByProjectIdAndRole(projectId, 3L).orElse(0);//"RESPONSABLE"); // Ajustar el criterio según tu base de datos
	}
    //para listar los proyectos asignados al Beneficiario
    public List<Project> getProjectsByBeneficiaryId(Integer id) {

    	return beneficiaryRepository.findAllProjectsByBeneficiaryId(id);
    }
    
	public List<Beneficiary> getBeneficiariesByClassExpense(String classExpense) {
		if(classExpense.toUpperCase() =="STAFF") {
			return beneficiaryRepository.findAllBeneficiariesByClassExpense(classExpense);
		}
		else {
			return beneficiaryRepository.findAllBeneficiariesByClassExpense2(classExpense);
		}
		
		
	}
}
