package com.via.hotel;

import org.testng.annotations.Test;
import com.via.base.BaseTestClass;
import com.via.base.ExcelRead;
import com.via.pom.HotelResultPage;
import com.via.pom.HotelSearchPage;
import com.via.pom.HotelTravellerDetailsPage;
import org.testng.annotations.DataProvider;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;

public class HotelTest extends BaseTestClass{
  
	@Test(dataProvider="hotelDataProvider")
  	public void hotelBooking(String destination, String checkIn, String checkOut, String nationality, String residence, String mobile, String email, int room, int[] adults, int[] child, int[][] age, String[][] travellers) 
	{
		HotelSearchPage hotelSearchPage = new HotelSearchPage(driver);
		hotelSearchPage.fillHotelSearchForm(destination,checkIn,checkOut,nationality,residence,room,adults,child,age);		
		//Assert
        Assert.assertEquals(driver.getTitle(), "Hotel Results");
		
		HotelResultPage hotelResultPage = new HotelResultPage(driver);
		hotelResultPage.selectRooms();
		//Assert
        Assert.assertEquals(hotelResultPage.isSelectionSuccessful(), true);
		
		HotelTravellerDetailsPage hotelTravellerDetailsPage = new HotelTravellerDetailsPage(driver);
		hotelTravellerDetailsPage.fillTravellerDetails(travellers,mobile,email);
		//Assert
        Assert.assertEquals(driver.getTitle(), "Confirm Your Hotel Booking -Via.com");
	}

	@DataProvider
	public Object[][] hotelDataProvider() {
		ExcelRead.excelConfig(".\\HotelData.xlsx", "FirstPage");
		int rows = ExcelRead.numberOfRows();
		int columns = ExcelRead.numberOfColumns();
		int numberOfRooms;
		Object hotelData[][] = new Object[rows][columns+1];

		for (int i = 1; i <=rows; i++) {
			for (int j = 0; j < 7; j++) {
				hotelData[i - 1][j] = ExcelRead.read(i, j);
			}
			numberOfRooms = Integer.parseInt(ExcelRead.read(i, 7));

			hotelData[i - 1][7] = ExcelRead.read(i, 7);//numberOfRooms;
			
			//For adults
			String str = ExcelRead.read(i, 8);
			String numberOfAdults[] = new String[numberOfRooms];
			int adults[] = new int[numberOfRooms];
			numberOfAdults = str.split(",");
						
			//For children
			String child = ExcelRead.read(i, 9);
			String numberOfChild[] = new String[numberOfRooms];
			int children[] = new int[numberOfRooms];
			numberOfChild = child.split(",");
						
			//For age
			String age = ExcelRead.read(i, 10);
			String ageOfChild[] = new String[numberOfRooms];
			ageOfChild = age.split(",");
			int ages[][] = new int[numberOfRooms][];
			
			for (int j = 0; j < numberOfRooms; j++) {
				adults[j] = Integer.parseInt(numberOfAdults[j]);
				children[j] = Integer.parseInt(numberOfChild[j]);
				ages[j] = new int[children[j]];
				for (int k = 0; k < children[j]; k++) {
					ages[j][k] = Integer.parseInt(ageOfChild[j+k]);
				}
			}
			
			hotelData[i-1][8] = adults;
			hotelData[i-1][9] = children;
			hotelData[i-1][10] = ages;
		}
		
		ExcelRead.excelConfig(".\\HotelData.xlsx", "SecondPage");
		rows = ExcelRead.numberOfRows();
		columns = 3;
		for(int i=1;i<=hotelData.length;i++) {
			numberOfRooms = Integer.parseInt((String) hotelData[i-1][7]);
			String[][] travellers= new String[numberOfRooms][columns];
			for(int j=0;j<numberOfRooms;j++) {//every room 
				for(int k=0;k<columns;k++) {			
					travellers[j][k] = ExcelRead.read(1+j,k);
				}
			}
			hotelData[i-1][11]=travellers;
		}

		ExcelRead.excelConfig(".\\HotelData.xlsx", "FirstPage");
		rows = ExcelRead.numberOfRows();
		for (int i = 1; i <=rows; i++) { 
			hotelData[i - 1][7] = Integer.parseInt(ExcelRead.read(i, 7));//numberOfRooms;
		}
		return hotelData;
	}

  @BeforeMethod
  public void beforeTest() {
	  driver.get("https://in.via.com/hotels");
  }

  @AfterTest
  public void afterTest() {
  }
}
