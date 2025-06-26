package feature;

import action.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import untils.BaseTest;

import java.util.List;

public class CheckoutCompleteTest extends BaseTest {
    LoginAction login;
    InventoryAction inventory;
    CartHeaderAction cartHeader;
    YourCartAction cart;
    CheckoutInformationAction checkoutInformation;
    CheckoutOverviewAction checkoutOverview;
    CheckoutCompleteAction checkoutComplete;

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

        checkoutOverview.clickFinish();

        checkoutComplete = new CheckoutCompleteAction(driver);
    }

    @Test
    public void testCompletePageDisplayedCorrectly() {
        Assert.assertEquals(checkoutComplete.getTitle(), "Checkout: Complete!", "Page title is incorrect.");

        String completeMessage = checkoutComplete.getCompleteMessage();
        Assert.assertTrue(completeMessage.contains("Thank you for your order!"),
                "Completion message is not displayed as expected.");
    }

    @Test
    public void testBackHomeRedirectsToInventory() {
        checkoutComplete.clickBackHome();
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory.html"), "Did not redirect to inventory page after Back Home.");
    }

    @Test
    public void testAllProductNamesAreDisplayedInOverview() {
        List<String> expectedProductNames = List.of(
                "Sauce Labs Backpack",
                "Sauce Labs Bike Light",
                "Sauce Labs Bolt T-Shirt",
                "Sauce Labs Fleece Jacket",
                "Sauce Labs Onesie",
                "Test.allTheThings() T-Shirt (Red)"
        );

        List<String> actualProductNames = checkoutOverview.getProductNames();

        Assert.assertEqualsNoOrder(
                actualProductNames.toArray(),
                expectedProductNames.toArray(),
                "Not all expected product names are displayed on the overview page."
        );
    }
}