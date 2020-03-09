package com.via.base;

import org.testng.annotations.Test;
import com.via.base.BaseTestClass;
import com.via.base.ExcelRead;
import com.via.pom.SignInPage;

import org.testng.Assert;
import org.testng.annotations.DataProvider;

public class SignInTest extends BaseTestClass{
  
	@Test(dataProvider="login")
	public void testSignIn(String email,String password) {
		SignInPage signIn=new SignInPage(driver);
		signIn.accountLogin(	email, 
											password);
		//Assert
		Assert.assertTrue(signIn.signInSuccessful());
		signIn.logOut();
	
	  }
  
  @DataProvider
  public Object[][] login() {
	ExcelRead.excelConfig(".\\SignUpDataProvider.xlsx","SignIn" );
	  int rows = ExcelRead.numberOfColumns();
	  int columns = ExcelRead.numberOfRows();
	  Object data[][]=new Object[rows][columns];
	  
	  for(int i=1;i<=rows;i++) {
		  for(int j=0;j<columns;j++) {
			  data[i-1][j]=ExcelRead.read(i,j);
		  }
	  }
	  return data;
}
}
