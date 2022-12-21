package steps.agentsteps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class AgentFlagChatSteps extends AbstractAgentSteps {

    @When("^(.*) click 'Flag chat' button$")
    public void pinChat(String agent){
        getAgentHomePage(agent).getChatHeader().clickFlagChatButton();
    }

    @When("^(.*) click 'Unflag chat' button$")
    public void unpinChat(String agent) {
        getAgentHomePage(agent).getChatHeader().clickflagOnButton();
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
