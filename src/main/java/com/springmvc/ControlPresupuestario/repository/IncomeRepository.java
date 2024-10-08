package com.springmvc.ControlPresupuestario.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springmvc.ControlPresupuestario.model.Income;

public interface IncomeRepository extends JpaRepository<Income, Long> {

}
