package com.qa.opencart.constants;

import java.util.Arrays;
import java.util.List;

public class Constants {

	public final static String LOGIN_PAGE_TITLE = "Account Login";
	public final static String LOGIN_PAGE_URL_FRACTION = "route=account/login";
	public final static String LOGOUT_PAGE_MESSAGE = "Account Logout";
	
	public final static String ACCOUNT_PAGE_TITLE = "My Account";
	public final static String ACCOUNT_PAGE_URL_FRACTION = "route=account/account";
	public final static List<String> EXPECTED_ACCOUNTS_SECTION_LIST = Arrays.asList("My Account", "My Orders", "Newsletter");
	
	public static final String ADD_CART_SUCCESS_MESSAGE="Success: You have added";
	
	public final static String SHOPPING_CART_PAGE_TITLE = "Shopping Cart";
	
	
	public final static int ELEMENT_TIME_OUT = 15;
	public final static int DEFAULT_TIME_OUT = 5;
}
