package com.qa.crm2.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.crm2.utils.ElementUtil;

public class SearchResultsPage {

	private WebDriver driver;
	private ElementUtil eleUtil;

	private By searchProducts = By.cssSelector("div.product-thumb");

	public SearchResultsPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	public int getSearchProductCount() {
		return eleUtil.waitForElementsVisible(searchProducts, 10).size();
	}

	public ProductInfoPage selectProduct(String productName) {
		System.out.println("searching for product: " + productName);
		eleUtil.waitForElementVisible(By.linkText(productName), 10).click();
		 return new ProductInfoPage(driver);
	}

}
