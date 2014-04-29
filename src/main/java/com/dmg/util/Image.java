package com.dmg.util;

public class Image {

	private String url;
	private int id;

	public Image(String url) {
		this.url= url;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
