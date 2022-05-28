package com.bookerapi.listeners;

import org.testng.ITestListener;
import org.testng.ITestResult;

import com.bookerapi.base.TestBase;

public class Listeners extends TestBase implements ITestListener{

	/***
	 * Invoked each time a test fails.
	 */
	public void onTestFailure(ITestResult result) {
		log.info("*****"+result.getName()+" Test is Fail*****");
	}
	
	/***
	 * Invoked each time a test is skipped.
	 */
	public void onTestSkipped(ITestResult result) {
		log.info("*****"+result.getName()+" Test is Skipped *****");
	}
	
	/***
	 * Invoked each time before a test will be invoked.
	 */
	public void onTestStart(ITestResult result) {
		log.info("*****"+result.getName()+" Test is Start*****");
	}
	
	/***
	 * Invoked each time a test succeeds.
	 */
	public void onTestSuccess(ITestResult result) {
		log.info("*****"+result.getName()+" Test is Successfull*****");
	}
}
