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
@Table(name = "tbl_ingresos")
@Entity
public class Income {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ing_id")
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "cuenta_id")//referencedColumnName = "cuenta_id", //foreignKey = @ForeignKey(name = "fk_tbl_ingr_relations_tbl_cuen"))
    private Account account;

    @ManyToOne
    @JoinColumn(name = "proy_id")
    private Project proyecto;
    
    @ManyToOne
    @JoinColumn(name = "pp_id")
    private PaymentPlan planPago;

    @Column(name = "ing_fecha", nullable = false)
    private Date fecha;

    @Column(name = "ing_categoria", length = 50, nullable = false)
    private String categoria;
    
    @Column(name = "ing_concepto", length = 250)
    private String concepto;

    @Column(name = "ing_monto", precision = 18, scale = 2, nullable = false)
    private double monto;

     @Column(name = "ing_monto_recurrente", precision = 18, scale = 2, nullable = false)
    private double montoRecurrente;

    @Column(name = "ing_monto_no_recurrente", precision = 18, scale = 2, nullable = false)
    private double montoNoRecurrente;

    @Column(name = "ing_estado", length = 10, nullable = false)
    private String estado;

    @Column(name = "ing_us_id", nullable = false)
    private Integer usuarioId;

    @Column(name = "ing_fecha_registro", nullable = false)
    private Timestamp fechaRegistro;

    @Column(name = "ing_proy_id_prestamo")
    private Long proyIdPrestamo;

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the account
	 */
	public Account getAccount() {
		return account;
	}

	/**
	 * @param account the account to set
	 */
	public void setAccount(Account account) {
		this.account = account;
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
	 * @return the categoria
	 */
	public String getCategoria() {
		return categoria;
	}

	/**
	 * @param categoria the categoria to set
	 */
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	/**
	 * @return the concepto
	 */
	public String getConcepto() {
		return concepto;
	}

	/**
	 * @param concepto the concepto to set
	 */
	public void setConcepto(String concepto) {
		this.concepto = concepto;
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

	/**
	 * @return the usuarioId
	 */
	public Integer getUsuarioId() {
		return usuarioId;
	}

	/**
	 * @param usuarioId the usuarioId to set
	 */
	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
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
	 * @return the proyIdPrestamo
	 */
	public Long getProyIdPrestamo() {
		return proyIdPrestamo;
	}

	/**
	 * @param proyIdPrestamo the proyIdPrestamo to set
	 */
	public void setProyIdPrestamo(Long proyIdPrestamo) {
		this.proyIdPrestamo = proyIdPrestamo;
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
	 * @return the montoRecurrente
	 */
	public double getMontoRecurrente() {
		return montoRecurrente;
	}

	/**
	 * @param montoRecurrente the montoRecurrente to set
	 */
	public void setMontoRecurrente(double montoRecurrente) {
		this.montoRecurrente = montoRecurrente;
	}

	/**
	 * @return the montoNoRecurrente
	 */
	public double getMontoNoRecurrente() {
		return montoNoRecurrente;
	}

	/**
	 * @param montoNoRecurrente the montoNoRecurrente to set
	 */
	public void setMontoNoRecurrente(double montoNoRecurrente) {
		this.montoNoRecurrente = montoNoRecurrente;
	}

	/**
	 * @return the planPago
	 */
	public PaymentPlan getPlanPago() {
		return planPago;
	}

	/**
	 * @param planPago the planPago to set
	 */
	public void setPlanPago(PaymentPlan planPago) {
		this.planPago = planPago;
	}

    
}
