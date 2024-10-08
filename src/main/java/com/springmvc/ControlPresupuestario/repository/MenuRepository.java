package com.springmvc.ControlPresupuestario.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springmvc.ControlPresupuestario.model.Menu;

public interface MenuRepository extends JpaRepository<Menu,Long>  {

}
