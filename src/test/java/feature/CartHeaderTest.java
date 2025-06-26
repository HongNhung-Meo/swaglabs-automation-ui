package feature;

import action.CartHeaderAction;
import action.InventoryAction;
import action.LoginAction;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import untils.BaseTest;

public class CartHeaderTest extends BaseTest {
    LoginAction login;
    InventoryAction inventory;
    CartHeaderAction cartHeader;

    @BeforeMethod
    public void setupPage() {
        login = new LoginAction(driver);
        login.loginWithStandardUser();

        inventory = new InventoryAction(driver);
        cartHeader = new CartHeaderAction(driver);
    }

    @Test
    public void cartIconShouldBeVisible() {
        Assert.assertTrue(cartHeader.isCartIconVisible(), "Cart icon is not visible");
    }

    @Test
    public void badgeShouldNotBeVisibleWhenEmpty() {
        Assert.assertFalse(cartHeader.isBadgeVisible(), "Badge should not be visible when cart is empty");
    }

    @Test
    public void badgeShouldUpdateAfterAddProduct() {
        inventory.addFirstProduct();
        int badge = cartHeader.getCartBadgeCount();
        Assert.assertEquals(badge, 1, "Cart badge count should be 1 after adding one product");
    }

    @Test
    public void clickCartIconShouldGoToCartPage() {
        cartHeader.openCart();
        Assert.assertTrue(driver.getCurrentUrl().contains("cart"), "Did not navigate to cart page after clicking cart icon");
    }
}