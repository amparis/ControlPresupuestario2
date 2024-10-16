
package com.springmvc.ControlPresupuestario.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_menu")
@Entity
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "me_id")
    private Long id;

    @Column(name = "me_descripcion", nullable = false)
    private String nombre;

    @Column(name = "me_url")
    private String url;

    @Column(name = "me_icono")
    private String icono;

    @Column(name = "me_orden")
    private Long orden;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "me_id_padre")
    private Menu supMenu; // Menú superior
    
    @OneToMany(mappedBy = "supMenu", cascade = CascadeType.ALL)
    private List<Menu> subMenus = new ArrayList<>(); // Lista de submenús


    @Column(name = "me_estado", nullable = false)
    private String estado;

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
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the icono
	 */
	public String getIcono() {
		return icono;
	}

	/**
	 * @param icono the icono to set
	 */
	public void setIcono(String icono) {
		this.icono = icono;
	}

	/**
	 * @return the orden
	 */
	public Long getOrden() {
		return orden;
	}

	/**
	 * @param orden the orden to set
	 */
	public void setOrden(Long orden) {
		this.orden = orden;
	}

	/**
	 * @return the supMenu
	 */
	public Menu getSupMenu() {
		return supMenu;
	}

	/**
	 * @param supMenu the supMenu to set
	 */
	public void setSupMenu(Menu supMenu) {
		this.supMenu = supMenu;
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
	 * @return the subMenus
	 */
	public List<Menu> getSubMenus() {
		return subMenus;
	}

	/**
	 * @param subMenus the subMenus to set
	 */
	public void setSubMenus(List<Menu> subMenus) {
		this.subMenus = subMenus;
	}



    // Constructors, Getters, and Setters are managed by Lombok
    
}
