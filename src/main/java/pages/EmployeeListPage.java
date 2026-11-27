
package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class EmployeeListPage extends BasePage {

    // Locators
    private final By employeeNameField =
            By.xpath("//label[normalize-space()='Employee Name']/../following-sibling::div//input");

    private final By employeeIdField =
            By.xpath("//label[normalize-space()='Employee Id']/../following-sibling::div//input");

    private final By suggestions =
            By.cssSelector(".oxd-autocomplete-option");

    private final By searchButton =
            By.xpath("//button[@type='submit']");

    private final By resetButton =
            By.xpath("//button[normalize-space()='Reset']");

    private final By resultRows =
            By.cssSelector(".oxd-table-body .oxd-table-row");

    private final By noRecordsMessage =
            By.xpath("//*[contains(normalize-space(),'No Records Found')]");

    // Constructor
    public EmployeeListPage(org.openqa.selenium.WebDriver driver) {
        super(driver);
    }

    // Search employee by name
    public void searchByEmployeeName(String employeeName) {

        type(employeeNameField, employeeName);

        WebDriverWait shortWait =
                new WebDriverWait(driver, Duration.ofSeconds(5));

        try {
            // Wait for autocomplete suggestions
            shortWait.until(
                    ExpectedConditions.visibilityOfElementLocated(suggestions)
            );

            // Prefer the matching suggestion. OrangeHRM requires an
            // autocomplete selection before the employee-name filter works,
            // so retain its first-suggestion fallback for partial labels.
            String expectedName = normalize(employeeName);
            List<WebElement> options = driver.findElements(suggestions);
            boolean suggestionSelected = false;
            for (WebElement option : options) {
                try {
                    if (normalize(option.getText()).contains(expectedName)) {
                        option.click();
                        suggestionSelected = true;
                        break;
                    }
                } catch (StaleElementReferenceException ignored) {
                    break;
                }
            }

            if (!suggestionSelected && !options.isEmpty()) {
                try {
                    options.get(0).click();
                } catch (StaleElementReferenceException ignored) {
                    // Search with the entered text if autocomplete refreshed.
                }
            }

        } catch (org.openqa.selenium.TimeoutException e) {
            // No autocomplete suggestion was displayed.
            // Continue with the entered text.
        }

        // Submit the search
        click(searchButton);

        // Wait for the search results to update
        WebDriverWait resultsWait =
                new WebDriverWait(driver, Duration.ofSeconds(10));

        resultsWait.until(d -> {
            try {
                // OrangeHRM replaces the table body asynchronously after a search.
                // Read fresh elements on every poll and retry if the old table is detached.
                boolean employeeFound = d.findElements(resultRows).stream()
                        .anyMatch(row -> row.getText()
                                .toLowerCase()
                                .contains(employeeName.toLowerCase()));

                boolean noRecords = d.findElements(noRecordsMessage).stream()
                        .anyMatch(WebElement::isDisplayed);

                return employeeFound || noRecords;
            } catch (StaleElementReferenceException ignored) {
                return false;
            }
        });
    }

    private String normalize(String value) {
        return value.trim().replaceAll("\\s+", " ").toLowerCase();
    }

    // Search by employee ID
    public void searchByEmployeeId(String employeeId) {
        type(employeeIdField, employeeId);
        click(searchButton);

        wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(resultRows),
                ExpectedConditions.visibilityOfElementLocated(noRecordsMessage)
        ));
    }

    // Reset search filters
    public void resetSearch() {
        click(resetButton);
    }

    // Get number of search results
    public int getResultsCount() {

        wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(resultRows),
                ExpectedConditions.visibilityOfElementLocated(noRecordsMessage)
        ));

        return driver.findElements(resultRows).size();
    }

    // Check if no records were found
    public boolean isNoRecordsFound() {

        try {
            wait.until(
                    ExpectedConditions.visibilityOfElementLocated(noRecordsMessage)
            );
            return true;

        } catch (org.openqa.selenium.TimeoutException e) {
            return false;
        }
    }

    // Check if search results exist
    public boolean hasSearchResults() {
        return getResultsCount() > 0;
    }

    // Get all search result rows
    public List<WebElement> getSearchResults() {
        return driver.findElements(resultRows);
    }

    // Check if employee exists in search results
    public boolean isEmployeeInResults(String employeeName) {
        return wait.until(d -> {
            try {
                return d.findElements(resultRows).stream()
                        .anyMatch(row -> row.getText()
                                .toLowerCase()
                                .contains(employeeName.toLowerCase()));
            } catch (StaleElementReferenceException ignored) {
                return false;
            }
        });
    }
}
