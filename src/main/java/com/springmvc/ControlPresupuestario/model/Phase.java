package com.springmvc.ControlPresupuestario.model;
import lombok.*;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "tbl_fases")
public class Phase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fase_id")
    private Long id;

    @Column(name = "fase_nombre", nullable = false, length = 255)
    private String nombre;

    @Column(name = "fase_descripcion", length = 255)
    private String faseDescripcion;

    @Column(name = "fase_estado", length = 50)
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
	 * @return the faseDescripcion
	 */
	public String getFaseDescripcion() {
		return faseDescripcion;
	}

	/**
	 * @param faseDescripcion the faseDescripcion to set
	 */
	public void setFaseDescripcion(String faseDescripcion) {
		this.faseDescripcion = faseDescripcion;
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


}
