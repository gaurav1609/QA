package cresc;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class Naptol {
	public static WebDriverWait wait;
	int timeoutSeconds = 15;

	@Test
	public void f() {

		WebDriver driver = new FirefoxDriver();
		wait = new WebDriverWait(driver, timeoutSeconds);
		driver.get("http://www.w3schools.com/aspnet/showaspx.asp?filename=demo_radiobuttonlist");
		driver.manage().window().maximize();
		// driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.tagName("iframe")));
		driver.switchTo().frame(
				driver.findElements(By.tagName("iframe")).get(2));
		System.out.println("Inside Iframe");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.id("radiolist1_3")));

		List<WebElement> radioList = driver.findElements(By
				.tagName("radiolist1"));
		System.out.println(radioList.size());

	}
}
