package testBase;

import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

public class BaseClass {


    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static Logger logger;
    public Properties p;

//will return the driver for the current thread
    public static WebDriver getDriver() {
        return driver.get();
    }

    private static void setDriver(WebDriver webDriver) {
        driver.set(webDriver);
    }

    @BeforeTest
    @Parameters({"browser"})
    public void setup(String browser) throws IOException {
        p = GenericMethods.loadProperties("./src/test/resources/config.properties");
        logger = LogManager.getLogger(this.getClass());

        WebDriver webDriver;

        switch (browser.toLowerCase()) {

            case "chrome":
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--disable-notifications");
                options.addArguments("--guest");
                webDriver = new ChromeDriver(options);
                break;

            case "edge":
                webDriver = new EdgeDriver();
                break;

            case "firefox":
                webDriver = new FirefoxDriver();
                break;

            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }

        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        webDriver.get(p.getProperty("url"));
// stores the driver in ThreadLocal for this thread
        setDriver(webDriver);
    }

    @AfterSuite
    public void teardown() {
        if (getDriver() != null) {
            getDriver().quit();
            logger.info("Browser closed successfully");
            driver.remove();
        }
    }
}