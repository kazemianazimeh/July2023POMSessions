package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.safari.SafariDriver;

public class DriverFactory {
	
	public WebDriver driver;
	public Properties prop; 
	public OptionsManager OptionsManager;
	public static String highlight;
	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();
	
	/**
	 * this method is to initialize driver on the basis of giving browser name 
	 * @param browserName
	 * @return this method returns the driver 
	 */
	public WebDriver initDriver(Properties prop) {
		
		OptionsManager = new OptionsManager(prop);
		
		highlight = prop.getProperty("highlight").trim();
		
		String browserName = prop.getProperty("browser").trim().toLowerCase();
		System.out.println("browser name is"+browserName);	
		
		if(browserName.equalsIgnoreCase("chrome")) {
//			driver = new ChromeDriver(OptionsManager.getChromeOptions());
			tlDriver.set(new ChromeDriver(OptionsManager.getChromeOptions()));
			
		}
		else if(browserName.equalsIgnoreCase("firefox")) {
//			driver = new FirefoxDriver(OptionsManager.getFirefoxOptions());
			tlDriver.set(new FirefoxDriver(OptionsManager.getFirefoxOptions()));

		}
		else if(browserName.equalsIgnoreCase("safari")) {
//			driver = new SafariDriver();
			tlDriver.set(new SafariDriver());
		}
		else if(browserName.equalsIgnoreCase("edge")) {
//			driver = new EdgeDriver(OptionsManager.getEdgeOptions());
			tlDriver.set(new EdgeDriver(OptionsManager.getEdgeOptions()));
		}
		else {
			System.out.println("please pass the right browser"+browserName);
		}
		
		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		getDriver().get(prop.getProperty("url"));
		
		return getDriver();
		
	}
	
		public synchronized static WebDriver getDriver() {
			return tlDriver.get();
		}
	
	/**this method is reading the properties from .properties files (Keys and Values)
	 * 	
	 * @return
	 */
	public Properties initiProp() {
			prop =  new Properties();//creating object of properties class
			try {
				FileInputStream ip = new FileInputStream("./src/test/resources/configure/configue.properties");//calling the FileInputStream class to get access to the properties files 
				prop.load(ip);//loading all the properties from files 
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return prop;
			
		}

	public static String  getScreenshot() {
		File scrFile =((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.FILE);
		String path = System.getProperty("user.dir")+"/screenshot/"+System.currentTimeMillis()+".png";
		File destination = new File(path);
		
		try {
			FileHandler.copy(scrFile, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}

}
