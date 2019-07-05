package com.testautomation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.testautomation.listener.ExtentReportListener;
import com.testautomation.util.CommonUtil;
import com.testautomation.util.PropertiesFileReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

public class VesselJobAdministrationPage extends BasePage {

	CommonUtil commonUtil = new CommonUtil();
	private By startDateFilter = By.id("startDateFilter");
	private By gcssStatusFilter = By.id("gcssStatusFilter");
	private By dndStatusFilter = By.id("dndStatusFilter");
	private By submissionDateList = By.xpath("//tr//tr//tr//tr//td[7]");	
	private By returnButton = By.xpath("//td[@class='btnArea']//input[2]");
	PropertiesFileReader obj = new PropertiesFileReader();
	private final static Logger LOGGER = Logger.getLogger(VesselJobAdministrationPage.class.getName());

	public String getTitle(WebDriver driver) throws Exception {
		Thread.sleep(2000);
		return driver.getTitle();
	}

	public boolean verifyCompletionDate(WebDriver driver) throws Exception {
		
		Select dropdownstartDateFilter = new Select(driver.findElement(startDateFilter));
		dropdownstartDateFilter.selectByVisibleText("All");		
		Thread.sleep(2000);
		
		Select dropdowngcssStatusFilter = new Select(driver.findElement(gcssStatusFilter));
		dropdowngcssStatusFilter.selectByVisibleText("COMPLETED");		
		Thread.sleep(2000);
		
		Select dropdowndndStatusFilter = new Select(driver.findElement(dndStatusFilter));
		dropdowndndStatusFilter.selectByVisibleText("COMPLETED");		
		Thread.sleep(2000);
		
		boolean isVerify= false;		
		for (int i = 2; i <= driver.findElements(submissionDateList).size() - 1; i++) {

			  if (driver.findElement(By.xpath("//tr//tr//tr[" + i + "]//td[7]")).getText() != "" 
				 //&& driver.findElement(By.xpath("//tr//tr//tr[" + i + "]//td[8]")).getText() != ""
				 && driver.findElement(By.xpath("//tr//tr//tr[" + i +"]//td[7]")).getText() .equals(commonUtil.fromDate())
						 || driver.findElement(By.xpath("//tr//tr//tr[" + i +"]//td[7]")).getText() .equals(commonUtil.toDate())) {
				  
				  LOGGER.info("Matching Submission date found for Incident date range from " +  commonUtil.fromDate() + " To " + commonUtil.toDate());
			      LOGGER.info("Comparing Submission and Completion Dates");
			      
				  isVerify = commonUtil.compareDate(driver.findElement(By.xpath("//tr//tr//tr[" + i + "]//td[7]")).getText(),
					      driver.findElement(By.xpath("//tr//tr//tr[" + i + "]//td[8]")).getText());				  
			}else {
				  LOGGER.info("No matching Submission date found for Incident date range from " +  commonUtil.fromDate() + " To " + commonUtil.toDate());
			}
		}
		return isVerify;
	}

	public void clickJobId(WebDriver driver) throws Exception {
			
		Properties properties = obj.getProperty();	
		LOGGER.info("submissionDateList size - "+ driver.findElements(submissionDateList).size());
		for (int i = 2; i <= driver.findElements(submissionDateList).size(); i++) {

			  if (driver.findElement(By.xpath("//tr//tr//tr[" + i + "]//td[7]")).getText() != "" 
				// && driver.findElement(By.xpath("//tr//tr//tr[" + i + "]//td[8]")).getText() != ""
				 && driver.findElement(By.xpath("//tr//tr//tr[" + i +"]//td[7]")).getText() .equals(commonUtil.fromDate())
				 || driver.findElement(By.xpath("//tr//tr//tr[" + i +"]//td[7]")).getText() .equals(commonUtil.toDate())) {
				
				  LOGGER.info("Before clicking on Job Id " + driver.findElement(By.xpath("//tr//tr//tr[" + i +"]//td[1]")).getText());
			   //Click matching Job Id
				driver.findElement(By.xpath("//tr//tr//tr[" + i +"]//td[1]/a")).click();  
				Thread.sleep(1000);
				LOGGER.info("After clicking on Job Id " +  driver.findElement(By.xpath("//tr//tr//tr[" + i +"]//td[1]")).getText());
				String expectedViewBookingLogPageTitle= properties.getProperty("viewbookinglogpage.page.title");
				String actualViewBookingLogTitle= new ViewBookingLogPage().getTitle(driver);
				Assert.assertEquals(actualViewBookingLogTitle, expectedViewBookingLogPageTitle,"View Booking Log page is not opening");
				
				new ViewBookingLogPage().verifyLogDescription(driver, driver.findElement(By.xpath("//tr//tr//tr[" + i +"]//td[1]")).getText());
				//click Return Button
				new ExtentReportListener().captureScreen(driver);
				Thread.sleep(1000);
				driver.findElement(returnButton).click();
				Thread.sleep(2000);
				 
			}			 
		}
		
	}
}
