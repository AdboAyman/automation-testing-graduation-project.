
package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DashboardPage extends BasePage {


    private final By dashboardHeader =
            By.xpath("//h6[text()='Dashboard']");

    private final By sideMenu =
            By.cssSelector(".oxd-sidepanel");

    private final By menuItems =
            By.cssSelector(".oxd-main-menu-item");

    private final By userDropdown =
            By.cssSelector(".oxd-userdropdown-tab");


    public DashboardPage(WebDriver driver) {
        super(driver);
    }


    public boolean isDashboardDisplayed() {
        return isDisplayed(dashboardHeader);
    }


    public String getDashboardHeader() {
        return getText(dashboardHeader);
    }


    public boolean isSideMenuDisplayed() {
        return isDisplayed(sideMenu);
    }


    public java.util.List<String> getMenuNames() {

        waitForVisibility(menuItems);

        return driver.findElements(menuItems)
                .stream()
                .map(element -> element.getText().trim())
                .filter(name -> !name.isEmpty())
                .toList();
    }


    public void openUserDropdown() {
        click(userDropdown);
    }
}
