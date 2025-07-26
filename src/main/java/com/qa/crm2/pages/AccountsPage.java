package com.qa.crm2.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.crm2.constants.AppConstants;
import com.qa.crm2.utils.ElementUtil;


public class AccountsPage {
	
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	private By logoutLink = By.linkText("Logout");
	private By myAccountLink = By.linkText("My Account");
	private By headers = By.cssSelector("div#content h2");
	private By search = By.name("search");
	private By searchIcon = By.cssSelector("div#search button");
	
	
	public AccountsPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	public String getAccPageTitle() {
		String title = eleUtil.waitForTitleIs(AppConstants.ACCOUNTS_PAGE_TITLE, 5);
		// String title = driver.getTitle();
		System.out.println("Account page title : " + title);
		return title;
	}

	public String getAccPageURL() {
		// String url = driver.getCurrentUrl();
		String url = eleUtil.waitForURLContains(AppConstants.ACC_PAGE_URL_FRACTION, 5);
		System.out.println("Account page url : " + url);
		return url;

	}
	
	public boolean isLogoutLinkExist() {
		boolean logout = eleUtil.waitForElementVisible(logoutLink,10).isDisplayed();
		return logout;
	}
	
	public boolean myAccountLinkExist() {
		boolean acc = eleUtil.waitForElementVisible(myAccountLink,10).isDisplayed();
		return acc;
	}
	
	public List<String> getAccountsPageHeadersList() {
		List<WebElement> headersEleList = eleUtil.getElements(headers);
		List<String> headersList = new ArrayList<String>();
		for(WebElement e :headersEleList) {
			String header = e.getText();
			headersList.add(header);
		}
		return headersList;
		
		
	}
	public SearchResultsPage doSearch(String SearchKey) {
		System.out.println("Searching for : " +SearchKey);
		eleUtil.doSendKeys(search, SearchKey);
		eleUtil.doClick(searchIcon);
		return new SearchResultsPage(driver);
		
		
	}

}
