package action.Checkout;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ui.Checkout.CompletePageUI;

import java.time.Duration;

public class CompleteAction {
    WebDriver driver;
    WebDriverWait wait;

    public CompleteAction(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public String getTitle() {
        return driver.findElement(CompletePageUI.PAGE_TITLE).getText();
    }

    public String getCompleteHeader() {
        return driver.findElement(CompletePageUI.COMPLETE_HEADER).getText();
    }

    public boolean isCompleteText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(CompletePageUI.COMPLETE_TEXT)).isDisplayed();
    }

    public boolean isBackHomeDisplayed() {
        return wait.until(ExpectedConditions.elementToBeClickable(CompletePageUI.BACK_HOME_BUTTON)).isDisplayed();
    }

    public void clickBackHome() {
        wait.until(ExpectedConditions.elementToBeClickable(CompletePageUI.BACK_HOME_BUTTON)).click();
    }
}