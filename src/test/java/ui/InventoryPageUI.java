package ui;

import org.openqa.selenium.By;

public class InventoryPageUI {
    public static final By APP_LOGO = By.className("app_logo");
    public static final By TITLE = By.className("title");
    public static final By SORT_DROPDOWN = By.className("product_sort_container");

    public static final By INVENTORY_ITEMS = By.className("inventory_item");
    public static final By ITEM_NAME = By.className("inventory_item_name");
    public static final By ITEM_DESC = By.className("inventory_item_desc");
    public static final By ITEM_PRICE = By.className("inventory_item_price");
    public static final By ITEM_IMAGE = By.className("inventory_item_img");
    public static final By ADD_REMOVE_BUTTON = By.cssSelector(".pricebar button");

}
