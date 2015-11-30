package cresc1;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class TestVideo {
	public WebDriver driver;
  @Test
  public void f() throws InterruptedException {
	  driver = new ChromeDriver();
	  driver.manage().window().maximize();
	  driver.get("https://www.youtube.com/watch?v=LeLn8sEAKfE");
	  JavascriptExecutor js = (JavascriptExecutor) driver;
	    
	  //play video
	  js .executeScript("document.getElementById(\"video\").pause()");
	  Thread.sleep(5000);
	    
	  //pause playing video 
	  js .executeScript("document.getElementById(\"video\").play()");
	    
	  //check video is paused
	  System.out.println(js .executeScript("document.getElementById(\"video\").paused"));
	    
	  js .executeScript("document.getElementById(\"video\").play()");
	    
	  // play video from starting
	  js .executeScript("document.getElementById(\"video\").currentTime=0");
	  Thread.sleep(5000);
	    
	  //reload video
	  js .executeScript("document.getElementById(\"video\").load()");
  }
}
