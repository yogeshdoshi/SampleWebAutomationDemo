package com.testbase;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import com.relevantcodes.extentreports.ExtentReports;
import com.test.util.TestUtil;
import io.github.bonigarcia.wdm.WebDriverManager;

public class TestBase {
	public WebDriver driver = null;	
	//public static String macDriverPath ="/Users/ydoshi/Data_Local/MarvelWS/Drivers/chromedriver.exe";
	public Properties prop;
	public ExtentReports extent;
 //   public static String macDriverPath= "Users/ydoshi/Data_Local/MarvelWS/Drivers/chromedriver";
		
	@BeforeSuite
	public void beforeSuite() throws IOException {				
		WebDriverManager.chromedriver().setup();			
		driver = new ChromeDriver();
		extent = new ExtentReports (System.getProperty("user.dir") +"/test-output/STMExtentReport.html", true);
		//extent.addSystemInfo("Environment","Environment Name")
		extent.addSystemInfo("Host Name", "localhost")
              .addSystemInfo("Environment", "Automation Testing")
              .addSystemInfo("User Name", "Yogesh Doshi");                
        extent.loadConfig(new File(System.getProperty("user.dir")+"\\extent-config.xml")); 
		prop = TestUtil.readPropertiesFile(System.getProperty("user.dir") + "/src/test/resources/config.properties");
		driver.get(prop.getProperty("launchurl"));
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}	

	// Suite cleanup
	@AfterSuite
	public void suiteTeardown() {
		System.out.println("suiteTeardown");
		driver.close();
		driver.quit();
	}
}
