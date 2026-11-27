
package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import utils.ConfigReader;

public class DriverManager {

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static void initializeDriver() {

        String browser = ConfigReader.getProperty("browser").toLowerCase();

        switch (browser) {

            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                driver.set(new ChromeDriver(chromeOptions));
                break;

            case "firefox":
                driver.set(new FirefoxDriver());
                break;

            default:
                throw new IllegalArgumentException(
                        "Unsupported browser: " + browser
                );
        }

        getDriver().manage().window().maximize();
    }

    public static WebDriver getDriver() {

        WebDriver currentDriver = driver.get();

        if (currentDriver == null) {
            throw new IllegalStateException(
                    "WebDriver is not initialized for this thread!"
            );
        }

        return currentDriver;
    }

    public static void quitDriver() {

        WebDriver currentDriver = driver.get();

        if (currentDriver != null) {
            currentDriver.quit();
            driver.remove();
        }
    }
}
