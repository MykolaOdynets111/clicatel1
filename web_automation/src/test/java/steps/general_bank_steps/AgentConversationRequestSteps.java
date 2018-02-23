package steps.general_bank_steps;

import agent_side_pages.AgentHomePage;
import agent_side_pages.UIElements.LeftMenuWithChats;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.testng.Assert;

public class AgentConversationRequestSteps {

    private AgentHomePage agentHomePage = new AgentHomePage();
    private LeftMenuWithChats leftMenuWithChats = agentHomePage.getLeftMenuWithChats();

    @Then("^Agent has new conversation request$")
    public void verifyIfAgentReceivesConversationRequest() {
        Assert.assertTrue(leftMenuWithChats.isNewConversationRequestIsShown(25),
                "There is no new conversation request on Agent Desk");
    }

    @When("^Agent click on new conversation$")
    public void acceptUserConversation() {
        leftMenuWithChats.openNewConversationRequest();
    }





}
