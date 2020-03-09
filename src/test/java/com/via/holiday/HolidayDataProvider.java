package com.via.holiday;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import org.testng.annotations.DataProvider;
import com.via.base.ExcelRead;

public class HolidayDataProvider {
	
@DataProvider(name="holidayDataProvider")
	public Object[] dataprovider() {
		
		Properties properties=new Properties();
		try {
			properties.load(new FileInputStream(".\\Constants.property"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ExcelRead.excelConfig(properties.getProperty("excelPathHolidayData"),
		properties.getProperty("holidayDestinationAndDateSheet"));
		HolidayData holiday[] = new HolidayData[ExcelRead.numberOfRows()];
		
		int totalHolidayPackages=+ExcelRead.numberOfRows();//---------
		
		for(int packageNo=0;packageNo<totalHolidayPackages;packageNo++) {
			
			holiday[packageNo] = new HolidayData();
			holiday[packageNo].setDestination(ExcelRead.read(packageNo+1, 0));
			holiday[packageNo].setDate(ExcelRead.read(packageNo+1, 1)); 	
			int  total_room =Integer.parseInt(ExcelRead.read(packageNo+1, 2));
			HolidayRoom[] roomsinfo=new HolidayRoom[total_room];
			String adultDetailsHolder[]=ExcelRead.read(packageNo+1, 3).split(",");
			String childDetailsHolder[]=ExcelRead.read(packageNo+1, 4).split(",");
			
			String childBedInfoDetailsHolder[]=ExcelRead.read(packageNo+1, 5).split(";");
			
			for (int room = 0; room < total_room; room++) {
				roomsinfo[room] = new HolidayRoom();
				roomsinfo[room].setAdult((Integer.parseInt(adultDetailsHolder[room])));
				roomsinfo[room].setChild(Integer.parseInt(childDetailsHolder[room]));
				roomsinfo[room].setChildBedInfoSize(roomsinfo[room].getChild());
				String childBedInfoDetailsHolderRoomWise[]=childBedInfoDetailsHolder[room].split(",");
				for (int child = 0; child < roomsinfo[room].getChild(); child++) {				
					roomsinfo[room].setChildBedInfoData(child,childBedInfoDetailsHolderRoomWise[child]);		
				}			
				holiday[packageNo].setRoomsinfo(roomsinfo);
			}			
		}	
			Object[] holidayObject= (Object[])holiday;
			return  holidayObject; 	
	}
}

