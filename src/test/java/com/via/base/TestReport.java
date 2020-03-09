package com.via.base;

import java.io.IOException;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.via.util.CaptureUtil;

public class TestReport extends BaseTestClass implements ITestListener{
	
	public void onTestStart(ITestResult result) {
		testLog = extentReporter.createTest(result.getName());
		testLog.info(result.getName()+" Started");
	}

	public void onTestSuccess(ITestResult result) {
		testLog.pass(result.getName()+" Successful");
		try {
			testLog.info("PFA : Final Snapshot Below ");
			testLog.addScreenCaptureFromPath(CaptureUtil.getScreenshot(driver));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void onTestFailure(ITestResult result) {
		testLog.fail(result.getName()+" Unsuccessful");
		try {
			testLog.info("PFA : Final Snapshot Below ");
			testLog.addScreenCaptureFromPath(CaptureUtil.getScreenshot(driver));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void onFinish(ITestContext context) {
		testLog.info("Writing Report");
		extentReporter.flush();
	}

	public void onTestSkipped(ITestResult result) {
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
	}

	public void onStart(ITestContext context) {
	}

}