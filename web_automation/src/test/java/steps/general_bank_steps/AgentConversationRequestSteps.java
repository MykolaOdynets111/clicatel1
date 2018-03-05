package steps.general_bank_steps;

import agent_side_pages.AgentHomePage;
import agent_side_pages.UIElements.LeftMenuWithChats;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import interfaces.JSHelper;
import org.testng.Assert;

public class AgentConversationRequestSteps implements JSHelper{

    private AgentHomePage agentHomePage;
    private LeftMenuWithChats leftMenuWithChats;

    @Then("^Agent has new conversation request$")
    public void verifyIfAgentReceivesConversationRequest() {
        agentHomePage = new AgentHomePage();
        leftMenuWithChats = agentHomePage.getLeftMenuWithChats();
        Assert.assertTrue(leftMenuWithChats.isNewConversationRequestIsShown(25),
                "There is no new conversation request on Agent Desk (Client ID: "+getUserNameFromLocalStorage()+")");
    }

    @When("^Agent click on new conversation$")
    public void acceptUserConversation() {
        leftMenuWithChats.openNewConversationRequest();
    }





}
