package com.hrm.testcases;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import com.hrm.baseclass.BaseClass;
import com.hrm.utilities.Log;

import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;

public class TC_001_VerifyHomePage extends BaseClass
{
	@Test
	void compareHomePageScreenshot()
	{	
		Log.info("Scenario: To verify Snapshot of the Homepage");
		String scName = "homepage";
		//openBrowserWithUrl(driver);
		captureScreenshot(driver, scName);
		boolean status = compareScreenshots(driver, scName, "HomepageESC");
		if (status == true)
		{
			Log.error("Screenshot is  not as expected");
			softAssert.assertFalse(status);
		}
		else
			Log.info("Screenshot is as expected");
			softAssert.assertFalse(status);
			softAssert.assertAll();
	}
	
}
