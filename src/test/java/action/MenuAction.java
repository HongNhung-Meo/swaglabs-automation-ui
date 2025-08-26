package action;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ui.MenuPageUI;

import java.time.Duration;

public class MenuAction {
    WebDriver driver;
    WebDriverWait wait;

    public MenuAction(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public boolean isMenuButtonDisplayed() {
        return driver.findElement(MenuPageUI.MENU_BUTTON).isDisplayed();
    }

    public boolean isAllItemsDisplayed() {
        return wait.until(ExpectedConditions.elementToBeClickable(MenuPageUI.MENU_ALL_ITEMS)).isDisplayed();
    }

    public boolean isAboutDisplayed() {
        return wait.until(ExpectedConditions.elementToBeClickable(MenuPageUI.MENU_ABOUT)).isDisplayed();
    }

    public boolean isLogoutDisplayed() {
        return wait.until(ExpectedConditions.elementToBeClickable(MenuPageUI.MENU_LOGOUT)).isDisplayed();
    }

    public boolean isResetDisplayed() {
        return wait.until(ExpectedConditions.elementToBeClickable(MenuPageUI.MENU_RESET)).isDisplayed();
    }

    public void openMenu() {
        wait.until(ExpectedConditions.elementToBeClickable(MenuPageUI.MENU_BUTTON)).click();
    }

    public void clickAllItems() {
        wait.until(ExpectedConditions.elementToBeClickable(MenuPageUI.MENU_ALL_ITEMS)).click();
    }

    public void clickAbout() {
        wait.until(ExpectedConditions.elementToBeClickable(MenuPageUI.MENU_ABOUT)).click();
    }

    public void clickLogout() {
        wait.until(ExpectedConditions.elementToBeClickable(MenuPageUI.MENU_LOGOUT)).click();
    }

    public void clickResetAppState() {
        wait.until(ExpectedConditions.elementToBeClickable(MenuPageUI.MENU_RESET)).click();
    }

}
