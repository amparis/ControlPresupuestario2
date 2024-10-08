package com.springmvc.ControlPresupuestario.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "movimientos")
@Entity
public class Transaction {
//Yggdrasil2024*  PASSWORD SUPABASE
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private float cuantity;

    private String concept;

    private Date dateCreation;

    private Date dateUpdate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_e")
    private Employee employee;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the cuantity
	 */
	public float getCuantity() {
		return cuantity;
	}

	/**
	 * @param cuantity the cuantity to set
	 */
	public void setCuantity(float cuantity) {
		this.cuantity = cuantity;
	}

	/**
	 * @return the concept
	 */
	public String getConcept() {
		return concept;
	}

	/**
	 * @param concept the concept to set
	 */
	public void setConcept(String concept) {
		this.concept = concept;
	}

	/**
	 * @return the dateCreation
	 */
	public Date getDateCreation() {
		return dateCreation;
	}

	/**
	 * @param dateCreation the dateCreation to set
	 */
	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	/**
	 * @return the dateUpdate
	 */
	public Date getDateUpdate() {
		return dateUpdate;
	}

	/**
	 * @param dateUpdate the dateUpdate to set
	 */
	public void setDateUpdate(Date dateUpdate) {
		this.dateUpdate = dateUpdate;
	}

	/**
	 * @return the employee
	 */
	public Employee getEmployee() {
		return employee;
	}

	/**
	 * @param employee the employee to set
	 */
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

}
