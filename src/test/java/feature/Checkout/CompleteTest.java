package feature.Checkout;

import action.*;
import action.Checkout.CompleteAction;
import action.Checkout.InformationAction;
import action.Checkout.OverviewAction;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import untils.BaseTest;

public class CompleteTest extends BaseTest {
    LoginAction login;
    InventoryAction inventory;
    CartAction cart;
    InformationAction inform;
    OverviewAction overview;
    CompleteAction complete;

    @BeforeMethod
    public void setupPage() {
        login = new LoginAction(driver);
        login.loginWithStandardUser();

        inventory = new InventoryAction(driver);
        cart = new CartAction(driver);
        inform = new InformationAction(driver);
        overview = new OverviewAction(driver);
        complete = new CompleteAction(driver);

        cart.openCart();
        cart.clickCheckout();
        inform.fillFormAndContinue();
        overview.clickFinish();
    }

    @Test(description = "Kiểm tra hiển thị của các item")
    @Story("Kiểm tra UI của trang Complete")
    @Severity(SeverityLevel.NORMAL)
    public void verifyCompleteUIDisplayed() {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(complete.getTitle(), "Checkout: Complete!", "Title không đúng");
        softAssert.assertEquals(complete.getCompleteHeader(), "Thank you for your order!", "CompleteHeader không hiển thị");
        softAssert.assertTrue(complete.isCompleteText(), "CompleteText không hiển thị");
        softAssert.assertTrue(complete.isBackHomeDisplayed(), "Button BackHome không hiển thị");

        softAssert.assertAll();
    }

    @Test(description = "Kiểm tra điều hướng khi click Button BackHome")
    @Story("Điều hướng BackHome")
    @Severity(SeverityLevel.CRITICAL)
    public void testBackHomeButtonNavigation() {
        complete.clickBackHome();
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory"),
                "Không điều hướng về trang Inventory khi click Back Home");
    }
}