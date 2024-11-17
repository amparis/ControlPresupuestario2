package com.springmvc.ControlPresupuestario.model;

public class AccountBalanceDTO {

    private Long accountId;
    private String nameBank;
    private String accountNumber;
    private Double totalAmount;
    private Double totalRecurringAmount;
    private Double totalNonRecurringAmount;
	/**
	 * @return the accountId
	 */
	public Long getAccountId() {
		return accountId;
	}
	/**
	 * @param accountId the accountId to set
	 */
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
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
	 * @return the totalAmount
	 */
	public Double getTotalAmount() {
		return totalAmount;
	}
	/**
	 * @param totalAmount the totalAmount to set
	 */
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}
	/**
	 * @return the totalRecurringAmount
	 */
	public Double getTotalRecurringAmount() {
		return totalRecurringAmount;
	}
	/**
	 * @param totalRecurringAmount the totalRecurringAmount to set
	 */
	public void setTotalRecurringAmount(Double totalRecurringAmount) {
		this.totalRecurringAmount = totalRecurringAmount;
	}
	/**
	 * @return the totalNonRecurringAmount
	 */
	public Double getTotalNonRecurringAmount() {
		return totalNonRecurringAmount;
	}
	/**
	 * @param totalNonRecurringAmount the totalNonRecurringAmount to set
	 */
	public void setTotalNonRecurringAmount(Double totalNonRecurringAmount) {
		this.totalNonRecurringAmount = totalNonRecurringAmount;
	}
    

}
