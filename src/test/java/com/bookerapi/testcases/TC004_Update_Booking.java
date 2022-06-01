package com.bookerapi.testcases;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.bookerapi.base.TestBase;
import com.bookerapi.util.MyRetry;
import com.bookerapi.util.XLUtils;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

//@Listeners(com.stockaccounting.listeners.Listeners.class)
public class TC004_Update_Booking extends TestBase{
	
	String id;
	public TC004_Update_Booking() {
		RestAssured.baseURI = prop.getProperty("baseURI");
		RequestSpecification httpRequest1 = RestAssured.given();
		Response response1 = httpRequest1.accept("application/json")
							  			.get("/booking");
		id = response1.getBody().jsonPath().getString("[0].bookingid");
	}

	@BeforeMethod
	public void updateBooking() throws InterruptedException {

		JSONObject cred = new JSONObject();
		cred.put("username", "admin");
		cred.put("password", "password123");

		log.info("**********Starting TC004_Update_Booking***********");
		RestAssured.baseURI = prop.getProperty("baseURI");

		httpRequest = RestAssured.given();
		response = httpRequest.contentType("application/json")
							  .accept("application/json")
							  .body(cred)
							  .post("/auth");
		System.out.println(response.getBody().jsonPath().prettify());

		Thread.sleep(5000);
	}
	
	@Test(retryAnalyzer = MyRetry.class,priority = 0,dataProvider = "bookerDataProvider")
	public void checkResponseBody_TC004(String firstName,String lastName,String totalPrice,String depositPaid, String checkin,String checkout,String additionalNeeds) {
		Response response = RestAssured.given()
				   					   .contentType("application/json")
									   .accept("application/json")
									   .auth().preemptive()
									   .basic("admin","password123")
									   .body("{\r\n"
										   		+ "    \"firstname\": \""+firstName.trim()+"\",\r\n"
										   		+ "    \"lastname\": \""+lastName.trim()+"\",\r\n"
										   		+ "    \"totalprice\": "+totalPrice.trim()+",\r\n"
										   		+ "    \"depositpaid\": "+depositPaid.trim()+",\r\n"
										   		+ "    \"bookingdates\": {\r\n"
										   		+ "        \"checkin\": \""+checkin.trim()+"\",\r\n"
										   		+ "        \"checkout\": \""+checkout.trim()+"\"\r\n"
										   		+ "    },\r\n"
										   		+ "    \"additionalneeds\": \""+additionalNeeds.trim()+"\"\r\n"
										   		+ "}")
									   .put("/booking/"+id+"");
		
		log.info("Json Response " + response.getBody().jsonPath().prettify());
		String body = response.getBody().asString();
		
		  Assert.assertEquals(body.contains("firstname"), true);
		  Assert.assertEquals(body.contains("lastname"), true);
		  Assert.assertEquals(body.contains("totalprice"), true);
		  Assert.assertEquals(body.contains("depositpaid"), true);
		  Assert.assertEquals(body.contains("bookingdates"), true);
		  Assert.assertEquals(body.contains("checkin"), true);
		  Assert.assertEquals(body.contains("checkout"), true);
		  Assert.assertEquals(body.contains("additionalneeds"), true);
	}
	
	/***
	 * @author Radhey
	 * @return String [][]
	 */
	@DataProvider(name = "bookerDataProvider")
	public String [][] getBookerData() {
		String [][] bookerData = {{"Radhey","Garode","10000","true","2020-03-21","2022-09-26","Dinner"}};
		return bookerData;
	}
	
	@Test(retryAnalyzer = MyRetry.class)
	public void checkStatusCode_TC004() {
		int statusCode = response.getStatusCode();
		log.info("Status Code "+statusCode);
		Assert.assertEquals(statusCode, 200);
	}
	
	@Test(retryAnalyzer = MyRetry.class)
	public void checkStatusLine_TC004() {
		String statusLine = response.getStatusLine();
		log.info("Status Line "+statusLine);
		Assert.assertEquals(statusLine, "HTTP/1.1 200 OK");
	}
	
	@Test(retryAnalyzer = MyRetry.class)
	public void checkContentType_TC004() {
		String contentType = response.contentType();
		log.info("Content Type "+contentType);
		Assert.assertEquals(contentType, "application/json; charset=utf-8");
	}
	
	@Test(retryAnalyzer = MyRetry.class)
	public void checkServerType_TC004() {
		String serverType = response.header("Server");
		log.info("Server Type "+serverType);
		Assert.assertEquals(serverType, "Cowboy");
	}
	
	@Test(retryAnalyzer = MyRetry.class)
	public void checkContentEncoder_TC004() {
		String contentEncoder = response.header("Content-Encoder");
		log.info("Content Encoder " + contentEncoder);
		Assert.assertEquals(contentEncoder, null);
	}
	
	@AfterClass
	public void tearDown() {
		log.info("**********Finished TC004_Put_CreateBooking***********");
	}
	
}
