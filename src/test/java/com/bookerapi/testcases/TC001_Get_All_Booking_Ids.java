package com.bookerapi.testcases;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.bookerapi.base.TestBase;
import com.bookerapi.util.MyRetry;

import io.restassured.RestAssured;

public class TC001_Get_All_Booking_Ids extends TestBase{

	@BeforeClass
	public void getAllBookingIds() throws InterruptedException {
		log.info("**********Starting TC001_Get_All_Booking_Ids***********");
		RestAssured.baseURI = "https://restful-booker.herokuapp.com";
		httpRequest = RestAssured.given();
		response = httpRequest.accept("application/json")
							  .get("/booking");
		Thread.sleep(5000);
	}
	
	@Test(retryAnalyzer = MyRetry.class)
	public void checkResponseBody() {
		log.info("**********TC001_checkResponseBody***********");
		String responseBody = response.getBody().jsonPath().prettify();
		log.info("ResponseBody "+responseBody);
		Assert.assertTrue(responseBody!=null);
	}
	
	@Test(retryAnalyzer = MyRetry.class)
	public void checkStatusCode() {
		log.info("**********TC001_checkStatusCode***********");
		int statusCode = response.getStatusCode();
		log.info("Status Code "+statusCode);
		Assert.assertEquals(statusCode, 200);
	}
	
	@Test(retryAnalyzer = MyRetry.class)
	public void checkResponseTime() {
		log.info("**********TC001_checkResponseTime***********");
		long time = response.getTime();
		if(time>2000) 
			log.warn("Response Time is greater then 2000" +time);
		Assert.assertTrue(time<2500);
	}
	
	@Test(retryAnalyzer = MyRetry.class)
	public void checkStatusLine() {
		log.info("**********TC001_checkStatusLine***********");
		String statusLine = response.getStatusLine();
		log.info("Status Line "+statusLine);
		Assert.assertEquals(statusLine, "HTTP/1.1 200 OK");
	}
	
	@Test(retryAnalyzer = MyRetry.class)
	public void checkContentType() {
		log.info("**********TC001_checkContentType***********");
		String contentType = response.contentType();
		log.info("Content-Type "+contentType);
		Assert.assertEquals(contentType, "application/json; charset=utf-8");
	}
	
	@Test(retryAnalyzer = MyRetry.class)
	public void checkServerType() {
		log.info("**********TC001_checkServerType***********");
		String serverType = response.header("Server");
		log.info("Server Type is "+serverType);
		Assert.assertEquals(serverType, "Cowboy");
	}
	
	@Test(retryAnalyzer = MyRetry.class)
	public void checkContentEncoding() {
		log.info("**********TC001_checkContentEncoding***********");
		String contentEncoding = response.header("Content-Encoding");
		log.info("Content Encoding is "+contentEncoding);
		Assert.assertEquals(contentEncoding, "gzip");
	}
	
	@Test(retryAnalyzer = MyRetry.class)
	public void checkContentLength() {
		log.info("**********TC001_checkContentLength***********");
        String contentLength = response.header("Content-Length");
        if(Integer.parseInt(contentLength)<100)
        	log.warn("Content Length is less then 100 "+contentLength);
        Assert.assertTrue(Integer.parseInt(contentLength)>100);
	}
	
	@Test(retryAnalyzer = MyRetry.class)
	public void checkCookies() {
		log.info("**********TC001_checkCookies***********");
		Map<String, String> cookies = response.getCookies();
		log.info(cookies);
	}
	
	@AfterClass
	public void tearDown() {
		log.info("**********Finished TC001_Get_All_Booking_Ids***********");
	}
}
