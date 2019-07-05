package com.testautomation.pages;

import org.openqa.selenium.WebDriver;

public class BasePage {
	
	public void launchBrowser(String url, WebDriver driver) {
		
		driver.get(url);
		driver.manage().window().maximize();		
		driver.navigate().refresh();
		
	}
}
