package viacom.utils;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;



public class BaseClass {

	public static WebDriver driver;
	
	@BeforeMethod (alwaysRun=true)
	public static void setUp() {
		
		ConfigsReader.readProperties("src/test/resources/configurations/application.properties");
		String browser=ConfigsReader.getProperty("browser");
		Reporter.log("Reading the configuration file for browser name");

		 ChromeOptions options = new ChromeOptions();
		 LoggingPreferences logPrefs = new LoggingPreferences();
		 logPrefs.enable(LogType.PERFORMANCE, Level.ALL );
		 options.setCapability( "goog:loggingPrefs", logPrefs );
		
		if (browser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", "src\\test\\resources\\drivers\\chromedriver.exe");
			driver = new ChromeDriver(options);
			Reporter.log("Setting up ChromeDriver");
		} else if (browser.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver", "src\\test\\resources\\drivers\\geckodriver.exe");
			driver = new FirefoxDriver();
			Reporter.log("Setting up FireFoxDriver");
		}  else if (browser.equalsIgnoreCase("edge")) {
			System.setProperty("webdriver.edge.driver", "src\\test\\resources\\drivers\\msedgedriver.exe");
			driver = new EdgeDriver();
			Reporter.log("Setting up EdgeDriver");
		} else {
			System.out.println("browser given is wrong: " + browser);
			Reporter.log("Error in Setting up WebDriver" + browser);
		}
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get(ConfigsReader.getProperty("url"));
		Reporter.log("Visiting the URL: " + ConfigsReader.getProperty("url"));
		
		
		
		
		List<LogEntry> entries = driver.manage().logs().get(LogType.PERFORMANCE).getAll();

		List<LogEntry> filtered = entries.stream()
				.filter(e -> e.toString().contains("http://sc.cc.com"))
	            .collect(Collectors.toList());
		
		

		for (LogEntry entry : entries) {
//		    System.out.println(new Date(entry.getTimestamp()) + " " + entry.getLevel() + " " + entry.getMessage());
		    if(entry.toString().contains("\"url\":\"http://sc.cc.com/")) {
		    	System.out.println(new Date(entry.getTimestamp()) + " " + entry.getLevel() + " " + entry.getMessage());
		    }
		}
		
		System.out.println(entries.size() + " " + LogType.PERFORMANCE + " log entries found");
		String firstCallString = filtered.get(0).toString();
		int index = firstCallString.indexOf("http://sc.cc.com");
		String partOfUrl = firstCallString.substring(index);
		String firstCallFullUrl = partOfUrl.substring(0, partOfUrl.indexOf("\""));
		System.out.println("First call’s full URL that has domain http://sc.cc.com: " + firstCallFullUrl);
		
		System.out.println("Total calls that have domain http://sc.cc.com: " + filtered.size());
		
	}
	
	@AfterMethod(alwaysRun=true)
	public void tearUp() {
		driver.quit();
		Reporter.log("Driver Closed After Testing");
	}
	
	public static void takeScreenshot(String fileName) {
		TakesScreenshot ts=(TakesScreenshot)driver;
        File scr=ts.getScreenshotAs(OutputType.FILE);
        
        try {
			FileUtils.copyFile(scr, new File("screenshots/"+fileName+".png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Unable to take screenshot");
			Reporter.log("Unable to take screenshot");
		}
	}
}

