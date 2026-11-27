
package pages;

import org.openqa.selenium.By;

import utils.ConfigReader;

public class PIMPage extends BasePage {

    private final By pimMenu =
            By.xpath("//span[normalize-space()='PIM']");

    private final By pimHeader =
            By.xpath("//h6[normalize-space()='PIM']");

    public PIMPage(org.openqa.selenium.WebDriver driver) {
        super(driver);
    }

    // Open PIM from the sidebar
    public void openPIM() {

        click(pimMenu);

        waitForUrlContains("/pim/viewEmployeeList");

        waitForVisibility(pimHeader);
    }

    // PIM opens directly on Employee List
    public void openEmployeeList() {

        waitForUrlContains("/pim/viewEmployeeList");
    }

    // Navigate to Add Employee
    public void openAddEmployee() {

        String addEmployeeUrl =
                ConfigReader.getProperty("base.url") + "pim/addEmployee";

        driver.get(addEmployeeUrl);

        waitForUrlContains("/pim/addEmployee");
        new AddEmployeePage(driver).waitForAddEmployeeForm();
    }

    public boolean isPIMDisplayed() {

        return isDisplayed(pimHeader);
    }

    public boolean isEmployeeListPageOpened() {

        return driver.getCurrentUrl()
                .contains("/pim/viewEmployeeList");
    }

    public boolean isAddEmployeePageOpened() {

        return driver.getCurrentUrl()
                .contains("/pim/addEmployee");
    }
}
