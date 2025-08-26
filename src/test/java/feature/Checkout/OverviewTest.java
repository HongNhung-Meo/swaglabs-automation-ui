package feature.Checkout;

import action.*;
import action.Checkout.InformationAction;
import action.Checkout.OverviewAction;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import untils.BaseTest;

import java.util.List;

@Epic("Checkout")
@Feature("Checkout Step Two Page")
public class OverviewTest extends BaseTest {
    LoginAction login;
    InventoryAction inventory;
    CartAction cart;
    InformationAction inform;
    OverviewAction overview;

    @BeforeMethod
    public void setupPage() {
        login = new LoginAction(driver);
        login.loginWithStandardUser();

        inventory = new InventoryAction(driver);
        cart = new CartAction(driver);
        inform = new InformationAction(driver);
        overview = new OverviewAction(driver);
    }

    @Test(description = "Kiểm tra hiển thị của các item")
    @Story("Kiểm tra UI của trang Overview")
    @Severity(SeverityLevel.NORMAL)
    public void verifyOverviewUIDisplayed() {
        cart.openCart();
        cart.clickCheckout();
        inform.fillFormAndContinue();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(overview.getTitle(), "Checkout: Overview", "Title không đúng");
        softAssert.assertTrue(overview.isPaymentInfo(), "Payment info label không hiển thị");
        softAssert.assertTrue(overview.isPaymentValue(), "Payment value label không hiển thị");
        softAssert.assertTrue(overview.isShippingInfo(), "Shipping info label không hiển thị");
        softAssert.assertTrue(overview.isShippingValue(), "Shipping value label không hiển thị");

        softAssert.assertTrue(overview.isCancelDisplayed(), "Cancel button không hiển thị");
        softAssert.assertTrue(overview.isFinishDisplayed(), "Finish button không hiển thị");
        softAssert.assertAll();
    }

    @Test(description = "Check UI và giá khi thêm 1 sản phẩm")
    @Severity(SeverityLevel.CRITICAL)
    public void testSingleProductPrice() {
        String productName = "Sauce Labs Backpack";

        inventory.addProductByName(productName);
        cart.openCart();
        cart.clickCheckout();
        inform.fillFormAndContinue();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(overview.isProductDisplayed("Sauce Labs Backpack"), "Sản phẩm không hiển thị");
        softAssert.assertTrue(overview.isProductDescDisplayed(productName), "Mô tả không hiển thị");

        String productPrice = overview.getProductPrice(productName);
        softAssert.assertNotNull(productPrice, "Không tìm thấy giá sản phẩm: " + productName);

        // check Item total = giá sp
        softAssert.assertEquals(overview.getItemTotal(),
                Double.parseDouble(productPrice.replace("$", "")),
                "Item total không khớp với giá sản phẩm");

        // check total = item total + tax
        softAssert.assertTrue(overview.isTotalCorrect(), "Tính toán total sai");

        softAssert.assertAll();
    }

    @Test(description = "Check UI & giá khi thêm nhiều sản phẩm")
    @Severity(SeverityLevel.BLOCKER)
    public void testMultipleProductsOverview() {
        String[] products = {"Sauce Labs Backpack", "Sauce Labs Bike Light"};

        // add nhiều sp
        for (String p : products) {
            inventory.addProductByName(p);
        }

        cart.openCart();
        cart.clickCheckout();
        inform.fillFormAndContinue();

        SoftAssert softAssert = new SoftAssert();

        // check từng sp hiển thị
        for (String p : products) {
            softAssert.assertTrue(overview.isProductDisplayed(p), "Tên sản phẩm không hiển thị: " + p);
            softAssert.assertTrue(overview.isProductDescDisplayed(p), "Mô tả không hiển thị: " + p);
            softAssert.assertNotNull(overview.getProductPrice(p), "Không tìm thấy giá sản phẩm: " + p);
        }

        // check item total = sum của từng giá
        List<Double> prices = overview.getItemPrices();
        double sumPrices = prices.stream().mapToDouble(Double::doubleValue).sum();
        softAssert.assertEquals(overview.getItemTotal(), sumPrices, "Item total không đúng");

        // check total = item total + tax
        softAssert.assertTrue(overview.isTotalCorrect(), "Tính toán total sai");

        softAssert.assertAll();
    }

    @Test(description = "Kiểm tra điều hướng khi click Cancel button")
    @Story("Điều hướng Cancel")
    @Severity(SeverityLevel.CRITICAL)
    public void testCancelButtonNavigation() {
        cart.openCart();
        cart.clickCheckout();
        inform.fillFormAndContinue();

        overview.clickCancel();
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory"),
                "Không điều hướng về trang Inventory khi click Cancel");
    }

    @Test(description = "Kiểm tra điều hướng khi click Finish button")
    @Story("Điều hướng Finish")
    @Severity(SeverityLevel.CRITICAL)
    public void testFinishButtonNavigation() {
        String productName = "Sauce Labs Backpack";
        inventory.addProductByName(productName);
        cart.openCart();
        cart.clickCheckout();
        inform.fillFormAndContinue();

        overview.clickFinish();
        Assert.assertTrue(driver.getCurrentUrl().contains("checkout-complete"),
                "Không điều hướng về Checkout Complete khi click Finish");
    }

}