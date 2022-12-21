package steps;

import driverfactory.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;


public class Hooks {

    @Before
    public void beforeScenario(Scenario scenario){
        //TODO add precondition steps for ChatHub functionality
        System.out.println("Before hooks Unity");
    }

    @After()
    public void afterScenario(Scenario scenario){

        System.out.println("After hooks Unity");

        if (DriverFactory.isTouchDriverExists()){
            DriverFactory.closeTouchBrowser();
        }
    }
}
