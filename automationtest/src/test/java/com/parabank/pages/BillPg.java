package com.parabank.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class BillPg {
WebDriver driver;

@FindBy(xpath = "//input[@name='payee.name']")
private WebElement payeeName;

@FindBy(xpath = "//input[@name='payee.address.street']")
private WebElement payeeAddress;

@FindBy (xpath = "//input[@name='payee.address.city']")
private WebElement payeeCity;

@FindBy (xpath = "//input[@name='payee.address.state']")
private WebElement payeeState;

@FindBy (xpath = "//input[@name='payee.address.zipCode']")
private WebElement payeeZipCode;

@FindBy (xpath = "//input[@name='payee.phoneNumber']")
private WebElement payeePhoneNumber;

@FindBy (xpath = "//input[@name='payee.accountNumber']")
private WebElement payeeAccNo;

@FindBy (xpath = "//input[@name='verifyAccount']")
private WebElement verifyPayeeAccNo;

@FindBy (xpath = "//input[@name='amount']")
private WebElement payeeAmt;

@FindBy (xpath = "//select[@name='fromAccountId']")
private WebElement fromAccountId ;

@FindBy (linkText = "Bill Pay")
private WebElement billPayOption ;

@FindBy (xpath = "//input[@value='Send Payment']")
private WebElement sendPayment ;

public BillPg(WebDriver driver) {
this.driver= driver;
PageFactory.initElements(driver, this);
}

public WebElement getPayeeName() {
	return payeeName;
}

public WebElement getPayeeAddress() {
	return payeeAddress;
}

public WebElement getPayeeCity() {
	return payeeCity;
}

public WebElement getPayeeState() {
	return payeeState;
}

public WebElement getPayeeZipCode() {
	return payeeZipCode;
}

public WebElement getPayeePhoneNumber() {
	return payeePhoneNumber;
}

public WebElement getPayeeAccNo() {
	return payeeAccNo;
}

public WebElement getVerifyPayeeAccNo() {
	return verifyPayeeAccNo;
}

public WebElement getPayeeAmt() {
	return payeeAmt;
}

public WebElement getFromAccountId() {
	return fromAccountId;
}

public WebElement getBillPayOption() {
	return billPayOption;
}

public WebElement getSendPaymentEle() {
	return sendPayment;
}

public void setValueInFromAccountField(int fromaccount) {
	Select accountCreationOptions=new Select(this.getFromAccountId());
	accountCreationOptions.selectByValue(String.valueOf(fromaccount));
}

public void enterDetailsOfPayee(String name, String adds, String city, String state, String zip, String phone, int account1,int account2, String amount) {
	this.getPayeeName().sendKeys(name);
	this.getPayeeAddress().sendKeys(adds);
	this.getPayeeCity().sendKeys(city);
	this.getPayeeState().sendKeys(state);
	this.getPayeeZipCode().sendKeys(zip);
	this.getPayeePhoneNumber().sendKeys(phone);
	this.getPayeeAccNo().sendKeys(String.valueOf(account1));
	this.getVerifyPayeeAccNo().sendKeys(String.valueOf(account1));
	this.getPayeeAmt().sendKeys(amount);	
	this.setValueInFromAccountField(account2);
}
}
