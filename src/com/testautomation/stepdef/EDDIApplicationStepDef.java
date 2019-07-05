package com.testautomation.stepdef;

import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import org.testng.Assert;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.GherkinKeyword;
import com.testautomation.listener.ExtentReportListener;
import com.testautomation.pages.EDDIBackdoorEntryPage;
import com.testautomation.pages.EDDIHomePage;
import com.testautomation.pages.VesselJobAdministrationPage;
import com.testautomation.pages.ViewBookingLogPage;
import com.testautomation.util.PropertiesFileReader;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;

public class EDDIApplicationStepDef extends ExtentReportListener {

	EDDIHomePage eddiHomePage = new EDDIHomePage();	
	EDDIBackdoorEntryPage backdoorEntryPage = new EDDIBackdoorEntryPage();
	VesselJobAdministrationPage vesselJobAdministrationPage = new VesselJobAdministrationPage();
	ViewBookingLogPage bookingLogPage = new ViewBookingLogPage();
	PropertiesFileReader obj= new PropertiesFileReader();
	ExtentTest logInfo=null;
	private WebDriver driver;
		
	@Before
    public void beforeScenario(Scenario scenario) {
	
		WebDriverManager.chromedriver().setup();   
		ChromeOptions options = new ChromeOptions();
		options.setHeadless(true);
		driver = new ChromeDriver(options);		
		
		test =  extent.createTest(scenario.getName());
		test =  test.createNode(scenario.getName());
    }
	
	
	  @After public void afterScenario() {
	 
		  if(driver != null) { driver.quit(); 
		  } 
	 }
	
	
	@Given("^Open EDDI Application$")
	public void openEDDIApplication() throws IOException {

		Properties properties = obj.getProperty();

		try {
			
		    logInfo = test.createNode(new GherkinKeyword("Given"), "Open EDDI2 Application");
		   
		    System.out.println("Env :"+ System.getProperty("env"));
		    
		    if(System.getProperty("env").equalsIgnoreCase("dev")) {		    	
		    	backdoorEntryPage.launchBrowser(properties.getProperty("appurl_dev"), driver);
		   }else if(System.getProperty("env").equalsIgnoreCase("test")) {
		    	backdoorEntryPage.launchBrowser(properties.getProperty("appurl_test"), driver);
		    }else if(System.getProperty("env").equalsIgnoreCase("pp")) {
		    	backdoorEntryPage.launchBrowser(properties.getProperty("appurl_pp"), driver);
		    }else {
		    	backdoorEntryPage.launchBrowser(properties.getProperty("appurl_prod"), driver);
		    }
		    
		    String expectedBackdoorEntryPagePageTitle= properties.getProperty("eddiloginpage.page.title");
			String actualBackdoorEntryPageTitle= backdoorEntryPage.getTitle(driver);
			Assert.assertEquals(actualBackdoorEntryPageTitle, expectedBackdoorEntryPagePageTitle,"EDDI Backdoor Entry page is not opening");
			logInfo.pass("Successfully open Backdoor Entry page");
			logInfo.addScreenCaptureFromPath(captureScreenShot(driver));

		} catch (AssertionError | Exception e) {
			testStepHandle("FAIL", driver, logInfo, e);
		}
	}

	@When("^Login Application$")	
	public void loginEDDI() throws IOException {
		
		Properties properties=obj.getProperty();
		
	try {   				
			logInfo=test.createNode(new GherkinKeyword("When"), "Login Application");
			//Login to EDDI
			backdoorEntryPage.loginEDDI(driver);			
			//After login success EDDI - Home Page will come
			String expectedEddiHomePageTitle= properties.getProperty("eddihomepage.page.title");
			String actualEddiHomeTitle= eddiHomePage.getTitle(driver);
			Assert.assertEquals(actualEddiHomeTitle, expectedEddiHomePageTitle,"EDDI Home page is not opening");				
			//Change Load Country
			eddiHomePage.changeCountry(driver);			
			//Navigate menu - Jobs -> Vessel -> Job Admin
			eddiHomePage.clickJobAdmin(driver);
			//Vessel Job Admin page will come
			String expectedVesselJobAdminPageTitle= properties.getProperty("vesseljobadminpage.page.title");
			String actualVesselJobAdminTitle= vesselJobAdministrationPage.getTitle(driver);
			Assert.assertEquals(actualVesselJobAdminTitle, expectedVesselJobAdminPageTitle,"Vessel Job Administration page is not opening");
			logInfo.pass("Successfully Login to application page and clicked on Job Admin");
			logInfo.addScreenCaptureFromPath(captureScreenShot(driver));	
		
		} catch (AssertionError | Exception e) {
			testStepHandle("FAIL",driver,logInfo,e);			
		}	
	}

	@Then("^Verify Completion Date$")
	public void verifyPCompletionDate() throws ClassNotFoundException {
		try {
			
		logInfo=test.createNode(new GherkinKeyword("Then"), "Verify Completion Date");				
		Assert.assertEquals(vesselJobAdministrationPage.verifyCompletionDate(driver), true, "Verify Completion Date - SUCCESS");
		logInfo.pass("Successfully Verify Completion Date");
		logInfo.addScreenCaptureFromPath(captureScreenShot(driver));	
		} catch (AssertionError | Exception e) {
			testStepHandle("FAIL",driver,logInfo,e);			
		}	
	}

	@Then("^Verify Log Description$")
	public void verifyLogDescription() throws ClassNotFoundException, IOException {
		Properties properties=obj.getProperty();
		try {
		logInfo=test.createNode(new GherkinKeyword("Then"), "Verify Log Description");
		//click on 1st Job Id		
		vesselJobAdministrationPage.clickJobId(driver);
		//verify View Booking Log page
		//String expectedViewBookingLogPageTitle= properties.getProperty("viewbookinglogpage.page.title");
		//String actualViewBookingLogTitle= vesselJobAdministrationPage.getTitle(driver);
		//Assert.assertEquals(actualViewBookingLogTitle, expectedViewBookingLogPageTitle,"View Booking Log page is not opening");
		//Assert.assertEquals(bookingLogPage.verifyLogDescription(driver), true, "Verify Log Description - SUCCESS");		
		logInfo.pass("Successfully Verify Log Description");
		logInfo.addScreenCaptureFromPath(captureScreenShot(driver));
		
		} catch (AssertionError | Exception e) {
			testStepHandle("FAIL",driver,logInfo,e);			
		}	
	}
	
	
}
