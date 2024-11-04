package com.springmvc.ControlPresupuestario.model;
import lombok.*;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_programacion_pagos")
public class PaymentPlan {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "pp_id", nullable = false)
	    private Long id;

	    @ManyToOne
	    @JoinColumn(name = "proy_id")
	    private Project proyecto;

	    @Column(name = "pp_descripcion", length = 255)
	    private String descripcion;

	    @Column(name = "pp_monto")
	    private double monto;
	    
	    @Column(name = "pp_monto_pagado")
	    private double montoPagado;
	    
	    @Column(name = "pp_estado")
	    private String estado;

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
		 * @return the proyecto
		 */
		public Project getProyecto() {
			return proyecto;
		}

		/**
		 * @param proyecto the proyecto to set
		 */
		public void setProyecto(Project proyecto) {
			this.proyecto = proyecto;
		}

		/**
		 * @return the descripcion
		 */
		public String getDescripcion() {
			return descripcion;
		}

		/**
		 * @param descripcion the descripcion to set
		 */
		public void setDescripcion(String descripcion) {
			this.descripcion = descripcion;
		}

		/**
		 * @return the monto
		 */
		public double getMonto() {
			return monto;
		}

		/**
		 * @param monto the monto to set
		 */
		public void setMonto(double monto) {
			this.monto = monto;
		}

		/**
		 * @return the montoPagado
		 */
		public double getMontoPagado() {
			return montoPagado;
		}

		/**
		 * @param montoPagado the montoPagado to set
		 */
		public void setMontoPagado(double montoPagado) {
			this.montoPagado = montoPagado;
		}
	    
}
