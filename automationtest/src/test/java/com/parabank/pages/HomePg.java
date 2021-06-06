package com.parabank.pages;

import java.util.HashMap;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePg {
WebDriver driver;
WebDriverWait wait;

@FindBy(xpath="//input[@name='username']")
private WebElement username;

@FindBy(xpath ="//input[@name='password']")
private WebElement password;

@FindBy(xpath = "//input[@value='Log In']")
private WebElement logInbtn;

public HomePg(WebDriver driver) {
this.driver=driver;	
PageFactory.initElements(driver,this);
wait=new WebDriverWait(driver, 10);
}

public void login(String uname, String pass) {
ExpectedConditions.visibilityOf(username);
username.sendKeys(uname);
password.sendKeys(pass);
logInbtn.click();
}


}
