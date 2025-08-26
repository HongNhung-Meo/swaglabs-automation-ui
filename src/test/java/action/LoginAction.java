package action;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ui.LoginPageUI;

public class LoginAction {
    WebDriver driver;

    public LoginAction(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isUsernameDisplayed() {
        return driver.findElement(LoginPageUI.USERNAME_FIELD).isDisplayed();
    }

    public boolean isPasswordDisplayed() {
        return driver.findElement(LoginPageUI.USERNAME_FIELD).isDisplayed();
    }

    public boolean isLoginButtonDisplayed() {
        return driver.findElement(LoginPageUI.LOGIN_BUTTON).isDisplayed();
    }

    public void enterUsername(String username) {
        driver.findElement(LoginPageUI.USERNAME_FIELD).sendKeys(username);
    }

    public void enterPassword(String password) {
        driver.findElement(LoginPageUI.PASSWORD_FIELD).sendKeys(password);
    }

    public void clickLogin() {
        driver.findElement(LoginPageUI.LOGIN_BUTTON).click();
    }

    public WebElement getErrorMessage() {
        return driver.findElement(LoginPageUI.ERROR_MESSAGE);
    }

    public boolean isLogoDisplayed() {
        return driver.findElement(LoginPageUI.APP_LOGO).isDisplayed();
    }

    public int getInventoryItemCount() {
        return driver.findElements(LoginPageUI.INVENTORY_ITEM).size();
    }

    public void loginWithStandardUser() {
        enterUsername("standard_user");
        enterPassword("secret_sauce");
        clickLogin();
    }
}