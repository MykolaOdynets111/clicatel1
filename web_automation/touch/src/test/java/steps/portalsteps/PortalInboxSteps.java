package steps.portalsteps;

import apihelper.ApiHelper;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import datamanager.Tenants;
import dbmanager.DBConnector;
import drivermanager.ConfigManager;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import portaluielem.InboxChatBody;
import steps.agentsteps.AgentConversationSteps;
import steps.dotcontrol.DotControlSteps;

import java.util.ArrayList;
import java.util.List;

public class PortalInboxSteps extends AbstractPortalSteps {

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
        Assert.assertEquals(getChatConsoleInboxPage().getFilterByDefault(),filterName,
                "Filter name by default does not match expected");
    }

    @Then("^Click three dots for dot control ticket$")
    public void clickThreeDotsButton(){
        getChatConsoleInboxPage().clickThreeDotsButton(DotControlSteps.getClient());
    }

    @When("Click 'Route to scheduler' button")
    public void clickRouteToScheduler(){
        getChatConsoleInboxPage().clickRouteToSchedulerButton();
    }

    @When("Click 'Assign manually' button")
    public void clickAssignManually(){
        getChatConsoleInboxPage().clickAssignManuallyButton();
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
            getChatConsoleInboxPage().getChatConsoleInboxRow(DotControlSteps.getClient());
            getChatConsoleInboxPage().exitChatConsoleInbox();
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

    @Given("^Save shown chats$")
    public void saveChats() {
        shownUsers = getChatConsoleInboxPage().getUsersNames();
    }

    @Given("^Click 'Load more' button$")
    public void clickLoadMore(){
        getChatConsoleInboxPage().clickLoadMore();
    }

    @Given("^More records are loaded$")
    public void moreRecordsAreShown(){
        SoftAssert soft = new SoftAssert();
        boolean result = getChatConsoleInboxPage().areNewChatsLoaded(shownUsers.size(), 4);
        List<String> newAllUsers = getChatConsoleInboxPage().getUsersNames();

        soft.assertTrue(result, "New chats are not loaded\n");
        soft.assertEquals(getChatConsoleInboxPage().getNumberOfChats().split("of")[0].trim(),
                "displaying " + newAllUsers.size(),
                "Incorrect size of all chats after loading more\n");
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
        Assert.assertEquals(getChatConsoleInboxPage().openInboxChatBody(DotControlSteps.getClient()).getClientMessageText(), message, "Messages is not the same");
        getChatConsoleInboxPage().exitChatConsoleInbox();
    }


    @When("Verify that (.*) chat status correct last message and timestamp is shown on Chat View")
    public void openChatView(String chatStatus){
        SoftAssert soft = new SoftAssert();
        List<String> messagesFromChatBody = AgentConversationSteps.getMessagesFromChatBody().get();
        InboxChatBody inboxChatBody = getChatConsoleInboxPage().openInboxChatBody(DotControlSteps.getClient());

        soft.assertEquals(inboxChatBody.getChatStatus().toLowerCase(), chatStatus.toLowerCase() + ":"
                , "Incorrect Chat status was shown on Chat view");
        soft.assertEquals(inboxChatBody.getAgentMessageText() + " " + inboxChatBody.getAgentMessageTime(), messagesFromChatBody.get(messagesFromChatBody.size() -1)
                , "Incorrect message with time was shown on Chat view");
        getChatConsoleInboxPage().exitChatConsoleInbox();
        AgentConversationSteps.getMessagesFromChatBody().remove();
        soft.assertAll();
    }

    @Then("Verify that (.*) status is shown for inbox conversation")
    public void verifyCustomerStatus(String status){
        Assert.assertEquals(getChatConsoleInboxPage().getChatConsoleInboxRow(DotControlSteps.getClient()).getStatus().trim(), status, "Incorrect status was shown");
        getChatConsoleInboxPage().exitChatConsoleInbox();
    }

    @Then ("Verify correct information is shown in Customer details and (.*) set as location")
    public void verifyCorrectCustomerInfo(String location){
        SoftAssert soft = new SoftAssert();
        soft.assertEquals(getChatConsoleInboxPage().getChatConsoleInboxRow(DotControlSteps.getClient()).getLocation(), location, "Incorrect location was shown");
        getChatConsoleInboxPage().exitChatConsoleInbox();
        soft.assertEquals(getChatConsoleInboxPage().getChatConsoleInboxRow(DotControlSteps.getClient()).getPhone(), DotControlSteps.getInitContext().getPhone(), "Incorrect phone was shown");
        getChatConsoleInboxPage().exitChatConsoleInbox();
        soft.assertAll();
    }
}
