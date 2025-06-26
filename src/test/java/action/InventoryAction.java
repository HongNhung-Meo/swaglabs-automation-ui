package action;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import ui.InventoryPage;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class InventoryAction {
    WebDriver driver;
    WebDriverWait wait;

    public InventoryAction(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public String getTitle() {
        return driver.findElement(InventoryPage.PAGE_TITLE).getText();
    }

    public int getAllInventoryItemCount() {
        return driver.findElements(InventoryPage.INVENTORY_ITEM).size();
    }

    public List<String> getAllProductNames() {
        return getTextListFromElements(InventoryPage.NAME_ITEM_LIST);
    }

    public List<String> getAllProductDescriptions() {
        return getTextListFromElements(InventoryPage.DESC_ITEM_LIST);
    }

    public List<Double> getAllProductPrices() {
        List<WebElement> priceElements = driver.findElements(InventoryPage.PRICE_ITEM_LIST);
        List<Double> prices = new ArrayList<>();
        for (WebElement el : priceElements) {
            prices.add(Double.parseDouble(el.getText().replace("$", "").trim()));
        }
        return prices;
    }

    private List<String> getTextListFromElements(By locator) {
        List<WebElement> elements = driver.findElements(locator);
        List<String> texts = new ArrayList<>();
        for (WebElement el : elements) {
            texts.add(el.getText());
        }
        return texts;
    }

    public void selectSortOption(String value) {
        try {
            Select dropdown = new Select(driver.findElement(InventoryPage.PRODUCT_SORT_DROPDOWN));
            dropdown.selectByValue(value);
        } catch (Exception e) {
            throw new RuntimeException("Sort option failed: " + value, e);
        }
    }

    public void addAllProducts() {
        List<WebElement> buttons = driver.findElements(InventoryPage.ADD_BUTTONS);
        for (WebElement btn : buttons) {
            btn.click();
        }
    }

    public void removeAllProducts() {
        List<WebElement> buttons = driver.findElements(InventoryPage.REMOVE_BUTTONS);
        for (WebElement btn : buttons) {
            btn.click();
        }
    }

    public void addFirstProduct() {
        List<WebElement> buttons = driver.findElements(InventoryPage.ADD_BUTTONS);
        if (!buttons.isEmpty()) buttons.get(0).click();
    }

    public void removeFirstProduct() {
        List<WebElement> buttons = driver.findElements(InventoryPage.REMOVE_BUTTONS);
        if (!buttons.isEmpty()) buttons.get(0).click();
    }

    public List<String> getAllAddRemoveButtonTexts() {
        return getTextListFromElements(InventoryPage.ADD_REMOVE_BUTTON);
    }
}