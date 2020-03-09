package com.via.pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HolidayBookPage {

	private WebDriverWait wait;

	@FindBy(xpath = "//*[@class='bookCTA']")
	private WebElement book_button;

	@FindBy(xpath = "//*[@class='CTA CTA-Calendar-Book']")
	private WebElement calendarBook;

	@FindBy(xpath = "//span[@class='dark js-roomModify']")
	private WebElement noOfRooms;

	@FindBy(xpath = "//span[@class='dark js-adultsModify']")
	private WebElement noOfTotalAdults;

	@FindBy(xpath = "//span[@class='dark js-childModify']")
	private WebElement noOfTotalChildren;

	public HolidayBookPage(WebDriver driver) {

		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, 30);
	}

	public int getNoOfRooms() {
		return Integer.parseInt(noOfRooms.getText());
	}

	public int getNoOfTotalAdults() {
		return Integer.parseInt(noOfTotalAdults.getText());
	}

	public int getNoOfChildren() {
		return Integer.parseInt(noOfTotalChildren.getText());
	}

	public void bookHolidayHotel() {
		book_button.click();
		wait.until(ExpectedConditions.visibilityOf(calendarBook));
		calendarBook.click();
	}

}
