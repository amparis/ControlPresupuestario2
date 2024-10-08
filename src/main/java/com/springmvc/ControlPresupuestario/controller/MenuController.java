package com.springmvc.ControlPresupuestario.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.springmvc.ControlPresupuestario.model.Menu;
import com.springmvc.ControlPresupuestario.service.MenuService;
import com.springmvc.ControlPresupuestario.service.RolMenuService;

@RestController
@RequestMapping("/menu")
public class MenuController {

	@Autowired
	MenuService menuService;
    @Autowired
    RolMenuService rolMenuService;
	
    @PostMapping("")
    public RedirectView postMenu(@ModelAttribute Menu newMenu) {

       // this.menuService.saveMenu(newMenu);

        return new RedirectView("/lista-menus");
    }
}
