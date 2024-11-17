package com.springmvc.ControlPresupuestario.model;

public class BeneficiaryExpenseSummaryDTO {
 
    private double sumatoriaDescargo;

    private Integer id;

    private String tipo;

    private String nombres;

    private String apellidos;

    private String documento;

    // Constructor que recibe los resultados de la consulta
    /*public BeneficiaryExpenseSummaryDTO(Long id,String nombres, String apellidos, String documento, String tipo, Double sumatoriaDescargo) {
        this.id = id;
    	this.nombres = nombres;
        this.apellidos = apellidos;
        this.documento = documento;
        this.tipo = tipo;
        this.sumatoriaDescargo = sumatoriaDescargo;
    }*/
    
    public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
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


	public String getDocumento() {
		return documento;
	}


	public void setDocumento(String documento) {
		this.documento = documento;
	}

/*
	public BeneficiaryExpenseSummaryDTO(Beneficiary beneficiario, double sumatoriaDescargo) {
        this.beneficiario = beneficiario;
        this.sumatoriaDescargo = sumatoriaDescargo;
    }*/


	public double getSumatoriaDescargo() {
		return sumatoriaDescargo;
	}

	public void setSumatoriaDescargo(double sumatoriaDescargo) {
		this.sumatoriaDescargo = sumatoriaDescargo;
	}

}
