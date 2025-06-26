package feature;

import action.CartHeaderAction;
import action.InventoryAction;
import action.LoginAction;
import action.YourCartAction;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import untils.BaseTest;

import java.util.List;

public class YourCartTest extends BaseTest {
    LoginAction login;
    YourCartAction cart;
    InventoryAction inventory;
    CartHeaderAction cartHeader;

    @BeforeMethod
    public void setupPage() {
        login = new LoginAction(driver);
        login.loginWithStandardUser();

        cart = new YourCartAction(driver);
        inventory = new InventoryAction(driver);
        cartHeader = new CartHeaderAction(driver);
    }

    @Test
    public void checkPageTitleIsCorrect() {
        cartHeader.openCart();
        Assert.assertEquals(cart.getTitle(), "Your Cart", "Page title is incorrect");
    }

    @Test
    public void addFirstProductToCart() {
        inventory.addFirstProduct();
        cartHeader.openCart();
        Assert.assertEquals(cart.getCartItemCount(), 1, "Should have 1 product in cart");
    }

    @Test
    public void removeFirstProductShouldEmptyCart() {
        inventory.addFirstProduct();
        cartHeader.openCart();
        cart.removeFirstProduct();
        Assert.assertTrue(cart.isCartEmpty(), "Cart should be empty after removing product");
    }

    @Test
    public void clickCheckoutShouldGoToCheckoutPage() {
        cartHeader.openCart();
        cart.clickCheckout();
        Assert.assertTrue(driver.getCurrentUrl().contains("checkout-step-one"), "Did not go to checkout page");
    }

    @Test
    public void clickContinueShouldReturnToInventoryAndKeepCartState() {
        inventory.addFirstProduct();
        cartHeader.openCart();

        int expectedCount = cartHeader.getCartBadgeCount();
        List<String> productNames = cart.getProductNames();
        Assert.assertFalse(productNames.isEmpty(), "No product found in cart before navigating");

        cart.clickContinue();

        Assert.assertTrue(driver.getCurrentUrl().contains("inventory"), "Did not return to inventory page");
        Assert.assertEquals(cartHeader.getCartBadgeCount(), expectedCount, "Cart badge mismatch after return");
    }

    @Test
    public void addAllProductsShouldDisplayAllInCartCorrectly() {
        inventory.addAllProducts();
        cartHeader.openCart();

        int expectedCount = cart.getCartItemCount();
        int badgeCount = cartHeader.getCartBadgeCount();
        Assert.assertEquals(badgeCount, expectedCount, "Cart badge doesn't match cart item count");

        List<String> names = cart.getProductNames();
        List<String> descs = cart.getProductDescriptions();
        List<Double> prices = cart.getProductPrices();

        Assert.assertFalse(names.isEmpty(), "No products found in cart");
        Assert.assertEquals(names.size(), descs.size(), "Mismatch: names vs descriptions");
        Assert.assertEquals(names.size(), prices.size(), "Mismatch: names vs prices");

        List<String> buttons = cart.getAddRemoveButtonTexts();
        for (String text : buttons) {
            Assert.assertEquals(text, "Remove", "Expected button text 'Remove' but got: " + text);
        }
    }

    @Test
    public void removeAllProductsShouldEmptyCart() {
        inventory.addAllProducts();
        cartHeader.openCart();
        cart.removeAllProducts();
        Assert.assertTrue(cart.isCartEmpty(), "Cart should be empty after removing all products");
        Assert.assertEquals(cartHeader.getCartBadgeCount(), 0, "Cart badge should be 0 after removing all products");
    }
}