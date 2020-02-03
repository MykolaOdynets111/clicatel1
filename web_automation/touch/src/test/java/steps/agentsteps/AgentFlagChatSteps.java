package steps.agentsteps;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import driverfactory.DriverFactory;
import org.testng.Assert;

public class AgentFlagChatSteps extends AbstractAgentSteps {

    @When("^(.*) click 'Flag chat' button$")
    public void pinChat(String agent){
        getAgentHomePage(agent).getChatHeader().clickFlagChatButton();
    }

    @When("^(.*) click 'Unflag chat' button$")
    public void unpinChat(String agent) {
        getAgentHomePage(agent).getChatHeader().clickUnflagChatButton();
    }

    @Then("^(.*) receives 'pin' error message$")
    public void agentReceivesErrorMessage(String agent) {
        getAgentHomePage(agent).isPinErrorMassageShown(agent);
    }

    @Then("^(.*) sees 'flag' icon in this chat$")
    public void verifyFlagAppearance(String agent){
        Assert.assertTrue(getLeftMenu(agent).isFlagIconShown(),
                "Flag icon is not shown");
    }

    @Then("^(.*) do not see 'flag' icon in this chat$")
    public void verifyFlagNotAppearance(String agent){
        Assert.assertTrue(getLeftMenu(agent).isFlagIconRemoved(),
                "Flag icon is shown");
    }
}
