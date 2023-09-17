package com.hrm.testcases;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.markuputils.Markup;
import com.hrm.baseclass.BaseClass;
import com.hrm.utilities.Log;

import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;

public class TC_001_VerifyHomePage extends BaseClass
{
	@Test
	void compareHomePageScreenshot() throws InterruptedException
	{	
		extentTest = extent.createTest("HomePage - snapshot", "Verify screenshot of the Homepage is as expected");
		Log.info("Scenario: To verify Snapshot of the Homepage");
		String scName = "homepage";
		//openBrowserWithUrl(driver);
		captureScreenshot(driver, scName);
		boolean status = compareScreenshots(driver, scName, "HomepageESC");
		if (status == true)
		{
			Log.error("Screenshot is  not as expected");
			softAssert.assertFalse(status);
			String base64Code = screenShotCapture();
			extentTest.fail("Screenshot is  not as expected").info(MediaEntityBuilder.createScreenCaptureFromBase64String(base64Code, "Screenshot Attached").build());
		}
		else
		{
			Log.info("Screenshot is as expected");
			softAssert.assertFalse(status);
			extentTest.pass("Screenshot is as expected");
		}
			softAssert.assertAll();
	}
	
}
