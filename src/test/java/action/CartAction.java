package action;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ui.CartPageUI;

import java.time.Duration;
import java.util.List;

public class CartAction {
    WebDriver driver;
    WebDriverWait wait;

    public CartAction(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public String getPageTitle() {
        return driver.findElement(CartPageUI.TITLE).getText();
    }

    public boolean isCartIconDisplayed() {
        return driver.findElement(CartPageUI.CART_ICON).isDisplayed();
    }

    public String getCartBadge() {
        try {
            return driver.findElement(CartPageUI.CART_BADGE).getText();
        } catch (Exception e) {
            return "";
        }
    }

    public boolean isCartEmpty() {
        return driver.findElements(CartPageUI.CART_ITEMS).isEmpty();
    }

    public void openCart() {
        wait.until(ExpectedConditions.elementToBeClickable(CartPageUI.CART_ICON)).click();
    }

    public int getCartItemCount() {
        return driver.findElements(CartPageUI.CART_ITEMS).size();
    }

    public String getProductNameByName(String productName) {
        By locator = By.xpath(String.format(CartPageUI.ITEM_NAME, productName));
        return driver.findElement(locator).getText();
    }

    public boolean isProductDescriptionDisplayedByName(String productName) {
        By locator = By.xpath(String.format(CartPageUI.ITEM_DESC, productName));
        return driver.findElement(locator).isDisplayed();
    }

    public boolean isProductPriceDisplayedByName(String productName) {
        By locator = By.xpath(String.format(CartPageUI.ITEM_PRICE, productName));
        return driver.findElement(locator).isDisplayed();
    }

    public boolean isRemoveButtonDisplayedByName(String productName) {
        By locator = By.xpath(String.format(CartPageUI.REMOVE_BUTTON_NAME, productName));
        List<WebElement> elements = driver.findElements(locator);
        return !elements.isEmpty() && elements.get(0).isDisplayed();
    }

    // ================= Business Remove =================

    public void removeProductByName(String productName) {
        By removeBtn = By.xpath(String.format(CartPageUI.REMOVE_BUTTON_NAME, productName));
        scrollIntoView(removeBtn);
        wait.until(ExpectedConditions.elementToBeClickable(removeBtn)).click();
    }

    public void removeProductsByNames(List<String> productNames) {
        for (String name : productNames) {
            removeProductByName(name);
        }
    }

    public void removeAllProducts() {
        while (!isCartEmpty()) {
            List<WebElement> removeButtons = driver.findElements(By.xpath(CartPageUI.REMOVE_BUTTONS));
            if (removeButtons.isEmpty()) break;

            WebElement btn = removeButtons.get(0);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", btn);

            wait.until(ExpectedConditions.elementToBeClickable(btn)).click();
        }
    }

    public void clickContinue() {
        scrollIntoView(CartPageUI.CONTINUE_BUTTON);
        wait.until(ExpectedConditions.elementToBeClickable(CartPageUI.CONTINUE_BUTTON)).click();
    }

    public void clickCheckout() {
        scrollIntoView(CartPageUI.CHECKOUT_BUTTON);
        wait.until(ExpectedConditions.elementToBeClickable(CartPageUI.CHECKOUT_BUTTON)).click();
    }

    private void scrollIntoView(By locator) {
        WebElement element = driver.findElement(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }
}