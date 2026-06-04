package listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import testBase.BaseClass;

 // Automatically retries a failed test
// Useful for flaky tests caused by timing issues or network delays
 
public class RetryAnalyzer implements IRetryAnalyzer {

    private int retryCount = 0;
    private static final int MAX_RETRY = 2; // retries per failed test

    @Override
    public boolean retry(ITestResult result) {
        if (retryCount < MAX_RETRY) {
            retryCount++;
            BaseClass.logger.warn("RETRYING TEST  : " + result.getName()  + " | Attempt " + retryCount + " of " + MAX_RETRY);
            return true;  //run the test again
        }
        BaseClass.logger.error("RETRY EXHAUSTED: " + result.getName()+ " failed after " + MAX_RETRY + " retries");
        return false; //stop retrying
    }
}
