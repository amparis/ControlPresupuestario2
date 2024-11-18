package com.springmvc.ControlPresupuestario.service;

import java.util.List;

import com.springmvc.ControlPresupuestario.model.Beneficiary;

public interface IBeneficiaryService {

    public List<Beneficiary> getBeneficiaries();
    
    //public Beneficiary getBeneficiaryById(Integer id);
    
    public Beneficiary getBeneficiaryById(Integer id);
    
	public Beneficiary saveBeneficiary(Beneficiary newBeneficiary);
	
	public void updateBeneficiary(long id, Beneficiary newBeneficiary);
	
	public void deleteBeneficiary(long id);
}
