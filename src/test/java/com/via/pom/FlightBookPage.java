package com.via.pom;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.via.util.DateUtil;

public class FlightBookPage {
	WebDriver driver;
	WebDriverWait wait;
	public FlightBookPage(WebDriver driver) {
		this.driver = driver;
		wait =new WebDriverWait(driver,30);
		PageFactory.initElements(driver, this);
	}
	
	By btnBook=By.xpath("//*[@class='bookCTA u_marB5']");
	By btnBookFlights = By.xpath("//*[@class='bookCTA return']");		
	By btnReturn = By.xpath("//*[@class='upperContainer via-processed']//button[@class='bookCTA u_marB5']"); 
	By btnDeparture=By.xpath("//*[@class='upperContainer via-processed ']//button[@class='bookCTA u_marB5']");
	
	public void oneWayTripBook() 
	{
		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.xpath("//div[@class='progressBar']"))));
		driver.findElement(btnBook).click();
	}

	public void twoWayTripBook()
	{
		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.xpath("//div[@class='progressBar']"))));
		driver.findElement(btnDeparture).click();
		driver.findElement(btnReturn).click();
		wait.until(ExpectedConditions.presenceOfElementLocated(btnBookFlights));
		driver.findElement(btnBookFlights).click();
	}	
	
	public void multiCityTripBook()
	{
		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.xpath("//div[@class='progressBar']"))));
		driver.findElement(btnDeparture).click();
		driver.findElement(btnReturn).click();
		wait.until(ExpectedConditions.presenceOfElementLocated(btnBookFlights));
		driver.findElement(btnBookFlights).click();
	}	
	
	//modify
	By icon=By.xpath("//div[@id='search-flight-btn']");
	By modify=By.xpath("//div[@class='noResultModifyBtn']");
	By searchFlight = By.id("search-flight-btn");
	WebElement destination;
	
	public void modify()
	{
		if(driver.findElement(icon).isDisplayed())
		{
			driver.findElement(modify).click();
			destination.clear();
			destination.sendKeys("Mumbai");
			waitForElement(2000);
			destination.sendKeys(Keys.DOWN);
			destination.sendKeys(Keys.TAB);
			waitForElement(2000);
			DateUtil.selectFirstDate("15/01/2019");
			driver.findElement(searchFlight).click();
			
		}
	}
	
	public void waitForElement(long s)
	{
		  try {
				Thread.sleep(s);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}
}
