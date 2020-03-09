package com.via.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.via.util.DateUtil;
import com.via.util.GuestsUtil;

public class HotelSearchPage {
	private WebDriver driver;
	private WebDriverWait wait;

	@FindBy(xpath="//ul[@id='ui-id-1']")
	@CacheLookup
	private WebElement searchList;
	
	@FindBy(xpath="//div[@class='hotelRoomDropDown  js-room']")
	@CacheLookup
	private WebElement roomDropdown;
	
	@FindBy(xpath="//div[@class='counter-element adult js-count via-processed']/div")
	@CacheLookup
	private WebElement adultCounter;
	
	@FindBy(xpath="//div[@class='counter-element child js-count via-processed']/div")
	@CacheLookup
	private WebElement childCounter;
	
	@FindBy(xpath="//div[@class='done']")
	@CacheLookup
	private WebElement roomsDoneButton;
		
	@FindBy(xpath="//div[@class='search-hotel']")
	private WebElement searchHotelsButton;

	@FindBy(xpath="//div[@class='addRoom']")
	@CacheLookup
	private WebElement addRoomsButton;

	@FindBy(xpath="//div[@class='roomConfigBody']")
	@CacheLookup
	private WebElement roomsBody;
	
	@FindBy(id="destination")
	private WebElement destinationField;
	
	private WebElement checkOut;
	private WebElement nationalityCountry;
	private WebElement residenceCountry;

	
	public HotelSearchPage(WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(this.driver, 5);
		PageFactory.initElements(this.driver, this);
	}
	
	public void fillHotelSearchForm(String destination, 
															String checkInDate, 
															String checkOutDate,
															String nationality, 
															String residence, 
															int numberOfRooms, 
															int numberOfAdults[], 
															int numberOfChildren[], 
															int ageOfChildren[][])
	{
		destinationField.clear();
		destinationField.sendKeys(destination);
		wait.until(ExpectedConditions.visibilityOf(searchList));
		searchList.findElement(By.xpath("//li[@class='ui-menu-item']/span[contains(text(),'"+destination+"')]")).click();
		destinationField.sendKeys(Keys.TAB);
		
		DateUtil.selectFirstDate(checkInDate);
		DateUtil.selectSecondDate(checkOut,checkInDate,checkOutDate);
		
		roomDropdown.click();
		int defaultRooms = Integer.parseInt(roomsBody.getAttribute("data-room"));
		//Clean up
		for (int i = 1; i < defaultRooms; i++)
			roomsBody.findElement(By.xpath("//span[@class='removeRoom via-processed' and @style='display: inline;']")).click();
		
		for (int i = 1; i < numberOfRooms; i++) 
			addRoomsButton.click();
		
		GuestsUtil.selectGuests(roomsBody,numberOfRooms,numberOfAdults,numberOfChildren,ageOfChildren);
		roomsDoneButton.click();

		nationalityCountry.sendKeys(nationality);
		residenceCountry.sendKeys(residence);
		
		searchHotelsButton.click();
	}
}
