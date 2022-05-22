package com.bookerapi.testcases;

import org.json.simple.JSONObject;
import org.junit.BeforeClass;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.bookerapi.base.TestBase;
import com.bookerapi.util.MyRetry;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class TC003_Post_CreateBooking extends TestBase {

	@BeforeMethod
	public void createBooking() throws InterruptedException {

		JSONObject cred = new JSONObject();
		cred.put("username", "admin");
		cred.put("password", "password123");

		log.info("**********Starting TC003_Post_CreateBooking***********");
		RestAssured.baseURI = "https://restful-booker.herokuapp.com";

		httpRequest = RestAssured.given();
		response = httpRequest.contentType("application/json").accept("application/json").body(cred).post("/auth");
		System.out.println(response.getBody().jsonPath().prettify());

		Thread.sleep(5000);
	}

	@Test(retryAnalyzer = MyRetry.class,priority = 0)
	public void checkResponseBody() {
		log.info("**********TC003_checkResponseBody***********");
		Response response = RestAssured.given()
				   					   .contentType("application/json")
				   					   .accept("application/json")
				   					   .body("{\r\n"
									   		+ "    \"firstname\": \"Radhey\",\r\n"
									   		+ "    \"lastname\": \"Garode\",\r\n"
									   		+ "    \"totalprice\": 50000.00,\r\n"
									   		+ "    \"depositpaid\": true,\r\n"
									   		+ "    \"bookingdates\": {\r\n"
									   		+ "        \"checkin\": \"2022-03-21\",\r\n"
									   		+ "        \"checkout\": \"2022-09-26\"\r\n"
									   		+ "    },\r\n"
									   		+ "    \"additionalneeds\": \"Dinner\"\r\n"
									   		+ "}")
				   					   .post("/booking");
		log.info("Json Response " + response.getBody().jsonPath().prettify());
		JsonPath jsonPath = response.getBody().jsonPath();
		Assert.assertEquals(jsonPath.get("firstname"), true);
		Assert.assertEquals(jsonPath.get("lastname"), true);
		Assert.assertEquals(jsonPath.get("totalprice"), true);
		Assert.assertEquals(jsonPath.get("depositpaid"), true);
		Assert.assertEquals(jsonPath.get("bookingdates"), true);
		Assert.assertEquals(jsonPath.get("checkin"), true);
		Assert.assertEquals(jsonPath.get("checkout"), true);
		Assert.assertEquals(jsonPath.get("additionalneeds"), true);
	}

	@Test(retryAnalyzer = MyRetry.class)
	public void checkStatusCode() {
		log.info("**********TC003_checkStatusCode***********");
		int statusCode = response.getStatusCode();
		log.info("Status Code " + statusCode);
		Assert.assertEquals(statusCode, 200);
	}

	@Test(retryAnalyzer = MyRetry.class)
	public void checkStatusLine() {
		log.info("**********TC003_checkStatusLine***********");
		String statusLine = response.getStatusLine();
		log.info("Status Line " + statusLine);
		Assert.assertEquals(statusLine, "HTTP/1.1 200 OK");
	}

	@Test(retryAnalyzer = MyRetry.class)
	public void checkContentType() {
		log.info("**********TC003_checkContentType***********");
		String contentType = response.contentType();
		log.info("content Type " + contentType);
		Assert.assertEquals(contentType, "application/json; charset=utf-8");
	}

	@Test(retryAnalyzer = MyRetry.class)
	public void checkServerType() {
		log.info("**********TC003_checkServerType***********");
		String server = response.header("Server");
		log.info("Server " + server);
		Assert.assertEquals(server, "Cowboy");
	}

	@Test(retryAnalyzer = MyRetry.class)
	public void checkContentEncoder() {
		log.info("**********TC003_checkContentEncoder***********");
		String contentEncoder = response.header("Content-Encoder");
		log.info("Content Encoder " + contentEncoder);
		Assert.assertEquals(contentEncoder, null);
	}

	@AfterClass
	public void tearDown() {
		log.info("**********Finished TC003_Post_CreateBooking***********");
	}
}
