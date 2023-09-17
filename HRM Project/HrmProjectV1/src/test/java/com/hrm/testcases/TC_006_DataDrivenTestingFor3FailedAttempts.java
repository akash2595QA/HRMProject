package com.hrm.testcases;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.hrm.baseclass.BaseClassDD;
import com.github.javafaker.Faker;
import com.hrm.pages.AdminPage;
import com.hrm.pages.HomePage;
import com.hrm.utilities.Log;


public class TC_006_DataDrivenTestingFor3FailedAttempts extends BaseClassDD
{	
	@Test(dataProvider="multiple-user-credentials")
	void verifyLoginWithThreeWrongAttempts(String usr, String pwd)
	{
		HomePage hm = new HomePage(driver);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.elementToBeClickable(hm.elementUsername()));
		hm.setUsername(usr);
		hm.setLoginPass(pwd);
		hm.clickLogin();
		String pageSourceAfterTwoFailesAttempts = driver.getPageSource();
		String expectedErrorMsg = "Your account is locked for 24 hours please try after 24 hours";
		if(pageSourceAfterTwoFailesAttempts.contains(expectedErrorMsg))
		{
			Log.info("Correct error message for three failed attempts is printed");
			softAssert.assertTrue(true);
		}
		else
		{
			Log.error("Expected error message for three failed attempts is not printed");
			softAssert.assertTrue(false);
		}
		softAssert.assertAll();
	}
	
	@DataProvider (name="multiple-user-credentials")
	public Object[][] getCredentials()
	{
		return new Object[][] {{"Admin1", "pass1"}, {"Admin2", "pass2"}, {"Admin3", "pass3"}};
	}

}

