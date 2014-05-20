package com.dmg.simplepayment.beans;

public enum UserStatus {
	
	NEW(0),ACTIVE(1), DISABLE(2),NOT_REGISTERED(4);
	
	private int value; 
	
	private UserStatus(int val) {
		this.value=val;
	}
	
	public int value(){
		return value;
	}
	
	

}
