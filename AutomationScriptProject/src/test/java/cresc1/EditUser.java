package cresc1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

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

public class EditUser {
	
	public WebDriver driver;
	public WebDriverWait wait;
	public JavascriptExecutor js;
	int timeoutSeconds = 45;
	String fileLocation = "C://Users//Gaurav//Desktop//Edit_SchoolAdmin.xlsx";
	public String user_type;
	String teachername;
	String url;
	
	@Parameters({ "environment" })
	@BeforeClass
	public void setup(String environment) {
		
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
			url="http://mad-learn.com.embr.mobi/";
			driver.navigate().to(url+"Login.aspx");
			waitForElement("btnGo");
			login(qa_username, qa_pwd);
		} else {
			url="http://platform.mad-learn.com/";
			driver.navigate().to(url+"Login.aspx");
			login(prod_username, prod_pwd);
		}

	}
	
	public void login(String email, String pwd) {

		sendValue("txtEmail", email);
		sendValue("txtPassword", pwd);

		driver.findElement(By.id("btnGo")).click();
		waitForElement("MainContent_lnkAddSAdmin");

	}
	
	@Test
	public void editUser() throws IOException, InterruptedException {
		
		String userid = null;

		/*driver.navigate()
				.to("http://mad-learn.com.embr.mobi/SchoolAdminAdd.aspx?SchoolId=0");
		waitForElement("MainContent_ucName_txtFirstName");*/

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

			Row row1 = sheet.getRow(count);
			Cell cell1 = row1.getCell(0);
			Cell cell2 = row1.getCell(1);
			Cell cell3 = row1.getCell(2);
			Cell cell4 = row1.getCell(3);
			Cell cell5 = row1.getCell(4);
			if (cell1.getCellType() == Cell.CELL_TYPE_NUMERIC) {
				cell1.setCellType(Cell.CELL_TYPE_STRING);
			
			userid = cell1.getStringCellValue();
			}
			else{
				userid = cell1.getStringCellValue();
			}
			String email = cell2.getStringCellValue();
			String usertype = cell3.getStringCellValue();
			if(cell4 != null){
				teachername = cell4.getStringCellValue();
				}
			//String result = cell5.getStringCellValue();
			
			Boolean returntype = enterandSaveValues(userid, usertype);

			if (cell5 == null) {
				cell5 = row1.createCell(4);

			}
			if (returntype == true) {
				cell5.setCellValue("");
				cell5.setCellValue("Pass");
			} else {
				
				boolean iswarningmszavailable = isWarningMsg();
				if(iswarningmszavailable == true)
				{
					cell5.setCellValue("");
					cell5.setCellValue(driver.findElement(
							By.id("MainContent_lblWarningMsg")).getText());
				}
				else{

					cell5.setCellValue("");
				cell5.setCellValue(driver.findElement(
						By.id("MainContent_lblErrorMsg")).getText());
				}
			}
		/*	driver.navigate()
					.to("http://mad-learn.com.embr.mobi/SchoolAdminAdd.aspx?SchoolId=0");
*/
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

	public boolean enterandSaveValues(String user_id, String user_type) throws InterruptedException {
		
		boolean var1 = false;
		System.out.println(user_id);
		if(user_id == null){
			return var1;
		}
		
		driver.navigate().to(url+"SchoolAdminEdit.aspx?UserId="+user_id);
		waitForElement("MainContent_ddlUserType");
		
		
		Boolean isEnabled = driver.findElement(By.id("MainContent_ddlUserType")).isEnabled();
		System.out.println(isEnabled);
		if(isEnabled == true){
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
						if(driver.findElement(By.xpath("//div[contains(@class, '"+abc+"') and .//span[contains(.,'"+teacher_name+"')]]//input")).isSelected() == false){
							driver.findElement(By.xpath("//div[contains(@class, '"+abc+"') and .//span[contains(.,'"+teacher_name+"')]]//input")).click();
						}
					driver.findElement(By.id("txtSearchMultiSelect")).clear();
				}
				}
			}
			}
		 else {
			usertypedd.selectByValue("1");
		}
		}

		driver.findElement(By.id("MainContent_btnSave")).click();
		//waitForElement("MainContent_lnkEdit");
		Thread.sleep(2000);
		if (isAlertPresents()) {
			driver.switchTo().alert().accept();
			driver.switchTo().defaultContent();
			}

			
		boolean message = isupdated();
		
		if (message = true) {

			
				var1 = true;
			} else {
				var1 = false;
			}
		

		return var1;
	}
	
	public boolean isAlertPresents() {
		try {
		driver.switchTo().alert();
		return true;
		}// try
		catch (Exception e) {
		return false;
		}// catch
		}

	public boolean isupdated() {
		try {
			if (driver.findElement(By.id("MainContent_lnkEdit"))
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
	

	public void sendValue(String elementId, String value) {
		driver.findElement(By.id(elementId)).sendKeys(value);

	}

	public void waitForElement(String ElementId) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.id(ElementId)));
	}
}
