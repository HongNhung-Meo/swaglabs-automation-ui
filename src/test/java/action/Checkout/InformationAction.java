package action.Checkout;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ui.Checkout.InformationPageUI;

import java.time.Duration;

public class InformationAction {
    WebDriver driver;
    WebDriverWait wait;

    public InformationAction(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    }

    public String getTitle() {
        return driver.findElement(InformationPageUI.PAGE_TITLE).getText();
    }


    public boolean isFirstNameDisplayed() {
        return driver.findElement(InformationPageUI.FIRST_NAME).isDisplayed();
    }

    public boolean isLastNameDisplayed() {
        return driver.findElement(InformationPageUI.LAST_NAME).isDisplayed();
    }

    public boolean isZipDisplayed() {
        return driver.findElement(InformationPageUI.ZIP_CODE).isDisplayed();
    }

    public boolean isCancelButtonDisplayed() {
        return driver.findElement(InformationPageUI.CANCEL_BUTTON).isDisplayed();
    }

    public boolean isContinueButtonDisplayed() {
        return driver.findElement(InformationPageUI.CONTINUE_BUTTON).isDisplayed();
    }
    public void enterFirstName(String firstname) {
        driver.findElement(InformationPageUI.FIRST_NAME).sendKeys(firstname);
    }

    public void enterLastName(String lastname) {
        driver.findElement(InformationPageUI.LAST_NAME).sendKeys(lastname);
    }

    public void enterZipCode(String zipcode) {
        driver.findElement(InformationPageUI.ZIP_CODE).sendKeys(zipcode);
    }

    public void clickContinue() {
        wait.until(ExpectedConditions.elementToBeClickable(InformationPageUI.CONTINUE_BUTTON)).click();
    }

    public void clickCancel() {
        wait.until(ExpectedConditions.elementToBeClickable(InformationPageUI.CANCEL_BUTTON)).click();
    }

    public WebElement getErrorMessage() {
        return driver.findElement(InformationPageUI.ERROR_MESSAGE);
    }

    public void fillFormAndContinue() {
        enterFirstName("Hong");
        enterLastName("Nhung");
        enterZipCode("2511");
        clickContinue();
    }
}