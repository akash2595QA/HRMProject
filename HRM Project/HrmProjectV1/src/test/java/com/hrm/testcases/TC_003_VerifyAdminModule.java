package com.hrm.testcases;
import org.testng.annotations.Test;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.github.javafaker.Faker;
import com.hrm.baseclass.*;
import com.hrm.pages.*;
import com.hrm.utilities.Log;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class TC_003_VerifyAdminModule extends BaseClass
{
	@Test
	void verifyAdminPageContents() throws InterruptedException
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		extentTest = extent.createTest("AdminPage - Tabs", "Verify approriate tabs are present on the Admin page");
		Log.info("Scenario: To verify approriate tabs are present on the Admin page");
		HomePage hm = new HomePage(driver);
		AdminPage ad = new AdminPage(driver);
		//openBrowserWithUrl(driver);
		hm.setUsername(validUsername);
		hm.setLoginPass(validPassword);
		hm.clickLogin();
		wait.until(ExpectedConditions.elementToBeClickable(ad.elementAdmin()));
		ad.clickAdmin();
		Thread.sleep(2000);
		String[] strArr = {"User Management", "Job", "Organization", "Qualifications", "Nationalities", "Corporate Branding", "Configuration"};
		boolean status = checkPageSourceContents(driver, strArr);
		if(status==true)
		{
			Log.info("Admin module contains all the features");
			softAssert.assertTrue(true);
			extentTest.pass("Admin module contains all the features");
		}
		else
		{
			Log.error("Admin module does not contain all the features");
			softAssert.assertTrue(false);
			String base64Code = screenShotCapture();
			extentTest.fail("Admin module does not contain all the features").info(MediaEntityBuilder.createScreenCaptureFromBase64String(base64Code, "Screenshot Attached").build());			
		}
		
		softAssert.assertAll();
	}
	
	@Test
	void verifySearchResultWithExistingUsername() throws InterruptedException
	{
		extentTest = extent.createTest("AdminPage - Search Existing Username", "Verify search results for existing username search");
		Log.info("Scenario: To verify search results for existing username search");
		HomePage hm = new HomePage(driver);
		AdminPage ad = new AdminPage(driver);
		//openBrowserWithUrl(driver);
		hm.setUsername(validUsername);
		hm.setLoginPass(validPassword);
		hm.clickLogin();
		Thread.sleep(2000);
		ad.clickAdmin();
		Thread.sleep(2000);
		ad.setSysUsername("Admin");
		ad.clickSearchBtn();
		scrollTillEnd();
		//String searchResultScript = "return document.getElementsByClassName('orangehrm-container').innerHTML;";
		//String searchResultsHTML = (String) js.executeScript(searchResultScript);
		//System.out.println(searchResultsHTML.contains("Admin"));
		Thread.sleep(2000);
		String searchResults = driver.findElement(By.xpath("//div[@class='orangehrm-container']")).getAttribute("outerHTML");
		if(searchResults.contains("Admin")==true)
		{
			softAssert.assertTrue(true);
			Log.info("Correct search results are displayed");
			extentTest.pass("Correct search results are displayed");
		}
		else
		{
			softAssert.assertTrue(false);
			Log.error("Incorrect search results are displayed");
			String base64Code = screenShotCapture();
			extentTest.fail("Incorrect search results are displayed").info(MediaEntityBuilder.createScreenCaptureFromBase64String(base64Code, "Screenshot Attached").build());
		}
			softAssert.assertAll();		
	}
	
	@Test
	void verifyUserRoleOptions() throws InterruptedException
	{
		extentTest = extent.createTest("AdminPage - Verify options in User role", "Verify the options present in User Role drop down menu");
		Log.info("Scenario: To verify the options present in User Role drop down menu");
		HomePage hm = new HomePage(driver);
		AdminPage ad = new AdminPage(driver);
		//openBrowserWithUrl(driver);
		hm.setUsername(validUsername);
		hm.setLoginPass(validPassword);
		hm.clickLogin();
		Thread.sleep(2000);
		ad.clickAdmin();
		//System.out.println("Clicked on admin");
		Thread.sleep(2000);
		ad.clickUserRole();
		WebElement dropDownContent = driver.findElement(By.xpath("//*[contains(@class, 'oxd-select-dropdown')]"));
		String dropDownText = dropDownContent.getText();
		//System.out.println(dropDownText);
		boolean AdminPresent = false;
		boolean ESSPresent = false;
		if(dropDownText.contains("Admin"))
		{
			AdminPresent=true;
		}
		if(dropDownText.contains("ESS"))
		{
			ESSPresent=true;
		}
		
		if(AdminPresent && ESSPresent)
		{
			softAssert.assertTrue(true);
			Log.info("Both the options are present");
			extentTest.pass("Both the options are present");
		}
		else
		{
			softAssert.assertTrue(false);
			Log.error("Options are not present");
			String base64Code = screenShotCapture();
			extentTest.fail("Options are not present").info(MediaEntityBuilder.createScreenCaptureFromBase64String(base64Code, "Screenshot Attached").build());
		}
		softAssert.assertAll();
	}
	
	@Test
	void verifyResultsForUserRoleAdmin() throws InterruptedException //Here We verify that the results shown on choosing Admin user role contains only Admin user role entries
	{
		extentTest = extent.createTest("AdminPage - Search with User role(Admin)", "Verify search results by selecting Admin option from User Role menu");
		Log.info("Scenario: To verify search results by selecting Admin option from User Role menu");
		HomePage hm = new HomePage(driver);
		AdminPage ad = new AdminPage(driver);
		//openBrowserWithUrl(driver);
		hm.setUsername(validUsername);
		hm.setLoginPass(validPassword);
		hm.clickLogin();
		Thread.sleep(2000);
		ad.clickAdmin();
		//System.out.println("Clicked on admin");
		Thread.sleep(2000);
		//ad.clickUserRole();
		WebElement select = driver.findElement(By.xpath("//form/div[1]/div/div[2]/div/div[2]/div/div/div[1][@class='oxd-select-text-input']"));
		select.sendKeys("admin");
		select.sendKeys(Keys.RETURN);
		ad.clickSearchBtn();
		Thread.sleep(2000);
		String searchResults = driver.findElement(By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div[2]/div[3]/div")).getAttribute("outerHTML");
		if(searchResults.contains("ESS"))
		{
			Log.error("Results are not correct(Results for ESS roles also present which is unexpected)");
			softAssert.assertTrue(false);
			String base64Code = screenShotCapture();
			extentTest.fail("Results are not correct(Results for ESS roles also present which is unexpected)").info(MediaEntityBuilder.createScreenCaptureFromBase64String(base64Code, "Screenshot Attached").build());
		}
		else
		{
			Log.info("Results are correct");
			softAssert.assertTrue(true);
			extentTest.pass("Results are correct");
		}	
		softAssert.assertAll();
		
	}
	
	@Test
	void verifyResultsForUserRoleESS() throws InterruptedException //Here We verify that the results shown on choosing Admin user role contains only Admin user role entries
	{
		extentTest = extent.createTest("AdminPage - Search with User role(ESS)", "Verify search results by selecting ESS option from User Role menu");
		Log.info("Scenario: To verify search results by selecting ESS option from User Role menu");
		HomePage hm = new HomePage(driver);
		AdminPage ad = new AdminPage(driver);
		//openBrowserWithUrl(driver);
		hm.setUsername(validUsername);
		hm.setLoginPass(validPassword);
		hm.clickLogin();
		Thread.sleep(2000);
		ad.clickAdmin();
		//System.out.println("Clicked on admin");
		Thread.sleep(2000);
		//ad.clickUserRole();
		WebElement select = driver.findElement(By.xpath("//form/div[1]/div/div[2]/div/div[2]/div/div/div[1][@class='oxd-select-text-input']"));
		select.sendKeys("ess");
		select.sendKeys(Keys.RETURN);
		ad.clickSearchBtn();
		Thread.sleep(2000);
		String searchResults = driver.findElement(By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div[2]/div[3]/div")).getAttribute("outerHTML");
		if(searchResults.contains("Admin"))
		{
			Log.error("Results are not correct(Results for Admin roles are also present which is unexpected)");
			softAssert.assertTrue(false);
			String base64Code = screenShotCapture();
			extentTest.fail("Results are not correct(Results for Admin roles are also present which is unexpected)").info(MediaEntityBuilder.createScreenCaptureFromBase64String(base64Code, "Screenshot Attached").build());
		}
		else
		{
			Log.info("Results are correct");
			softAssert.assertTrue(true);
			extentTest.pass("Screenshot is as expected");
		}	
		softAssert.assertAll();
		
	}
	
	@Test
	void verifyExistingEmployeeNameRecords() throws InterruptedException
	{
		extentTest = extent.createTest("AdminPage - Search Existing Employee name", "Verify search results for existing Employee Name");
		Log.info("Scenario: To verify search results for existing Employee Name");
		HomePage hm = new HomePage(driver);
		AdminPage ad = new AdminPage(driver);
		//openBrowserWithUrl(driver);
		hm.setUsername(validUsername);
		hm.setLoginPass(validPassword);
		hm.clickLogin();
		Thread.sleep(2000);
		ad.clickAdmin();
		//System.out.println("Clicked on admin");
		Thread.sleep(2000);
		//enter existing employee name in Employee Name field
		String employeeNameEntered = "Paul Collings";
		ad.setEmployeeName(employeeNameEntered);
		//select first suggestion  
		Thread.sleep(8000);
		driver.findElement(By.xpath("//div/div[3]/div/div[2]/div[@class='oxd-autocomplete-wrapper']/div[2]")).click();
		Thread.sleep(1000);
		ad.clickSearchBtn();
		Thread.sleep(1000);
		String eNameResult = driver.findElement(By.xpath("//div/div[2]/div/div/div[4]/div")).getText();
		if(eNameResult.equals(employeeNameEntered))
		{
			Log.info("Correct record for the Employee name is displayed");
			softAssert.assertTrue(true);
			extentTest.pass("Correct record for the Employee name is displayed");
		}
		else
		{
			Log.error("No record found");
			softAssert.assertTrue(false);
			String base64Code = screenShotCapture();
			extentTest.fail("No record found").info(MediaEntityBuilder.createScreenCaptureFromBase64String(base64Code, "Screenshot Attached").build());
		}
			
	}
	
	@Test
	void verifyNonExistingEmployeeNameRecords() throws InterruptedException
	{
		extentTest = extent.createTest("AdminPage - Search Non existing Employee name", "Verify search results for non exisitng Employee name");
		Log.info("Scenario: To verify search results for non exisitng Employee name");
		//Search for some employee name that does not exist in the record
		HomePage hm = new HomePage(driver);
		AdminPage ad = new AdminPage(driver);
		//openBrowserWithUrl(driver);
		hm.setUsername(validUsername);
		hm.setLoginPass(validPassword);
		hm.clickLogin();
		Thread.sleep(2000);
		ad.clickAdmin();
		//System.out.println("Clicked on admin");
		Thread.sleep(2000);
		//enter existing employee name in Employee Name field
		String employeeNameEntered = "nonexisting";
		ad.setEmployeeName(employeeNameEntered);
		Thread.sleep(1000);
		ad.clickSearchBtn();
		Thread.sleep(1000);
	//	System.out.println("Record text: "+ad.getRecordTxt());
		if(ad.getRecordTxt().equals("No Record Found"))
				{
					Log.info("Output is as expected");
					softAssert.assertTrue(true);
					extentTest.pass("Output is as expected");
				}
		else
		{
			Log.error("Output is not as expected, it should be 'No Record Found'");
			softAssert.assertTrue(false);
			String base64Code = screenShotCapture();
			extentTest.fail("Output is not as expected, it should be 'No Record Found'").info(MediaEntityBuilder.createScreenCaptureFromBase64String(base64Code, "Screenshot Attached").build());
		}
		captureScreenshot(driver, "searchNonExistingEmployee_failed");
		softAssert.assertAll();
		
	}
	
	@Test
	void verifyStatusContentsInDropDown() throws InterruptedException
	{
		extentTest = extent.createTest("AdminPage - Options in 'Status' menu", "Verify options in Status drop down menu");
		Log.info("Scenario: To verify options in Status drop down menu");
		HomePage hm = new HomePage(driver);
		AdminPage ad = new AdminPage(driver);
		//openBrowserWithUrl(driver);
		hm.setUsername(validUsername);
		hm.setLoginPass(validPassword);
		hm.clickLogin();
		Thread.sleep(3000);
		ad.clickAdmin();
		//System.out.println("Clicked on admin");
		Thread.sleep(2000);
		ad.clickUserStatus();
		Thread.sleep(3000);
		String statusDropDownContents = driver.findElement(By.xpath("//form/div[1]/div/div[4]/div/div[2]/div/div[2]")).getAttribute("outerHTML");
		boolean enabledPresent = false;
		boolean disabledPresent = false;
		if(statusDropDownContents.contains("Enabled"))
		{
			enabledPresent=true;
		}
		if(statusDropDownContents.contains("Disabled"))
		{
			enabledPresent=true;;
		}
		if(enabledPresent && enabledPresent)
		{
			Log.info("Both the status options are present in the dropdown");
			softAssert.assertTrue(true);
			extentTest.pass("Both the status options are present in the dropdown");
		}
		else
		{
			Log.error("Options are missing in the dropdown");
			softAssert.assertTrue(false);
			String base64Code = screenShotCapture();
			extentTest.fail("Options are missing in the dropdown").info(MediaEntityBuilder.createScreenCaptureFromBase64String(base64Code, "Screenshot Attached").build());
		}		
	}
	
	@Test
	void verifyResultsForStatusEnabled() throws InterruptedException
	{
		extentTest = extent.createTest("AdminPage - Status 'Enabled' search results", "Verify 'Enabled' option in Status drop down menu");
		Log.info("Scenario: To verify 'Enabled' option in Status drop down menu");
		HomePage hm = new HomePage(driver);
		AdminPage ad = new AdminPage(driver);
		//openBrowserWithUrl(driver);
		hm.setUsername(validUsername);
		hm.setLoginPass(validPassword);
		hm.clickLogin();
		Thread.sleep(3000);
		ad.clickAdmin();
		//System.out.println("Clicked on admin");
		Thread.sleep(2000);
		ad.clickUserStatus();
		Thread.sleep(3000);
		//WebElement statusDropDown = driver.findElement(By.xpath("//form/div[1]/div/div[4]/div/div[2]/div/div[2]"));
		ad.userStatusDropDownElement().sendKeys("e"); //enabled will be selected
		ad.userStatusDropDownElement().sendKeys(Keys.RETURN);
		Thread.sleep(1000);
		ad.clickSearchBtn();	
		Thread.sleep(3000);
		String searchResults = driver.findElement(By.xpath("//div/div[2]/div[3][@class='orangehrm-container']")).getAttribute("outerHTML");
		if(searchResults.contains("Disabled"))
		{
			Log.error("Results are not correct(Results for 'Disabled' status are also present which is unexpected)");
			softAssert.assertTrue(false);
			String base64Code = screenShotCapture();
			extentTest.fail("Results are not correct(Results for 'Disabled' status are also present which is unexpected)").info(MediaEntityBuilder.createScreenCaptureFromBase64String(base64Code, "Screenshot Attached").build());
		}
		else if(!searchResults.contains("Enabled") && !searchResults.contains("Disabled"))
		{
			Log.info("No Record Found");
			softAssert.assertTrue(true);
			extentTest.pass("No Record Found");
		}		
		else
		{
			Log.info("Results are correct");
			softAssert.assertTrue(true);
			extentTest.pass("Results are correct");
		}	
		softAssert.assertAll();
	}
	
	@Test
	void verifyResultsForStatusDisabled() throws InterruptedException
	{
		extentTest = extent.createTest("AdminPage - Status 'Disabled' search results", "Verify 'Disabled' option in Status drop down menu");
		Log.info("Scenario: To verify 'Disabled' option in Status drop down menu");
		HomePage hm = new HomePage(driver);
		AdminPage ad = new AdminPage(driver);
		//openBrowserWithUrl(driver);
		hm.setUsername(validUsername);
		hm.setLoginPass(validPassword);
		hm.clickLogin();
		Thread.sleep(3000);
		ad.clickAdmin();
		//System.out.println("Clicked on admin");
		Thread.sleep(2000);
		ad.clickUserStatus();
		Thread.sleep(3000);
		ad.userStatusDropDownElement().sendKeys("d"); //disabled will be selected
		Thread.sleep(2000);
		ad.userStatusDropDownElement().sendKeys(Keys.RETURN);
		Thread.sleep(1000);
		ad.clickSearchBtn();	
		Thread.sleep(3000);
		String searchResults = driver.findElement(By.xpath("//div/div[2]/div[3][@class='orangehrm-container']")).getAttribute("outerHTML");
		if(searchResults.contains("Enabled"))
		{
			Log.error("Results are not correct(Results for 'Enabled' status are also present which is unexpected)");
			softAssert.assertTrue(false);
			String base64Code = screenShotCapture();
			extentTest.fail("Results are not correct(Results for 'Enabled' status are also present which is unexpected)").info(MediaEntityBuilder.createScreenCaptureFromBase64String(base64Code, "Screenshot Attached").build());
		}
		else if(!searchResults.contains("Enabled") && !searchResults.contains("Disabled"))
		{
			Log.info("No Record Found");
			softAssert.assertTrue(true);
			extentTest.pass("No Record Found");
		}
		else
		{
			Log.info("Results are correct");
			softAssert.assertTrue(true);
			extentTest.pass("Results are correct");
		}	
		softAssert.assertAll();
	}
	
	@Test
	void compareAddUserScreenshot() throws InterruptedException
	{
		extentTest = extent.createTest("AdminPage - Page after click on addUser", "Compare the screenshots after clicking on Add user");
		Log.info("Scenario: Compare the screenshots after clicking on Add user");
		HomePage hm = new HomePage(driver);
		AdminPage ad = new AdminPage(driver);
		//openBrowserWithUrl(driver);
		hm.setUsername(validUsername);
		hm.setLoginPass(validPassword);
		hm.clickLogin();
		Thread.sleep(3000);
		ad.clickAdmin();
	//	System.out.println("Clicked on admin");
		Thread.sleep(2000);
		ad.clickAddUser();
		Thread.sleep(2000);
		captureScreenshot(driver, "actualAddUserSC");
		Thread.sleep(2000);
		boolean compareStatus = compareScreenshots(driver, "actualAddUserSC", "AdminAddUserESC");
		if(compareStatus==true)
		{
			Log.error("The Expected and Actual screenshots are different");
			softAssert.assertTrue(false);
			String base64Code = screenShotCapture();
			extentTest.fail("The Expected and Actual screenshots are different").info(MediaEntityBuilder.createScreenCaptureFromBase64String(base64Code, "Screenshot Attached").build());
		}
		else
		{
			Log.info("addUser Screenshots are as expected ");
			softAssert.assertTrue(true);
			extentTest.pass("addUser Screenshots are as expected");
		}
		softAssert.assertAll();
		
	}
	
	@Test
	void verifyStatusAfterAddingNewUser() throws InterruptedException
	{
		extentTest = extent.createTest("AdminPage - Add new user sucessfully", "Verify status after adding new user successfully");
		Log.info("Scenario: To verify status after adding new user successfully");
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
		String randomUsername = faker.name().username();
		//System.out.println("random username is : "+randomUsername);		
		ad.elementNewUsername().sendKeys(randomUsername);
		ad.elementNewUserPass().sendKeys("skywalker@123");
		ad.elementNewUserConfirmPass().sendKeys("skywalker@123");
		ad.clickSave();
		boolean successFullySavedStatus=false ;
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				String pageAfterAddingUser1 = driver.getPageSource();
				return pageAfterAddingUser1.contains("Successfully Saved");
			}
		}
		);
		String pageAfterAddingUser = driver.getPageSource();
		//System.out.println(pageAfterAddingUser);
		if(pageAfterAddingUser.contains("Successfully Saved"))
		{
			Log.info("Successfully Saved");
			softAssert.assertTrue(true);
			extentTest.pass("Successfully Saved");
		}
		else
		{
			Log.error("Not saved");
			softAssert.assertTrue(false);
			String base64Code = screenShotCapture();
			extentTest.fail("User not saved").info(MediaEntityBuilder.createScreenCaptureFromBase64String(base64Code, "Screenshot Attached").build());
		}
		softAssert.assertAll();
	}
	
	@Test
	void verifyByAddingNewDuplicateUsername() throws InterruptedException
	{
		extentTest = extent.createTest("AdminPage - add duplicate user", "Verify error mesage when trying to create a duplicate user");
		Log.info("Scenario: To verify error mesage when trying to create a duplicate user");
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
		driver.findElement(By.xpath("//div/div[2]/div/div[2]/div/div[2]")).click();//selecting first suggestion that appears
		ad.elementNewUserStatus().sendKeys("e");
		ad.elementNewUserStatus().sendKeys(Keys.RETURN);
		String randomUsername = faker.name().username();
		ad.elementNewUsername().sendKeys(randomUsername);
		ad.elementNewUserPass().sendKeys("skywalker@123");
		ad.elementNewUserConfirmPass().sendKeys("skywalker@123");
		ad.clickSave();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(ad.elementAddUser()));
		ad.clickAddUser();
		Thread.sleep(2000);
		ad.elementNewUserRole().sendKeys("a");
		ad.elementNewUserRole().sendKeys(Keys.RETURN);
		ad.elementNewEmployeeName().sendKeys("a");
		Thread.sleep(3000);
		driver.findElement(By.xpath("//div/div[2]/div/div[2]/div/div[2]")).click();//selecting first suggestion that appears
		ad.elementNewUserStatus().sendKeys("e");
		ad.elementNewUserStatus().sendKeys(Keys.RETURN);
		ad.elementNewUsername().sendKeys(randomUsername);//username
		ad.elementNewUserPass().sendKeys("skywalker@123");
		ad.elementNewUserConfirmPass().sendKeys("skywalker@123");
		ad.clickSave();
		Thread.sleep(2000);
		String duplicateUsernameExistsTxt = driver.findElement(By.xpath("//div[4]/div/span[contains(@class, 'oxd-text' )]")).getText();
		if (duplicateUsernameExistsTxt.equals("User already exist"))
		{
			Log.info("Correct error msg for duplicate username is printed");
			softAssert.assertTrue(true);
			extentTest.pass("Correct error msg for duplicate username is printed");
		}
		else
		{
			Log.error("Expected error msg is 'User Already Exists' but printed error msg is '"+duplicateUsernameExistsTxt+"'");
			softAssert.assertTrue(false);
			String base64Code = screenShotCapture();
			extentTest.fail("Expected error msg is 'User Already Exists' but printed error msg is"+duplicateUsernameExistsTxt+"'").info(MediaEntityBuilder.createScreenCaptureFromBase64String(base64Code, "Screenshot Attached").build());
		}
		softAssert.assertAll();
		
	}
	
	@Test
	void deleteUser() throws InterruptedException
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		extentTest = extent.createTest("AdminPage - Delete User", "Verify status after successfully deleting user");
		Log.info("Scenario: To verify status after successfully deleting user");
		HomePage hm = new HomePage(driver);
		AdminPage ad = new AdminPage(driver);
		Faker faker = new Faker();
		//openBrowserWithUrl(driver);
		hm.setUsername(validUsername);
		hm.setLoginPass(validPassword);
		hm.clickLogin();
		wait.until(ExpectedConditions.elementToBeClickable(ad.elementAdmin()));
		ad.clickAdmin();
		//System.out.println("Clicked on admin");
		Thread.sleep(2000);
		ad.clickAddUser();
		Thread.sleep(2000);
		ad.elementNewUserRole().sendKeys("a");
		ad.elementNewUserRole().sendKeys(Keys.RETURN);
		ad.elementNewEmployeeName().sendKeys("a");
		Thread.sleep(3000);
		driver.findElement(By.xpath("//div/div[2]/div/div[2]/div/div[2]")).click();//selecting first suggestion that appears
		ad.elementNewUserStatus().sendKeys("e");
		ad.elementNewUserStatus().sendKeys(Keys.RETURN);
		String randomUsername = faker.name().username();
		ad.elementNewUsername().sendKeys(randomUsername);
		ad.elementNewUserPass().sendKeys("skywalker@123");
		ad.elementNewUserConfirmPass().sendKeys("skywalker@123");
		ad.clickSave();
		//WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		wait.until(ExpectedConditions.elementToBeClickable(ad.elementAddUser()));
		//System.out.println("After wait time");
		ad.setSysUsername(randomUsername);
		//System.out.println("searched username");
		ad.clickSearchBtn();
		wait.until(ExpectedConditions.elementToBeClickable(ad.elementDeleteUserBtn()));
		ad.clickDeleteUserBtn();
	//	Alert alert = driver.switchTo().alert();
	//	alert.accept();
		Thread.sleep(1000);
		ad.clickAlertDeleteBtn();
		wait.until(new ExpectedCondition<Boolean>() {

			@Override
			public Boolean apply(WebDriver driver) {
				String getPageSourceAfterDelete = driver.getPageSource();
				return getPageSourceAfterDelete.contains("Successfully Deleted");
			}
		});
		
		String getPageSourceAfterDelete = driver.getPageSource();
		String base64Code = screenShotCapture();
		if(getPageSourceAfterDelete.contains("Successfully Deleted") == true)
		{
			Log.info("correct 'Successfully deleted' confirmation msg is printed");
			softAssert.assertTrue(true);
			extentTest.pass("correct 'Successfully deleted' confirmation msg is printed").info(MediaEntityBuilder.createScreenCaptureFromBase64String(base64Code, "Screenshot Attached").build());;
		}
		else
		{
			Log.error("'Successfull deleted' acknowledgement msg is not printed, probably user was not deleted successfully");
			softAssert.assertTrue(false);
			extentTest.fail("'Successfull deleted' acknowledgement msg is not printed, probably user was not deleted successfully").info(MediaEntityBuilder.createScreenCaptureFromBase64String(base64Code, "Screenshot Attached").build());
		}
			
			softAssert.assertAll();
		}
	
	

}

