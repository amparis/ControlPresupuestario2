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
@Table(name = "tbl_paises")
public class Country {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "pais_id")
	    private Long id;

	    @Column(name = "pais_nombre", nullable = false, length = 255)
	    private String nombre;

	    @Column(name = "pais_nombre_espaniol", nullable = false, length = 255)
	    private String nombreEspaniol;

	    @Column(name = "pais_divisa", length = 50)
	    private String divisa;

	    @Column(name = "pais_cod_divisa", length = 10)
	    private String codDivisa;

	    @Column(name = "pais_cod_phone", length = 10)
	    private String codPhone;

	    @Column(name = "pais_iso2", length = 2)
	    private String iso2;

	    @Column(name = "pais_iso3", length = 3)
	    private String iso3;

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
		 * @return the nombreEspaniol
		 */
		public String getNombreEspaniol() {
			return nombreEspaniol;
		}

		/**
		 * @param nombreEspaniol the nombreEspaniol to set
		 */
		public void setNombreEspaniol(String nombreEspaniol) {
			this.nombreEspaniol = nombreEspaniol;
		}

		/**
		 * @return the divisa
		 */
		public String getDivisa() {
			return divisa;
		}

		/**
		 * @param divisa the divisa to set
		 */
		public void setDivisa(String divisa) {
			this.divisa = divisa;
		}

		/**
		 * @return the codDivisa
		 */
		public String getCodDivisa() {
			return codDivisa;
		}

		/**
		 * @param codDivisa the codDivisa to set
		 */
		public void setCodDivisa(String codDivisa) {
			this.codDivisa = codDivisa;
		}

		/**
		 * @return the codPhone
		 */
		public String getCodPhone() {
			return codPhone;
		}

		/**
		 * @param codPhone the codPhone to set
		 */
		public void setCodPhone(String codPhone) {
			this.codPhone = codPhone;
		}

		/**
		 * @return the iso2
		 */
		public String getIso2() {
			return iso2;
		}

		/**
		 * @param iso2 the iso2 to set
		 */
		public void setIso2(String iso2) {
			this.iso2 = iso2;
		}

		/**
		 * @return the iso3
		 */
		public String getIso3() {
			return iso3;
		}

		/**
		 * @param iso3 the iso3 to set
		 */
		public void setIso3(String iso3) {
			this.iso3 = iso3;
		}

	    // Getters and Setters are managed by Lombok annotations

}
