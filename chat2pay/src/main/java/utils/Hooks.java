package utils;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import steps.GeneralSteps;

public class Hooks extends GeneralSteps {

    @Before
    public void beforeScenario() {
    }

    @After
    public void afterScenario() {
        if (createdWidgetId.get() != null) {
            createdWidgetId.remove();
        }
    }
}
