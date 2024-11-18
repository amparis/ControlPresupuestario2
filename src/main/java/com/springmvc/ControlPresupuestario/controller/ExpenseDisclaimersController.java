package com.springmvc.ControlPresupuestario.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springmvc.ControlPresupuestario.model.Beneficiary;
import com.springmvc.ControlPresupuestario.model.EgresoKey;
import com.springmvc.ControlPresupuestario.model.EgresoProjectKey;
import com.springmvc.ControlPresupuestario.model.Expense;
import com.springmvc.ControlPresupuestario.model.ExpenseCategory;
import com.springmvc.ControlPresupuestario.model.ExpenseDisclaimers;
import com.springmvc.ControlPresupuestario.model.Project;
import com.springmvc.ControlPresupuestario.model.Scale;
import com.springmvc.ControlPresupuestario.model.UserAdm;
import com.springmvc.ControlPresupuestario.service.AccountService;
import com.springmvc.ControlPresupuestario.service.ExpenseCategoryService;
import com.springmvc.ControlPresupuestario.service.ExpenseDisclaimersService;
import com.springmvc.ControlPresupuestario.service.ExpenseService;
import com.springmvc.ControlPresupuestario.service.IMyUserDetailsService;
import com.springmvc.ControlPresupuestario.service.PefilService;
import com.springmvc.ControlPresupuestario.service.PhaseService;
import com.springmvc.ControlPresupuestario.service.ProjectPhaseService;
import com.springmvc.ControlPresupuestario.service.ProjectService;
import com.springmvc.ControlPresupuestario.service.RolMenuService;
import com.springmvc.ControlPresupuestario.service.UnitOfMeasurementService;
import com.springmvc.ControlPresupuestario.service.UserAdmService;

@Controller
@RequestMapping("/gastos")
public class ExpenseDisclaimersController {

	@Autowired
	ExpenseService expenseService;
    @Autowired
    PefilService pefilService;
    @Autowired
    UserAdmService userService;
    @Autowired
    IMyUserDetailsService userDetailsService;
    @Autowired
    RolMenuService rolMenuService;
    @Autowired
    AccountService accountService;
    @Autowired
    ProjectService projectService;
    
	@Autowired
	ProjectPhaseService projectPhaseService;
	@Autowired
	PhaseService phaseService;
	@Autowired
	ExpenseDisclaimersService expenseDisclaimersService;
	@Autowired
	UnitOfMeasurementService unitOfMeasurementService;
	@Autowired
	ExpenseCategoryService expenseCategoryService;

	private final String DIRECTORIO_BASE = "D:/SISTEMAS_integrales/descargosStaff";
   
