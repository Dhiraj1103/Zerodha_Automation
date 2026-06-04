package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class GenericMethods {

    public static Properties loadProperties(String loc) throws IOException {
        Properties p = new Properties();
        FileReader file = new FileReader(loc);
        p.load(file);
        return p;
    }

    public static String takeScreenshot(WebDriver driver, String testName, Logger logger) {
        try {
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String path = System.getProperty("user.dir") + "/reports/" + testName + "_" + timestamp + ".png";
            File dest = new File(path);
            dest.getParentFile().mkdirs();
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(src, dest);
            logger.info("Screenshot saved: " + path);
            return path;   // ← return path for Extent to attach
        } catch (IOException e) {
            logger.error("Failed to save screenshot for: " + testName, e);
            return null;
        }
    }
}





