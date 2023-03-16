package steps;

import driverfactory.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;


public class Hooks {

    @Before
    public void beforeScenario() {
        System.out.println("Before hooks Unity");
    }

    @After
    public void afterScenario() {
        System.out.println("After hooks Unity");
        if (DriverFactory.isTouchDriverExists()) {
            DriverFactory.closeTouchBrowser();
        }
    }
}
