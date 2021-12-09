import BaseTest.BaseTest;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@Execution(ExecutionMode.CONCURRENT)
public class TitleTest extends BaseTest {

    String expectedTitle;

    @Test
    public void pageTitleTest() {
        expectedTitle = System.getProperty("title");
        assertThat(driver.getTitle(), equalTo(expectedTitle));
    }
}