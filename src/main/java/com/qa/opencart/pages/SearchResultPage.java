package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.DataProvider;

import com.qa.opencart.constants.Constants;
import com.qa.opencart.utils.ElementUtil;

public class SearchResultPage {

	private WebDriver driver;
	private ElementUtil elementUtil;
	
	private By productLink;
	private By searchResult = By.cssSelector("div.product-layout.product-grid");
	
	public SearchResultPage(WebDriver driver) {
		this.driver=driver;
		elementUtil = new ElementUtil(this.driver);
	}
	
	public int getSearchResultCount() {
		return elementUtil.waitForElementsVisible(searchResult, Constants.ELEMENT_TIME_OUT).size();
	}
	
		
	
	public ProductInfoPage selectProduct(String productName) {
		productLink = By.linkText(productName);
		elementUtil.doClick(productLink);
		return new ProductInfoPage(driver);
	}

}
