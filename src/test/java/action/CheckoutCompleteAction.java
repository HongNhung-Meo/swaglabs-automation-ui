package action;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ui.CheckoutCompletePage;

import java.time.Duration;

public class CheckoutCompleteAction {
    WebDriver driver;
    WebDriverWait wait;

    public CheckoutCompleteAction(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public String getTitle() {
        return driver.findElement(CheckoutCompletePage.PAGE_TITLE).getText();
    }

    public String getCompleteMessage() {
        return driver.findElement(CheckoutCompletePage.COMPLETE_TEXT).getText();
    }

    public void clickBackHome() {
        wait.until(ExpectedConditions.elementToBeClickable(CheckoutCompletePage.BACK_HOME_BUTTON)).click();
    }
}