package steps;

import driverfactory.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;


public class Hooks extends GeneralSteps{

    @Before
    public void beforeScenario(Scenario scenario){
        //TODO add precondition steps for Chat2Pay functionality
        System.out.println("Before hooks Chat 2 Pay");
    }

    @After
    public void afterScenario(Scenario scenario){

        System.out.println("After hooks Chat 2 Pay");

        if (DriverFactory.isTouchDriverExists()){
            DriverFactory.closeTouchBrowser();
        }
        if (createdWidgetId.get() != null) {
            createdWidgetId.remove();
        }
    }
}
