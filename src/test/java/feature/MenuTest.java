package feature;

import action.CartHeaderAction;
import action.LoginAction;
import action.MenuAction;
import action.InventoryAction;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import untils.BaseTest;

import java.util.List;

public class MenuTest extends BaseTest {
    LoginAction login;
    MenuAction menu;
    InventoryAction inventory;
    CartHeaderAction cartHeader;

    @BeforeMethod
    public void setupPage() {
        login = new LoginAction(driver);
        login.loginWithStandardUser();

        menu = new MenuAction(driver);
        inventory = new InventoryAction(driver);
        cartHeader = new CartHeaderAction(driver);
    }

    @Test
    public void testClickAllItemsShouldStayOnInventoryPage() {
        menu.openMenu();
        menu.clickAllItems();
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory"), "Did not remain on inventory page after clicking All Items");
    }

    @Test
    public void testClickAboutShouldRedirectToSauceLabs() {
        menu.openMenu();
        menu.clickAbout();
        Assert.assertEquals(driver.getCurrentUrl(), "https://saucelabs.com/", "Did not navigate to Sauce Labs page");
    }

    @Test
    public void testClickLogoutShouldReturnToLoginPage() {
        menu.openMenu();
        menu.clickLogout();
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/", "Did not navigate back to login page after logout");
    }

    @Test
    public void testResetAppStateShouldClearCart() {
        inventory.addAllProducts();
        menu.openMenu();
        menu.clickResetAppState();

        Assert.assertEquals(cartHeader.getCartBadgeCount(), 0, "Cart badge count should be 0 after Reset App State");
        List<String> buttonTexts = inventory.getAllAddRemoveButtonTexts();
        for (String text : buttonTexts) {
            Assert.assertEquals(text, "Add to cart", "BUG UI: After resetting, expected button to be 'Add to cart' but found '" + text + "'");
        }
    }
}
