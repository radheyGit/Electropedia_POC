/***
 * @author Radhey
 */
package org.electropedia.base;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.electropedia.constants.AppConstants;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestBase {

	public static Logger log;
	public static Properties prop;
	public static WebDriver driver;
	
	public TestBase() {
		super();
		try {
			FileInputStream fi = new FileInputStream(AppConstants.CONFIG_PPROPERTY_FILEPATH);
			prop = new Properties();
			prop.load(fi);
		} catch (Exception e) {
			log.error("Error Occured at Test Base Class "+e.getMessage());
		}
		
	}
	
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "BrowserPlugins/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(prop.getProperty("Url"));
	}

	public void loggerSetUp() {
		log = Logger.getLogger("Electropedia");
		PropertyConfigurator.configure("log4j.properties");
		log.setLevel(Level.DEBUG);
	}
}
