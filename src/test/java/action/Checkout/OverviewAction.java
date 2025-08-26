package action.Checkout;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ui.Checkout.OverviewPageUI;
import untils.PriceUntils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class OverviewAction {
    WebDriver driver;
    WebDriverWait wait;

    public OverviewAction(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public String getTitle() {
        return driver.findElement(OverviewPageUI.TITLE).getText();
    }

    public boolean isProductDisplayed(String productName) {
        return getItemNames().contains(productName);
    }

    public boolean isProductDescDisplayed(String productName) {
        List<String> names = getItemNames();
        List<String> descs = getItemDescs();

        for (int i = 0; i < names.size(); i++) {
            if (names.get(i).equals(productName)) {
                return descs.size() > i && !descs.get(i).isEmpty();
            }
        }
        return false;
    }

    // ===== Lấy danh sách sản phẩm =====
    public List<String> getItemNames() {
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(OverviewPageUI.ITEM_NAME));
        List<WebElement> elements = driver.findElements(OverviewPageUI.ITEM_NAME);
        List<String> names = new ArrayList<>();
        for (WebElement el : elements) {
            scrollToElement(el);
            names.add(el.getText());
        }
        return names;
    }

    public List<String> getItemDescs() {
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(OverviewPageUI.ITEM_DESC));
        List<WebElement> elements = driver.findElements(OverviewPageUI.ITEM_DESC);
        List<String> descs = new ArrayList<>();
        for (WebElement el : elements) {
            scrollToElement(el);
            descs.add(el.getText());
        }
        return descs;
    }

    public List<Double> getItemPrices() {
        List<WebElement> priceElements = driver.findElements(OverviewPageUI.ITEM_PRICE);
        List<Double> prices = new ArrayList<>();
        for (WebElement price : priceElements) {
            scrollToElement(price);
            prices.add(Double.parseDouble(price.getText().replace("$", "")));
        }
        return prices;
    }

    public String getProductPrice(String productName) {
        // giả sử itemNames và itemPrices cùng index
        List<String> names = getItemNames();
        List<Double> prices = getItemPrices();
        for (int i = 0; i < names.size(); i++) {
            if (names.get(i).equals(productName)) {
                return "$" + prices.get(i);
            }
        }
        return null;
    }

    public boolean isPaymentInfo() {
        return driver.findElement(OverviewPageUI.PAYMENT_INFO).isDisplayed();
    }

    public boolean isPaymentValue() {
        return driver.findElement(OverviewPageUI.PAYMENT_VALUE).isDisplayed();
    }

    public boolean isShippingInfo() {
        return driver.findElement(OverviewPageUI.SHIPPING_INFO).isDisplayed();
    }

    public boolean isShippingValue() {
        return driver.findElement(OverviewPageUI.SHIPPING_VALUE).isDisplayed();
    }

    public boolean isFinishDisplayed() {
        return wait.until(ExpectedConditions.elementToBeClickable(OverviewPageUI.FINISH_BUTTON)).isDisplayed();
    }

    public boolean isCancelDisplayed() {
        return wait.until(ExpectedConditions.elementToBeClickable(OverviewPageUI.CANCEL_BUTTON)).isDisplayed();
    }

    // ===== Giá =====
    public double getItemTotal() {
        String text = driver.findElement(OverviewPageUI.ITEM_TOTAL).getText();
        return Double.parseDouble(text.replace("Item total: $", ""));
    }

    public double getTax() {
        String text = driver.findElement(OverviewPageUI.TAX).getText();
        return Double.parseDouble(text.replace("Tax: $", ""));
    }

    public double getTotal() {
        String text = driver.findElement(OverviewPageUI.TOTAL).getText();
        return Double.parseDouble(text.replace("Total: $", ""));
    }

    public boolean isTotalCorrect() {
        return PriceUntils.isTotalCorrect(getItemTotal(), getTax(), getTotal());
    }

    public void clickFinish() {
        WebElement finishBtn = driver.findElement(OverviewPageUI.FINISH_BUTTON);
        scrollToElement(finishBtn);
        wait.until(ExpectedConditions.elementToBeClickable(finishBtn)).click();
    }

    public void clickCancel() {
        WebElement cancelBtn = driver.findElement(OverviewPageUI.CANCEL_BUTTON);
        scrollToElement(cancelBtn);
        wait.until(ExpectedConditions.elementToBeClickable(cancelBtn)).click();
    }

    private void scrollToElement(WebElement element) {
        ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
    }
}