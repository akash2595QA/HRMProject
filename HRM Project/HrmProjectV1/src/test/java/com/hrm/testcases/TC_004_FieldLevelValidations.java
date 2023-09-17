package com.hrm.testcases;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.hrm.baseclass.BaseClass;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.github.javafaker.Faker;
import com.hrm.pages.AdminPage;
import com.hrm.pages.HomePage;
import com.hrm.utilities.Log;


public class TC_004_FieldLevelValidations extends BaseClass
{
	@Test
	void verifyLoginWithTwoWrongAttempts() throws InterruptedException
	{
		extentTest = extent.createTest("FieldValidation - 2 failed login attempts", "verify behavior of the Login after two failed attempts");
		Log.info("Scenario: To verify behavior of the Login after two failed attempts");
		HomePage hm = new HomePage(driver);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.elementToBeClickable(hm.elementUsername()));
		hm.setUsername("invalidusername1");
		hm.setLoginPass("invalidpass1");
		hm.clickLogin();
		Thread.sleep(2000);
		hm.setUsername("invalidusername2");
		hm.setLoginPass("invalidpass2");
		hm.clickLogin();
		Thread.sleep(2000);
		String pageSourceAfterTwoFailesAttempts = driver.getPageSource();
		String expectedErrorMsg = "You already made 2 wrong attempts and on 3rd attempt your account will be locked for 24 hours";
		if(pageSourceAfterTwoFailesAttempts.contains(expectedErrorMsg))
		{
			Log.info("Correct error message for two failed attempts is printed");
			softAssert.assertTrue(true);
			extentTest.pass("Correct error message for two failed attempts is printed");
		}
		else
		{
			Log.error("Expected error message for two failed attempts is not printed");
			softAssert.assertTrue(false);
			String base64Code = screenShotCapture();
			extentTest.fail("Expected error message for two failed attempts is not printed").info(MediaEntityBuilder.createScreenCaptureFromBase64String(base64Code, "Screenshot Attached").build());
		}
		softAssert.assertAll();
	}
	
	@Test
	void validatingAddUserWhenEmployeeNameFieldIsEmpty() throws InterruptedException
	{
		extentTest = extent.createTest("FieldVaidation - Add user empty Employee name", "Validate adding user keeping Employee name field blank");
		Log.info("To validate adding user keeping Employee name field blank");
		HomePage hm = new HomePage(driver);
		AdminPage ad = new AdminPage(driver);
		Faker faker = new Faker();
		//openBrowserWithUrl(driver);
		hm.setUsername(validUsername);
		hm.setLoginPass(validPassword);
		hm.clickLogin();
		Thread.sleep(3000);
		ad.clickAdmin();
		//System.out.println("Clicked on admin");
		Thread.sleep(2000);
		ad.clickAddUser();
		Thread.sleep(2000);
		ad.elementNewUserRole().sendKeys("a");
		ad.elementNewUserRole().sendKeys(Keys.RETURN);
		Thread.sleep(3000);
		//driver.findElement(By.xpath("//div/div[2]/div/div[2]/div/div[2]")).click();
		ad.elementNewUserStatus().sendKeys("e");
		ad.elementNewUserStatus().sendKeys(Keys.RETURN);
		String randomUsername = faker.name().username();
		//System.out.println("random username is : "+randomUsername);		
		ad.elementNewUsername().sendKeys(randomUsername);
		ad.elementNewUserPass().sendKeys("skywalker@123");
		ad.elementNewUserConfirmPass().sendKeys("skywalker@123");
		ad.clickSave();
		Thread.sleep(3000);
		String actualMsg = driver.findElement(By.xpath("//div[2]/div/span[contains(@class, 'oxd-text')]")).getText();
		String expecTedMsg = "Employee name is required";
		if(actualMsg.equals(expecTedMsg))
		{
			Log.info("Correct error message is displayed");
			softAssert.assertTrue(true);
			extentTest.pass("Correct error message is displayed");
		//softAssert.assertEquals(actualMsg, expecTedMsg, "Expected error message is not displayed: ");
		}
		else
		{
			softAssert.assertTrue(false);
			Log.error("Expected error message is not displayed: ");
			String base64Code = screenShotCapture();
			extentTest.fail("Expected error message is not displayed:").info(MediaEntityBuilder.createScreenCaptureFromBase64String(base64Code, "Screenshot Attached").build());
		
		}
		softAssert.assertAll();
	}
	
	@Test
	void validatingAddUserWhenEmployeeNameFieldValuesAreLessThanMinimum() throws InterruptedException
	{
		extentTest = extent.createTest("FieldValidation - Employee name(less than min characters)", "Validate Employee name field by adding less than 5 characters");
		Log.info("Scenario: To validate Employee name field by adding less than 5 characters");
		HomePage hm = new HomePage(driver);
		AdminPage ad = new AdminPage(driver);
		Faker faker = new Faker();
		//openBrowserWithUrl(driver);
		hm.setUsername(validUsername);
		hm.setLoginPass(validPassword);
		hm.clickLogin();
		Thread.sleep(3000);
		ad.clickAdmin();
		//System.out.println("Clicked on admin");
		Thread.sleep(2000);
		ad.clickAddUser();
		Thread.sleep(2000);
		ad.elementNewUserRole().sendKeys("a");
		ad.elementNewUserRole().sendKeys(Keys.RETURN);
		Thread.sleep(3000);
		ad.elementNewEmployeeName().sendKeys("a");
		Thread.sleep(1000);
		//driver.findElement(By.xpath("//div/div[2]/div/div[2]/div/div[2]")).click();
		ad.elementNewUserStatus().sendKeys("e");
		ad.elementNewUserStatus().sendKeys(Keys.RETURN);
		String randomUsername = faker.name().username();
		//System.out.println("random username is : "+randomUsername);		
		ad.elementNewUsername().sendKeys(randomUsername);
		ad.elementNewUserPass().sendKeys("skywalker@123");
		ad.elementNewUserConfirmPass().sendKeys("skywalker@123");
		ad.clickSave();
		Thread.sleep(3000);
		String actualMsg = driver.findElement(By.xpath("//div[2]/div/span[contains(@class, 'oxd-text')]")).getText();
		String expecTedMsg = "Employee name should be minimum of 5 letters and maximum of 100 only";
		//softAssert.assertEquals(actualMsg, expecTedMsg, "Expected error message is not displayed: ");
		if(actualMsg.equals(expecTedMsg))
		{
			Log.info("Correct error message is displayed");
			softAssert.assertTrue(true);
			extentTest.pass("Correct error message is displayed");
		//softAssert.assertEquals(actualMsg, expecTedMsg, "Expected error message is not displayed: ");
		}
		else
		{
			softAssert.assertTrue(false);
			Log.error("Expected error message is not displayed: ");
			String base64Code = screenShotCapture();
			extentTest.fail("Expected error message is not displayed:").info(MediaEntityBuilder.createScreenCaptureFromBase64String(base64Code, "Screenshot Attached").build());
		
		}
		softAssert.assertAll();
		
	}
	
	@Test
	void validatingAddUserWhenUserameFieldIsEmpty() throws InterruptedException
	{
		extentTest = extent.createTest("FieldValidation - empty username", "Validate Username field by keeping it empty when adding new user");
		Log.info("Scenario: To validate Username field by keeping it empty when adding new user");
		HomePage hm = new HomePage(driver);
		AdminPage ad = new AdminPage(driver);
		Faker faker = new Faker();
		//openBrowserWithUrl(driver);
		hm.setUsername(validUsername);
		hm.setLoginPass(validPassword);
		hm.clickLogin();
		Thread.sleep(3000);
		ad.clickAdmin();
		//System.out.println("Clicked on admin");
		Thread.sleep(2000);
		ad.clickAddUser();
		Thread.sleep(2000);
		ad.elementNewUserRole().sendKeys("a");
		ad.elementNewUserRole().sendKeys(Keys.RETURN);
		ad.elementNewEmployeeName().sendKeys("a");
		Thread.sleep(3000);
		driver.findElement(By.xpath("//div/div[2]/div/div[2]/div/div[2]")).click();
		ad.elementNewUserStatus().sendKeys("e");
		ad.elementNewUserStatus().sendKeys(Keys.RETURN);
		ad.elementNewUserPass().sendKeys("skywalker@123");
		ad.elementNewUserConfirmPass().sendKeys("skywalker@123");
		ad.clickSave();
		Thread.sleep(2000);
		String actualError = driver.findElement(By.xpath("//div/div[4]/div/span[contains(@class, \"oxd-text\")]")).getText();
		String expectedError = "User Name is required field";
		if(actualError.equals(expectedError))
		{
			Log.info("Correct error message is displayed");
			softAssert.assertTrue(true);
			extentTest.pass("Correct error message is displayed");
		//softAssert.assertEquals(actualMsg, expecTedMsg, "Expected error message is not displayed: ");
		}
		else
		{
			softAssert.assertTrue(false);
			Log.error("Expected error message is not displayed: ");
			String base64Code = screenShotCapture();
			extentTest.fail("Expected error message is not displayed:").info(MediaEntityBuilder.createScreenCaptureFromBase64String(base64Code, "Screenshot Attached").build());
		
		}
		softAssert.assertAll();
	}
		
		@Test
		void verifyAddUserUsernameFieldValueLimit() throws InterruptedException
		{
			extentTest = extent.createTest("FieldValidation - username field char limit", "validate username field when wntering less than 5 and more than 20 characters");
			Log.info("Scenario: To validate username field when wntering less than 5 and more than 20 characters");
			HomePage hm = new HomePage(driver);
			AdminPage ad = new AdminPage(driver);
			Faker faker = new Faker();
		//	openBrowserWithUrl(driver);
			hm.setUsername(validUsername);
			hm.setLoginPass(validPassword);
			hm.clickLogin();
			Thread.sleep(3000);
			ad.clickAdmin();
			//System.out.println("Clicked on admin");
			Thread.sleep(2000);
			ad.clickAddUser();
			Thread.sleep(2000);
			ad.elementNewUserRole().sendKeys("a");
			ad.elementNewUserRole().sendKeys(Keys.RETURN);
			ad.elementNewEmployeeName().sendKeys("a");
			Thread.sleep(3000);
			driver.findElement(By.xpath("//div/div[2]/div/div[2]/div/div[2]")).click();
			ad.elementNewUserStatus().sendKeys("e");
			ad.elementNewUserStatus().sendKeys(Keys.RETURN);
			String randomUsername = faker.lorem().characters(4);
			//System.out.println("random username is : "+randomUsername);		
			ad.elementNewUsername().sendKeys(randomUsername);
			ad.elementNewUserPass().sendKeys("skywalker@123");
			ad.elementNewUserConfirmPass().sendKeys("skywalker@123");
			ad.clickSave();
			Thread.sleep(2000);
			String actualError = driver.findElement(By.xpath("//div/div[4]/div/span[contains(@class, \"oxd-text\")]")).getText();
			String expectedError = "User Name filed should be minimum of 5 letters and maximum of 20 letters";
			if(actualError.equals(expectedError))
			{
				Log.info("Correct error message is displayed");
				softAssert.assertTrue(true);
				extentTest.pass("Correct error message is displayed");
			//softAssert.assertEquals(actualMsg, expecTedMsg, "Expected error message is not displayed: ");
			}
			else
			{
				softAssert.assertTrue(false);
				Log.error("Expected error message is not displayed: ");
				String base64Code = screenShotCapture();
				extentTest.fail("Expected error message is not displayed:").info(MediaEntityBuilder.createScreenCaptureFromBase64String(base64Code, "Screenshot Attached").build());
			
			}
			
			ad.elementNewUsername().sendKeys(Keys.DELETE);
			String randomUsernameMax = faker.lorem().characters(45);
			ad.elementNewUsername().sendKeys(randomUsernameMax);
			Thread.sleep(1000);
			String actualError2 = driver.findElement(By.xpath("//div/div[4]/div/span[contains(@class, \"oxd-text\")]")).getText();
			String expectedError2 = "User Name filed should be minimum of 5 letters and maximum of 20 letters";
			if(actualError2.equals(expectedError2))
			{
				Log.info("Correct error message is displayed");
				softAssert.assertTrue(true);
				extentTest.pass("Correct error message is displayed");
			//softAssert.assertEquals(actualMsg, expecTedMsg, "Expected error message is not displayed: ");
			}
			else
			{
				softAssert.assertTrue(false);
				Log.error("Expected error message is not displayed: ");
				String base64Code = screenShotCapture();
				extentTest.fail("Expected error message is not displayed:").info(MediaEntityBuilder.createScreenCaptureFromBase64String(base64Code, "Screenshot Attached").build());
			
			}	
			softAssert.assertAll();
			
		}
		
		@Test
		void verfyAddUserUsernameFieldForOnlyAlphaNumeric() throws InterruptedException
		{
			extentTest = extent.createTest("FieldValidation - Username for alphanumeric", "Validate Username field by adding non alphanumeric data");
			Log.info("Scenario: To validate Username field by adding non alphanumeric data");
			HomePage hm = new HomePage(driver);
			AdminPage ad = new AdminPage(driver);
			Faker faker = new Faker();
			//openBrowserWithUrl(driver);
			hm.setUsername(validUsername);
			hm.setLoginPass(validPassword);
			hm.clickLogin();
			Thread.sleep(3000);
			ad.clickAdmin();
			//System.out.println("Clicked on admin");
			Thread.sleep(2000);
			ad.clickAddUser();
			Thread.sleep(2000);
			ad.elementNewUserRole().sendKeys("a");
			ad.elementNewUserRole().sendKeys(Keys.RETURN);
			ad.elementNewEmployeeName().sendKeys("a");
			Thread.sleep(3000);
			driver.findElement(By.xpath("//div/div[2]/div/div[2]/div/div[2]")).click();
			ad.elementNewUserStatus().sendKeys("e");
			ad.elementNewUserStatus().sendKeys(Keys.RETURN);
			String randomExpression = faker.regexify("[&^$%#!)(?]");
			String randomUsername = "%^%$#&@"+randomExpression;
			//System.out.println(randomUsername);
			//System.out.println("random username is : "+randomUsername);		
			ad.elementNewUsername().sendKeys(randomUsername);
			ad.elementNewUserPass().sendKeys("skywalker@123");
			ad.elementNewUserConfirmPass().sendKeys("skywalker@123");
			ad.clickSave();
			Thread.sleep(2000);
			String pageSourceAfterSaving = driver.getPageSource();
			if(pageSourceAfterSaving.contains("User Name field takes only alpha numeric data"))
			{
				Log.info("Expected error message for non Alphanumeric data entry is displayed");
				softAssert.assertTrue(true);
				extentTest.pass("Expected error message for non Alphanumeric data entry is displayed");
			}
			else
			{
				Log.error("Expected error message for non Alphanumeric data entry is not displayed");
				softAssert.assertTrue(false);
				String base64Code = screenShotCapture();
				extentTest.fail("Expected error message for non Alphanumeric data entry is not displayed").info(MediaEntityBuilder.createScreenCaptureFromBase64String(base64Code, "Screenshot Attached").build());
			}
			softAssert.assertAll();
		}

}

