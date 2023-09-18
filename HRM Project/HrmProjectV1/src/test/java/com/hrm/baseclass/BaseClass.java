package com.hrm.baseclass;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.github.javafaker.Faker;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.hrm.utilities.ReadConfig;

import io.github.bonigarcia.wdm.WebDriverManager;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;

import org.openqa.selenium.chrome.ChromeOptions;
import com.hrm.utilities.Log;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;


public class BaseClass 

{

	public static WebDriver driver;
	ReadConfig readConfig = new ReadConfig();
	public SoftAssert softAssert = new SoftAssert();
	public Log log = new Log();
	
	public String validUsername = readConfig.getUsername();
	public String validPassword = readConfig.getPassword();
	String pageUrl = readConfig.getPageUrl();
	String browserName = readConfig.getBrowserName();
	
	//Extent Report
	protected static ExtentReports extent;
	protected ExtentTest extentTest;
	
	static
	{
		extent = new ExtentReports();
		File file = new File("extent-report.html");
		ExtentSparkReporter sparkReporter = new ExtentSparkReporter(file);
		extent.attachReporter(sparkReporter);
		Log.info("Completed ExtentReport Setup");
	}
	
	@BeforeMethod
	public void setup() throws InterruptedException
	{
		
		if (browserName.equalsIgnoreCase("chrome"))
		{
			Log.info("Entered Chrome browser setup");
			ChromeOptions options = new ChromeOptions();
			options.setBinary("C:\\Akash\\Downloads\\chrome-win64\\chrome-win64\\chrome.exe");
			options.addArguments("--remote-allow-origins=*");//not recommended as it disables the same origin policy
			System.setProperty("webdriver.chrome.driver", "C:\\Akash\\Downloads\\chromedriver-win64-117\\chromedriver-win64\\chromedriver.exe");
			driver = new ChromeDriver(options);
		}
		
		else if (browserName.equalsIgnoreCase("firefox"))
		{
			Log.info("Entered Firefox browser setup");
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		}
		
		else if (browserName.equalsIgnoreCase("edge"))
		{
			Log.info("Entered Edge browser setup");
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
		}
		Log.info("Setup Completed");
		driver.get(pageUrl);
		driver.manage().window().maximize();
		Log.info("Opened Page Url");
		Thread.sleep(4000);
	}
	

	public void openBrowserWithUrl(WebDriver driver)
	{
		driver.get(pageUrl);
		driver.manage().window().maximize();
		try 
			{
				Thread.sleep(2000);
			} 
		catch (InterruptedException e) 
			{
			
				e.printStackTrace();
			}
		System.out.println("Browser opened");
	}
	
	public void captureScreenshot(WebDriver driver, String fname)
	{
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		File target = new File(System.getProperty("user.dir")+"/Screenshots/"+fname+".png");
		try {
				FileUtils.copyFile(source, target);
			} 
		catch (IOException e) 
			{
				e.printStackTrace();
			}
		Log.info("Captured Screenshot");
	}
	
	public boolean compareScreenshots(WebDriver driver, String actualFile, String expectedFile)
	{
		Log.info("Comparing Screenshots");
		AShot ashot = new AShot();
		BufferedImage expectedImage = null;
		try 
			{
				expectedImage = ImageIO.read(new File("C:\\Akash\\Project Workarea\\HRM Project\\HrmProjectV1\\Screenshots\\Expected\\"+expectedFile+".png"));
			} 
		catch (IOException e) 
			{
				e.printStackTrace();
			}
		BufferedImage actualImage = null;
		try 
			{
				//actualImage = ImageIO.read(new File("C:\\Akash\\Project Workarea\\HRM Project\\HrmProjectV1\\Screenshots\\Expected\\HomepageESC.png"));
				actualImage = ImageIO.read(new File("C:\\Akash\\Project Workarea\\HRM Project\\HrmProjectV1\\Screenshots\\"+actualFile+".png"));
			} 
		catch (IOException e) 
			{
				e.printStackTrace();
			}
		//To compare we have a class called ImageDiffer
		ImageDiffer imgDiff = new ImageDiffer();
		//spot the differences in image and store the result in ImageDiff reference
		ImageDiff diff =  imgDiff.makeDiff(expectedImage, actualImage);
		
		BufferedImage highlightedDifference = diff.getMarkedImage();
		try 
			{
				ImageIO.write(highlightedDifference, "png", new File("C:\\Akash\\Project Workarea\\HRM Project\\HrmProjectV1\\Screenshots\\Differences\\"+actualFile+"_diff_spotted.png"));
			} 
		catch (IOException e) 
			{
				e.printStackTrace();
			}
		if(diff.hasDiff()==true)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public boolean checkPageSourceContents(WebDriver driver, String[] arr)
	{
		for(String s: arr)
		{
			if(!driver.getPageSource().contains(s))
			{
				return false;
			}
		}
		return true;
	}
	
	public void scrollTillEnd()
	{
		Log.info("Scrolling down");
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}
	
	// This returns Base64 of the screenshot
	public static String screenShotCapture() {
		Log.info("Capturing Base64 screenshot");
		TakesScreenshot takeScreenshot = (TakesScreenshot) driver;
		String base64Code = takeScreenshot.getScreenshotAs(OutputType.BASE64);
		return base64Code;
	}
	
	
	@AfterMethod
	public void tearDown()
	{
		try 
			{
				Thread.sleep(1000);
			} 
		catch (InterruptedException e) 
			{		
				e.printStackTrace();
			}
		
		Log.info("Browser Closed");
		extent.flush();
		driver.close();

	}
	
}
