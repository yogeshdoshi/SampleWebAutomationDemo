package com.axelerant;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.test.util.TestUtil;

import io.restassured.response.Response;

public class Assignment2 {
	Properties prop = null;
	private static final Logger logger = LogManager.getLogger(Assignment2.class);
	private String createAccountApi = "/parabank/services_proxy/bank/createAccount";

	@BeforeTest
	public void beforeTest() throws IOException {
		prop = TestUtil.readPropertiesFile(System.getProperty("user.dir") + "/src/test/resources/config.properties");
		logger.info("api url is " + prop.getProperty("api.baseURL"));
	}
	@DataProvider(name="getAccountType")
	private Object[][] getAccountType(){
		return new Object[][] {
			{"0"},{"1"}};
	}
	
	@Test(priority = 1, dataProvider = "getAccountType")
	public void createAccount(String accType) throws JSONException {
		logger.info("Verifying User is able to Create New Account");
		Response response1 = given().header("Content-type", "application/json;charset=UTF-8")
				.header("authority", "parabank.parasoft.com")				
				.header("accept", "application/json, text/plain, */*").header("sec-ch-ua-mobile", "?0")
				.header("origin", "https://parabank.parasoft.com")							
				.header("cookie", "JSESSIONID=6D6F371876493B3D4FF90890CBFB7885").cookie("").when()
				.post(prop.getProperty("api.baseURL") + createAccountApi+ "?customerId=12212&newAccountType="+accType+"&fromAccountId=12345")
				.then().extract().response();
		String id = response1.jsonPath().getString("id");
		String balance=response1.jsonPath().getString("balance");
		String acc_type=response1.jsonPath().getString("type");
		logger.info("Account Created Details id="+id +" And Balance= "+balance+ " And Type="+acc_type);
		//Newly created account verification
		Assert.assertTrue(!id.isEmpty() && Integer.parseInt(id)>0, "Account id cannot be empty and account id is greater than zero");
		if(accType.equals("0")) {
		Assert.assertTrue(acc_type.equals("CHECKING"), "AccountType is incorrect");
		} else {
		Assert.assertTrue(acc_type.equals("SAVINGS"), "AccountType is incorrect");	
		}
	}
}
