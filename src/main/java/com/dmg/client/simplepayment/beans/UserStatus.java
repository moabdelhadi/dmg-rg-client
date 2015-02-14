package com.dmg.client.simplepayment.beans;

public enum UserStatus {

	NEW(0), RESGISTERED(1), ACTIVE(2);

	private int value;

	private UserStatus(int val) {
		value = val;
	}

	public int value() {
		return value;
	}

}
