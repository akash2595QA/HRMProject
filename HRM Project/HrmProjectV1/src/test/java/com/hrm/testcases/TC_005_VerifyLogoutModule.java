package com.hrm.testcases;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.github.javafaker.Faker;
import com.hrm.pages.AdminPage;
import com.hrm.pages.HomePage;
import com.hrm.utilities.Log;
import com.hrm.baseclass.BaseClass;

public class TC_005_VerifyLogoutModule extends BaseClass
{
	@Test
	void verifyContentsOfTheUserProfileBtn() throws InterruptedException
	{
		extentTest = extent.createTest("User Profile - menu", "Verify contents on clicking User profile option");
		Log.info("Scenario: To verify contents on clicking User profile option");
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
			Log.error("The User Profile dropdown does not contain all or some of these options {\"About\", \"Support\", \"Change Password\", \"Logout\"}");
			softAssert.assertTrue(false);
			String base64Code = screenShotCapture();
			extentTest.fail("The User Profile dropdown does not contain all or some of these options {\"About\", \"Support\", \"Change Password\", \"Logout\"}").info(MediaEntityBuilder.createScreenCaptureFromBase64String(base64Code, "Screenshot Attached").build());
		}
		else
		{
			Log.info("The User dropdown contains all the expected options");
			softAssert.assertTrue(true);
			extentTest.pass("The User dropdown contains all the expected options");
		}
		softAssert.assertAll();
		
	}
	
	@Test
	void verifyLogout() throws InterruptedException
	{
		extentTest = extent.createTest("Logout button", "verify that the logout function works correctly");
		Log.info("Scenario: To verify that the logout function works correctly");
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
			Log.info("Successfully logged out");
			softAssert.assertTrue(true);	
			extentTest.pass("Successfully logged out");
		}
		else
		{
			Log.error("Unsuccessfull logout");
			softAssert.assertTrue(false);
			String base64Code = screenShotCapture();
			extentTest.fail("Unsuccessfull logout").info(MediaEntityBuilder.createScreenCaptureFromBase64String(base64Code, "Screenshot Attached").build());
		}
		softAssert.assertAll();
	}
}
