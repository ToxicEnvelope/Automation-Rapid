package com.tutorial.rapid.Nice1.core.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.tutorial.rapid.Nice1.core.BasePage;

public abstract class AbstractDynamicLoadingPage extends BasePage {

	// Fields
	@FindBy(css = "div.example > div#start")
	private WebElement startBtnContainer;
	private WebElement startBtn;
	
	// Constructor
	public AbstractDynamicLoadingPage(WebDriver driver) {
		super(driver);
		this.startBtnContainer = 
				waitUntilElementPresenceByCSS("div.example > div#start");
		System.out.println("Button Container: " + startBtnContainer);
		
	}
	
	// tries to click on the Start button
	public boolean attemptToClickOnStartButton() {
		try {
			// identify the Start button using its container as an anchor
			this.startBtn = startBtnContainer.findElement(By.xpath("//button"));
			System.out.println("Start Button: " + startBtn);
			if(startBtn.isDisplayed()) {
				// click on the button
				click(startBtn);
				return true;
			}
			// otherwise
			return false;
		}
		// when exception accrues
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}