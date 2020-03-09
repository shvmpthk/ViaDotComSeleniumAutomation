package com.via.holiday;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.via.base.BaseTestClass;
import com.via.pom.HolidayBookPage;
import com.via.pom.HolidaySearchFormPage;


public class BookHolidayTest extends BaseTestClass{

	@BeforeMethod
	public void beforeMethod() {
		driver.get("https://in.via.com/holidays");
	}
	
	@Test(dataProvider="holidayDataProvider", dataProviderClass=HolidayDataProvider.class)
	public void holidaySearchTest(HolidayData holiday) {
		HolidaySearchFormPage	holidaySearch=new HolidaySearchFormPage(driver);
		holidaySearch.searchHoliday(holiday);
		HolidayBookPage bookHotel=new HolidayBookPage(driver);
		Assert.assertTrue(holidaySearch.getTotalRooms(holiday.getRoomsinfo())==bookHotel.getNoOfRooms(),"Number of rooms do not match");
		Assert.assertTrue(holidaySearch.getTotalChildren(holiday.getRoomsinfo())==bookHotel.getNoOfChildren(),"Number of total children do not match");
		Assert.assertTrue(holidaySearch.getTotalAdults(holiday.getRoomsinfo())==bookHotel.getNoOfTotalAdults(),"Number of total adults do not match");
		bookHotel.bookHolidayHotel();
	}
	
}  

