package com.bookerapi.testcases;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.bookerapi.base.TestBase;
import com.bookerapi.util.MyRetry;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class TC004_Update_Booking extends TestBase{

	@BeforeMethod
	public void updateCooking() throws InterruptedException {

		JSONObject cred = new JSONObject();
		cred.put("username", "admin");
		cred.put("password", "password123");

		log.info("**********Starting TC004_Update_Booking***********");
		RestAssured.baseURI = "https://restful-booker.herokuapp.com";

		httpRequest = RestAssured.given();
		response = httpRequest.contentType("application/json")
							  .accept("application/json")
							  .body(cred)
							  .post("/auth");
		System.out.println(response.getBody().jsonPath().prettify());

		Thread.sleep(5000);
	}
	
	@Test(retryAnalyzer = MyRetry.class,priority = 0)
	public void checkResponseBody() {
		log.info("**********TC003_checkResponseBody***********");
		Response response = RestAssured.given()
				   					   .contentType("application/json")
									   .accept("application/json")
									   .auth().preemptive()
									   .basic("admin","password123")
									   .body("{\r\n"
										   + "    \"firstname\" : \"James\",\r\n"
										   + "    \"lastname\" : \"Brown\",\r\n"
										   + "    \"totalprice\" : 111,\r\n"
										   + "    \"depositpaid\" : true,\r\n"
										   + "    \"bookingdates\" : {\r\n"
										   + "        \"checkin\" : \"2018-01-01\",\r\n"
										   + "        \"checkout\" : \"2019-01-01\"\r\n"
										   + "    },\r\n"
										   + "    \"additionalneeds\" : \"Breakfast\"\r\n"
										   + "}")
									   .put("/booking/15");
		
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
}
