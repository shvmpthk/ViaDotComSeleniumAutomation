package com.via.holiday;

public class HolidayRoom 
{
	private String[] childBedInfo;
	private int adult,child;

	public int getAdult() {
		return adult;
	}

	public void setAdult(int adult) {
		this.adult = adult;
	}

	public int getChild() {
		return child;
	}

	public void setChild(int child) {
		this.child = child;
	}
	public String[] getChildBedInfo() {
		return childBedInfo;
	}

	public void setChildBedInfo(String[] childBedInfo) {
		this.childBedInfo = childBedInfo;
	}
	public void setChildBedInfoSize(int i)
	{
		this.childBedInfo=new String[i];
	}
	public void setChildBedInfoData(int i,String data)
	{
		this.childBedInfo[i]=data;
	}

	
}