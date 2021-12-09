package BaseTest;

import configuration.BrowserEnvironment;
import configuration.EnvironmentProperty;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;

public class BaseTest {
    private static BrowserEnvironment browserEnvironment;
    private static EnvironmentProperty environmentProperty;

    protected WebDriver driver;

    @BeforeAll //raz przed wszystkimi testami
    static void setDriver() {
        environmentProperty = EnvironmentProperty.getInstance();
        browserEnvironment = new BrowserEnvironment();
    }

    @BeforeEach
        //raz przed każdym testem
    void setup() {
        driver = browserEnvironment.getDriver();
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

}
