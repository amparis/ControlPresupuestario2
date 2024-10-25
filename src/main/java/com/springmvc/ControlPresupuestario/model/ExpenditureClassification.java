package com.springmvc.ControlPresupuestario.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_clasificacion_egresos")
public class ExpenditureClassification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "clase_id")
    private Long id;
    
    @Column(name = "clase_nombre", length = 100)
    private String nombreClase;

    @Column(name = "clase_descripcion", length = 250)
    private String descripcionClase;
    
    @Column(name = "clase_forma_pago", length = 10)
    private String formaPago;

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
	 * @return the nombreClase
	 */
	public String getNombreClase() {
		return nombreClase;
	}

	/**
	 * @param nombreClase the nombreClase to set
	 */
	public void setNombreClase(String nombreClase) {
		this.nombreClase = nombreClase;
	}

	/**
	 * @return the descripcionClase
	 */
	public String getDescripcionClase() {
		return descripcionClase;
	}

	/**
	 * @param descripcionClase the descripcionClase to set
	 */
	public void setDescripcionClase(String descripcionClase) {
		this.descripcionClase = descripcionClase;
	}

	/**
	 * @return the formaPago
	 */
	public String getFormaPago() {
		return formaPago;
	}

	/**
	 * @param formaPago the formaPago to set
	 */
	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}
    
    
}
