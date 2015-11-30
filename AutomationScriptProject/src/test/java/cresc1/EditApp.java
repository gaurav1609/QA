package cresc1;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class EditApp {

	public static WebDriver driver;
	public static WebDriverWait wait;
	int timeoutSeconds = 45;
	String app_name = "Test 08-12-14_59";

	@BeforeTest
	public void setup() throws InterruptedException, MalformedURLException {
	/*	System.setProperty("webdriver.chrome.driver", "C://Windows//System32//chromedriver.exe");
		//driver = new FirefoxDriver();
driver = new ChromeDriver();
		DesiredCapabilities capability = new DesiredCapabilities();
		capability = DesiredCapabilities.safari();
		capability.setBrowserName("safari");
	//	capability.setVersion("5.1.7");
		capability.setPlatform(org.openqa.selenium.Platform.ANY);
	//	driver = new SafariDriver();
		driver = new RemoteWebDriver(new URL("http://172.16.1.46:5555/wd/hub"),
				capability);*/
		wait = new WebDriverWait(driver, timeoutSeconds);
		driver.navigate().to("http://crescmsqaus.azurewebsites.net");
		driver.findElement(By.id("txtEmail")).sendKeys(Keys.F12);
	//	driver.navigate().to("http://crescmsqaus.azurewebsites.net");
		driver.manage().window().maximize();

	}

	@Test
	public void login() throws IOException, InterruptedException {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnGo")));
		driver.findElement(By.id("txtEmail")).sendKeys("rajan1785@gmail.com");
		driver.findElement(By.id("txtPassword")).sendKeys("Cres@2737");
		driver.findElement(By.id("btnGo")).click();
		waitForElement("MainContent_lnkXMPList");
		manageApp();

	}

	public void manageApp() throws InterruptedException {
		driver.findElement(By.id("MainContent_lnkXMPList")).click();
		waitForElement("MainContent_lnkAddNew");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.linkText(app_name)));
		driver.findElement(By.linkText(app_name)).click();
		waitForElement("aRun");
			Thread.sleep(3000);
		addtemplate();
		// Thread.sleep(3000);

	}

	public void addtemplate() throws InterruptedException {
		waitForElement("lnkAddScreen");
		driver.findElement(By.id("lnkAddScreen")).click();
		wait.until(ExpectedConditions.textToBePresentInElementLocated(
				By.className("drag-template-img"),
				"Please drag template from the template list"));
			Thread.sleep(3000);
			String alt_value = driver.findElements(By.xpath("//img[@id='imgImage']")).get(5).getAttribute("alt");
			System.out.println(alt_value);
		/*WebElement dragElement=driver.findElement(By.id("imgImage"));
		  WebElement dropElement=driver.findElement(By.id("dropTarget"));  
		
		  Actions builder = new Actions(driver);
		
		builder.clickAndHold(dragElement);
		builder.moveToElement(dropElement,5,5);
		builder.perform();
		
			Thread.sleep(500);
		
			builder.release(dropElement);
			   builder.perform();
		action.dragAndDrop((driver.findElements(By.id("imgImage")).get(1)), driver.findElement(By.id("dropTarget")));
		action.build().perform();*/
	}

	public void waitForElement(String elementId) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.id(elementId)));

	}

	public void sendValue(String elementId, String value) {
		driver.findElement(By.id(elementId)).sendKeys(value);

	}
	
	@AfterTest
	public void tearDown(){
		driver.quit();
	}
}
