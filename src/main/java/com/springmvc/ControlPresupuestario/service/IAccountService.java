package com.springmvc.ControlPresupuestario.service;

import java.util.List;

import com.springmvc.ControlPresupuestario.model.Account;

public interface IAccountService {

	public List<Account> getAccounts();
	
	public Account getAccount(long id);
	
	//public Account getAccountById(Long id);
	
	public Account saveAccount(Account newAccount);
	
	public void updateAccount(long id, Account newAccount);
	
	public void deleteAccount(long id);
}
