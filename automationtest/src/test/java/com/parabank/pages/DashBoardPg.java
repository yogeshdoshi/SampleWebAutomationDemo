package com.parabank.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class DashBoardPg {
WebDriver driver;
	
@FindBy(linkText = "Open New Account")
private WebElement openNewAccLink;

@FindBy(linkText = "Accounts Overview")
private WebElement openAccountOverview;

@FindBy(linkText = "Log Out")
private WebElement LogOut;

public DashBoardPg(WebDriver driver) {
this.driver=driver;
PageFactory.initElements(driver, this);
}

public WebElement getNewAccountElement() {
	return openNewAccLink;
}

public WebElement getNewAccountOverviewEle() {
	return openAccountOverview;
}

public WebElement getNewlyCreatedAccountEleBasedOnAccountNumber(int accountNo){
	return driver.findElement(By.linkText(String.valueOf(accountNo)));	
}

public WebElement getLogoutEle() {
	return LogOut;
}

}
