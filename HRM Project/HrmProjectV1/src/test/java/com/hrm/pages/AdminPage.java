package com.hrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.WebElement;

public class AdminPage 
{
	WebDriver driver;
	public AdminPage(WebDriver rdriver)
	{
		driver = rdriver;
		PageFactory.initElements(driver, this);
	}
	
    @FindBy(xpath="//nav/div[2]/ul/li[1]/a")
    private WebElement linkAdmin;
 
    @FindBy(xpath="//div[1]/div/div[2]/input")
    private WebElement sysUsername;    
	
    @FindBy(xpath="//button[@type='submit']")
    private WebElement searchBtn;  
    
    @FindBy(xpath="//div[2]/div/div[2]/div[@class='oxd-select-wrapper']")
    private WebElement userRoleDropDown;    
    
    @FindBy(xpath="//div[3]/div/div[2]/div/div/input") //div/div[2]/div/div[contains(@class, 'oxd-autocomplete-text-input')]")
    private WebElement txtemployeeName;    
  
    @FindBy(xpath="//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div[2]/div[2]/div/span")
    private WebElement txtNoRecord;

    @FindBy(xpath="//form/div[1]/div/div[4]/div/div[2]/div/div/div[1]")
    private WebElement userStatusDropdown;    
    
    @FindBy(xpath="//div/div[2]/div[1]/button")
    private WebElement addUser;
 
    @FindBy(xpath="//div/div[1]/div/div[2]/div/div/div[1][@class='oxd-select-text-input']")
    private WebElement addNewUserRole;
    
    @FindBy(xpath="//div/div[2]/div/div[2]/div/div/input")
    private WebElement addNewEmployeeName;
  
    @FindBy(xpath="//div/div[3]/div/div[2]/div/div/div[1]")
    private WebElement addNewUserStatus;    

    @FindBy(xpath="//form/div[1]/div/div[4]/div/div[2]/input")
    private WebElement addNewUsername;    
  
    @FindBy(xpath="//div[1]/div/div[2]/input[@type='password']")
    private WebElement addNewUserPassword;     
    
    @FindBy(xpath="//div[2]/div/div[2]/input[@type='password']")
    private WebElement addNewUserConfirmPassword; 
  
    @FindBy(xpath="//div[3]/button[@type='submit']")
    private WebElement btnSave;     

    @FindBy(xpath="//div[6]/div/button[1][contains(@class, 'oxd-icon-button')]")
    private WebElement btnDeleteUser;     
    
    @FindBy(xpath="//div/div[3]/button[2]")
    private WebElement alertbtnDelete;      
    
    @FindBy(className ="oxd-userdropdown-name")
    private WebElement btnUserProfile;  
  
    @FindBy(xpath ="//ul[@class='oxd-dropdown-menu']/li[4]")
    private WebElement btnLogout; 
    
  
    //Actions
	public void clickAdmin()
	{
		linkAdmin.click();
	}
	
	public WebElement elementAdmin()
	{
		return linkAdmin;
	}
	
	public void setSysUsername(String uname)
	{
		sysUsername.sendKeys(uname);
	}
	
	public WebElement elementSearchUsername()
	{
		return sysUsername;
	}
	
	public void clickSearchBtn()
	{
		searchBtn.click();
	}
	
	public void clickUserRole()
	{
		userRoleDropDown.click();
	}
	
	public void setEmployeeName(String ename)
	{
		txtemployeeName.sendKeys(ename);
	}
		
	public String getRecordTxt()
	{
		return txtNoRecord.getText();
	}
	
	public void clickUserStatus()
	{
		userStatusDropdown.click();
	}
	
	public WebElement userStatusDropDownElement()
	{
		return userStatusDropdown;
	}
	
	public void clickAddUser()
	{
		addUser.click();
	}
	
	public WebElement elementAddUser()
	{
		return addUser;
	}
	
	public WebElement elementNewUserRole()
	{
		return addNewUserRole;
	}
	
	public WebElement elementNewEmployeeName()
	{
		return addNewEmployeeName;
	}	
	
	public WebElement elementNewUserStatus()
	{
		return addNewUserStatus;
	}
	
	public WebElement elementNewUsername()
	{
		return addNewUsername;
	}	
	
	
	public WebElement elementNewUserPass()
	{
		return addNewUserPassword;
	}
	
	public WebElement elementNewUserConfirmPass()
	{
		return addNewUserConfirmPassword;
	}
	
	public void clickSave()
	{
		btnSave.click();
	}	
	
	public void clickDeleteUserBtn()
	{
		btnDeleteUser.click();
	}
	
	public WebElement elementDeleteUserBtn()
	{
		return btnDeleteUser;
	}
	
	public void clickAlertDeleteBtn()
	{
		alertbtnDelete.click();
	}
	
	public void clickOnUserProfile() 
	{
		btnUserProfile.click();
	}
	
	public void clickLogoutBtn()
	{
		btnLogout.click();
	}
}
