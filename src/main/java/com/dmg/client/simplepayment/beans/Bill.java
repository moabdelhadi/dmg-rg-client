package com.dmg.client.simplepayment.beans;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.dmg.core.bean.AbstractPojo;

@MappedSuperclass
public class Bill extends AbstractPojo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "DOCNO")
	protected String docNo;

	@Column(name = "DOCTYPE")
	protected String docType;

	@Column(name = "YEARCODE")
	protected String yearCode;

	@Column(name = "SRNO")
	protected String serialNo;
	
	@Column(name = "INVNO")
	protected String invNo;

	@Column(name = "PARTYNAME")
	protected String partyName;

	@Column(name = "PREV_BALANCE")
	protected BigDecimal prevBalance;

	@Column(name = "LAST_REC_DOCDATE")
	protected Date lastReceivingDate;

	@Column(name = "LAST_REC_AMT")
	protected BigDecimal LastReceivingAmount;

	@Column(name = "CITY")
	protected String city;

	@Column(name = "INVDATE")
	protected Date billDate;

	@Column(name = "SERVICE")
	protected BigDecimal service;

	@Column(name = "GAS_DIFFERENCE")
	protected BigDecimal gasDifference;

	@Column(name = "LAST_REC_PAY_REF")
	protected String lastReceivedPayReference;

	@Column(name = "COLLECTOR_NAME")
	protected String collectorName;

	@Column(name = "LAST_READING")
	protected String lastReading;

	@Column(name = "LAST_READING_DATE")
	protected Date lastReadingDate;

	@Column(name = "CURR_READING")
	protected String currentReading;

	@Column(name = "CURR_READING_DATE")
	protected Date currentReadingDate;

	@Column(name = "BUILDING_CODE")
	protected String buildingCode;

	@Column(name = "BUILDING_NAME")
	protected String buildingName;

	@Column(name = "APARTMENT_CODE")
	protected String apartmentCode;

	@Column(name = "TOTAL_UNIT")
	protected String totalUnit;

	@Column(name = "UNIT_PRICE")
	protected String unitPrice;

	@Column(name = "AMT")
	protected BigDecimal amount;

	@Column(name = "TOTALAMT")
	protected BigDecimal totalAmount;

	@Column(name = "CONTRACT_NO")
	protected String contractNo;

	@Column(name = "RECEIVED_AMT")
	protected BigDecimal receivedAmmount;

	
	public Bill() {

	}

	public String getDocNo() {
		return docNo;
	}

	public void setDocNo(String docNo) {
		this.docNo = docNo;
	}

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
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

	
	public String getInvNo() {
		return invNo;
	}

	public void setInvNo(String invNo) {
		this.invNo = invNo;
	}

	public String getPartyName() {
		return partyName;
	}

	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}

	public BigDecimal getPrevBalance() {
		return prevBalance;
	}

	public void setPrevBalance(BigDecimal prevBalance) {
		this.prevBalance = prevBalance;
	}

	public Date getLastReceivingDate() {
		return lastReceivingDate;
	}

	public void setLastReceivingDate(Date lastReceivingDate) {
		this.lastReceivingDate = lastReceivingDate;
	}

	public BigDecimal getLastReceivingAmount() {
		return LastReceivingAmount;
	}

	public void setLastReceivingAmount(BigDecimal lastReceivingAmount) {
		LastReceivingAmount = lastReceivingAmount;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Date getBillDate() {
		return billDate;
	}

	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}

	public BigDecimal getService() {
		return service;
	}

	public void setService(BigDecimal service) {
		this.service = service;
	}

	public BigDecimal getGasDifference() {
		return gasDifference;
	}

	public void setGasDifference(BigDecimal gasDifference) {
		this.gasDifference = gasDifference;
	}

	public String getLastReceivedPayReference() {
		return lastReceivedPayReference;
	}

	public void setLastReceivedPayReference(String lastReceivedPayReference) {
		this.lastReceivedPayReference = lastReceivedPayReference;
	}

	public String getCollectorName() {
		return collectorName;
	}

	public void setCollectorName(String collectorName) {
		this.collectorName = collectorName;
	}

	public String getLastReading() {
		return lastReading;
	}

	public void setLastReading(String lastReading) {
		this.lastReading = lastReading;
	}

	public Date getLastReadingDate() {
		return lastReadingDate;
	}

	public void setLastReadingDate(Date lastReadingDate) {
		this.lastReadingDate = lastReadingDate;
	}

	public String getCurrentReading() {
		return currentReading;
	}

	public void setCurrentReading(String currentReading) {
		this.currentReading = currentReading;
	}

	public Date getCurrentReadingDate() {
		return currentReadingDate;
	}

	public void setCurrentReadingDate(Date currentReadingDate) {
		this.currentReadingDate = currentReadingDate;
	}

	public String getBuildingCode() {
		return buildingCode;
	}

	public void setBuildingCode(String buildingCode) {
		this.buildingCode = buildingCode;
	}

	public String getBuildingName() {
		return buildingName;
	}

	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}

	public String getApartmentCode() {
		return apartmentCode;
	}

	public void setApartmentCode(String apartmentCode) {
		this.apartmentCode = apartmentCode;
	}

	public String getTotalUnit() {
		return totalUnit;
	}

	public void setTotalUnit(String totalUnit) {
		this.totalUnit = totalUnit;
	}

	public String getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public BigDecimal getReceivedAmmount() {
		return receivedAmmount;
	}

	public void setReceivedAmmount(BigDecimal receivedAmmount) {
		this.receivedAmmount = receivedAmmount;
	}

	@Override
	public String toString() {
		return "Bill [docNo=" + docNo + ", docType=" + docType + ", yearCode=" + yearCode + ", serialNo=" + serialNo + ", partyName=" + partyName + ", prevBalance=" + prevBalance
				+ ", lastReceivingDate=" + lastReceivingDate + ", LastReceivingAmount=" + LastReceivingAmount + ", city=" + city + ", billDate=" + billDate + ", service="
				+ service + ", gasDifference=" + gasDifference + ", lastReceivedPayReference=" + lastReceivedPayReference + ", collectorName=" + collectorName + ", lastReading="
				+ lastReading + ", lastReadingDate=" + lastReadingDate + ", currentReading=" + currentReading + ", currentReadingDate=" + currentReadingDate + ", buildingCode="
				+ buildingCode + ", buildingName=" + buildingName + ", apartmentCode=" + apartmentCode + ", totalUnit=" + totalUnit + ", unitPrice=" + unitPrice + ", amount="
				+ amount + ", totalAmount=" + totalAmount + ", contractNo=" + contractNo + ", receivedAmmount=" + receivedAmmount + "]";
	}
	
	
	



}
