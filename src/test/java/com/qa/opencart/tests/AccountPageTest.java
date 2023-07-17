package com.qa.opencart.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.OpencartConstants;

public class AccountPageTest extends BaseTest {

	
	@BeforeClass
	public void accountPageSetup() {
		accPage = loginPage.doLogin(prop.getProperty("username").trim(), prop.getProperty("password").trim());

	}

	@Test
	public void accountPageTitleTest() {
		String actTitle = accPage.getAccessPageTitle();
		Assert.assertEquals(actTitle, OpencartConstants.ACCOUNT_PAGE_TITLE_VALUE);
	}

	@Test
	public void accountPageUrlTest() {
		String actUrl = accPage.getAccountUrl();
		Assert.assertTrue(actUrl.contains(OpencartConstants.ACCOUNT_PAGE_URL_FRACTION_VALUE));
	}

	@Test
	public void isLogoutLinkExistTest() {
		Assert.assertTrue(accPage.isLogoutLinkExist());
	}

	@Test
	public void accPageHeadersCountTest() {
		List<String> actualAccPageHeadersList = accPage.getAccountHeadersList();
		System.out.println(actualAccPageHeadersList);
		Assert.assertEquals(actualAccPageHeadersList.size(), OpencartConstants.ACCOUNT_PAGE_HEADERS_COUNT);
	}

	@Test
	public void accPageHeadersValueTest() {
		List<String> actualAccPageHeadersList = accPage.getAccountHeadersList();
		System.out.println(actualAccPageHeadersList);
		Assert.assertEquals(actualAccPageHeadersList, OpencartConstants.EXPECTED_ACCOUNT_PAGE_HEADERS_LIST);
	}

	@DataProvider
	public Object[][] getProductData() {
		return new Object [][] {
			{"MacBook"},
			{"iMac"},
			{"Apple"},
			{"Samsung"}
			
		};
		
	}
	
	@Test (dataProvider = "getProductData")
	public void searchProductCountTest(String searchKey) {
		searchPage = accPage.performSearch(searchKey);
		Assert.assertTrue(searchPage.getSearchProductCount()>0);
	}
	
	@DataProvider
	public Object[][] getProductTestData(){
		return new Object[][] {
			{"MacBook","MacBook Pro"},
			{"MacBook","MacBook Air"},
			{"iMac","iMac"},
			{"Apple","Apple Cinema 30\""},
			{"Samsung","Samsung SyncMaster 941BW"},
			{"Samsung","Samsung Galaxy Tab 10.1"}
		};
		
	}
	
	
	@Test (dataProvider = "getProductTestData")
	public void searchProductTest(String searchKey, String productName) {
		searchPage = accPage.performSearch(searchKey);
		if(searchPage.getSearchProductCount()>0) {
			productInfoPage = searchPage.selectProduct(productName);
			String actualProductHeader = productInfoPage.getProductHeaderValue();
			Assert.assertEquals(actualProductHeader, productName);
		}

	}

}
