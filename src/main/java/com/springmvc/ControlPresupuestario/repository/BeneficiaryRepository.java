package com.springmvc.ControlPresupuestario.repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.springmvc.ControlPresupuestario.model.Beneficiary;
import com.springmvc.ControlPresupuestario.model.ExpenseCategory;
import com.springmvc.ControlPresupuestario.model.Project;
import com.springmvc.ControlPresupuestario.model.UserAdm;

import org.springframework.stereotype.Repository;

@Repository
public interface BeneficiaryRepository extends JpaRepository<Beneficiary, Integer >{
	// Agregar métodos personalizados aquí si es necesario
	// Ya puedo trabajar con las operaciones CRUD
		boolean existsByNombresAndApellidosAndDocumento(String nombres,String apellidos, String documento);//Metodo para buscar por nombre, fecha inicla y fecha final

		Beneficiary findById(Long beneficiaryId);

	//@Query("SELECT u.beneficiario.id FROM UserAdmProject up JOIN FETCH UserAdm u WHERE up.project.id= :projectId and u.perfil.id= :roleId")
	  //public Integer findByProjectIdAndRole(Long projectId, Long roleId);
	  @Query("SELECT u.beneficiario.id FROM UserAdmProject up JOIN up.userAdm u WHERE up.project.id = :projectId AND u.perfil.id = :roleId and up.estado='V'")
	  public Integer findByProjectIdAndRole(Long projectId, Long roleId);
	  
	  @Query("SELECT b FROM Beneficiary b  WHERE  b.estado = :estado order by b.tipo, b.nombres, b.razonSocial")
	  public List<Beneficiary> findAllByEstado(String estado);
	  
	  @Query("SELECT b FROM Beneficiary b  WHERE  b.estado = :estado and b.tipo=:tipo")
	  public List<Beneficiary> findAllByEstadoAndTipo(String estado, String tipo);

	  @Query("SELECT p FROM Project p " +
		       "JOIN UserAdmProject up ON up.project.id = p.id " +
		       "JOIN up.userAdm u ON u.id = up.userAdm.id " +
		       "WHERE u.beneficiario.id = :beneficiaryId")
	  public List<Project> findAllProjectsByBeneficiaryId(Integer beneficiaryId);
	  
	  @Query("SELECT b FROM Beneficiary b  WHERE  b.estado = 'V' and UPPER(b.tipo) =UPPER(:classExpense)")
	  public List<Beneficiary> findAllBeneficiariesByClassExpense(String classExpense);
	  
	  @Query("SELECT b FROM Beneficiary b  WHERE  b.estado = 'V' and UPPER(b.tipo) <>UPPER(:classExpense)")
	  public List<Beneficiary> findAllBeneficiariesByClassExpense2(@Param("classExpense")  String classExpense);

}
