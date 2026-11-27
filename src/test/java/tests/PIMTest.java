
package tests;

import base.BaseTest;
import com.fasterxml.jackson.databind.JsonNode;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pages.LoginPage;
import pages.PIMPage;
import pages.EmployeeListPage;

import utils.JsonReader;

public class PIMTest extends BaseTest {


    private void loginAsAdmin() {

        LoginPage loginPage = new LoginPage(driver);

        loginPage.login("Admin", "admin123");
    }


    private EmployeeListPage navigateToEmployeeList() {

        PIMPage pimPage = new PIMPage(driver);

        pimPage.openPIM();
        pimPage.openEmployeeList();

        return new EmployeeListPage(driver);
    }


    @DataProvider(name = "employeeSearchData")
    public Object[][] employeeSearchData() {

        return JsonReader.getData("employeeSearchData");
    }


    @Test(
            dataProvider = "employeeSearchData",
            description = "Verify employee search functionality"
    )
    public void verifyEmployeeSearch(JsonNode testData) {

        String employeeName =
                testData.get("employeeName").asText();

        String expected =
                testData.get("expected").asText();


        loginAsAdmin();


        EmployeeListPage employeeListPage =
                navigateToEmployeeList();


        employeeListPage.searchByEmployeeName(employeeName);


        if (expected.equals("found")) {

            Assert.assertTrue(
                    employeeListPage.hasSearchResults(),
                    "Employee search should return results"
            );

            Assert.assertTrue(
                    employeeListPage.isEmployeeInResults(employeeName),
                    "Expected employee name should appear in results"
            );

        } else if (expected.equals("notFound")) {

            Assert.assertTrue(
                    employeeListPage.isNoRecordsFound(),
                    "No Records Found should be displayed"
            );

        } else {

            Assert.fail("Unknown expected search result: " + expected);
        }
    }
}
