package com.qa.crm2.base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

import com.beust.jcommander.Parameter;
import com.qa.crm2.factory.DriverFactory;
import com.qa.crm2.pages.AccountsPage;
import com.qa.crm2.pages.AddToCartPage;
import com.qa.crm2.pages.LoginPage;
import com.qa.crm2.pages.ProductInfoPage;
import com.qa.crm2.pages.RegisterationPage;
import com.qa.crm2.pages.SearchResultsPage;

import io.qameta.allure.Step;

public class BaseTest {
	
	WebDriver driver;
	protected Properties prop;
	DriverFactory df;
	protected LoginPage loginpage;
	protected AccountsPage accPage;
	protected SearchResultsPage searchResultsPage;
	protected ProductInfoPage productInfoPage;
	protected SoftAssert softAssert;
	protected AddToCartPage addToCartPage;
	protected RegisterationPage registerationPage;
	
	@Step("Launching {0} browser & init the properties")
	@Parameters({"browser"})
	@BeforeTest
	public void setup(String browserName){
		df = new DriverFactory();
	  prop = df.initProp();
	  
	  if(browserName!=null) {
		  prop.setProperty("browser", browserName);
	  }
    driver = df.initDriver(prop);
    loginpage = new LoginPage(driver);
    softAssert = new SoftAssert();
	}
	
	@Step("Closing the browser")
	@AfterTest
	public void tearDown() {
		driver.quit();
	}

}
