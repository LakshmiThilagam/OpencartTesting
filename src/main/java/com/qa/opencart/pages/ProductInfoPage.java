package com.qa.opencart.pages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.Constants;
import com.qa.opencart.utils.ElementUtil;

public class ProductInfoPage {

	private WebDriver driver;
	private ElementUtil elementUtil;

	private By productName = By.cssSelector("div#content h1");
	private By imagesList = By.cssSelector("ul.thumbnails a");
	private By quantityVal = By.xpath("//input[@id='input-quantity']");
//	private By successMsg = By.xpath("//div[contains(text(),'Success: You have added')]");
	private By successMsg = By.cssSelector("div.alert.alert-success");
	private By addCartBtn = By.xpath("//button[@id='button-cart']");
	private By shoppingCartLink = By.linkText("shopping cart");
	private By metaData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[1]/li");
	private By productPriceData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[2]/li");

	Map<String, String> productDataInfo;

	public ProductInfoPage(WebDriver driver) {
		this.driver = driver;
		elementUtil = new ElementUtil(this.driver);
	}

	public String getProductName() {
		return elementUtil.waitForElementVisible(productName, Constants.ELEMENT_TIME_OUT).getText();
	}

	public int getImagesCount() {
		return elementUtil.waitForElementsVisible(imagesList, Constants.ELEMENT_TIME_OUT).size();
	}

	public ProductInfoPage addNumberOfItems(int quantity) {
		WebElement ele = elementUtil.waitForElementVisible(quantityVal, Constants.ELEMENT_TIME_OUT);
		ele.clear();
		ele.sendKeys(String.valueOf(quantity));
		return this;
	}

	public ProductInfoPage clickAddCartBtn() {
		elementUtil.waitForElementVisible(addCartBtn, Constants.ELEMENT_TIME_OUT).click();
		return this;
	}

	public String getSuccessMsg() {

		return elementUtil.waitForElementVisible(successMsg, Constants.ELEMENT_TIME_OUT).getText();

	}

	public ShoppingCartPage doClickShoppingCart() {
		elementUtil.waitForElementVisible(shoppingCartLink, Constants.ELEMENT_TIME_OUT).click();
		return new ShoppingCartPage(driver);
	}

	public String getPageInnerText() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String pageText = (js.executeScript("return document.documentElement.innerText")).toString();
		System.out.println("==================================\n" + pageText + "================================\n");
		return pageText;

	}

	public Map<String, String> getMetaData() {
		productDataInfo = new HashMap<String, String>();
		productDataInfo.put("name", getProductName());
		getProductData();
		getProductPrice();
		productDataInfo.forEach((k, v) -> System.out.println(k + ":" + v));
		return productDataInfo;
	}

	private Map<String, String> getProductData() {
		List<WebElement> productData = elementUtil.getElements(metaData);
		for (WebElement e : productData) {
			String meta[] = e.getText().split(":");
			String key = meta[0].trim();
			String value = meta[1].trim();
			productDataInfo.put(key, value);
		}
		return productDataInfo;
	}

	private Map<String, String> getProductPrice()
	{
		List<WebElement> productPrice = elementUtil.getElements(productPriceData);
		String price = productPrice.get(0).getText();
		String tax = productPrice.get(1).getText();
		productDataInfo.put("price",price);
		productDataInfo.put("tax",tax);
		return productDataInfo;
	}

}