	   @GetMapping("/registro-descargo/{id}")
	    public String showRegisterFormDescargo( @PathVariable("id") long projectId, Model model) {
	        model.addAttribute("loginUser", this.userService.getUser(userDetailsService.getUserDetailsService().getId()));
	        model.addAttribute("menuRoles",this.rolMenuService.getAllRolMenusByRoleId());
	        model.addAttribute("menuRoles2",this.rolMenuService.getRolMenusByRoleId());
		   
	        model.addAttribute("project", projectService.getProject(projectId)); 
	        Integer project_Id = (int) projectId;
	        model.addAttribute("expenseTransferred",expenseDisclaimersService.getSummaryExpenseVoucherByProyectoId(project_Id));
		       // Obtener la lista de fases con descargo del servicio
	        List<Expense> expensesWithDescargo = expenseService.getExpensesVigentesWithDescargoByProjectId(projectId);
	     // Extrae solo los IDs de fase asociados a cada Expense
	        List<Long> phasesList = expensesWithDescargo.stream()
	            .map(expense -> expense.getProyectoFase().getFase().getId()) 
	            .distinct() // Opcional: para evitar IDs duplicados
	            .collect(Collectors.toList());
	       
	        model.addAttribute("gastoDescargos", expenseDisclaimersService.getExpenseDisclaimersByProjectId(projectId));
	        
	        List<ExpenseDisclaimers> descargosByProject =  expenseDisclaimersService.getExpenseDisclaimersByProjectId(projectId);
	        double totalAmountDescargos = descargosByProject.stream().mapToDouble(ExpenseDisclaimers::getCostoTotal).sum();
	        double totalAmountDescargosUSD = descargosByProject.stream().mapToDouble(ExpenseDisclaimers::getTotalUSD).sum();
	        model.addAttribute("totalAmountDescargos", totalAmountDescargos);
	        model.addAttribute("totalAmountDescargosUSD", totalAmountDescargosUSD);
	       
	       // List<ExpenseDisclaimers> descargosByProject =  expenseDisclaimersService.getExpenseDisclaimersByProjectId(projectId);
	        Map<String, Map<EgresoProjectKey, List<ExpenseDisclaimers>>> groupedGastos = descargosByProject.stream()
	        	    .collect(Collectors.groupingBy(
	        	        descargo -> {             // Determinar si el tipo es 'LEGAL ENTITY' o 'NATURAL PERSON' y asignar el nombre correspondiente
	                    if ("LEGAL ENTITY".equals(descargo.getEgreso().getBeneficiario().getTipo())) {
	                        return descargo.getEgreso().getBeneficiario().getRazonSocial()+'_'+descargo.getEgreso().getBeneficiario().getId(); // Si es entidad legal, usa la razón social
	                    } else {
	                        // Si es persona natural, combina los nombres y apellidos
	                        return descargo.getEgreso().getBeneficiario().getNombres() + " " + descargo.getEgreso().getBeneficiario().getApellidos()+'_'+descargo.getEgreso().getBeneficiario().getId();
	                    }
	                },
	        	        Collectors.groupingBy(
	        	            descargo -> {
	        	                String beneficiarioFullName;
	        	                // Condición para verificar si el tipo es 'LEGAL ENTITY'
	        	                if ("LEGAL ENTITY".equals(descargo.getEgreso().getBeneficiario().getTipo())) {
	        	                    beneficiarioFullName = descargo.getEgreso().getBeneficiario().getRazonSocial(); // Usar razonSocial si es entidad legal
	        	                } else {
	        	                    beneficiarioFullName = descargo.getEgreso().getBeneficiario().getNombres() + " " + descargo.getEgreso().getBeneficiario().getApellidos(); // Nombres y apellidos
	        	                }

	        	                // Crear la clave EgresoProjectKey con el nombre o razón social del beneficiario
	        	                return new EgresoProjectKey(
	        	                    descargo.getEgreso().getBeneficiario().getId(),     // beneficiarioId
	        	                    beneficiarioFullName,                               // nombre completo o razón social
	        	                    descargo.getEgreso().getId(),                       // expenseId
	        	                    descargo.getEgreso().getClasificacionEgreso().getNombreClase(),
	        	                    descargo.getEgreso().getCargoItem()                 // nombreClaseCargoItem
	        	                );
	        	            }
	        	        )
	        	    ));

	        	model.addAttribute("gastosGroupedByBeneficiaryAndExpense", groupedGastos);

		    //model.addAttribute("expenseDescargos",expenseService.getExpensesVigentesWithDescargoByProjectIdAndBeneficiaryId(projectId, null));
		    model.addAttribute("fasesByProyecto",phaseService.getPhaseByListId(phasesList));
	        ///////////////////////////////////////////////////////////////////////////////////////////////
		 // Calcular subtotales
		    Map<EgresoProjectKey, Map<String, BigDecimal>> subtotalesPorGrupo = new HashMap<>();
		    groupedGastos.forEach((key, gastosMap) -> {
		        // Iterar sobre las entradas del mapa, donde la clave es EgresoProjectKey
		        gastosMap.forEach((egresoProjectKey, gastosList) -> { // egresoProjectKey es EgresoProjectKey
		            // Calcular el subtotal para "costoTotal"
		            BigDecimal totalCostSubtotal = gastosList.stream()
		                .map(g -> !Double.isNaN(g.getCostoTotal()) ? new BigDecimal(g.getCostoTotal()) : BigDecimal.ZERO)
		                .reduce(BigDecimal.ZERO, BigDecimal::add);

		            // Calcular el subtotal para "totalUSD"
		            BigDecimal totalUSDSubtotal = gastosList.stream()
		                .map(g ->  !Double.isNaN(g.getTotalUSD()) ? new BigDecimal(g.getTotalUSD()) : BigDecimal.ZERO)
		                .reduce(BigDecimal.ZERO, BigDecimal::add);

		            // Crear un mapa de subtotales
		            Map<String, BigDecimal> subtotales = new HashMap<>();
		            subtotales.put("costoTotal", totalCostSubtotal);
		            subtotales.put("totalUSD", totalUSDSubtotal);

		            // Almacenar los subtotales con la clave EgresoProjectKey
		            subtotalesPorGrupo.put(egresoProjectKey, subtotales);
		        });
		    });

		    // Añadir los subtotales al modelo para usarlos en la vista
		    model.addAttribute("subtotalesPorGrupo", subtotalesPorGrupo);

       ////////////////////////////////////////////////////////////////////////////////////////////////// 		
	        model.addAttribute("unitOfMeasurements", unitOfMeasurementService.getUnitOfMeasurements());
	        model.addAttribute("expenseDisclaimer", new ExpenseDisclaimers());
             
	        List<Expense> expenses = expenseService.getExpensesVigentesWithDescargoByProjectId(projectId);
	        double totalAmount = expenses.stream().mapToDouble(Expense::getMontoTotal).sum();
	        double totalAmountLCU = expenses.stream().mapToDouble(Expense::getTotalLCU).sum();
	        model.addAttribute("totalAmountExpenses", totalAmount);
	        model.addAttribute("totalAmountExpensesLCU", totalAmountLCU);
  	         
	          return "registro_descargo";
	    }
	   
