package com.qa.crm2.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.qa.crm2.base.BaseTest;
import com.qa.crm2.constants.AppConstants;

public class AddToCartPageTest extends BaseTest {

	@BeforeClass
	public void infoAddToCartSetup() {
		accPage = loginpage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}

	@Test
	public void addToCartPageTitleTest() {
		searchResultsPage = accPage.doSearch("macbook");
		productInfoPage = searchResultsPage.selectProduct("MacBook Pro");
		productInfoPage.increaseItem();
		productInfoPage.addToCart();
		addToCartPage = productInfoPage.clickOnShoppingCart();
		String acccTitle = addToCartPage.getAddToCartPageTitle();
		System.out.println("Add to cart page title :" + acccTitle);
		Assert.assertEquals(acccTitle, AppConstants.ADDTOCART_PAGE_TITLE);
	}

}
