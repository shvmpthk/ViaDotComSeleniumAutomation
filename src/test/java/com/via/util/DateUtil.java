package com.via.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.via.base.BaseTestClass;;

public class DateUtil extends BaseTestClass{
	private static WebDriverWait wait;
	private static By rightArrowElement = By.xpath("//span[@class='vc-month-box-head-cell vc-month-controls icon-Rightarrowthin vc-month-control-active js-next-month']");
	private static By dateElement;
	private static int monthDifference;
	private static String dateToSubtract = new SimpleDateFormat("dd/MM/yyyy").format(new Date());

	public static void selectDate(String date)
	{
		wait = new WebDriverWait(driver, 5);
		dateElement = By.xpath("//div[@class='vc-cell ' and text()='"+Integer.parseInt(date.substring(0, 2))+"']");
		wait.until(ExpectedConditions.presenceOfElementLocated(dateElement));
		monthDifference = Integer.parseInt(date.substring(3, 5)) - Integer.parseInt(dateToSubtract.substring(3, 5));
		if(Integer.parseInt(date.substring(6)) > Integer.parseInt(dateToSubtract.substring(6)))
			monthDifference = 12 + monthDifference;
	}
	
	public static void selectFirstDate(String firstDate)
	{
		selectDate(firstDate);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		WebElement nextMonth = driver.findElement(rightArrowElement);
		while(monthDifference>0)
		{
			nextMonth.click();
			monthDifference--;
		}
		driver.findElements(dateElement).get(0).click();
	}
	
	public static void selectSecondDate(WebElement calendarOpenButton, String firstDate, String secondDate)
	{
		calendarOpenButton.click();
		dateToSubtract = firstDate;
		selectDate(secondDate);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		while(monthDifference>0)
		{
			driver.findElements(rightArrowElement).get(1).click();
			monthDifference--;
		}
		driver.findElements(dateElement).get(2).click();	
	}
	
	public static void selectMultiFirstDate(String firstDate) {
		selectDate(firstDate);
		while (monthDifference > 0) {
			driver.findElements(rightArrowElement).get(1).click();
			monthDifference--;
		}
		driver.findElements(dateElement).get(2).click();
	}
	
	public static void selectMultiSecondDate(WebElement calendarOpen, String firstDate, String secondDate) {
		calendarOpen.click();
		dateToSubtract = firstDate;
		selectDate(secondDate);
		while (monthDifference > 0) {
			driver.findElements(rightArrowElement).get(2).click();
			monthDifference--;
		}
		driver.findElements(dateElement).get(4).click();
	}
}