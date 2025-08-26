package ui.Checkout;

import org.openqa.selenium.By;

public class OverviewPageUI {
    public static final By TITLE = By.className("title");

    public static final By ITEM_NAME = By.className("inventory_item_name");
    public static final By ITEM_DESC = By.className("inventory_item_desc");
    public static final By ITEM_PRICE = By.className("inventory_item_price");
    public static final By PAYMENT_INFO = By.xpath("//div[contains(text(),'Payment')]");
    public static final By PAYMENT_VALUE = By.xpath("//div[contains(text(),'Card')]");
    public static final By SHIPPING_INFO = By.xpath("//div[contains(text(),'Shipping')]");
    public static final By SHIPPING_VALUE = By.xpath("//div[contains(text(),'Delivery')]");

    public static By ITEM_TOTAL = By.className("summary_subtotal_label");
    public static By TAX = By.className("summary_tax_label");
    public static By TOTAL = By.className("summary_total_label");

    public static By FINISH_BUTTON = By.id("finish");
    public static final By CANCEL_BUTTON = By.id("cancel");
}