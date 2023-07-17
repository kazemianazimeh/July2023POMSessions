package com.qa.opencart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;

public class ProductPageInfoTest extends BaseTest {

	@BeforeClass
	public void productPageInfoSetup() {
		accPage = loginPage.doLogin(prop.getProperty("username").trim(), prop.getProperty("password").trim());
	}
	
	@DataProvider
	public Object[][] getProductTestData(){
		return new Object[][] {
			{"MacBook","MacBook Pro", 4},
			{"MacBook","MacBook Air", 4},
			{"iMac","iMac", 3},
			{"Apple","Apple Cinema 30\"",6},
			{"Samsung","Samsung SyncMaster 941BW",1},
			{"Samsung","Samsung Galaxy Tab 10.1",7}
		};
	}
	
	@Test(dataProvider = "getProductTestData")
	public void productImageCountTest(String searchKey, String productName, int productImageCount) {
		searchPage = accPage.performSearch(searchKey);
		productInfoPage = searchPage.selectProduct(productName);
		int actualImageCount = productInfoPage.getProductImageCount();
		Assert.assertEquals(actualImageCount, productImageCount);
		
	}
	
	@Test
	public void productInfoTest() {
		searchPage = accPage.performSearch("MacBook");
		productInfoPage = searchPage.selectProduct("MacBook Pro");
		Map<String, String> ActualProductInfoMap = productInfoPage.getProductInfo();
		softAssert.assertEquals(ActualProductInfoMap.get("Brand"), "Apple");
		softAssert.assertEquals(ActualProductInfoMap.get("Product Code"), "Product 18");
		softAssert.assertEquals(ActualProductInfoMap.get("product name"), "MacBook Pro");
		softAssert.assertEquals(ActualProductInfoMap.get("product price"), "$2,000.00");

		softAssert.assertAll();

	}
	
	@DataProvider
	public Object[][] getProductSearchDataTest() {
		return new Object[][] {
			{"MacBook", "MacBook Pro"},
		};
	}
	
	@Test (dataProvider = "getProductSearchDataTest")
	public void addToCartTest(String searchKey, String ProductKey) {
		searchPage = accPage.performSearch(searchKey);
		productInfoPage = searchPage.selectProduct(ProductKey);
		productInfoPage.enterQuantity(2);
		String actualSuccessMsg = productInfoPage.addProductToCart();
		softAssert.assertTrue(actualSuccessMsg.indexOf("Success")>=0);
		softAssert.assertTrue(actualSuccessMsg.indexOf("MacBook Pro")>=0);
		softAssert.assertEquals(actualSuccessMsg, "Success: You have added MacBook Pro to your shopping cart!");
		
		softAssert.assertAll();

		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
