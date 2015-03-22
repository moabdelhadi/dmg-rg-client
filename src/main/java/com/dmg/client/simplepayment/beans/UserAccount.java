package com.dmg.client.simplepayment.beans;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.dmg.core.bean.AbstractPojo;

@MappedSuperclass
public class UserAccount extends AbstractPojo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 711201638919389292L;

	@Column(name = "EMAIL")
	protected String email;

	@Column(name = "PASSWORD")
	protected String password;

	@Column(name = "NAME")
	protected String name;

	@Column(name = "CITY")
	protected String city;

	@Column(name = "BUILDING_NUMBER")
	protected String buildingNumber;

	@Column(name = "APPARTMENT_NUMBER")
	protected String appartmentNumber;

	@Column(name = "CONTRACT_NO")
	protected String contractNo;

	@Column(name = "PHONE")
	protected String phone;

	@Column(name = "MOBILE")
	protected String mobile;

	@Column(name = "POBOX")
	protected String pobox;

	@Column(name = "POBOX_CITY")
	protected String poboxCity;

	@Column(name = "STATUS")
	protected int status;

	@Column(name = "LASTUPDATE")
	protected Date lastUpdate;

	@Column(name = "PASS_RESET_KEY")
	protected String passResetKey;

	@Column(name = "ACTIVATION_KEY")
	protected String activationKey;
	
	@Column(name = "ENABLE")
	protected Boolean enable;

	@Column(name="BALANCE")
	protected BigDecimal balance;
	
	@Column(name = "SYNC_STATUS")
	protected int syncStatus;
	
	
	public UserAccount() {

	}

	public UserAccount(String accountNo, String password, String city) {
		contractNo = accountNo;
		this.password = password;
		this.city = city;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getBuildingNumber() {
		return buildingNumber;
	}

	public void setBuildingNumber(String buildingNumber) {
		this.buildingNumber = buildingNumber;
	}

	public String getAppartmentNumber() {
		return appartmentNumber;
	}

	public void setAppartmentNumber(String appartmentNumber) {
		this.appartmentNumber = appartmentNumber;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPobox() {
		return pobox;
	}

	public void setPobox(String pobox) {
		this.pobox = pobox;
	}

	public String getPoboxCity() {
		return poboxCity;
	}

	public void setPoboxCity(String poboxCity) {
		this.poboxCity = poboxCity;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getPassResetKey() {
		return passResetKey;
	}

	public void setPassResetKey(String passResetKey) {
		this.passResetKey = passResetKey;
	}

	public String getActivationKey() {
		return activationKey;
	}

	public void setActivationKey(String activationKey) {
		this.activationKey = activationKey;
	}
	
	
	public Boolean isEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}
	
	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public Boolean getEnable() {
		return enable;
	}
	
	public int getSyncStatus() {
		return syncStatus;
	}

	public void setSyncStatus(int syncStatus) {
		this.syncStatus = syncStatus;
	}


	@Override
	public String toString() {
		return "UserAccount [email=" + email + ", password=" + password + ", name=" + name + ", city=" + city + ", buildingNumber=" + buildingNumber + ", appartmentNumber=" + appartmentNumber
				+ ", accountId=" + contractNo + ", phone=" + phone + ", mobile=" + mobile + ", pobox=" + pobox + ", poboxCity=" + poboxCity + ", status=" + status 
				 + ", lastUpdate=" + lastUpdate + "]";
	}

}
