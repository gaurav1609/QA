package cresc;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import org.apache.http.util.EntityUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.Point;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteTouchScreen;
import org.openqa.selenium.interactions.HasTouchScreen;
import org.openqa.selenium.interactions.TouchScreen;
import org.openqa.selenium.interactions.touch.TouchActions;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AppiumTest {
    private WebDriver driver;

    private List<Integer> values;

    private static final int MINIMUM = 0;
    private static final int MAXIMUM = 10;

    @BeforeMethod
    public void setUp() throws Exception {
        // set up appium
        File classpathRoot = new File(System.getProperty("user.dir"));
        System.out.println(classpathRoot);
        File appDir = new File(classpathRoot, "../../../apps/TestApp/build/Release-iphonesimulator");
        System.out.println(appDir);
        File app = new File(appDir, "TestApp.app");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.BROWSER_NAME, "iOS");
        capabilities.setCapability(CapabilityType.VERSION, "6.0");
        capabilities.setCapability(CapabilityType.PLATFORM, "Mac");
        capabilities.setCapability("app", app.getAbsolutePath());
        driver = new SwipeableWebDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        values = new ArrayList<Integer>();
    }

    @AfterMethod
    public void tearDown() throws Exception {
        driver.quit();
    }


    private void populate() {
        //populate text fields with two random number
        List<WebElement> elems = driver.findElements(By.tagName("textField"));
        Random random = new Random();
        for (WebElement elem : elems) {
            int rndNum = random.nextInt(MAXIMUM - MINIMUM + 1) + MINIMUM;
            elem.sendKeys(String.valueOf(rndNum));
            values.add(rndNum);
        }
    }

    @Test
    public void testUIComputation() throws Exception {

        // populate text fields with values
        populate();
        // trigger computation by using the button
        WebElement button = driver.findElement(By.tagName("button"));
        button.click();
        // is sum equal ?
        WebElement texts = driver.findElement(By.tagName("staticText"));
        assertEquals(texts.getText(), String.valueOf(values.get(0) + values.get(1)));
    }

    @Test
    public void testActive() throws Exception {
        WebElement text = driver.findElement(By.xpath("//textfield[1]"));
        assertTrue(text.isDisplayed());

        WebElement button = driver.findElement(By.xpath("//button[1]"));
        assertTrue(button.isDisplayed());
    }

    @Test
    public void testBasicAlert() throws Exception {
        driver.findElement(By.xpath("//button[2]")).click();

        Alert alert = driver.switchTo().alert();
        //check if title of alert is correct
        assertEquals(alert.getText(), "Cool title");
        alert.accept();
    }

    @Test
    public void testBasicTagName() throws Exception {
        WebElement text = driver.findElement(By.xpath("//textfield[1]"));
        assertEquals(text.getTagName(), "UIATextField");
    }

    @Test
    public void testBasicButton() throws Exception {
        WebElement button = driver.findElement(By.xpath("//button[1]"));
        assertEquals(button.getText(), "ComputeSumButton");
    }

    @Test
    public void testClear() throws Exception {
        WebElement text = driver.findElement(By.xpath("//textfield[1]"));
        text.sendKeys("12");
        text.clear();

        assertEquals(text.getText(), "");
    }

    @Test
    public void testHideKeyboard() throws Exception {
        driver.findElement(By.xpath("//textfield[1]")).sendKeys("12");

        WebElement button = driver.findElement(By.name("Done"));
        assertTrue(button.isDisplayed());

        button.click();
    }

    @Test
    public void testFindElementByTagName() throws Exception {
        Random random = new Random();

        WebElement text = driver.findElement(By.tagName("textField"));
        int number = random.nextInt(MAXIMUM - MINIMUM + 1) + MINIMUM;
        text.sendKeys(String.valueOf(number));

        driver.findElement(By.tagName("button")).click();

        // is sum equal ?
        WebElement sumLabel = driver.findElement(By.tagName("staticText"));
        assertEquals(sumLabel.getText(), String.valueOf(number));
    }

    @Test
    public void testFindElementsByTagName() throws Exception {
        Random random = new Random();

        WebElement text = driver.findElements(By.tagName("textField")).get(1);

        int number = random.nextInt(MAXIMUM - MINIMUM + 1) + MINIMUM;
        text.sendKeys(String.valueOf(number));

        driver.findElements(By.tagName("button")).get(0).click();

        // is sum equal ?
        WebElement texts = driver.findElements(By.tagName("staticText")).get(0);
        assertEquals(texts.getText(), String.valueOf(number));
    }

    @Test
    public void testFindElementByName() throws Exception {
        Random random = new Random();

        WebElement text = driver.findElement(By.name("TextField1"));

        int number = random.nextInt(MAXIMUM - MINIMUM + 1) + MINIMUM;
        text.sendKeys(String.valueOf(number));

        // is sum equal ?
        WebElement sumLabel = driver.findElement(By.name("SumLabel"));
        driver.findElement(By.name("ComputeSumButton")).click();

        assertEquals(sumLabel.getText(), String.valueOf(number));
    }

    @Test
    public void testFindElementsByName() throws Exception {
        Random random = new Random();

        WebElement text = driver.findElements(By.name("TextField1")).get(0);

        int number = random.nextInt(MAXIMUM - MINIMUM + 1) + MINIMUM;
        text.sendKeys(String.valueOf(number));

        // is sum equal ?
        WebElement sumLabel = driver.findElements(By.name("SumLabel")).get(0);
        driver.findElements(By.name("ComputeSumButton")).get(0).click();

        assertEquals(sumLabel.getText(), String.valueOf(number));
    }

    @Test
    public void testFindElementByXpath() throws Exception {
        Random random = new Random();

        WebElement text = driver.findElement(By.xpath("//textfield[1]"));

        int number = random.nextInt(MAXIMUM - MINIMUM + 1) + MINIMUM;
        text.sendKeys(String.valueOf(number));

        // is sum equal ?
        driver.findElement(By.xpath("//button[1]")).click();

        WebElement sumLabel = driver.findElement(By.xpath("//text[1]"));
        assertEquals(sumLabel.getText(), String.valueOf(number));
    }

    @Test
    public void testFindElementsByXpath() throws Exception {
        Random random = new Random();

        WebElement text = driver.findElements(By.xpath("//textfield")).get(1);

        int number = random.nextInt(MAXIMUM - MINIMUM + 1) + MINIMUM;
        text.sendKeys(String.valueOf(number));

        // is sum equal ?
        driver.findElements(By.xpath("//button")).get(0).click();

        WebElement sumLabel = driver.findElements(By.xpath("//text")).get(0);
        assertEquals(sumLabel.getText(), String.valueOf(number));
    }

    @Test
    public void testAttribute() throws Exception {
        Random random = new Random();

        WebElement text = driver.findElement(By.xpath("//textfield[1]"));

        int number = random.nextInt(MAXIMUM - MINIMUM + 1) + MINIMUM;
        text.sendKeys(String.valueOf(number));

        assertEquals(text.getAttribute("name"), "TextField1");
        assertEquals(text.getAttribute("label"), "TextField1");
        assertEquals(text.getAttribute("value"), String.valueOf(number));
    }

    @Test
    public void testSlider() throws Exception {
        //get the slider
        WebElement slider = driver.findElement(By.xpath("//slider[1]"));
        assertEquals(slider.getAttribute("value"), "50%");
        TouchActions drag = new TouchActions(driver).flick(slider, new Integer(-1), 0, 0);
        drag.perform();
        assertEquals(slider.getAttribute("value"), "0%");
    }

    @Test
    public void testLocation() throws Exception {
        WebElement button = driver.findElement(By.xpath("//button[1]"));

        Point location = button.getLocation();

        assertEquals(location.getX(), 94);
        assertEquals(location.getY(), 122);
    }

    @Test
    public void testSessions() throws Exception {
        HttpGet request = new HttpGet("http://localhost:4723/wd/hub/sessions");
        HttpClient httpClient = new DefaultHttpClient();
        HttpResponse response = httpClient.execute(request);
        HttpEntity entity = response.getEntity();
        JSONObject jsonObject = (JSONObject) new JSONParser().parse(EntityUtils.toString(entity));

        String sessionId = ((RemoteWebDriver) driver).getSessionId().toString();
        assertEquals(sessionId, jsonObject.get("sessionId"));
    }

    @Test
    public void testSize() {
        Dimension text1 = driver.findElement(By.xpath("//textfield[1]")).getSize();
        Dimension text2 = driver.findElement(By.xpath("//textfield[2]")).getSize();
        assertEquals(text1.getWidth(), text2.getWidth());
        assertEquals(text1.getHeight(), text2.getHeight());
    }

    public class SwipeableWebDriver extends RemoteWebDriver implements HasTouchScreen {
        private RemoteTouchScreen touch;

        public SwipeableWebDriver(URL remoteAddress, Capabilities desiredCapabilities) {
            super(remoteAddress, desiredCapabilities);
            touch = new RemoteTouchScreen(getExecuteMethod());
        }

        public TouchScreen getTouch() {
            return touch;
        }
    }
}
