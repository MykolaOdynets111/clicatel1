package steps.general_bank_steps;

import agent_side_pages.AgentHomePage;
import agent_side_pages.UIElements.LeftMenuWithChats;
import api_helper.TwitterAPI;
import api_helper.ApiHelper;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import dataprovider.FacebookUsers;
import dataprovider.TwitterUsers;
import interfaces.JSHelper;
import org.testng.Assert;

public class AgentConversationRequestSteps implements JSHelper{

    private AgentHomePage agentHomePage;
    private LeftMenuWithChats leftMenuWithChats;

    @Then("^Agent has new conversation request$")
    public void verifyIfAgentReceivesConversationRequest() {
        Assert.assertTrue(getLeftMenu().isNewConversationRequestFromTouchIsShown(10),
                "There is no new conversation request on Agent Desk (Client ID: "+getUserNameFromLocalStorage()+")\n" +
                        "Number of logged in agents: " + ApiHelper.getNumberOfLoggedInAgents() +"\n");

    }

    @Then("^Agent has new conversation request from FB user$")
    public void verifyIfAgentReceivesConversationRequestFromFB() {
        agentHomePage = getAgentHomePage();
        leftMenuWithChats = agentHomePage.getLeftMenuWithChats();
        Assert.assertTrue(leftMenuWithChats.isNewConversationRequestFromSocialIsShown(FacebookUsers.getLoggedInUserName(),10),
                "There is no new conversation request on Agent Desk (Client ID: "+getUserNameFromLocalStorage()+")");
    }

    @Then("^Agent has new conversation request from twitter user$")
    public void verifyAgentHasRequestFormTwitterUser(){
        agentHomePage = getAgentHomePage();
        leftMenuWithChats = agentHomePage.getLeftMenuWithChats();
        Assert.assertTrue(leftMenuWithChats.isNewConversationRequestFromSocialIsShown(TwitterUsers.getLoggedInUserName(),40),
                "There is no new conversation request on Agent Desk (Client ID: "+getUserNameFromLocalStorage()+")");
    }

    @Then("^From agent chat should be removed from agent desk$")
    public void verifyConversationRemovedFromChatDesk(){
        Assert.assertTrue(getLeftMenu().isConversationRequestIsRemoved(10),
                "Conversation request is not removed from Agent Desk (Client ID: "+getUserNameFromLocalStorage()+")"
                        );
    }


//    @When("^Agent click on new conversation$")
//    public void acceptUserConversation() {
//        leftMenuWithChats.openNewConversationRequest();
//>>>>>>> develop
//    }

    @When("^Agent click on new conversation request from (.*)$")
    public void acceptUserFromSocialConversation(String socialChannel) {
        String userName=null;
        switch (socialChannel.toLowerCase()){
            case "touch":
                userName = getUserNameFromLocalStorage();
                break;
            case "twitter":
                userName = TwitterUsers.getLoggedInUserName();
                break;
            case "facebook":
                userName = FacebookUsers.getLoggedInUserName();
                break;
        }
        leftMenuWithChats.openNewConversationRequest(userName);
    }


    private AgentHomePage getAgentHomePage() {
        if (agentHomePage==null) {
            agentHomePage =  new AgentHomePage();
            return agentHomePage;
        } else{
            return agentHomePage;
        }
    }

    private LeftMenuWithChats getLeftMenu() {
        if (leftMenuWithChats==null) {
            leftMenuWithChats =  getAgentHomePage().getLeftMenuWithChats();
            return leftMenuWithChats;
        } else{
            return leftMenuWithChats;
        }
    }

}
