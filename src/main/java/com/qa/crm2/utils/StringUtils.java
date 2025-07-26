package com.qa.crm2.utils;

public class StringUtils {

	public static String getRandomEmailId() {
		String emailId = "decbatch" + System.currentTimeMillis() + "@opencart.com";
		return emailId;
	}

	

}
