package com.dmg.simplepayment.beans;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.dmg.core.bean.AbstractPojo;

@Entity
@Table(name = "BILLS2")
public class Bill extends AbstractPojo{

	@Column(name = "LAST_READING")
	private String lastReading;
	
	@Column(name = "LAST_READING_DATE")
	private Date lastReadingDate;
	
	@Column(name = "CURR_READING")
	private String currentReading;
	
	@Column(name = "CURR_READING_DATE")
	private Date currentReadingDate;

	@Column(name = "BUILDING_CODE")
	private String buildingCode ;
	
	@Column(name = "BUILDING_NAME")
	private String buildingName;
	
	@Column(name = "APARTMENT_CODE")
	private String apartmentCode;
	
	@Column(name = "TOTAL_UNIT")
	private String totalUnit;
	
	@Column(name = "UNIT_PRICE")
	private String unitPrice;
	
	@Column(name = "AMT")
	private String amount;
	
	@Column(name = "TOTALAMT")
	private String totalAmount;
	
	@Column(name = "CONTRACT_NO")
	private String contractNo;
	
	public Bill() {

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

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	@Override
	public String toString() {
		return "Bill [lastReading=" + lastReading + ", lastReadingDate=" + lastReadingDate + ", currentReading="
				+ currentReading + ", currentReadingDate=" + currentReadingDate + ", buildingCode=" + buildingCode
				+ ", buildingName=" + buildingName + ", apartmentCode=" + apartmentCode + ", totalUnit=" + totalUnit
				+ ", unitPrice=" + unitPrice + ", amount=" + amount + ", totalAmount=" + totalAmount + ", contractNo="
				+ contractNo + "]";
	}
	
	
	
}
