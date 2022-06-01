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
//@Listeners(com.stockaccounting.listeners.Listeners.class)
public class TC003_Post_CreateBooking extends TestBase {

	@BeforeMethod
	public void createBooking() throws InterruptedException {

		JSONObject cred = new JSONObject();
		cred.put("username", "admin");
		cred.put("password", "password123");

		log.info("**********Starting TC003_Post_CreateBooking***********");
		RestAssured.baseURI = prop.getProperty("baseURI");

		httpRequest = RestAssured.given();
		response = httpRequest.contentType("application/json").accept("application/json").body(cred).post("/auth");
		System.out.println(response.getBody().jsonPath().prettify());

		Thread.sleep(5000);
	}
	/***
	 * @author Radhey
	 * @param firstName
	 * @param lastName
	 * @param totalPrice
	 * @param depositPaid
	 * @param checkin
	 * @param checkout
	 * @param additionalNeeds
	 */
	@Test(retryAnalyzer = MyRetry.class,priority = 0,dataProvider = "bookerDataProvider")
	public void checkResponseBody_TC003(String firstName,String lastName,String totalPrice,String depositPaid, String checkin,String checkout,String additionalNeeds) {
		Response response = RestAssured.given()
				   					   .contentType("application/json")
				   					   .accept("application/json")
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
				   					   .post("/booking");
		log.info("Json Response " + response.getBody().jsonPath().prettify());
		String body = response.getBody().asString();
		Assert.assertEquals(body.contains(firstName),true);
		Assert.assertEquals(body.contains(lastName), true);
		Assert.assertEquals(body.contains(totalPrice), true);
		Assert.assertEquals(body.contains(depositPaid), true);
		Assert.assertEquals(body.contains(checkin), true);
		Assert.assertEquals(body.contains(checkout), true);
		Assert.assertEquals(body.contains(additionalNeeds), true);
	}
	/***
	 * This method is used to fetch data from Excel sheet and store it 
	 * into String Array
	 * @author Radhey
	 * @return String [][]
	 */
	@DataProvider(name = "bookerDataProvider")
	public String [][] getBookerData_TC003() {
		
		String xlFileName = prop.getProperty("ExcelFile");
		String xlSheetName = prop.getProperty("SheetName");
		int rowCount = XLUtils.getRowCount(xlFileName, xlSheetName);
		int cellCount = XLUtils.getCellCount(xlFileName, xlSheetName, 1);
		
		String [][] bookerData = new String [rowCount][cellCount];
		
		for(int i=1;i<=rowCount;i++) {
			for(int j=0;j<cellCount;j++) {
				bookerData[i-1][j] = XLUtils.getCellData(xlFileName, xlSheetName, i, j);
			}
		}
		/*
		String [][] bookerData = {{"Radhey","Garode","10000","true","2020-03-21","2022-09-26","Dinner"},
								  {"John","Hex","5000","true","2021-05-15","2021-05-18","Lunch"},
								  {"Roby","Smith","2200","true","2019-07-02","2019-07-10","Dinner"},
								  {"Mandy","Morgon","4800","true","2018-06-28","2018-07-03","Breakfast"}};
		*/
		return bookerData;
	}
	
	@Test(retryAnalyzer = MyRetry.class)
	public void checkStatusCode_TC003() {
		int statusCode = response.getStatusCode();
		log.info("Status Code " + statusCode);
		Assert.assertEquals(statusCode, 200);
	}

	@Test(retryAnalyzer = MyRetry.class)
	public void checkStatusLine() {
		String statusLine = response.getStatusLine();
		log.info("Status Line " + statusLine);
		Assert.assertEquals(statusLine, "HTTP/1.1 200 OK");
	}

	@Test(retryAnalyzer = MyRetry.class)
	public void checkContentType_TC003() {
		String contentType = response.contentType();
		log.info("content Type " + contentType);
		Assert.assertEquals(contentType, "application/json; charset=utf-8");
	}

	@Test(retryAnalyzer = MyRetry.class)
	public void checkServerType_TC003() {
		String server = response.header("Server");
		log.info("Server " + server);
		Assert.assertEquals(server, "Cowboy");
	}

	@Test(retryAnalyzer = MyRetry.class)
	public void checkContentEncoder_TC003() {
		String contentEncoder = response.header("Content-Encoder");
		log.info("Content Encoder " + contentEncoder);
		Assert.assertEquals(contentEncoder, null);
	}

	@AfterClass
	public void tearDown() {
		log.info("**********Finished TC003_Post_CreateBooking***********");
	}
}
