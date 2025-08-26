package feature.Checkout;

import action.*;
import action.Checkout.InformationAction;
import io.qameta.allure.*;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import untils.BaseTest;
import untils.ExcelUntils;

import java.util.List;
import java.util.Map;

@Epic("Checkout")
@Feature("Checkout Step One Page")
public class InformationTest extends BaseTest {
    LoginAction login;
    InventoryAction inventory;
    CartAction cart;
    InformationAction checkoutInform;

    @BeforeMethod
    public void setup() {
        login = new LoginAction(driver);
        login.loginWithStandardUser();

        inventory = new InventoryAction(driver);
        cart = new CartAction(driver);
        checkoutInform = new InformationAction(driver);

        // mở giỏ và vào checkout
        cart.openCart();
        cart.clickCheckout();
    }

    @Step("Điền thông tin: firstname = {0}, lastname = {1}, zipcode = {2}")
    public void fillCheckoutDataInform(String first, String last, String zip) {
        checkoutInform.enterFirstName(first);
        checkoutInform.enterLastName(last);
        checkoutInform.enterZipCode(zip);
    }

    @Test(description = "Kiểm tra hiển thị của các item trên Checkout Step One")
    @Story("UI Validation")
    @Severity(SeverityLevel.NORMAL)
    public void verifyCheckoutStepOneUI() {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(checkoutInform.getTitle(), "Checkout: Your Information", "Title không đúng");
        softAssert.assertTrue(checkoutInform.isFirstNameDisplayed(), "Firstname không hiển thị");
        softAssert.assertTrue(checkoutInform.isLastNameDisplayed(), "Lastname không hiển thị");
        softAssert.assertTrue(checkoutInform.isZipDisplayed(), "Zipcode không hiển thị");
        softAssert.assertTrue(checkoutInform.isCancelButtonDisplayed(), "Nút Cancel không hiển thị");
        softAssert.assertTrue(checkoutInform.isContinueButtonDisplayed(), "Nút Continue không hiển thị");

        softAssert.assertAll();
    }

    @Test(description = "Kiểm tra Cancel trên Checkout Step One")
    @Story("Functionality")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Click Cancel trên Checkout Step One phải quay về giỏ hàng")
    public void testCancelButton() {
        checkoutInform.clickCancel();
        Assert.assertEquals(cart.getPageTitle(), "Your Cart", "Không quay về trang giỏ hàng sau khi Cancel");
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
    @Story("Validation với nhiều bộ dữ liệu")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Kiểm thử checkout step one với dữ liệu từ Excel bao gồm cả pass và fail")
    public void testCheckoutInfo(Map<String, String> rowData) {
        String firstName = rowData.get("FirstName");
        String lastName = rowData.get("LastName");
        String zipCode = rowData.get("ZipCode");
        String expectedResult = rowData.get("ExpectedResult");   // success / failure
        String errorMsg = rowData.get("ExpectedErrorMessage");

        fillCheckoutDataInform(firstName, lastName, zipCode);
        checkoutInform.clickContinue();

        if ("success".equalsIgnoreCase(expectedResult)) {
            Assert.assertTrue(driver.getCurrentUrl().contains("step-two"),
                    "Không chuyển sang Checkout Step Two khi dữ liệu hợp lệ");
        } else if ("failure".equalsIgnoreCase(expectedResult)) {
            WebElement error = checkoutInform.getErrorMessage();
            Assert.assertTrue(error.isDisplayed(), "Thông báo lỗi không hiển thị sau khi Login không hợp lệ");
            Assert.assertTrue(error.getText().contains(errorMsg), "Thông báo lỗi hiển thị sai nội dung");
        } else {
            Assert.fail("Dữ liệu test không hợp lệ: ExpectedResult phải là 'success' hoặc 'failure'");
        }
    }
}