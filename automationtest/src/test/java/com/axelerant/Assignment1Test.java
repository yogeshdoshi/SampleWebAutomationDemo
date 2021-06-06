package com.axelerant;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.parabank.pages.AccountDetailsPg;
import com.parabank.pages.AccountOpenedPg;
import com.parabank.pages.BillPg;
import com.parabank.pages.DashBoardPg;
import com.parabank.pages.HomePg;
import com.parabank.pages.NewAccountPg;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.test.util.TestUtil;
import com.testbase.TestBase;

public class Assignment1Test extends TestBase {
	private static Logger log=Logger.getLogger(Assignment1Test.class);
	ExtentTest logger;
	public ArrayList<Integer> newlyCreatedAccountNos=new ArrayList<Integer>(); 	
	
	
@DataProvider(name="accounttype")
public Object[][] getAccountType(){
	return new Object[][]{
		{"CHECKING"},
		{"SAVINGS"}
		};
}

@DataProvider(name="getNewlyCreatedAccountNos")
public Object[][] getNewlyCreatedAccountNos(){
	return new Object[][]{
		{newlyCreatedAccountNos.get(0),"CHECKING"},
		{newlyCreatedAccountNos.get(1), "SAVINGS"}
		};
}
	
@Test
public void loginTest() {
	logger = extent.startTest("loginTest");
	HomePg home=new HomePg(driver);
	home.login(prop.getProperty("username"), prop.getProperty("password"));	
	DashBoardPg db=new DashBoardPg(driver);	
	Assert.assertEquals(db.getNewAccountElement().getText(), "Open New Account" , "Open New Account is not visible");
	logger.log(LogStatus.PASS, "Test Case Passed is passTest");
}

@Test(dataProvider = "accounttype",dependsOnMethods = "loginTest")
public void verifyNewAccountOpening_TC001(String accounttype) {	
	logger = extent.startTest("verifyNewAccountOpening_TC001");
	DashBoardPg db=new DashBoardPg(driver);
	NewAccountPg NewAccPg=new NewAccountPg(driver);
	db.getNewAccountElement().click();
	//Selecting account type as savings
	if(accounttype.equals("SAVINGS")) {		
		ExpectedConditions.visibilityOf(NewAccPg.getAccountSelectEle());
		Select accountCreationOptions=new Select(NewAccPg.getAccountSelectEle());
		accountCreationOptions.selectByVisibleText(accounttype);
	}	
	NewAccPg.getOpenNewAccBtn().click();
	AccountOpenedPg postNewAcc=new AccountOpenedPg(driver);
	newlyCreatedAccountNos.add(Integer.parseInt(postNewAcc.getNewlyCreatedAccountIdEle().getText()));	
	Assert.assertTrue(newlyCreatedAccountNos.size()>0, "Issue in creating new account");
	logger.log(LogStatus.PASS, "Test Case Passed is passTest");
}

@Test(dataProvider = "getNewlyCreatedAccountNos",dependsOnMethods = "verifyNewAccountOpening_TC001")
public void verifyNewlyCreatedAccountDetails_TC002(int accNo, String acc_type) {	
	logger = extent.startTest("verifyNewlyCreatedAccountDetails_TC002");
	DashBoardPg db=new DashBoardPg(driver);
	db.getNewAccountOverviewEle().click();	
	db.getNewlyCreatedAccountEleBasedOnAccountNumber(accNo).click();	
	AccountDetailsPg accdetails=new AccountDetailsPg(driver);	
	TestUtil.sleepInMiliSeconds(1500);
	Assert.assertEquals(accdetails.getAccountType().getText(), acc_type , "NewlyCreated Acccounttype seems to be incorrect");
	Assert.assertEquals(accdetails.getBalance().getText(), "$100.00" , "NewlyCreated Acccount balance seems to be incorrect");
	Assert.assertEquals(accdetails.getAccountId().getText(), String.valueOf(accNo) , "NewlyCreated AcccountId seems to be incorrect");
	logger.log(LogStatus.PASS, "Test Case Passed is passTest");
}

@Test(dependsOnMethods = "verifyNewlyCreatedAccountDetails_TC002")
public void paymentTransferAndVerifyTransactionDetails_TC003_001() {
	logger = extent.startTest("paymentTransferAndVerifyTransactionDetails_TC003_001");
	BillPg billpg=new BillPg(driver);
	billpg.getBillPayOption().click();
	billpg.enterDetailsOfPayee("Yogesh Doshi", "Heaven Tower, Adajan Patia", "Surat","Gujarat", "395001", "9999999999", this.newlyCreatedAccountNos.get(1), this.newlyCreatedAccountNos.get(0) , "200");	
	billpg.getSendPaymentEle().click();	
	logger.log(LogStatus.PASS, "Test Case Passed is passTest");
}

@Test(dataProvider = "getNewlyCreatedAccountNos", dependsOnMethods = "paymentTransferAndVerifyTransactionDetails_TC003_001")
public void paymentTransferAndVerifyTransactionDetails_TC003_002(int accNo, String acc_type) {
	logger = extent.startTest("paymentTransferAndVerifyTransactionDetails_TC003_002");
	DashBoardPg db=new DashBoardPg(driver);
	db.getNewAccountOverviewEle().click();	
	db.getNewlyCreatedAccountEleBasedOnAccountNumber(accNo).click();	
	AccountDetailsPg accdetails=new AccountDetailsPg(driver);	
	TestUtil.sleepInMiliSeconds(1500);
	//from account 1, 200 transferred as bill payment so amount would be 100-200=-100
	//in other account it would be 100 as we have not made any change to that account 
	if(accNo==this.newlyCreatedAccountNos.get(0)) {
	Assert.assertEquals(accdetails.getBalance().getText(), "-$100.00" , "NewlyCreated Acccount balance seems to be incorrect");
	}else {
	Assert.assertEquals(accdetails.getBalance().getText(), "$100.00" , "NewlyCreated Acccount balance seems to be incorrect");	
	}
	logger.log(LogStatus.PASS, "Test Case Passed is passTest");
}

@AfterMethod
public void getResult(ITestResult result) throws Exception{
	if(result.getStatus() == ITestResult.FAILURE){
		logger.log(LogStatus.FAIL, "Test Case Failed is "+result.getName());
		logger.log(LogStatus.FAIL, "Test Case Failed is "+result.getThrowable());		 			
        String screenshotPath = this.getScreenshot(driver, result.getName());		 
		logger.log(LogStatus.FAIL, logger.addScreenCapture(screenshotPath));
	}else if(result.getStatus() == ITestResult.SKIP){
		logger.log(LogStatus.SKIP, "Test Case Skipped is "+result.getName());
	}
	extent.endTest(logger);
}

public String getScreenshot(WebDriver driver, String screenshotName) throws Exception {
    	
String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
TakesScreenshot ts = (TakesScreenshot) driver;
File source = ts.getScreenshotAs(OutputType.FILE);
//after execution, you could see a folder "FailedTestsScreenshots" under src folder
String destination = System.getProperty("user.dir") + "/FailedTestsScreenshots/"+screenshotName+dateName+".png";
File finalDestination = new File(destination);
FileUtils.copyFile(source, finalDestination);
return destination;
}

@AfterTest
public void afterTest() {
	extent.flush();
	extent.close();
	DashBoardPg db=new DashBoardPg(driver);
	db.getLogoutEle().click();
}
}
