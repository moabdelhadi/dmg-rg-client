package com.dmg.simplepayment.beans;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.dmg.core.bean.AbstractPojo;

@Entity
@Table(name = "USERACCOUNTS", uniqueConstraints = { @UniqueConstraint(columnNames = { "ACCOUNT_ID" }) })
public class UserAccount extends AbstractPojo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 711201638919389292L;


	@Column(name = "EMAIL")
	private String email;

	@Column(name = "PASSWORD")
	protected String password;

	@Column(name = "FIRST_NAME")
	private String firstName;

	@Column(name = "LAST_NAME")
	private String lastName;

	@Column(name = "CITY")
	private String city;
	
	@Column(name = "BUILDING_NUMBER")
	private String buildingNumber;
	
	@Column(name = "APPARTMENT_NUMBER")
	private String appartmentNumber;
	
	@Column(name = "ACCOUNT_ID")
	private String accountId;
	
	@Column(name = "PHONE")
	private String phone;
	
	@Column(name = "ADDRESS")
	private String address;
	
	@Column(name = "STATUS")
	private int status;
	
	@Column(name = "ACTIVATIONSTRING")
	private String activationString;
	
	@Column(name = "LASTUPDATE")
	private Date lastUpdate;


	public UserAccount(){
		
	}

	public UserAccount(String email, String password) {
		this.email = email;
		this.password = password;
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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	@Override
	public String toString() {
		return "UserAccount [email=" + email + ", password=" + password + ", firstName=" + firstName + ", lastName=" + lastName + ", city=" + city + ", buildingNumber="
				+ buildingNumber + ", appartmentNumber=" + appartmentNumber + ", accountId=" + accountId + ", phone=" + phone + ", address=" + address + ", status=" + status
				+ ", activationString=" + activationString + ", lastUpdate=" + lastUpdate + "]";
	}
	
}
