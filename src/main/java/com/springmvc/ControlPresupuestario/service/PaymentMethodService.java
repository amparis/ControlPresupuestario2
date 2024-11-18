package com.springmvc.ControlPresupuestario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springmvc.ControlPresupuestario.model.PaymentMethod;
import com.springmvc.ControlPresupuestario.repository.PaymentMethodRepository;


@Service
public class PaymentMethodService {

	@Autowired
    private PaymentMethodRepository paymentMethodRepository;
   
    public List<PaymentMethod> getAllPaymentMethods() {
        return paymentMethodRepository.findAll();
    }
    


    public PaymentMethod getPaymentMethodById(Long id) {
        return paymentMethodRepository.findById(id).get();
    }
    
    public PaymentMethod savenPaymentMethod(PaymentMethod newPaymentMethod) {
	    
        return paymentMethodRepository.save(newPaymentMethod);
    }

    public void deletenewPaymentMethod(Long id) {
    	paymentMethodRepository.deleteById(id);
    }
    

}
