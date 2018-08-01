package steps;

import agent_side_pages.AgentHomePage;
import agent_side_pages.AgentLoginPage;
import agent_side_pages.UIElements.LeftMenuWithChats;
import agent_side_pages.UIElements.ProfileWindow;
import api_helper.ApiHelper;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import dataManager.FacebookUsers;
import dataManager.Tenants;
import dataManager.TwitterUsers;
import interfaces.JSHelper;
import io.restassured.response.Response;
import org.openqa.selenium.WebDriverException;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DefaultAgentSteps implements JSHelper {
    private AgentHomePage agentHomePage;
    private AgentHomePage secondAgentHomePage;
    private ProfileWindow profileWindow;
    private LeftMenuWithChats leftMenuWithChats;

    @Given("^I login as (.*) of (.*)")
    public void loginAsAgentForTenant(String ordinalAgentNumber, String tenantOrgName){
        AgentLoginPage.openAgentLoginPage(ordinalAgentNumber, tenantOrgName).loginAsAgentOf(tenantOrgName, ordinalAgentNumber);
                Assert.assertTrue(getAgentHomePage(ordinalAgentNumber).isAgentSuccessfullyLoggedIn(ordinalAgentNumber), "Agent is not logged in.");
    }

    @When("I login with the same credentials in another browser as an agent of (.*)")
    public void loginWithTheSameCreds(String tenantOrgName){
        AgentLoginPage.openAgentLoginPage("second agent", tenantOrgName).loginAsAgentOf(tenantOrgName, "main agent");
        Assert.assertTrue(getAgentHomePage("second agent").isAgentSuccessfullyLoggedIn("second agent"), "Agent is not logged in.");
    }

    @Then("^In the first browser Connection Error should be shown$")
    public void verifyAgentIsDisconnected(){
        Assert.assertTrue(getAgentHomePage("first agent").isConnectionErrorShown("first agent"),
                "Agent in the first browser is not disconnected");
    }

    @Given("^(.*) tenant feature is set to (.*) for (.*)$")
    public void setFeatureStatus(String feature, String status, String tenantOrgName){
        ApiHelper.updateFeatureStatus(tenantOrgName, feature, status);
    }

    @Then("^Icon should contain (.*) agent's initials$")
    public void verifyUserInitials(String tenantOrgName){
        Response agentInfoResp = Tenants.getPrimaryAgentInfoForTenant(tenantOrgName);
        String expectedInitials = Character.toString(agentInfoResp.getBody().jsonPath().get("firstName").toString().charAt(0)) +
                Character.toString(agentInfoResp.getBody().jsonPath().get("lastName").toString().charAt(0));

        Assert.assertEquals(agentHomePage.getHeader().getTextFromIcon(), expectedInitials, "Agent initials is not as expected");
    }

    @When("^I click icon with initials$")
    public void clickIconWithInitials(){
        agentHomePage.getHeader().clickIconWithInitials();
    }

    @Then("^I see (.*) agent's info$")
    public void verifyAgentInfoInInfoPopup(String tenantOrgName){
        SoftAssert soft = new SoftAssert();
        Response agentInfoResp = Tenants.getPrimaryAgentInfoForTenant(tenantOrgName);
        String agentName = agentInfoResp.getBody().jsonPath().get("firstName") + " "
                + agentInfoResp.getBody().jsonPath().get("lastName");
        soft.assertEquals(agentHomePage.getHeader().getAgentName(), agentName,
                "Agent name is not as expected");
        soft.assertEquals(agentHomePage.getHeader().getAgentEmail(), agentInfoResp.getBody().jsonPath().get("email"),
                "Agent name is not as expected");
        soft.assertAll();
    }

    @When("^I click \"Profile Settings\" button$")
    public void clickProfileSettingsButton(){
        agentHomePage.getHeader().clickProfileSettingsButton();
    }


    @Then("^(.*) Agent's info derails is shown in profile window$")
    public void verifyAgentInfoInProfileWindow(String tenantOrgName){
        Response agentInfoResp = Tenants.getPrimaryAgentInfoForTenant(tenantOrgName);
        SoftAssert soft = new SoftAssert();
        List<String> expected=new ArrayList<>();
        agentInfoResp.getBody().jsonPath().getList("roles", Map.class).stream().map(e -> ((Map) e)).forEach(e -> {expected.add(
                e.get("name").toString().toUpperCase() + " ("+
                        e.get("solution").toString().toUpperCase() +        ")"
        );});
        soft.assertTrue(getProfileWindow("agent").isAgentInfoShown(agentInfoResp.getBody().jsonPath().get("firstName")),
                "Agent first name is not shown in profile window");
        soft.assertTrue(getProfileWindow("agent").isAgentInfoShown(agentInfoResp.getBody().jsonPath().get("lastName")),
                "Agent last name is not shown in profile window");
        soft.assertTrue(getProfileWindow("agent").isAgentInfoShown(agentInfoResp.getBody().jsonPath().get("email")),
                "Agent email is not shown in profile window");
        soft.assertEquals(getProfileWindow("agent").getListOfRoles(), expected,
                "Agent roles listed in Profile window are not as expected");
        soft.assertAll();
    }

    @Then("^(.*) is logged in chat desk$")
    public void verifyAgentLoggedIn(String ordinalAgentNumber){
        agentHomePage = new AgentHomePage(ordinalAgentNumber);
        Assert.assertTrue(agentHomePage.isAgentSuccessfullyLoggedIn(ordinalAgentNumber), "Agent is not logged in chat desk.");

    }

    @When("^Agent transfers chat$")
    public void transferChat(){
        getAgentHomeForMainAgent().clickTransferButton();
        getAgentHomeForMainAgent().getTransferChatWindow().transferChat();
    }

    @Then("Second agent receives incoming transfer with \"(.*)\" note from the first agent")
    public void verifyIncomingTransferReceived(String notes){
        Assert.assertEquals(getAgentHomeForSecondAgent().getIncomingTransferWindow().getTransferNotes(), notes,
                "Notes in incoming transfer window is not as added by the first agent");
    }

    @Then("(.*) can see transferring agent name, user name and following user's message: '(.*)'")
    public void verifyIncomingTransferDetails(String agent, String userMessage){
        SoftAssert soft = new SoftAssert();
        String expectedUserName = getUserNameFromLocalStorage();
        Response agentInfoResp = Tenants.getPrimaryAgentInfoForTenant(Tenants.getTenantUnderTestOrgName());
        String expectedAgentNAme = agentInfoResp.getBody().jsonPath().get("firstName") + " "+
                agentInfoResp.getBody().jsonPath().get("lastName");

        soft.assertEquals(getAgentHomePage(agent).getIncomingTransferWindow().getClientName(), expectedUserName,
                "User name in Incoming transfer window is not as expected");
        soft.assertEquals(getAgentHomePage(agent).getIncomingTransferWindow().getClientMessage(), userMessage,
                "User message in Incoming transfer window is not as expected");
        soft.assertEquals(getAgentHomePage(agent).getIncomingTransferWindow().getFromAgentName(), expectedAgentNAme,
                "Transferring agent name in Incoming transfer window is not as expected");
        soft.assertAll();
    }

    @Then("^Second agent click \"Accept transfer\" button$")
    public void acceptIncomingTransfer(){
        getAgentHomeForSecondAgent().getIncomingTransferWindow().acceptTransfer();
    }

    @Then("^(.*) has new conversation request$")
    public void verifyIfAgentReceivesConversationRequest(String agent) {
        Assert.assertTrue(getLeftMenu(agent).isNewConversationRequestIsShown(30, agent),
                "There is no new conversation request on Agent Desk (Client ID: "+getUserNameFromLocalStorage()+")\n" +
                        "Number of logged in agents: " + ApiHelper.getNumberOfLoggedInAgents() +"\n");
    }

    @Then("^Agent select \"(.*)\" filter option$")
    public void selectFilterOption(String option){
        getLeftMenu("main agent").selectFilterOption(option);
    }

    @Then("^(.*) has new conversation request from twitter user$")
    public void verifyAgentHasRequestFormTwitterUser(String agent){
                agentHomePage = getAgentHomePage(agent);
                leftMenuWithChats = agentHomePage.getLeftMenuWithChats();
                Assert.assertTrue(leftMenuWithChats.isNewConversationRequestFromSocialIsShown(TwitterUsers.getLoggedInUserName(),60),
                                "There is no new conversation request on Agent Desk (Client ID: "+getUserNameFromLocalStorage()+")");
            }

    @Then("^(.*) should not see from user chat in agent desk$")
    public void verifyConversationRemovedFromChatDesk(String agent){
        Assert.assertTrue(getLeftMenu(agent).isConversationRequestIsRemoved(10),
                "Conversation request is not removed from Agent Desk (Client ID: "+getUserNameFromLocalStorage()+")"
        );
    }

    @When("^(.*) click on new conversation request from (.*)$")
    public void acceptUserFromSocialConversation(String agent, String socialChannel) {
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
        getLeftMenu(agent).openNewConversationRequest(userName);
    }



    @When("^(.*) click on new conversation$")
    public void acceptUserConversation(String ordinalAgentNumber) {
        getLeftMenu(ordinalAgentNumber).openNewConversationRequest();
    }

    @When("^(.*) changes status to: (.*)$")
    public void changeAgentStatus(String agent, String newStatus){
        try {
            getAgentHomePage(agent).getHeader().clickIconWithInitials();
            getAgentHomePage(agent).getHeader().selectStatus(newStatus);
            getAgentHomePage(agent).getHeader().clickIconWithInitials();
        } catch (WebDriverException e) {
            Assert.assertTrue(false, "Unable to change agent status. Please check the screenshot.");
        }
    }

    private AgentHomePage getAgentHomePage(String ordinalAgentNumber){
        if (ordinalAgentNumber.equalsIgnoreCase("second agent")){
            return getAgentHomeForSecondAgent();
        } else {
            return getAgentHomeForMainAgent();
        }
    }

    private AgentHomePage getAgentHomeForSecondAgent(){
        if (secondAgentHomePage==null) {
            secondAgentHomePage = new AgentHomePage("second agent");
            return secondAgentHomePage;
        } else{
            return secondAgentHomePage;
        }
    }

    private AgentHomePage getAgentHomeForMainAgent(){
        if (agentHomePage==null) {
            agentHomePage = new AgentHomePage("main agent");
            return agentHomePage;
        } else{
            return agentHomePage;
        }
    }

    private ProfileWindow getProfileWindow(String ordinalAgentNumber){
        if (profileWindow==null) {
            profileWindow = getAgentHomePage(ordinalAgentNumber).getProfileWindow();
            return profileWindow;
        } else{
            return profileWindow;
        }
    }


    private LeftMenuWithChats getLeftMenu(String agent) {
        return getAgentHomePage(agent).getLeftMenuWithChats();
    }
}
