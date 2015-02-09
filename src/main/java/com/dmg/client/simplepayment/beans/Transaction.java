package com.dmg.client.simplepayment.beans;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.dmg.core.bean.AbstractPojo;

@Entity
@Table(name = "TRANSACTIONS")
public class Transaction extends AbstractPojo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 711201638919389292L;

	@Column(name = "CONTRACT_NO")
	private String contractNo;

	@Column(name = "TOTALAMT")
	private String totalAmount;

	@Column(name = "DOCNO")
	private String docNo;

	@Column(name = "INVDATE")
	private Date invDate;

	@Column(name = "YEARCODE")
	private String yearCode;

	@Column(name = "SRNO")
	private String serialNo;

	@Column(name = "CITY")
	private String city;

	@Column(name = "BUILDING_CODE")
	private String buildingCode;

	@Column(name = "STATUS")
	private String status;

	@Column(name = "APARTMENT_CODE")
	private String apartmentCode;

	@Column(name = "AMOUNT")
	private String amount;

	@Column(name = "FEES")
	private String fees;

	@Column(name = "SYNC_STATUS")
	private int syncStatus;

	@Column(name = "ACCOUNT_NUMBER")
	private String accountNumber;

	@Column(name = "BILL_NUMBER")
	private String billNumber;

	@Column(name = "PAYMENT_DATE")
	private Date paymentDate;

	public Transaction() {

	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getDocNo() {
		return docNo;
	}

	public void setDocNo(String docNo) {
		this.docNo = docNo;
	}

	public Date getInvDate() {
		return invDate;
	}

	public void setInvDate(Date invDate) {
		this.invDate = invDate;
	}

	public String getYearCode() {
		return yearCode;
	}

	public void setYearCode(String yearCode) {
		this.yearCode = yearCode;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getBuildingCode() {
		return buildingCode;
	}

	public void setBuildingCode(String buildingCode) {
		this.buildingCode = buildingCode;
	}

	public String getApartmentCode() {
		return apartmentCode;
	}

	public void setApartmentCode(String apartmentCode) {
		this.apartmentCode = apartmentCode;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getFees() {
		return fees;
	}

	public void setFees(String fees) {
		this.fees = fees;
	}

	public int getSyncStatus() {
		return syncStatus;
	}

	public void setSyncStatus(int syncStatus) {
		this.syncStatus = syncStatus;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getBillNumber() {
		return billNumber;
	}

	public void setBillNumber(String billNumber) {
		this.billNumber = billNumber;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

}
