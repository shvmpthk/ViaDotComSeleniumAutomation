package com.via.pom;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.via.exceptions.NotEnoughSeatsException;

public class BusResultPage {
	private WebDriver driver;
	private JavascriptExecutor js;
	private Actions action;
	private WebDriverWait wait;
		
	@FindBy(xpath="//button[contains(text(),'View Seats')]")
	List<WebElement> viewBusSeats;
	
	@FindBy(xpath="//fieldset[@class='flex u_marT5']//select[@class='selectPickup selt-option']")
	private WebElement boarding;
	
	@FindBy(xpath="//select[@class='selectDrop selt-option']")
	private List<WebElement> dropping;
	
	@FindBy(xpath="//button[@class='CTA-green proceed u_font14 u_fontW600']")
	private WebElement proceedBtn;
	
	public BusResultPage(WebDriver driver) {
		this.driver = driver;
		action = new Actions(driver);
		js=(JavascriptExecutor)driver;
		PageFactory.initElements(driver, this);		
	}
	
	public boolean resultNotFound() {
		try {
			driver.findElement(By.xpath("//*[@class='container noResultFound']"));
		} catch(NoSuchElementException e) {
			return true;
		}
		return false;
	}
	
	public boolean isSelectionSuccessful() {
		return driver.getTitle().equals("Confirm Your Bus Booking -Via.com");
	}
	
	public boolean viewSeats(int way, int busNumber) {
		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.className("progressBar"))));//id:busPageLoaderTemplate

		switch(way) {
			case 0:
				viewBusSeats.get(busNumber).click();
				return true;
			case 1:
			case 2:
				List<WebElement> viewSeatButtons = driver.findElements(By.xpath("//ul["+way+"]//button[contains(text(),'View Seats')]"));
				if(!viewSeatButtons.isEmpty()) {
					js.executeScript("arguments[0].click();",viewSeatButtons.get(busNumber));
					return true;
				}
		}
		return false;
	}
	
	public int selectSeats(int nOfSeats,String bLocation, String dLocation, int busNumber) throws NotEnoughSeatsException{
		if(!viewSeats(0,busNumber)) 
			return -1;

		wait = new WebDriverWait(driver, 5);		
		
		List<WebElement> seats = driver.findElements(By.xpath("//div[@id='busSearchResultsContainer']/div/ul/li[1]/div[2]/div/div[1]/div/div/div[2]/div/i[contains(@data-original-title,'Available')]"));
		if(seats.isEmpty()) return seats.size(); 
		if(nOfSeats<=seats.size())
			for(int i=0;i<nOfSeats;i++)
				seats.get(i).click();
		else
			throw new NotEnoughSeatsException("Number of seats required "+nOfSeats+" exceeds available seats "+seats.size());
												
		wait.until(ExpectedConditions.visibilityOf(boarding));
		boarding.sendKeys(bLocation);
		action.sendKeys(boarding,Keys.TAB).perform();
		if(!boarding.isSelected()) new Select(boarding).selectByIndex(1);
		
		if(!dropping.isEmpty()){
			dropping.get(0).sendKeys(dLocation);
			action.sendKeys(dropping.get(0),Keys.TAB).perform();
			if(!dropping.get(0).isSelected()) 
				new Select(dropping.get(0)).selectByIndex(1);
		}
		proceedBtn.click();
		return nOfSeats;
	}
	
	//-----HRISHIKESH
	public int selectSeatsForOneSideJourney(int nOfSeats,String bLocation, String dLocation, int busNumber) throws NotEnoughSeatsException{
		if(!viewSeats(1,busNumber)) 
			return -1;

		wait = new WebDriverWait(driver, 5);	
		List<WebElement> seats = driver.findElements(By.xpath("//i[contains(@data-original-title,'Available')]"));	//
		if(seats.isEmpty()) return seats.size(); 
		if(nOfSeats<=seats.size())
			for(int i=0;i<nOfSeats;i++)
				seats.get(i).click();
		else
			throw new NotEnoughSeatsException("Number of seats required "+nOfSeats+" exceeds available seats "+seats.size());

		wait.until(ExpectedConditions.visibilityOf(boarding));
		boarding.sendKeys(bLocation);
		action.sendKeys(boarding,Keys.TAB).perform();
		if(!boarding.isSelected()) new Select(boarding).selectByIndex(1);
		
		if(!dropping.isEmpty()){
			dropping.get(0).sendKeys(dLocation);
			action.sendKeys(dropping.get(0),Keys.TAB).perform();
			if(!dropping.get(0).isSelected()) 
				new Select(dropping.get(0)).selectByIndex(1);
		}
		proceedBtn.click();
		return nOfSeats;
	}

	public int selectSeatsForReturnJourney(int nOfSeats,String bLocation, String dLocation, int busNumber) throws NotEnoughSeatsException{
		if(!viewSeats(2,busNumber)) 
			return -1;
		
		wait = new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.stalenessOf(driver.findElement(By.xpath("//i[contains(@data-original-title,'Available')]")))));
		List<WebElement> seats = driver.findElements(By.xpath("//i[contains(@data-original-title,'Available')]"));
		if(seats.isEmpty()) return seats.size(); 
		if(nOfSeats<=seats.size())
			for(int i=0;i<nOfSeats;i++) {
				seats.get(i).click();
			}
		else
			throw new NotEnoughSeatsException("Number of seats required "+nOfSeats+" exceeds available seats "+seats.size());

		js.executeScript("arguments[0].setAttribute('value', arguments[1]);",boarding,bLocation);

		if(!boarding.isSelected()) new Select(boarding).selectByIndex(1);
		
		 if(!dropping.isEmpty()){
			dropping.get(0).sendKeys(dLocation);
			action.sendKeys(dropping.get(0),Keys.TAB).perform();
			if(!dropping.get(0).isSelected()) 
				new Select(dropping.get(0)).selectByIndex(1);
		}
		 
		js.executeScript("arguments[0].click();", proceedBtn);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[text()='Book Buses']")));
		js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//button[text()='Book Buses']")));
		return nOfSeats;
	}
}
