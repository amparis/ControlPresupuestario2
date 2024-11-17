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

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_divisas")

public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "divisa_id")
    private Integer id;

    @Column(name = "divisa_pais_cod",  length = 255)
    private String divisa_cod;

    @Column(name = "divisa_pais", length = 255)
    private String divisa;

    @ManyToOne
    @JoinColumn(name = "pais_id")
    private Country pais;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDivisa_cod() {
		return divisa_cod;
	}

	public void setDivisa_cod(String divisa_cod) {
		this.divisa_cod = divisa_cod;
	}

	public String getDivisa() {
		return divisa;
	}

	public void setDivisa(String divisa) {
		this.divisa = divisa;
	}

	public Country getPais() {
		return pais;
	}

	public void setPais(Country pais) {
		this.pais = pais;
	}
    

}
