package com.springmvc.ControlPresupuestario.model;

import lombok.*;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_rol_menu")
@Entity
@IdClass(RolMenuId.class)  // Composite key class
public class RolMenu {

    @Id
    @Column(name = "rm_rol_id", nullable = false)
    private Long roleId;

    @Id
    @Column(name = "rm_me_id", nullable = false)
    private Long menuId;

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

    @ManyToOne
    @JoinColumn(name = "rm_me_id", insertable = false, updatable = false)
    private Menu menu;

    @ManyToOne
    @JoinColumn(name = "rm_rol_id", insertable = false, updatable = false)
    private Perfil role;

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
	 * @return the create
	 */
	public Boolean getAcreate() {
		return acreate;
	}

	/**
	 * @param create the create to set
	 */
	public void setAcreate(Boolean acreate) {
		this.acreate = acreate;
	}

	/**
	 * @return the read
	 */
	public Boolean getAread() {
		return aread;
	}

	/**
	 * @param read the read to set
	 */
	public void setAread(Boolean aread) {
		this.aread = aread;
	}

	/**
	 * @return the update
	 */
	public Boolean getAupdate() {
		return aupdate;
	}

	/**
	 * @param update the update to set
	 */
	public void setAupdate(Boolean aupdate) {
		this.aupdate = aupdate;
	}

	/**
	 * @return the delete
	 */
	public Boolean getAdelete() {
		return adelete;
	}

	/**
	 * @param delete the delete to set
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
    
    

}
