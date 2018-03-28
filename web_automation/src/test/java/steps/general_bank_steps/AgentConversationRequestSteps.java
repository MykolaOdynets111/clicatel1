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
        agentHomePage = getAgentHomePage();
        leftMenuWithChats = agentHomePage.getLeftMenuWithChats();
        Assert.assertTrue(leftMenuWithChats.isNewConversationRequestFromTouchIsShown(10),
                "There is no new conversation request on Agent Desk (Client ID: "+getUserNameFromLocalStorage()+")");
    }

    @Then("^Agent has new conversation request from FB user$")
    public void verifyIfAgentReceivesConversationRequestFromFB() {
        agentHomePage = getAgentHomePage();
        leftMenuWithChats = agentHomePage.getLeftMenuWithChats();
        Assert.assertTrue(leftMenuWithChats.isNewConversationRequestFromFBIsShown(10),
                "There is no new conversation request on Agent Desk (Client ID: "+getUserNameFromLocalStorage()+")");
    }

    @When("^Agent click on new conversation$")
    public void acceptUserConversation() {
        leftMenuWithChats.openNewConversationRequest();
    }

    public AgentHomePage getAgentHomePage() {
        if (agentHomePage==null) {
            agentHomePage = new AgentHomePage();
            return agentHomePage;
        } else{
            return agentHomePage;
        }
    }



}
