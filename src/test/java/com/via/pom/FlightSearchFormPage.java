package com.via.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.via.util.DateUtil;

//Author : Akash S 
public class FlightSearchFormPage {
	
	WebDriver driver;
	WebDriverWait wait;
	Actions action;
	
	public FlightSearchFormPage(WebDriver driver) {
		this.driver=driver;
		wait=new WebDriverWait(driver, 20);
		action = new Actions(driver);
		// This initElements method will initialize mentioned WebElements in class
		PageFactory.initElements(driver, this);	
	}

	//Declare variable of 'By' datatype
	By adultCount = By.xpath("//div[@class='counter-element adult']//div[@class='count']");
	By childCount=By.xpath("//div[@class='counter-element child']//div[@class='count']");
	By infantCount=By.xpath("//div[@class='counter-element infant']//div[@class='count']");
	By adultPlus = By.xpath("//div[@class='counter-element adult']//div[@class='plus']");
	By childPlus=By.xpath("//div[@class='counter-element child']//div[@class='plus']");
	By infantPlus=By.xpath("//div[@class='counter-element infant']//div[@class='plus']");
	By adultMinus = By.xpath("//div[@class='counter-element adult']//div[@class='minus']");
	By childMinus = By.xpath("//div[@class='counter-element child']//div[@class='minus']");
	By infantMinus = By.xpath("//div[@class='counter-element infant']//div[@class='minus']");
	By showMoreOptions=By.xpath("//div[@class='more']//a");
	By showRecentSearch=By.xpath("//a[@class=\"showRecentSearch closed\"]");
	By noResultFound=By.xpath("//div[@class=\"result noResultsFound\"]");
	By searchBtn = By.xpath("//div[@class='search-btn']");
	By nextMonthArrow=By.xpath("//span[@class='vc-month-box-head-cell vc-month-controls icon-Rightarrowthin vc-month-control-active js-next-month']");
	By roundTrip=By.xpath("//div[@class='round-trip']");
	By returnDate=By.id("return");
	
	//Multicity 
	By multiCitySource=By.id("source-0");
	By multiCityDestination1=By.id("destination-0");
	By multiCityDestination2=By.id("destination-1");
	By multiCityDeparture1=By.id("departure-0");
	By multiCityDeparture2= By.id("departure-1");
	By multiCityMenu=By.xpath("//label[contains(text(),'Multi-city')]");
	By multiCityNextFlight = By.id("multi-city-label-1");
	By searchFlight = By.id("search-flight-btn");
	By showmore=By.xpath("//a[@class='show-more']");
	
	//modify search
	By selectFlight =By.xpath("//*[@class='u_font14 u_fontW400 u_clViaBlack']");
	By selectAirport=By.xpath("//*[@class='selectedSector u_pad10']");
	
	//WebElements by id and name
	private WebElement 	source,
											destination,
											prefCarrier,
											preferredClass,
											routingType,
											filterBody;
	
