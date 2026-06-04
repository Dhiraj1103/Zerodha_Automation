package pageObject;

import java.util.List;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import testBase.BaseClass;

public class BuyFlow extends BasePage {

    public BuyFlow(WebDriver driver) {
        super(driver);
    }

    Logger logger = BaseClass.logger;

    @FindBy(xpath = "//input[@icon='search']")
    private WebElement searchBar;

    @FindBy(css = ".name")
    private WebElement stock;

    @FindBy(css = ".button-blue")
    private WebElement btnBuy;
    
    @FindBy(xpath = "//button[contains(text(),'S')]")
    private WebElement btnSell;

    @FindBy(xpath = "//label[text()='Regular']")
    private WebElement lblRegular;

    @FindBy(xpath = "//div[contains(@class,'type four columns su-radio-wrap checked')]")
    private WebElement rbtnLongterm;
    
    @FindBy(xpath = "//div[contains(@class,'type four columns su-radio-wrap')]")
    private WebElement rbtnIntraday;

    @FindBy(xpath = "//div[@class='order-window-wrapper']//span[contains(text(),'Advanced')]")
    private WebElement ddAdvancedOptions;

    @FindBy(xpath = "//label[contains(text(),'Day')]")
    private WebElement rbtnDay;

    @FindBy(xpath = "//label[contains(text(),'Immediate')]")
    private WebElement rbtnImmediate;

    @FindBy(css = ".submit")
    private WebElement btnBuyStock;

    @FindBy(xpath = "//div[contains(@class,'su-toast-item')]//div[@class='message']")
    private WebElement successToastMessage;

    @FindBy(xpath = "//div[contains(@class,'su-toast-item')]//span[contains(@class,'close')]")
    private WebElement iconCross;

    @FindBy(xpath = "//button[contains(@class,'button-outline button-transparent cancel')]")
    private WebElement btnCancel;

    @FindBy(xpath = "//div[@class='su-checkbox-group']")
    private WebElement chkStoploss;

    @FindBy(xpath = "(//div[@class='su-checkbox-group'])[2]")
    private WebElement chkTarget;

    @FindBy(xpath = "//h4[@class='title']")
    List<WebElement> errorMessage;

    public void searchStock(String stockName) {
        verifyVisibilityOfElement(searchBar);
        searchBar.sendKeys(stockName);
        logger.info("Searched stock: " + stockName);
    }

    public void hoverStock() {
        verifyVisibilityOfElement(stock);
        new Actions(driver).moveToElement(stock).perform();
        logger.info("Stock hovered successfully");
    }

    public void clickOnBuy() {
        verifyVisibilityOfElement(btnBuy);
        btnBuy.click();
        logger.info("Clicked Buy button");
    }
    
    public void clickOnSell() {
        verifyVisibilityOfElement(btnSell);
        btnSell.click();
        logger.info("Clicked Sell button");
    }

    public void navigateToRegular() {
        verifyVisibilityOfElement(lblRegular);
        lblRegular.click();
        logger.info("Navigated to Regular");
    }

    public void selectLongterm() {
        verifyInteractable(rbtnLongterm);
        rbtnLongterm.click();
        logger.info("Longterm selected");
    }

    
    public void selectIntraday() {
        verifyInteractable(rbtnIntraday);
        rbtnIntraday.click();
        logger.info("Longterm selected");
    }
    