	   @GetMapping("/getExpensesByBeneficiary")
	   @ResponseBody
	   public List<Expense> getExpensesByBeneficiary(@RequestParam Long projectId, @RequestParam Integer beneficiaryId) {
	       return expenseService.getExpensesVigentesWithDescargoByProjectIdAndBeneficiaryId(projectId, beneficiaryId); 
	   }	 
	    //-------- mapping lista de proyectos asignados a cada beneficiario que Presentara VOUCHERS
	    @GetMapping("/lista-proyectosAsignados")
	    public String getProjects(Model model) {
	    	UserAdm loginUser = userService.getUser(userDetailsService.getUserDetailsService().getId());
	        model.addAttribute("loginUser",loginUser );
	        model.addAttribute("menuRoles",this.rolMenuService.getAllRolMenusByRoleId());
	        model.addAttribute("menuRoles2",this.rolMenuService.getRolMenusByRoleId());
	        
	        //model.addAttribute("projects", this.expenseService.getExpenseByBeneficiaryId(loginUser.getBeneficiario().getId()));
	        
	        List<Project> projectByBeneficiary = expenseService.getExpenseByBeneficiaryId(loginUser.getBeneficiario().getId());
	     // Extrae solo los IDs de proyectos asociados a un beneficiario
	        List<Long> projectList = projectByBeneficiary.stream()
	            .map(project -> project.getId()) 
	            .distinct() // Opcional: para evitar IDs duplicados
	            .collect(Collectors.toList());
	        
	        model.addAttribute("projects",projectService.getProjectsByListId(projectList));

	        return "lista_proyectosAsignados";
	    }
	    
   
	   @GetMapping("/registro-descargoBeneficiario/{id}")
	    public String showRegisterFormDescargoByBeneficiary(  @PathVariable("id") long projectId, Model model) {
	        model.addAttribute("loginUser", this.userService.getUser(userDetailsService.getUserDetailsService().getId()));
	        model.addAttribute("menuRoles",this.rolMenuService.getAllRolMenusByRoleId());
	        model.addAttribute("menuRoles2",this.rolMenuService.getRolMenusByRoleId());
		   
	        UserAdm loginUser = userService.getUser(userDetailsService.getUserDetailsService().getId());
	        model.addAttribute("expenseDescargos",expenseService.getExpensesVigentesWithDescargoByProjectIdAndBeneficiaryId(projectId, loginUser.getBeneficiario().getId()));
		       // Obtener la lista de fases con descargo del servicio
	        List<Expense> expensesWithDescargo = expenseService.getExpensesVigentesWithDescargoByProjectIdAndBeneficiaryId(projectId, loginUser.getBeneficiario().getId());
	     // Extrae solo los IDs de fase asociados a cada Expense
	        List<Long> phasesList = expensesWithDescargo.stream()
	            .map(expense -> expense.getProyectoFase().getFase().getId()) 
	            .distinct() // Opcional: para evitar IDs duplicados
	            .collect(Collectors.toList());
	        
	        model.addAttribute("fasesByProyecto",phaseService.getPhaseByListId(phasesList));
	        model.addAttribute("project", projectService.getProject(projectId)); 
	        model.addAttribute("gastoDescargos", expenseDisclaimersService.getExpenseDisclaimersByProjectIdAndBeneficiaryId(projectId,loginUser.getBeneficiario().getId()));
	        
	        List<ExpenseDisclaimers> descargosByProjectBeneficiary =  expenseDisclaimersService.getExpenseDisclaimersByProjectIdAndBeneficiaryId(projectId,loginUser.getBeneficiario().getId());
	        double totalAmountDescargos = descargosByProjectBeneficiary.stream().mapToDouble(ExpenseDisclaimers::getCostoTotal).sum();
	        double totalAmountDescargosUSD = descargosByProjectBeneficiary.stream().mapToDouble(ExpenseDisclaimers::getTotalUSD).sum();
	        model.addAttribute("totalAmountDescargos", totalAmountDescargos);
	        model.addAttribute("totalAmountDescargosUSD", totalAmountDescargosUSD);

	        Map<EgresoKey, List<ExpenseDisclaimers>> groupedGastos = descargosByProjectBeneficiary.stream()
	        	    .collect(Collectors.groupingBy(d -> new EgresoKey(
	        	        d.getEgreso().getId(),
	        	        d.getEgreso().getClasificacionEgreso().getNombreClase(),
	        	        d.getEgreso().getCargoItem()
	        	    )));

	        model.addAttribute("gastoDescargosGroupedByExpense", groupedGastos);
	        model.addAttribute("totalUSDvouchers", expenseDisclaimersService.getSummaryExpenseDisclaimersByProjectIdAndBeneficiaryId(projectId,loginUser.getBeneficiario().getId()));
	        ///////////////////////////////////////////////////////////////////////////////////////////////
	        	// Calcular subtotales para cada grupo
	        	Map<EgresoKey, Map<String, BigDecimal>> subtotalesPorGrupo = new HashMap<>();
	        	groupedGastos.forEach((key, gastos) -> {
	        		BigDecimal totalCostSubtotal = gastos.stream()
	        			    .map(g -> new BigDecimal(g.getCostoTotal())) // Conversión explícita a BigDecimal
	        			    .reduce(BigDecimal.ZERO, BigDecimal::add);

	        			BigDecimal totalUSDSubtotal = gastos.stream()
	        			    .map(g -> new BigDecimal(g.getTotalUSD())) // Conversión explícita a BigDecimal
	        			    .reduce(BigDecimal.ZERO, BigDecimal::add);

	        	    Map<String, BigDecimal> subtotales = new HashMap<>();
	        	    subtotales.put("costoTotal", totalCostSubtotal);
	        	    subtotales.put("totalUSD", totalUSDSubtotal);
	        	    subtotalesPorGrupo.put(key, subtotales);
	        	});
	        	model.addAttribute("subtotalesPorGrupo", subtotalesPorGrupo);

	       ////////////////////////////////////////////////////////////////////////////////////////////////// 		
	        model.addAttribute("unitOfMeasurements", unitOfMeasurementService.getUnitOfMeasurements());
	        model.addAttribute("expenseDisclaimer", new ExpenseDisclaimers());
             
	        List<Expense> expenses = expenseService.getExpensesVigentesWithDescargoByProjectIdAndBeneficiaryId(projectId, loginUser.getBeneficiario().getId());
	        double totalAmount = expenses.stream().mapToDouble(Expense::getMontoTotal).sum();
	        double totalAmountLCU = expenses.stream().mapToDouble(Expense::getTotalLCU).sum();
	        model.addAttribute("totalAmountExpenses", totalAmount);
	        model.addAttribute("totalAmountExpensesLCU", totalAmountLCU);
	        
	          return "registro_descargoBeneficiario";
	          
	    }
	   
