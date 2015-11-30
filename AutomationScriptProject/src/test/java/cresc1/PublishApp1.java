package cresc1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class PublishApp1 {
	public static WebDriver driver;
	public JavascriptExecutor js;
	public static WebDriverWait wait;
	int timeoutSeconds = 180;
//	String fileLocation = "C://Users//Gaurav//Desktop//Application1.xls";
	String fileLocation = "C://QA//Application1.xls";
	@BeforeTest
	public void setup() throws InterruptedException, MalformedURLException {
		//System.setProperty("webdriver.chrome.driver", "C://Windows//System32//chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
			wait = new WebDriverWait(driver, timeoutSeconds);
			js = (JavascriptExecutor) driver;
		
		driver.navigate().to("http://crescerancemanagementsystem.com/login.aspx");
		
		
	}

@Test
  public void login() throws InterruptedException, ParseException, IOException {
	  FileInputStream fis1 = new FileInputStream(fileLocation);
	HSSFWorkbook wb1 = new HSSFWorkbook(fis1);
	HSSFSheet sheet = wb1.getSheetAt(0);
	String strCellValue = null;
   
	  wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnGo")));
	  driver.findElement(By.id("txtEmail")).sendKeys("rajatgupta@crescerance.com");
		driver.findElement(By.id("txtPassword")).sendKeys("rajatgupta");
		driver.findElement(By.id("btnGo")).click();
		
	//	publishApp();
		 for(int count = 1; count<=sheet.getLastRowNum(); count++)
		//	 for(int count = 1; count<=1; count++)
		    {
		    	Row row1 = sheet.getRow(count);
		    	Cell cell1 = row1.getCell(0);
		    /*	Cell cell2 = row1.getCell(2);
		    	if(cell2 == null){
		    		cell2 = row1.createCell(2);
		    	}*/
		    	if(cell1.getCellType() == Cell.CELL_TYPE_NUMERIC) {
		    		int i = (int)cell1.getNumericCellValue(); 
		    		strCellValue = String.valueOf(i); 
		    	}
		    	System.out.println(strCellValue);
		    	driver.navigate().to("http://crescerancemanagementsystem.com/XMP/XMLManagement.aspx?ProjectId="+strCellValue);
		    		publishApp(count, strCellValue);
		    }

  }



  
  //public void publishApp(int i) throws InterruptedException, ParseException, IOException{
public void publishApp(int i, String strCellValue) throws InterruptedException, ParseException, IOException{
	  System.out.println(js);
	  waitForElement("lnkRePublish");
	  
			driver.findElement(By.id("lnkRePublish")).click();


				wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("cboxIframe")));
				
			driver.switchTo().frame(driver.findElement(By.className("cboxIframe")));
		/*	waitForElement("MainContent_txtComments");
			driver.findElement(By.id("MainContent_txtComments")).sendKeys("Publishing");
			driver.findElement(By.id("MainContent_lnkPublish")).click();*/
		//--- Code1 Remove From here
			waitForElement("Img1");
			Thread.sleep(3000);
			if(isAlertPresent()){
				System.out.println("Alert Exist");
				writeResult(i, "Alert Exist", strCellValue);
			}
			else{
				System.out.println("Alert doesn't Exist");
			
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("Img1")));
			Thread.sleep(3000);
			driver.switchTo().defaultContent();
			
			wait.until(ExpectedConditions.elementToBeClickable(By.id("lnkRePublish")));
			
	//	wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("lnkProjectList")));
			int iframe_size = driver.findElements(By.tagName("iframe")).size();
			System.out.println(iframe_size);
	
		if(iframe_size == 2)
		{
			writeResult(i, "Failed", strCellValue);
		}
		else{
			writeResult(i, "Done1.", strCellValue);
		}
}
	/*	js.executeScript("jQuery('iframe').contents().find('#MainContent_lnkGoToDashBoard').click()");
		waitForElement("MainContent_lnkSetPermission");*/
		// Code2 Remove From here
  }

public boolean isAlertPresent() 
{ 
    try 
    { 
    	Alert alert = driver.switchTo().alert(); 
        alert.accept();
        return true; 
    }   // try 
    catch (NoAlertPresentException Ex) 
    { 
        return false; 
    }   // catch 
}   // isAlertPresent() 
  
  //public void writeResult(int i, String starttime, String endtime, long diffSeconds) throws IOException{

public void writeResult(int i, String Result, String strCellValue) throws IOException{
	   //For Server
	  //  String fileLocation = "C://Users//Gaurav//Desktop//PublishResultwithiCS.xls";
	  FileInputStream fsIP= new FileInputStream(new File(fileLocation)); //Read the spreadsheet that needs to be updated
      
      HSSFWorkbook wb = new HSSFWorkbook(fsIP); //Access the workbook
        
      HSSFSheet worksheet = wb.getSheetAt(0); //Access the worksheet, so that we can update / modify it.
      Row row11 = worksheet.createRow(i);
      Cell cell1 = row11.getCell(0);
     Cell cell2 = row11.getCell(1);
     
     if(cell1 == null){
    	 cell1 = row11.createCell(0);
     }
     
     if(cell2 == null){
    	 cell2 = row11.createCell(1);
     }
     cell1.setCellType(Cell.CELL_TYPE_NUMERIC);
     cell1.setCellValue(strCellValue);
 //    cell1.setCellType(Cell.CELL_TYPE_NUMERIC);
//     cell1.setCellValue(value)
    
     cell2.setCellValue(Result);
        
      fsIP.close(); //Close the InputStream
       
      FileOutputStream output_file =new FileOutputStream(new File(fileLocation));  //Open FileOutputStream to write updates
        
      wb.write(output_file); //write changes
        
      output_file.close();  //close the stream  


  }
  public void waitForElement(String elementId) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.id(elementId)));
	}
  
  @AfterTest
	public void tearDown(){
	driver.quit();
	}
}