	public boolean isResultDisplayed() {
		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.xpath("//div[@class='progressBar']"))));
		return filterBody.isDisplayed();
	}
	//For filling the flight one-way trip form  
	public void fillUpOneWayTripForm(String	adultS, String childS, String infantS, String flightDate, String flightClass,String flightRouting,String flightAirline,String flightSource,String flightDestination)
	{
		source.clear();
		source.sendKeys(flightSource);
		waitForElement(2000);
		source.sendKeys(Keys.DOWN);
		source.sendKeys(Keys.TAB);
		destination.clear();
		destination.sendKeys(flightDestination);
		waitForElement(2000);
		destination.sendKeys(Keys.DOWN);
		destination.sendKeys(Keys.TAB);
		waitForElement(2000);
		
		DateUtil.selectFirstDate(flightDate);
		int adult=Integer.parseInt(adultS);
		int child=Integer.parseInt(childS);
		int infant=Integer.parseInt(infantS);
		
		
	    //To click on multiple panel for adults, child and infants
	    for(int i=1;i<adult;i++, driver.findElement(adultPlus).click());

		for(int i=0;i<child;i++,driver.findElement(childPlus).click());

		for(int i=0;i<infant;i++,driver.findElement(infantPlus).click());
		
		driver.findElement(showMoreOptions).click();	
		prefCarrier.clear();
		prefCarrier.sendKeys(flightAirline);
		preferredClass.sendKeys(flightClass);
		routingType.sendKeys(flightRouting);
		driver.findElement(showRecentSearch).click();
		driver.findElement(searchBtn).click();	
		
		//to modify search test
		try {
		if (!driver.findElements(selectFlight).isEmpty()) {
			driver.findElement(selectAirport).click();
			FlightBookPage flightBook = new FlightBookPage(driver);
			flightBook.modify();
		}
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}

	//----Shraddha
	public void fillUpTwoWayTripForm(String	adultS, String childS, String infantS, String flightOnwardDate,String flightReturnDate, String flightClass,String flightRouting,String flightAirline,String flightSource,String flightDestination)
	{
		driver.findElement(roundTrip).click();
		
		source.clear();
		source.sendKeys(flightSource);
		waitForElement(2000);
		source.sendKeys(Keys.DOWN);
		source.sendKeys(Keys.TAB);
		
		destination.clear();
		destination.sendKeys(flightDestination);
		waitForElement(2000);
		destination.sendKeys(Keys.DOWN);
		destination.sendKeys(Keys.TAB);
		waitForElement(2000);
		DateUtil.selectFirstDate(flightOnwardDate);
		DateUtil.selectSecondDate(driver.findElement(returnDate),flightOnwardDate, flightReturnDate);
		
		//Clear Counts
		while(Integer.parseInt(driver.findElement(adultCount).getText())>1) 
			driver.findElement(adultMinus).click();
		while(Integer.parseInt(driver.findElement(childCount).getText())>0) 
			driver.findElement(childMinus).click();
		while(Integer.parseInt(driver.findElement(infantCount).getText())>0) 
			driver.findElement(infantMinus).click();
		
		int adult=Integer.parseInt(adultS);
		int child=Integer.parseInt(childS);
		int infant=Integer.parseInt(infantS);
		for(int i=1;i<adult;i++, driver.findElement(adultPlus).click());
		for(int i=0;i<child;i++,driver.findElement(childPlus).click());
		for(int i=0;i<infant;i++,driver.findElement(infantPlus).click());
		
		driver.findElement(showMoreOptions).click();	
		prefCarrier.clear();
		prefCarrier.sendKeys(flightAirline);
		preferredClass.sendKeys(flightClass);
		routingType.sendKeys(flightRouting);
		driver.findElement(showRecentSearch).click();
		driver.findElement(searchBtn).click();	
	}
	
	//Hrishi
	public void multiCityFlights(String adultS, String childS, String infantS, String flightFirstDate,String flightSecondDate, String flightClass,String flightRouting,String flightAirline,String flightSource,String flightDestination1,String flightDestination2)
	{
		driver.findElement(multiCityMenu).click();
		driver.findElement(multiCitySource).sendKeys(flightSource);
		waitForElement(2000);
		driver.findElement(multiCitySource).sendKeys(Keys.DOWN);
		driver.findElement(multiCitySource).sendKeys(Keys.TAB);
		driver.findElement(multiCityDestination1).sendKeys(flightDestination1);
		waitForElement(2000);
		driver.findElement(multiCityDestination1).sendKeys(Keys.DOWN);
		driver.findElement(multiCityDestination1).sendKeys(Keys.TAB);
		driver.findElement(multiCityDeparture1).click();

		DateUtil.selectMultiFirstDate(flightFirstDate);
		
		//Clear Counts
		while(Integer.parseInt(driver.findElement(adultCount).getText())>1) 
			driver.findElement(adultMinus).click();
		while(Integer.parseInt(driver.findElement(childCount).getText())>0) 
			driver.findElement(childMinus).click();
		while(Integer.parseInt(driver.findElement(infantCount).getText())>0) 
			driver.findElement(infantMinus).click();
		
		int adult=Integer.parseInt(adultS);
		int child=Integer.parseInt(childS);
		int infant=Integer.parseInt(infantS);
		for(int i=1;i<adult;i++, driver.findElement(adultPlus).click());
		for(int i=0;i<child;i++,driver.findElement(childPlus).click());
		for(int i=0;i<infant;i++,driver.findElement(infantPlus).click());

		driver.findElement(showMoreOptions).click();	
		prefCarrier.clear();
		prefCarrier.sendKeys(flightAirline);
		preferredClass.sendKeys(flightClass);
		routingType.sendKeys(flightRouting);
		driver.findElement(multiCityNextFlight).click();

		//Switch to second flight
		driver.findElement(multiCityDestination2).sendKeys(flightDestination2);
		waitForElement(2000);
		driver.findElement(multiCityDestination2).sendKeys(Keys.DOWN);
		driver.findElement(multiCityDestination2).sendKeys(Keys.TAB);
		DateUtil.selectMultiSecondDate(driver.findElement(multiCityDeparture2), flightFirstDate, flightSecondDate);
		
		//Clear Counts
		while(Integer.parseInt(driver.findElement(adultCount).getText())>1) 
			driver.findElement(adultMinus).click();
		while(Integer.parseInt(driver.findElement(childCount).getText())>0) 
			driver.findElement(childMinus).click();
		while(Integer.parseInt(driver.findElement(infantCount).getText())>0) 
			driver.findElement(infantMinus).click();

		for(int i=1;i<adult;i++, driver.findElement(adultPlus).click());
		for(int i=0;i<child;i++,driver.findElement(childPlus).click());
		for(int i=0;i<infant;i++,driver.findElement(infantPlus).click());

		prefCarrier.clear();
		prefCarrier.sendKeys(flightAirline);
		preferredClass.sendKeys(flightClass);
		routingType.sendKeys(flightRouting);
		driver.findElement(searchFlight).click();
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
