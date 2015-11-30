package cresc1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ChangePermission {

	public static WebDriver driver;
	public static WebDriverWait wait;
	int timeoutSeconds = 45;
	String fileLocation = "C:\\QA/Permission.xls";
	String fileLocation1 = "C:\\QA/MAD-teacher-emails.xls";
	public JavascriptExecutor js;

	public void setJavascriptExecutor(JavascriptExecutor obj) {
		js = obj;
	}

	@BeforeClass
	public void setup() {

	//	driver = new FirefoxDriver();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		wait = new WebDriverWait(driver, timeoutSeconds);
		
		/***************** Change the URL *************/
		
		 driver.navigate().to( "http://platform.mad-learn.com/");
		 

		//driver.navigate().to("http://crescmsqaus.azurewebsites.net/");
		waitForElement("btnGo");

	}

	@Test
	public void login() throws InterruptedException, IOException {
		js = (JavascriptExecutor) driver;
		
		/***************** Change the User Details *************/ 
		/*sendValue("txtEmail", "rajan1785@gmail.com");
		sendValue("txtPassword", "Cres@2737");*/
		
		  sendValue("txtEmail", "rajatgupta@crescerance.com");
		  sendValue("txtPassword", "rajatgupta");
		 
		
		driver.findElement(By.id("btnGo")).click();
		waitForElement("MainContent_lnkSetPermission");
		
		/***************** Change the URL **************/
		
	/*	driver.navigate()
				.to("http://crescmsqaus.azurewebsites.net/UserPermissionAccess.aspx?UserId=4");*/
		driver.navigate()
		.to("http://platform.mad-learn.com/UserPermissionAccess.aspx?UserId=4");
		
		waitForElement("MainContent_btnSave");
		FileInputStream fis = new FileInputStream(fileLocation);
		HSSFWorkbook wb11 = new HSSFWorkbook(fis);
		HSSFSheet sheet1 = wb11.getSheetAt(0);
		
		/* ************Reading All Permissions From Permission.xls***********************/
		
		for (int count1 = 1; count1 <= sheet1.getLastRowNum(); count1++) {
			Row row11 = sheet1.getRow(count1);
			Cell Cell11 = row11.getCell(0);
			Cell Cell12 = row11.getCell(1);
			String Permission_Name = Cell11.getStringCellValue();
			String Permission_checkbox = Cell12.getStringCellValue();

			FileInputStream fis1 = new FileInputStream(fileLocation1);
			HSSFWorkbook wb1 = new HSSFWorkbook(fis1);
			HSSFSheet sheet = wb1.getSheetAt(0);
			String strCellValue = null;
			Row HeaderRow = sheet.getRow(0);
			Cell HeaderCell = HeaderRow.getCell(count1 + 1);
			if (HeaderCell == null) {
				HeaderCell = HeaderRow.createCell(count1 + 1);
			}
			String Permission_Name_Text = driver.findElement(
					By.id(Permission_Name)).getText();
			HeaderCell.setCellValue(Permission_Name_Text);
			
			/* ************Changing the permissions for all users from MAD-teacher-emails.xls ***********************/
			
			for (int count = 1; count <=sheet.getLastRowNum(); count++) {
				Row row1 = sheet.getRow(count);
				Cell cell1 = row1.getCell(0);
				Cell cell2 = row1.getCell(count1 + 1);
				if (cell2 == null) {
					cell2 = row1.createCell(count1 + 1);
				}
				if (cell1.getCellType() == Cell.CELL_TYPE_NUMERIC) {
					int i = (int) cell1.getNumericCellValue();
					strCellValue = String.valueOf(i);
				}
				// driver.navigate().to("http://crescmsqaus.azurewebsites.net/UserPermissionAccess.aspx?UserId="+strCellValue);
				
				/***************** Change the URL *************/
				
				/*driver.navigate().to(
						"http://crescmsqaus.azurewebsites.net/UserPermissionAccess.aspx?UserId="
								+ strCellValue);*/
				
				driver.navigate().to(
						"http://platform.mad-learn.com/UserPermissionAccess.aspx?UserId="
								+ strCellValue);
				
				waitForElement("MainContent_btnSave");
				Boolean isChecked = driver.findElement(
						By.id(Permission_checkbox)).isSelected();
				if (isChecked == true) {
					cell2.setCellValue("");
					CellStyle cellStyle = wb1.createCellStyle();
					cellStyle.setFillForegroundColor(HSSFColor.YELLOW.index);
					cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
					cell2.setCellValue("Already Selected");
					cell2.setCellStyle(cellStyle);
				} else {
					js.executeScript("jQuery('#" + Permission_checkbox
							+ "').click()");
					driver.findElement(By.id("MainContent_btnSave")).click();
					waitForElement("MainContent_lblConfirmMsg");

					Assert.assertEquals(
							"Permission Access has been changed for the user.",
							driver.findElement(
									By.id("MainContent_lblConfirmMsg"))
									.getText());
					cell2.setCellValue("");
					cell2.setCellValue("Done");
				}

				fis1.close();
				FileOutputStream outFile = new FileOutputStream(new File(
						fileLocation1));
				wb1.write(outFile);
				outFile.close();
			}
		}
	}

	public void waitForElement(String elementId) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.id(elementId)));

	}

	public void sendValue(String elementId, String value) {
		driver.findElement(By.id(elementId)).sendKeys(value);

	}

	public void savePermission() {

	}

	@AfterClass()
	public void tearDown() {
		driver.quit();

	}

}
