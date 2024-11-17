package com.springmvc.ControlPresupuestario.model;

import java.math.BigDecimal;
import java.math.BigInteger;

public class SummaryExpenseVoucherDTO {
    private BigInteger  ben_id;
    private String fullname;
    private BigDecimal  totalUsd;
    private BigDecimal  totalLcu;
    private BigDecimal  gtotalUsd;
    private BigDecimal  gtotalLCU;
	public BigInteger getBen_id() {
		return ben_id;
	}
	public void setBen_id(BigInteger ben_id) {
		this.ben_id = ben_id;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public BigDecimal getTotalUsd() {
		return totalUsd;
	}
	public void setTotalUsd(BigDecimal totalUsd) {
		this.totalUsd = totalUsd;
	}
	public BigDecimal getTotalLcu() {
		return totalLcu;
	}
	public void setTotalLcu(BigDecimal totalLcu) {
		this.totalLcu = totalLcu;
	}
	public BigDecimal getGtotalUsd() {
		return gtotalUsd;
	}
	public void setGtotalUsd(BigDecimal gtotalUsd) {
		this.gtotalUsd = gtotalUsd;
	}
	public BigDecimal getGtotalLCU() {
		return gtotalLCU;
	}
	public void setGtotalLCU(BigDecimal gtotalLCU) {
		this.gtotalLCU = gtotalLCU;
	}
    
    
}
