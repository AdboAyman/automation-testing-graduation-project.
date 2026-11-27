
package tests;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import base.BaseTest;
import com.fasterxml.jackson.databind.JsonNode;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pages.LoginPage;
import pages.DashboardPage;
import utils.JsonReader;
@Feature("Authentication")
public class LoginTest extends BaseTest {


    @DataProvider(name = "loginData")
    public Object[][] loginData() {
        return JsonReader.getData("loginData");
    }


    @Test(
            dataProvider = "loginData",
            description = "Verify OrangeHRM login with different credentials"
    )
    @Story("Login functionality")
    @Description("Verify valid login, invalid login, and empty credentials.")
    public void verifyLogin(JsonNode testData) {

        String username = testData.get("username").asText();
        String password = testData.get("password").asText();
        String expected = testData.get("expected").asText();

        LoginPage loginPage = new LoginPage(driver);

        loginPage.login(username, password);

        switch (expected) {

            case "valid":

                DashboardPage dashboardPage =
                        new DashboardPage(driver);

                Assert.assertTrue(
                        dashboardPage.isDashboardDisplayed(),
                        "Dashboard should be displayed after valid login"
                );

                Assert.assertTrue(
                        driver.getCurrentUrl().contains("/dashboard/index"),
                        "URL should contain /dashboard/index"
                );

                break;

            case "invalid":

                Assert.assertTrue(
                        loginPage.isErrorMessageDisplayed(),
                        "Invalid credentials message should be displayed"
                );

                Assert.assertEquals(
                        loginPage.getErrorMessage(),
                        "Invalid credentials"
                );

                break;

            case "empty":

                Assert.assertTrue(
                        loginPage.areRequiredMessagesDisplayed(),
                        "Required messages should appear under both fields"
                );

                break;

            default:

                Assert.fail("Unknown login test data: " + expected);
        }
    }
}
