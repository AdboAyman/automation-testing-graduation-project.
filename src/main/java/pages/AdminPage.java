
package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AdminPage extends BasePage {

    private final By adminMenu =
            By.xpath("//span[normalize-space()='Admin']");

    private final By userManagementMenu =
            By.xpath("//span[normalize-space()='User Management']");

    private final By usersMenu =
            By.xpath("//a[contains(@href, '/admin/viewSystemUsers')]");

    private final By addButton =
            By.xpath("//button[normalize-space()='Add']");

    private final By userRoleDropdown =
            By.xpath("//label[normalize-space()='User Role']/../following-sibling::div");

    private final By employeeNameField =
            By.xpath("//input[@placeholder='Type for hints...']");

    private final By usernameField =
            By.xpath("//label[normalize-space()='Username']/../following-sibling::div//input");

    private final By passwordField =
            By.xpath("//label[normalize-space()='Password']/../following-sibling::div//input");

    private final By addUserHeader =
            By.xpath("//h6[normalize-space()='Add User']");

    public AdminPage(WebDriver driver) {
        super(driver);
    }

    public void openAdmin() {
        click(adminMenu);
        waitForUrlContains("/admin/viewSystemUsers");
    }

    public void openUserManagement() {
        if (!isDisplayed(usersMenu)) {
            click(userManagementMenu);
        }
    }

    public void openUsers() {
        // Selecting Admin opens the Users page directly in the current
        // OrangeHRM layout. Avoid waiting for a second, optional menu link.
        if (driver.getCurrentUrl().contains("/admin/viewSystemUsers")) {
            return;
        }

        if (!isDisplayed(usersMenu)) {
            openUserManagement();
        }

        click(usersMenu);
        waitForUrlContains("/admin/viewSystemUsers");
    }

    public void openAddUserForm() {
        click(addButton);
        waitForVisibility(addUserHeader);
    }

    public boolean isAddUserFormDisplayed() {
        return isDisplayed(addUserHeader)
                && isDisplayed(userRoleDropdown)
                && isDisplayed(employeeNameField)
                && isDisplayed(usernameField)
                && isDisplayed(passwordField);
    }
}
