package action;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ui.CheckoutInformationPage;

import java.time.Duration;

public class CheckoutInformationAction {
    WebDriver driver;
    WebDriverWait wait;

    public CheckoutInformationAction(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    }

    public String getTitle() {
        return driver.findElement(CheckoutInformationPage.PAGE_TITLE).getText();
    }

    public void enterFirstName(String firstname) {
        driver.findElement(CheckoutInformationPage.FIRST_NAME).sendKeys(firstname);
    }

    public void enterLastName(String lastname) {
        driver.findElement(CheckoutInformationPage.LAST_NAME).sendKeys(lastname);
    }

    public void enterZipCode(String zipcode) {
        driver.findElement(CheckoutInformationPage.ZIP_CODE).sendKeys(zipcode);
    }

    public void clickContinue() {
        wait.until(ExpectedConditions.elementToBeClickable(CheckoutInformationPage.CONTINUE_BUTTON)).click();
    }

    public void clickCancel() {
        wait.until(ExpectedConditions.elementToBeClickable(CheckoutInformationPage.CANCEL_BUTTON)).click();
    }

    public WebElement getErrorMessage() {
        return driver.findElement(CheckoutInformationPage.ERROR_MESSAGE);
    }

    public void fillFormAndContinue(String firstName, String lastName, String zipCode) {
        enterFirstName(firstName);
        enterLastName(lastName);
        enterZipCode(zipCode);
        clickContinue();
    }
}