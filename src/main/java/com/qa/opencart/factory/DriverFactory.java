package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.aspectj.util.FileUtil;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import com.qa.opencart.exception.FrameworkException;

public class DriverFactory {

	public WebDriver driver;
	public Properties prop;
	public OptionsManager optionsManager;

	public static String highlight;

	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();
	
    //private final Logger logger = Logger.getLogger(DriverFactory.class);

    

	/**
	 * this method is initializing the driver on the basis of given browser name
	 * 
	 * @param browserName
	 * @return this returns the driver
	 */
	public WebDriver initDriver(Properties prop) {


		optionsManager = new OptionsManager(prop);

		highlight = prop.getProperty("highlight").trim();
		String browserName = prop.getProperty("browser").toLowerCase().trim();
		// String browserName = system.getProperty("browser");

		System.out.println("browser name is : " + browserName);
		//logger.info("browser name is : \" + browserName");
		
		
		if(browserName.equalsIgnoreCase("chrome")) {
			tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
		}
		
		else if (browserName.equalsIgnoreCase("firefox")) {
			tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
		}
		
		else if (browserName.equalsIgnoreCase("safari")) {
			tlDriver.set(new SafariDriver());
		}
		
		else if (browserName.equalsIgnoreCase("edge")) {
			tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
		}
		
		else {
			System.out.println("please pass the right browser ..." + browserName);
			throw new FrameworkException("NO BROWSER FOUND EXCEPTION ....");
		}
		
		
		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		getDriver().get(prop.getProperty("url"));
		
		
		return getDriver();
		
		
}
	
	public synchronized static WebDriver getDriver() {
		return tlDriver.get();
	}
	
	public Properties initProp() {
		
		// mvn clean install -Denv="qa"
				// mvn clean install
				prop = new Properties();
				FileInputStream ip = null;
				String envName = System.getProperty("env");
				System.out.println("Running test cases on Env: " + envName);

				try {
					if (envName == null) {
						System.out.println("no env is passed....Running tests on QA env...");
						ip = new FileInputStream(".\\src\\test\\resources\\configure\\qa.config.properties");
					} else {
						switch (envName.toLowerCase().trim()) {
						case "qa":
							ip = new FileInputStream(".\\src\\test\\resources\\configure\\qa.config.properties");
							break;
						case "stage":
							ip = new FileInputStream(".\\src\\test\\resources\\configure\\stage.config.properties");
							break;
						case "dev":
							ip = new FileInputStream(".\\src\\test\\resources\\configure\\dev.config.properties");
							break;
						case "prod":
							ip = new FileInputStream(".\\src\\test\\resources\\configure\\config.properties");
							break;

						default:
							System.out.println("....Wrong env is passed....No need to run the test cases....");
							throw new FrameworkException("WRONG ENV IS PASSED ....");
						 
						}

					}
				} catch (FileNotFoundException e) {

				}

				try {
					prop.load(ip);
				} catch (IOException e) {
					e.printStackTrace();
				}

				return prop;
			}

	
	
	public static String getScreenshot() {
		File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
		String path = System.getProperty("user.dir") + "/screenshot/" + System.currentTimeMillis() + ".png";
		File destination = new File(path);
		try {
			FileUtil.copyFile(srcFile, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}
}
















