package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.Constants;
import com.qa.opencart.utils.ElementUtil;

import io.qameta.allure.Step;

public class LoginPage {
	
	//Initialize ...
	private WebDriver driver;
	private ElementUtil elementUtil;
	
	//Locators
	private By emailAddress = By.id("input-email");
	private By passwordVal = By.id("input-password");
	private By forgottonPasswordLink = By.linkText("Forgotten Password");
	private By loginBtn = By.xpath("//input[@type='submit']");
	private By logoutSuccessMsg = By.xpath("//div[@id='content']/h1");		

	//Actions
	public LoginPage(WebDriver driver) {
		this.driver=driver;
		elementUtil = new ElementUtil(this.driver);
	}


	@Step("To get login page url of Open cart application")
	public String getLoginPageUrl() {
		return elementUtil.waitForUrlContains(Constants.LOGIN_PAGE_URL_FRACTION, Constants.DEFAULT_TIME_OUT);
	}
	
	@Step("To enter {0} and {1} and click login button")
	public AccountPage doLogin(String userName, String password) {
		elementUtil.waitForElementVisible(emailAddress, Constants.ELEMENT_TIME_OUT).sendKeys(userName);
		elementUtil.doSendKeys(passwordVal, password);
		elementUtil.doClick(loginBtn);
		return new AccountPage(driver);
	}
	
	@Step("To check forget password link is exist or not")
	public boolean isForgotPasswordLinkExist() {
		return elementUtil.doIsDisplayed(forgottonPasswordLink);
	}

	@Step("To get login page title of Open cart application")
	public String getLoginPageTitle() {
		return elementUtil.waitForTitleIs(Constants.LOGIN_PAGE_TITLE, Constants.DEFAULT_TIME_OUT);
	}

	@Step("To logout from Open cart application")
	public String getLogoutSuccess() {
		// TODO Auto-generated method stub
		return elementUtil.waitForElementVisible(logoutSuccessMsg, Constants.ELEMENT_TIME_OUT).getText();
	}
	
	
	
}
