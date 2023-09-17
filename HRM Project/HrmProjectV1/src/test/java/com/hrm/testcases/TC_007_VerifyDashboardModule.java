package com.hrm.testcases;
import org.testng.annotations.Test;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.hrm.baseclass.BaseClass;
import com.hrm.pages.AdminPage;
import com.hrm.pages.HomePage;
import com.hrm.utilities.Log;

public class TC_007_VerifyDashboardModule extends BaseClass
{
	@Test
	void verifyDashboardPageContents() throws InterruptedException
	{
		extentTest = extent.createTest("DashboardPage - check all the tabs", "Verify approriate tabs are present on the Dashboard Page");
		Log.info("Scenario: To verify approriate tabs are present on the Dashboard Page");
		HomePage hm = new HomePage(driver);
		//AdminPage ad = new AdminPage(driver);
		//openBrowserWithUrl(driver);
		hm.setUsername(validUsername);
		hm.setLoginPass(validPassword);
		hm.clickLogin();
		Thread.sleep(7000);
		String[] strArr = {"Time at Work", "My Actions", "Quick Launch", "Assign Leave", "Leave List", "Timesheets", "Apply Leave", "My Leave", "My Timesheet", "Buzz Latest Posts", "Employees on Leave Today", "Employee Distribution by Sub Unit", "Employee Distribution by Location"};
		boolean status = checkPageSourceContents(driver, strArr);
		if(status==true)
		{
			Log.info("Dashboard module contains all the features");
			softAssert.assertTrue(true);
			extentTest.pass("Dashboard module contains all the features");
		}
		else
		{
			Log.error("Dashboard module does not contain all the features");
			softAssert.assertTrue(false);
			String base64Code = screenShotCapture();
			extentTest.fail("Dashboard module does not contain all the features").info(MediaEntityBuilder.createScreenCaptureFromBase64String(base64Code, "Screenshot Attached").build());
		}
		
		softAssert.assertAll();
	}
	
}
