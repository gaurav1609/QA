package cresc1;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class EditChannel {
	
	public WebDriver driver;
	public WebDriverWait wait;
	public JavascriptExecutor js;
	int timeoutSeconds = 60;
	String projectId="9822";
	
	@Parameters({"environment"})
	@BeforeClass
	public void setup(String environment){
		driver = new FirefoxDriver();
		js = (JavascriptExecutor) driver;
		wait = new WebDriverWait(driver, timeoutSeconds);
		if(environment.equalsIgnoreCase("QA")){
		driver.navigate().to("http://crescmsqaus.azurewebsites.net/");
		}
		else{
			driver.navigate().to("http://crescmsqaus.azurewebsites.net/");
		}
		driver.manage().window().maximize();
		waitForElement("btnGo");
		
	}
	
	@Parameters({"environment"})
	@Test
	public void login(String environment){
		
		if(environment.equalsIgnoreCase("QA")){
		  sendValue("txtEmail", "rajan1785@gmail.com");
		  sendValue("txtPassword", "Cres@2737");
		}
		else{

		sendValue("txtEmail", "rajatgupta@crescerance.com");
		sendValue("txtPassword", "rajatgupta");
		}

		driver.findElement(By.id("btnGo")).click();
		waitForElement("MainContent_lnkAddSAdmin");
		if(environment.equalsIgnoreCase("QA")){
			driver.navigate().to("http://crescmsqaus.azurewebsites.net/MMP/Manageapp.aspx?ProjectId="+projectId);
			}
			else{
				driver.navigate().to("http://crescmsqaus.azurewebsites.net/MMP/Manageapp.aspx?ProjectId="+projectId);
			}
	waitForElement("lnkAddScreen");
	try {
		Thread.sleep(3000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	
  @Test
  public void editChannel() {
	System.out.println(driver.findElements(By.id("imgScreen")).size());  
	//js.executeScript("jQuery("[name='"ctl00$MainContent$dlScreens$ctl03$imgScreen"']").click()");
	try {
		Thread.sleep(5000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
 // driver.findElement(By.name("ctl00$MainContent$dlScreens$ctl03$imgScreen")).click();
  }
  
  public void waitForElement(String ElementId) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.id(ElementId)));
	}
  
	public void sendValue(String elementId, String value) {
		driver.findElement(By.id(elementId)).sendKeys(value);

	}

	@AfterSuite
	public void tearDown() {
//	driver.quit();
	}
}
