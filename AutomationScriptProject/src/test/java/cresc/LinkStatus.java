package cresc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.*;

import com.thoughtworks.selenium.*;

public class LinkStatus {
	WebDriver driver;
	public Selenium selenium;
	public int y = 0;
	DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy_hh.mm.ss");
	Date date = new Date();
	String filePath = "C://Users//Gaurav//workspace//Cresc//" + "Result_"
			+ dateFormat.format(date) + ".xls";
	HSSFWorkbook workbook = new HSSFWorkbook();

	@BeforeClass
	public void setup() {

		driver = new FirefoxDriver();
		driver.manage().window().maximize();

	}

	@Test
	public void checkUrl() throws InterruptedException, IOException,
			InvalidFormatException {

		List<String> result1 = new ArrayList<String>();

		File root = new File("D:\\XML");
		HSSFSheet resultSheet = workbook.createSheet("Check Result");
		

		if (root.listFiles().length > 0) {

			for (File file : root.listFiles()) {

				if (file.getName().endsWith(".xml")) {
					result1.add(file.getName());

				}
			}

			for (int p = 0; p < result1.size(); p++) {
				Row row1 = resultSheet.createRow(p + 1);
				row1.createCell(0);
				Cell cell1 = row1.createCell(1);
				cell1.setCellType(Cell.CELL_TYPE_FORMULA);
				
				
				row1.createCell(1).setCellValue(FilenameUtils.removeExtension(result1.get(p)));
				FileOutputStream out = new FileOutputStream(filePath);
				workbook.write(out);
				out.close();

				workbook.createSheet(FilenameUtils.removeExtension(result1
						.get(p)));
				FileOutputStream fileOut = new FileOutputStream(filePath);
				workbook.write(fileOut);
				fileOut.flush();
				fileOut.close();
				fetchURL(result1.get(p));
				
			}
			createHyperlink(result1);
		}

	}
	
	public void createHyperlink(List<String> result1) throws IOException{
		HSSFSheet sheetResult = workbook.getSheet("Check Result");
		for (int p = 0; p < result1.size(); p++) {
			Row row1 = sheetResult.createRow(p + 1);
			row1.createCell(0);
			Cell cell1 = row1.createCell(1);
			cell1.setCellType(Cell.CELL_TYPE_FORMULA);
			cell1.setCellFormula(("HYPERLINK(\"#'"+FilenameUtils.removeExtension(result1.get(p))+"'!B2\",\""+FilenameUtils.removeExtension(result1.get(p))+"\")"));
			sheetResult.autoSizeColumn(0);
			sheetResult.setColumnWidth(1,7500);
			FileOutputStream out = new FileOutputStream(filePath);
			workbook.write(out);
			out.close();
		}
	}

	public void fetchURL(String fileName) throws InvalidFormatException,
			IOException {
		String xmlFile = "D:\\XML\\" + fileName;
		String fileName1 = FilenameUtils.removeExtension(fileName);
		int code = 0;	
		HSSFSheet sheet = workbook.getSheet(fileName1);
		
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle = workbook.createCellStyle();
		HSSFFont hSSFFont = workbook.createFont();
		hSSFFont.setFontName(HSSFFont.FONT_ARIAL);
		hSSFFont.setFontHeightInPoints((short) 13);
		hSSFFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		hSSFFont.setColor(HSSFColor.BLACK.index);
		
	//	cellStyle.setFillBackgroundColor(HSSFColor.YELLOW.index);		
		cellStyle.setFillForegroundColor(HSSFColor.YELLOW.index);
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cellStyle.setFont(hSSFFont);
		
		Row row12 = sheet.createRow(1);
		row12.createCell(0);
		Cell cell1B = row12.createCell(1);
		cell1B.setCellValue("URLs");
		
		cell1B.setCellStyle(cellStyle);
		
		Cell cell1F = row12.createCell(2);
		cell1F.setCellValue("Response Code");
		cell1F.setCellStyle(cellStyle);
		
		String fileStr = FileUtils.readFileToString(new File(xmlFile));
		GetUrl url = new GetUrl();
		List<String> value = url.extractUrls(fileStr);
		List<String> value1 = url.extractUrls(fileStr);
		List<WebElement> all_links_webpage = null;
		for (int i = 0; i < value.size(); i++) {

			if (value.get(i).toLowerCase()
					.contains("CRESCERANCE.COM".toLowerCase())) {
				driver.get(value.get(i));
				all_links_webpage = driver.findElements(By.tagName("a"));

				for (int j = 0; j < all_links_webpage.size(); j++) {
					if (all_links_webpage.get(j).getAttribute("href")
							.contains("http")) {

						value1.add(all_links_webpage.get(j)
								.getAttribute("href"));
					}
				}
			}

		}
		for (int s = 0; s < value1.size(); s++) {
			driver.navigate().to(value1.get(s));
			code = getURLResponse(value1.get(s), value1);

			Row row11 = sheet.createRow(s + 2);
			row11.createCell(0);
			row11.createCell(1).setCellValue(value1.get(s));
			sheet.autoSizeColumn(1);
			row11.createCell(2);
			row11.createCell(2).setCellValue(code);
			sheet.autoSizeColumn(2);

		}
		FileOutputStream fileOut = new FileOutputStream(filePath);
		workbook.write(fileOut);
		fileOut.flush();
		fileOut.close();

	}

	public int getURLResponse(String url1, List<String> value)
			throws IOException {
		int code = 0;
		try {
			URL url = new URL(url1);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();

			code = connection.getResponseCode();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return code;

	}

	@AfterClass
	public void tearDown() {
		driver.quit();

	}
}
