package com.dmg.client.servlet;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class CaptchaRespone implements Serializable{
	
	private Boolean success=false;
	private Date challenge_ts;  // timestamp of the challenge load (ISO format yyyy-MM-dd'T'HH:mm:ssZZ)
	private String hostname;         // the hostname of the site where the reCAPTCHA was solved
	
	@SerializedName("error-codes")
	private List<String> errorCodes;
	  
	  
	  
	  
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	public Date getChallenge_ts() {
		return challenge_ts;
	}
	public void setChallenge_ts(Date challenge_ts) {
		this.challenge_ts = challenge_ts;
	}
	public String getHostname() {
		return hostname;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	public List<String> getErrorCodes() {
		return errorCodes;
	}
	public void setErrorCodes(List<String> errorCodes) {
		this.errorCodes = errorCodes;
	}

	  
	  

}
