package finzy;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BaseClass {
	
	WebDriver driver;
	
	public BaseClass(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//input[@class='gLFyf gsfi']")
	WebElement searchBox;
	
	@FindBy(xpath="//div[@class='FPdoLc lJ9FBc']/center/input[@name='btnK']")
	WebElement searchBtn;
	
//	@FindBy(xpath="//span[@class='z1asCe MZy1Rb']")
//	WebElement searchIcn;
	
//	@FindBy(xpath="//a[@href='https://finzy.com/']")
//	WebElement finzyWebsite;
	
	@FindBy(xpath="//a[@id=\"pnnext\"]/span[text()='Next']")
	WebElement nxtPge;
	
	//search finzy website as per given keyword
	public void searchText(String searchText){
		searchBox.clear();
		searchBox.sendKeys(searchText);
		try{
		searchBtn.click();
		}
		catch(NoSuchElementException e) {
		driver.findElement(By.xpath("//span[@class='z1asCe MZy1Rb']")).click();
		}
	}
	
	//Navigate to next page in browser
	public void goToNextPage() {
		WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(20));
		nxtPge.click();
		wait.until(ExpectedConditions.visibilityOf(nxtPge));
	}
	
	//Verify finzy website is available in the search results
	public String findFinzyInSearchResults() {
		//WebElement finzyWebsite=driver.findElement(By.xpath("//a[@href='https://finzy.com/']"));
		String result=null;
		List<WebElement> finzyWebsite=driver.findElements(By.xpath("//a[@href='https://finzy.com/']"));
		if(finzyWebsite.size()!=0)
			result="Finzy website appears in the first page for this search keyword";
		
		else {
			for(int i=2;i<=10;i++) {
				goToNextPage();
				finzyWebsite=driver.findElements(By.xpath("//a[@href='https://finzy.com/']"));
				if(finzyWebsite.size()!=0) {
					result="Finzy website appears in page "+ i +" for this search keyword";
					break;
				}
			}
		}
		 return result;
	}
	
}