	// Método para recuperar cattegoria de gastos por fase
	   @GetMapping("/getCategoriesByPhase")
	   @ResponseBody
	   public List<ExpenseCategory> getCategoriesByPhase(@RequestParam Long phaseId) {
		   	
	       return expenseCategoryService.getExpenseCategoryByPhase(phaseId); 
	   }	   
	   
	   public String saveFile(MultipartFile file) throws IOException {
	        // Definir el directorio donde se guardará el archivo
	        String uploadDir = "/uploads/";
	        
	        // Obtener el nombre original del archivo
	        String fileName = file.getOriginalFilename();
	        
	        // Definir la ruta completa donde se guardará el archivo
	        String filePath = uploadDir + fileName;
	        
	        // Guardar el archivo en el servidor
	        Path path = Paths.get(filePath);
	        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
	        
	        // Devolver la URL o la ruta relativa del archivo guardado
	        return filePath;
	    }
	    
	    @GetMapping("/descargar/{filename:.+}")
	    public ResponseEntity<Resource> descargarArchivo(@PathVariable String filename) throws FileNotFoundException {
	        try {
	            Path filePath  = Paths.get(DIRECTORIO_BASE).resolve(filename).normalize();//Paths.get("D:/SISTEMAS_integrales/descargosStaff", filename);
	            Resource resource = new UrlResource(filePath.toUri());

	            if (resource.exists() || resource.isReadable()) {
	            	 String contentType;//String contentType = Files.probeContentType(path);
	            	
	            	try {
	                    contentType = Files.probeContentType(filePath);
	                } catch (IOException e) {
	                    contentType = "application/octet-stream"; // Tipo por defecto en caso de error
	                }

	                if (contentType == null) {
	                    contentType = "application/octet-stream"; // Tipo por defecto si no se puede determinar
	                }

	                return ResponseEntity.ok()
	                        .contentType(MediaType.parseMediaType(contentType)) // Use the correct content type
	                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
	                        .body(resource);
	            } else {
	                throw new FileNotFoundException("El archivo no existe o no es accesible.");
	            }
	        } catch (MalformedURLException e) {
	            throw new RuntimeException("Error al procesar el archivo.", e);
	        }
	    }
	   
