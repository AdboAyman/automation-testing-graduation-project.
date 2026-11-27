
package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    private final By usernameField = By.name("username");
    private final By passwordField = By.name("password");
    private final By loginButton = By.cssSelector("button[type='submit']");

    private final By errorMessage =
            By.cssSelector(".oxd-alert-content-text");

    private final By requiredMessages =
            By.cssSelector(".oxd-input-field-error-message");


    public LoginPage(WebDriver driver) {
        super(driver);
    }


    public void enterUsername(String username) {
        type(usernameField, username);
    }


    public void enterPassword(String password) {
        type(passwordField, password);
    }


    public void clickLogin() {
        click(loginButton);
    }


    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }


    public String getErrorMessage() {
        return getText(errorMessage);
    }


    public boolean isErrorMessageDisplayed() {
        return isDisplayed(errorMessage);
    }


    public int getRequiredMessagesCount() {
        waitForVisibility(requiredMessages);
        return driver.findElements(requiredMessages).size();
    }


    public boolean areRequiredMessagesDisplayed() {
        return getRequiredMessagesCount() == 2;
    }
}
