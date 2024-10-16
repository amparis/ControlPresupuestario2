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

@Entity
@Table(name = "tbl_usuario_proyecto")
public class UserAdmProject {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "up_id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "up_proy_id", nullable = false)
    private Project project;

    @ManyToOne
    @JoinColumn(name = "up_us_id", nullable = false)
    private UserAdm userAdm;

    @Column(name = "up_estado", nullable = false, length = 255)
    private String estado;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public UserAdm getUserAdm() {
        return userAdm;
    }

    public void setUserAdm(UserAdm userAdm) {
        this.userAdm = userAdm;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}
