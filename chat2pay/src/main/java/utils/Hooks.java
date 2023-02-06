package utils;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import steps.GeneralSteps;

public class Hooks extends GeneralSteps {

    @Before
    public void beforeScenario() {
        //TODO add precondition steps for Chat2Pay functionality
        System.out.println("Before hooks Chat 2 Pay");
    }

    @After
    public void afterScenario() {

        System.out.println("After hooks Chat 2 Pay");
        if (createdWidgetId.get() != null) {
            createdWidgetId.remove();
        }
    }
}
