package com.qa.crm2.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.crm2.base.BaseTest;
import com.qa.crm2.constants.AppConstants;
import com.qa.crm2.errors.AppError;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

//@Listeners(ExtentReportListener.class)

@Epic("Epic : 100 Design open cart login page")
@Story("User story 101 : Design login page login feature")
@Feature("Feature 102: Adding Login feature")
public class LoginPageTest extends BaseTest {

	@Description("Checking login page title.....")
	@Severity(SeverityLevel.MINOR)
	@Test
	public void loginPageTitleTest() {
		String accTitle = loginpage.getLoginPageTitle();
		//Assert.assertEquals(accTitle, "Account Login");
		Assert.assertEquals(accTitle, AppConstants.LOGIN_PAGE_TITLE, AppError.TITLE_NOT_FOUND);

	}

	@Description("Checking login page URL.....")
	@Severity(SeverityLevel.MINOR)
	@Test
	public void loginPageUrlTest() {
		String accUrl = loginpage.getLoginPageURL();
		Assert.assertTrue(accUrl.contains(AppConstants.LOGIN_PAGE_URL_FRACTION));

	}
	
	@Description("Checking login page ForgotPAssword link.....")
	@Severity(SeverityLevel.CRITICAL)
    @Test
	public void forgotPwdLinkExistTest() {
		Assert.assertTrue(loginpage.isForgotPwdLinkExist());
	}

	@Description("Checking login test.....")
	@Severity(SeverityLevel.BLOCKER)
	@Test
	public void loginTest() {
		accPage = loginpage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		Assert.assertEquals(accPage.getAccPageTitle(), AppConstants.ACCOUNTS_PAGE_TITLE);
	}

}
