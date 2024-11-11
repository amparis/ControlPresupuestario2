package com.springmvc.ControlPresupuestario.model;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import java.sql.Date;


@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_beneficiario")
public class Beneficiary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ben_id")
    private Integer id;

    @Column(name = "ben_tipo", length = 50)
    private String tipo;

    @Column(name = "ben_nombres", length = 100)
    private String nombres;

    @Column(name = "ben_apellidos", length = 100)
    private String apellidos;
    
    @Column(name = "ben_doc", length = 50)
    private String documento;

    @Column(name = "ben_razon_social", length = 300)
    private String razonSocial;

    @Column(name = "ben_estado", length = 5)
    private String estado;

    @Column(name = "ben_observacion", length = 1024)
    private String observacion;

    @ManyToOne
    @JoinColumn(name = "pais_id")
    private Country pais;

    @Column(name = "ben_email", length = 100)
    private String email;

    @Column(name = "ben_telefono_contacto", length = 50)
    private String telefonoContacto;

    @Column(name = "ben_us_id")
    private Integer usuarioId; // Relación con usuario, pero manejada manualmente

    @Column(name = "ben_fecha_ingreso")
    private Date fechaIngreso;

    // Getters y Setters


    public void setId(Integer id) {
        this.id = id;
    }


	public String getNombres() {
		return nombres;
	}


	public void setNombres(String nombres) {
		this.nombres = nombres;
	}


	public String getApellidos() {
		return apellidos;
	}


	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}


	public Integer getId() {
		return id;
	}

	public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefonoContacto() {
        return telefonoContacto;
    }

    public void setTelefonoContacto(String telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }


	public Country getPais() {
		return pais;
	}


	public void setPais(Country pais) {
		this.pais = pais;
	}
    
    
}
