package com.springmvc.ControlPresupuestario.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springmvc.ControlPresupuestario.model.Beneficiary;
import com.springmvc.ControlPresupuestario.model.EgresoKey;
import com.springmvc.ControlPresupuestario.model.Expense;
import com.springmvc.ControlPresupuestario.model.ExpenseCategory;
import com.springmvc.ControlPresupuestario.model.ExpenseDisclaimers;
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

	private final String DIRECTORIO_BASE = "D:/SISTEMAS_integrales/descargosStaff/descargosBeneficiario";
	   @GetMapping("/lista-descargos/{id}")
	    public String showListDescargo( @PathVariable("id") long projectId, Model model) {
	        model.addAttribute("loginUser", this.userService.getUser(userDetailsService.getUserDetailsService().getId()));
	        model.addAttribute("menuRoles",this.rolMenuService.getAllRolMenusByRoleId());
	        model.addAttribute("menuRoles2",this.rolMenuService.getRolMenusByRoleId());
	        
	        model.addAttribute("project", projectService.getProject(projectId)); 
	        model.addAttribute("expenseSummary", expenseService.getExpensesVigentesWithDescargo(projectId));
	        model.addAttribute("fasesProyecto",projectPhaseService.getProjectPhasesByProyectoId(projectId));
	        model.addAttribute("cuentas", accountService.getAccounts());
	        model.addAttribute("gastoDescargos", expenseDisclaimersService.getExpenseDisclaimersByProjectId(projectId));
	        model.addAttribute("descargo", new ExpenseDisclaimers());
               

	          return "lista_descargos";
	    }
	   
	   @GetMapping("/registro-descargo/{id}")
	    public String showRegisterFormDescargo( @PathVariable("id") long expenseId, Model model) {
	        model.addAttribute("loginUser", this.userService.getUser(userDetailsService.getUserDetailsService().getId()));
	        model.addAttribute("menuRoles",this.rolMenuService.getAllRolMenusByRoleId());
	        model.addAttribute("menuRoles2",this.rolMenuService.getRolMenusByRoleId());
	        model.addAttribute("expenseSelected",expenseService.getExpense(expenseId));
	        model.addAttribute("project", projectService.getProject(expenseService.getExpense(expenseId).getProyectoFase().getProyecto().getId())); 
	        model.addAttribute("fasesProyecto",projectPhaseService.getProjectPhasesByProyectoId(expenseService.getExpense(expenseId).getProyectoFase().getProyecto().getId()));
	        model.addAttribute("expenseCategories",expenseCategoryService.getExpenseCategoryByPhase(expenseService.getExpense(expenseId).getProyectoFase().getFase().getId()));
	        model.addAttribute("unitOfMeasurements", unitOfMeasurementService.getUnitOfMeasurements());
	       // model.addAttribute("gastoDescargos", expenseDisclaimersService.getExpenseDisclaimersByExpenseId(expenseId));
	        model.addAttribute("expenseDisclaimer", new ExpenseDisclaimers());
              

	          return "registro_descargo";
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
	       
	        //Map<Long, List<ExpenseDisclaimers>> groupedGastos = descargosByProjectBeneficiary.stream()
	        //	    .collect(Collectors.groupingBy(d -> d.getEgreso().getId()));
	        Map<EgresoKey, List<ExpenseDisclaimers>> groupedGastos = descargosByProjectBeneficiary.stream()
	        	    .collect(Collectors.groupingBy(d -> new EgresoKey(
	        	        d.getEgreso().getId(),
	        	        d.getEgreso().getClasificacionEgreso().getNombreClase(),
	        	        d.getEgreso().getCargoItem()
	        	    )));

	        model.addAttribute("gastoDescargosGroupedByExpense", groupedGastos);


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
		   	System.out.println("ingreso a METODO: "+phaseId);
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
	                String uploadDir = DIRECTORIO_BASE+'/';
	                
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
	            redirectAttributes.addFlashAttribute("message", "Expense Disclaimer saved success.");
	            redirectAttributes.addFlashAttribute("messageType", "success");
	            return "redirect:/gastos/lista-descargos/"+expenseId;
	        } catch (Exception e) {
	            redirectAttributes.addFlashAttribute("message", "Error saving expense disclaimer: " + e.getMessage());
	            redirectAttributes.addFlashAttribute("messageType", "error");
	            return "redirect:/gastos/registro-descargo/"+expenseId;
	        }
	    }
	    @PostMapping("/guardar-descargoB")
	    public String saveExpenseBeneficiario(@ModelAttribute ExpenseDisclaimers expenseDisclaimer,
	            @RequestParam(required = false) Long id,
	            @RequestParam(value = "inputproyectoId", required = false) long projectId,
	            //@RequestParam(value = "inputexpenseId", required = false) long expenseId,
	            @RequestParam("file") MultipartFile file,  // Nuevo parámetro para el archivo
	            RedirectAttributes redirectAttributes) {

	        // Intentar guardar el proyecto si todas las validaciones pasaron
	    	try {
	            if (!file.isEmpty()) {
	                // Definir la ruta de almacenamiento
	                String uploadDir = DIRECTORIO_BASE;
	                
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
	            redirectAttributes.addFlashAttribute("message", "Expense Disclaimer saved success.");
	            redirectAttributes.addFlashAttribute("messageType", "success");
	            return "redirect:/gastos/registro-descargoBeneficiario/"+projectId;
	        } catch (Exception e) {
	            redirectAttributes.addFlashAttribute("message", "Error saving expense disclaimer: " + e.getMessage());
	            redirectAttributes.addFlashAttribute("messageType", "error");
	            return "redirect:/gastos/registro-descargoBeneficiario/"+projectId;
	        }
	    }
}
