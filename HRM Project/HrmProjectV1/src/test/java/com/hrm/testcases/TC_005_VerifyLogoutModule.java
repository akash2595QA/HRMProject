package com.hrm.testcases;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;
import com.hrm.pages.AdminPage;
import com.hrm.pages.HomePage;
import com.hrm.baseclass.BaseClass;

public class TC_005_VerifyLogoutModule extends BaseClass
{
	@Test
	void verifyContentsOfTheUserProfileBtn() throws InterruptedException
	{
		HomePage hm = new HomePage(driver);
		AdminPage ad = new AdminPage(driver);
		Faker faker = new Faker();
		//openBrowserWithUrl(driver);
		hm.setUsername(validUsername);
		hm.setLoginPass(validPassword);
		hm.clickLogin();
		Thread.sleep(3000);
		ad.clickOnUserProfile();
		Thread.sleep(3000);
		String getUserProfileDropDownMenu = driver.findElement(By.className("oxd-dropdown-menu")).getAttribute("outerHTML");
		String[] strArr = {"About", "Support", "Change Password", "Logout"};
		boolean chkStatus = checkPageSourceContents(driver, strArr);
		if(chkStatus==false)
		{
			System.out.println("The User Profile dropdown does not contain all or some of these options {\"About\", \"Support\", \"Change Password\", \"Logout\"}");
			softAssert.assertTrue(false);
		}
		else
		{
			System.out.println("The User dropdown contains all the expected options");
			softAssert.assertTrue(true);
		}
		softAssert.assertAll();
		
	}
	
	@Test
	void verifyLogout() throws InterruptedException
	{
		HomePage hm = new HomePage(driver);
		AdminPage ad = new AdminPage(driver);
		Faker faker = new Faker();
		//openBrowserWithUrl(driver);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.elementToBeClickable(hm.elementUsername()));
		hm.setUsername(validUsername);
		hm.setLoginPass(validPassword);
		hm.clickLogin();
		Thread.sleep(3000);
		ad.clickOnUserProfile();
		Thread.sleep(3000);
		ad.clickLogoutBtn();
		Thread.sleep(2000);
		String expectedPageUrlAfterLogout = "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login";
		String actualPageUrlAfterLogout = driver.getCurrentUrl();
		if(actualPageUrlAfterLogout.equals(expectedPageUrlAfterLogout))
		{
			System.out.println("Successfully logged out");
			softAssert.assertTrue(true);			
		}
		else
		{
			System.out.println("Unsuccessfull logout");
			softAssert.assertTrue(false);
		}
		softAssert.assertAll();
	}
}
