package pageObject;


import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import testBase.BaseClass;

public class BuyFlowHelper extends BaseClass  {

    private final BuyFlow buyflow;
    private final OrderDetailsPage orderPage;
    private final Logger logger = BaseClass.logger;

    public BuyFlowHelper(WebDriver driver) {
        this.buyflow   = new BuyFlow(driver);
        this.orderPage = new OrderDetailsPage(driver);
    }

    public void buyOrder(boolean sellIsEnabled, boolean longtermIsEnabled, String stockName, String orderType, String qty, boolean priceIsEnabled, String price,
    	boolean triggerPriceIsEnabled, String triggerPrice, String validity, boolean stopLossTargetChk) throws InterruptedException {
    	
        buyflow.searchStock(stockName);
        buyflow.hoverStock();
        if(sellIsEnabled) {
        buyflow.clickOnSell();
        }
        else {
        buyflow.clickOnBuy();
        }
        buyflow.navigateToRegular();
        if(longtermIsEnabled) {
        buyflow.selectLongterm();
        }
        else {
       	buyflow.selectIntraday();
        }
        
        switch(orderType) {
        case("Market"):  buyflow.orderTypeSelectionLocator(orderType); logger.info("Order type set to: MARKET"); break;
        case("Limit"):  buyflow.orderTypeSelectionLocator(orderType); logger.info("Order type set to: LIMIT"); break;
        case("SL"):  	buyflow.orderTypeSelectionLocator(orderType); logger.info("Order type set to: SL"); break;
        case("SL-M"):  buyflow.orderTypeSelectionLocator(orderType); logger.info("Order type set to: SL-M"); break;
        default: throw new IllegalArgumentException("Invalid order type: " + orderType);
        }
        buyflow.inputValues(qty, "Qty.");;
        
        if(priceIsEnabled) {
        buyflow.inputValues(price, "Price");
        }
        
        if(triggerPriceIsEnabled) {
        buyflow.inputValues(triggerPrice, "Trigger price");
        }
        
        if (!stopLossTargetChk) {
            buyflow.clickAdvancedOptions();
            switch (validity.toLowerCase()) {
                case "day":       buyflow.selectDay();       break;
                case "immediate": buyflow.selectImmediate(); break;
                default: throw new IllegalArgumentException("Invalid validity: " + validity);
            }
        } else {
            buyflow.checkStoploss();
            buyflow.checkTarget();
        }
        buyflow.buyStock();

    }


    // Teardown 1 - Dismiss toasts >> cancel >> verify order
// Changes are to done when the market is closed or opened
    
    public void dismissAndVerify(String qty, String price, String trigger,
                                 String orderType, String productType,
                                 String validity, String stockName) throws InterruptedException {
        buyflow.clickOnCross();
        Thread.sleep(500);
        buyflow.clickOnCross();
        buyflow.clickCancel();
        Thread.sleep(500);
        orderPage.clickOrderStatus();
        orderPage.verifyOrder(qty, price, trigger, orderType, productType, validity, stockName);
        orderPage.clickCloseButton();
        logger.info("Order verified: " + orderType + " | " + validity);
    }

    // Teardown 2 - Dismiss toasts >> verify stoploss/target order

    public void dismissAndVerifyWithStoplossTarget(String qty, String price, String trigger,
                                                   String orderType, String productType,
                                                   String validity, String stockName,
                                                   String stoploss, String target) throws InterruptedException {
        buyflow.clickOnCross();
        Thread.sleep(500);
//        buyflow.clickOnCross();
//        Thread.sleep(500);
        orderPage.clickOrderStatus();
        orderPage.verifyStoplossTargetOrder(qty, price, trigger, orderType, productType,
                validity, stockName, stoploss, target);
        orderPage.clickCloseButton();
        logger.info("Stoploss/Target order verified: " + orderType + " | stoploss: " + stoploss + " | target: " + target);
    }


}
