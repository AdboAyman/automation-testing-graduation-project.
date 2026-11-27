
package base;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import utils.ConfigReader;

public class BaseTest {

    protected WebDriver driver;

    @BeforeMethod
    public void setUp() {


        DriverManager.initializeDriver();


        driver = DriverManager.getDriver();


        driver.get(ConfigReader.getProperty("base.url"));
    }

    @AfterMethod
    public void tearDown() {


        DriverManager.quitDriver();

        driver = null;
    }
}
