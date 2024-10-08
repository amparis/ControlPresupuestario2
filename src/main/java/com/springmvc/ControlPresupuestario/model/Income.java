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

import java.math.BigDecimal;
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
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "cuenta_id",  insertable = false, updatable = false)//referencedColumnName = "cuenta_id", //foreignKey = @ForeignKey(name = "fk_tbl_ingr_relations_tbl_cuen"))
    private Account account;

    @ManyToOne
    @JoinColumn(name = "proy_id", insertable = false, updatable = false)// referencedColumnName = "proy_id",  foreignKey = @ForeignKey(name = "fk_tbl_ingr_relations_tbl_proy"))
    private Project proyecto;

    @Column(name = "ing_fecha", nullable = false)
    private Timestamp fecha;

    @Column(name = "ing_categoria", length = 50, nullable = false)
    private String categoria;
    
    @Column(name = "ing_concepto", length = 250)
    private String concepto;

    @Column(name = "ing_monto", precision = 18, scale = 2, nullable = false)
    private BigDecimal monto;

    @Column(name = "ing_porcent_recu", precision = 4, scale = 2, nullable = false)
    private BigDecimal porcentajeRecurrente;

    @Column(name = "ing_montore_currente", precision = 18, scale = 2, nullable = false)
    private BigDecimal montoRecurrente;

    @Column(name = "ing_monto_proyecto", precision = 18, scale = 2, nullable = false)
    private BigDecimal montoProyecto;

    @Column(name = "ing_estado", length = 10, nullable = false)
    private String estado;

    @Column(name = "ing_us_id", nullable = false)
    private Integer usuarioId;

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
	public Timestamp getFecha() {
		return fecha;
	}

	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(Timestamp fecha) {
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
	 * @return the monto
	 */
	public BigDecimal getMonto() {
		return monto;
	}

	/**
	 * @param monto the monto to set
	 */
	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	/**
	 * @return the porcentajeRecaudado
	 */
	public BigDecimal getPorcentajeRecurrente() {
		return porcentajeRecurrente;
	}

	/**
	 * @param porcentajeRecaudado the porcentajeRecaudado to set
	 */
	public void setPorcentajeRecurrente(BigDecimal porcentajeRecurrente) {
		this.porcentajeRecurrente = porcentajeRecurrente;
	}

	/**
	 * @return the montoRecaudadoCorriente
	 */
	public BigDecimal getMontoRecurrente() {
		return montoRecurrente;
	}

	/**
	 * @param montoRecaudadoCorriente the montoRecaudadoCorriente to set
	 */
	public void setMontoRecurrente(BigDecimal montoRecurrente) {
		this.montoRecurrente = montoRecurrente;
	}

	/**
	 * @return the montoProyecto
	 */
	public BigDecimal getMontoProyecto() {
		return montoProyecto;
	}

	/**
	 * @param montoProyecto the montoProyecto to set
	 */
	public void setMontoProyecto(BigDecimal montoProyecto) {
		this.montoProyecto = montoProyecto;
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

    
}
