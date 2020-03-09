package com.via.flight;

import org.testng.annotations.Test;
import com.via.base.BaseTestClass;
import com.via.base.ExcelRead;
import com.via.pom.FlightBookPage;
import com.via.pom.FlightSearchFormPage;
import com.via.pom.FlightTravellerDetailsFormPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.Assert;
import org.testng.annotations.AfterTest;

public class FlightBookingTest extends BaseTestClass {

	String excelsheet=".\\ViaFlight.xlsx";
	@Test(priority='1',dataProvider= "flightOneWayDp")
	public void testOneWayTrip(String adult,String child,String infant, String flightDate,String flightClass,String flightRouting,String flightAirline, String flightSource,String flightDestination,String contact, String email, String[][] travellers) {
		 FlightSearchFormPage flightsearchform=new FlightSearchFormPage(driver);
		 FlightBookPage flightbook=new FlightBookPage(driver);
		 FlightTravellerDetailsFormPage flighttrravellerdetails =new FlightTravellerDetailsFormPage(driver);
		 
		 flightsearchform.fillUpOneWayTripForm(
				 adult,						//int 	adult 
				 child,						//int child 
				 infant,						//int infant  
				 flightDate, 				//String flightDate  
				 flightClass,				//String flightClass 
				 flightRouting,			//String flightRouting 
				 flightAirline,			//String flightAirline 
				 flightSource,			//String flightSource 
				 flightDestination		//String flightDestination 
				 );
		 //Assert
		 Assert.assertTrue(flightsearchform.isResultDisplayed());
		 
		 flightbook.oneWayTripBook();
		 Assert.assertEquals(driver.getTitle(), "Flight Booking");
		 
		 flighttrravellerdetails.fillTravellerDetails(
				 	travellers,
				 	contact,
				 	email
				 );
	}
	
	@DataProvider
	  public Object[][] flightOneWayDp() {
		
		ExcelRead.excelConfig(excelsheet,"OneWaySearch" );
		  int lastCell=ExcelRead.numberOfColumns();
		  int rowNumber=ExcelRead.numberOfRows();
		  Object data[][]=new Object[rowNumber][lastCell];
		  
		  for(int i=1;i<=rowNumber;i++) {
			  for(int j=0;j<lastCell;j++) {
				  data[i-1][j]=ExcelRead.read(i,j);
			  }
		  }
		  
		  ExcelRead.excelConfig(excelsheet,"Travellers" );
		  lastCell=ExcelRead.numberOfColumns();
		  rowNumber=ExcelRead.numberOfRows();
		  
		  for(int i=0;i<data.length;i++) {
			  int noOfPeople = Integer.parseInt((String) data[i][data[i].length-1]);
			  String[][] travellers= new String[noOfPeople][lastCell];
			  int noOfAdult=Integer.parseInt((String) data[i][0]);
			  int noOfChild=Integer.parseInt((String) data[i][1]);
			  int noOfInfant=Integer.parseInt((String) data[i][2]);
			  
			  noOfPeople=0;
			  //Adults
			  for(int j=0;j<noOfAdult;j++) { 
				  for(int k=0;k<lastCell;k++)  
					  travellers[noOfPeople][k] = ExcelRead.read(1+j,k);
				  noOfPeople++;
			  }
			//Child
			  for(int j=0;j<noOfChild;j++) { 
				  for(int k=0;k<lastCell;k++)  
					  travellers[noOfPeople][k] = ExcelRead.read(14+j,k);
				  noOfPeople++;
			  }
			//Infant
			  for(int j=0;j<noOfInfant;j++) {
				  for(int k=0;k<lastCell;k++)  
					  travellers[noOfPeople][k] = ExcelRead.read(20+j,k);
				  noOfPeople++;
			  }
			  
			  data[i][data[i].length-1]=travellers;
			  
		  }
		  return data;
	    }
	
  	
	@Test(priority='2',dataProvider= "flightTwoWayDp")
	public void testTwoWayTrip(String adult,String child,String infant,String flightOnwardDate,String flightReturnDate,String flightClass,String flightRouting,String flightAirline,String flightSource,String flightDestination,String contact,String email,String[][] travellers) {
		  FlightSearchFormPage flightsearchform=new FlightSearchFormPage(driver);
		  FlightBookPage flightbook=new FlightBookPage(driver);
		  FlightTravellerDetailsFormPage flighttrravellerdetails =new FlightTravellerDetailsFormPage(driver);

		  flightsearchform.fillUpTwoWayTripForm(
				  	 adult, 
					 child,
					 infant,  
					 flightOnwardDate,
					 flightReturnDate,
					 flightClass, 
					 flightRouting, 
					 flightAirline, 
					 flightSource, 
				     flightDestination
			  );
	  flightbook.twoWayTripBook();
	  flighttrravellerdetails.fillTravellerDetails(
			  travellers,
			  contact,
			  email 		
		); 
	}
	
