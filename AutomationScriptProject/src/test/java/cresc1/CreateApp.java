package cresc1;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

public class CreateApp {
	
	public static WebDriver driver;
	public static WebDriverWait wait;
	int timeoutSeconds = 45;
	String fileLocation = "ExcelData/CreateApp.xls";
	String username, password;
	DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy_ss");
	Date date = new Date();
	String app_name = "Test "+dateFormat.format(date);
	
	@BeforeTest
	public void setup(){
		driver = new FirefoxDriver();
		wait = new WebDriverWait(driver, timeoutSeconds);
		driver.navigate().to("http://crescmsqaus.azurewebsites.net");
		driver.manage().window().maximize();
		
	}
	
	public void readExcel(){
		 try {
			 
				  FileInputStream fis1 = new FileInputStream(fileLocation);
				HSSFWorkbook wb1 = new HSSFWorkbook(fis1);
				HSSFSheet sheet = wb1.getSheetAt(0);
		        for(int count = 0; count<sheet.getLastRowNum(); count++){
		        	Row row1 = sheet.getRow(count+1);
		        	Cell cell1 = row1.getCell(0);
		        	if (cell1 != null) {
		        			        	{
		        		username = row1.getCell(0).toString();;
		        		password = row1.getCell(1).toString();
	        		
		        	}
		        	}
		        }
		           
		        fis1.close();
		    } catch (IOException e) {
		        System.out.println("Test data file not found");
		    }   
		 
			//System.out.println(fileLocation);
		
	}
	
//  @Test
  public void login()
			throws IOException {
	  readExcel();
	  System.out.println(username);
	  System.out.println(password);
	 	wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnGo")));
		driver.findElement(By.id("txtEmail")).sendKeys(username);
		driver.findElement(By.id("txtPassword")).sendKeys(password);
		driver.findElement(By.id("btnGo")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.id("imgHome")));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.id("lblUserName")));
		waitForElement("MainContent_lnkCreateXMPApp");
		createApp();

	}
  
  
  public void createApp(){
	  driver.findElement(By.id("MainContent_lnkCreateXMPApp")).click();
	  waitForElement("MainContent_btnSaveInTemp");
	  step1();
  }
  
  public void step1(){
	  Select sel = new Select(driver.findElement(By.id("ddlSchool")));
	  sel.selectByValue("158");
	  //sel.selectByVisibleText("Demo School For Sales Team");
	  sendValue("txtProjectName", app_name);
	  try {
		Thread.sleep(2000);
	} catch (InterruptedException e) {
		e.printStackTrace();
	}
	  sendValue("txtAppLaunchName", app_name);
	  driver.findElement(By.id("MainContent_UCCreateApp_chkEnableChannel")).click();
	  try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	  /*wait.until(ExpectedConditions.elementToBeSelected(By.id("MainContent_UCCreateApp_chkShowChannelPopup")));*/
	  driver.findElement(By.id("MainContent_UCCreateApp_chkShowChannelPopup")).click();
	  
	  
  }
  
  public void step2(){
	  
  }
  
  public void waitForElement(String elementId) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.id(elementId)));

	}

public void sendValue(String elementId, String value){
		driver.findElement(By.id(elementId)).sendKeys(value);
		
	}


  
  @AfterTest
  public void teardown(){
	//  driver.quit();
	  
  }
}
