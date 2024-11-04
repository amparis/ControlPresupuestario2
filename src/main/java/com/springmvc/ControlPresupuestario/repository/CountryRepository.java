package com.springmvc.ControlPresupuestario.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.springmvc.ControlPresupuestario.model.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country,Long>{
	
	  @Query("SELECT c FROM Country c  ORDER BY c.nombre ASC")
	  public List<Country> findAllAndSortByNombre();

}
