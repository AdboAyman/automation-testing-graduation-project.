
package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AddEmployeePage extends BasePage {


    private final By firstNameField =
            By.name("firstName");

    private final By middleNameField =
            By.name("middleName");

    private final By lastNameField =
            By.name("lastName");


    private final By saveButton =
            By.cssSelector("button[type='submit']");


    private final By requiredMessage =
            By.xpath("//input[@name='firstName']/ancestor::div[contains(@class,'oxd-input-group')]//span[contains(@class,'oxd-input-field-error-message')]");


    private final By personalDetailsHeader =
            By.xpath("//h6[normalize-space()='Personal Details']");


    public AddEmployeePage(WebDriver driver) {
        super(driver);
    }


    public void enterFirstName(String firstName) {
        type(firstNameField, firstName);
    }


    public void enterMiddleName(String middleName) {
        type(middleNameField, middleName);
    }


    public void enterLastName(String lastName) {
        type(lastNameField, lastName);
    }


    public void clickSave() {
        click(saveButton);
    }


    public void addEmployee(String firstName, String lastName) {
        enterFirstName(firstName);
        enterLastName(lastName);
        clickSave();
    }


    public boolean isFirstNameRequiredDisplayed() {
        return isDisplayed(requiredMessage);
    }


    public boolean isPersonalDetailsDisplayed() {
        return isDisplayed(personalDetailsHeader);
    }

    public boolean isAddEmployeeFormDisplayed() {
        return isDisplayed(firstNameField)
                && isDisplayed(lastNameField)
                && isDisplayed(saveButton);
    }

    public boolean waitForAddEmployeeForm() {
        return waitForVisibility(firstNameField).isDisplayed();
    }


    public boolean waitForPersonalDetailsPage() {
        return waitForUrlContains("viewPersonalDetails");
    }
}
