package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.OpencartConstants;
import com.qa.opencart.util.ElementUtil;

public class SearchPage {
	
	private WebDriver driver;
	private ElementUtil eleUtil;
	private By searchProductResutls = By.cssSelector("div#content div.product-layout");

	public SearchPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	public int getSearchProductCount() {
		int productCount =  eleUtil.waitForElementsVisible(searchProductResutls, OpencartConstants.DEFAULT_MEDIUM_TIME_OUT).size();
		System.out.println("product count is"+productCount);
		return productCount;
	}
	
	public PorductInfoPage selectProduct(String productName) {
		By productLoactor = By.linkText(productName);
		eleUtil.waitForElementVisible(productLoactor, OpencartConstants.DEFAULT_MEDIUM_TIME_OUT).click();
		return new PorductInfoPage(driver);
		
	}
	
	

}
