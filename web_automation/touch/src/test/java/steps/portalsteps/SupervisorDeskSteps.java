package steps.portalsteps;

import agentpages.uielements.ChatBody;
import apihelper.ApiHelper;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import datamanager.Tenants;
import dbmanager.DBConnector;
import drivermanager.ConfigManager;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import steps.agentsteps.AgentConversationSteps;
import steps.dotcontrol.DotControlSteps;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SupervisorDeskSteps extends AbstractPortalSteps {

    private List<String> shownUsers = new ArrayList<>();

    @Given("^autoSchedulingEnabled is set to false$")
    public void turnOffAutoScheduling(){
        ApiHelper.updateTenantConfig(Tenants.getTenantUnderTestOrgName(), "autoSchedulingEnabled", "false");
    }

    @Given("^autoSchedulingEnabled is set to true$")
    public void turnOnAutoScheduling(){
        ApiHelper.updateTenantConfig(Tenants.getTenantUnderTestOrgName(), "autoSchedulingEnabled", "true");
    }

    @Then("^Filter \"(.*)\" is selected by default$")
    public void filterIsSelectedByDefault(String filterName) {
        Assert.assertEquals(getChatConsoleInboxPage().getSupervisorLeftPanel().getFilterByDefaultName(), filterName,
                "Filter name by default does not match expected");
    }

    @Then("^Select dot control ticket checkbox$")
    public void clickThreeDotsButton(){
        getChatConsoleInboxPage().getSupervisorTicketsTable().selectTicketCheckbox(DotControlSteps.getClient());
    }

    @When("Click 'Route to scheduler' button")
    public void clickRouteToScheduler(){
        getChatConsoleInboxPage().clickRouteToSchedulerButton();
    }

    @When("Click 'Assign manually' button")
    public void clickAssignManually(){
        getChatConsoleInboxPage().getSupervisorTicketsTable().clickAssignManuallyButton();
    }

    @Then("^Supervisor Desk Live has new conversation (.*) request$")
    public void verifySupervisorDeskHasRequestFormSocialUser(String channel){
        String userName = getUserName(channel);
        Assert.assertTrue(getChatConsoleInboxPage().isLiveChatShownInSD(userName, 5),
                "There is no Live chat on Supervisor Desk Live (Client ID: "+userName+")");
    }

    @And("Agent click On Live Supervisor Desk chat from (.*) channel")
    public void clickOnLiveChat(String channel){
        getChatConsoleInboxPage().getSupervisorDeskLiveRow(getUserName(channel)).clickOnUserName();
    }

    @Then("Supervisor Desk Live chat container header has (.*) User photo, name and (.*) channel")
    public void verifyCorrectHeaderInfo(String channel, String image){
        String userName = getUserName(channel);
        SoftAssert soft =new SoftAssert();
        soft.assertTrue(getChatConsoleInboxPage().getChatHeader().isAvatarContainUserNameFirstLetter(userName),
                "Header Avatar does not contain first letter of the user name: " + userName);
        soft.assertEquals(getChatConsoleInboxPage().getChatHeader().getChatHeaderText(), userName,
                "Incorrect Name was shown in chat header");
        soft.assertTrue(getChatConsoleInboxPage().getChatHeader().isValidChannelImg(image),
                "Icon for channel in chat header as not expected");
        soft.assertAll();
    }

    @Then("Supervisor Desk Live chat Profile is displayed")
    public void profileFormIsShown(){
        Assert.assertTrue(getChatConsoleInboxPage().getProfile().isProfilePageDisplayed(),
                "Profile form is not shown");
    }

    @Then("^(.*) is set as 'current agent' for dot control ticket$")
    public void verifyCurrentAgent(String agent){
        Response rest = ApiHelper.getAgentInfo(Tenants.getTenantUnderTestOrgName(), agent);
        String agentName = rest.jsonPath().get("firstName") + " " + rest.jsonPath().get("lastName");
        getChatConsoleInboxPage().getCurrentDriver().navigate().refresh();
        getChatConsoleInboxPage().waitForConnectingDisappear(2,5);
        boolean result = false;
        for (int i = 0; i < 8; i++){
            if(agentName.equals(getChatConsoleInboxPage().getCurrentAgentOfTheChat(DotControlSteps.getClient()))){
                result = true;
                break;
            }
            else waitFor(1000);
        }
        Assert.assertTrue(result, "Agent " +agentName+ " is not set up as 'Current agent'");
    }

    @Then("Ticket is present and has (.*) type")
    public void verifyUnassignedType(String status){
        if (status.equalsIgnoreCase("Unassigned")) {
            Assert.assertTrue(getChatConsoleInboxPage().getCurrentAgentOfTheChat(DotControlSteps.getClient()).equalsIgnoreCase("Unassigned"),
                    "Unassigned ticket should be present");
        } else if (status.equalsIgnoreCase("Assigned")){
            Response rest = ApiHelper.getAgentInfo(Tenants.getTenantUnderTestOrgName(), "agent");
            String agentName = rest.jsonPath().get("firstName") + " " + rest.jsonPath().get("lastName");
            Assert.assertTrue(agentName.equals(getChatConsoleInboxPage().getCurrentAgentOfTheChat(DotControlSteps.getClient())),
                    "Assigned ticket should be present");
        } else if (status.equalsIgnoreCase("Processed") || status.equalsIgnoreCase("Overdue")){
            getChatConsoleInboxPage().getSupervisorDeskLiveRow(DotControlSteps.getClient());
        }
    }

    @Then("Update ticket with (.*) status")
    public void updateTicketStatus(String status){
        DBConnector.updateAgentHistoryTicketStatus(ConfigManager.getEnv(), status, DotControlSteps.getClientId());
    }

    @Then("^'Assign chat' window is opened$")
    public void assignChatWindowOpened(){
        Assert.assertTrue(getChatConsoleInboxPage().isAssignChatWindowOpened()
                , "'Assign chat' window is not opened");
    }

    @When("^I assign chat on (.*)$")
    public void assignChatManually(String agent){
        String agentName = getAgentName(agent);
        getChatConsoleInboxPage().assignChatOnAgent(agentName);
    }

    @Given("^Save shown tickets$")
    public void saveChats() {
        shownUsers = getChatConsoleInboxPage().getSupervisorTicketsTable().getUsersNames();
    }

    @Given("^Supervisor scroll page to the bottom$")
    public void scrollTicketsDown(){
        getChatConsoleInboxPage().scrollTicketsDown();
    }

    @Given("^More tickets are loaded$")
    public void moreRecordsAreShown(){
        SoftAssert soft = new SoftAssert();
        boolean result = getChatConsoleInboxPage().areNewChatsLoaded(shownUsers.size(), 4);
        soft.assertTrue(result, "New chats are not loaded\n");
        soft.assertAll();
    }

    private String getAgentName(String agent){
        Response rest = ApiHelper.getAgentInfo(Tenants.getTenantUnderTestOrgName(), agent);
        return rest.jsonPath().get("firstName") + " " + rest.jsonPath().get("lastName");
    }

    @When("User select (.*) conversation type")
    public void selectConversationType(String type){
        getChatConsoleInboxPage().selectConversationType(type);
    }

    @Then("Verify (.*) ticket types available in dropdown on Inbox")
    public void verifyTicketTypes(List<String> ticketTypes){
        Assert.assertEquals(getChatConsoleInboxPage().getTicketTypes(), ticketTypes, "Ticket types are different");
    }

    @When("User select (.*) ticket type")
    public void selectTicketType(String type){
        getChatConsoleInboxPage().selectTicketType(type);
    }

    @Then("Verify that live chat is displayed with (.*) message to agent")
    public void verifyLiveChatPresent(String message){
        Assert.assertTrue(getChatConsoleInboxPage().openInboxChatBody(DotControlSteps.getClient()).isUserMessageShown(message), "Messages is not the same");
    }


    @When("Verify that correct messages and timestamps are shown on Chat View")
    public void openChatView(){
        SoftAssert soft = new SoftAssert();
        List<String> messagesFromChatBody = AgentConversationSteps.getMessagesFromChatBody().get();
        ChatBody inboxChatBody = getChatConsoleInboxPage().openInboxChatBody(DotControlSteps.getClient());
        messagesFromChatBody.removeAll(Collections.singleton(""));
        soft.assertEquals(inboxChatBody.getAllMessages(), messagesFromChatBody
                , "Incorrect messages with times were shown on Chat view");
        AgentConversationSteps.getMessagesFromChatBody().remove();
        soft.assertAll();
    }

}
