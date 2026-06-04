package pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage{

	public LoginPage(WebDriver driver) {
		super(driver);
	}
	
	@FindBy(id = "userid")
	WebElement userId;
	
	@FindBy(id = "password")
	WebElement password;
	
	@FindBy(css = ".button-orange.wide")
	WebElement btnLogin;
	

	public void setUserId(String uid) {
		userId.sendKeys(uid);
	}
	
	public void setPass(String pass) {
		password.sendKeys(pass);
	}

	public void clickLogin() {
		btnLogin.click();
	}
	


}
