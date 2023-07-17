package com.qa.opencart.pages;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.OpencartConstants;
import com.qa.opencart.util.ElementUtil;

public class PorductInfoPage {

	private WebDriver driver;
	private ElementUtil eleUtil;
	
	
	private By productHeader = By.tagName("h1");
	private By productImage = By.xpath("//ul[@class='thumbnails']//img");
	private By productMetaInfo = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[position()=1]/li");
	private By productPriceInfo = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[position()=2]/li");
	private By quantityFeildText = By.id("input-quantity");
	private By addToCartBtn = By.id("button-cart");
	private By cartSuccessMsg = By.xpath("//div[@class='alert alert-success alert-dismissible']");

	private Map<String, String> productInfoMap;

	
	public PorductInfoPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	public String getProductHeaderValue() {
		String productHeaderValue = eleUtil.doElementGetText(productHeader);
		System.out.println("product header is"+ productHeaderValue);
		return productHeaderValue;
	}
	
	public int getProductImageCount() {
		int imageCount = eleUtil.waitForElementsVisible(productImage, OpencartConstants.DEFAULT_MEDIUM_TIME_OUT).size();
		System.out.println("product images count is "+ imageCount);
		return imageCount;
	}
	
	public void enterQuantity(int qty) {
		System.out.println("the quantity is" + qty);
		eleUtil.doSendKeys(quantityFeildText, String.valueOf(qty));
	}
	
	public String addProductToCart() {
		eleUtil.doClick(addToCartBtn);
		String successMag = eleUtil.waitForElementVisible(cartSuccessMsg, OpencartConstants.DEFAULT_MEDIUM_TIME_OUT).getText();
		StringBuilder sb = new StringBuilder(successMag);
		String msg = sb.substring(0, successMag.length()-1).replace("\n","").toString();
		System.out.println(msg);
		return msg;
	}
	
	
	
	
	public Map<String, String> getProductInfo() {
//		productInfoMap = new HashMap<String, String>();//without order 
//		productInfoMap = new LinkedHashMap<String, String>();//with the order that the method is written 
		productInfoMap = new TreeMap<String, String>();//sorting the keys from capital letters, to small letter, to numeric 


		//header
		productInfoMap.put("product name", getProductHeaderValue());
		getProductMetaData();
		getProductPrice();
		System.out.println(productInfoMap);
		return productInfoMap; 
		
	}
		
	private Map<String, String> getProductMetaData() {
		List<WebElement> MetaList = eleUtil.getElements(productMetaInfo);
		for(WebElement e : MetaList) {
		String metaDataText = e.getText();
		String metaInfo[]=metaDataText.split(":");
		String key = metaInfo [0].trim();
		String value = metaInfo [1].trim();
		productInfoMap.put(key, value);
		}
		return productInfoMap;
	}
		
	private Map<String, String> getProductPrice() {
		List<WebElement> prdocutPriceList = eleUtil.getElements(productPriceInfo);
		String price = prdocutPriceList.get(0).getText();
		String exTax = prdocutPriceList.get(1).getText();
		String exTaxValue = exTax.split(":")[1].trim();
		productInfoMap.put("product price", price);
		productInfoMap.put("product ex Tax price", exTaxValue);
		
		return productInfoMap;
	}

	
	
	
	
	
	
	
	
	
		
}
	
		
	
	
	
	
	


