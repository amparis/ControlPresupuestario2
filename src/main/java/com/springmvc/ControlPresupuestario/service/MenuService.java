package com.springmvc.ControlPresupuestario.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.springmvc.ControlPresupuestario.model.Menu;
import com.springmvc.ControlPresupuestario.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
	
}
