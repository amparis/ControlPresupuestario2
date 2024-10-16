package com.springmvc.ControlPresupuestario.model;

import lombok.*;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "tbl_rol_menu")
@Entity
@IdClass(RolMenuId.class)  // Clave compuesta
public class RolMenu {

    @Id
    @Column(name = "rm_rol_id", nullable = false)
    private Long roleId; // Mapeo de la clave foránea

    @Id
    @Column(name = "rm_me_id", nullable = false)
    private Long menuId; // Mapeo de la clave foránea

    @Column(name = "rm_estado")
    private String estado;

    @Column(name = "acreate")
    private Boolean acreate;

    @Column(name = "aread")
    private Boolean aread;

    @Column(name = "aupdate")
    private Boolean aupdate;

    @Column(name = "adelete")
    private Boolean adelete;

    // Relación con Menu
    @ManyToOne
    @JoinColumn(name = "rm_me_id", insertable = false, updatable = false)
    private Menu menu; // Relación con Menu

    // Relación con Perfil
    @ManyToOne
    @JoinColumn(name = "rm_rol_id", insertable = false, updatable = false)
    private Perfil role; // Relación con Perfil

	/**
	 * @return the roleId
	 */
	public Long getRoleId() {
		return roleId;
	}

	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	/**
	 * @return the menuId
	 */
	public Long getMenuId() {
		return menuId;
	}

	/**
	 * @param menuId the menuId to set
	 */
	public void setMenuId(Long menuId) {
		this.menuId = menuId;
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
	 * @return the acreate
	 */
	public Boolean getAcreate() {
		return acreate;
	}

	/**
	 * @param acreate the acreate to set
	 */
	public void setAcreate(Boolean acreate) {
		this.acreate = acreate;
	}

	/**
	 * @return the aread
	 */
	public Boolean getAread() {
		return aread;
	}

	/**
	 * @param aread the aread to set
	 */
	public void setAread(Boolean aread) {
		this.aread = aread;
	}

	/**
	 * @return the aupdate
	 */
	public Boolean getAupdate() {
		return aupdate;
	}

	/**
	 * @param aupdate the aupdate to set
	 */
	public void setAupdate(Boolean aupdate) {
		this.aupdate = aupdate;
	}

	/**
	 * @return the adelete
	 */
	public Boolean getAdelete() {
		return adelete;
	}

	/**
	 * @param adelete the adelete to set
	 */
	public void setAdelete(Boolean adelete) {
		this.adelete = adelete;
	}

	/**
	 * @return the menu
	 */
	public Menu getMenu() {
		return menu;
	}

	/**
	 * @param menu the menu to set
	 */
	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	/**
	 * @return the role
	 */
	public Perfil getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(Perfil role) {
		this.role = role;
	}

    // Getters y Setters


}
