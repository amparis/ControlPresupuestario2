package com.springmvc.ControlPresupuestario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springmvc.ControlPresupuestario.model.ExpenditureClassification;

@Repository
public interface ExpenditureClassificationRepository extends JpaRepository<ExpenditureClassification, Long> {

}
