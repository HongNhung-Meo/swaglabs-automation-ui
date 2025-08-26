package ui.Checkout;

import org.openqa.selenium.By;

public class InformationPageUI {
    public static final By PAGE_TITLE = By.className("title");

    public static final By FIRST_NAME = By.id("first-name");
    public static final By LAST_NAME = By.id("last-name");
    public static final By ZIP_CODE = By.id("postal-code");
    public static final By ERROR_MESSAGE = By.cssSelector(".error-message-container.error");

    public static final By CANCEL_BUTTON = By.id("cancel");
    public static final By CONTINUE_BUTTON = By.id("continue");
}