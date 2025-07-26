package com.qa.crm2.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.crm2.constants.AppConstants;
import com.qa.crm2.utils.ElementUtil;
import com.qa.crm2.pages.RegisterationPage;
import com.qa.crm2.utils.TimeUtil;
import com.qa.crm2.logger.Log;

import io.qameta.allure.Step;


public class LoginPage {

	private WebDriver driver;
	private ElementUtil eleUtil;

	private By emailId = By.id("input-email");
	private By password = By.id("input-password");
	private By loginButton = By.xpath("//input[@value='Login']");
	private By forgotPWdLink = By.linkText("Forgotten Password");
	private By registerLink = By.linkText("Register");
	private By testWebhook = By.linkText("RRegister");

	public LoginPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	@Step("Getting Login Page Title...")
	public String getLoginPageTitle() {
		String title = eleUtil.waitForTitleIs(AppConstants.LOGIN_PAGE_TITLE, TimeUtil.DEFAULT_SHORT_TIME);
		// String title = driver.getTitle();
		Log.info("Login Page title :" +title);
		System.out.println("login page title : " + title);
		return title;
	}

	@Step("Getting Login Page Url...")
	public String getLoginPageURL() {
		// String url = driver.getCurrentUrl();
		String url = eleUtil.waitForURLContains(AppConstants.LOGIN_PAGE_URL_FRACTION, 5);
		System.out.println("login page url : " + url);
		return url;

	}

	@Step("Getting the state of forgot pwd link...")
	public boolean isForgotPwdLinkExist() {
		// return driver.findElement(forgotPWdLink).isDisplayed();
		return eleUtil.isElementDisplayed(forgotPWdLink);
	}
	
	@Step("Login with username: {0} and Password: {1}")
    public AccountsPage doLogin(String username, String pwd) {
		System.out.println("user creds: " + username + " : " + pwd);
//		driver.findElement(emailId).sendKeys(username);
//		driver.findElement(password).sendKeys(pwd);
//		driver.findElement(loginButton).click();
		eleUtil.waitForElementVisible(emailId, 10).sendKeys(username);
		eleUtil.doSendKeys(password, pwd);
		eleUtil.doClick(loginButton);
		// return driver.getTitle();
		//return eleUtil.waitForTitleIs("My Account", 5);
		
		return new AccountsPage(driver);
	}
	
	@Step("Navigating to registration page...")
	public RegisterationPage navigateToRegisterPage() {
		eleUtil.waitForElementVisible(registerLink, TimeUtil.DEFAULT_LONG_TIME).click();
		return new RegisterationPage(driver);
	}

}
