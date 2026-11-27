
package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class UITestPage extends BasePage {


    private final By menuItems =
            By.cssSelector(".oxd-main-menu-item");


    private final By footer =
            By.cssSelector(".oxd-layout-footer");

    private final By footerLink =
            By.cssSelector(".oxd-layout-footer a");


    public UITestPage(WebDriver driver) {
        super(driver);
    }


    public List<String> getSidebarMenuNames() {

        waitForVisibility(menuItems);

        List<String> menuNames = new ArrayList<>();

        for (WebElement element : driver.findElements(menuItems)) {
            String name = element.getText().trim();

            if (!name.isEmpty()) {
                menuNames.add(name);
            }
        }

        return menuNames;
    }


    public boolean containsRequiredMenus() {

        List<String> actualMenus = getSidebarMenuNames();

        List<String> expectedMenus = List.of(
                "Admin",
                "PIM",
                "Leave",
                "Time",
                "Recruitment",
                "My Info",
                "Performance",
                "Dashboard",
                "Directory"
        );

        return actualMenus.containsAll(expectedMenus);
    }


    public void scrollToFooter() {

        WebElement footerElement = waitForVisibility(footer);

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView(true);",
                footerElement
        );
    }


    public String getFooterText() {
        return getText(footer);
    }


    public boolean isFooterBrandingDisplayed() {
        scrollToFooter();

        return getFooterText().contains("OrangeHRM, Inc");
    }


    public void clickFooterLink() {
        scrollToFooter();
        click(footerLink);
    }


    public Set<String> getWindowHandles() {
        return driver.getWindowHandles();
    }


    public void switchToNewTab(String originalWindow) {

        wait.until(d ->
                d.getWindowHandles().size() > 1
        );

        for (String window : driver.getWindowHandles()) {

            if (!window.equals(originalWindow)) {
                driver.switchTo().window(window);
                break;
            }
        }
    }


    public boolean isOrangeHRMWebsiteOpened() {

        wait.until(d ->
                d.getCurrentUrl().contains("orangehrm.com")
        );

        return driver.getCurrentUrl().contains("orangehrm.com");
    }
}
