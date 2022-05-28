package com.bookerapi.listeners;

import javax.naming.spi.DirStateFactory.Result;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class Listeners_Extent_Reporter extends TestListenerAdapter{
	
	public ExtentHtmlReporter htmlReporter;
	public ExtentReports extentReports;
	public ExtentTest test;
	
	@Override
	public void onStart(ITestContext testContext) {
		htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir")+"/reports/bookerAPIReport.html");
		htmlReporter.config().setDocumentTitle("Booker API Automation Report");
		htmlReporter.config().setReportName("Booker API Automation Test Report");
		htmlReporter.config().setTheme(Theme.DARK);
		
		extentReports = new ExtentReports();
		extentReports.attachReporter(htmlReporter);
		extentReports.setSystemInfo("Project Name", "Bookers API Automation");
		extentReports.setSystemInfo("Host name", "localhost");
		extentReports.setSystemInfo("Environement", "QA");
		extentReports.setSystemInfo("user", "Radhey");
	}
	
	@Override
	public void onTestSuccess(ITestResult result) {
		test = extentReports.createTest(result.getName());//creating new entry in the report
		test.log(Status.PASS, "Test Case PASSED is "+result.getName());
	}

	@Override
	public void onTestFailure(ITestResult result) {
		test = extentReports.createTest(result.getName());
		test.log(Status.FAIL, "Test Case FAILED is "+result.getName());//to add name in extent report
		test.log(Status.FAIL, "Test Case FAILED is "+result.getThrowable());//to add error/exception in extent report
	}
	
	@Override
	public void onTestSkipped(ITestResult result) {
		test = extentReports.createTest(result.getName());
		test.log(Status.SKIP, "Test Case SKIPPED is "+result.getName());
	}
	
	@Override
	public void onFinish(ITestContext testContext) {
		extentReports.flush();
	}
}
