package action;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ui.CheckoutOverviewPage;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class CheckoutOverviewAction {
    WebDriver driver;
    WebDriverWait wait;

    public CheckoutOverviewAction(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    }

    public String getTitle() {
        return driver.findElement(CheckoutOverviewPage.PAGE_TITLE).getText();
    }

    public List<Double> getItemPrices() {
        List<WebElement> priceElements = driver.findElements(CheckoutOverviewPage.PRICE_ITEM_LIST);
        List<Double> prices = new ArrayList<>();
        for (WebElement price : priceElements) {
            prices.add(Double.parseDouble(price.getText().replace("$", "")));
        }
        return prices;
    }

    public double getDisplayedItemTotal() {
        String text = driver.findElement(CheckoutOverviewPage.ITEM_TOTAL).getText();
        return Double.parseDouble(text.replace("Item total: $", ""));
    }

    public double getDisplayedTax() {
        String text = driver.findElement(CheckoutOverviewPage.TAX).getText();
        return Double.parseDouble(text.replace("Tax: $", ""));
    }

    public double getDisplayedTotal() {
        String text = driver.findElement(CheckoutOverviewPage.TOTAL).getText();
        return Double.parseDouble(text.replace("Total: $", ""));
    }

    public void clickFinish() {
        scrollToElement(CheckoutOverviewPage.FINISH_BUTTON);
        wait.until(ExpectedConditions.elementToBeClickable(CheckoutOverviewPage.FINISH_BUTTON)).click();
    }

    public void clickCancel() {
        scrollToElement(CheckoutOverviewPage.CANCEL_BUTTON);
        wait.until(ExpectedConditions.elementToBeClickable(CheckoutOverviewPage.CANCEL_BUTTON)).click();
    }

    private void scrollToElement(By locator) {
        WebElement element = driver.findElement(locator);
        ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
    }

    public List<String> getProductNames() {
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(CheckoutOverviewPage.NAME_ITEM_LIST));

        List<WebElement> elements = driver.findElements(CheckoutOverviewPage.NAME_ITEM_LIST);
        List<String> names = new ArrayList<>();
        for (WebElement el : elements) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", el);
            names.add(el.getText());
        }
        return names;
    }
}