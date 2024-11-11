package com.springmvc.ControlPresupuestario.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.sql.Date;
import java.sql.Timestamp;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_gastos")
public class ExpenseDisclaimers {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "gas_id")
	    private Long id;

	    @ManyToOne
	    @JoinColumn(name = "uni_id")
	    private UnitOfMeasurement unidadMedida;

	    @ManyToOne
	    @JoinColumn(name = "egre_id")
	    private Expense egreso;

	    @ManyToOne
	    @JoinColumn(name = "catg_id")
	    private ExpenseCategory categoriaGastos;

	    @Column(name = "gas_descripcion")
	    private String descripcion;

	    @Column(name = "gas_cantidad")
	    private Integer cantidad;

	    @Column(name = "gas_costo_unitario")
	    private double costoUnitario;

	    @Column(name = "gas_costo_total")
	    private double costoTotal;

	    @Column(name = "gas_total_usd")
	    private double totalUSD;

	    @Column(name = "gas_observacion")
	    private String observacion;

	    @Column(name = "gas_fecha")
	    private Date fecha;

	    @Column(name = "gas_fecha_registro")
	    private Timestamp fechaRegistro;

	    @Column(name = "us_id")
	    private Integer userId;

	    @Column(name = "gas_cantidad_pagos")
	    private Integer cantidadPagos;
	    
	    @Column(name = "gas_attach", columnDefinition = "text")
	    private String attach;

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
		 * @return the unidadMedida
		 */
		public UnitOfMeasurement getUnidadMedida() {
			return unidadMedida;
		}

		/**
		 * @param unidadMedida the unidadMedida to set
		 */
		public void setUnidadMedida(UnitOfMeasurement unidadMedida) {
			this.unidadMedida = unidadMedida;
		}

		/**
		 * @return the egreso
		 */
		public Expense getEgreso() {
			return egreso;
		}

		/**
		 * @param egreso the egreso to set
		 */
		public void setEgreso(Expense egreso) {
			this.egreso = egreso;
		}

		/**
		 * @return the categoriaGastos
		 */
		public ExpenseCategory getCategoriaGastos() {
			return categoriaGastos;
		}

		/**
		 * @param categoriaGastos the categoriaGastos to set
		 */
		public void setCategoriaGastos(ExpenseCategory categoriaGastos) {
			this.categoriaGastos = categoriaGastos;
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
		 * @return the cantidad
		 */
		public Integer getCantidad() {
			return cantidad;
		}

		/**
		 * @param cantidad the cantidad to set
		 */
		public void setCantidad(Integer cantidad) {
			this.cantidad = cantidad;
		}

		/**
		 * @return the costoUnitario
		 */
		public double getCostoUnitario() {
			return costoUnitario;
		}

		/**
		 * @param costoUnitario the costoUnitario to set
		 */
		public void setCostoUnitario(double costoUnitario) {
			this.costoUnitario = costoUnitario;
		}

		/**
		 * @return the costoTotal
		 */
		public double getCostoTotal() {
			return costoTotal;
		}

		/**
		 * @param costoTotal the costoTotal to set
		 */
		public void setCostoTotal(double costoTotal) {
			this.costoTotal = costoTotal;
		}

		/**
		 * @return the totalUSD
		 */
		public double getTotalUSD() {
			return totalUSD;
		}

		/**
		 * @param totalUSD the totalUSD to set
		 */
		public void setTotalUSD(double totalUSD) {
			this.totalUSD = totalUSD;
		}

		/**
		 * @return the observacion
		 */
		public String getObservacion() {
			return observacion;
		}

		/**
		 * @param observacion the observacion to set
		 */
		public void setObservacion(String observacion) {
			this.observacion = observacion;
		}

		/**
		 * @return the fecha
		 */
		public Date getFecha() {
			return fecha;
		}

		/**
		 * @param fecha the fecha to set
		 */
		public void setFecha(Date fecha) {
			this.fecha = fecha;
		}

		/**
		 * @return the fechaRegistro
		 */
		public Timestamp getFechaRegistro() {
			return fechaRegistro;
		}

		/**
		 * @param fechaRegistro the fechaRegistro to set
		 */
		public void setFechaRegistro(Timestamp fechaRegistro) {
			this.fechaRegistro = fechaRegistro;
		}

		/**
		 * @return the userId
		 */
		public Integer getUserId() {
			return userId;
		}

		/**
		 * @param userId the userId to set
		 */
		public void setUserId(Integer userId) {
			this.userId = userId;
		}

		/**
		 * @return the cantidadPagos
		 */
		public Integer getCantidadPagos() {
			return cantidadPagos;
		}

		/**
		 * @param cantidadPagos the cantidadPagos to set
		 */
		public void setCantidadPagos(Integer cantidadPagos) {
			this.cantidadPagos = cantidadPagos;
		}

		/**
		 * @return the attach
		 */
		public String getAttach() {
			return attach;
		}

		/**
		 * @param attach the attach to set
		 */
		public void setAttach(String attach) {
			this.attach = attach;
		}

}
