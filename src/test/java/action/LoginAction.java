package action;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ui.LoginPage;

public class LoginAction {
    WebDriver driver;

    public LoginAction(WebDriver driver) {
        this.driver = driver;
    }

    public void enterUsername(String username) {
        driver.findElement(LoginPage.USERNAME_FIELD).sendKeys(username);
    }

    public void enterPassword(String password) {
        driver.findElement(LoginPage.PASSWORD_FIELD).sendKeys(password);
    }

    public void clickLogin() {
        driver.findElement(LoginPage.LOGIN_BUTTON).click();
    }

    public WebElement getErrorMessage() {
        return driver.findElement(LoginPage.ERROR_MESSAGE);
    }

    public boolean isLogoDisplayed() {
        return driver.findElement(LoginPage.APP_LOGO).isDisplayed();
    }

    public int getInventoryItemCount() {
        return driver.findElements(LoginPage.INVENTORY_ITEM).size();
    }

    public void loginWithStandardUser() {
        enterUsername("standard_user");
        enterPassword("secret_sauce");
        clickLogin();
    }
}