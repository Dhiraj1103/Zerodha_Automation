package testCases;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pageObject.BuyFlow;
import pageObject.BuyFlowHelper;
import testBase.BaseClass;

public class BuyingLongtermCombinations extends BaseClass {

    private BuyFlowHelper helper;
    
    @BeforeMethod
    public void initHelper() {
        helper = new BuyFlowHelper(getDriver());
    }
    
    @Test(description = "Regular | Longterm | Market | Day", priority = 1)
    public void regular_Longterm_Market_Day() throws InterruptedException {
        logger.info("***** Combination 1 started *****");
        helper.buyOrder(false, true, "Coal", "Market", "1", false, "0", false, "0", "day", false);
        helper.dismissAndVerify("1", "0", "0", "MARKET", "CNC", "DAY", "COAL");
        logger.info("***** Combination 1 ended *****");
    }

    @Test(description = "Regular | Longterm | Market | Immediate", priority = 2)
    public void regular_Longterm_Market_Immediate() throws InterruptedException {
        helper.buyOrder(false, true,"Coal", "Market", "1", false, "0", false, "0", "immediate", false);
        helper.dismissAndVerify("1", "0", "0", "MARKET", "CNC", "IOC", "COAL");
        logger.info("***** Combination 2 ended *****");
    }

    @Test(description = "Regular | Longterm | Limit | Day", priority = 3)
    public void regular_Longterm_Limit_Day() throws InterruptedException {
        logger.info("***** Combination 3 started *****");
        helper.buyOrder(false, true,"Coal", "Limit", "1", true, "460", false, "0", "day", false);
        helper.dismissAndVerify("1", "460", "0", "LIMIT", "CNC", "DAY", "COAL");
        logger.info("***** Combination 3 ended *****");
    }

    @Test(description = "Regular | Longterm | Limit | Immediate", priority = 4, groups = "System testing")
    public void regular_Longterm_Limit_Immediate() throws InterruptedException {
        logger.info("***** Combination 4 started *****");
        helper.buyOrder(false, true,"Coal", "Limit", "1", true, "460", false, "0", "immediate", false);
        helper.dismissAndVerify("1", "460", "0", "LIMIT", "CNC", "IOC", "COAL");
        logger.info("***** Combination 4 ended *****");
   }

    @Test(description = "Regular | Longterm | SL | Day", priority = 5, groups = "System testing")
    public void regular_Longterm_SL_Day() throws InterruptedException {
        logger.info("***** Combination 5 started *****");
        helper.buyOrder(false, true,"Coal", "SL", "1", true, "472", true, "470", "day", false);
        helper.dismissAndVerify("1", "472", "470", "SL", "CNC", "DAY", "COAL");
        logger.info("***** Combination 5 ended *****");
        
    }
    

    @Test(description = "Regular | Longterm | SL | Immediate", priority = 6)
    public void regular_Longterm_SL_Immediate() throws InterruptedException {
        logger.info("***** Combination 6 started *****");
        helper.buyOrder(false, true,"Coal", "SL", "1", true, "472", true, "470", "immediate", false);
        helper.dismissAndVerify("1", "460", "459", "SL", "CNC", "IOC", "COAL");
        logger.info("***** Combination 6 ended *****");
   }

    @Test(description = "Regular | Longterm | SL-M | Day", priority = 7)
    public void regular_Longterm_SLM_Day() throws InterruptedException {
        logger.info("***** Combination 7 started *****");
        helper.buyOrder(false, true,"Coal", "SL-M", "1", false, "0", true, "459", "day", false);
        helper.dismissAndVerify("1", "0", "459", "SL-M", "CNC", "DAY", "COAL");
        logger.info("***** Combination 7 ended *****");
    }

    @Test(description = "Regular | Longterm | SL-M | Immediate", priority = 8)
    public void regular_Longterm_SLM_Immediate() throws InterruptedException {
        logger.info("***** Combination 8 started *****");
        helper.buyOrder(false, true,"Coal", "SL-M", "1", false, "0", true, "459", "immediate", false);
        helper.dismissAndVerify("1", "0", "459", "SL-M", "CNC", "IOC", "COAL");
        logger.info("***** Combination 8 ended *****");
    }

    @Test(description = "Regular | Longterm | Market | Stoploss + Target", priority = 9)
    public void regular_Longterm_Market_Stoploss_Target() throws InterruptedException {
        logger.info("***** Combination 9 started *****");
        helper.buyOrder(false, true,"Coal", "Market", "1", false, "0", false, "0", "day", true);
        helper.dismissAndVerifyWithStoplossTarget("1", "0", "0", "MARKET", "CNC", "DAY", "COAL", "-5", "5");
        logger.info("***** Combination 9 ended *****");
    }

    @Test(description = "Regular | Longterm | Limit | Stoploss + Target", priority = 10)
    public void regular_Longterm_Limit_Stoploss_Target() throws InterruptedException {
        logger.info("***** Combination 10 started *****");
        helper.buyOrder(false, true,"Coal", "SL", "1", true, "460", true, "459", "day", true);
        helper.dismissAndVerifyWithStoplossTarget("1", "470", "0", "LIMIT", "CNC", "DAY", "COAL", "-5", "5");
        logger.info("***** Combination 10 ended *****");
    }

    @Test(description = "Regular | Longterm | SL | Stoploss + Target", priority = 11)
    public void regular_Longterm_SL_Stoploss_Target() throws InterruptedException {
        logger.info("***** Combination 11 started *****");
        helper.buyOrder(false, true,"Coal", "LIMIT", "1", true, "460", false, "0", "day", true);
        helper.dismissAndVerifyWithStoplossTarget("1", "470", "467", "SL", "CNC", "DAY", "COAL", "-5", "5");
        logger.info("***** Combination 11 ended *****");
    }

    @Test(description = "Regular | Longterm | SL-M | Stoploss + Target", priority = 12)
    public void regular_Longterm_SLM_Stoploss_Target() throws InterruptedException {
        logger.info("***** Combination 12 started *****");
        helper.buyOrder(false, true,"Coal", "SL-M", "1", false, "0", true, "459", "day", true);
        helper.dismissAndVerifyWithStoplossTarget("1", "0", "467", "SL-M", "CNC", "DAY", "COAL", "-5", "5");
        logger.info("***** Combination 12 ended *****");
    }
}
