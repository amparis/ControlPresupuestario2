package com.springmvc.ControlPresupuestario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springmvc.ControlPresupuestario.model.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country,Long>{

}
