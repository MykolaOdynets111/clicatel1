package steps.unitysteps;

import io.cucumber.java.en.When;
import steps.agentsteps.AbstractAgentSteps;

public class UnityLoginSteps extends AbstractUnitySteps {

    @When("I login to Unity")
    public void loginToUnity(){
       AbstractUnitySteps.getLoginForChatHub().loginToUnity("chat2payqauser11+echo1@gmail.com","Password#1");
    }


}

