package com.testbase;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import com.relevantcodes.extentreports.ExtentReports;
import com.test.util.TestUtil;
import io.github.bonigarcia.wdm.WebDriverManager;

public class TestBase {
	public static HashMap<String,String> cookie;
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
        extent.loadConfig(new File(System.getProperty("user.dir")+"/src/test/resources"+"/extent-config.xml")); 
		prop = TestUtil.readPropertiesFile(System.getProperty("user.dir") + "/src/test/resources/config.properties");
		driver.get(prop.getProperty("launchurl"));
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}	
	
	
	public void storeCookies() {		
    File file = new File("Cookies.data");							
    try		
    {	  
        // Delete old file if exists
		file.delete();		
        file.createNewFile();			
        FileWriter fileWrite = new FileWriter(file);							
        BufferedWriter Bwrite = new BufferedWriter(fileWrite);							
        // loop for getting the cookie information 		
        	
        // loop for getting the cookie information 		
        for(Cookie ck : driver.manage().getCookies())							
        {			
            Bwrite.write((ck.getName()+";"+ck.getValue()+";"+ck.getDomain()+";"+ck.getPath()+";"+ck.getExpiry()+";"+ck.isSecure()));																									
            Bwrite.newLine();             
        }			
        Bwrite.close();			
        fileWrite.close();	
        
    }
    catch(Exception ex)					
    {		
        ex.printStackTrace();			
    }
	}
	
	// Suite cleanup
	@AfterSuite
	public void suiteTeardown() {
		storeCookies();
		driver.close();
		driver.quit();
	}
}
