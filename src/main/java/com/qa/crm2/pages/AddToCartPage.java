package com.qa.crm2.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.crm2.constants.AppConstants;
import com.qa.crm2.utils.ElementUtil;

public class AddToCartPage {
	
	// Page class/Page Library/Page Object
	        private WebDriver driver;
			private ElementUtil eleUtil;

	// 1. Private By Locators

			private By productHeader = By.tagName("h1");
		
	// 2. Public Page Class Const...
			public AddToCartPage(WebDriver driver) {
				this.driver=driver;
				eleUtil = new ElementUtil(driver);
			}
			
			public String getAddToCartPageTitle() {
				String title = eleUtil.waitForTitleIs(AppConstants.ADDTOCART_PAGE_TITLE, AppConstants.SHORT_DEFAUTT_WAIT);
		        System.out.println("Add To Cart Page title :" + title);
				return title;
			}
		



}
