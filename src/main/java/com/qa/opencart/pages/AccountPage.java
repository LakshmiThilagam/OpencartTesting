package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.Constants;
import com.qa.opencart.utils.ElementUtil;

public class AccountPage {

	private WebDriver driver;
	private ElementUtil elementUtil;
	
	private By logo = By.cssSelector("div@logo a");
	private By headers = By.xpath("//div[@id='content']/h2");
	private By logoutLink = By.linkText("Logout");
	private By search = By.name("search");
	private By searchIcon = By.cssSelector("div#search button");
	

	public AccountPage(WebDriver driver) {
		this.driver=driver;
		elementUtil = new ElementUtil(this.driver);
	}

	public String getAccountPageTitle()
	{
		return elementUtil.waitForTitleIs(Constants.ACCOUNT_PAGE_TITLE,Constants.DEFAULT_TIME_OUT);
	}
	
	public List<String> getSubHeaders()
	{
		List<WebElement> subHeaderList = elementUtil.getElements(headers);
		List<String> headerList = new ArrayList<String>();
		for(WebElement e: subHeaderList) {
			String headerText=e.getText();
			headerList.add(headerText);
		}
		return headerList;
		
	}
	
	public boolean getLogoutLink() {
		return elementUtil.waitForElementVisible(logoutLink, Constants.ELEMENT_TIME_OUT).isDisplayed();
	}
	
	public LoginPage doClickLogout()
	{
		if(getLogoutLink()) {
			elementUtil.doClick(logoutLink);
			
		}
		return new LoginPage(driver);
	}
	
	public boolean isSearchExists() {
		return elementUtil.doIsDisplayed(search);
	}
	
	public SearchResultPage doSearch(String searchKey) {
		if(isSearchExists()) {
			elementUtil.waitForElementsVisible(searchIcon, Constants.ELEMENT_TIME_OUT);
			elementUtil.doSendKeys(search, searchKey);
			elementUtil.doClick(searchIcon);
			return new SearchResultPage(driver);
		}
		return null;
	}
	
	
}
