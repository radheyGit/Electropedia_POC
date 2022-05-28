package com.bookerapi.testcases;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.bookerapi.base.TestBase;
import com.bookerapi.util.MyRetry;

import io.restassured.RestAssured;

//@Listeners(com.stockaccounting.listeners.Listeners.class)
public class TC001_Get_All_Booking_Ids extends TestBase{
	
	@BeforeClass
	public void getAllBookingIds(){
		log.info("**********Starting TC001_Get_All_Booking_Ids***********");
		RestAssured.baseURI = prop.getProperty("baseURI");
		httpRequest = RestAssured.given();
		response = httpRequest.accept("application/json")
							  .get("/booking");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test(retryAnalyzer = MyRetry.class, priority = 0)
	public void checkResponseBody_TC001() {
		String responseBody = response.getBody().jsonPath().prettify();
		log.info("ResponseBody "+responseBody);
		Assert.assertTrue(responseBody!=null);
	}
	
	@Test(retryAnalyzer = MyRetry.class)
	public void checkStatusCode_TC001() {
		int statusCode = response.getStatusCode();
		log.info("Status Code "+statusCode);
		Assert.assertEquals(statusCode, 200);
	}
	
	@Test(retryAnalyzer = MyRetry.class)
	public void checkResponseTime_TC001() {
		long time = response.getTime();
		if(time>3000) 
			log.warn("Response Time is greater then 2000" +time);
		Assert.assertTrue(time<2500);
	}
	
	@Test(retryAnalyzer = MyRetry.class)
	public void checkStatusLine_TC001() {
		String statusLine = response.getStatusLine();
		log.info("Status Line "+statusLine);
		Assert.assertEquals(statusLine, "HTTP/1.1 200 OK");
	}
	
	@Test(retryAnalyzer = MyRetry.class)
	public void checkContentType_TC001() {
		String contentType = response.contentType();
		log.info("Content-Type "+contentType);
		Assert.assertEquals(contentType, "application/json; charset=utf-8");
	}
	
	@Test(retryAnalyzer = MyRetry.class)
	public void checkServerType_TC001() {
		String serverType = response.header("Server");
		log.info("Server Type is "+serverType);
		Assert.assertEquals(serverType, "Cowboy");
	}
	
	@Test(retryAnalyzer = MyRetry.class)
	public void checkContentEncoding_TC001() {
		String contentEncoding = response.header("Content-Encoding");
		log.info("Content Encoding is "+contentEncoding);
		Assert.assertEquals(contentEncoding, null);
	}
	
	@Test(retryAnalyzer = MyRetry.class)
	public void checkContentLength_TC001() {
        String contentLength = response.header("Content-Length");
        if(Integer.parseInt(contentLength)<100)
        	log.warn("Content Length is less then 100 "+contentLength);
        Assert.assertTrue(Integer.parseInt(contentLength)>100);
	}
	
	@Test(retryAnalyzer = MyRetry.class)
	public void checkCookies_TC001() {
		Map<String, String> cookies = response.getCookies();
		log.info(cookies);
	}
	
	@AfterClass
	public void tearDown() {
		log.info("**********Finished TC001_Get_All_Booking_Ids***********");
	}
}
