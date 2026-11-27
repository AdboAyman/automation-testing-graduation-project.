
package tests;

import base.BaseTest;

import org.testng.Assert;
import org.testng.annotations.Test;

import pages.LoginPage;
import pages.AdminPage;

public class AdminTest extends BaseTest {

    @Test(
            description = "Verify Add User form contains all required fields"
    )
    public void verifyAddUserForm() {


        LoginPage loginPage = new LoginPage(driver);

        loginPage.login("Admin", "admin123");


        AdminPage adminPage = new AdminPage(driver);

        adminPage.openAdmin();


        adminPage.openUserManagement();


        adminPage.openUsers();


        adminPage.openAddUserForm();


        Assert.assertTrue(
                adminPage.isAddUserFormDisplayed(),
                "Add User form should display User Role, Employee Name, Username and Password"
        );
    }
}
