package pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import testBase.BaseClass;

public class HomePage extends BasePage {

	public HomePage(WebDriver driver) {
		super(driver);
	}
	
	@FindBy(xpath = "(//button[@type='button'])[2]")
	WebElement btnIUnderstand;
	
	@FindBy(xpath = "//span[contains(text(),'Orders')]")
	WebElement navOrders;
	
	public void clickUnderstand() {
		btnIUnderstand.click();
	}
	
	public void navigateToOrders() {
		navOrders.click();
	}
	

}
