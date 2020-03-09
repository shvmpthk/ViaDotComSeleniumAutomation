package com.via.pom;


import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BusConfirmBookingPage {
		WebDriver driver;
		WebDriverWait wait;
		Actions action;
		
		By contactMobile = By.id("contactMobile");
		By contactEmail = By.id("contactEmail");
		By termsCond = By.id("read_terms_label");
		By makePay = By.id("makePayCTA");
		By confirmBooking = By.id("confirmProceedPayBtn");
		By paymentTab = By.id("step3Lbl");
		By detailsContainer = By.id("paxContainer");
		By photoId = By.id("Identity-1");
		By photoIdNumber = By.id("js-identity-number");

		public BusConfirmBookingPage(WebDriver driver) {
			this.driver=driver;
			action = new Actions(driver);
			wait=new WebDriverWait(driver, 10);
		}
		
		public void fillGuestDetails(String[][] travellers, String contact, String email) {
			
			for(int i=0;i<travellers.length;i++) 
				driver.findElement(By.name("AdultTitle"+i)).sendKeys(travellers[i][0]);
			
			for(int i=0;i<travellers.length;i++) { 
				driver.findElement(By.name("AdultFirstName"+i)).clear();
				driver.findElement(By.name("AdultFirstName"+i)).sendKeys(travellers[i][1]);
			}
			for(int i=0;i<travellers.length;i++)
				driver.findElement(By.name("AdultAge"+i)).sendKeys(travellers[i][2]);				
			
			if(driver.findElement(detailsContainer).getText().contains("Enter a photo ID details"))		//If Photo ID is asked 
				enterIdDetails();
			
			driver.findElement(contactMobile).clear();
			driver.findElement(contactMobile).sendKeys(contact);
			driver.findElement(contactEmail).clear();
			driver.findElement(contactEmail).sendKeys(email);
			JavascriptExecutor js = (JavascriptExecutor)driver;
			js.executeScript("arguments[0].scrollIntoView();", driver.findElement(termsCond));
			if(!driver.findElement(termsCond).isSelected()) {
				driver.findElement(termsCond).click();
			}
			driver.findElement(makePay).click();
		}
		
		public void enterIdDetails() {
			driver.findElement(photoId).sendKeys("Aadhar");
			action.sendKeys(Keys.TAB);
			driver.findElement(photoIdNumber).sendKeys("123412341234");
		}
		
		public void confirmBooking() {
			driver.findElement(confirmBooking).click();
		}
		
		public boolean paymentWindowActive() {
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(text(),'Credit')]")));
			return driver.findElement(By.xpath("//div[contains(text(),'Credit')]")).getText().contains("Credit");
		} 
}