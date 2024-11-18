package com.springmvc.ControlPresupuestario.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.springmvc.ControlPresupuestario.model.Scale;

@Repository
public interface ScaleRepository extends JpaRepository<Scale, Long>{
	  @Query("SELECT s FROM Scale s ORDER BY s.id")
	  public List<Scale> findAll();
	
	  @Query("SELECT s FROM Scale s  WHERE  s.estado = :estado")
	  public List<Scale> findAllByEstado(String estado);	
	  
	  @Query("SELECT s FROM Scale s  WHERE  s.nombre = :nombre")
	  public Scale findScaleByNombre(String nombre);	
}
