package endtoend;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.Test;

@Test
public class BaseTest {

    @AfterSuite(alwaysRun = true)
    public void setUpEnvsProp(){

    }
}
