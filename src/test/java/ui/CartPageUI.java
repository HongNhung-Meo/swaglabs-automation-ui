package ui;

import org.openqa.selenium.By;

public class CartPageUI {
    public static final By TITLE = By.className("title");

    public static final By CART_ICON = By.className("shopping_cart_link");
    public static final By CART_BADGE = By.className("shopping_cart_badge");

    public static final By CART_ITEMS = By.className("cart_item");
    public static String ITEM_NAME = "//div[@class='cart_item']//div[text()='%s']/ancestor::div[@class='cart_item']//div[@class='inventory_item_name']";
    public static String ITEM_DESC = "//div[@class='cart_item']//div[text()='%s']/ancestor::div[@class='cart_item']//div[@data-test='inventory-item-desc']";
    public static String ITEM_PRICE = "//div[@class='cart_item']//div[text()='%s']/ancestor::div[@class='cart_item']//div[@class='inventory_item_price']";

    public static final String REMOVE_BUTTON_NAME = "//div[@class='cart_item']//div[text()='%s']/ancestor::div[@class='cart_item']//button";
    public static final String REMOVE_BUTTONS = "//div[@class='cart_item']//button";
    public static final By CONTINUE_BUTTON = By.id("continue-shopping");
    public static final By CHECKOUT_BUTTON = By.id("checkout");
}