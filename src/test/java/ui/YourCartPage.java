package ui;

import org.openqa.selenium.By;

public class YourCartPage {
    public static final By PAGE_TITLE = By.className("title");

    public static final By CART_ITEM = By.className("cart_item");
    public static final By CART_ITEM_NAME = By.className("inventory_item_name");
    public static final By CART_ITEM_PRICE = By.className("inventory_item_price");
    public static final By CART_ITEM_DESC = By.xpath("//div[@data-test= 'inventory-item-desc']");

    public static final By ADD_BUTTON = By.className("btn_inventory");
    public static final By REMOVE_BUTTON = By.xpath("//button[contains(@class,'cart_button')]");
    public static final By CONTINUE_BUTTON = By.id("continue-shopping");
    public static final By CHECKOUT_BUTTON = By.id("checkout");
}