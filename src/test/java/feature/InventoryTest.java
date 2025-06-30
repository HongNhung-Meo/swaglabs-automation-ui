package feature;

import action.CartHeaderAction;
import action.InventoryAction;
import action.LoginAction;
import action.MenuAction;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import untils.BaseTest;

import java.util.List;

public class InventoryTest extends BaseTest {
    LoginAction login;
    InventoryAction inventory;
    MenuAction menu;
    CartHeaderAction cartHeader;

    @BeforeMethod
    public void setupPage() {
        login = new LoginAction(driver);
        login.loginWithStandardUser();

        inventory = new InventoryAction(driver);
        menu = new MenuAction(driver);
        cartHeader = new CartHeaderAction(driver);

        menu.openMenu();
        menu.clickResetAppState();
        driver.navigate().refresh();
    }

    @Test (description = "Kiểm tra tiêu đề")
    public void checkPageLoadedSuccessfully() {
        Assert.assertEquals(inventory.getTitle(), "Products", "Page title is incorrect");
    }

    @Test
    public void checkAllDescriptionsAreDisplayed() {
        List<String> descriptions = inventory.getAllProductDescriptions();
        for (String desc : descriptions) {
            Assert.assertFalse(desc.isEmpty(), "Product description is empty!");
        }
    }

    @Test
    public void openMenuAndClickAbout() {
        menu.openMenu();
        menu.clickAbout();
        Assert.assertEquals(driver.getCurrentUrl(), "https://saucelabs.com/", "Did not navigate to the expected About page");
    }

    @Test
    public void openMenuAndClickLogout() {
        menu.openMenu();
        menu.clickLogout();
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/", "User was not redirected to the login page after logout");
    }

    @Test
    public void addAllProductsUpdateCheckCartAndButtons() {
        inventory.addAllProducts();
        int expectedCount = inventory.getAllInventoryItemCount();
        int actualCount = cartHeader.getCartBadgeCount();
        Assert.assertEquals(actualCount, expectedCount, "Cart badge count does not match the total number of added items");

        List<String> buttonTexts = inventory.getAllAddRemoveButtonTexts();
        for (String text : buttonTexts) {
            Assert.assertEquals(text, "Remove", "Button is not 'Remove'");
        }
    }

    @Test
    public void removeAllProductsUpdateCheckCartAndButtons() {
        inventory.addAllProducts();
        inventory.removeAllProducts();
        Assert.assertEquals(cartHeader.getCartBadgeCount(), 0, "Cart is not empty after removing all products");

        List<String> buttonTexts = inventory.getAllAddRemoveButtonTexts();
        for (String text : buttonTexts) {
            Assert.assertEquals(text, "Add to cart", "Button is not 'Add to cart'");
        }
    }

    @Test
    public void selectOptionSortByNameAZ() {
        inventory.selectSortOption("az");
        List<String> names = inventory.getAllProductNames();
        List<String> sorted = names.stream().sorted().toList();
        Assert.assertEquals(names, sorted, "Product names are not sorted in ascending (A-Z) order");
    }

    @Test
    public void selectOptionSortByNameZA() {
        inventory.selectSortOption("za");
        List<String> names = inventory.getAllProductNames();
        List<String> sorted = names.stream().sorted((a, b) -> b.compareToIgnoreCase(a)).toList();
        Assert.assertEquals(names, sorted, "Product names are not sorted in descending (Z-A) order");
    }

    @Test
    public void selectOptionSortByPriceLowToHigh() {
        inventory.selectSortOption("lohi");
        List<Double> prices = inventory.getAllProductPrices();
        List<Double> sorted = prices.stream().sorted().toList();
        Assert.assertEquals(prices, sorted, "Product prices are not sorted in ascending (low to high) order");
    }

    @Test
    public void selectOptionSortByPriceHighToLow() {
        inventory.selectSortOption("hilo");
        List<Double> prices = inventory.getAllProductPrices();
        List<Double> sorted = prices.stream().sorted((a, b) -> Double.compare(b, a)).toList();
        Assert.assertEquals(prices, sorted, "Product prices are not sorted in descending (high to low) order");
    }

    @Test
    public void addFirstProduct_shouldIncreaseBadgeAndChangeButtonText() {
        inventory.addFirstProduct();

        int actualCount = cartHeader.getCartBadgeCount();
        Assert.assertEquals(actualCount, 1, "Cart badge count should be 1 after adding one product");

        List<String> buttonTexts = inventory.getAllAddRemoveButtonTexts();
        Assert.assertEquals(buttonTexts.get(0), "Remove", "First product button should change to 'Remove'");
    }

    @Test
    public void removeFirstProduct_shouldDecreaseBadgeAndRevertButtonText() {
        inventory.addFirstProduct();
        Assert.assertEquals(cartHeader.getCartBadgeCount(), 1, "Precondition failed: product not added");

        inventory.removeFirstProduct();
        Assert.assertEquals(cartHeader.getCartBadgeCount(), 0, "Cart badge count should be 0 after removing the product");

        List<String> buttonTexts = inventory.getAllAddRemoveButtonTexts();
        Assert.assertEquals(buttonTexts.get(0), "Add to cart", "First product button should revert to 'Add to cart'");
    }
}
