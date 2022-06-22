package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.Constants;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("EPIC - 001: This epic for Opencart applicaton - Login Page")
@Story("LOGIN - 101: Design test for login page and all the features")
public class LoginPageTest extends BaseTest {

	@Description("Login page title test ...")
	@Severity(SeverityLevel.MINOR)
	@Test
	public void loginPageTitleTest() {
		String actTitle = loginPage.getLoginPageTitle();
		Assert.assertEquals(actTitle, Constants.LOGIN_PAGE_TITLE);
	}
	
	@Description("Login page URL test ...")
	@Severity(SeverityLevel.MINOR)
	@Test
	public void LoginPageUrlTest()
	{
		String url = loginPage.getLoginPageUrl();
		Assert.assertTrue(url.contains(Constants.LOGIN_PAGE_URL_FRACTION));
	}
	
	@Description("Login page forget password link test ...")
	@Severity(SeverityLevel.NORMAL)
	@Test
	public void forgetPasswordLinkTest()
	{
		Assert.assertTrue(loginPage.isForgotPasswordLinkExist());
	}
	
	@Description("Login page test ...")
	@Severity(SeverityLevel.CRITICAL)
	@Test
	public void loginTest() {
		Assert.assertTrue(loginPage.doLogin(prop.getProperty("username").trim(),prop.getProperty("password").trim())
		          .getLogoutLink());
	}
	
}
