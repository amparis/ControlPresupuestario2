package com.springmvc.ControlPresupuestario.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;


import com.springmvc.ControlPresupuestario.model.Beneficiary;
import com.springmvc.ControlPresupuestario.model.Expense;
import com.springmvc.ControlPresupuestario.model.ExpenseCategory;
import com.springmvc.ControlPresupuestario.model.Scale;
import com.springmvc.ControlPresupuestario.service.AccountService;
import com.springmvc.ControlPresupuestario.service.BeneficiaryService;
import com.springmvc.ControlPresupuestario.service.CountryService;
import com.springmvc.ControlPresupuestario.service.CurrencyService;
import com.springmvc.ControlPresupuestario.service.ExpenditureClassificationService;
import com.springmvc.ControlPresupuestario.service.ExpenseService;
import com.springmvc.ControlPresupuestario.service.IMyUserDetailsService;
import com.springmvc.ControlPresupuestario.service.MenuService;
import com.springmvc.ControlPresupuestario.service.PaymentMethodService;
import com.springmvc.ControlPresupuestario.service.PefilService;
import com.springmvc.ControlPresupuestario.service.ProjectPhaseService;
import com.springmvc.ControlPresupuestario.service.ProjectService;
import com.springmvc.ControlPresupuestario.service.RolMenuService;
import com.springmvc.ControlPresupuestario.service.ScaleService;
import com.springmvc.ControlPresupuestario.service.UserAdmService;

@Controller
@RequestMapping("/expenses")
public class ExpenseController {

	@Autowired
	ExpenseService expenseService;
    @Autowired
    PefilService pefilService;
    @Autowired
    IMyUserDetailsService userDetailsService;
    //AGREGANDO USUARIO
    @Autowired
    UserAdmService userService;
    @Autowired
    MenuService menuService;
    
    @Autowired
    RolMenuService rolMenuService;
    @Autowired
    AccountService accountService;
    @Autowired
    ProjectService projectService;
	@Autowired
	CountryService countryService; 
	@Autowired
	CurrencyService currencyService;
	@Autowired
	BeneficiaryService beneficiaryService;
	@Autowired
	ExpenditureClassificationService expenditureClassificationService;
	@Autowired
	ProjectPhaseService projectPhaseService;
	@Autowired
	ScaleService scaleService;
	@Autowired
	PaymentMethodService paymentMethodService;
    
	private final String DIRECTORIO_BASE = "D:/SISTEMAS_integrales/descargosStaff";
	
    @GetMapping("/registro-egreso/{id}")
    public String showRegisterFormIngreso( @PathVariable("id") long projectId, Model model) {
        model.addAttribute("loginUser", this.userService.getUser(userDetailsService.getUserDetailsService().getId()));

        model.addAttribute("menuRoles",this.rolMenuService.getAllRolMenusByRoleId());
        model.addAttribute("menuRoles2",this.rolMenuService.getRolMenusByRoleId());
        model.addAttribute("project", projectService.getProject(projectId)); 
        model.addAttribute("expense", new Expense());
        model.addAttribute("beneficiaries", beneficiaryService.getAllBeneficiariesEstado("V"));
        model.addAttribute("beneficiary", new Beneficiary());
        model.addAttribute("fasesProyecto",projectPhaseService.getProjectPhasesByProyectoId(projectId));
        model.addAttribute("paymentMethod",paymentMethodService.getAllPaymentMethods());
        model.addAttribute("cuentas", accountService.getAccounts());
        model.addAttribute("paises", countryService.getAllCountries()); 
        model.addAttribute("divisas", currencyService.getAllCurrencies()); 
        model.addAttribute("escalas", scaleService.getAllScalesEstado("V"));
        model.addAttribute("scale", new Scale());
        model.addAttribute("paisesBeneficiary", countryService.getAllCountries()); 
        model.addAttribute("expenditureClassifications",expenditureClassificationService.getExpenditureClassifications());
               

          return "registro_egreso";
    }
    
	// Método para recuperar beneficiarios por clasificacion de egreso
	   @GetMapping("/getBeneficiariesByClassExpense")
	   @ResponseBody
	   public List<Beneficiary> getBeneficiariesByClassExpense(@RequestParam String classExpense) {
		   	//System.out.println("ingreso a METODO: "+classExpense);
	       return beneficiaryService.getBeneficiariesByClassExpense(classExpense); 
	   }	   
	   
    // Guardar
    
    @PostMapping("/guardar-egreso")
    public String saveExpense(@ModelAttribute Expense expense,
            @RequestParam(required = false) Long id,
            @RequestParam(value = "inputproyectoId", required = false) long projectId,
            @RequestParam("file") MultipartFile file,  // Nuevo parámetro para el archivo
            @RequestParam("paymentCurrency") String paymentCurrency,
            RedirectAttributes redirectAttributes) {

    	if (expense.getFechaFin() != null && expense.getFechaFin().toString().trim().isEmpty()) {
    	    expense.setFechaFin(null);
    	}
        if ("1".equals(paymentCurrency)) {
            // El radio button de USD está seleccionado
            System.out.println("Payment is in USD");
            expense.setDivisa(currencyService.getCurrencyById((int)144));
        }    	

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
                expense.setAttach(uploadDir + newFileName);
            }
            else
            {
            	expense.setAttach(null);
            }
            expenseService.saveExpense(expense);

            redirectAttributes.addFlashAttribute("message", "Expense saved success.");
            redirectAttributes.addFlashAttribute("messageType", "success");
            return "redirect:/ejecucion/registro-ejecucion/"+projectId;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Error saving expense: " + e.getMessage());
            redirectAttributes.addFlashAttribute("messageType", "error");
            return "redirect:/expenses/registro-egreso/"+projectId;
        }
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
    /*    Path path = Paths.get("D:\\SISTEMAS_integrales\\descargosStaff").resolve(filename);
        Resource resource = new FileSystemResource(path);

        if (!resource.exists()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
    
        try {
            Path path = Paths.get("D:/SISTEMAS_integrales/descargosStaff", filename);
            Resource resource = new UrlResource(path.toUri());

            if (resource.exists() || resource.isReadable()) {
                // Devolver el archivo para que se abra en el navegador
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                throw new FileNotFoundException("El archivo no existe o no es accesible.");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error al procesar el archivo.", e);
        }
        */
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

}