	@DataProvider
     public Object[][] flightTwoWayDp() {
		
		ExcelRead.excelConfig(excelsheet,"TwoWaySearch" );
		int lastCell=ExcelRead.numberOfColumns();
		int rowNumber=ExcelRead.numberOfRows();
		Object data[][]=new Object[rowNumber][lastCell];
		
		for(int i=1;i<=rowNumber;i++){
			for(int j=0;j<lastCell;j++){
				data[i-1][j]=ExcelRead.read(i, j);
			}
		}
		
		ExcelRead.excelConfig(excelsheet,"Travellers" );
	    lastCell=ExcelRead.numberOfColumns();
		rowNumber=ExcelRead.numberOfRows();
		for(int i=0;i<data.length;i++) {
			int noOfPeople= Integer.parseInt((String) data[i][data[i].length-1]);
			String[][] travellers=new String[noOfPeople][lastCell];
			
			int noOfAdult=Integer.parseInt((String) data[i][0]);
			int noOfChild=Integer.parseInt((String) data[i][1]);
			int noOfInfant=Integer.parseInt((String) data[i][2]);
			
			noOfPeople=0;
			//Adult
			for(int j=0;j<noOfAdult;j++) {
				for(int k=0;k<lastCell;k++) 
					travellers[noOfPeople][k]=ExcelRead.read(1+j, k);
					noOfPeople++;		
				}
			
			//Child
			for(int j=0;j<noOfChild;j++) {
				for(int k=0;k<lastCell;k++) 
					travellers[noOfPeople][k]=ExcelRead.read(14+j, k);
					noOfPeople++;		
				}
			
			//Infant
			for(int j=0;j<noOfInfant;j++) {
				for(int k=0;k<lastCell;k++) 
					travellers[noOfPeople][k]=ExcelRead.read(20+j, k);
					noOfPeople++;		
				}
			data[i][data[i].length-1]=travellers;
		}
		return data;
	}
  
	@Test(priority='3',dataProvider="flightMultiCityDp")
	public void testMultiCityFlight(String adult,String child,String infant,String flightFirstDate,String flightSecondDate,String flightClass,String flightRouting,String flightAirline,String flightSource,String flightDestination1,String flightDestination2,String contact,String email,String[][] travellers)
	  {
		FlightSearchFormPage flightsearchform=new FlightSearchFormPage(driver);
		 FlightBookPage flightbook=new FlightBookPage(driver);
		 FlightTravellerDetailsFormPage flighttrravellerdetails =new FlightTravellerDetailsFormPage(driver);
		  
		  flightsearchform.multiCityFlights(
				  	adult, 
				    child, 
				    infant,  
					flightFirstDate,
					flightSecondDate,
					flightClass, 
				    flightRouting, 
					flightAirline ,
					flightSource, 
					flightDestination1,
					flightDestination2
				  );
		  flightbook.multiCityTripBook();
		  flighttrravellerdetails.fillTravellerDetails(
				  travellers,
				  contact,
				  email
				  );
	  }
	@DataProvider
    public Object[][] flightMultiCityDp() {
		
		ExcelRead.excelConfig(excelsheet,"MultiCitySearch" );
		int lastCell=ExcelRead.numberOfColumns();
		int rowNumber=ExcelRead.numberOfRows();
		Object data[][]=new Object[rowNumber][lastCell];
		
		for(int i=1;i<=rowNumber;i++){
			for(int j=0;j<lastCell;j++){
				data[i-1][j]=ExcelRead.read(i, j);
			}
		}
		
		ExcelRead.excelConfig(excelsheet,"Travellers" );
	    lastCell=ExcelRead.numberOfColumns();
		rowNumber=ExcelRead.numberOfRows();
		for(int i=0;i<data.length;i++) {
			int noOfPeople= Integer.parseInt((String) data[i][data[i].length-1]);
			String[][] travellers=new String[noOfPeople][lastCell];
			
			int noOfAdult=Integer.parseInt((String) data[i][0]);
			int noOfChild=Integer.parseInt((String) data[i][1]);
			int noOfInfant=Integer.parseInt((String) data[i][2]);
			
			noOfPeople=0;
			//Adult
			for(int j=0;j<noOfAdult;j++) {
				for(int k=0;k<lastCell;k++) 
					travellers[noOfPeople][k]=ExcelRead.read(1+j, k);
					noOfPeople++;		
				}
			
			//Child
			for(int j=0;j<noOfChild;j++) {
				for(int k=0;k<lastCell;k++) 
					travellers[noOfPeople][k]=ExcelRead.read(14+j, k);
					noOfPeople++;		
				}
			
			//Infant
			for(int j=0;j<noOfInfant;j++) {
				for(int k=0;k<lastCell;k++) 
					travellers[noOfPeople][k]=ExcelRead.read(20+j, k);
					noOfPeople++;		
				}
			data[i][data[i].length-1]=travellers;
		}
		return data;
	}
	@BeforeMethod
	public void beforeMethod() {
		  //Flight Page
		  driver.get("https://in.via.com/");	  
	}

	 @BeforeTest
	 public void beforeTest() {
		 //Preconditions
	 }

	 @AfterTest
	 public void afterTest() {
		 //Postconditions
	 }
}
