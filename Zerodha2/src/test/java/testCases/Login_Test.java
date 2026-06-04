package testCases;

import static org.testng.Assert.assertTrue;

import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import pageObject.HomePage;
import pageObject.LoginPage;
import testBase.BaseClass;
import testBase.GenericMethods;

public class Login_Test extends BaseClass {

	public Properties p;

	@Test()
	public void login() {

		logger.info("*************** Login Started ******************");

		try {		
			p=GenericMethods.loadProperties(System.getProperty("user.dir") + "//src//test//resources//config.properties");

			LoginPage login = new LoginPage(getDriver());

			String userid = p.getProperty("userid");
			Assert.assertNotNull(userid, "User ID is missing");

			login.setUserId(userid);
			logger.info("User ID entered successfully");

			String password = p.getProperty("password");
			Assert.assertNotNull(password, "Password is missing");

			login.setPass(password);
			logger.info("Password entered successfully");

			login.clickLogin();
			logger.info("Clicked on Login button successfully");

			WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(30));

			wait.until(ExpectedConditions.presenceOfElementLocated(
					By.xpath("(//button[@type='button'])[2]")));
			
			

			logger.info("OTP entered successfully");

		//	String expectedURL = "https://kite.zerodha.com/dashboard";
			String expectedURL = "https://kite.zerodha.com/";
			String actualURL = getDriver().getCurrentUrl();
			HomePage hm = new HomePage(getDriver());

			if (actualURL.contains(expectedURL)) {

				logger.info("Login successful");

				Assert.assertTrue(true);

				hm.clickUnderstand();

			} else {

				logger.error("Login failed - URL mismatch");
				logger.error("Expected URL: " + expectedURL);
				logger.error("Actual URL: " + actualURL);

				Assert.fail("Login Failed");

			}
            hm.navigateToOrders();
			logger.info("*************** Login Ended Successfully *******************");

		}

		// File Handling Exception
		catch (IOException e) {

			logger.error("Config file not found: " + e.getMessage());
			Assert.fail("IOException occurred");

		}

		// Wait Timeout Exception
		catch (TimeoutException e) {

			logger.error("Dashboard not loaded within wait time");
			Assert.fail("TimeoutException - Login Failed");

		}

		// Selenium Exception
		catch (WebDriverException e) {

			logger.error("WebDriver Exception: " + e.getMessage());
			Assert.fail("WebDriver Exception occurred");

		}

		// Generic Exception
		catch (Exception e) {

			logger.error("Exception occurred: " + e.getMessage());
			Assert.fail("Test Failed due to Exception");

		}
	}
}