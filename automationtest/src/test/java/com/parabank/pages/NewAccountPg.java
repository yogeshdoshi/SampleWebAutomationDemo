package com.parabank.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.test.util.TestUtil;

public class NewAccountPg {
WebDriver driver;	
	
@FindBy(tagName ="input")
private WebElement openNewAccBtn;

@FindBy(xpath = "//select[@id='type']")
private WebElement accountTypeSelectionEle;

public NewAccountPg(WebDriver driver) {
	this.driver=driver;
	PageFactory.initElements(driver, this);
}

public WebElement getOpenNewAccBtn() {		
	TestUtil.sleepInMiliSeconds(2000);
	ExpectedConditions.visibilityOf(openNewAccBtn);
	return openNewAccBtn;
}

public WebElement getAccountSelectEle() {
	return accountTypeSelectionEle;
}
}
