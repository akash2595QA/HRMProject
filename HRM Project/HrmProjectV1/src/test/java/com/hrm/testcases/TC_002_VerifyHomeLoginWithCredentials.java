package com.hrm.testcases;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import com.hrm.baseclass.BaseClass;
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
		Log.info("Scenario: To verify the error message when invalid password is entered");
		HomePage hm = new HomePage(driver);
		String invalidPass = "adminwrong1";
		String expectedErrorMsg = "Invalid credentials";
		//openBrowserWithUrl(driver);
		hm.setUsername(validUsername);
		hm.setLoginPass(invalidPass);
		hm.clickLogin();
		Thread.sleep(2000);
		String actualErrorMsg = hm.getInvalidErrorMsg();
		//System.out.println("Actual msg is - "+actualErrorMsg+"---------------This is Expected message - "+expectedErrorMsg);
		softAssert.assertEquals(actualErrorMsg, expectedErrorMsg);
		softAssert.assertAll();
	}
	
	@Test
	void validPasswordAttempt() throws InterruptedException
	{
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
			softAssert.assertFalse(status);
		}
		else
			Log.info("Dashboard Screenshots is same as expected");
			softAssert.assertFalse(status);
			softAssert.assertAll();
	}

}
