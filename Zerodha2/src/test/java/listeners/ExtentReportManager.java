package listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import java.io.File;
public class ExtentReportManager {

    private static ExtentReports extent;

  // safe for parallel runs 
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    // Call once at suite start — creates the HTML file
    public static void initReport() {
        String reportPath = System.getProperty("user.dir") + "/reports/ExtentReport.html";

        ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);

        // Customization
        spark.config().setTheme(Theme.DARK);        
        spark.config().setDocumentTitle("Zerodha Test Report");
        spark.config().setReportName("Buy Flow Automation Report");
        spark.config().setTimeStampFormat("dd-MM-yyyy HH:mm:ss");
        
        extent = new ExtentReports(); 
        extent.attachReporter(spark);

        // Add system info shown in report 
        extent.setSystemInfo("Project",     "Zerodha");
        extent.setSystemInfo("Environment", "Test");
        extent.setSystemInfo("Browser",     "Chrome");
        extent.setSystemInfo("Tester",      System.getProperty("user.name"));
        extent.setSystemInfo("OS",          System.getProperty("os.name"));
    }

    // Creates a new test node in the report
    public static void createTest(String testName, String description) {
        ExtentTest test = extent.createTest(testName, description);
        extentTest.set(test);
    }

    // Get current test used by pass/fail/skip
    public static ExtentTest getTest() {
        return extentTest.get();
    }

    // Writes everything to the HTML file
    // Call once at suite finish
    public static void flushReport() {
        if (extent != null) {
            extent.flush();
        }
    }
}









