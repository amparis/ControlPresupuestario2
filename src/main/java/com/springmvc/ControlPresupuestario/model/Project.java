package com.springmvc.ControlPresupuestario.model;

import lombok.*;
import javax.persistence.*;
import java.sql.Date;
import java.time.Instant;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_proyectos")
@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "proy_id")
    private long id;

    @Column(name = "proy_nombre", nullable = false)
    private String nombre;

    @Column(name = "proy_descripcion", nullable = false)
    private String descripcion;

    @Column(name = "proy_montocontrato", nullable = false)
    private double montoContrato;
    
    @Column(name = "proy_porcentaje_recurrente", nullable = false)
    private double porcentajeRecurrente;
    
    @Column(name = "proy_monto_recurrente", nullable = false)
    private double montoRecurrente;
    
    @Column(name = "proy_monto_no_recurrente", nullable = false)
    private double montoNoRecurrente;

    @Column(name = "proy_fechacreacion", nullable = false)
    private Date fechaCreacion;//private Instant fechaCreacion;

    @Column(name = "proy_fechainicial", nullable = false)
    private Date fechaInicial;

    @Column(name = "proy_fechafin", nullable = false)
    private Date fechaFin;

    @Column(name = "proy_estado", nullable = false)
    private String estado;

    //@ManyToOne
    //@JoinColumn(name = "us_id", nullable = false)
    //private Usuario usuario;
    @Column(name = "us_id", nullable = false)
    private long us_id;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public double getMontoContrato() {
		return montoContrato;
	}

	public void setMontoContrato(double montoContrato) {
		this.montoContrato = montoContrato;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Date getFechaInicial() {
		return fechaInicial;
	}

	public void setFechaInicial(Date fechaInicial) {
		this.fechaInicial = fechaInicial;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public long getUs_id() {
		return us_id;
	}

	public void setUs_id(long us_id) {
		this.us_id = us_id;
	}

	public double getPorcentajeRecurrente() {
		return porcentajeRecurrente;
	}

	public void setPorcentajeRecurrente(double porcentajeRecurrente) {
		this.porcentajeRecurrente = porcentajeRecurrente;
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
	// columnas adicionales
    @Column(name = "proy_cliente")
    private String cliente;
    
    @Column(name = "proy_gestion")//an;o del proyecto
    private Integer gestion;

    @Column(name = "proy_pais", nullable = false)
    private String pais;
    
    @Column(name = "proy_codigo", nullable = false)
    private String sigla;

	/**
	 * @return the cliente
	 */
	public String getCliente() {
		return cliente;
	}

	/**
	 * @param cliente the cliente to set
	 */
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	/**
	 * @return the gestion
	 */
	public Integer getGestion() {
		return gestion;
	}

	/**
	 * @param gestion the gestion to set
	 */
	public void setGestion(Integer gestion) {
		this.gestion = gestion;
	}

	/**
	 * @return the pais
	 */
	public String getPais() {
		return pais;
	}

	/**
	 * @param pais the pais to set
	 */
	public void setPais(String pais) {
		this.pais = pais;
	}

	/**
	 * @return the sigla
	 */
	public String getSigla() {
		return sigla;
	}

	/**
	 * @param sigla the sigla to set
	 */
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
    
    

}
