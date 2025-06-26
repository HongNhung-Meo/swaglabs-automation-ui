package feature;

import action.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import untils.BaseTest;
import untils.PriceUntils;

import java.util.List;

public class CheckoutOverviewTest extends BaseTest {
    LoginAction login;
    InventoryAction inventory;
    CartHeaderAction cartHeader;
    YourCartAction cart;
    CheckoutInformationAction checkoutInformation;
    CheckoutOverviewAction checkoutOverview;

    @BeforeMethod
    public void setupPage() {
        login = new LoginAction(driver);
        login.loginWithStandardUser();

        inventory = new InventoryAction(driver);
        cartHeader = new CartHeaderAction(driver);
        cart = new YourCartAction(driver);
        checkoutInformation = new CheckoutInformationAction(driver);
        checkoutOverview = new CheckoutOverviewAction(driver);

        inventory.addAllProducts();
        cartHeader.openCart();
        cart.clickCheckout();

        checkoutInformation.fillFormAndContinue("Hong", "Nhung", "12345");
        checkoutInformation.clickContinue();
    }

    @Test
    public void checkPageTitleIsCorrect() {
        Assert.assertEquals(checkoutOverview.getTitle(), "Checkout: Overview", "Page title is incorrect");
    }

    @Test
    public void testItemTotalCalculation() {
        List<Double> prices = checkoutOverview.getItemPrices();
        double expectedItemTotal = PriceUntils.calculateItemTotal(prices);
        double displayedItemTotal = checkoutOverview.getDisplayedItemTotal();

        Assert.assertEquals(displayedItemTotal, expectedItemTotal, 0.01, "Item total is incorrect.");
    }

    @Test
    public void testFinalTotalIsCorrect() {
        double itemTotal = checkoutOverview.getDisplayedItemTotal();
        double tax = checkoutOverview.getDisplayedTax();
        double total = checkoutOverview.getDisplayedTotal();

        boolean isCorrect = PriceUntils.isTotalCorrect(itemTotal, tax, total);
        Assert.assertTrue(isCorrect, "Total price (including tax) is incorrect.");
    }

    @Test
    public void testClickFinishRedirectsToCompletePage() {
        checkoutOverview.clickFinish();
        Assert.assertTrue(driver.getCurrentUrl().contains("checkout-complete.html"), "Did not navigate to the checkout complete page.");
    }

    @Test
    public void testCancelReturnsToCartWithItems() {
        checkoutOverview.clickCancel();
        Assert.assertTrue(driver.getCurrentUrl().contains("cart.html"), "Did not return to the cart page after clicking Cancel.");
        List<Double> prices = cart.getItemPrices();
        Assert.assertFalse(prices.isEmpty(), "Cart is empty after clicking Cancel, but items were expected.");
    }
}