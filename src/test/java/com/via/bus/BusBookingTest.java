package com.via.bus;

import org.testng.annotations.Test;
import com.via.base.BaseTestClass;
import com.via.base.ExcelRead;
import com.via.exceptions.NotEnoughSeatsException;
import com.via.pom.BusConfirmBookingPage;
import com.via.pom.BusHomePage;
import com.via.pom.BusResultPage;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import com.via.util.AssertUtil;

public class BusBookingTest extends BaseTestClass{
	
	@Test(priority=1,dataProvider="oneWayBookingData",description="Testing One way Bus Booking functionality")
	public void testOneWay(String from,String to,String deptDate,String noOfSeats,String boarding, String dropping, String contact, String email,String travellers[][]) {	//One way trip
		//Booking Details
		BusHomePage busHomePage = new BusHomePage(driver);
		busHomePage.searchOneWayTrip(from, to, deptDate);
		testLog.info("Data : "+from+to+deptDate+noOfSeats+boarding+dropping+contact+email+travellers.toString());
		//Assertion
		AssertUtil.checkAssertion(driver.getTitle(),"Bus Results","Invalid data");

		//Seat Occupation 
		BusResultPage busResultPage = new BusResultPage(driver);
		//Assertion
		AssertUtil.checkAssertion(busResultPage.resultNotFound(), true, "No buses for this route. Search modification needed !");
		
		boolean seatSelected=false;
		int busNumber=0;
		while(!seatSelected) {
			try {
				int returnVal = busResultPage.selectSeats(Integer.parseInt(noOfSeats),boarding, dropping, busNumber);
				if(returnVal == -1) {
					testLog.error("No Busses found");
					Assert.fail("No Busses found");
				}
				else if (returnVal == 0) {
					testLog.error("Seats not visible");
					Assert.fail("Seats not visible");
				}
				seatSelected=true;
			} catch (NotEnoughSeatsException e) {
				e.printStackTrace();
				busNumber++;
			}
		}
		//Assertion
		AssertUtil.checkAssertion(busResultPage.isSelectionSuccessful(), true, "Unable to proceed to pay for confirmation of booking");
		
		//Traveler Details
		BusConfirmBookingPage busConfirmBookingPage = new BusConfirmBookingPage(driver);
		busConfirmBookingPage.fillGuestDetails(
				travellers,
				contact,
				email
				);

		//Payment
		busConfirmBookingPage.confirmBooking();
		//Assertion
		AssertUtil.checkAssertion(busConfirmBookingPage.paymentWindowActive(),true,"Payment Form Inactive");
	}

	@Test(priority=2,dataProvider="returnTripBookingData",description="Testing Two way Bus Booking functionality")
	public void testTwoWay(String from,String to,String deptDate,String retDate, String noOfSeats,String boarding, String dropping, String contact, String email,String travellers[][]) {	//One way trip
		//Booking Details
		BusHomePage busHomePage = new BusHomePage(driver);
		busHomePage.searchRoundTrip(from, to, deptDate, retDate);
		//Assertion
		AssertUtil.checkAssertion(driver.getTitle(), "Bus Results", "Invalid Data");

		//Seat Occupation
		BusResultPage busResultPage = new BusResultPage(driver);
		AssertUtil.checkAssertion(busResultPage.resultNotFound(), true, "No buses for this route. Search modification needed !");

		boolean seatSelected=false;
		int busNumber=0;
		while(!seatSelected) {
			try {
				int returnVal = busResultPage.selectSeatsForOneSideJourney(Integer.parseInt(noOfSeats),boarding, dropping, busNumber);
				if(returnVal == -1) {
					testLog.error("No Busses found");
					Assert.fail("No Busses found");
				}
				else if (returnVal == 0) {
					testLog.error("Seats not visible");
					Assert.fail("Seats not visible");
				}
				seatSelected=true;
			} catch (NotEnoughSeatsException e) {
				e.printStackTrace();
				busNumber++;
			}
		}
		seatSelected=false;
		busNumber=0;
		while(!seatSelected) {
			try {
				int returnVal = busResultPage.selectSeatsForReturnJourney(Integer.parseInt(noOfSeats), boarding, dropping, busNumber);
				if(returnVal == -1) {
					testLog.error("No Busses found");
					Assert.fail("No Busses found");
				}
				else if (returnVal == 0) {
					testLog.error("Seats not visible");
					Assert.fail("Seats not visible");
				}
				seatSelected=true;
			} catch (NotEnoughSeatsException e) {
				e.printStackTrace();
				busNumber++;
			}
		}
		//Assertion
		AssertUtil.checkAssertion(busResultPage.isSelectionSuccessful(), true, "Unable to proceed to pay for confirmation of booking");

		//Traveler Details
		BusConfirmBookingPage busConfirmBookingPage = new BusConfirmBookingPage(driver);
		busConfirmBookingPage.fillGuestDetails(
				travellers,
				contact,
				email
				);

		//Payment
		busConfirmBookingPage.confirmBooking();
		//Assertion
		AssertUtil.checkAssertion(busConfirmBookingPage.paymentWindowActive(),true,"Payment Form Inactive");
	}

