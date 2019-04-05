import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;

public class FirstTest {

    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception
    {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "AndroidTestDevice");
        capabilities.setCapability("platformVersion", "6.0");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", ".main.MainActivity");
        capabilities.setCapability("app", "/Users/g.sarkisov/Desktop/JavaAppiumAutomation/apks/org.wikipedia.apk");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities); // передали андроид-драйверу
        // все необходимые для запуска параметры

    }

    @After
    public void tearDown()
    {
        driver.quit();
    }

    @Test
    public void firstTest()
    {
        // нажмем на строку поиска для начала поиска
        WebElement element_to_init_search = driver.findElementByXPath("//*[contains(@text,'Search Wikipedia')]");
        element_to_init_search.click();

        WebElement element_to_enter_search_line = waitForElementPresentByXpath(
                "//*[contains(@text,'Search…')]",
                "Cannot find search input"
        );

                //driver.findElementById("org.wikipedia:id/search_src_text");
        element_to_enter_search_line.sendKeys("Java");

        // System.out.println("First test run");
    }

    // метод таймаута для элементов, найденных через xpath
    private WebElement waitForElementPresentByXpath(String xpath, String error_message, long timeoutInSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        By by = By.xpath(xpath);
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );

    }

    // перегрузка метода позволяет использовать таймаут по умолчанию - то есть,
    // если указать значение, то будет использовано прописанное, а если нет, то по умолчанию
    private WebElement waitForElementPresentByXpath(String xpath, String error_message)
    {
        return waitForElementPresentByXpath(xpath, error_message, 5);
    }
}
