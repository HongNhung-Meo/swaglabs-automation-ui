package ui;

import org.openqa.selenium.By;

public class CheckoutOverviewPage {
    public static final By PAGE_TITLE = By.className("title");

    public static final By NAME_ITEM_LIST = By.className("inventory_item_name");
    public static final By PRICE_ITEM_LIST = By.className("inventory_item_price");
    public static By ITEM_TOTAL = By.className("summary_subtotal_label");
    public static By TAX = By.className("summary_tax_label");
    public static By TOTAL = By.className("summary_total_label");

    public static By FINISH_BUTTON = By.id("finish");
    public static final By CANCEL_BUTTON = By.id("cancel");
}