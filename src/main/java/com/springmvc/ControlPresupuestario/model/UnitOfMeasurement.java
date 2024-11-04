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
@Table(name = "tbl_unidad_medida")
public class UnitOfMeasurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uni_id")
    private Long id;
    
    @Column(name = "uni_descripcion", length = 100)
    private String descripcionUnidad;
    
    
    @Column(name = "uni_abreviatura", length = 100)
    private String abrevitura;
    
    
    @Column(name = "uni_estado", length = 100)
    private String estado;


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
	 * @return the descripcionUnidad
	 */
	public String getDescripcionUnidad() {
		return descripcionUnidad;
	}


	/**
	 * @param descripcionUnidad the descripcionUnidad to set
	 */
	public void setDescripcionUnidad(String descripcionUnidad) {
		this.descripcionUnidad = descripcionUnidad;
	}


	/**
	 * @return the abrevitura
	 */
	public String getAbrevitura() {
		return abrevitura;
	}


	/**
	 * @param abrevitura the abrevitura to set
	 */
	public void setAbrevitura(String abrevitura) {
		this.abrevitura = abrevitura;
	}


	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}


	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}
    
}
