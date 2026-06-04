package testCases;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pageObject.BuyFlow;
import pageObject.BuyFlowHelper;
import testBase.BaseClass;

public class OrderValidations extends BaseClass{
    // VALIDATION TESTS (TC_001–TC_009)

    private BuyFlowHelper helper;
    private BuyFlow buyflow;
    

    @BeforeMethod
    public void initHelper() {
        helper = new BuyFlowHelper(getDriver());
        buyflow = new BuyFlow(getDriver());
    }
    @Test(description = "TC_001 - Buy order with quantity = 0 should not be accepted", priority = 1)
    public void verifyQuantityAs0() throws InterruptedException {
        logger.info("***** TC_001 started *****");
        helper.buyOrder(false, true,"Coal", "market", "0", false, "0", false, "0", "day", false);
        buyflow.invalidQuantity_Price();
        logger.info("***** TC_001 ended *****");
    }

    @Test(description = "TC_002 - Buy order with quantity < 0 should not be accepted", priority = 2)
    public void verifyQuantityLessThan0() throws InterruptedException {
        logger.info("***** TC_002 started *****");
        helper.buyOrder(false, true,"Coal", "market", "-1", false, "0", false, "0", "day", false);
        buyflow.invalidQuantity_Price();
        logger.info("***** TC_002 ended *****");
    }

    @Test(description = "TC_003 - Buy order with quantity exceeding exchange limit should not be accepted", priority = 3)
    public void verifyQuantityMoreExchangeQuantity() throws InterruptedException {
        logger.info("***** TC_003 started *****");
        helper.buyOrder(false, true,"Coalindia", "market", "50000000", false, "0", false, "0", "day", false);
        buyflow.verifyPriceMoreThanCircuit();
        logger.info("***** TC_003 ended *****");
    }

    @Test(description = "TC_004 - Buy order with price = 0 should not be accepted", priority = 4)
    public void verifyPriceAs0() throws InterruptedException {
        logger.info("***** TC_004 started *****");
        helper.buyOrder(false, true,"Coalindia", "limit", "2", true, "0", false, "0", "day", false);
        buyflow.invalidQuantity_Price();
        logger.info("***** TC_004 ended *****");
    }

    @Test(description = "TC_005 - Buy order with price above upper circuit should not be accepted", priority = 5)
    public void priceMorethanCircuit() throws InterruptedException {
        logger.info("***** TC_005 started *****");
        helper.buyOrder(false, true,"Coalindia", "limit", "2", true, "5000", false, "0", "day", false);
        buyflow.verifyPriceMoreThanCircuit();
        logger.info("***** TC_005 ended *****");
    }

    @Test(description = "TC_006 - Buy order with price below lower circuit should not be accepted", priority = 6)
    public void priceLessthanCircuit() throws InterruptedException {
        logger.info("***** TC_006 started *****");
        helper.buyOrder(false, true,"Coalindia", "limit", "2", true, "200", false, "0", "day", false);
        buyflow.verifyPriceMoreThanCircuit();
        logger.info("***** TC_006 ended *****");
    }

    @Test(description = "TC_007 - Buy order where trigger price > price should not be accepted", priority = 7)
    public void triggerPriceGreaterThanPrice() throws InterruptedException {
        logger.info("***** TC_007 started *****");
        helper.buyOrder(false, true,"Coalindia", "sl", "2", true, "465", true, "467", "day", false);
        buyflow.invalidQuantity_Price();
        logger.info("***** TC_007 ended *****");
    }

    @Test(description = "TC_008 - Buy order where trigger price < market price should not be accepted", priority = 8)
    public void triggerPriceLessThanMarketPrice() throws InterruptedException {
        logger.info("***** TC_008 started *****");
        helper.buyOrder(false, true,"Coalindia", "sl", "2", true, "465", true, "0", "day", false);
        buyflow.verifyPriceMoreThanCircuit();
        logger.info("***** TC_008 ended *****");
    }
}
