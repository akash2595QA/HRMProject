package com.hrm.testcases;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;
import com.hrm.baseclass.*;
import com.hrm.pages.*;

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
		HomePage hm = new HomePage(driver);
		AdminPage ad = new AdminPage(driver);
		//openBrowserWithUrl(driver);
		hm.setUsername(validUsername);
		hm.setLoginPass(validPassword);
		hm.clickLogin();
		Thread.sleep(2000);
		ad.clickAdmin();
		Thread.sleep(2000);
		String[] strArr = {"User Management", "Job", "Organization", "Qualifications", "Nationalities", "Corporate Branding", "Configuration"};
		boolean status = checkPageSourceContents(driver, strArr);
		if(status==true)
		{
			System.out.println("Admin module contains all the features");
		}
		else
		{
			System.out.println("Admin module does not contain all the features");
		}
		softAssert.assertTrue(status);
		softAssert.assertAll();
	}
	
	@Test
	void verifySearchResultWithExistingUsername() throws InterruptedException
	{
		HomePage hm = new HomePage(driver);
		AdminPage ad = new AdminPage(driver);
		//openBrowserWithUrl(driver);
		hm.setUsername(validUsername);
		hm.setLoginPass(validPassword);
		hm.clickLogin();
		Thread.sleep(2000);
		ad.clickAdmin();
		System.out.println("Clicked on admin");
		Thread.sleep(2000);
		ad.setSysUsername("Admin");
		ad.clickSearchBtn();
		System.out.println("Searching done for user");
		scrollTillEnd();
		//String searchResultScript = "return document.getElementsByClassName('orangehrm-container').innerHTML;";
		//String searchResultsHTML = (String) js.executeScript(searchResultScript);
		//System.out.println(searchResultsHTML.contains("Admin"));
		Thread.sleep(2000);
		String searchResults = driver.findElement(By.xpath("//div[@class='orangehrm-container']")).getAttribute("outerHTML");
		if(searchResults.contains("Admin")==true)
		{
			softAssert.assertTrue(true);
		}
		else
			softAssert.assertTrue(false);
			softAssert.assertAll();		
	}
	
	@Test
	void verifyUserRoleOptions() throws InterruptedException
	{
		HomePage hm = new HomePage(driver);
		AdminPage ad = new AdminPage(driver);
		//openBrowserWithUrl(driver);
		hm.setUsername(validUsername);
		hm.setLoginPass(validPassword);
		hm.clickLogin();
		Thread.sleep(2000);
		ad.clickAdmin();
		System.out.println("Clicked on admin");
		Thread.sleep(2000);
		ad.clickUserRole();
		WebElement dropDownContent = driver.findElement(By.xpath("//*[contains(@class, 'oxd-select-dropdown')]"));
		String dropDownText = dropDownContent.getText();
		System.out.println(dropDownText);
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
			System.out.println("Both the options are present");
		}
		else
		{
			softAssert.assertTrue(false);
			System.out.println("Options are not present");
		}
		softAssert.assertAll();
	}
	
	@Test
	void verifyUserRoleForUserRoleAdmin() throws InterruptedException //Here We verify that the results shown on choosing Admin user role contains only Admin user role entries
	{
		HomePage hm = new HomePage(driver);
		AdminPage ad = new AdminPage(driver);
		//openBrowserWithUrl(driver);
		hm.setUsername(validUsername);
		hm.setLoginPass(validPassword);
		hm.clickLogin();
		Thread.sleep(2000);
		ad.clickAdmin();
		System.out.println("Clicked on admin");
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
			System.out.println("Results are not correct(Results for ESS roles also present which is unexpected)");
			softAssert.assertTrue(false);
		}
		else
		{
			System.out.println("Results are correct");
			softAssert.assertTrue(true);
		}	
		softAssert.assertAll();
		
	}
	
	@Test
	void verifyResultsForUserRoleESS() throws InterruptedException //Here We verify that the results shown on choosing Admin user role contains only Admin user role entries
	{
		HomePage hm = new HomePage(driver);
		AdminPage ad = new AdminPage(driver);
		//openBrowserWithUrl(driver);
		hm.setUsername(validUsername);
		hm.setLoginPass(validPassword);
		hm.clickLogin();
		Thread.sleep(2000);
		ad.clickAdmin();
		System.out.println("Clicked on admin");
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
			System.out.println("Results are not correct(Results for Admin roles are also present which is unexpected)");
			softAssert.assertTrue(false);
		}
		else
		{
			System.out.println("Results are correct");
			softAssert.assertTrue(true);
		}	
		softAssert.assertAll();
		
	}
	
	@Test
	void verifyExistingEmployeeNameRecords() throws InterruptedException
	{
		HomePage hm = new HomePage(driver);
		AdminPage ad = new AdminPage(driver);
		//openBrowserWithUrl(driver);
		hm.setUsername(validUsername);
		hm.setLoginPass(validPassword);
		hm.clickLogin();
		Thread.sleep(2000);
		ad.clickAdmin();
		System.out.println("Clicked on admin");
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
			System.out.println("Correct record for the Employee name is displayed");
			softAssert.assertTrue(true);
		}
		else
		{
			System.out.println("No record found");
			softAssert.assertTrue(false);
		}
			
	}
	
	@Test
	void verifyNonExistingEmployeeNameRecords() throws InterruptedException
	{
		//Search for some employee name that does not exist in the record
		HomePage hm = new HomePage(driver);
		AdminPage ad = new AdminPage(driver);
		//openBrowserWithUrl(driver);
		hm.setUsername(validUsername);
		hm.setLoginPass(validPassword);
		hm.clickLogin();
		Thread.sleep(2000);
		ad.clickAdmin();
		System.out.println("Clicked on admin");
		Thread.sleep(2000);
		//enter existing employee name in Employee Name field
		String employeeNameEntered = "nonexisting";
		ad.setEmployeeName(employeeNameEntered);
		Thread.sleep(1000);
		ad.clickSearchBtn();
		Thread.sleep(1000);
		System.out.println("Record text: "+ad.getRecordTxt());
		if(ad.getRecordTxt().equals("No Record Found"))
				{
					System.out.println("Output is as expected");
					softAssert.assertTrue(true);
				}
		else
		{
			System.out.println("Output is not as expected, it should be 'No Record Found'");
			softAssert.assertTrue(false);
		}
		captureScreenshot(driver, "searchNonExistingEmployee_failed");
		softAssert.assertAll();
		
	}
	
	@Test
	void verifyStatusContentsInDropDown() throws InterruptedException
	{
		HomePage hm = new HomePage(driver);
		AdminPage ad = new AdminPage(driver);
		//openBrowserWithUrl(driver);
		hm.setUsername(validUsername);
		hm.setLoginPass(validPassword);
		hm.clickLogin();
		Thread.sleep(3000);
		ad.clickAdmin();
		System.out.println("Clicked on admin");
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
			System.out.println("Both the status options are present in the dropdown");
			softAssert.assertTrue(true);
		}
		else
		{
			System.out.println("Options are missing in the dropdown");
			softAssert.assertTrue(false);
		}		
	}
	
	@Test
	void verifyResultsForStatusEnabled() throws InterruptedException
	{
		HomePage hm = new HomePage(driver);
		AdminPage ad = new AdminPage(driver);
		//openBrowserWithUrl(driver);
		hm.setUsername(validUsername);
		hm.setLoginPass(validPassword);
		hm.clickLogin();
		Thread.sleep(3000);
		ad.clickAdmin();
		System.out.println("Clicked on admin");
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
			System.out.println("Results are not correct(Results for 'Disabled' status are also present which is unexpected)");
			softAssert.assertTrue(false);
		}
		else if(!searchResults.contains("Enabled") && !searchResults.contains("Disabled"))
		{
			System.out.println("No Record Found");
			softAssert.assertTrue(true);
		}		
		else
		{
			System.out.println("Results are correct");
			softAssert.assertTrue(true);
		}	
		softAssert.assertAll();
	}
	
	@Test
	void verifyResultsForStatusDisabled() throws InterruptedException
	{
		HomePage hm = new HomePage(driver);
		AdminPage ad = new AdminPage(driver);
		//openBrowserWithUrl(driver);
		hm.setUsername(validUsername);
		hm.setLoginPass(validPassword);
		hm.clickLogin();
		Thread.sleep(3000);
		ad.clickAdmin();
		System.out.println("Clicked on admin");
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
			System.out.println("Results are not correct(Results for 'Enabled' status are also present which is unexpected)");
			softAssert.assertTrue(false);
		}
		else if(!searchResults.contains("Enabled") && !searchResults.contains("Disabled"))
		{
			System.out.println("No Record Found");
			softAssert.assertTrue(true);
		}
		else
		{
			System.out.println("Results are correct");
			softAssert.assertTrue(true);
		}	
		softAssert.assertAll();
	}
	
	@Test
	void compareAddUserScreenshot() throws InterruptedException
	{
		HomePage hm = new HomePage(driver);
		AdminPage ad = new AdminPage(driver);
		//openBrowserWithUrl(driver);
		hm.setUsername(validUsername);
		hm.setLoginPass(validPassword);
		hm.clickLogin();
		Thread.sleep(3000);
		ad.clickAdmin();
		System.out.println("Clicked on admin");
		Thread.sleep(2000);
		ad.clickAddUser();
		Thread.sleep(2000);
		captureScreenshot(driver, "actualAddUserSC");
		Thread.sleep(2000);
		boolean compareStatus = compareScreenshots(driver, "actualAddUserSC", "AdminAddUserESC");
		if(compareStatus==true)
		{
			System.out.println("The Expected and Actual screenshots are different");
			softAssert.assertTrue(false);
		}
		else
		{
			System.out.println("addUser Screenshots are as expected ");
			softAssert.assertTrue(true);
		}
		softAssert.assertAll();
		
	}
	
	@Test
	void verifyStatusAfterAddingNewUser() throws InterruptedException
	{
		HomePage hm = new HomePage(driver);
		AdminPage ad = new AdminPage(driver);
		Faker faker = new Faker();
		//openBrowserWithUrl(driver);
		hm.setUsername(validUsername);
		hm.setLoginPass(validPassword);
		hm.clickLogin();
		Thread.sleep(3000);
		ad.clickAdmin();
		System.out.println("Clicked on admin");
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
		System.out.println("random username is : "+randomUsername);		
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
			System.out.println("Successfully Saved");
			softAssert.assertTrue(true);
		}
		else
		{
			System.out.println("Not saved");
			softAssert.assertTrue(false);
		}
		softAssert.assertAll();
	}
	
	@Test
	void verifyByAddingNewDuplicateUsername() throws InterruptedException
	{
		HomePage hm = new HomePage(driver);
		AdminPage ad = new AdminPage(driver);
		Faker faker = new Faker();
		//openBrowserWithUrl(driver);
		hm.setUsername(validUsername);
		hm.setLoginPass(validPassword);
		hm.clickLogin();
		Thread.sleep(3000);
		ad.clickAdmin();
		System.out.println("Clicked on admin");
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
			System.out.println("Correct error msg for duplicate username is printed");
			softAssert.assertTrue(true);
		}
		else
		{
			System.out.println("Expected error msg is 'User Already Exists' but printed error msg is '"+duplicateUsernameExistsTxt+"'");
			softAssert.assertTrue(false);
		}
		softAssert.assertAll();
		
	}
	
	@Test
	void deleteUser() throws InterruptedException
	{
		HomePage hm = new HomePage(driver);
		AdminPage ad = new AdminPage(driver);
		Faker faker = new Faker();
		//openBrowserWithUrl(driver);
		hm.setUsername(validUsername);
		hm.setLoginPass(validPassword);
		hm.clickLogin();
		Thread.sleep(3000);
		ad.clickAdmin();
		System.out.println("Clicked on admin");
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
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
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
		if(getPageSourceAfterDelete.contains("Successfully Deleted") == true)
		{
			System.out.println("correct 'Successfully deleted' confirmation msg is printed");
			softAssert.assertTrue(true);
		}
		else
		{
			System.out.println("'Successfull deleted' acknowledgement msg is not printed, probably user was not deleted successfully");
			softAssert.assertTrue(false);
		}
			
			softAssert.assertAll();
		}
	
	

}

