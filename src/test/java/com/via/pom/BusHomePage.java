package com.via.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.via.util.DateUtil;

//PageFactoryModel
//author : Shivam | Hrishikesh
public class BusHomePage {
	private WebDriver driver;
	private Actions action;
	private WebDriverWait wait;
	private WebElement src;
	private WebElement dest;
	private WebElement pkg_src;
	private WebElement pkg_dest;
	
	@FindBy(xpath="//div[@id='busSearchBox']/header/ul/li[2]/a")
	private WebElement roundTrip;
	
	@FindBy(xpath="//div[@id='busSearchBox']/header/ul/li[3]/a")
	private WebElement packages;
	
	@FindBy(id="return")
	private WebElement returnDate;
	@FindBy(css=".search-btn.search-journey")
	private WebElement searchJourney;
	@FindBy(xpath="//*[@class='search-btn search-package']")
	private WebElement searchPackage;
	
	public BusHomePage(WebDriver driver) {
		this.driver = driver;
		this.action = new Actions(driver);
		PageFactory.initElements(driver, this);
	}
	
	//Functional APIs
	public void searchOneWayTrip(String from,String to,String deptDate) {
		wait = new WebDriverWait(driver, 5);		//Fluent Wait
		
		src.clear();
		src.sendKeys(from);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.className("ui-menu-item")));
		src.sendKeys(Keys.DOWN);
		src.sendKeys(Keys.TAB);
		
		//testing
		dest.clear();
		dest.sendKeys(to);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		dest.sendKeys(Keys.DOWN);
		dest.sendKeys(Keys.TAB);
		
		DateUtil.selectFirstDate(deptDate);
		
		searchJourney.click();
	}
	
	public void searchRoundTrip(String from,String to,String deptDate,String retDate) {
		wait = new WebDriverWait(driver, 5);	
		roundTrip.click();

		src.clear();
		src.sendKeys(from);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.className("ui-menu-item")));
		src.sendKeys(Keys.DOWN);
		src.sendKeys(Keys.TAB);
		
		dest.clear();
		dest.sendKeys(to);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e2) {
			e2.printStackTrace();
		}
		dest.sendKeys(Keys.DOWN);
		dest.sendKeys(Keys.TAB);
		
		DateUtil.selectFirstDate(deptDate);
		
	    DateUtil.selectSecondDate(returnDate, deptDate, retDate);
	    
	    action.sendKeys(Keys.ESCAPE).perform();
	    
	    searchJourney.click();
	
	}
	
	public void searchPackages(String from,String to,String deptDate) {
		wait = new WebDriverWait(driver, 5);	
		
		packages.click();
		
		pkg_src.sendKeys(from);
		pkg_src.sendKeys(Keys.DOWN);
		pkg_src.sendKeys(Keys.TAB);
		
		pkg_dest.sendKeys(to);
		pkg_dest.sendKeys(Keys.DOWN);
		pkg_dest.sendKeys(Keys.TAB);
		
		DateUtil.selectFirstDate(deptDate);
		
		searchPackage.click();
	}
}
