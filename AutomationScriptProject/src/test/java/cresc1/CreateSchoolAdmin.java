package cresc1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class CreateSchoolAdmin {

	public WebDriver driver;
	public WebDriverWait wait;
	public JavascriptExecutor js;
	int timeoutSeconds = 30;
	String fileLocation = "C://Users//Gaurav//Desktop//Student-Account-Information.xlsx";

	@BeforeClass
	public void setup() {

		driver = new ChromeDriver();
		driver.manage().window().maximize();
		wait = new WebDriverWait(driver, timeoutSeconds);
		js = (JavascriptExecutor) driver;

		// driver.navigate().to( "http://crescmsqaus.azurewebsites.net/");

		driver.navigate().to("http://platform.embr.mobi/");
		waitForElement("btnGo");

	}

	public void login() {
		/*
		 * sendValue("txtEmail", "rajan1785@gmail.com");
		 * sendValue("txtPassword", "Cres@2737");
		 */

		sendValue("txtEmail", "rajatgupta@crescerance.com");
		sendValue("txtPassword", "rajatgupta");

		driver.findElement(By.id("btnGo")).click();
		waitForElement("MainContent_lnkAddSAdmin");

	}

	@Test
	public void createSchool() throws IOException, InterruptedException {

		login();
		// driver.navigate().to("http://crescmsqaus.azurewebsites.net/SchoolAdminAdd.aspx?SchoolId=0");

		driver.navigate()
				.to("http://platform.embr.mobi/SchoolAdminAdd.aspx?SchoolId=0");

		FileInputStream fis1 = new FileInputStream(fileLocation);
		XSSFWorkbook wb1 = new XSSFWorkbook(fis1);
		XSSFSheet sheet = wb1.getSheetAt(0);

		for (int count = 1; count <= sheet.getLastRowNum(); count++) {
		

			Row row1 = sheet.getRow(count);
			Cell cell1 = row1.getCell(0);
			Cell cell2 = row1.getCell(1);
			Cell cell3 = row1.getCell(2);
			Cell cell4 = row1.getCell(3);
			Cell cell5 = row1.getCell(4);
			Cell cell6 = row1.getCell(5);
			Cell cell7 = row1.getCell(6);
			Cell cell8 = row1.getCell(7);

			String school_name = cell1.getStringCellValue();
			String fname = cell2.getStringCellValue();
			String lname = cell3.getStringCellValue();
			String email = cell4.getStringCellValue();
			String pwd = cell5.getStringCellValue();
			String phone = null;
			if (cell6.getCellType() == Cell.CELL_TYPE_NUMERIC) {
				cell6.setCellType(Cell.CELL_TYPE_STRING);
				phone = cell6.getStringCellValue();
			}

			String state = cell7.getStringCellValue();
			Boolean returntype = enterandSaveValues(school_name, fname, lname,
					email, pwd, phone, state);
			if (cell8 == null) {
				System.out.println("Inside Cell8");
				cell8 = row1.createCell(7);
				if (returntype == true) {
					cell8.setCellValue("Pass");
				} else {
					cell8.setCellValue("Fail");
				}
			}

			// driver.navigate().to("http://crescmsqaus.azurewebsites.net/SchoolAdminAdd.aspx?SchoolId=0");

			driver.navigate()
					.to("http://platform.embr.mobi/SchoolAdminAdd.aspx?SchoolId=0");

		}

		fis1.close(); // Close the InputStream

		FileOutputStream output_file = new FileOutputStream(new File(
				fileLocation)); // Open FileOutputStream to write updates

		wb1.write(output_file); // write changes

		output_file.close(); // close the stream
	}

	public boolean enterandSaveValues(String school_name, String fname,
			String lname, String email, String pwd, String phone, String state)
			throws InterruptedException {
		Select schooldd = new Select(driver.findElement(By
				.id("MainContent_ddlSchools")));
		schooldd.selectByVisibleText(school_name);
		sendValue("MainContent_ucName_txtFirstName", fname);
		sendValue("MainContent_ucName_txtLastName", lname);
		sendValue("MainContent_txtEmail", email);
		driver.findElement(By.id("MainContent_txtEmail")).sendKeys(Keys.TAB);
		Thread.sleep(1000);
		sendValue("MainContent_txtPassword", pwd);
		sendValue("MainContent_txtMobile", phone);
		Select statedd = new Select(driver.findElement(By
				.id("MainContent_ddlStates")));
		statedd.selectByVisibleText(state);
		driver.findElement(By.id("MainContent_btnSave")).click();
		waitForElement("MainContent_lblConfirmMsg");
	/*	if (driver.findElement(By.id("MainContent_lblWarningMsg"))
				.isDisplayed()) {
			driver.findElement(By.id("MainContent_txtPassword")).sendKeys(
					"password1");
			Thread.sleep(500);
			driver.findElement(By.id("MainContent_btnSave")).click();
		}
	*/	if (driver.findElement(By.id("MainContent_lblConfirmMsg"))
				.isDisplayed()) {
			System.out.println(driver.findElement(
					By.id("MainContent_lblConfirmMsg")).getText());
		} else {
			System.out.println(driver.findElement(
					By.id("MainContent_lblErrorMsg")).getText());
		}
		boolean var;
		if (driver.findElement(By.id("MainContent_lblConfirmMsg")).getText()
				.equalsIgnoreCase("School Admin added successfully.")) {
			var = true;
		} else {
			System.out.println(driver.findElement(
					By.id("MainContent_lblErrorMsg")).getText());
			var = false;
		}

		return var;
	}

	public boolean isSuccessMsg() {
		try {
			driver.findElement(By.id("MainContent_lblConfirmMsg"))
					.isDisplayed();
			return true;
		} // try
		catch (NoAlertPresentException Ex) {
			return false;
		} // catch
	}

	public void sendValue(String elementId, String value) {
		driver.findElement(By.id(elementId)).sendKeys(value);

	}

	public void waitForElement(String ElementId) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.id(ElementId)));
	}

	@AfterSuite
	public void tearDown() {
		driver.quit();
	}
}
