package com.tutorial.rapid.Nice1.core;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.tutorial.rapid.Nice1.utilities.IDoStuff;

public abstract class BasePage implements IDoStuff {

	// Fields
	protected WebDriver _driver;
	protected JavascriptExecutor _js;
	private TakesScreenshot screen;
	
	// Constructor
	// Given WebDriver object 
	public BasePage(WebDriver driver) {
		this._driver = driver;
		// initialized PageFactory on each page child
		PageFactory.initElements(driver, this);
	}
	
	// fillText -> send keys into an input field element  
	protected void fillText(WebElement el, String word) {
		// create a reference of JavascriptExecutor
		this._js = (JavascriptExecutor) _driver;
		// as long as displayed
		if(el.isDisplayed()) {
			// as long as empty field text
			if(el.getText().isEmpty()) 
			{	
				// inject border
				_js.executeScript("arguments[0].setAttribute('style','border: 2px solid green');", el);
				el.sendKeys(word);
				System.out.println("Sent Keys: " + word);
			}
			// otherwise
			_js.executeScript("arguments[0].setAttribute('style','border: 2px solid orange');", el);
			el.clear();
			_js.executeScript("arguments[0].setAttribute('style','border: 2px solid yellow');", el);
			el.sendKeys(word);
			System.out.println("Sent Keys: " + word);
		}	
	}
	
	// click -> clicks on HTML tag object 
	protected void click(WebElement el) {
		try {
			this._js = (JavascriptExecutor) _driver;
			// inject border
			_js.executeScript("arguments[0].setAttribute('style','border: 2px solid blue');", el);
			el.click();
			System.out.println("CLICKED: " + el);
		}
		// in case of un-clickable exception
		catch (WebDriverException e) {
			// try to click using JS
			e.printStackTrace();
			this._js = (JavascriptExecutor) _driver;
			// inject border
			_js.executeScript("arguments[0].setAttribute('style','border: 2px solid pink');", el);
			// enforce click via DOM with JavascriptExecutor
			_js.executeScript("arguments[0].click()", el);
			System.out.println("CLICKED: " + el);
		}
	}
	
	// Abstract method to be implement
	protected abstract void highlightAndCapture(WebElement element);
	
	// wait -> wait for the WebDriver object the wait 'x' seconds given milliseconds measure
	protected void wait(int mSeconds) {
		try {
			Thread.sleep(mSeconds);
		} 
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	// refresh browser's page
	public void refreshPage() {
		try {
			// via WebDriver object
			_driver.navigate().refresh();
			wait(5000);
		}
		// in case of unable to refresh 
		catch (Exception e) {
			this._js = (JavascriptExecutor) _driver;
			// enforce refresh via DOM using JavascriptExecutor
			_js.executeScript("window.location.reload()");
			wait(5000);
		}
	}
	
	// snapShot -> take a screen shot on the screen
	protected void snapShot() {
		this.screen = (TakesScreenshot) _driver;
		// reference to File object
		File src = screen.getScreenshotAs(OutputType.FILE);
		try {
			// create a new File System at <Project Directory>/src/ScreenShots/
			FileUtils.copyFile(src, new File(System.getProperty("user.dir") 
								             		 + "/src/ScreenShots/" 
								             		 // set a fingerprint timestamp as file name .png 
								                     + getFingerprint() + ".png"));
		}
		// on IOExeption
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// create a fingerprint timestamp
	private String getFingerprint() {
		// return the current time as String
		Calendar c = Calendar.getInstance();
		return c.getTime().toString();
	}
	
	// IMPLICIT WAIT METHODS
	// GIVEN A VISIBILITY CONDITION
	// By ID
	protected WebElement  waitUntilElementLocatedByID(String id) {
		WebDriverWait wait = new WebDriverWait(_driver, 10);
		return wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(id)));
	}
	// By CSS SELECTOR
	protected WebElement waitUntilElementLocatedByCSS(String css) {
		WebDriverWait wait = new WebDriverWait(_driver, 10);
		return wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(css)));
	}
	
	// IMPLICIT WAIT METHODS
	// GIVEN A PRESENCE CONDITION
	// By ID	
	protected WebElement waitUntilElementPresenceByCSS(String css) {
		WebDriverWait wait = new WebDriverWait(_driver, 10);
		return wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(css)));
	}
	//By CSS SELECTOR
	protected WebElement waitUntilElementPresenceByID(String id) {
		WebDriverWait wait = new WebDriverWait(_driver, 10);
		return wait.until(ExpectedConditions.presenceOfElementLocated(By.id(id)));
	}
}