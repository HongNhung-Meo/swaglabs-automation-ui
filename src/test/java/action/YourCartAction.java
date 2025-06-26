package action;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ui.YourCartPage;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class YourCartAction {
    WebDriver driver;
    WebDriverWait wait;

    public YourCartAction(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public String getTitle() {
        return driver.findElement(YourCartPage.PAGE_TITLE).getText();
    }

    public void clickContinue() {
        scrollToElement(YourCartPage.CONTINUE_BUTTON);
        wait.until(ExpectedConditions.elementToBeClickable(YourCartPage.CONTINUE_BUTTON)).click();
    }

    public void clickCheckout() {
        scrollToElement(YourCartPage.CHECKOUT_BUTTON);
        wait.until(ExpectedConditions.elementToBeClickable(YourCartPage.CHECKOUT_BUTTON)).click();
    }

    private void scrollToElement(By locator) {
        WebElement element = driver.findElement(locator);
        ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
    }

    public boolean isCartEmpty() {
        return driver.findElements(YourCartPage.CART_ITEM).isEmpty();
    }

    public int getCartItemCount() {
        return driver.findElements(YourCartPage.CART_ITEM).size();
    }

    public void removeFirstProduct() {
        List<WebElement> buttons = driver.findElements(YourCartPage.REMOVE_BUTTON);
        if (!buttons.isEmpty()) {
            buttons.get(0).click();
        }
    }

    public void removeAllProducts() {
        List<WebElement> buttons = driver.findElements(YourCartPage.REMOVE_BUTTON);
        for (WebElement btn : buttons) {
            if (btn.getText().equalsIgnoreCase("Remove")) {
                btn.click();
            }
        }
    }

    public List<String> getProductNames() {
        List<WebElement> elements = driver.findElements(YourCartPage.CART_ITEM_NAME);
        List<String> names = new ArrayList<>();
        for (WebElement el : elements) {
            names.add(el.getText());
        }
        return names;
    }

    public List<String> getProductDescriptions() {
        List<WebElement> elements = driver.findElements(YourCartPage.CART_ITEM_DESC);
        List<String> descriptions = new ArrayList<>();
        for (WebElement el : elements) {
            descriptions.add(el.getText());
        }
        return descriptions;
    }

    public List<Double> getProductPrices() {
        List<WebElement> elements = driver.findElements(YourCartPage.CART_ITEM_PRICE);
        List<Double> prices = new ArrayList<>();
        for (WebElement el : elements) {
            String priceText = el.getText().replace("$", "");
            try {
                prices.add(Double.parseDouble(priceText));
            } catch (NumberFormatException e) {
                prices.add(0.0);
            }
        }
        return prices;
    }

    public List<String> getAddRemoveButtonTexts() {
        List<WebElement> buttons = driver.findElements(YourCartPage.ADD_BUTTON);
        List<String> texts = new ArrayList<>();
        for (WebElement btn : buttons) {
            texts.add(btn.getText());
        }
        return texts;
    }

    public List<Double> getItemPrices() {
        List<WebElement> priceElements = driver.findElements(YourCartPage.CART_ITEM_PRICE);
        List<Double> prices = new ArrayList<>();
        for (WebElement element : priceElements) {
            prices.add(Double.parseDouble(element.getText().replace("$", "")));
        }
        return prices;
    }
}