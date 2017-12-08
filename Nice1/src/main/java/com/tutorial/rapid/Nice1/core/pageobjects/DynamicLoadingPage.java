package com.tutorial.rapid.Nice1.core.pageobjects;


import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DynamicLoadingPage extends AbstractDynamicLoadingPage {

	// Fields
	@FindBy(css = "div#loading")
	private WebElement loader;
	private WebElement msgElem;
	private WebElement changeContentBtn_AJAX;
	
	// Constructor
	public DynamicLoadingPage(WebDriver driver) {
		super(driver);
	}

	// tries to return the text value using .getText() method
	public String attempToGetHelloWorldText() {
		try {
			// identify the loader
			this.loader = waitUntilElementLocatedByCSS("div#loading");
			System.out.println("Loader: " + loader);
			// as long as loader object is displayed 
			while(loader.isDisplayed()) {
				// ait a.5 seconds
				wait(1500);
				// identify the actual message from screen
				this.msgElem = waitUntilElementPresenceByCSS("div#finish > h4");
				System.out.println("Message Element: " + msgElem);
			}
			System.out.println("Text: " + msgElem.getText());
			// return the value
			return msgElem.getText();
		}
		// when exception accrues
		catch (Exception e) {
			e.printStackTrace();
			return "CASE FAILED : " + e.getCause();
		}
	}
	
	// attempt to return a text with red border and a screenshot  
	public WebElement attempToGetHelloWorldTextAndCapture() {
		try {
			// identify the loader
			this.loader = waitUntilElementLocatedByCSS("div#loading");
			System.out.println("Loader: " + loader);
			// as long as displayed
			while(loader.isDisplayed()) {
				// wait 1.5 seconds
				wait(1500);
				// identify the actual message from screen
				this.msgElem = waitUntilElementPresenceByCSS("div#finish > h4");
				System.out.println("Message Element: " + msgElem);
			}
			// inject border and take a screenshot
			highlightAndCapture(msgElem);
			// return the WebElement 
			return msgElem;
		}
		// when exception accrues
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	// Inherited method by BasePage
	@Override
	protected void highlightAndCapture(WebElement el) {
		// when exists
		if(el.isDisplayed())
		{
			// inject red border to passed WebElement 
			_js.executeScript("arguments[0].setAttribute('style','border: 2px solid red');", el);
			// takes a screenshot  (Inherited Method by BasePage) 
			snapShot();
		}
	}

	// Implemented method which inherited by BasePage : IDoStuff Interface
	// Reference to Online Source: 
	//					https://www.guru99.com/handling-ajax-call-selenium-webdriver.html
	public boolean navigateTo(String s) {
		try {
			// open new tab
			waitUntilElementPresenceByCSS("body").sendKeys(Keys.CONTROL + "t");
			// wait 1.5 seconds
			wait(1500);
			// create reference to list of tabs using the WebDriver window handler
			ArrayList<String> tabs = new ArrayList<String>(_driver.getWindowHandles());
			// get the first index  ( First Tab)
			// Reference to Online Source: 
			// 			https://seleniumhq.github.io/selenium/docs/api/java/org/openqa/selenium/WebDriver.TargetLocator.html#window-java.lang.String-
			_driver.switchTo().window(tabs.get(0));
			// navigate to a given url
			_driver.navigate().to(s);
			// switch to the 2nd frame object out of 3.
			_driver.switchTo().frame(1);
			// identify the button inside the frame
			this.changeContentBtn_AJAX = _driver.findElement(By.xpath("//*[@id='demo']/button"));
			// click on it
			click(changeContentBtn_AJAX);
			// navigate backwards
			_driver.navigate().back();
			// focus on the default frame 
			_driver.switchTo().defaultContent();
			return true;
		} 
		// when exception accrues
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// check if the AJAX call was completed
	public boolean isAjaxCompleted(String src) {
		// return boolean if received boolean as a result
		if (navigateTo(src)) {
			return true;
		}
		else {
			return false;
		}
	}
}