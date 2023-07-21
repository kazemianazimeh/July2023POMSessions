package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.OpencartConstants;
import com.qa.opencart.util.ElementUtil;

import io.qameta.allure.Step;

public class LoginPage {
	
	private WebDriver driver;
	private ElementUtil eleUtil;

	
	//1st step in page design => private by locators 
	private By emailID = By.id("input-email");
	private By passID = By.id("input-password");
	private By loginBott = By.xpath("//input[@class='btn btn-primary']");
	private By forgottenPass = By.linkText("Forgotten Password");
	private By registerLink = By.linkText("Register");
	
	
	//2nd step in page design => page constructor => should have its own driver 
	public LoginPage (WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	
	//3rd => page actions or methods => behavior of the page
	@Step("...getting the log in page title ...")
	public String getLoginPageTitle() {
//		String title = driver.getTitle();
		String title = eleUtil.waitForTitleIsAndFetch(OpencartConstants.DEFAULT_SHORT_TIME_OUT, OpencartConstants.LOGIN_PAGE_TITLE_VALUE);
		System.out.println("title of the page is"+title);
		return title;
	}
	
	@Step("...getting the log in page url ...")
	public String getLoginPageUrl() {
//		String url = driver.getCurrentUrl();
		String url = eleUtil.waitForURLContainsAndFetch(OpencartConstants.DEFAULT_SHORT_TIME_OUT, OpencartConstants.LOGIN_PAGE_URL_FRACTION_VALUE);
		System.out.println("page current url is"+url);
		return url;
	}
	
	@Step("...getting the forgot password link ...")
	public boolean isForgotPassLinkExist() {
		return eleUtil.waitForElementVisible(forgottenPass, OpencartConstants.DEFAULT_SHORT_TIME_OUT).isDisplayed();
//		return driver.findElement(forgottenPass).isDisplayed();
	}
	
	//we can use private class variable inside a public method (encapsulation) to protect people from accessing to our sensitive data
	//if they want to have access, they simply can use our public methods 
	
	@Step("login with username :{0} and password: {1}")
	public AccountsPage doLogin(String user, String pass) {
		
		//instead of writing find element, we create object of our Util class 
		eleUtil.waitForElementVisible(emailID, OpencartConstants.DEFAULT_MEDIUM_TIME_OUT).sendKeys(user);
		eleUtil.doSendKeys(passID, pass);
		eleUtil.doClick(loginBott);
//		driver.findElement(emailID).sendKeys(user);
//		driver.findElement(passID).sendKeys(pass);
//		driver.findElement(loginBott).click();
		return new AccountsPage(driver);
	}
	@Step("...navigating to registration page ...")
	public RegisterPage navigateToRegister() {
		eleUtil.doClick(registerLink);
		return new RegisterPage(driver);
	}
}
