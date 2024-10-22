package com.springmvc.ControlPresupuestario.model;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.JoinColumn;
import java.sql.Timestamp;
import java.sql.Date;

@Entity
@Table(name = "tbl_montos_contrato")
public class ProjectHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mc_id")
    private Long id;

    @Column(name = "mc_monto", nullable = false)
    private double amount;

    @Column(name = "mc_justificacion", nullable = false)
    private String observation;

    @Column(name = "mc_fecha_registro", nullable = false)
    private Timestamp registrationDate;

    @Column(name = "mc_fecha_inicial", nullable = false)
    private Date startDate;

    @Column(name = "mc_fecha_final", nullable = false)
    private Date endDate;

    @Column(name = "mc_estado", nullable = false)
    private String status;

    @ManyToOne
   @JoinColumn(name = "proy_id", nullable = false)
    private Project project;

    @Column(name = "mc_detalle", nullable = false)
    private String detail;

    
    @Column(name = "mc_correlativo")
    private Integer correlativo;
    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public Timestamp getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Timestamp registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
    
    @Column(name = "us_id", nullable = false)
    private Long us_id;

	/**
	 * @return the us_id
	 */
	public Long getUs_id() {
		return us_id;
	}

	/**
	 * @param us_id the us_id to set
	 */
	public void setUs_id(Long us_id) {
		this.us_id = us_id;
	}
    
}
