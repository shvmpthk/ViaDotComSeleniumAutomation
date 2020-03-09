package com.via.holiday;

public class HolidayData {
	private HolidayRoom[] roomsinfo;
	private String destination;
	private String date;
	
	public HolidayRoom[] getRoomsinfo() {
		return roomsinfo;
	}
	public void setRoomsinfo(HolidayRoom[] roomsinfo) {
		this.roomsinfo = roomsinfo;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
}
