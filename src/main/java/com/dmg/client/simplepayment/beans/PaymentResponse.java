package com.dmg.client.simplepayment.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.dmg.core.bean.AbstractPojo;

@Entity
@Table(name = "TXNRESP")
public class PaymentResponse extends AbstractPojo {

	@Column(name = "vpc_3DSXID")
	private String d3sxid;
	@Column(name = "vpc_3DSenrolled")
	private String d3Senrolled;
	@Column(name = "vpc_AVSResultCode")
	private String aVSResultCode;
	@Column(name = "vpc_AcqAVSRespCode")
	private String acqAVSRespCode;
	@Column(name = "vpc_AcqCSCRespCode")
	private String acqCSCRespCode;
	@Column(name = "vpc_AcqResponseCode")
	private String acqResponseCode;
	@Column(name = "vpc_Amount")
	private String amount;
	@Column(name = "vpc_BatchNo")
	private String batchNo;
	@Column(name = "vpc_CSCResultCode")
	private String cSCResultCode;
	@Column(name = "vpc_Card")
	private String card;
	@Column(name = "vpc_Command")
	private String command;
	@Column(name = "vpc_Locale")
	private String locale;
	@Column(name = "vpc_MerchTxnRef")
	private String merchTxnRef;
	@Column(name = "vpc_Merchant")
	private String merchant;
	@Column(name = "vpc_Message")
	private String message;
	@Column(name = "vpc_OrderInfo")
	private String orderInfo;
	@Column(name = "vpc_ReceiptNo")
	private String receiptNo;
	@Column(name = "vpc_RiskOverallResult")
	private String riskOverallResult;
	@Column(name = "vpc_SecureHash")
	private String secureHash;
	@Column(name = "vpc_TransactionNo")
	private String transactionNo;
	@Column(name = "vpc_TxnResponseCode")
	private String txnResponseCode;
	@Column(name = "vpc_VerSecurityLevel")
	private String verSecurityLevel;
	@Column(name = "vpc_VerStatus")
	private String verStatus;
	@Column(name = "vpc_VerType")
	private String verType;
	@Column(name = "vpc_Version")
	private String version;
	@Column(name = "vpc_AuthorizeId")
	private String authorizeId;
	@Column(name = "vpc_CSCRequestCode")
	private String cSCRequestCode;
	@Column(name = "vpc_VerToken")
	private String verToken;
	@Column(name = "vpc_3DSECI")
	private String d3SECI;
	@Column(name = "vpc_3DSstatus")
	private String d3Sstatus;
	public String getD3sxid() {
		return d3sxid;
	}
	public void setD3sxid(String d3sxid) {
		this.d3sxid = d3sxid;
	}
	public String getD3Senrolled() {
		return d3Senrolled;
	}
	public void setD3Senrolled(String d3Senrolled) {
		this.d3Senrolled = d3Senrolled;
	}
	public String getaVSResultCode() {
		return aVSResultCode;
	}
	public void setaVSResultCode(String aVSResultCode) {
		this.aVSResultCode = aVSResultCode;
	}
	public String getAcqAVSRespCode() {
		return acqAVSRespCode;
	}
	public void setAcqAVSRespCode(String acqAVSRespCode) {
		this.acqAVSRespCode = acqAVSRespCode;
	}
	public String getAcqCSCRespCode() {
		return acqCSCRespCode;
	}
	public void setAcqCSCRespCode(String acqCSCRespCode) {
		this.acqCSCRespCode = acqCSCRespCode;
	}
	public String getAcqResponseCode() {
		return acqResponseCode;
	}
	public void setAcqResponseCode(String acqResponseCode) {
		this.acqResponseCode = acqResponseCode;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public String getcSCResultCode() {
		return cSCResultCode;
	}
	public void setcSCResultCode(String cSCResultCode) {
		this.cSCResultCode = cSCResultCode;
	}
	public String getCard() {
		return card;
	}
	public void setCard(String card) {
		this.card = card;
	}
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public String getLocale() {
		return locale;
	}
	public void setLocale(String locale) {
		this.locale = locale;
	}
	public String getMerchTxnRef() {
		return merchTxnRef;
	}
	public void setMerchTxnRef(String merchTxnRef) {
		this.merchTxnRef = merchTxnRef;
	}
	public String getMerchant() {
		return merchant;
	}
	public void setMerchant(String merchant) {
		this.merchant = merchant;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getOrderInfo() {
		return orderInfo;
	}
	public void setOrderInfo(String orderInfo) {
		this.orderInfo = orderInfo;
	}
	public String getReceiptNo() {
		return receiptNo;
	}
	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}
	public String getRiskOverallResult() {
		return riskOverallResult;
	}
	public void setRiskOverallResult(String riskOverallResult) {
		this.riskOverallResult = riskOverallResult;
	}
	public String getSecureHash() {
		return secureHash;
	}
	public void setSecureHash(String secureHash) {
		this.secureHash = secureHash;
	}
	public String getTransactionNo() {
		return transactionNo;
	}
	public void setTransactionNo(String transactionNo) {
		this.transactionNo = transactionNo;
	}
	public String getTxnResponseCode() {
		return txnResponseCode;
	}
	public void setTxnResponseCode(String txnResponseCode) {
		this.txnResponseCode = txnResponseCode;
	}
	public String getVerSecurityLevel() {
		return verSecurityLevel;
	}
	public void setVerSecurityLevel(String verSecurityLevel) {
		this.verSecurityLevel = verSecurityLevel;
	}
	public String getVerStatus() {
		return verStatus;
	}
	public void setVerStatus(String verStatus) {
		this.verStatus = verStatus;
	}
	public String getVerType() {
		return verType;
	}
	public void setVerType(String verType) {
		this.verType = verType;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getAuthorizeId() {
		return authorizeId;
	}
	public void setAuthorizeId(String authorizeId) {
		this.authorizeId = authorizeId;
	}
	public String getcSCRequestCode() {
		return cSCRequestCode;
	}
	public void setcSCRequestCode(String cSCRequestCode) {
		this.cSCRequestCode = cSCRequestCode;
	}
	public String getVerToken() {
		return verToken;
	}
	public void setVerToken(String verToken) {
		this.verToken = verToken;
	}
	public String getD3SECI() {
		return d3SECI;
	}
	public void setD3SECI(String d3seci) {
		d3SECI = d3seci;
	}
	public String getD3Sstatus() {
		return d3Sstatus;
	}
	public void setD3Sstatus(String d3Sstatus) {
		this.d3Sstatus = d3Sstatus;
	}
	@Override
	public String toString() {
		return "PaymentResponse [d3sxid=" + d3sxid + ", d3Senrolled="
				+ d3Senrolled + ", aVSResultCode=" + aVSResultCode
				+ ", acqAVSRespCode=" + acqAVSRespCode + ", acqCSCRespCode="
				+ acqCSCRespCode + ", acqResponseCode=" + acqResponseCode
				+ ", amount=" + amount + ", batchNo=" + batchNo
				+ ", cSCResultCode=" + cSCResultCode + ", card=" + card
				+ ", command=" + command + ", locale=" + locale
				+ ", merchTxnRef=" + merchTxnRef + ", merchant=" + merchant
				+ ", message=" + message + ", orderInfo=" + orderInfo
				+ ", receiptNo=" + receiptNo + ", riskOverallResult="
				+ riskOverallResult + ", secureHash=" + secureHash
				+ ", transactionNo=" + transactionNo + ", txnResponseCode="
				+ txnResponseCode + ", verSecurityLevel=" + verSecurityLevel
				+ ", verStatus=" + verStatus + ", verType=" + verType
				+ ", version=" + version + ", authorizeId=" + authorizeId
				+ ", cSCRequestCode=" + cSCRequestCode + ", verToken="
				+ verToken + ", d3SECI=" + d3SECI + ", d3Sstatus=" + d3Sstatus
				+ ", id=" + id + ", creationDate=" + creationDate
				+ ", updateDate=" + updateDate + "]";
	}
	
	
	

}
