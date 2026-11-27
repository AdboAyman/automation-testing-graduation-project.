
package tests;

import base.BaseTest;
import com.fasterxml.jackson.databind.JsonNode;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pages.LoginPage;
import pages.PIMPage;
import pages.AddEmployeePage;
import pages.EmployeeListPage;

import utils.JsonReader;

public class EmployeeTest extends BaseTest {


    private void loginAsAdmin() {

        LoginPage loginPage = new LoginPage(driver);

        loginPage.login("Admin", "admin123");
    }


    private AddEmployeePage navigateToAddEmployee() {

        PIMPage pimPage = new PIMPage(driver);

        pimPage.openPIM();
        pimPage.openAddEmployee();

        return new AddEmployeePage(driver);
    }


    @Test(description = "Verify Add Employee page opens correctly")
    public void verifyAddEmployeePage() {

        loginAsAdmin();

        AddEmployeePage addEmployeePage =
                navigateToAddEmployee();

        Assert.assertTrue(
                addEmployeePage.waitForAddEmployeeForm(),
                "Add Employee form should be ready"
        );

        Assert.assertTrue(
                driver.getCurrentUrl().contains("addEmployee"),
                "URL should contain addEmployee"
        );

        Assert.assertTrue(
                addEmployeePage.isAddEmployeeFormDisplayed(),
                "First Name, Last Name and Save controls should be displayed"
        );
    }


    @Test(description = "Verify First Name required validation")
    public void verifyFirstNameRequiredValidation() {

        loginAsAdmin();

        AddEmployeePage addEmployeePage =
                navigateToAddEmployee();

        addEmployeePage.enterLastName("Smith");
        addEmployeePage.clickSave();

        Assert.assertTrue(
                addEmployeePage.isFirstNameRequiredDisplayed(),
                "First Name required message should be displayed"
        );
    }


    @DataProvider(name = "employeeData")
    public Object[][] employeeData() {

        return JsonReader.getData("employeeData");
    }


    @Test(
            dataProvider = "employeeData",
            description = "Create employee and verify employee appears in search"
    )
    public void verifyEmployeeCreation(JsonNode testData) {

        String firstName =
                testData.get("firstName").asText();

        String lastName =
                testData.get("lastName").asText();


        loginAsAdmin();


        AddEmployeePage addEmployeePage =
                navigateToAddEmployee();


        addEmployeePage.addEmployee(firstName, lastName);


        Assert.assertTrue(
                addEmployeePage.waitForPersonalDetailsPage(),
                "Personal Details page should open after saving"
        );

        Assert.assertTrue(
                addEmployeePage.isPersonalDetailsDisplayed(),
                "Personal Details heading should be displayed"
        );


        PIMPage pimPage = new PIMPage(driver);

        pimPage.openPIM();
        pimPage.openEmployeeList();


        EmployeeListPage employeeListPage =
                new EmployeeListPage(driver);

        employeeListPage.searchByEmployeeName(
                firstName + " " + lastName
        );

        Assert.assertTrue(
                employeeListPage.hasSearchResults(),
                "New employee should appear in search results"
        );

        Assert.assertTrue(
                employeeListPage.isEmployeeInResults(
                        firstName + " " + lastName
                ),
                "Created employee name should appear in results"
        );
    }
}
