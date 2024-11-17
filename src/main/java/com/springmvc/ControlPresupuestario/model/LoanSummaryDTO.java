package com.springmvc.ControlPresupuestario.model;

import java.math.BigDecimal;
import java.math.BigInteger;

public class LoanSummaryDTO {
	    private BigInteger  prestamoId;
	    private BigDecimal  prestado;
	    private Integer proyectoId;
	    private String nombre;
	    private Integer cuentaId;
	    private String nameBank;
   private BigDecimal  devuelto;
	    private BigDecimal  saldo;

	    // Getters y setters
	    public BigInteger  getPrestamoId() { return prestamoId; }
	    public void setPrestamoId(BigInteger  prestamoId) { this.prestamoId = prestamoId; }

	    public BigDecimal  getPrestado() { return prestado; }
	    public void setPrestado(BigDecimal  prestado) { this.prestado = prestado; }

	    public Integer getProyectoId() { return proyectoId; }
	    public void setProyectoId(Integer proyectoId) { this.proyectoId = proyectoId; }

	    public BigDecimal  getDevuelto() { return devuelto; }
	    public void setDevuelto(BigDecimal  devuelto) { this.devuelto = devuelto; }

	    public BigDecimal  getSaldo() { return saldo; }
	    public void setSaldo(BigDecimal  saldo) { this.saldo = saldo; }
	    
		public String getNombre() {
			return nombre;
		}
		public void setNombre(String nombre) {
			this.nombre = nombre;
		}
		public Integer getCuentaId() {
			return cuentaId;
		}
		public void setCuentaId(Integer cuentaId) {
			this.cuentaId = cuentaId;
		}
		public String getNameBank() {
			return nameBank;
		}
		public void setNameBank(String nameBank) {
			this.nameBank = nameBank;
		}
	    
}
