package cresc1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class CreateUser {

	public WebDriver driver;
	public WebDriverWait wait;
	public JavascriptExecutor js;
	int timeoutSeconds = 75;
	String fileLocation;
	public String user_type;
	String teachername;
	
	public void setJavascriptExecutor(JavascriptExecutor obj) {
		js = obj;
	}

	@Parameters({"filename", "environment" })
	@BeforeClass
	public void setup(String filename, String environment) {

		fileLocation = filename;
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		capabilities.setCapability("chrome.switches", Arrays.asList("--disable-extensions"));
		driver = new ChromeDriver(capabilities);
		driver.manage().window().maximize();
		wait = new WebDriverWait(driver, timeoutSeconds);
		String qa_username = "rajan1785@gmail.com";
		String qa_pwd = "Cres@2737";
		String prod_username = "rajatgupta@crescerance.com";
		String prod_pwd = "rajatgupta";
		js = (JavascriptExecutor) driver;
		if (environment.equalsIgnoreCase("QA")) {
			driver.navigate().to("http://mad-learn.com.embr.mobi/Login.aspx");
			waitForElement("btnGo");
			login(qa_username, qa_pwd);
		} else {

			driver.navigate().to("http://platform.mad-learn.com/Login.aspx");
			login(prod_username, prod_pwd);
		}

	}

	// @Test
	public void login(String email, String pwd) {

		sendValue("txtEmail", email);
		sendValue("txtPassword", pwd);

		driver.findElement(By.id("btnGo")).click();
		waitForElement("MainContent_lnkAddSAdmin");

	}

	public void sendValue(String elementId, String value) {
		driver.findElement(By.id(elementId)).sendKeys(value);

	}

	public void waitForElement(String ElementId) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.id(ElementId)));
	}

	@Test
	public void createUser() throws IOException, InterruptedException {
		
		System.out.println(fileLocation);

		driver.navigate()
				.to("http://platform.mad-learn.com/SchoolAdminAdd.aspx?SchoolId=0");
		waitForElement("MainContent_ucName_txtFirstName");

		InputStream inp = new FileInputStream(fileLocation);
		String ext = FilenameUtils.getExtension(fileLocation);
		Workbook wb_xssf = null; // Declare XSSF WorkBook
		Workbook wb_hssf = null;
		Sheet sheet = null;
		if (ext.equalsIgnoreCase("xls")) {
			POIFSFileSystem fs = new POIFSFileSystem(inp);
			wb_hssf = new HSSFWorkbook(fs);
			sheet = wb_hssf.getSheetAt(0);

		}
		if (ext.equalsIgnoreCase("xlsx")) {
			wb_xssf = new XSSFWorkbook(inp);
			sheet = wb_xssf.getSheetAt(0);

		}

		for (int count = 1; count <= sheet.getLastRowNum(); count++) {
			Cell cell10;

			Row row1 = sheet.getRow(count);
			Cell cell1 = row1.getCell(0);
			Cell cell2 = row1.getCell(1);
			Cell cell3 = row1.getCell(2);
			Cell cell4 = row1.getCell(3);
			Cell cell5 = row1.getCell(4);
			Cell cell6 = row1.getCell(5);
			Cell cell7 = row1.getCell(6);
			Cell cell8 = row1.getCell(7);
			Cell cell9 = row1.getCell(8);
			cell10 = row1.getCell(9);

			String school_name = cell1.getStringCellValue();
			String fname = cell2.getStringCellValue();
			String lname = cell3.getStringCellValue();
			String email = cell4.getStringCellValue();
			String pwd = cell5.getStringCellValue();
			String phone = null;
			if (cell6.getCellType() == Cell.CELL_TYPE_NUMERIC) {
				cell6.setCellType(Cell.CELL_TYPE_STRING);
				phone = cell6.getStringCellValue();
			} else {
				phone = cell6.getStringCellValue();
			}

			String state = cell7.getStringCellValue();
			user_type = cell8.getStringCellValue();
			if(cell9 != null){
			teachername = cell9.getStringCellValue();
			}
			Boolean returntype = enterandSaveValues(school_name, fname, lname,
					email, pwd, phone, state);

			if (cell10 == null) {
				cell10 = row1.createCell(9);

			}
			if (returntype == true) {
			//	cell10.setCellValue("");
				cell10.setCellValue("Pass");
			} else {
				
				boolean iswarningmszavailable = isWarningMsg();
				if(iswarningmszavailable == true)
				{
					cell10.setCellValue("");
					cell10.setCellValue(driver.findElement(
							By.id("MainContent_lblWarningMsg")).getText());
				}
				else{

					cell10.setCellValue("");
				cell10.setCellValue(driver.findElement(
						By.id("MainContent_lblErrorMsg")).getText());
				}
			}
			driver.navigate()
					.to("http://platform.mad-learn.com/SchoolAdminAdd.aspx?SchoolId=0");

		}
		inp.close(); // Close the InputStream

		FileOutputStream output_file = new FileOutputStream(new File(
				fileLocation)); // Open FileOutputStream to write updates

		if (ext.equalsIgnoreCase("xls")) {
			wb_hssf.write(output_file);

		}
		if (ext.equalsIgnoreCase("xlsx")) {
			wb_xssf.write(output_file);

		}

		output_file.close();
	}

	public boolean enterandSaveValues(String school_name, String fname,
			String lname, String email, String pwd, String phone, String state) throws InterruptedException {
		
		boolean var1 = false;
		Select schooldd = new Select(driver.findElement(By.id("ddlSchools")));
		schooldd.selectByVisibleText(school_name);
		sendValue("MainContent_ucName_txtFirstName", fname);
		sendValue("MainContent_ucName_txtLastName", lname);
		Select usertypedd = new Select(driver.findElement(By
				.id("MainContent_ddlUserType")));
		if (user_type.equalsIgnoreCase("School Admin")) {
			usertypedd.selectByValue("1");
			
		}
		else if (user_type.equalsIgnoreCase("teacher")) {
			usertypedd.selectByValue("2");
		}

		else if (user_type.equalsIgnoreCase("Student")) {
			usertypedd.selectByValue("3");
			waitForElement("txtSearchMultiSelect");
			List<String> teachers = Arrays.asList(teachername.split("\\s*,\\s*"));
			if(teachers.size()<=0)
			{
				var1 = false;
				return var1;
			}
			else{
				for(int tsize=0; tsize<teachers.size(); tsize++){
					String teacher_name=teachers.get(tsize).trim();
					Thread.sleep(500);
					String abc = "cresMultiselect_items";
					driver.findElement(By.id("txtSearchMultiSelect")).sendKeys(teachers.get(tsize).trim());
					wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//div[contains(@class, '"+abc+"') and .//span[contains(.,'"+teacher_name+"')]]//input"))));
					Thread.sleep(1000);
					int text = driver.findElements(By.xpath("//div[contains(@class, '"+abc+"') and .//span[contains(.,'"+teacher_name+"')]]//input")).size();
					if(text >= 1){
					driver.findElement(By.xpath("//div[contains(@class, '"+abc+"') and .//span[contains(.,'"+teacher_name+"')]]//input")).click();
					driver.findElement(By.id("txtSearchMultiSelect")).clear();
				}
				}
			}
			}
		 else {
			usertypedd.selectByValue("1");
		}

		sendValue("MainContent_txtEmail", email);
		driver.findElement(By.id("MainContent_txtEmail")).sendKeys(Keys.TAB);
		Thread.sleep(1000);
		if (driver.findElement(By.id("MainContent_txtPassword")).isEnabled()) {
			sendValue("MainContent_txtPassword", pwd);
		}
		sendValue("MainContent_txtMobile", phone);
		Select statedd = new Select(driver.findElement(By
				.id("MainContent_ddlStates")));
		statedd.selectByVisibleText(state);
		driver.findElement(By.id("MainContent_btnSave")).click();
		Thread.sleep(1000);
		Boolean message = isSuccessMsg();
		
		if (message = true) {

			if (driver.findElement(By.id("MainContent_lblConfirmMsg"))
					.getText()
					.equalsIgnoreCase("School Admin added successfully.")) {
				var1 = true;
			} 
			else if (driver.findElement(By.id("MainContent_lblConfirmMsg"))
					.getText()
					.equalsIgnoreCase("School Teacher added successfully.")) {
				var1 = true;
			} 
			else if (driver.findElement(By.id("MainContent_lblConfirmMsg"))
					.getText()
					.equalsIgnoreCase("School Student added successfully.")) {
				var1 = true;
			} 
			
			else {
				var1 = false;
			}
		}

		return var1;
	}

	public boolean isSuccessMsg() {
		try {
			if (driver.findElement(By.id("MainContent_lblConfirmMsg"))
					.isDisplayed() == true) {
				return true;
			} else {
				return false;
			}
		} // try
		catch (NoAlertPresentException Ex) {
			return false;
		} // catch
	}

	public boolean isErrorMsg() {
		try {
			driver.findElement(By.id("MainContent_lblErrorMsg")).isDisplayed();
			return true;
		} // try
		catch (NoAlertPresentException Ex) {
			return false;
		} // catch
	}
	
	public boolean isWarningMsg() {
		try {
			driver.findElement(By.id("MainContent_lblWarningMsg")).isDisplayed();
			return true;
		} // try
		catch (NoAlertPresentException Ex) {
			return false;
		} // catch
	}

	@AfterSuite
	public void tearDown() {
		driver.quit();
	}
}
