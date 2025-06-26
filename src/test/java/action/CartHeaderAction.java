package action;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ui.CartHeaderPage;

import java.time.Duration;

public class CartHeaderAction {
    WebDriver driver;
    WebDriverWait wait;

    public CartHeaderAction(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void openCart() {
        wait.until(ExpectedConditions.elementToBeClickable(CartHeaderPage.CART_ICON)).click();
    }

    public int getCartBadgeCount() {
        try {
            String text = driver.findElement(CartHeaderPage.CART_BADGE).getText();
            return Integer.parseInt(text);
        } catch (NoSuchElementException e) {
            return 0;
        }
    }

    public boolean isCartIconVisible() {
        return driver.findElement(CartHeaderPage.CART_ICON).isDisplayed();
    }

    public boolean isBadgeVisible() {
        try {
            return driver.findElement(CartHeaderPage.CART_BADGE).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}