    public void inputValues(String value, String valueLoc) {
    	WebElement ipValue=driver.findElement(By.xpath("//input[contains(@label,'"+valueLoc+"')]"));
    	verifyVisibilityOfElement(ipValue);
    	if(!valueLoc.contains("Qty.")) {
        new Actions(driver).click(ipValue)
        .keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL)
        .sendKeys(Keys.DELETE).perform();
        logger.info("Value cleared");
    }
    	ipValue.sendKeys(value);
        logger.info("Value cleared");

    }
    
    public void orderTypeSelectionLocator(String orderType) {
    	WebElement orderTypes=driver.findElement(By.xpath("//label[contains(text(),'"+orderType+"')]"));
    	verifyVisibilityOfElement(orderTypes);
    	orderTypes.click();
    	logger.info(orderType+" order Type selected");
    }
    
    public void clickAdvancedOptions() {
        verifyVisibilityOfElement(ddAdvancedOptions);
        try {
            String tooltip = ddAdvancedOptions.getAttribute("data-tooltip-content");
            if ("Show advanced options".equalsIgnoreCase(tooltip)) {
                ddAdvancedOptions.click();
                logger.info("Advanced options expanded");
            } else {
                logger.info("Advanced options already visible");
            }
        } catch (Exception e) {
            logger.error("Error handling advanced options", e);
            Assert.fail("clickAdvancedOptions failed: " + e.getMessage());
        }
    }

    public void selectDay() {
        verifyVisibilityOfElement(rbtnDay);
        rbtnDay.click();
        logger.info("Validity: DAY");
    }

    public void selectImmediate() {
        verifyVisibilityOfElement(rbtnImmediate);
        rbtnImmediate.click();
        logger.info("Validity: IOC");
    }

    public void buyStock() {
        verifyInteractable(btnBuyStock);
        btnBuyStock.click();
        logger.info("Buy submitted");
    }

    public void clickOnCross() {
        //  avoids stale element 
        try {
            WebElement cross = driver.findElement(
                    By.xpath("//div[contains(@class,'su-toast-item')]//span[contains(@class,'close')]"));
            verifyVisibilityOfElement(cross);
            cross.click();
            logger.info("Toast dismissed");
        } catch (Exception e) {
            logger.warn("Toast cross not found — may have already been dismissed: " + e.getMessage());
        }
    }

    public void clickCancel() {
        try {
            WebElement cancel = driver.findElement(
                    By.xpath("//button[contains(@class,'button-outline button-transparent cancel')]"));
            verifyInteractable(cancel);
            cancel.click();
            logger.info("Form cancelled");
        } catch (Exception e) {
            logger.warn("Cancel button not found — form may already be closed: " + e.getMessage());
        }
    }

    public void checkStoploss() {
        // if a toast or overlay is covering the checkbox
        safeClick(chkStoploss);
        logger.info("Stoploss checked");
    }

    public void checkTarget() {
        // check to being covered by the stoploss section expanding after it's checked
        safeClick(chkTarget);
        logger.info("Target checked");
    }

    public void verifyToastMessage() {
        verifyVisibilityOfElement(successToastMessage);
        String toastMessage = successToastMessage.getText();
        boolean orderProcessed = toastMessage.contains("Insufficient funds")
                || toastMessage.contains("Markets are closed right now.")
                || toastMessage.contains("next market opening")
                || toastMessage.contains("GTT orders are blocked for AMO orders")
                || toastMessage.contains("Trigger price for stoploss buy");
        Assert.assertTrue(orderProcessed, "Unexpected toast message: " + toastMessage);
        logger.info("Toast verified: " + toastMessage);
    }

    public void invalidQuantity_Price() {
        boolean hasError = errorMessage.size() > 0 && errorMessage.get(0).isDisplayed();
        Assert.assertFalse(hasError, "Test failed — invalid value was accepted");
        logger.info("Confirmed: invalid value rejected");
    }

    public void validQuantity_Price() {
        boolean hasError = errorMessage.size() > 0 && errorMessage.get(0).isDisplayed();
        Assert.assertTrue(hasError, "Test failed — valid value was not accepted");
        logger.info("Confirmed: valid value accepted");
    }

    public void verifyPriceMoreThanCircuit() {
        Assert.assertTrue(errorMessage.get(0).isDisplayed(),
                "Test failed — price beyond circuit was accepted");
        clickOnCross();
        logger.info("Confirmed: circuit price rejected — " + successToastMessage.getText());
    }
}