	    @PostMapping("/guardar-descargo")
	    public String saveExpense(@ModelAttribute ExpenseDisclaimers expenseDisclaimer,
	            @RequestParam(required = false) Long id,
	            @RequestParam(value = "inputproyectoId", required = false) long projectId,
	            @RequestParam(value = "inputexpenseId", required = false) long expenseId,
	            @RequestParam("file") MultipartFile file,  // Nuevo parámetro para el archivo
	            RedirectAttributes redirectAttributes) {
	    	
	    	System.out.println(" print  expense   "+ expenseId);
	    	System.out.println(" print  py   "+ projectId);
	        // Intentar guardar el proyecto si todas las validaciones pasaron
	    	try {
	            if (!file.isEmpty()) {
	                // Definir la ruta de almacenamiento
	                String uploadDir = DIRECTORIO_BASE+"/";
	                // Obtener la fecha actual en formato MMddyyyy
	                String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("MMddyyyy"));
	                // Obtener el nombre original del archivo y su extensión
	                String originalFileName = file.getOriginalFilename();
	                String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
	                String fileBaseName = originalFileName.substring(0, originalFileName.lastIndexOf("."));
	            
	                // Guardar el archivo en el directorio definido
	                //String fileName = file.getOriginalFilename();
	                // Construir el nuevo nombre de archivo con la fecha
	                String newFileName = currentDate + "_" + fileBaseName + fileExtension;
	           
	                Path path = Paths.get(uploadDir + newFileName);
	                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
	                
	                // Establecer la ruta del archivo en el objeto expense
	                expenseDisclaimer.setAttach(uploadDir + newFileName);

	            }
	            else
	            {
	            	expenseDisclaimer.setAttach(null);
	            }
	        	//expenseDisclaimersService.saveExpenseDisclaimers(expenseDisclaimer,expenseId);
	        	expenseDisclaimersService.saveExpenseDisclaimers(expenseDisclaimer);
	            redirectAttributes.addFlashAttribute("message", "Expense voucher saved success.");
	            redirectAttributes.addFlashAttribute("messageType", "success");
	            return "redirect:/gastos/lista-descargos/"+expenseId;
	        } catch (Exception e) {
	            redirectAttributes.addFlashAttribute("message", "Error saving expense voucher: " + e.getMessage());
	            redirectAttributes.addFlashAttribute("messageType", "error");
	            return "redirect:/gastos/registro-descargo/"+expenseId;
	        }
	    }
	   
	    @PostMapping("/guardar-descargoNonRecurring")
	    public String saveDescargoByBeneficiario(@ModelAttribute ExpenseDisclaimers expenseDisclaimer,
	            @RequestParam(required = false) Long id,
	            @RequestParam(value = "inputproyectoId", required = false) long projectId,
	            @RequestParam(value = "inputTipoSend", required = false) long tipo,
	            @RequestParam("file") MultipartFile file,  // Nuevo parámetro para el archivo
	            RedirectAttributes redirectAttributes) {

	        // Intentar guardar el proyecto si todas las validaciones pasaron
	    	try {
	            if (!file.isEmpty()) {
	                // Definir la ruta de almacenamiento
	                String uploadDir = DIRECTORIO_BASE+"/";
	                
	                // Guardar el archivo en el directorio definido
	                String fileName = file.getOriginalFilename();
	                Path path = Paths.get(uploadDir + fileName);
	                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
	                
	                // Establecer la ruta del archivo en el objeto expense
	                expenseDisclaimer.setAttach(uploadDir + fileName);
	            }
	            else
	            {
	            	expenseDisclaimer.setAttach(null);
	            }
	        	//expenseDisclaimersService.saveExpenseDisclaimers(expenseDisclaimer,expenseId);
	        	expenseDisclaimersService.saveExpenseDisclaimers(expenseDisclaimer);
	            redirectAttributes.addFlashAttribute("message", "Expense voucher saved success.");
	            redirectAttributes.addFlashAttribute("messageType", "success");
		        if (tipo == 1){//rol:ADMIN
		        	return "redirect:/gastos/registro-descargo/"+projectId;
		        }
		        else
		        {
		        	return "redirect:/gastos/registro-descargoBeneficiario/"+projectId;
		        }
	            //return "redirect:/gastos/registro-descargoBeneficiario/"+projectId;
	        } catch (Exception e) {
	            redirectAttributes.addFlashAttribute("message", "Error saving expense voucher: " + e.getMessage());
	            redirectAttributes.addFlashAttribute("messageType", "error");
		        if (tipo == 1){//rol:ADMIN
		        	return "redirect:/gastos/registro-descargo/"+projectId;
		        }
		        else
		        {
		        	return "redirect:/gastos/registro-descargoBeneficiario/"+projectId;
		        }
	            //return "redirect:/gastos/registro-descargoBeneficiario/"+projectId;
	        }
	    }
	    
	    @GetMapping("/registro-planPagos/{id}")
	    public String showRegisterFormPlanPagos( @PathVariable("id") long expenseId, Model model) {
	        model.addAttribute("loginUser", this.userService.getUser(userDetailsService.getUserDetailsService().getId()));
	        model.addAttribute("menuRoles",this.rolMenuService.getAllRolMenusByRoleId());
	        model.addAttribute("menuRoles2",this.rolMenuService.getRolMenusByRoleId());
		   
	        model.addAttribute("project", projectService.getProject(expenseService.getExpense(expenseId).getProyectoFase().getProyecto().getId())); 
	        model.addAttribute("expenseSelected", expenseService.getExpense(expenseId)); 
	        model.addAttribute("categoriesGastos",expenseCategoryService.getExpenseCategoryByPhase(expenseService.getExpense(expenseId).getProyectoFase().getFase().getId())); 
	        model.addAttribute("planPagosList", expenseDisclaimersService.getExpenseDisclaimersByExpenseId(expenseId));    

	        model.addAttribute("expensePaymentPlan", new ExpenseDisclaimers());
             /*
	        List<Expense> expenses = expenseService.getExpensesVigentesWithDescargoByProjectId(projectId);
	        double totalAmount = expenses.stream().mapToDouble(Expense::getMontoTotal).sum();
	        double totalAmountLCU = expenses.stream().mapToDouble(Expense::getTotalLCU).sum();
	        model.addAttribute("totalAmountExpenses", totalAmount);
	        model.addAttribute("totalAmountExpensesLCU", totalAmountLCU);
  	         */
	          return "registro_planPagos";
	    }
		   @GetMapping("/getPago")
		   @ResponseBody
		   public ExpenseDisclaimers getPago(@RequestParam Long pagoId) {
		       return expenseDisclaimersService.getExpenseDisclaimer(pagoId);
		   }	 
		// Eliminar 
		@GetMapping("/eliminar-voucherBeneficiario/{projectId}/{id}/{tipo}")
		public String deleteBeneficiary(@PathVariable("projectId") long projectId, @PathVariable("id") Integer id, @PathVariable("tipo") long tipo,RedirectAttributes redirectAttributes) {
		        try {
		        	expenseDisclaimersService.deleteExpenseDisclaimers(id);
		            redirectAttributes.addFlashAttribute("message", "Register deleted successfully..");
		            redirectAttributes.addFlashAttribute("messageType", "success");
		        } catch (Exception e) {
		            redirectAttributes.addFlashAttribute("message", "Error deleting registry: " + e.getMessage());
		            redirectAttributes.addFlashAttribute("messageType", "error");
		        }
		        if (tipo == 1){//rol:ADMIN
		        	return "redirect:/gastos/registro-descargo/"+projectId;
		        }
		        else
		        {
		        	return "redirect:/gastos/registro-descargoBeneficiario/"+projectId;
		        }
		    }

}
