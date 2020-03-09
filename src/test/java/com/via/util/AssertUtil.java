package com.via.util;

import org.testng.Assert;

import com.via.base.BaseTestClass;

public class AssertUtil extends BaseTestClass{
	
	public static void checkAssertion(String actual,String expected,String message){
		try {
			Assert.assertEquals(actual, expected,message);
		} catch(AssertionError ae){
			testLog.info("Message : "+message);
			testLog.error("Error : "+ae.getMessage());
			throw ae;
		}
	}
	
	public static void checkAssertion(boolean actual,boolean expected,String message){
		try {
			Assert.assertEquals(actual, expected,message);
		} catch(AssertionError ae){
			testLog.info("Message : "+message);
			testLog.error("Error : "+ae.getMessage());
			throw ae;
		}
	}
}
