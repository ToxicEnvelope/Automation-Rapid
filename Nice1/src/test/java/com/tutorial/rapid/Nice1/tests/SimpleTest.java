package com.tutorial.rapid.Nice1.tests;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.tutorial.rapid.Nice1.core.pageobjects.DynamicLoadingPage;

public class SimpleTest {

	// Fields
	private WebDriver driver;
	private static final String baseUrl = "http://the-internet.herokuapp.com/dynamic_loading/2";
	private static final String ajaxSrc = "https://www.w3schools.com/js/tryit.asp?filename=tryjs_ajax_first";
	private static final String EXPECTED_STRING_1 = "Hello World!";
	private static final String EXPECTED_STRING_2 = "Hey World!";
	private DynamicLoadingPage dlp;
	
	
	// Before the Class runs
	@BeforeClass
	public void setUp() {
		try {
			// set driver-engine and webdriver path 
			String engine = "webdriver.chrome.driver",
				   wdPath = System.getProperty("user.dir") + "/src/exec/chromedriver";
			System.setProperty(engine, wdPath);
			// reference to ChromeOptions object
			// force the browser to start maximized
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--start-maximized");
			// apply options on WebDriver object
			driver = new ChromeDriver(options);
			// wait 2.5 seconds
			driver.manage().timeouts().implicitlyWait(2500, TimeUnit.MILLISECONDS);
			// navigate to base url
			driver.get(baseUrl);
		}
		// when exception ....
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// After the Class runs
	@AfterClass
	public void tearDown() {
		if(driver != null) 
		{
			// kill driver
			driver.quit();
		}
	}
	
	@Test(priority=0)
	public void _POSITIVE_TEST_() {
		System.out.println("\n++++++++++++ POSITIVE TEST ++++++++++++");
		this.dlp = new DynamicLoadingPage(driver);
		// try to click on start button
		dlp.attemptToClickOnStartButton();
		// get the string 
		String ACTUAL_STRING = dlp.attempToGetHelloWorldText();
		// check the ajax
		System.out.println("\n++++++++++++ AJAX TEST ++++++++++++");
		Assert.assertTrue(dlp.isAjaxCompleted(ajaxSrc));
		// verify the text 
		Assert.assertEquals(ACTUAL_STRING, EXPECTED_STRING_1);
	}
	
	@Test(priority=1)
	public void _REFRESH_SEQUENCE_() throws InterruptedException {
		System.out.println("\n++++++++++++ REFRESH ++++++++++++");
        dlp.refreshPage();
	}
	
	@Test(priority=2)
	public void _NEGATIVE_TEST_() {
		System.out.println("\n++++++++++++ NEGATIVE TEST ++++++++++++");
		// try to click on start button
		this.dlp = new DynamicLoadingPage(driver);
		dlp.attemptToClickOnStartButton();
		WebElement elem = dlp.attempToGetHelloWorldTextAndCapture();
		String ACTUAL_STRING = elem.getText();
		// verify the text
		Assert.assertEquals(ACTUAL_STRING, EXPECTED_STRING_2);
	}
	
}
