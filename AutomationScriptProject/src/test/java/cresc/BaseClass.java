package cresc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.thoughtworks.selenium.*;
import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;

public class BaseClass {
	public static WebDriver driver;
	public Selenium selenium;
	public static JavascriptExecutor js;
	public int y = 0;
	public static WebDriverWait wait;
	int timeoutSeconds = 15;

	@BeforeClass
	public void setup() {

		driver = new FirefoxDriver();
		driver.manage().window().maximize();

	}

	public void setWait(WebDriver driver, int timeoutSeconds) {

		BaseClass.wait = new WebDriverWait(driver, this.timeoutSeconds);
		BaseClass.driver = driver;
	}

	public void setJavascriptExecutor(JavascriptExecutor obj) {
		js = obj;
	}

	@Parameters({ "browser" })
	@BeforeClass
	public void setup(String browser) throws MalformedURLException,
			InterruptedException {

		DesiredCapabilities capability = new DesiredCapabilities();

		if (browser.equalsIgnoreCase("firefox")) {
			// BrowserUsed = BrowserType.FireFox;
			System.out.println("firefox");
			capability = DesiredCapabilities.firefox();
			capability.setBrowserName("firefox");
			capability.setPlatform(org.openqa.selenium.Platform.ANY);
		}
		if (browser.equalsIgnoreCase("safari")) {
			// BrowserUsed = BrowserType.IE;
			System.out.println("safari");
			capability.setBrowserName(DesiredCapabilities.safari().getBrowserName());
			
			capability.setPlatform(org.openqa.selenium.Platform.MAC);
			String iepath = this.GetLocator("browserpath", "ie");
			System.setProperty("iexplore.binary", iepath);
		}
		if (browser.equalsIgnoreCase("chrome")) {
			// BrowserUsed = BrowserType.Chrome;
			System.out.println("chrome");
			capability.setBrowserName(DesiredCapabilities.chrome()
					.getBrowserName());
			capability.setPlatform(org.openqa.selenium.Platform.ANY);
			capability.setCapability("chrome.switches",
					Arrays.asList("--disable-extensions"));
			String chromepath = this.GetLocator("browserpath", "chrome");
			System.setProperty("chrome.binary", chromepath);
		}
		driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),
				capability);

		String home_url = GetData("urls", "home_url");
		selenium = new WebDriverBackedSelenium(driver, home_url);
		//driver.navigate().to(home_url);
		driver.navigate().to("http://gmail.com");
		driver.manage().window().maximize();

		wait = new WebDriverWait(driver, 120);
		js = (JavascriptExecutor) driver;

	}

	/*
	 * @Test public void checkUrl() throws InterruptedException, IOException {
	 * 
	 * List<String> result1 = new ArrayList<String>();
	 * 
	 * File root = new File("D:\\XML");
	 * 
	 * if (root.listFiles().length > 0) {
	 * 
	 * for (File file : root.listFiles()) {
	 * 
	 * if (file.getName().endsWith(".xml")) { result1.add(file.getName());
	 * 
	 * } } for (int p = 0; p < result1.size(); p++) {
	 * System.out.println(result1.get(p)); fetchingURL(result1.get(p)); } }
	 * 
	 * }
	 * 
	 * public void fetchingURL(String fileName) throws IOException { String
	 * xmlFile = "D:\\XML\\" + fileName; int code = 0; String fileName1 =
	 * FilenameUtils.removeExtension(fileName); String filename = fileName1 +
	 * ".xls"; HSSFWorkbook hwb = new HSSFWorkbook(); HSSFSheet sheet =
	 * hwb.createSheet("new sheet");
	 * 
	 * 
	 * String fileStr = FileUtils.readFileToString(new File(xmlFile)); GetUrl
	 * url = new GetUrl(); List<String> value = url.extractUrls(fileStr);
	 * List<String> value1 = url.extractUrls(fileStr); List<WebElement>
	 * all_links_webpage = null; for (int i = 0; i < value.size(); i++) {
	 * 
	 * if (value.get(i).toLowerCase()
	 * .contains("CRESCERANCE.COM".toLowerCase())) { driver.get(value.get(i));
	 * all_links_webpage = driver.findElements(By .tagName("a"));
	 * System.out.println("Size is: "+all_links_webpage.size());
	 * 
	 * for (int j = 0; j < all_links_webpage.size(); j++) { if
	 * (all_links_webpage.get(j).getAttribute("href") .contains("http")) {
	 * System.out.println(all_links_webpage.get(j).getAttribute("href"));
	 * 
	 * value1.add(all_links_webpage.get(j).getAttribute("href")); } } }
	 * 
	 * 
	 * 
	 * } for(int s=0; s<value1.size(); s++){
	 * driver.navigate().to(value1.get(s)); code = getURLResponse(value1.get(s),
	 * value1); HSSFRow row = sheet.createRow(s+1);
	 * row.createCell(0).setCellValue(value1.get(s)); row.createCell(1);
	 * row.createCell(2); row.createCell(3).setCellValue(code);
	 * row.createCell(4); row.createCell(5); row.createCell(6); FileOutputStream
	 * fileOut = new FileOutputStream(filename); hwb.write(fileOut);
	 * fileOut.close(); }
	 * 
	 * // createReport(fileName1, value); // updateReport(fileName1, value,
	 * code); }
	 * 
	 * public void getInternalUrl(String fileName, List<String> value) throws
	 * IOException {
	 * 
	 * List<WebElement> all_links_webpage = driver.findElements(By
	 * .tagName("a")); if (all_links_webpage.size() < 0) { try {
	 * 
	 * for (int j = 0; j < all_links_webpage.size(); j++) {
	 * 
	 * if (all_links_webpage.get(j).getAttribute("href") .contains("http")) {
	 * getURLResponse(all_links_webpage.get(j) .getAttribute("href"), value); }
	 * 
	 * } }
	 * 
	 * catch (MalformedURLException e) { e.printStackTrace(); } catch
	 * (IOException e) { e.printStackTrace();
	 * 
	 * } } }
	 * 
	 * public int getURLResponse(String url1, List<String> value) throws
	 * IOException { int code = 0; try { URL url = new URL(url1);
	 * HttpURLConnection connection = (HttpURLConnection) url .openConnection();
	 * connection.setRequestMethod("GET"); connection.connect();
	 * 
	 * code = connection.getResponseCode(); System.out.println(code);
	 * 
	 * } catch (MalformedURLException e) { e.printStackTrace(); } catch
	 * (IOException e) { e.printStackTrace(); } return code;
	 * 
	 * 
	 * }
	 */
	public String getTagValue(String sTag, Element eElement) {
		Node nValue = (Node) eElement.getElementsByTagName(sTag).item(0)
				.getChildNodes().item(0);

		return nValue.getNodeValue();
	}

	public String GetData(String tagName, String elementName) {
		return FetchDataFromXML("XmlData/Data_FF.xml", tagName, elementName);
		/*
		 * switch (BrowserUsed) { case FireFox:
		 * 
		 * case IE: return FetchDataFromXML("XmlData/Data_IE.xml", tagName,
		 * elementName); case Chrome: return
		 * FetchDataFromXML("XmlData/Data_CH.xml", tagName, elementName);
		 * default: return ""; }
		 */
	}

	public String FetchDataFromXML(String xml, String tagName,
			String elementName) {
		Element eElement;
		Node nNode;
		File fXmlFile = new File(xml);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		Document doc;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName(tagName);
			for (int i = 0; i < nList.getLength(); i++) {
				nNode = nList.item(i);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					eElement = (Element) nNode;
					return getTagValue(elementName, eElement);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}

	public String GetLocator(String tagName, String elementName) {

		return FetchDataFromXML("XmlData/Element.xml", tagName, elementName);

	}

	public String GetErrormsg(String tagName, String elementName) {

		return FetchDataFromXML("XmlData/ErrorMessage.xml", tagName,
				elementName);

	}

	@AfterClass
	public void tearDown() {
		driver.quit();

	}
}
