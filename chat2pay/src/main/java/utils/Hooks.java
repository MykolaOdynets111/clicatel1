package utils;

import io.cucumber.java.After;
import steps.GeneralSteps;

public class Hooks extends GeneralSteps {

    @After
    public void afterScenario() {
        clearTestData();
    }
}