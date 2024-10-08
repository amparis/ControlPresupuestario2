package com.springmvc.ControlPresupuestario.service;
import java.util.List;

import com.springmvc.ControlPresupuestario.model.Menu;

public interface IMenuService {

		public List<Menu> getMenus();
		
		public Menu getMenu(long id);
}
