package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.qa.opencart.exceptions.FrameworkExceptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory {
	WebDriver driver;
	public Properties prop;
	OptionsManager optionsManager;
	public static ThreadLocal<WebDriver> threadLocal = new ThreadLocal<WebDriver>();
	
	/**
	 * This method is used to initialize webdriver based on given browser name
	 * @param browserName
	 * @return webdriver
	 */
	public WebDriver init_driver(Properties prop) {
		String browserName=prop.getProperty("browser").trim();
		optionsManager = new OptionsManager(prop);
		if(browserName.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
//			driver = new ChromeDriver(optionsManager.getChromeBrowserOptions());
			threadLocal.set(new ChromeDriver(optionsManager.getChromeBrowserOptions()));
		}
		else if(browserName.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
//			driver = new FirefoxDriver();	
			threadLocal.set(new FirefoxDriver());
		}
		else if(browserName.equalsIgnoreCase("edge")) {
			WebDriverManager.edgedriver().setup();
//			driver = new EdgeDriver();
			threadLocal.set(new EdgeDriver());
		}
		else
		{
			System.out.println("Enter valid browser name !!!");
			throw new FrameworkExceptions("Invalid browser name exception");
		}
		
		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		getDriver().get(prop.getProperty("url").trim());
		return getDriver();
	}
	
	public static WebDriver getDriver() {
		return threadLocal.get();
	}
	public static String getScreenshot() {
		File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
		String path = "./"+"Screenshot"+System.currentTimeMillis()+".png";
		File destFile = new File(path);
		try {
			FileUtils.copyFile(srcFile, destFile);
		}catch(IOException e)
		{
			e.printStackTrace();
		}
		return path;
	}
	public Properties init_prop() {
		FileInputStream fp = null;
		prop = new Properties();
		//mvn clean install -Denv="qa"
		String envName = System.getProperty("env");
		try {
			if(envName==null)
			{
				System.out.println("No environment variable is given... Hence used default environment");
				fp = new FileInputStream("./src/test/resources/config/config.properties");
			}
			else
			{
				switch(envName.toLowerCase()){
				case "qa":
					System.out.println("The given environment details is :" + envName.toLowerCase());
					fp = new FileInputStream("./src/test/resources/config/qa.config.properties");
					break;
				default:
					System.out.println("Entered environment is not in our list "+ envName);
					throw new FrameworkExceptions("Invalid environment exception");
//					break;
				}
			}
			
		} catch (FileNotFoundException e1) {
					e1.printStackTrace();
		}
		try {			
			prop.load(fp);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}
}
