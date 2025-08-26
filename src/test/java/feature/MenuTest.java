package feature;

import action.CartAction;
import action.InventoryAction;
import action.LoginAction;
import action.MenuAction;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import untils.BaseTest;

@Feature("Menu Navigation")
public class MenuTest extends BaseTest {
    LoginAction login;
    MenuAction menu;
    InventoryAction inventory;
    CartAction cart;

    @BeforeMethod
    public void setupPage() {
        login = new LoginAction(driver);
        login.loginWithStandardUser();

        inventory = new InventoryAction(driver);
        cart = new CartAction(driver);
        menu = new MenuAction(driver);
    }

    @Test(description = "Verify Menu button hiển thị trên Inventory page")
    public void verifyMenuButtonDisplayed() {
        Assert.assertTrue(menu.isMenuButtonDisplayed(), "Menu button không hiển thị!");
    }

    @Test(description = "Kiểm tra hiển thị của các item")
    @Story("Kiểm tra UI của Menu")
    @Severity(SeverityLevel.MINOR)
    public void verifyInventoryUIElementsDisplayed() {
        SoftAssert softAssert = new SoftAssert();
        menu.openMenu();
        softAssert.assertTrue(menu.isAllItemsDisplayed(), "All Items không hiển thị");
        softAssert.assertTrue(menu.isAboutDisplayed(), "About không hiển thị");
        softAssert.assertTrue(menu.isLogoutDisplayed(), "Logout không hiển thị");
        softAssert.assertTrue(menu.isResetDisplayed(), "Reset không hiển thị");
        softAssert.assertAll();
    }

    @Test(description = "Kiểm tra điều hướng khi click vào menu All items")
    @Story("Navigation")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Click All items trong menu phải điều hướng về trang Inventory")
    public void testNavigateAllItems() {
        menu.openMenu();
        menu.clickAllItems();

        Assert.assertTrue(driver.getCurrentUrl().contains("inventory"), "Không điều hướng về Inventory");
    }

    @Test(description = "Kiểm tra điều hướng khi click Logout")
    @Story("Navigation")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Click Logout trong menu phải điều hướng về trang Login")
    public void testNavigateLogout() {
        menu.openMenu();
        menu.clickLogout();
        Assert.assertTrue(driver.getCurrentUrl().contains("saucedemo.com/"), "Không điều hướng về Login");
    }

    @Test(description = "Kiểm tra điều hướng khi click About")
    @Story("Navigation")
    @Severity(SeverityLevel.NORMAL)
    @Description("Click About trong menu phải điều hướng sang trang Sauce Labs")
    public void testNavigateAbout() {
        menu.openMenu();
        menu.clickAbout();

        Assert.assertTrue(driver.getCurrentUrl().contains("saucelabs"), "Không điều hướng sang trang About (Sauce Labs)");
    }

    @Test(description = "Kiểm tra chức năng Reset App State")
    @Story("Functionality")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Click Reset App State trong menu phải xóa sạch giỏ hàng")
    public void testResetAppState() {
        inventory.addProductByName("Sauce Labs Backpack");
        menu.openMenu();
        menu.clickResetAppState();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(cart.getCartBadge().isEmpty(), "Cart badge vẫn hiển thị sau khi reset");
        softAssert.assertEquals(inventory.getButtonTextByProduct("Sauce Labs Backpack"), "Add to cart");
        softAssert.assertAll();
    }
}
