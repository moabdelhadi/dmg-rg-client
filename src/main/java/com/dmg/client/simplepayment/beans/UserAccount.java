package com.dmg.client.simplepayment.beans;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.dmg.core.bean.AbstractPojo;

@Entity
@Table(name = "USERACCOUNTS", uniqueConstraints = { @UniqueConstraint(columnNames = { "CONTRACT_NO" }) })
public class UserAccount extends AbstractPojo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 711201638919389292L;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "PASSWORD")
	protected String password;

	@Column(name = "NAME")
	private String name;

	@Column(name = "CITY")
	private String city;

	@Column(name = "BUILDING_NUMBER")
	private String buildingNumber;

	@Column(name = "APPARTMENT_NUMBER")
	private String appartmentNumber;

	@Column(name = "CONTRACT_NO")
	private String contractNo;

	@Column(name = "PHONE")
	private String phone;

	@Column(name = "MOBILE")
	private String mobile;

	@Column(name = "POBOX")
	private String pobox;

	@Column(name = "POBOX_CITY")
	private String poboxCity;

	@Column(name = "STATUS")
	private int status;

	@Column(name = "ACTIVATIONSTRING")
	private String activationString;

	@Column(name = "LASTUPDATE")
	private Date lastUpdate;

	public UserAccount() {

	}

	public UserAccount(String accountNo, String password, String city) {
		this.contractNo = accountNo;
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

	public String getActivationString() {
		return activationString;
	}

	public void setActivationString(String activationString) {
		this.activationString = activationString;
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

	@Override
	public String toString() {
		return "UserAccount [email=" + email + ", password=" + password + ", name=" + name + ", city=" + city
				+ ", buildingNumber=" + buildingNumber + ", appartmentNumber=" + appartmentNumber + ", accountId="
				+ contractNo + ", phone=" + phone + ", mobile=" + mobile + ", pobox=" + pobox + ", poboxCity="
				+ poboxCity + ", status=" + status + ", activationString=" + activationString + ", lastUpdate="
				+ lastUpdate + "]";
	}

}
