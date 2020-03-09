package com.via.pom;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


//	Author : Akash | Shraddha
public class FlightTravellerDetailsFormPage {
	WebDriver driver;
	WebDriverWait wait;
	Actions action;
	JavascriptExecutor js;
	public FlightTravellerDetailsFormPage(WebDriver driver) {
		this.driver=driver;
		action = new Actions(driver);
		js=(JavascriptExecutor)driver;
		wait=new WebDriverWait(driver, 20);
		PageFactory.initElements(driver, this);
	}
	
	By adult=By.xpath("//div[@id='paxContainer']//div[@class='paxDiv js-pax via-collapse-blk' and @data-ptype='adt']");
	By child=By.xpath("//div[@id='paxContainer']//div[@class='paxDiv js-pax via-collapse-blk' and @data-ptype='chd']");
	By infant=By.xpath("//div[@id='paxContainer']//div[@class='paxDiv js-pax via-collapse-blk' and @data-ptype='inf']");
	By contactMobile=By.id("contactMobile");
	By contactEmail=By.id("contactEmail");
	By termsCond=By.id("read_terms");
	By makePay = By.id("makePayCTA");
	
	public void fillTravellerDetails(String[][] traveller,String contact,String email)
	{
		int indexCount=0;
		List<WebElement> noOfAdult = driver.findElements(adult);
		List<WebElement> noOfChild = driver.findElements(child);
		List<WebElement> noOfInfant = driver.findElements(infant);
		
		//Adult Member Details
		for(int i=indexCount;i<indexCount+noOfAdult.size();i++) {//"Mr","Sanjay","Jalona"
			driver.findElement(By.id("adult"+(i+1)+"Title")).sendKeys(traveller[i][0]);
			driver.findElement(By.id("adult"+(i+1)+"FirstName")).clear();;
			driver.findElement(By.id("adult"+(i+1)+"FirstName")).sendKeys(traveller[i][1]);
			driver.findElement(By.id("adult"+(i+1)+"Surname")).clear();
			driver.findElement(By.id("adult"+(i+1)+"Surname")).sendKeys(traveller[i][2]);
			
			if(i==noOfAdult.size()-1)
				break;

			noOfAdult.get(i+1).click();
		}
		indexCount += noOfAdult.size();
		
		//Child Member Details
		for(int i=indexCount,j=0;i<indexCount+noOfChild.size();i++,j++) {
			noOfChild.get(j).click();
			driver.findElement(By.id("child"+(j+1)+"Title")).sendKeys(traveller[i][0]);
			driver.findElement(By.id("child"+(j+1)+"FirstName")).sendKeys(traveller[i][1]);
			driver.findElement(By.id("child"+(j+1)+"Surname")).sendKeys(traveller[i][2]);
			driver.findElement(By.id("child"+(j+1)+"DOBday")).sendKeys(traveller[i][3]);
			driver.findElement(By.id("child"+(j+1)+"DOBmonth")).sendKeys(traveller[i][4]);
			driver.findElement(By.id("child"+(j+1)+"DOByear")).sendKeys(traveller[i][5]);
			
			if(j==noOfChild.size()-1)
				break;

			noOfChild.get(j+1).click();
		}
		indexCount += noOfChild.size();
		
		//Infant Member Details
		for(int i=indexCount,j=0;i<indexCount+noOfInfant.size();i++,j++) {
			noOfInfant.get(j).click();
			driver.findElement(By.id("infant"+(j+1)+"Title")).sendKeys(traveller[i][0]);
			driver.findElement(By.id("infant"+(j+1)+"FirstName")).sendKeys(traveller[i][1]);
			driver.findElement(By.id("infant"+(j+1)+"Surname")).sendKeys(traveller[i][2]);
			driver.findElement(By.id("infant"+(j+1)+"DOBday")).sendKeys(traveller[i][3]);
			driver.findElement(By.id("infant"+(j+1)+"DOBmonth")).sendKeys(traveller[i][4]);
			driver.findElement(By.id("infant"+(j+1)+"DOByear")).sendKeys(traveller[i][5]);
			
			if(j==noOfInfant.size()-1)
				break;

			noOfInfant.get(j+1).click();
		}

		driver.findElement(contactMobile).sendKeys(contact);
		driver.findElement(contactEmail).clear();
		driver.findElement(contactEmail).sendKeys(email);
		
		if(!driver.findElement(termsCond).isSelected())
			driver.findElement(termsCond).click();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		wait.until(ExpectedConditions.visibilityOf (driver.findElement(makePay)));
		js.executeScript("arguments[0].click();",driver.findElement(makePay));
	}
	
	public boolean endPageReached() {
		return driver.findElement(By.id("confirmProceedPayBtn")).isDisplayed();	
	}
	
}
