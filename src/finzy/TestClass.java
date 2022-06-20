package finzy;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestClass{
	
	WebDriver driver;
	
	BaseClass baseClass;
	
	//public static String browser="chrome";


	@BeforeTest
	@Parameters("browser")
	public void openBrowser(String browser) {
		//switch case to run on a specified browser
		switch(browser){
		case "chrome":
			WebDriverManager.chromedriver().setup();
			driver=new ChromeDriver();
			break;
		case "firefox":
			WebDriverManager.firefoxdriver().setup();
			driver=new FirefoxDriver();
			break;
		case "safari":
			WebDriverManager.safaridriver().setup();
			driver=new SafariDriver();
			break;
		}
		driver.manage().window().maximize();
		driver.get("https://www.google.com/");
		baseClass=new BaseClass(driver);
	}
	

	@Test(dataProvider="search-words")
	public void search(String searchText) {
		baseClass.searchText(searchText);
		String result=baseClass.findFinzyInSearchResults();
		Assert.assertNotNull(result, "Finzy website is not found in first 10 pages of search result");
		System.out.println(result);
	}

	@AfterTest
	public void quitBrowser() {
		driver.close();
		driver.quit();
	}
	
	@DataProvider(name="search-words")
	public Object[][] dpMethod(){
		return new Object[][]{{"finzy"},{"lending india"},{"peer to peer lending"},{"low-risk investments"}};
		
	}
		
	
}
