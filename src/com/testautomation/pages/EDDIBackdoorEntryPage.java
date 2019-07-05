package com.testautomation.pages;

import java.util.Properties;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import com.testautomation.listener.ExtentReportListener;
import com.testautomation.util.PropertiesFileReader;

public class EDDIBackdoorEntryPage extends BasePage{

	private By username = By.id("Backgate_username");
	private By submit = By.id("Backgate__loginStart");
	//private final static Logger LOGGER = Logger.getLogger(EDDIBackdoorEntryPage.class.getName());
	
	PropertiesFileReader obj= new PropertiesFileReader();
	
	public String getTitle(WebDriver driver) throws Exception
	{
		Thread.sleep(2000);
		return driver.getTitle();
	}
	

	public void loginEDDI(WebDriver driver) throws Exception
	
	{
		Properties properties = obj.getProperty();
		
		driver.findElement(username).clear();
		 System.out.println("Env :"+ System.getProperty("env"));
		    
		    if(System.getProperty("env").equalsIgnoreCase("dev")) {	
		    	driver.findElement(username).sendKeys(properties.getProperty("username_dev"));
		    }else {
		    	driver.findElement(username).sendKeys(properties.getProperty("username_rest"));
		    }
		    
		new ExtentReportListener().captureScreen(driver);
		driver.findElement(submit).click();
		Thread.sleep(2000);
	}
}
