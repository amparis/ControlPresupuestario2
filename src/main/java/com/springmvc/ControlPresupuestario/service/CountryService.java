package com.springmvc.ControlPresupuestario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springmvc.ControlPresupuestario.model.Country;
import com.springmvc.ControlPresupuestario.repository.CountryRepository;

@Service
public class CountryService {

	@Autowired
	private CountryRepository countryRepository;
	
    public List<Country> getAllCountries() {
        return countryRepository.findAllAndSortByNombre();
    }	
    
    public Country getCountryById(Long id) {
        return countryRepository.findById(id).get();
    }

    public Country saveCountry(Country newCountry) {
       return countryRepository.save(newCountry);
    }
    
    public void deleteCountry(Long id) {
    	 countryRepository.deleteById(id);
    }

}
