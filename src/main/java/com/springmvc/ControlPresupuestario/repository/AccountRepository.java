package com.springmvc.ControlPresupuestario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.springmvc.ControlPresupuestario.model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

	  @Query("SELECT  a.id FROM Account a WHERE a.id <> :accountId")
	  public Long findAllAccountDestiny(Long accountId);//si no existe desembolso? AJUSTAR con otra categoria
}
