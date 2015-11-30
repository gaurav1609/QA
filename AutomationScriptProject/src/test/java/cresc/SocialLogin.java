package cresc;

import java.util.Set;

import org.junit.AfterClass;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SocialLogin {
	
	WebDriver driver;
	
	@BeforeClass
	public void setup() {
		driver = new FirefoxDriver();
		driver.manage().window().maximize();
	//	driver.navigate().to("https://www.loginradius.com/");
		driver.navigate().to("https://www.gmail.com/");

	}
	
  @Test
  public void login() {
		WebDriverWait wait =  new WebDriverWait(driver, 45);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("link-signup")));
		driver.findElement(By.id("link-signup")).click();
	  wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("FirstName")));
	  WebElement elem = driver.findElement(By.id("FirstName"));
	  WebElement elem1 = driver.findElement(By.id("LastName"));
	  elem.sendKeys("Email");
	  elem.sendKeys(Keys.CONTROL,"a");
	  elem.sendKeys(Keys.CONTROL,"c");
	  elem1.sendKeys(Keys.CONTROL, "v");
	  
	 /* elem.sendKeys("test");
			  elem.sendKeys(Keys.c);
			  elem.sendKeys(Keys.CONTROL, 'c');
			  elem.sendKeys(Keys.CONTROL, 'v');*/
/*Actions action = new Actions(driver);
action.contextClick(driver.findElement(By.id("Email")));
action.*/
	  /*driver.findElement(By.id("slider")).findElement(By.className("popup")).findElement(By.tagName("img")).click();
	  wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("mypopuploginmodal")));
	  driver.findElements(By.className("bricks_new_popup")).get(0).click();
	  Set<String> handlers = driver.getWindowHandles();  
	  String strHendelFb = handlers.toArray()[1].toString();
	  driver.switchTo().window(strHendelFb);
	  wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));
	  driver.findElement(By.id("email")).sendKeys("test@gmail.com"); 
	  driver.findElement(By.id("pass")).sendKeys("Password");
	  driver.findElement(By.id("u_0_1")).click();
	  driver.switchTo().window(handlers.toArray()[0].toString());*/
  }
  
  @AfterClass
	public void tearDown() {
	  driver.quit();
  }
}
