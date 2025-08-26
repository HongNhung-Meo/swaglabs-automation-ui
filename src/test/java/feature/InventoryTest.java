package feature;

import action.InventoryAction;
import action.LoginAction;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import untils.BaseTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Epic("Product Management")
@Feature("Inventory Feature")
public class InventoryTest extends BaseTest {
    LoginAction login;
    InventoryAction inventory;

    @BeforeMethod
    public void setupPage() {
        login = new LoginAction(driver);
        login.loginWithStandardUser();

        inventory = new InventoryAction(driver);
    }

    @Test(description = "Kiểm tra hiển thị của các item")
    @Story("Kiểm tra UI của trang Inventory")
    @Severity(SeverityLevel.NORMAL)
    public void verifyInventoryUIElementsDisplayed() {
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertTrue(inventory.isLogoDisplayed(), "Logo không hiển thị");
        softAssert.assertEquals(inventory.getPageTitle(), "Products", "Title không đúng");
        softAssert.assertTrue(inventory.isSortDropdownDisplayed(), "Sort Dropdown không hiển thị");

        List<String> expectedOptions = Arrays.asList("Name (A to Z)", "Name (Z to A)", "Price (low to high)", "Price (high to low)");
        softAssert.assertEquals(inventory.getAllOptionsFromSortDropdown(), expectedOptions);

        for (String name : inventory.getAllItemNames()) {
            softAssert.assertFalse(name.isEmpty(), "Tên sản phẩm bị trống");
        }
        for (String desc : inventory.getAllItemDescriptions()) {
            softAssert.assertFalse(desc.isEmpty(), "Mô tả bị trống");
        }
        for (String price : inventory.getAllItemPrices()) {
            softAssert.assertTrue(price.startsWith("$"), "Giá không hợp lệ: " + price);
        }
        for (Boolean img : inventory.getAllItemImagesDisplayed()) {
            softAssert.assertTrue(img, "Có sản phẩm không hiển thị hình ảnh");
        }
        for (Boolean btn : inventory.areAllButtonsDisplayed()) {
            softAssert.assertTrue(btn, "Có button không hiển thị");
        }
        softAssert.assertAll();
    }

    @Test(description = "Sắp xếp theo Name (A to Z)")
    @Story("Sắp xếp theo Tên")
    @Severity(SeverityLevel.CRITICAL)
    public void testSortByNameAToZ() {
        inventory.selectSortOption("Name (A to Z)");
        List<String> productNames = inventory.getAllProductNames();

        List<String> sortedNames = new ArrayList<>(productNames);
        Collections.sort(sortedNames);

        Assert.assertEquals(productNames, sortedNames, "Sản phẩm chưa được sort A → Z đúng!");
    }

    @Test(description = "Sắp xếp theo Name (Z to A)")
    @Story("Sắp xếp theo Tên")
    @Severity(SeverityLevel.CRITICAL)
    public void testSortByNameZToA() {
        inventory.selectSortOption("Name (Z to A)");
        List<String> productNames = inventory.getAllProductNames();

        List<String> sortedNames = new ArrayList<>(productNames);
        sortedNames.sort(Collections.reverseOrder());

        Assert.assertEquals(productNames, sortedNames, "Sản phẩm chưa được sort Z → A đúng!");
    }

    @Test(description = "Sắp xếp theo Price (low to high)")
    @Story("Sắp xếp theo Giá")
    @Severity(SeverityLevel.BLOCKER)
    public void testSortByPriceLowToHigh() {
        inventory.selectSortOption("Price (low to high)");
        List<Double> productPrices = inventory.getAllProductPrices();

        List<Double> sortedPrices = new ArrayList<>(productPrices);
        Collections.sort(sortedPrices);

        Assert.assertEquals(productPrices, sortedPrices, "Sản phẩm chưa được sort giá thấp → cao đúng!");
    }

    @Test(description = "Sắp xếp theo Price (high to low)")
    @Story("Sắp xếp theo Giá")
    @Severity(SeverityLevel.BLOCKER)
    public void testSortByPriceHighToLow() {
        inventory.selectSortOption("Price (high to low)");
        List<Double> productPrices = inventory.getAllProductPrices();

        List<Double> sortedPrices = new ArrayList<>(productPrices);
        sortedPrices.sort(Collections.reverseOrder());

        Assert.assertEquals(productPrices, sortedPrices, "Sản phẩm chưa được sort giá cao → thấp đúng!");
    }

    @Test(description = "Thêm 1 sản phẩm theo tên")
    @Description("Kiểm tra khi thêm 1 sản phẩm, button phải đổi sang 'Remove'")
    @Step("Thêm 1 sản phẩm")
    public void testAddSingleProduct() {
        inventory.addProductByName("Sauce Labs Backpack");
        Assert.assertEquals(inventory.getButtonTextByProduct("Sauce Labs Backpack"), "Remove");
    }

    @Test(description = "Thêm nhiều sản phẩm theo danh sách")
    @Description("Kiểm tra khi thêm nhiều sản phẩm, button của các sản phẩm đó phải đổi sang 'Remove'")
    @Step("Thêm nhiều sản phẩm")
    public void testAddMultipleProducts() {
        List<String> products = Arrays.asList("Sauce Labs Backpack", "Sauce Labs Bike Light");
        inventory.addProductsByNames(products);

        for (String product : products) {
            Assert.assertEquals(inventory.getButtonTextByProduct(product), "Remove");
        }
    }

    @Test(description = "Thêm tất cả sản phẩm")
    @Step("Thêm tất cả sản phẩm")
    @Description("Kiểm tra khi thêm tất cả sản phẩm, button của tất cả item phải đổi sang 'Remove'")
    public void testAddAllProducts() {
        inventory.addAllProducts();

        for (String text : inventory.getAllButtonTexts()) {
            Assert.assertEquals(text, "Remove");
        }

    }

    @Test(description = "Xóa 1 sản phẩm theo tên")
    @Description("Kiểm tra khi xóa 1 sản phẩm, button phải đổi sang 'Add to cart'")
    @Step("Xóa 1 sản phẩm")
    public void testRemoveSingleProduct() {
        inventory.addProductByName("Sauce Labs Backpack");
        inventory.removeProductByName("Sauce Labs Backpack");

        Assert.assertEquals(inventory.getButtonTextByProduct("Sauce Labs Backpack"), "Add to cart");
    }

    @Test(description = "Xóa nhiều sản phẩm theo danh sách")
    @Description("Kiểm tra khi xóa nhiều sản phẩm, button phải đổi sang 'Add to cart'")
    @Step("Xóa nhiều sản phẩm")
    public void testRemoveMultipleProducts() {
        List<String> products = Arrays.asList("Sauce Labs Backpack", "Sauce Labs Bike Light");
        inventory.addProductsByNames(products);
        inventory.removeProductsByNames(products);

        for (String product : products) {
            Assert.assertEquals(inventory.getButtonTextByProduct(product), "Add to cart");
        }
    }

    @Test(description = "Xóa tất cả sản phẩm")
    @Description("Kiểm tra khi xóa tất cả sản phẩm, button của tất cả item phải đổi sang 'Add to cart'")
    @Step("Xóa tất cả sản phẩm")
    public void testRemoveAllProducts() {
        inventory.addAllProducts();
        inventory.removeAllProducts();

        for (String text : inventory.getAllButtonTexts()) {
            Assert.assertEquals(text, "Add to cart");
        }
    }
}