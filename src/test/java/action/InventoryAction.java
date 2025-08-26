package action;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import ui.InventoryPageUI;

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

    public boolean isLogoDisplayed() {
        return driver.findElement(InventoryPageUI.APP_LOGO).isDisplayed();
    }

    public String getPageTitle() {
        return driver.findElement(InventoryPageUI.TITLE).getText();
    }

    public boolean isSortDropdownDisplayed() {
        return driver.findElement(InventoryPageUI.SORT_DROPDOWN).isDisplayed();
    }

    public List<String> getAllItemNames() {
        List<String> names = new ArrayList<>();
        for (WebElement item : getInventoryItems()) {
            names.add(item.findElement(InventoryPageUI.ITEM_NAME).getText());
        }
        return names;
    }

    public List<String> getAllItemDescriptions() {
        List<String> descs = new ArrayList<>();
        for (WebElement item : getInventoryItems()) {
            descs.add(item.findElement(InventoryPageUI.ITEM_DESC).getText());
        }
        return descs;
    }

    public List<String> getAllItemPrices() {
        List<String> prices = new ArrayList<>();
        for (WebElement item : getInventoryItems()) {
            prices.add(item.findElement(InventoryPageUI.ITEM_PRICE).getText());
        }
        return prices;
    }

    public List<Boolean> getAllItemImagesDisplayed() {
        List<Boolean> imagesDisplayed = new ArrayList<>();
        for (WebElement item : getInventoryItems()) {
            imagesDisplayed.add(item.findElement(InventoryPageUI.ITEM_IMAGE).isDisplayed());
        }
        return imagesDisplayed;
    }

    public List<Boolean> areAllButtonsDisplayed() {
        List<Boolean> displayedList = new ArrayList<>();
        for (WebElement item : getInventoryItems()) {
            displayedList.add(item.findElement(InventoryPageUI.ADD_REMOVE_BUTTON).isDisplayed());
        }
        return displayedList;
    }

    private List<WebElement> getInventoryItems() {
        return driver.findElements(InventoryPageUI.INVENTORY_ITEMS);
    }

    private WebElement getItemByName(String productName) {
        return getInventoryItems().stream().filter(item -> item.findElement(InventoryPageUI.ITEM_NAME).getText().equalsIgnoreCase(productName)).findFirst().orElseThrow(() -> new RuntimeException("Product not found: " + productName));
    }

    // ================= Business Add =================

    public void addProductByName(String productName) {
        WebElement item = getItemByName(productName);
        WebElement btn = item.findElement(InventoryPageUI.ADD_REMOVE_BUTTON);
        if (btn.getText().equals("Add to cart")) {
            wait.until(ExpectedConditions.elementToBeClickable(btn)).click();
        }
    }

    public void addProductsByNames(List<String> productNames) {
        for (String name : productNames) {
            addProductByName(name);
        }
    }

    public void addAllProducts() {
        clickAllButtonsWithText("Add to cart");
    }

    // ================= Business Remove =================

    public void removeProductByName(String productName) {
        WebElement item = getItemByName(productName);
        WebElement btn = item.findElement(InventoryPageUI.ADD_REMOVE_BUTTON);
        if (btn.getText().equals("Remove")) {
            wait.until(ExpectedConditions.elementToBeClickable(btn)).click();
        }
    }

    public void removeProductsByNames(List<String> productNames) {
        for (String name : productNames) {
            removeProductByName(name);
        }
    }

    public void removeAllProducts() {
        clickAllButtonsWithText("Remove");
    }

    // ================= Helpers =================

    private void clickAllButtonsWithText(String expectedText) {
        for (WebElement item : getInventoryItems()) {
            WebElement btn = item.findElement(InventoryPageUI.ADD_REMOVE_BUTTON);
            if (btn.getText().equals(expectedText)) {
                wait.until(ExpectedConditions.elementToBeClickable(btn)).click();
            }
        }
    }

    public String getButtonTextByProduct(String productName) {
        WebElement item = getItemByName(productName);
        return item.findElement(InventoryPageUI.ADD_REMOVE_BUTTON).getText();
    }

    public List<String> getAllButtonTexts() {
        List<String> btnTexts = new ArrayList<>();
        for (WebElement item : getInventoryItems()) {
            btnTexts.add(item.findElement(InventoryPageUI.ADD_REMOVE_BUTTON).getText());
        }
        return btnTexts;
    }

    public void selectSortOption(String optionText) {
        WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(InventoryPageUI.SORT_DROPDOWN));
        Select select = new Select(dropdown);
        select.selectByVisibleText(optionText);
    }

    public List<String> getAllProductNames() {
        List<String> names = new ArrayList<>();
        for (WebElement item : getInventoryItems()) {
            names.add(item.findElement(InventoryPageUI.ITEM_NAME).getText());
        }
        return names;
    }

    public List<Double> getAllProductPrices() {
        List<Double> prices = new ArrayList<>();
        for (WebElement item : getInventoryItems()) {
            String priceText = item.findElement(InventoryPageUI.ITEM_PRICE).getText().replace("$", "");
            prices.add(Double.parseDouble(priceText));
        }
        return prices;
    }

    public List<String> getAllOptionsFromSortDropdown() {
        WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(InventoryPageUI.SORT_DROPDOWN));
        Select select = new Select(dropdown);
        List<WebElement> options = select.getOptions();
        List<String> optionTexts = new ArrayList<>();
        for (WebElement option : options) {
            optionTexts.add(option.getText().trim());
        }
        return optionTexts;
    }
}