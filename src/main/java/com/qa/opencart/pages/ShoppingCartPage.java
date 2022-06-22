package com.qa.opencart.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.Constants;
import com.qa.opencart.utils.ElementUtil;

public class ShoppingCartPage {

	private WebDriver driver;
	private ElementUtil elementUtil;
	
	private By productName = By.xpath("//div[@class='table-responsive']//td[2]/a");
	
	
	public String getShoppingCartPageTitle()
	{
		return elementUtil.waitForTitleIs(Constants.SHOPPING_CART_PAGE_TITLE,Constants.DEFAULT_TIME_OUT);
	}
	
	public ShoppingCartPage(WebDriver driver) {
		this.driver=driver;
		elementUtil = new ElementUtil(this.driver);
	}
	
	public boolean checkProductName(String productsName) {
		List<WebElement> productList = elementUtil.waitForElementsVisible(productName, Constants.ELEMENT_TIME_OUT);
		for(WebElement e: productList) {
			String product = e.getText();
			System.out.println("The product name is "+ product);
			if(product.equalsIgnoreCase(productsName))
				return true;
		}
		return false;
		
	}
}
