package listeners;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import testBase.BaseClass;
import testBase.GenericMethods;

public class TestListener implements ITestListener {

    private static final Logger logger = LogManager.getLogger(TestListener.class);

    @Override
    public void onStart(ITestContext context) {
        // initialize report at suite start
        ExtentReportManager.initReport();
        logger.info("========================================");
        logger.info("SUITE STARTED: " + context.getName());
        logger.info("========================================");
    }

    @Override
    public void onTestStart(ITestResult result) {
      // create a test node for each test
        ExtentReportManager.createTest(result.getName(), result.getMethod().getDescription());
      // log groups and priority as info in the report
        ExtentReportManager.getTest().info("Groups   : " + String.join(", ", result.getMethod().getGroups()));
        ExtentReportManager.getTest().info("Priority : " + result.getMethod().getPriority());
        logger.info("TEST STARTED : " + result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        // mark test as passed in report
        ExtentReportManager.getTest().pass("Test Passed");
        logger.info("TEST PASSED  : " + result.getName() + " (" + getElapsedTime(result) + " ms)");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        // take screenshot and attach to report
        String screenshotPath = GenericMethods.takeScreenshot(BaseClass.getDriver(), result.getName(), logger);
        try {
            ExtentReportManager.getTest().fail(result.getThrowable(),
                MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
        } catch (Exception e) {
            ExtentReportManager.getTest().fail(result.getThrowable());
            logger.warn("Could not attach screenshot to report: " + e.getMessage());
        }

        String fullMessage = result.getThrowable().getMessage();
        String shortMessage = fullMessage != null ? fullMessage.split("\n")[0] : "Unknown error";

        logger.error("TEST FAILED  : " + result.getName() + " (" + getElapsedTime(result) + " ms)");
        logger.error("  Reason     : " + shortMessage);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        //  mark as skipped in report
        ExtentReportManager.getTest().skip("Test Skipped" + (result.getThrowable() != null    ? " — " + result.getThrowable().getMessage() : ""));
        logger.warn("TEST SKIPPED : " + result.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        //  write the HTML file — MUST call this or report stays empty
        ExtentReportManager.flushReport();
        logger.info("========================================");
        logger.info("SUITE FINISHED: " + context.getName());
        logger.info("  Passed  : " + context.getPassedTests().size());
        logger.info("  Failed  : " + context.getFailedTests().size());
        logger.info("  Skipped : " + context.getSkippedTests().size());
        logger.info("========================================");
    }


    private long getElapsedTime(ITestResult result) {
        return result.getEndMillis() - result.getStartMillis();
    }
}