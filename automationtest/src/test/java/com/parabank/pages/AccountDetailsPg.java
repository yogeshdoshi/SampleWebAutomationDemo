package com.parabank.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AccountDetailsPg {
WebDriver driver;

@FindBy(css = "#accountId")
private WebElement accountId;

@FindBy(css = "#accountType")
private WebElement accountType;

@FindBy(css = "#balance")
private WebElement balance;

public AccountDetailsPg(WebDriver driver) {
	this.driver=driver;
	PageFactory.initElements(driver, this);
}

public WebElement getAccountId() {
	return accountId;
}

public WebElement getAccountType() {
	return accountType;
}

public WebElement getBalance() {
	return balance;
}

}
