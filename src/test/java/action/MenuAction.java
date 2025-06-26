package action;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ui.MenuPage;

import java.time.Duration;

public class MenuAction {
    WebDriver driver;
    WebDriverWait wait;

    public MenuAction(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void openMenu() {
        wait.until(ExpectedConditions.elementToBeClickable(MenuPage.MENU_BUTTON)).click();
    }

    public void clickAllItems() {
        wait.until(ExpectedConditions.elementToBeClickable(MenuPage.MENU_ALL_ITEMS)).click();
    }

    public void clickResetAppState() {
        wait.until(ExpectedConditions.elementToBeClickable(MenuPage.MENU_RESET)).click();
    }

    public void clickLogout() {
        wait.until(ExpectedConditions.elementToBeClickable(MenuPage.MENU_LOGOUT)).click();
    }

    public void clickAbout() {
        wait.until(ExpectedConditions.elementToBeClickable(MenuPage.MENU_ABOUT)).click();
    }
}
