package com.bookerapi.testcases;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.bookerapi.base.TestBase;
import com.bookerapi.util.MyRetry;

import io.restassured.RestAssured;
import io.restassured.response.Response;

//@Listeners(com.stockaccounting.listeners.Listeners.class)
public class TC005_Delete_Booking extends TestBase{

	@BeforeMethod
	public void deleteBooking() {
		log.info("**********Starting TC005_Delete_Booking***********");
		
		JSONObject cred = new JSONObject();
		cred.put("username", "admin");
		cred.put("password", "password123");
		
		RestAssured.baseURI = prop.getProperty("baseURI");
		httpRequest = RestAssured.given();
		response = httpRequest.contentType("application/json")
							  .accept("application/json")
							  .body(cred)
							  .post("/auth");
		System.out.println(response.getBody().jsonPath().prettify());
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/***
	 * In this test every time we need to pass new id in endpoint
	 */
	@Test(retryAnalyzer = MyRetry.class)
	public void checkResponseBody_TC005() {
		httpRequest = RestAssured.given();
		response = httpRequest.accept("application/json")
							  .get("/booking");
		String id = response.getBody().jsonPath().getString("[0].bookingid");

		Response response = RestAssured.given()
				   					   .contentType("application/json")
				   					   .accept("application/json")
				   					   .auth().preemptive()
				   					   .basic("admin","password123")
				   					   .delete("/booking/"+id+"");
		String body = response.getBody().asString();
		log.info("Response "+body);
		Assert.assertEquals(body.contains("Created"), true);
	}
	
	@Test(retryAnalyzer = MyRetry.class)
	public void checkStatusCode_TC005() {
		int statusCode = response.getStatusCode();
		log.info("Status Code "+statusCode);
		Assert.assertEquals(statusCode, 200);
	}
	
	@Test(retryAnalyzer = MyRetry.class)
	public void checkStatusLine_TC005() {
		String statusLine = response.getStatusLine();
		log.info("Status Line "+statusLine);
		Assert.assertEquals(statusLine, "HTTP/1.1 200 OK");
	}
	
	@Test(retryAnalyzer = MyRetry.class)
	public void checkContentType_TC005() {
		String contentType = response.contentType();
		log.info("Content Type "+contentType);
		Assert.assertEquals(contentType, "application/json; charset=utf-8");
	}
	
	@Test(retryAnalyzer = MyRetry.class)
	public void checkServerType_TC005() {
		String serverType = response.header("Server");
		log.info("Server Type "+serverType);
		Assert.assertEquals(serverType, "Cowboy");
	}
	
	@Test(retryAnalyzer = MyRetry.class)
	public void checkContentEncoder_TC005() {
		String contentEncoder = response.header("Content-Encoder");
		log.info("Content Encoder " + contentEncoder);
		Assert.assertEquals(contentEncoder, null);
	}
	
	@AfterClass
	public void tearDown() {
		log.info("**********Finished TC004_Put_CreateBooking***********");
	}
}
