package com.aziphael.webapp.bean;

import org.apache.commons.lang3.RandomStringUtils;

public class TestBean {

	private String string = RandomStringUtils.randomAlphabetic(4);

	public String getString() {
		return string;
	}

	public void setString(String string) {
		this.string = string;
	}
}
