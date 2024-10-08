package com.springmvc.ControlPresupuestario.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springmvc.ControlPresupuestario.model.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

}
