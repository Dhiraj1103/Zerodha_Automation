package pageObject;

import java.time.Duration;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import testBase.BaseClass;

public class OrderDetailsPage extends BasePage{

	public OrderDetailsPage(WebDriver driver) {
		super(driver);
	}
	Logger logger = BaseClass.logger;
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));


	@FindBy(xpath  = "//span[contains(@class,'text-label small order-status-label')]")
    WebElement orderStatus;	
	
	@FindBy(xpath = "//div[@class='seven columns text-right']")
	List<WebElement> orderDetails;
	
	@FindBy(xpath = "//div[@class='eight columns tradingsymbol-wrapper']/h3")
	WebElement orderName;
	
	@FindBy(xpath = "//button[contains(text(),'Close')]")
	WebElement btnClose;
	
	
	public void clickOrderStatus() {
		verifyVisibilityOfElement(orderStatus);
		orderStatus.click();
		logger.info("Clicked on order status");
	}
	
	public void verifyOrder(String qty, String price, String trigger, String orderType, String productType, String validityType, String order) {
		verifyVisibilityOfElements(orderDetails);
		String actQty = orderDetails.get(0).getText().trim();
	    String actPrice = orderDetails.get(1).getText().trim();
	    String actTriggerPrice = orderDetails.get(3).getText().trim();
	    String actOrderType = orderDetails.get(4).getText().trim();
	    String actProductType = orderDetails.get(5).getText().trim();
	    String actValidityType = orderDetails.get(6).getText().trim();
	    String actOrderName = orderName.getText().trim();

	    boolean orderIsDisplayed =
	            actQty.contains(qty) &&
	            actPrice.contains(price) &&
	            actTriggerPrice.contains(trigger) &&
	            actOrderType.contains(orderType) &&
	            actProductType.contains(productType) &&
	            actValidityType.contains(validityType) &&
	            actOrderName.contains(order);

	    Assert.assertTrue(orderIsDisplayed,
	            "Test case failed - Order details mismatch");

	    logger.info("Test case passed - Order placed successfully");
	}
	
	public void verifyStoplossTargetOrder(String qty, String price, String trigger, String orderType, 
			String productType, String validityType, String order, String stoploss, String target) {
		verifyVisibilityOfElements(orderDetails);
		String actQty = orderDetails.get(0).getText().trim();
	    String actPrice = orderDetails.get(1).getText().trim();
	    String actTriggerPrice = orderDetails.get(3).getText().trim();
	    String actOrderType = orderDetails.get(4).getText().trim();
	    String actProductType = orderDetails.get(5).getText().trim();
	    String actValidityType = orderDetails.get(6).getText().trim();
	    String actStoploss = orderDetails.get(12).getText().trim();
	    String actTarget = orderDetails.get(12).getText().trim();
	    String actOrderName = orderName.getText().trim();

	    boolean orderIsDisplayed =
	            actQty.contains(qty) &&
	            actPrice.contains(price) &&
	            actTriggerPrice.contains(trigger) &&
	            actOrderType.contains(orderType) &&
	            actProductType.contains(productType) &&
	            actValidityType.contains(validityType) &&
	            actStoploss.contains(stoploss) &&
	            actTarget.contains(target) &&
	            actOrderName.contains(order);

	    Assert.assertTrue(orderIsDisplayed,"Test case failed - Order details mismatch");

	    logger.info("Test case passed - Order placed successfully");
	}
	
	public void clickCloseButton() {
		btnClose.click();
		logger.info("Clicked on close button successfully");
	}

}


















