package cresc1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import com.thoughtworks.selenium.Selenium;

public class BaseClass {

	public static WebDriver driver;
	public Selenium selenium;
	public static JavascriptExecutor js;
	public static WebDriverWait wait;
	int timeoutSeconds = 45;
	String home_url = "http://cresadminqa.azurewebsites.net/";
	String fileLocation = "ExcelData/Book1.xls";

	@Parameters({ "browser" })
	@BeforeClass
	public void setup(String browser) throws MalformedURLException {
		DesiredCapabilities capability = new DesiredCapabilities();
		if (browser.equalsIgnoreCase("firefox")) {
			System.out.println("firefox");
			capability = DesiredCapabilities.firefox();
			capability.setBrowserName("firefox");
			capability.setPlatform(org.openqa.selenium.Platform.ANY);
		}
		
		if (browser.equalsIgnoreCase("chrome")) {
			System.out.println("chrome");
			capability = DesiredCapabilities.chrome();
			capability.setBrowserName("chrome");
			capability.setPlatform(org.openqa.selenium.Platform.ANY);
		}
		
		driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capability);
	//	driver = new FirefoxDriver();
		wait = new WebDriverWait(driver, timeoutSeconds);
		driver.navigate().to(home_url);
		driver.manage().window().maximize();

	}

	// @Test
	public void login(String usertype, String username, String password)
			throws IOException {

		String utype = usertype;
		String uname = username;
		String pwd = password;
		

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnGo")));
		driver.findElement(By.id("txtEmail")).sendKeys(uname);
		driver.findElement(By.id("txtPassword")).sendKeys(pwd);
		driver.findElement(By.id("btnGo")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.id("imgHome")));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.id("lblUserName")));
		accessURL(utype);

	}

	public void logout() {
		driver.findElement(By.id("lblUserName")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.id("ItemLogout")));
		driver.findElement(By.linkText("Logout")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnGo")));
	}

	@AfterClass
	public void tearDown() {
		driver.quit();
	}

	public void accessURL(String utype) throws IOException {
		
		DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy_hh.mm.ss");
		Date date = new Date();
		String fileName = "Result"+dateFormat.format(date)+".xls";
		String usertype1 = utype;
		ArrayList<String> page_url = this.GetData("PageURL");
		FileInputStream fileInputStream;
		HSSFSheet sheet = null;
		HSSFWorkbook wb = null;
			fileInputStream = new FileInputStream(fileLocation);
			wb = new HSSFWorkbook(fileInputStream);

			sheet = wb.getSheetAt(1);
		

		for (int i = 0; i < 10; i++) {
			String completeURL = home_url + page_url.get(i);
			HSSFCellStyle cellStyle = wb.createCellStyle();
			cellStyle = wb.createCellStyle();
			cellStyle.setFillForegroundColor(HSSFColor.RED.index);
			cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			
			driver.navigate().to(completeURL);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By
					.id("imgHome")));
			String currentURL = driver.getCurrentUrl();
			
			Cell cell1 = null;

			Row row11 = sheet.getRow(i+1);
			if(usertype1.equalsIgnoreCase("School Admin"))
			{
			cell1 = row11.getCell(3);
			}
			if(usertype1.equalsIgnoreCase("Crescerance Admin"))
				{
				cell1 = row11.getCell(5);
			}
			if(usertype1.equalsIgnoreCase("Super Admin"))
					{
					cell1 = row11.getCell(7);
				}
			if(usertype1.equalsIgnoreCase("Campus Admin"))
				
				{
					cell1 = row11.getCell(9);
				}
						
			Cell cell2 = null;
			if(usertype1.equalsIgnoreCase("School Admin")){
			cell2 = row11.getCell(4);
			if (cell2 == null) {
				cell2 = row11.createCell(4);
			}
			}
			if(usertype1.equalsIgnoreCase("Crescerance Admin")){
				cell2 = row11.getCell(6);
				if (cell2 == null) {
					cell2 = row11.createCell(6);
				}
				}
			if(usertype1.equalsIgnoreCase("Super Admin")){
				cell2 = row11.getCell(8);
				if (cell2 == null) {
					cell2 = row11.createCell(8);
				}
				}
			if(usertype1.equalsIgnoreCase("Campus Admin")){
				cell2 = row11.getCell(10);
				if (cell2 == null) {
					cell2 = row11.createCell(10);
				}
				}
		
			if (cell1.getStringCellValue().equalsIgnoreCase("Y")) {

				if (completeURL.equalsIgnoreCase(currentURL)) {

					cell2.setCellValue("Pass");
				} else {
					cell2.setCellStyle(cellStyle);
					cell2.setCellValue("Fail");
				}
			} else {
				if (completeURL.equalsIgnoreCase(currentURL)) {
					cell2.setCellStyle(cellStyle);
					cell2.setCellValue("Fail");
				} else {
					cell2.setCellValue("Pass");
				}

			}
			// writeResult(page_url.get(i), currentURL);
		}
		fileInputStream.close();
		FileOutputStream outFile = new FileOutputStream(new File(fileLocation));
	//	FileOutputStream fileOut = new FileOutputStream(fileLocation);
		wb.write(outFile);
	//	outFile.flush();
		outFile.close();

	}

	public ArrayList<String> GetData(String elementName) {
		return FetchDataFromExcel();
	}

	public ArrayList<String> FetchDataFromExcel() {
		ArrayList<String> mylist = new ArrayList<String>();
		try {
			FileInputStream fileInputStream = new FileInputStream(fileLocation);
			HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);
			HSSFSheet worksheet = workbook.getSheet("URLS");
			for (int row = 0; row < worksheet.getLastRowNum(); row++) {
				Row row1 = worksheet.getRow(row + 1);
				Cell cell = row1.getCell(2);
				if (cell.getStringCellValue() != "") {
					mylist.add(cell.getStringCellValue());
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return mylist;

	}

}
