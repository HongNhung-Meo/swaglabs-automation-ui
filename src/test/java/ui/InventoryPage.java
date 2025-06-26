package ui;

import org.openqa.selenium.By;

public class InventoryPage {
    public static final By PAGE_TITLE = By.className("title");

    public static final By INVENTORY_ITEM = By.className("inventory_item");
    public static final By NAME_ITEM_LIST = By.className("inventory_item_name");
    public static final By DESC_ITEM_LIST = By.className("inventory_item_desc");
    public static final By PRICE_ITEM_LIST = By.className("inventory_item_price");

    public static final By ADD_BUTTONS = By.xpath("//button[text()='Add to cart']");
    public static final By REMOVE_BUTTONS = By.xpath("//button[text()='Remove']");
    public static final By ADD_REMOVE_BUTTON = By.cssSelector(".pricebar button");

    public static final By PRODUCT_SORT_DROPDOWN = By.className("product_sort_container");
}
