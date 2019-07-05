package com.testautomation.pages;

import java.util.Properties;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import com.testautomation.util.PropertiesFileReader;

public class EDDIHomePage extends BasePage{
	
	private By loadCountry 	= By.id("HeaderForm_countryCode");
	private By jobs 		= By.id("el0");
	private By vessel 		= By.id("el4");
	private By jobAdmin 	= By.id("el15");
    //private final static Logger LOGGER = Logger.getLogger(EDDIHomePage.class.getName());
	
	PropertiesFileReader obj= new PropertiesFileReader();
	
	public String getTitle(WebDriver driver) throws Exception
	{
		Thread.sleep(2000);
		return driver.getTitle();
	}
	

	public void changeCountry(WebDriver driver) throws Exception
	
	{
		Properties properties = obj.getProperty();
		
		Select dropdown = new Select(driver.findElement(loadCountry));
		dropdown.selectByVisibleText(properties.getProperty("loadcountry"));
	}
	
	public void clickJobAdmin(WebDriver driver) throws Exception
	
	{
		driver.findElement(jobs).click();
		driver.findElement(vessel).click();
		driver.findElement(jobAdmin).click();
		
		Thread.sleep(2000);
	}


}
