package feature;

import action.*;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import untils.BaseTest;
import untils.ExcelUntils;

import java.util.List;
import java.util.Map;

public class CheckoutInformationTest extends BaseTest {
    LoginAction login;
    InventoryAction inventory;
    CartHeaderAction cartHeader;
    YourCartAction cart;
    CheckoutInformationAction checkoutInformation;

    @BeforeMethod
    public void setup() {
        login = new LoginAction(driver);
        login.loginWithStandardUser();

        inventory = new InventoryAction(driver);
        cartHeader = new CartHeaderAction(driver);
        cart = new YourCartAction(driver);
        checkoutInformation = new CheckoutInformationAction(driver);

        inventory.addFirstProduct();
        cartHeader.openCart();
        cart.clickCheckout();
    }

    public void fillCheckoutDataInfor(String first, String last, String zip) {
        checkoutInformation.enterFirstName(first);
        checkoutInformation.enterLastName(last);
        checkoutInformation.enterZipCode(zip);
    }

    @DataProvider(name = "informationData")
    public Object[][] informationData() {
        String excelFilePath = "DataTest.xlsx";
        String sheetName = "Information";

        List<Map<String, String>> excelData = ExcelUntils.readExcelData(excelFilePath, sheetName);
        Object[][] data = new Object[excelData.size()][1];
        for (int i = 0; i < excelData.size(); i++) {
            data[i][0] = excelData.get(i);
        }
        return data;
    }

    @Test(dataProvider = "informationData")
    public void testCheckoutInfo(Map<String, String> rowData) {
        String firstName = rowData.get("FirstName");
        String lastName = rowData.get("LastName");
        String zipCode = rowData.get("ZipCode");
        String expectedResult = rowData.get("ExpectedResult");
        String expectedErrorMsg = rowData.get("ExpectedErrorMessage");

        fillCheckoutDataInfor(firstName, lastName, zipCode);
        checkoutInformation.clickContinue();

        boolean isStepTwoPage;
        try {
            isStepTwoPage = driver.getCurrentUrl().contains("checkout-step-two");
        } catch (Exception e) {
            isStepTwoPage = false;
        }

        if ("success".equals(expectedResult)) {
            Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/checkout-step-two.html", "Did not navigate to Checkout Step Two (Overview) as expected");

        } else if ("failure".equals(expectedResult)) {
            boolean uiBug = false;

            if (isStepTwoPage) {
                System.out.println("BUG: Expected failure but navigated to Step Two");
                uiBug = true;
            }

            String title = checkoutInformation.getTitle();
            if (!title.equals("Checkout: Your Information")) {
                System.out.println("BUG UI: Page title mismatch");
                uiBug = true;
            }

            WebElement error = checkoutInformation.getErrorMessage();
            if (error == null || !error.isDisplayed()) {
                System.out.println("BUG UI: Error message not displayed!");
                uiBug = true;
            } else if (!error.getText().equals(expectedErrorMsg)) {
                System.out.println("BUG UI: Error message incorrect. Expected: '" + expectedErrorMsg + "', Found: '" + error.getText() + "'");
                uiBug = true;
            }

            if (uiBug) {
                Assert.fail("BUG UI: Form accepted invalid input or showed incorrect UI feedback");
            }
        } else {
            Assert.fail("Invalid test data: ExpectedResult must be either 'success' or 'failure'");
        }
    }

    @Test
    public void clickCancelShouldReturnToCartAndDisplayCorrectly() {
        checkoutInformation.clickCancel();

        Assert.assertTrue(driver.getCurrentUrl().contains("cart"), "Cancel did not return to Your Cart page");
        Assert.assertEquals(cart.getTitle(), "Your Cart", "Page title is incorrect after clicking Cancel");

        List<String> productNamesAfterCancel = cart.getProductNames();
        int expectedCount = cartHeader.getCartBadgeCount();

        Assert.assertEquals(productNamesAfterCancel.size(), expectedCount, "Cart items changed after clicking Cancel");
        Assert.assertEquals(cart.getCartItemCount(), expectedCount, "Cart count mismatch after returning from Checkout");
    }

    @Test
    public void testWhitespaceInput_AllFields() {
        fillCheckoutDataInfor("   ", "   ", "   ");
        checkoutInformation.clickContinue();

        if (driver.getCurrentUrl().contains("checkout-step-two")) {
            Assert.fail("BUG UI: Checkout proceeded to Step Two despite whitespace-only input");
        }
    }
}