package com.via.pom;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HotelTravellerDetailsPage {

	private WebDriver driver;
	
	@FindBy(xpath="//div[@id='paxContainer']")
	private WebElement guestsContainer;
	
	@FindBy(xpath="//p[@class='roomTitle via-collapse-header']")
	private List<WebElement> addTravellers;
	
	@FindBy(id="contactMobile")
	private WebElement contactMobile;
	
	@FindBy(id="contactEmail")
	private WebElement contactEmail;
	
	@FindBy(id="read_terms_label")
	private WebElement termsConditionsCheckBox;
	
	@FindBy(id="makePayCTA")
	private WebElement proceedToBookingButton;
	
	@FindBy(id="confirmProceedPayBtn")
	private WebElement makePaymentButton;
	
	public HotelTravellerDetailsPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(this.driver, this);
	}
	
	public void fillTravellerDetails(String details[][], String contact, String email)
	{
		int rooms = guestsContainer.findElements(By.xpath("//div[@class='paxRoom via-collapse-blk js-paxRoom']")).size();;
		for (int i = 0; i <rooms; i++)
		{
			driver.findElement(By.name("Room"+i+"AdultTitle0")).sendKeys(details[i][0]);
			driver.findElement(By.name("Room"+i+"AdultFirstName0")).clear();
			driver.findElement(By.name("Room"+i+"AdultFirstName0")).sendKeys(details[i][1]);
			driver.findElement(By.name("Room"+i+"AdultLastName0")).clear();
			driver.findElement(By.name("Room"+i+"AdultLastName0")).sendKeys(details[i][2]);
			if(i==rooms-1)
				break;
			addTravellers.get(i+1).click();
		}

		contactMobile.clear();
		contactMobile.sendKeys(contact);
		contactEmail.clear();
		contactEmail.sendKeys(email);
		
		if(!termsConditionsCheckBox.isSelected())
		{ 
			termsConditionsCheckBox.click();
		}
		proceedToBookingButton.click();
		makePaymentButton.click();
	}
}