package com.bookerapi.base;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.annotations.BeforeClass;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class TestBase {

	public static RequestSpecification httpRequest;
	public static Response response;
	public String empID="51838";
	
	public Logger log;
	
	@BeforeClass
	public void setUp() {
		log = Logger.getLogger("EmployeeRestAPI");
		PropertyConfigurator.configure("log4j.properties");
		log.setLevel(Level.DEBUG);
	}
}
