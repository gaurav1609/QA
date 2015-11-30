package cresc1;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.thoughtworks.selenium.Selenium;

public class PNTest {

	public static WebDriver driver;
	public Selenium selenium;
	public static WebDriverWait wait;
	int timeoutSeconds = 45;

	@BeforeClass
	public void setup() {
		// String profilePath1 = this.GetData("systemsetting", "profilePath");

		driver = new ChromeDriver();		
		driver.manage().window().maximize();
		wait = new WebDriverWait(driver, 60);
		driver.navigate().to(
				"http://embrtest.embr.mobi/");
		waitForElement("btnGo");

	}

	@Test
	public void login() throws InterruptedException {
		sendValue("txtEmail", "rajan1785@gmail.com");
		sendValue("txtPassword", "Cres@2737");
		driver.findElement(By.id("btnGo")).click();
		waitForElement("MainContent_lnkPushCreate");
		driver.findElement(By.id("MainContent_lnkPushCreate")).click();
		waitForElement("MainContent_ddlApplication");
		for (int i = 1; i <= 100; i++) {
			sendPN(i);
		}

	}

	public void sendPN(int i) throws InterruptedException {

		// wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("MainContent_lnkPushCreate")));

		Select applicationdd = new Select(driver.findElement(By
				.id("MainContent_ddlApplication")));
		applicationdd.selectByVisibleText("Test app 21 september");
		waitForElement("MainContent_Label2");
		sendValue("MainContent_txtMessageType", "New Test PN "+i+"");
		/*driver.findElement(By.id("MainContent_txtMessageType")).sendKeys(
				"Test PN " + i);*/
		driver.findElement(By.id("MainContent_btnSend")).click();
		waitForElement("MainContent_lblConfirmMsg");
		Thread.sleep(20000);
	}

	public void waitForElement(String elementId) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.id(elementId)));

	}
	
	public void sendValue(String elementId, String value){
		driver.findElement(By.id(elementId)).sendKeys(value);
		
	}
	
	@AfterClass()
	public void tearDown(){
		driver.quit();
		
	}
}
