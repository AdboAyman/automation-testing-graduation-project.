
package tests;

import base.BaseTest;

import org.testng.Assert;
import org.testng.annotations.Test;

import pages.LoginPage;
import pages.UITestPage;

import java.util.Set;

public class UITest extends BaseTest {


    private void loginAsAdmin() {

        LoginPage loginPage = new LoginPage(driver);

        loginPage.login("Admin", "admin123");
    }


    @Test(description = "Verify footer branding and OrangeHRM external link")
    public void verifyFooterAndExternalLink() {

        loginAsAdmin();

        UITestPage uiTestPage = new UITestPage(driver);


        Assert.assertTrue(
                uiTestPage.isFooterBrandingDisplayed(),
                "Footer should contain OrangeHRM, Inc"
        );


        String originalWindow = driver.getWindowHandle();


        uiTestPage.clickFooterLink();


        uiTestPage.switchToNewTab(originalWindow);


        Assert.assertTrue(
                uiTestPage.isOrangeHRMWebsiteOpened(),
                "External OrangeHRM website should open in a new tab"
        );
    }


    @Test(description = "Verify required sidebar menu items")
    public void verifySidebarMenuItems() {

        loginAsAdmin();

        UITestPage uiTestPage = new UITestPage(driver);

        Assert.assertTrue(
                uiTestPage.containsRequiredMenus(),
                "Sidebar should contain all required menu items"
        );
    }
}
