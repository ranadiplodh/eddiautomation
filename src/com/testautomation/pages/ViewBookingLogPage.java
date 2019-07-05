package com.testautomation.pages;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.testautomation.listener.ExtentReportListener;
import com.testautomation.util.PropertiesFileReader;

public class ViewBookingLogPage extends BasePage{
	
	private final static Logger LOGGER = Logger.getLogger(ViewBookingLogPage.class.getName());
	private By logDescriptions = By.xpath("//tr//tr//tr//td[4]");	
	PropertiesFileReader obj= new PropertiesFileReader();
	
	public String getTitle(WebDriver driver) throws Exception
	{
		Thread.sleep(2000);
		return driver.getTitle();
	}
	public void verifyLogDescription(WebDriver driver, String jobId) throws Exception
	{	
		List<String> logDescriptionValues = new ArrayList<String>();
		
		for(int i=2; i <= driver.findElements(logDescriptions).size(); i++ ) {
			
			if(driver.findElement(By.xpath("//tr//tr//tr["+i+"]//td[4]")).getText()!=""){
				logDescriptionValues.add(driver.findElement(By.xpath("//tr//tr//tr["+i+"]//td[4]")).getText());
			}
		}
		LOGGER.info("Job Id -- " + jobId + " Job Description - " + logDescriptionValues);
		//Assert.assertEquals(logDescriptionValues.contains("ERROR"), true,"Log Description contains Error!");
		
		Thread.sleep(1000);
	}

}
