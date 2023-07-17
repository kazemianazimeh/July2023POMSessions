package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.OpencartConstants;
import com.qa.opencart.util.ExcelUtil;

public class RegisterPageTest extends BaseTest {

	@BeforeClass
	public void registerPageSetup() {
		registerPage = loginPage.navigateToRegister();
	}
	
	@DataProvider
	public Object[][] getRegTestData() {
		Object regData [][] = ExcelUtil.getTestData(OpencartConstants.REGISTER_SHEET_NAME);
		return regData;
	}
	
	@Test (dataProvider = "getRegTestData")
	public void userRegTest(String firstName, String lastName, String email, String telephone, String password, String subscribe) {
		Assert.assertTrue(registerPage.registerUser(firstName, firstName, email, email, password, password));
	}
		

	
	
}
