package com.qa.opencart.base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.asserts.SoftAssert;

import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.pages.AccountPage;
import com.qa.opencart.pages.LoginPage;
import com.qa.opencart.pages.ProductInfoPage;
import com.qa.opencart.pages.SearchResultPage;
import com.qa.opencart.pages.ShoppingCartPage;

public class BaseTest {
	public DriverFactory driverFactory;
	public Properties prop;
	public WebDriver driver;
	
	protected LoginPage loginPage;
	protected AccountPage accountPage;
	protected SearchResultPage searchResultPage;
	protected ProductInfoPage productInfoPage;
	protected ShoppingCartPage shoppingCartPage;
	
	public SoftAssert softAssert;
	
	@BeforeTest
	public void setUp() {
		driverFactory = new DriverFactory();
		prop=driverFactory.init_prop();
		driver = driverFactory.init_driver(prop);
		loginPage = new LoginPage(driver);
		softAssert = new SoftAssert();
	}
	
	@AfterTest
	public void tearDown()
	{
		driver.quit();
	}
}
