package com.via.pom;

import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HotelResultPage {
	
	private WebDriver driver;
	private WebDriverWait wait;
	
	@FindBy(xpath = "//div[@class='filterHotel isFixed']")
	private WebElement filterPanel;
	
	@FindBy(xpath="//div[@id='0']/*/*/div[@class='selectBtn js-viewRoom via-processed']")
	private WebElement selectRoomsButton;
	
	@FindBy(xpath="//div[@id='roomHotel0']/*/*/*/div[@class='bookBtn js-bookRoom via-processed']")
	private List<WebElement> bookRoomButtons;
	
	public HotelResultPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(this.driver, this);
		wait = new WebDriverWait(this.driver, 40);
}
	
	public void selectRooms()
	{	
		wait.until(ExpectedConditions.visibilityOf(filterPanel));
		selectRoomsButton.click();
		
		wait.until(ExpectedConditions.visibilityOfAllElements(bookRoomButtons));
		bookRoomButtons.get(0).click();
	}
	
	public boolean isSelectionSuccessful() {
		try {
			Thread.sleep(15000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return driver.getTitle().contains("Confirm");
	}
}
