package com.hrm.testcases;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.hrm.baseclass.BaseClass;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;
import com.hrm.pages.*;
import com.hrm.utilities.Log;
public class TC_002_VerifyHomeLoginWithCredentials extends BaseClass
{
	
	@Test
	void invalidPasswordAttempt() throws InterruptedException
	{	
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		extentTest = extent.createTest("Login - Invalid credentials", "Verify the error message on invalid password login attempt");
		Log.info("Scenario: To verify the error message when invalid password is entered");
		HomePage hm = new HomePage(driver);
		String invalidPass = "adminwrong1";
		String expectedErrorMsg = "Invalid credentials";
		//openBrowserWithUrl(driver);
		hm.setUsername(validUsername);
		hm.setLoginPass(invalidPass);
		hm.clickLogin();
		wait.until(ExpectedConditions.elementToBeClickable(hm.elementLoginfailureMsg()));
		String actualErrorMsg = hm.getInvalidErrorMsg();
		//System.out.println("Actual msg is - "+actualErrorMsg+"---------------This is Expected message - "+expectedErrorMsg);
		if(actualErrorMsg.equals(expectedErrorMsg))
		{
			softAssert.assertTrue(true);
			Log.info("Correct error message is displayed");
			extentTest.pass("Correct error message is displayed");
		}
		else
		{
			softAssert.assertTrue(false);
			Log.error("Incorrect error message is displayed");
			String base64Code = screenShotCapture();
			extentTest.fail("Incorrect error message is displayed").info(MediaEntityBuilder.createScreenCaptureFromBase64String(base64Code, "Screenshot Attached").build());
		}
		softAssert.assertAll();
	}
	
	@Test
	void validPasswordAttempt() throws InterruptedException
	{
		extentTest = extent.createTest("Login - Valid credentials", "Verify screenshot of the dashboard after login is as expected");
		Log.info("Scenario: To verify Snapshot of the Page after entering valid login details");
		HomePage hm = new HomePage(driver);
	//	openBrowserWithUrl(driver);
		hm.setUsername(validUsername);
		hm.setLoginPass(validPassword);
		hm.clickLogin();
		Thread.sleep(2000);
		//JavascriptExecutor js = (JavascriptExecutor) driver;
		//js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		String screenshotFile = "dashboardPage";
		captureScreenshot(driver, screenshotFile);
		boolean status = compareScreenshots(driver, screenshotFile, "DashboardESC");
		if (status == true)
		{
			Log.error("Dashboard Screenshot is not same as expected");
			softAssert.assertTrue(false);
			String base64Code = screenShotCapture();
			extentTest.fail("Dashboard Screenshot is not as expected").info(MediaEntityBuilder.createScreenCaptureFromBase64String(base64Code, "Screenshot Attached").build());
		}
		else
		{
			Log.info("Dashboard Screenshots is same as expected");			
			softAssert.assertTrue(true);
			extentTest.pass("Dashboard Screenshot is as expected");	
		}
		softAssert.assertAll();
	}

}
