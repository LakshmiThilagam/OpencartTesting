package com.qa.opencart.tests;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.Constants;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("EPIC - 002: This epic for Opencart applicaton - Account Page")
@Story("USER STORY - 101: Desing test for Account page and its features")
public class AccoutPageTest extends BaseTest {

	@BeforeClass
	public void accSetup() {
		accountPage = loginPage.doLogin(prop.getProperty("username").trim(), prop.getProperty("password").trim());
	}

	@Description("Account page title test ...")
	@Severity(SeverityLevel.MINOR)
	@Test(priority=1)
	public void accPageTitleTest()
	{
		String accTitle= accountPage.getAccountPageTitle();
		Assert.assertEquals(accTitle, Constants.ACCOUNT_PAGE_TITLE);
	}
	
	
	@Test(priority=2)
	public void accPageSubHeadersTest() {
		List<String> headersList = accountPage.getSubHeaders();
		System.out.println("The list of sub headers are "+ headersList);
		Assert.assertEquals(headersList, Constants.EXPECTED_ACCOUNTS_SECTION_LIST );
	}

	@Test(priority=5)
	public void logOutTest() {
		String logoutMsg = accountPage.doClickLogout().getLogoutSuccess();
		Assert.assertEquals(logoutMsg, Constants.LOGOUT_PAGE_MESSAGE);
	}
	
	@DataProvider
	public Object[][] getSearchKey(){
		return new Object[][] {
			{"macbook"},
			{"apple"},
			{"samsung"}
		};
	}
	@Test(priority=3, dataProvider="getSearchKey")
	public void searchResultTest(String searchKey){
		searchResultPage = accountPage.doSearch(searchKey);
		Assert.assertTrue(searchResultPage.getSearchResultCount()>0);
	}
	
	@DataProvider
	public Object[][] getProductName() {
		return new Object[][] { { "MacBook", "MacBook Pro" }, { "Apple", "Apple Cinema 30\"" },
				{ "Samsung", "Samsung SyncMaster 941BW" } };
	}
	
	@Test(priority=4, dataProvider="getProductName")
	public void productResultTest(String searchKey, String productName) {
		searchResultPage = accountPage.doSearch(searchKey);
		productInfoPage = searchResultPage.selectProduct(productName);
		Assert.assertEquals(productInfoPage.getProductName(),productName);
	}
	
	@DataProvider
	public Object[][] getProductImagesCount(){
		return new Object[][] {
			{"MacBook", "MacBook Pro",4},
			{"Apple","Apple Cinema 30\"",6},
			{"Samsung","Samsung SyncMaster 941BW",1}
		};
	}
	
	@Test(priority=5, dataProvider="getProductImagesCount")
	public void productImageCountTest(String searchKey, String productName, int count) {
		searchResultPage = accountPage.doSearch(searchKey);
		productInfoPage = searchResultPage.selectProduct(productName);
		Assert.assertEquals(productInfoPage.getImagesCount(),count);
	}

	@DataProvider
	public Object[][] addProductQuantityCount() {
		return new Object[][] { { "MacBook", "MacBook Pro", 4 }, { "Samsung", "Samsung SyncMaster 941BW", 1 } };
	}

	@Test(priority = 6, dataProvider = "addProductQuantityCount")
	public void productAddQuantityTest(String searchKey, String productName, int quantity) {
		searchResultPage = accountPage.doSearch(searchKey);
		productInfoPage = searchResultPage.selectProduct(productName);
		String successMsg = productInfoPage.addNumberOfItems(quantity).clickAddCartBtn().getSuccessMsg();
		System.out.println("The success message " + successMsg);
		Assert.assertTrue(successMsg.contains("Success: You have added " + productName + " to your shopping cart!"));
	}

	@DataProvider
	public Object[][] checkProductName(){
		return new Object[][] {
			{"MacBook", "MacBook Pro",4},
			{"Samsung","Samsung SyncMaster 941BW",1}
		};
	}
	@Test(priority=7, dataProvider="checkProductName")
	public void shoppingCartTest(String searchKey, String productName, int quantity) throws InterruptedException {
		searchResultPage = accountPage.doSearch(searchKey);
		productInfoPage = searchResultPage.selectProduct(productName);
		productInfoPage.addNumberOfItems(quantity);
		productInfoPage.clickAddCartBtn();
		shoppingCartPage = productInfoPage.doClickShoppingCart();
		Assert.assertTrue(shoppingCartPage.checkProductName(productName));
	}

	@Test(priority = 8)
	public void pageInnerTextTest() {
		searchResultPage = accountPage.doSearch("MacBook");
		productInfoPage = searchResultPage.selectProduct("MacBook");
		Assert.assertTrue(productInfoPage.getPageInnerText().contains("Intel Core 2 Duo processor"));
	}
	@Test
	public void productDataTest() {
		searchResultPage = accountPage.doSearch("Samsung");
		productInfoPage = searchResultPage.selectProduct("Samsung SyncMaster 941BW");
		Map<String,String> productData = productInfoPage.getMetaData();
		softAssert.assertEquals(productData.get("name"),"Samsung SyncMaster 941BW");
		softAssert.assertEquals(productData.get("Brand"),"Apple"); //doubt
		softAssert.assertAll();
		
		}
}
