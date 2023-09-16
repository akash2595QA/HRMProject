package com.hrm.testcases;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import com.hrm.baseclass.BaseClass;

import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;

public class TC_001_VerifyHomePage extends BaseClass
{
	@Test
	void compareHomePageScreenshot()
	{	
		String scName = "homepage";
		//openBrowserWithUrl(driver);
		captureScreenshot(driver, scName);
		boolean status = compareScreenshots(driver, scName, "HomepageESC");
		if (status == true)
		{
			System.out.println("Screenshots are not same");
			softAssert.assertFalse(status);
		}
		else
			System.out.println("Screenshots are same");
			softAssert.assertFalse(status);
			softAssert.assertAll();
	}
	
}
