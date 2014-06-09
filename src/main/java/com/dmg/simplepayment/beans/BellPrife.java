package com.dmg.simplepayment.beans;

public class BellPrife {

	private String date;
	private String amount;
	private String id;
	
	public BellPrife(String id, String date, String amount) {
		this.amount=amount;
		this.date=date;
		this.id=id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}



	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
