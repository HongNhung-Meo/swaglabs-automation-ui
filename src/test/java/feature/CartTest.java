package feature;

import action.InventoryAction;
import action.LoginAction;
import action.CartAction;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import untils.BaseTest;

import java.util.Arrays;
import java.util.List;

@Epic("Cart Management")
@Feature("Cart feature")

public class CartTest extends BaseTest {
    LoginAction login;
    CartAction cart;
    InventoryAction inventory;

    @BeforeMethod
    public void setupPage() {
        login = new LoginAction(driver);
        login.loginWithStandardUser();

        cart = new CartAction(driver);
        inventory = new InventoryAction(driver);
    }

    @Test(description = "Kiểm tra hiển thị của các item")
    @Story("Kiểm tra UI của trang Cart")
    @Severity(SeverityLevel.NORMAL)
    public void verifyCartUIDisplayed() {
        cart.openCart();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(cart.getPageTitle(), "Your Cart", "Title không đúng");
        softAssert.assertTrue(cart.isCartIconDisplayed(), "Giỏ hàng không hiển thị");
        softAssert.assertAll();
    }

    @Test(description = "Thêm 1 sản phẩm và kiểm tra UI")
    @Story("Add one product and verify UI")
    @Severity(SeverityLevel.CRITICAL)
    public void addOneProductAndVerifyUI() {
        String product = "Sauce Labs Backpack";
        inventory.addProductByName(product);
        cart.openCart();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(cart.getProductNameByName(product), product, "Tên sản phẩm sai");
        softAssert.assertTrue(cart.isProductDescriptionDisplayedByName(product), "Mô tả sản phẩm không hiển thị");
        softAssert.assertTrue(cart.isProductPriceDisplayedByName(product), "Giá sản phẩm không hiển thị");
        softAssert.assertTrue(cart.isRemoveButtonDisplayedByName(product), "Nút Remove không hiển thị");
        softAssert.assertEquals(cart.getCartBadge(), "1", "Badge không đúng");
        softAssert.assertAll();
    }

    @Test(description = "Thêm nhiều sản phẩm và kiểm tra UI")
    @Story("Add multiple products and verify UI")
    @Severity(SeverityLevel.CRITICAL)
    public void addMultipleProductsAndVerifyUI() {
        List<String> products = Arrays.asList("Sauce Labs Backpack", "Sauce Labs Bike Light", "Sauce Labs Bolt T-Shirt");
        inventory.addProductsByNames(products);
        cart.openCart();

        SoftAssert softAssert = new SoftAssert();
        for (String product : products) {
            softAssert.assertEquals(cart.getProductNameByName(product), product, "Tên sản phẩm sai: " + product);
            softAssert.assertTrue(cart.isProductDescriptionDisplayedByName(product), "Mô tả không hiển thị: " + product);
            softAssert.assertTrue(cart.isProductPriceDisplayedByName(product), "Giá không hiển thị: " + product);
            softAssert.assertTrue(cart.isRemoveButtonDisplayedByName(product), "Nút Remove không hiển thị: " + product);
        }
        softAssert.assertEquals(cart.getCartBadge(), String.valueOf(products.size()), "Badge không đúng");
        softAssert.assertAll();
    }

    @Test(description = "Xóa 1 sản phẩm theo tên")
    @Story("Remove one product by name")
    @Severity(SeverityLevel.CRITICAL)
    public void removeOneProductByName() {
        List<String> products = Arrays.asList("Sauce Labs Backpack", "Sauce Labs Bike Light");
        inventory.addProductsByNames(products);
        cart.openCart();

        cart.removeProductByName("Sauce Labs Backpack");

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertFalse(cart.isRemoveButtonDisplayedByName("Sauce Labs Backpack"), "Sản phẩm đã xóa vẫn hiển thị");
        softAssert.assertTrue(cart.isRemoveButtonDisplayedByName("Sauce Labs Bike Light"), "Sản phẩm còn lại bị mất");
        softAssert.assertEquals(cart.getCartBadge(), "1", "Badge không đúng sau khi xóa");
        softAssert.assertTrue(cart.getCartItemCount() == 1, "Số lượng sản phẩm không đúng");
        softAssert.assertAll();
    }

    @Test(description = "Xóa nhiều sản phẩm theo tên")
    @Story("Remove multiple products by name")
    @Severity(SeverityLevel.CRITICAL)
    public void removeMultipleProductsByName() {
        List<String> products = Arrays.asList("Sauce Labs Backpack", "Sauce Labs Bike Light", "Sauce Labs Bolt T-Shirt", "Sauce Labs Fleece Jacket");
        inventory.addProductsByNames(products);
        cart.openCart();

        cart.removeProductsByNames(Arrays.asList("Sauce Labs Backpack", "Sauce Labs Bolt T-Shirt"));

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertFalse(cart.isRemoveButtonDisplayedByName("Sauce Labs Backpack"));
        softAssert.assertFalse(cart.isRemoveButtonDisplayedByName("Sauce Labs Bolt T-Shirt"));
        softAssert.assertTrue(cart.isRemoveButtonDisplayedByName("Sauce Labs Bike Light"));
        softAssert.assertTrue(cart.isRemoveButtonDisplayedByName("Sauce Labs Fleece Jacket"));
        softAssert.assertEquals(cart.getCartBadge(), "2", "Badge không đúng sau khi xóa nhiều sản phẩm");
        softAssert.assertTrue(cart.getCartItemCount() == 2, "Số lượng sản phẩm không đúng");
        softAssert.assertAll();
    }

    @Test(description = "Xóa tất cả sản phẩm")
    @Story("Remove all products from cart")
    @Severity(SeverityLevel.CRITICAL)
    public void removeAllProductsFromCart() {
        List<String> products = Arrays.asList("Sauce Labs Backpack", "Sauce Labs Bike Light");
        inventory.addProductsByNames(products);
        cart.openCart();

        cart.removeAllProducts();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(cart.isCartEmpty(), "Cart chưa rỗng sau khi xóa tất cả sản phẩm");
        softAssert.assertEquals(cart.getCartBadge(), "", "Badge vẫn còn hiển thị");

        softAssert.assertAll();
    }

    @Test(description = "Nút Continue Shopping đưa về Inventory")
    @Severity(SeverityLevel.NORMAL)
    @Story("Navigation")
    public void testContinueShopping() {
        cart.openCart();
        cart.clickContinue();

        Assert.assertTrue(driver.getCurrentUrl().contains("inventory"), "Không quay lại trang Inventory");
    }

    @Test(description = "Nút Checkout chuyển qua checkout page")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Navigation")
    public void testCheckoutNavigation() {
        inventory.addProductByName("Sauce Labs Backpack");
        cart.openCart();
        cart.clickCheckout();

        Assert.assertTrue(driver.getCurrentUrl().contains("checkout-step-one"), "Không điều hướng đến trang checkout");
    }
}