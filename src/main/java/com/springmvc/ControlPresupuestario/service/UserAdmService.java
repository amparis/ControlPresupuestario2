package com.springmvc.ControlPresupuestario.service;

import com.springmvc.ControlPresupuestario.model.UserAdm;
import com.springmvc.ControlPresupuestario.repository.UserAdmRepository;
import com.springmvc.ControlPresupuestario.utility.CryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.sql.Timestamp;

@Service
public class UserAdmService {

	@Autowired
	UserAdmRepository userRepository;
    @Autowired
    PefilService pefilService;
	
    public List<UserAdm> getUsers(){
    	
    	return this.userRepository.findAll();
    }

    public UserAdm getUser(long id) {
    	return this.userRepository.findById(id).get();
    	
    }

    /*public UserAdm getUserByEmail(String email) {
    	
    	//return userRepository.findByEmail(email);
    	return userRepository.findByUsernamee(email);
    }*/
    public UserAdm getUserByUsernamee(String usernamee) {
    	
    	//return userRepository.findByEmail(email);
    	return userRepository.findByUsernamee(usernamee);
    }

    public UserAdm saveUser(UserAdm newUser) {
    	// Verificar si ya existe un usuario con el mismo nombre
		boolean exists = userRepository.existsByUsernamee(newUser.getUsernamee());
	    
		if (exists) {
	        throw new IllegalStateException("Ya existe un usario con el mismo nombre.");
	    }
  	
    	long millis = System.currentTimeMillis();
        newUser.setDateCreation(new Timestamp(millis));
        newUser.setPassword(CryptPasswordEncoder.getPasswordEncoder(newUser.getPassword()));
        newUser.setEstado("V");

        return this.userRepository.save(newUser);
    }

    public void updateUser(long id, UserAdm newUser) {
         UserAdm UserUpdate = this.userRepository.findById(id).get();

        if (this.userRepository.findById(id).isPresent()) {

            long millis = System.currentTimeMillis();

            if (newUser.getUsernamee() != null) UserUpdate.setUsernamee(newUser.getUsernamee());
            
            if (newUser.getFullname() != null) UserUpdate.setFullname(newUser.getFullname());

            if (newUser.getEmail() != null) UserUpdate.setEmail(newUser.getEmail());

            newUser.setDateUpdate(new Timestamp(millis));
           
            this.userRepository.save(UserUpdate);
        }
    }

    public void deleteUser(long id) {
    	
    	this.userRepository.deleteById(id);
    }

	public Optional<UserAdm> getUserByBeneficiaryId(Integer beneficiaryId) {
		// TODO Auto-generated method stub
		return userRepository.findByBeneficiario(beneficiaryId);
	}

}
