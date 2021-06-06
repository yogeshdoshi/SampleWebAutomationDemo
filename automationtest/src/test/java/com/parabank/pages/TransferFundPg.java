package com.parabank.pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;



public class TransferFundPg {
WebDriver driver;

@FindBy(id = "amount")
public WebElement amount;


@FindBy(css = "#fromAccountId")
public WebElement fromAccountId;


@FindBy(css = "#toAccountId")
public WebElement toAccountId;

@FindBy(xpath = "//input[@class='button']")
public WebElement transferBtn;

@FindBy(linkText = "Transfer Funds")
public WebElement transferOption;

@FindBy(xpath = "//h1[contains(text(),'Transfer Complete!')]")
public WebElement transferComplete;

//h1[contains(text(),'Transfer Complete!')]

@FindBy(xpath = "//h1[contains(text(),'Transfer Funds')]")
public WebElement transferFundsLabel;

public TransferFundPg(WebDriver driver) {
	this.driver=driver;
	PageFactory.initElements(driver, this);
}

public WebElement getAmountEle() {
	return amount;
}

public WebElement getFromAccountIdEle() {
	return fromAccountId;
}

public WebElement getToAccountIdEle() {
	return toAccountId;
}

public WebElement getTransferBtnEle() {
	return transferBtn;	
}

public WebElement getTransferOptionEle() {
	return transferOption;	
}

public WebElement getTransferFundsLabel() {
	return transferFundsLabel;
}

public WebElement getTransferCompleteEle() {
	return transferComplete;
}

public void setValueInFromAccountField(int fromaccount) {
	Select accountCreationOptions=new Select(this.getFromAccountIdEle());
	accountCreationOptions.selectByValue(String.valueOf(fromaccount));
}

public void setValueInToAccountField(int toaccount) {
	Select accountCreationOptions=new Select(this.getToAccountIdEle());
	accountCreationOptions.selectByValue(String.valueOf(toaccount));
}
}
