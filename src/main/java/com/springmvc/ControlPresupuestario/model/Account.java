package com.springmvc.ControlPresupuestario.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_cuentas")
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cuenta_id")
    //@JoinColumn(name = "id_r")
    private Long id;
    
    @Column(name = "cuenta_numero", length = 100, nullable = false)
    private String accountNumber;

    @Column(name = "cuenta_banco", length = 250, nullable = false)
    private String nameBank;
    
    @Column(name = "cuenta_pais", length = 100, nullable = false)
    private String country;

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
	 * @return the accountNumber
	 */
	public String getAccountNumber() {
		return accountNumber;
	}

	/**
	 * @param accountNumber the accountNumber to set
	 */
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	/**
	 * @return the nameBank
	 */
	public String getNameBank() {
		return nameBank;
	}

	/**
	 * @param nameBank the nameBank to set
	 */
	public void setNameBank(String nameBank) {
		this.nameBank = nameBank;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	

}
