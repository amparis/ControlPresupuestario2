package com.springmvc.ControlPresupuestario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springmvc.ControlPresupuestario.model.Currency;
import com.springmvc.ControlPresupuestario.repository.CurrencyRepository;

@Service
public class CurrencyService {
	@Autowired
	private CurrencyRepository currencyRepository;
	
    public List<Currency> getAllCurrencies() {
        return currencyRepository.findAllAndSortByNombre();
    }	
    
    public Currency getCurrencyById(Integer id) {
        return currencyRepository.findById(id).get();
    }

    public Currency saveCurrency(Currency newcurrency) {
       return currencyRepository.save(newcurrency);
    }
    
    public void deleteCurrency(Integer id) {
    	currencyRepository.deleteById(id);
    }

}
