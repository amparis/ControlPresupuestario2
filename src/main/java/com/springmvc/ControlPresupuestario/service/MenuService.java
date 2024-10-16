package com.springmvc.ControlPresupuestario.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.springmvc.ControlPresupuestario.model.Menu;
import com.springmvc.ControlPresupuestario.model.RolMenu;
import com.springmvc.ControlPresupuestario.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

@Service
public class MenuService {

	@Autowired
	MenuRepository menuRepository;
	
	public List<Menu> getMenus(){
		return this.menuRepository.findAll();
	}
	
	public Menu getMenu(long id) {
		
		return this.menuRepository.findById(id).get();
	}
	
	 public List<Menu> getMenusVigentes(){
		 return this.menuRepository.findAllByEstado();
	 }
	  
	   /*public List<Menu> getMenusVigentes() {
	        List<Menu> allMenus = menuRepository.findAll(); // Obtener todos los menús
	        List<Menu> parentMenus = new ArrayList<>(); // Lista de menús de nivel superior

	        // Filtrar los menús vigentes (por ejemplo, estado = "V")
	        for (Menu menu : allMenus) {
	            if ("V".equals(menu.getEstado()) && menu.getSupMenu() == null) {
	                parentMenus.add(menu); // Agregar menús sin superior a la lista de menús padre
	            }
	        }

	        // Añadir submenús a los menús de nivel superior
	        for (Menu parent : parentMenus) {
	            List<Menu> subMenus = new ArrayList<>();
	            for (Menu menu : allMenus) {
	                if ("V".equals(menu.getEstado()) && menu.getSupMenu() != null && 
	                    menu.getSupMenu().getId().equals(parent.getId())) {
	                    subMenus.add(menu); // Agregar submenús a la lista
	                }
	            }
	            parent.setSubMenus(subMenus); // Establecer submenús en el menú padre
	        }

	        return parentMenus; // Retornar solo los menús de nivel superior con sus submenús
	    }*/
/*
	public List<Menu> getMenusJerarquicos() {
        List<Menu> allMenus = getMenus();
        return construirMenuJerarquico(allMenus);
    }

    private List<Menu> construirMenuJerarquico(List<Menu> allMenus) {
        // Mapa para almacenar menús por ID
        Map<Long, Menu> menuMap = new HashMap<>();
        List<Menu> menuRaiz = new ArrayList<>();

        // Llenar el mapa
        for (Menu menu : allMenus) {
            menuMap.put(menu.getId(), menu);
        }

        // Construir la jerarquía
        for (Menu menu : allMenus) {
            if (menu.getSupMenu() == null) {
                // Menú raíz
                menuRaiz.add(menu);
            } else {
                // Menú hijo
                Menu padre = menuMap.get(menu.getSupMenu().getId());
                if (padre != null) {
                    // Aquí podrías agregar lógica para agregar el menú hijo a una lista de hijos dentro del menú padre
                    // Ejemplo: padre.getHijos().add(menu);  (necesitarías modificar la clase Menu para incluir una lista de hijos)
                	padre.getHijos().add(menu);
                }
            }
        }
        return menuRaiz;
    }*/

	/*public Optional<Menu> findById(Long menuId) {
		// TODO Auto-generated method stub
		return this.menuRepository.findById(menuId);
	}
	*/
}
