package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.OpencartConstants;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;



@Epic("EPIC - 100: design log in for open card app")
@Story("US-Login: 101: design log in page features for open cart")



public class LoginPageTest extends BaseTest {
	
	
	@Severity(SeverityLevel.TRIVIAL)
	@Description("...getting the title of the page... creator:Azima")
	@Test(priority = 1)
	public void loginPageTitleTest() {
		String actualTitle = loginPage.getLoginPageTitle();
		Assert.assertEquals(actualTitle, OpencartConstants.LOGIN_PAGE_TITLE_VALUE);
	}
	
	@Severity(SeverityLevel.NORMAL)
	@Description("...getting the url of the page... creator:Azima")
	@Test(priority = 2)
	public void loginPageUrlTest() {
		String actualUrl = loginPage.getLoginPageUrl();
		Assert.assertTrue(actualUrl.contains(OpencartConstants.LOGIN_PAGE_URL_FRACTION_VALUE));
	}
	
	@Severity(SeverityLevel.CRITICAL)
	@Description("...checking forgot password link is availabe ... creator:Azima")
	@Test(priority = 3)
	public void forgottenButExistTest() {
		Assert.assertTrue(loginPage.isForgotPassLinkExist());
	}
	
	
	@Severity(SeverityLevel.CRITICAL)
	@Description("...checking user is able to log to app with correct username and password ... creator:Azima")
	@Test(priority = 4)
	public void loginTest() {
		accPage = loginPage.doLogin(prop.getProperty("username").trim(), prop.getProperty("password").trim());
		Assert.assertTrue(accPage.isLogoutLinkExist());
	}

	
}
