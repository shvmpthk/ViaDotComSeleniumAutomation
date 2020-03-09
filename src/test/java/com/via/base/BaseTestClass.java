package com.via.base;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class BaseTestClass{
		protected static WebDriver driver;
		protected static ExtentTest testLog;
		protected static ExtentReports extentReporter;
		
		@BeforeSuite
		@Parameters({"browser","domain"})
	  	public void beforeSuite(String browser, String domain) {
	  	if(browser.equalsIgnoreCase("chrome")){	
			System.setProperty("webdriver.chrome.driver", ".//src//test//resources//chromedriver.exe");
			driver = new ChromeDriver(new ChromeOptions().addArguments("start-maximized"));
	  	}
	  	else if(browser.equalsIgnoreCase("firefox")){
			System.setProperty("webdriver.gecko.driver", ".//src//test//resources//geckodriver.exe");
			driver = new FirefoxDriver(new FirefoxOptions().addArguments("start-maximized"));
		}
		else
			driver = new HtmlUnitDriver(true);
	  		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.get(domain);
			extentReporter=new ExtentReports();
			extentReporter.attachReporter(new ExtentHtmlReporter("ViaComReport0.html"));
		}
  
	  @AfterSuite
	  public void afterSuite() {
		  driver.quit();
	  }
}

