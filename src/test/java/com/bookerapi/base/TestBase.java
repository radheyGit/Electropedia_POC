package com.bookerapi.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.annotations.BeforeClass;

import com.bookerapi.constants.AppConstants;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class TestBase {

	public static RequestSpecification httpRequest;
	public static Response response;
	public String empID="51838";
	public static Logger log;
	public static Properties prop;
	
	
	public TestBase() {
		super();
		setUp();
		try {
			FileInputStream fi = new FileInputStream(AppConstants.CONFIG_PPROPERTY_FILEPATH);
			prop = new Properties();
			prop.load(fi);
		} catch (Exception e) {
			log.error("Error Occured at Test Base Class "+e.getMessage());
		}
		
	}

	public void setUp() {
		log = Logger.getLogger("BookerAPI");
		PropertyConfigurator.configure("log4j.properties");
		log.setLevel(Level.DEBUG);
	}
}
