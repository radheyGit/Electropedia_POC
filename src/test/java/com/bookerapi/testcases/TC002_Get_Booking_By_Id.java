package com.bookerapi.testcases;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.bookerapi.base.TestBase;
import com.bookerapi.util.MyRetry;

import io.restassured.RestAssured;
import io.restassured.response.ResponseBody;

public class TC002_Get_Booking_By_Id extends TestBase{
	
	@BeforeClass
	public void getBookById() throws InterruptedException {
		log.info("**********Starting TC002_Get_All_Booking_Ids***********");
		RestAssured.baseURI = "https://restful-booker.herokuapp.com";
		httpRequest = RestAssured.given();
		response = httpRequest.accept("application/json")
							  .get("/booking/15");
		Thread.sleep(5000);
	}
	
	@Test(retryAnalyzer = MyRetry.class)
	public void checkResponseBody() {
		log.info("**********TC002_checkResponseBody***********");
		ResponseBody body = response.getBody();
		log.info("Response Body "+body.jsonPath().prettify());
		String firstName = body.jsonPath().get("firstname").toString();
		Assert.assertTrue(firstName.equalsIgnoreCase("Sally"));
	}
	
	@Test(retryAnalyzer = MyRetry.class)
	public void checkStatusCode() {
		log.info("**********TC002_checkStatusCode***********");
		int statusCode = response.getStatusCode();
		log.info("Status Code "+statusCode);
		Assert.assertEquals(statusCode, 200);
	}
	
	@Test(retryAnalyzer = MyRetry.class)
	private void checkResponseTime() {
		log.info("**********TC002_checkResponseTime***********");
		long time = response.getTime();
		if(time>2000)
			log.warn("Response Time is greater than 2000 "+time);
		Assert.assertTrue(time<2000);
	}
	
	@Test(retryAnalyzer = MyRetry.class)
	public void checkStatusLine() {
		log.info("**********TC002_checkStatusLine***********");
		String statusLine = response.getStatusLine();
		log.info("Status Line "+statusLine);
		Assert.assertEquals(statusLine, "HTTP/1.1 200 OK");
	}
	
	@Test(retryAnalyzer = MyRetry.class)
	public void checkContententType() {
		log.info("**********TC002_checkContententType***********");
		String contentType = response.contentType();
		log.info("Content Type "+contentType);
		Assert.assertEquals(contentType, "application/json; charset=utf-8");
	}
	
	@Test(retryAnalyzer = MyRetry.class)
	public void checkContentLength() {
		log.info("**********TC002_checkContentLength***********");
		String contentLength = response.header("Content-Length");
		log.info("Content Leanth "+contentLength);
		Assert.assertTrue(Integer.parseInt(contentLength)<1500);
	}
	
	@Test(retryAnalyzer = MyRetry.class)
	public void tearDown() {
		log.info("**********Finished TC002***********");
	}
}
