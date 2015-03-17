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

	@Column(name = "CITY")
	private String city;

	@Column(name = "vpc_OrderInfo")
	private String orderInfo;

	@Column(name = "STATUS")
	private String status;

	@Column(name = "vpc_Amount")
	private String amount;

	@Column(name = "vpc_Command")
	private String command;

	@Column(name = "vpc_MerchTxnRef")
	private String merchTxnRef;

	@Column(name = "vpc_SecureHash")
	private String secureHash;

	@Column(name = "vpc_SecureHashType")
	private String secureHashType;

	@Column(name = "SYNC_STATUS")
	private int syncStatus;

	@Column(name = "vpc_Version")
	private String version;

	@Column(name = "vpc_Locale")
	private String locale;

	@Column(name = "vpc_ReturnURL")
	private String returnURL;

	@Column(name = "vpc_AccessCode")
	private String accessCode;

	@Column(name = "vpc_Merchant")
	private String merchant;

	@Column(name = "FEES")
	private String fees;
	
	@Column(name = "INV_DOCNO")
	private String invDocNo;
	
	@Column(name = "INV_DOCTYPE")
	private String invDocType;
	
	@Column(name = "INV_YEARCODE")
	private String invYearCode;

	public Transaction() {

	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getOrderInfo() {
		return orderInfo;
	}

	public void setOrderInfo(String orderInfo) {
		this.orderInfo = orderInfo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String getMerchTxnRef() {
		return merchTxnRef;
	}

	public void setMerchTxnRef(String merchTxnRef) {
		this.merchTxnRef = merchTxnRef;
	}

	public String getSecureHash() {
		return secureHash;
	}

	public void setSecureHash(String secureHash) {
		this.secureHash = secureHash;
	}

	public String getSecureHashType() {
		return secureHashType;
	}

	public void setSecureHashType(String secureHashType) {
		this.secureHashType = secureHashType;
	}

	public int getSyncStatus() {
		return syncStatus;
	}

	public void setSyncStatus(int syncStatus) {
		this.syncStatus = syncStatus;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getReturnURL() {
		return returnURL;
	}

	public void setReturnURL(String returnURL) {
		this.returnURL = returnURL;
	}

	public String getAccessCode() {
		return accessCode;
	}

	public void setAccessCode(String accessCode) {
		this.accessCode = accessCode;
	}

	public String getMerchant() {
		return merchant;
	}

	public void setMerchant(String merchant) {
		this.merchant = merchant;
	}

	public String getFees() {
		return fees;
	}

	public void setFees(String fees) {
		this.fees = fees;
	}
	
	public String getInvDocNo() {
		return invDocNo;
	}

	public void setInvDocNo(String invDocNo) {
		this.invDocNo = invDocNo;
	}

	public String getInvDocType() {
		return invDocType;
	}

	public void setInvDocType(String invDocType) {
		this.invDocType = invDocType;
	}

	public String getInvYearCode() {
		return invYearCode;
	}

	public void setInvYearCode(String invYearCode) {
		this.invYearCode = invYearCode;
	}

	@Override
	public String toString() {
		return "Transaction [contractNo=" + contractNo + ", city=" + city
				+ ", orderInfo=" + orderInfo + ", status=" + status
				+ ", amount=" + amount + ", command=" + command
				+ ", merchTxnRef=" + merchTxnRef + ", secureHash=" + secureHash
				+ ", secureHashType=" + secureHashType + ", syncStatus="
				+ syncStatus + ", version=" + version + ", locale=" + locale
				+ ", returnURL=" + returnURL + ", accessCode=" + accessCode
				+ ", merchant=" + merchant + ", fees=" + fees + ", id=" + id
				+ ", creationDate=" + creationDate + ", updateDate="
				+ updateDate + "]";
	}

}
