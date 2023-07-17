package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.OpencartConstants;
import com.qa.opencart.util.ElementUtil;

public class AccountsPage {

	private WebDriver driver;
	private ElementUtil eleUtil;
	
	private By logoutLink = By.linkText("Logout");
	private By accHeaders = By.xpath("//div[@id='content']/h2");
	private By search = By.xpath("//input[@class='form-control input-lg']");
	private By searchIcon = By.cssSelector("#search button");
	
	
	public AccountsPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	public String getAccessPageTitle() {
		String title = eleUtil.waitForTitleIsAndFetch(OpencartConstants.DEFAULT_SHORT_TIME_OUT, OpencartConstants.ACCOUNT_PAGE_TITLE_VALUE);
//		String title = driver.getTitle();
		System.out.println("the account page title is"+title);
		return title;
	}
	
	public String getAccountUrl() {
		String URL = eleUtil.waitForURLContainsAndFetch(OpencartConstants.DEFAULT_SHORT_TIME_OUT, OpencartConstants.ACCOUNT_PAGE_URL_FRACTION_VALUE);
//		String URL = driver.getCurrentUrl();
		System.out.println("account page current url is"+URL);
		return URL;
	}
	
	public boolean isLogoutLinkExist() {
		return eleUtil.waitForElementVisible(logoutLink, OpencartConstants.DEFAULT_SHORT_TIME_OUT).isDisplayed();
//		return driver.findElement(logoutLink).isDisplayed();
	}
	
	public boolean isSearchBoxExist() {
		return eleUtil.waitForElementVisible(search, OpencartConstants.DEFAULT_SHORT_TIME_OUT).isDisplayed();
//		return driver.findElement(search).isDisplayed();
	}
	
	public List<String> getAccountHeadersList() {
		
		List<WebElement> headersList = eleUtil.waitForElementsVisible(accHeaders, OpencartConstants.DEFAULT_SHORT_TIME_OUT);
//		List<WebElement> headersList = driver.findElements(accHeaders);
		List<String> accountHeadersListValue = new ArrayList<String>();
		for(WebElement e : headersList) {
			String text = e.getText();
			accountHeadersListValue.add(text);
		}
		return accountHeadersListValue;
		
	}
	
	public SearchPage performSearch(String searchKey) {
		if(isSearchBoxExist()) {
			eleUtil.doSendKeys(search, searchKey);
			eleUtil.doClick(searchIcon);
			return new SearchPage(driver); 
		}
		else {
			System.out.println("search field is not present on the page");
			return null;
		}
		
		
	}
	
	
	
	
}
