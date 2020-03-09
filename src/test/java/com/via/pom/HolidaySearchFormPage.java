package com.via.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.via.holiday.HolidayData;
import com.via.holiday.HolidayRoom;

public class HolidaySearchFormPage {
	private WebDriver driver;
	private WebDriverWait wait;
	private Select select;
	
	@FindBy(xpath = "//*[@id=\"holidayDest\"]/div[3]/div/div[1]/label")
	private WebElement flexibleDateCheck;
	@FindBy(id = "destination")
	private WebElement destination;
	@FindBy(id = "departure")
	private WebElement calendar;
	@FindBy(xpath = "//*[@id=\"searchRoomConfig\"]/div[1]/i[2]")
	private WebElement roomDetailsExpand;
	@FindBy(xpath = "//*[@id=\"searchRoomConfig\"]/div[2]/div[3]/div[3]")
	private WebElement roomDoneBtn;
	@FindBy(xpath = "//*[@id=\"addRoom\"]/div")
	private WebElement incrementRoom;
	@FindBy(xpath = "//div[@class='done']")
	private WebElement doneConfigRoomBtn;
	@FindBy(id = "search-holiday-btn")
	private WebElement searchHolidayBtn, Holidays;

	public HolidaySearchFormPage(WebDriver driver) {
		
		this.driver = driver;
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, 10);
	}
	
	public void searchHoliday(HolidayData holiday) {
		selectDestination(holiday.getDestination());
		String departure_date[] = holiday.getDate().split("-");
		selectDate(departure_date[0], departure_date[1], departure_date[2]);
		selectFlexibleDates();
		fillRoomDetails(holiday.getRoomsinfo());
	}
		
	public void selectDestination(String dest) {
		
		destination.clear();
		destination.sendKeys(dest);
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[@id=\"ui-id-1\"]"))));
		destination.sendKeys(Keys.DOWN);
		destination.sendKeys(Keys.ENTER);
	}

	public void selectFlexibleDates() {
		
		if (!flexibleDateCheck.isSelected())
			flexibleDateCheck.click();
	}

	public int getTotalRooms(HolidayRoom[] roomsinfo) {
		
		return roomsinfo.length;
	}

	public int getTotalAdults(HolidayRoom[] roomsinfo) {
		
		int sum = 0;
		for (int i = 0; i < roomsinfo.length; i++) {
			sum = sum + roomsinfo[i].getAdult();
		}
		return sum;
	}

	public int getTotalChildren(HolidayRoom[] roomsinfo) {
		
		int sum = 0;
		for (int i = 0; i < roomsinfo.length; i++) {
			sum = sum + roomsinfo[i].getChild();
		}
		return sum;
	}

	public void fillRoomDetails(HolidayRoom[] roomsinfo) {
		
		for (int i = 0; i < roomsinfo.length; i++) {
			fillRoomDetailsRoomwise((i + 1), roomsinfo[i].getAdult(), roomsinfo[i].getChild(),
					roomsinfo[i].getChildBedInfo());
		}
		doneConfigRoomBtn.click();
		searchHolidayBtn.click();
	}

	public void fillRoomDetailsRoomwise(int room, int excelAdultCount, int excelChildCount, String[] childBedInfo) {	
		int siteAdultCount, siteChildCount;
		if (room == 1) {
			roomDetailsExpand.click();
			wait.until(ExpectedConditions.visibilityOf(incrementRoom));
		} else {
			incrementRoom.click();
		}
		siteAdultCount = Integer.parseInt(driver.findElement(By.xpath("//div[@class='eachRoom roomNummber-" + room
				+ "']//div[@class='counter-element adult']//div//div[@class='count']")).getText());
		siteChildCount = Integer.parseInt(driver.findElement(By.xpath("//div[@class='eachRoom roomNummber-" + room
				+ "']//div[@class='counter-element child']//div//div[@class='count']")).getText());
		WebElement incrementAdult = driver.findElement(By.xpath("//div[@class='eachRoom roomNummber-" + room
				+ "']//div[@class='counter-element adult']//div//div[@class='plus'][contains(text(),'+')]"));
		WebElement decrementAdult = driver.findElement(By.xpath("//div[@class='eachRoom roomNummber-" + room
				+ "']//div[@class='counter-element adult']//div//div[@class='minus'][contains(text(),'-')]"));
		WebElement incrementChild = driver.findElement(By.xpath("//div[@class='eachRoom roomNummber-" + room
				+ "']//div[@class='counter-element child']//div//div[@class='plus'][contains(text(),'+')]"));
		WebElement decrementChild = driver.findElement(By.xpath("//div[@class='eachRoom roomNummber-" + room
				+ "']//div[@class='counter-element child']//div//div[@class='minus'][contains(text(),'-')]"));
		
		for (; siteAdultCount != excelAdultCount;) {
			if (siteAdultCount < excelAdultCount) {
				incrementAdult.click();
				siteAdultCount++;
			} else if (siteAdultCount > excelAdultCount) {
				decrementAdult.click();
				siteAdultCount--;
			}
		}
		
		for (; siteChildCount != excelChildCount;) {	
			if (siteChildCount < excelChildCount) {
				incrementChild.click();
				siteChildCount++;
			} else if (siteChildCount > excelChildCount) {
				decrementChild.click();
				siteChildCount--;
			}
		}
		
		for (int i = 1; i <= excelChildCount; i++) {	
			wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//div[@class='eachRoom roomNummber-" + room + "']//select[@name='childAge-" + i + "']")));
			select = new Select(driver.findElement(
					By.xpath("//div[@class='eachRoom roomNummber-" + room + "']//select[@name='childAge-" + i + "']")));
			select.selectByVisibleText(childBedInfo[i - 1]);
		}
	}

	
public void selectDate(String day, String month, String year) {
		
		calendar.click();
		Boolean result = driver.findElement(By.xpath("//*[@id=\"depart-cal\"]/div[3]/div[1]/span[2]")).getText()
				.contains(year);
		for (; !result;) {
			driver.findElement(By.xpath("//*[@id=\"depart-cal\"]/div[4]/div[1]/span[3]")).click();
			result = driver.findElement(By.xpath("//*[@id=\"depart-cal\"]/div[3]/div[1]/span[2]")).getText()
					.contains(year);
		}

		result = driver.findElement(By.xpath("//*[@id=\"depart-cal\"]/div[3]/div[1]/span[2]/span")).getText()
				.contains(month);
		
		for (; !result;) {
			driver.findElement(By.xpath("//*[@id=\"depart-cal\"]/div[4]/div[1]/span[3]")).click();
			result = driver.findElement(By.xpath("//*[@id=\"depart-cal\"]/div[3]/div[1]/span[2]/span")).getText()
					.contains(month);
		}
		
		if (Integer.parseInt(day) < 10) {
			day = day.substring(1, 2);
		}
		
		int i, j;
		for (i = 2; i <= 7; i++) {
			for (j = 1; j <= 7; j++) {
				WebElement date = driver.findElement(
						By.xpath("//div[@id='depart-cal']//div[3]//div[2]//div[" + i + "]//div[" + j + "]"));
				if ((date.isEnabled()) && (date.getText().equals(day)))
					date.click();
			}
		}
	}

}