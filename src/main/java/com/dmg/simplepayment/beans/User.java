package com.dmg.simplepayment.beans;

import com.dmg.util.dao.mapping.Column;
import com.dmg.util.dao.mapping.Entity;
import com.dmg.util.dao.mapping.PrimaryKey;

@Entity(tableName = "users")
public class User {

	private int id;
	private String email;
	private String password;
	private String city;
	private String buildingNo;
	private String appartmentNo;
	private String accountNo;
	private String name;
	private String phone;
	private String address;
	private int status;
	private String activationString;

	public User(String email, String password) {
		this.email = email;
		this.password = password;
	}

	public User() {
	}

	@Column(name = "id")
	@PrimaryKey
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "password")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "city")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "buildingNo")
	public String getBuildingNo() {
		return buildingNo;
	}

	public void setBuildingNo(String buildingNo) {
		this.buildingNo = buildingNo;
	}

	@Column(name = "appartmentNo")
	public String getAppartmentNo() {
		return appartmentNo;
	}

	public void setAppartmentNo(String appartmentNo) {
		this.appartmentNo = appartmentNo;
	}

	@Column(name = "accountNo")
	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "phone")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "address")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "status")
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Column(name = "activationString")
	public String getActivationString() {
		return activationString;
	}

	public void setActivationString(String activationString) {
		this.activationString = activationString;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", password=" + password
				+ ", city=" + city + ", buildingNo=" + buildingNo
				+ ", appartmentNo=" + appartmentNo + ", accountNo=" + accountNo
				+ ", name=" + name + ", phone=" + phone + ", address="
				+ address + ", status=" + status + ", activationString="
				+ activationString + "]";
	}

}
