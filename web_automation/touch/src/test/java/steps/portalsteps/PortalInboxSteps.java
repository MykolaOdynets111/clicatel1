package steps.portalsteps;

import apihelper.ApiHelper;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import datamanager.Tenants;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import steps.dotcontrol.DotControlSteps;

import java.util.ArrayList;
import java.util.List;

public class PortalInboxSteps extends AbstractPortalSteps {

    private List<String> shownUsers = new ArrayList<>();

    @Given("^autoSchedulingEnabled is set to false$")
    public void turnOffAutoScheduling(){
        ApiHelper.updateTenantConfig(Tenants.getTenantUnderTestOrgName(), "autoSchedulingEnabled", "false");
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

    @Then("Verify that live chat is displayed with (.*) message to agent")
    public void verifyLiveChatPresent(String message){
        Assert.assertEquals(getChatConsoleInboxPage().openInboxChatBody(DotControlSteps.getClient()).getClientMessageText(), message, "Messages is not the same");
        getChatConsoleInboxPage().exitChatConsoleInbox();
    }

}
