package com.springmvc.ControlPresupuestario.repository;

import com.springmvc.ControlPresupuestario.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {

}
