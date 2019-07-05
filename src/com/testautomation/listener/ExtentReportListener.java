package com.testautomation.listener;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.testautomation.util.PropertiesFileReader;

public class ExtentReportListener {

	public static ExtentHtmlReporter report = null;
	public static ExtentReports extent = null;
	public static ExtentTest test = null;
	public static PropertiesFileReader obj= new PropertiesFileReader();
	
	public static ExtentReports setUp() {
		String reportLocation = System.getProperty("screenshotDirectory", "Reports") + File.separator + "Report_" + System.currentTimeMillis() + ".html";
		report = new ExtentHtmlReporter(reportLocation);		
		report.config().setDocumentTitle("Automation Test Report - DigitalQA");
		report.config().setReportName("Automation Test Report - DigitalQA");
		report.config().setTheme(Theme.STANDARD);		
		System.out.println("Extent Report location initialized . . .");
		report.start();
		
		extent = new ExtentReports();
		extent.attachReporter(report);		
		extent.setSystemInfo("Application", "DigitalQA");
		extent.setSystemInfo("Operating System", System.getProperty("os.name"));
		extent.setSystemInfo("User Name", System.getProperty("user.name"));
		System.out.println("System Info. set in Extent Report");		
		return extent;
	}
	
	public static void testStepHandle(String teststatus,WebDriver driver,ExtentTest extenttest,Throwable throwable) {
		switch (teststatus) {
		case "FAIL":		
			extenttest.fail(MarkupHelper.createLabel("Test Case is Failed : ", ExtentColor.RED));			
			extenttest.error(throwable.fillInStackTrace());
			
			try {
				extenttest.addScreenCaptureFromPath(captureScreenShot(driver));
				} catch (IOException e) {
				e.printStackTrace();
				}
			
			if (driver != null) {
				driver.quit();
			}		
		break;
		
		case "PASS":			
			extenttest.pass(MarkupHelper.createLabel("Test Case is Passed : ", ExtentColor.GREEN));
			break;
			
		default:
			break;
		}
	}
	
	public static String captureScreenShot(WebDriver driver) throws IOException {
	
		String screenshotDirectory = System.getProperty("screenshotDirectory", "target/screenshots");
        String screenshotAbsolutePath = screenshotDirectory + File.separator + System.currentTimeMillis() + ".png";
		TakesScreenshot screen = (TakesScreenshot) driver;
		File src = screen.getScreenshotAs(OutputType.FILE);
		File target = new File(screenshotAbsolutePath);
		FileUtils.copyFile(src, target);
		return screenshotAbsolutePath;
	}
	
	public void captureScreen(WebDriver driver) throws IOException {
		
		String screenshotDirectory = System.getProperty("screenshotDirectory", "target/screenshots");
        String screenshotAbsolutePath = screenshotDirectory + File.separator + System.currentTimeMillis() + ".png";
		TakesScreenshot screen = (TakesScreenshot) driver;
		File src = screen.getScreenshotAs(OutputType.FILE);
		File target = new File(screenshotAbsolutePath);
		FileUtils.copyFile(src, target);
		
	}
}