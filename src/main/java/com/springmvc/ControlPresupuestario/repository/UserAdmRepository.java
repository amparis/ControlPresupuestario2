package com.springmvc.ControlPresupuestario.repository;

import com.springmvc.ControlPresupuestario.model.RolMenu;
import com.springmvc.ControlPresupuestario.model.UserAdm;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface UserAdmRepository extends JpaRepository<UserAdm,Long>{
	
    //public UserAdm findByEmail(String email);
    public UserAdm findByUsernamee(String usernamee);
	boolean existsByUsernamee(String usernamee);//Metodo para buscar por nombre
	
	//List<UserAdm> findByBeneficiaryIsNotNull();
	  @Query("SELECT rm FROM UserAdm rm  WHERE rm.beneficiario.id= :us_ben_id and rm.beneficiario.estado = 'V'")
	  public Optional<UserAdm> findByBeneficiario(Integer us_ben_id);

}
