package com.via.util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class GuestsUtil {
	public static void selectGuests(WebElement roomBody, int rooms, int adults[], int child[], int age[][])
	{
		WebElement roomNumber = roomBody.findElement(By.id("room1"));
		WebElement adultContainer = roomNumber.findElement(By.className("adult"));
		WebElement childContainer = roomNumber.findElement(By.className("child"));
		
		while(Integer.parseInt(adultContainer.findElement(By.className("count")).getText())>1) 
			adultContainer.findElement(By.className("minus")).click();

		while(Integer.parseInt(childContainer.findElement(By.className("count")).getText())>0) 
			childContainer.findElement(By.className("minus")).click();

		for (int i = 0; i < rooms; i++) {
			roomNumber = roomBody.findElement(By.id("room" + (i+1)));
			adultContainer = roomNumber.findElement(By.className("adult"));
			childContainer = roomNumber.findElement(By.className("child"));
			
			for (int j = 1; j < adults[i]; j++)
				adultContainer.findElement(By.className("plus")).click();

			for (int j = 0; j < child[i]; j++)
				childContainer.findElement(By.className("plus")).click();
			
			for (int j = 0; j < child[i]; j++) {
				roomNumber.findElement(By.xpath("//div[@id='room"+(i+1)+"childAge"+(j+1)+"']/label/select")).sendKeys(Integer.toString(age[i][j]));
			}
		}
	}
}