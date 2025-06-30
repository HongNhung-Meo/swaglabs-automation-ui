package feature;

import action.LoginAction;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import untils.BaseTest;
import untils.ExcelUntils;

import java.util.List;
import java.util.Map;

public class LoginTest extends BaseTest {
    LoginAction loginAction;

    @BeforeMethod
    public void setupPage() {
        loginAction = new LoginAction(driver);
    }

    public void fillLoginData(String user, String pass) {
        loginAction.enterUsername(user);
        loginAction.enterPassword(pass);
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
    public void testLogin(Map<String, String> rowData) {
        String userName = rowData.get("UserName");
        String passWord = rowData.get("PassWord");
        String expectedResult = rowData.get("ExpectedResult");
        String errorMsg = rowData.get("ExpectedErrorMessage");

        fillLoginData(userName, passWord);
        loginAction.clickLogin();

        if ("success".equals(expectedResult)) {
            Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/inventory.html", "Login failed unexpectedly!");
            Assert.assertTrue(loginAction.isLogoDisplayed(), "Logo is not displayed");
          Assert.assertTrue(loginAction.getInventoryItemCount() > 0, "Inventory is empty after successful login");

        } else if ("failure".equals(expectedResult)) {

            WebElement error = loginAction.getErrorMessage();
            Assert.assertTrue(error.isDisplayed(), "Error message not displayed for invalid login");
            Assert.assertTrue(error.getText().contains(errorMsg), "Incorrect error message displayed");
        } else {
            Assert.fail("Invalid test data: ExpectedResult must be either 'success' or 'failure'");
        }
    }
}