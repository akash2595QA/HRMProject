package com.hrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.WebElement;

public class HomePage 
{
	WebDriver driver;
	public HomePage(WebDriver rdriver)
	{
		driver = rdriver;
		PageFactory.initElements(driver, this);
	}
	
	//Elements
    @FindBy(name="username")
    private WebElement txtUsername;
    
    @FindBy(name="password")
	private WebElement txtPassword;		
    
	@FindBy(xpath="//button[@type='submit']")
	private WebElement btnLogin;		
	
	@FindBy(xpath="//div[1]/p")
	private WebElement loginFalureMessage;
	
	@FindBy(linkText="Forgot your password?")
    private WebElement linkForgotpPass;
	
	//Actions
	public void setUsername(String uname)
	{
		txtUsername.sendKeys(uname);
	}
	
	public WebElement elementUsername()
	{
		return txtUsername;
	}
	
	public void setLoginPass(String pass)
	{
		txtPassword.sendKeys(pass);
	}
	
	public void clickLogin()
	{
		btnLogin.click();
	}
	
	public void clickForgetPass()
	{
		linkForgotpPass.click();
	}
	
	public String getInvalidErrorMsg()
	{
		return loginFalureMessage.getText();
	}
	
	public WebElement elementLoginfailureMsg()
	{
		return loginFalureMessage;
	}
}
