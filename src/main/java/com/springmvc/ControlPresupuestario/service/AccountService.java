package com.springmvc.ControlPresupuestario.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springmvc.ControlPresupuestario.model.Account;
import com.springmvc.ControlPresupuestario.model.UserAdm;
import com.springmvc.ControlPresupuestario.repository.AccountRepository;

@Service
public class AccountService {

	@Autowired
	AccountRepository accountRepository;
	
    @Autowired
    UserAdmService userService;
    @Autowired
    IMyUserDetailsService userDetailsService;
    
	public List<Account> getAccounts(){
		
		return this.accountRepository.findAll();
	}
	
	public Long getAccountDestiny(long accountId){
		
		return this.accountRepository.findAllAccountDestiny(accountId);
	}
	public Account getAccount(long id) {
		
		return this.accountRepository.findById(id).get();
		
	}
	
	//public Account getAccountById(Long id);
	
	public Account saveAccount(Account newAccount) {
		/* existira una VALIDACION, PREGUNTAR DE QUE TIPO
		boolean exists = projectRepository.existsByNombreAndFechaInicialAndFechaFin(
	            newProject.getNombre(), newProject.getFechaInicial(), newProject.getFechaFin());

	    if (exists) {
	        throw new IllegalStateException("Ya existe un proyecto con el mismo nombre, fecha de inicial y fecha de fin.");
	    }
		*/
		//newAccount.set.setEstado("V");

		return this.accountRepository.save(newAccount);
	}
	
	public void updateAccount(long id, Account newAccount) {
		
	}
	
	public void deleteAccount(long id) {
		this.accountRepository.deleteById(id);
	}

}
