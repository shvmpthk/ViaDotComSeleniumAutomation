package com.via.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignInPage {
	WebDriver driver;
	
	public SignInPage(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//div[contains(text(),'Sign In')]")
	WebElement signIn;
	@FindBy(id="loginIdText")
	WebElement email;
	@FindBy(id="passwordText")
	WebElement pwd;
	@FindBy(id="loginValidate")
	WebElement signInBtn;
	@FindBy(xpath="//div[@class='elementPad menuLabel secNavIcon']")
	WebElement signInIcon;	
	@FindBy(linkText="Logout")
	WebElement logout;

	public void accountLogin(String emailS,String password)
	{
		signIn.click();
		email.clear();
		email.sendKeys(emailS);//"divya@gmail.com"
		pwd.clear();
		pwd.sendKeys(password);//"divya1996"
		signInBtn.click();	
	}
	public boolean signInSuccessful() {
		return driver.findElement(By.xpath("//*[@class='elementPad menuLabel secNavIcon']")).getText().contains("Hi");
	}
	public void logOut() {
		//Logout
		signInIcon.click();
		logout.click();
	}

}
