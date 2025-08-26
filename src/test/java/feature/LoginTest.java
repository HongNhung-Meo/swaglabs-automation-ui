package feature;

import action.LoginAction;
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

@Epic("Authentication")
@Feature("Login Feature")
public class LoginTest extends BaseTest {
    LoginAction login;

    @BeforeMethod
    public void setupPage() {
        login = new LoginAction(driver);
    }

    @Step("Điền username: {0} và password: {1}")
    public void fillLoginData(String user, String pass) {
        login.enterUsername(user);
        login.enterPassword(pass);
    }
    @Test(description = "Kiểm tra hiển thị của các item")
    @Story("Kiểm tra UI của trang Login")
    @Severity(SeverityLevel.NORMAL)
    public void verifyLoginUIElementsDisplayed() {
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertTrue(login.isLogoDisplayed(), "Logo không hiển thị");
        softAssert.assertTrue(login.isUsernameDisplayed(), "Username không hiển thị");
        softAssert.assertTrue(login.isPasswordDisplayed(), "Password không hiển thị");
        softAssert.assertTrue(login.isLoginButtonDisplayed(), "Button Login không hiển thị");

        softAssert.assertAll();
    }

    @DataProvider(name = "loginData")
    public Object[][] loginData() {
        String excelFilePath = "DataTest.xlsx";
        String sheetName = "Login";

        List<Map<String, String>> excelData = ExcelUntils.readExcelData(excelFilePath, sheetName);
        Object[][] data = new Object[excelData.size()][1];
        for (int i = 0; i < excelData.size(); i++) {
            data[i][0] = excelData.get(i);
        }
        return data;
    }

    @Test(dataProvider = "loginData")
    @Story("Login với nhiều bộ dữ liệu")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Kiểm thử Login với data từ Excel bao gồm cả trường hợp pass và fail")
    public void testLogin(Map<String, String> rowData) {
        String userName = rowData.get("UserName");
        String passWord = rowData.get("PassWord");
        String expectedResult = rowData.get("ExpectedResult");
        String errorMsg = rowData.get("ExpectedErrorMessage");

        fillLoginData(userName, passWord);
        login.clickLogin();

        if ("success".equals(expectedResult)) {
            Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/inventory.html", "Login không thành công");
            Assert.assertTrue(login.getInventoryItemCount() > 0, "Inventory trống sau khi Login thành công");

        } else if ("failure".equals(expectedResult)) {
            WebElement error = login.getErrorMessage();
            Assert.assertTrue(error.isDisplayed(), "Thông báo lỗi không hiển thị sau khi Login không hợp lệ");
            Assert.assertTrue(error.getText().contains(errorMsg), "Thông báo lỗi hiển thị sai nội dung");
        } else {
            Assert.fail("Dữ liệu test không hợp lệ: ExpectedResult phải là 'success' hoặc 'failure'");
        }
    }
}