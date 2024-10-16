package com.springmvc.ControlPresupuestario.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springmvc.ControlPresupuestario.model.Menu;

public interface MenuRepository extends JpaRepository<Menu,Long>  {
	  @Query("SELECT m FROM Menu m WHERE m.estado = 'V' order by m.orden")
	  public List<Menu> findAllByEstado();

}
