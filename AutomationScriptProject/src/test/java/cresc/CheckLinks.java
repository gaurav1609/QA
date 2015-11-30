package cresc;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class CheckLinks {
	WebDriver driver;
	public static WebDriverWait wait;
	@BeforeClass
	public void Setup(){
		driver = new FirefoxDriver();
		wait = new WebDriverWait(driver, 60);
		
		
	}

@Test
  public void f() throws IOException {
	driver.navigate().to("http://www.linkedin.com/");
	System.out.println(wait);
	  //driver.navigate().to("http://www.linkedin.com/");
	  wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btn-submit")));
	  List<WebElement> all_links_webpage = driver.findElements(By.tagName("a"));
	  System.out.println(all_links_webpage.size());
	  String[] value = null;
	  value= new String[all_links_webpage.size()];
	  for (int i=0; i<all_links_webpage.size(); i++){
		  if (all_links_webpage.get(i).getAttribute("href")
					.contains("http")) {
			  System.out.println(all_links_webpage.get(i));
		 value[i]=all_links_webpage.get(i).getAttribute("href");	
		 
		 
				URL url;
				try {
					url = new URL(value[i]);
					HttpURLConnection connection = (HttpURLConnection) url
							.openConnection();
					connection.setRequestMethod("GET");
					connection.connect();

					int code = connection.getResponseCode();
					System.out.println(code);
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				

			} 
	  }
		  
  }
  public void tearDown(){
	  driver.quit();
  }
}