	@Test(priority=3,dataProvider="oneWayBookingData",description="Testing Bus Packages functionality")
	public void testPackage(String from,String to,String deptDate,String noOfSeats,String boarding, String dropping, String contact, String email,String travellers[][]) {	//Packages
		//Booking Details
		BusHomePage busHomePage = new BusHomePage(driver);
		busHomePage.searchPackages(from, to, deptDate);
		//Assertion
		AssertUtil.checkAssertion(driver.getTitle(), "Bus Results","Invalid data");

		//Seat Occupation
		BusResultPage busResultPage = new BusResultPage(driver);
		AssertUtil.checkAssertion(busResultPage.resultNotFound(), true, "No buses for this route. Search modification needed !");
		//busResultPage.viewSeats();
		boolean seatSelected=false;
		int busNumber=0;
		while(!seatSelected) {
			try {
				int returnVal = busResultPage.selectSeats(Integer.parseInt(noOfSeats),boarding, dropping, busNumber);
				if(returnVal == -1) {
					testLog.error("No Busses found");
					Assert.fail("No Busses found");
				}
				else if (returnVal == 0) {
					testLog.error("Seats not visible");
					Assert.fail("Seats not visible");
				}
				seatSelected=true;
			} catch (NotEnoughSeatsException e) {
				e.printStackTrace();
				busNumber++;
			}
		}
		//Assertion
		AssertUtil.checkAssertion(busResultPage.isSelectionSuccessful(), true, "Unable to proceed to pay for confirmation of booking");

		//Traveler Details
		BusConfirmBookingPage busConfirmBookingPage = new BusConfirmBookingPage(driver);
		busConfirmBookingPage.fillGuestDetails(
				travellers,
				contact,
				email
				);
		
		//Payment
		busConfirmBookingPage.confirmBooking();
		//Assertion
		AssertUtil.checkAssertion(busConfirmBookingPage.paymentWindowActive(),true,"Payment Form Inactive");
	}

	@DataProvider
	public Object[][] oneWayBookingData() {
		ExcelRead.excelConfig(".\\ViaBus.xlsx","BusSearch" );
		int lastCell=ExcelRead.numberOfColumns();
		int rowNumber=ExcelRead.numberOfRows();
		Object data[][]=new Object[rowNumber][lastCell];

		for(int i=1;i<=rowNumber;i++) 
			for(int j=0;j<lastCell;j++) 
				data[i-1][j]=ExcelRead.read(i,j);
		
		ExcelRead.excelConfig(".\\ViaBus.xlsx","Travellers" );
		lastCell=ExcelRead.numberOfColumns();
		rowNumber=ExcelRead.numberOfRows();

		for(int i=0;i<data.length;i++) {
			int noOfAdult=Integer.parseInt((String) data[i][data[i].length-1]);
			String[][] travellers= new String[noOfAdult][lastCell];

			//Adults
			for(int j=0;j<noOfAdult;j++) { 
				for(int k=0;k<lastCell;k++)  
					travellers[j][k] = ExcelRead.read(1+j,k);
			}

			data[i][data[i].length-1]=travellers;
		}
		return data;
	}

	@DataProvider
	public Object[][] returnTripBookingData() {
		ExcelRead.excelConfig(".\\ViaBus.xlsx","ReturnSearch" );
		int lastCell=ExcelRead.numberOfColumns();
		int rowNumber=ExcelRead.numberOfRows();
		Object data[][]=new Object[rowNumber][lastCell];

		for(int i=1;i<=rowNumber;i++) {
			for(int j=0;j<lastCell;j++) {
				data[i-1][j]=ExcelRead.read(i,j);
			}
		}

		ExcelRead.excelConfig(".\\ViaBus.xlsx","Travellers" );
		lastCell=ExcelRead.numberOfColumns();
		rowNumber=ExcelRead.numberOfRows();

		for(int i=0;i<data.length;i++) {
			int noOfAdult=Integer.parseInt((String) data[i][data[i].length-1]);
			String[][] travellers= new String[noOfAdult][lastCell];

			//Adults
			for(int j=0;j<noOfAdult;j++) { 
				for(int k=0;k<lastCell;k++)  
					travellers[j][k] = ExcelRead.read(1+j,k);
			}

			data[i][data[i].length-1]=travellers;
		}
		return data;
	}

	@BeforeMethod
	public void setHome() {
		driver.get("https://in.via.com/bus-tickets");
	}

	@BeforeTest
	public void beforeTest() {
		//Preconditions
	}

	@AfterTest
	public void afterTest() {
		//PostConditions
	}
}