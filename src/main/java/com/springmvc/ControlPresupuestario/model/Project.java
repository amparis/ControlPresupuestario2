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

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
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
	 * @return the montoContrato
	 */
	public double getMontoContrato() {
		return montoContrato;
	}

	/**
	 * @param montoContrato the montoContrato to set
	 */
	public void setMontoContrato(double montoContrato) {
		this.montoContrato = montoContrato;
	}

	/**
	 * @return the fechaCreacion
	 */
	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	/**
	 * @param fechaCreacion the fechaCreacion to set
	 */
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	/**
	 * @return the fechaInicial
	 */
	public Date getFechaInicial() {
		return fechaInicial;
	}

	/**
	 * @param fechaInicial the fechaInicial to set
	 */
	public void setFechaInicial(Date fechaInicial) {
		this.fechaInicial = fechaInicial;
	}

	/**
	 * @return the fechaFin
	 */
	public Date getFechaFin() {
		return fechaFin;
	}

	/**
	 * @param fechaFin the fechaFin to set
	 */
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
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
	 * @return the us_id
	 */
	public long getUs_id() {
		return us_id;
	}

	/**
	 * @param us_id the us_id to set
	 */
	public void setUs_id(long us_id) {
		this.us_id = us_id;
	}

    // Getters and setters are generated by Lombok (@Getter and @Setter)

}