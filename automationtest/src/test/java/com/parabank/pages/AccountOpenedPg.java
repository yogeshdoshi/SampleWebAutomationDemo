package com.parabank.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AccountOpenedPg {
WebDriver driver;	

@FindBy(css = "#newAccountId")
private WebElement newAccountId;

public AccountOpenedPg(WebDriver driver) {
this.driver=driver;
PageFactory.initElements(driver, this);
}

public WebElement getNewlyCreatedAccountIdEle() {
	return newAccountId;
}
	
}
