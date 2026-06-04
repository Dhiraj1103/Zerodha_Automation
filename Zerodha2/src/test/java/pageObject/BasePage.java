package pageObject;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {
	
    WebDriver driver;
    WebDriverWait wait; 
 
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(20)); 
        PageFactory.initElements(driver, this);
    }
	
	public WebElement verifyVisibilityOfElement(WebElement ele) {
	    return wait.until(ExpectedConditions.visibilityOf(ele));
	}
	
	public List<WebElement> verifyVisibilityOfElements(List<WebElement> eles) {
	    return wait.until(ExpectedConditions.visibilityOfAllElements(eles));
	}
	
	public WebElement verifyInteractable(WebElement click) {
		return wait.until(ExpectedConditions.elementToBeClickable(click));

	}
	
    public void safeClick(WebElement ele) {
        // Step 1 scroll into view so fixed headers/footers don't block it
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", ele);

        // Step 2  wait for clickable
        wait.until(ExpectedConditions.elementToBeClickable(ele));

        try {
            // Step 3  normal click
            ele.click();
        } catch (org.openqa.selenium.ElementClickInterceptedException e) {
            // Step 4  something is overlapping (toast, dropdown, animation)
            // JS click bypasses the overlay and fires the click event directly on the element
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", ele);
        }
    }
}
