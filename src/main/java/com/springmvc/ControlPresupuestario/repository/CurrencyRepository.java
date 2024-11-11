package com.springmvc.ControlPresupuestario.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.springmvc.ControlPresupuestario.model.Country;
import com.springmvc.ControlPresupuestario.model.Currency;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency ,Integer>{
	  @Query("SELECT c FROM Currency c  ORDER BY c.divisa ASC")
	  public List<Currency> findAllAndSortByNombre();
}
