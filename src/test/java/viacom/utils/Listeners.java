package viacom.utils;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

public class Listeners extends BaseClass implements ITestListener{

	@Override
	public void onTestStart(ITestResult result) {
		System.out.println("Test case started:" +result.getName());
		Reporter.log("Test case started:" +result.getName());
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		System.out.println("Test case passed:"+result.getName());
		Reporter.log("Test case passed:"+result.getName());

	}

	@Override
	public void onTestFailure(ITestResult result) {
		System.out.println("Test case failed:"+result.getName());
		Reporter.log("Test case failed:"+result.getName());;
		takeScreenshot(result.getName());
		Reporter.log("Taking screesnhot");

	}

	@Override
	public void onTestSkipped(ITestResult result) {
		System.out.println("Test case skipped:"+result.getName());
		Reporter.log("Test case skipped:"+result.getName());
	}

	@Override
	public void onStart(ITestContext context) {
		System.out.println("Test started:"+context.getName());
	}

	@Override
	public void onFinish(ITestContext context) {
		System.out.println("Test finished:"+context.getName());
		Reporter.log("Test finished:"+context.getName());
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
	
	}
}
