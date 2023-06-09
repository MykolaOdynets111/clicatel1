package steps.agentsteps;

import agentpages.uielements.IncomingTransferWindow;
import apihelper.ApiHelper;
import datamanager.Tenants;
import datamanager.jacksonschemas.TenantChatPreferences;
import datamanager.jacksonschemas.dotcontrol.DotControlInitRequest;
import drivermanager.ConfigManager;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import steps.FacebookSteps;
import steps.TwitterSteps;
import steps.dotcontrol.DotControlSteps;

import java.util.Arrays;
import java.util.List;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

public class AgentTransferSteps extends AbstractAgentSteps {

    private String secondAgentName;

    @When("^(.*) click on 'Transfer' chat$")
    public void agentClickOnTransferChat(String agent) {
        getAgentHomePage(agent).getChatHeader().clickTransferButton();
        getAgentHomePage(agent).getTransferChatWindow().waitForUpdatingAvailableAgents();
    }

    @Then("^Transfer chat pop up appears for (.*)$")
    public void transferChatPopUpAppears(String agent) {
        Assert.assertTrue(getAgentHomePage(agent).getTransferChatWindow().isTransferChatShown(),"Transfer chat pop up is not appears");
    }

    @When("^(.*) click 'Cancel transfer' button$")
    public void cancelTransferChat(String agent){
        getAgentHomePage(agent).getChatHeader().clickCancelTransferButton();
    }

    @When("^(.*) transfers chat$")
    public void transferChat(String agent){
        getAgentHomePage(agent).getChatHeader().clickTransferButton();
        getAgentHomePage(agent).getTransferChatWindow().waitForUpdatingAvailableAgents();
        secondAgentName = getAgentHomePage(agent).getTransferChatWindow().transferChat(agent);
    }
    @And("^(.*) transfers chat to (.*) department$")
    public void transferChatTodDepartment(String agent, String departmentName){
        getAgentHomePage(agent).getChatHeader().clickTransferButton();
        getAgentHomePage(agent).getTransferChatWindow().waitForUpdatingAvailableAgents();
        Assert.assertTrue(getAgentHomePage(agent).getTransferChatWindow().isTransferChatShown(),"Transfer chat pop up is not appears");
        getAgentHomePage(agent).getTransferChatWindow().transferChatToDepartment(departmentName);
    }

    @And("^(.*) transfers overnight ticket$")
    public void agentTransfersOvernightTicket(String agent) {
        getAgentHomePage(agent).getChatHeader().clickTransferButton();
        getAgentHomePage(agent).getTransferChatWindow().waitForUpdatingAvailableAgents();
        secondAgentName = getAgentHomePage(agent).getTransferChatWindow().transferOvernightTicket(agent);
    }

    @When("^(.*) transfer a few chats$")
    public void transferFewDotControlChats(String agent){
        for(DotControlInitRequest chat : createdChatsViaDotControl) {
            int availableAgents = ApiHelper.getNumberOfLoggedInAgents();
            for(int i = 0; i<11; i ++){
                if(availableAgents<2) {
                    getAgentHomePage(agent).waitFor(1000);
                    availableAgents = ApiHelper.getNumberOfLoggedInAgents();
                } else{
                    break;
                }
            }
            if(availableAgents<2) Assert.fail(
                    "Second agent is not available after waiting 11 seconds after chat transfer");
            getLeftMenu(agent).openChatByUserName(chat.getInitContext().getFullName());
            transferChat(agent);
            getAgentHomePage(agent).waitForModalWindowToDisappear();
        }
    }

    @Then("^(.*) can not click '(.*)' button$")
    public void agentCanNotClickTransferChatButton(String agent, String transferButton) {
        Assert.assertTrue(getAgentHomePage(agent).getChatHeader().isButtonDisabled(transferButton),
                "Transfer chat button is enabled ");
    }

    @When("^Select 'Transfer to' drop down$")
    public void selectTransferToDropDown() {
        getAgentHomeForMainAgent().getTransferChatWindow().openDropDownAgent();
    }

    @When("^(.*) open 'Transfer to' drop down$")
    public void clickTransferToDropDown(String agent) {
        getAgentHomePage(agent).getTransferChatWindow().openDropDownAgent();
    }

    @Then("^Agent notes field is appeared$")
    public void verifyNotesFieldAppears(){
        Assert.assertTrue(getAgentHomePage("first").getTransferChatWindow().isNoteShown(),
                "'Note' input is not shown after selecting agent");
    }

    @Then("^Agent is shown as current chat assignment and disabled for selection$")
    public void agentSeesCurrentlyThereSNoAgentsAvailable() {
        String AgentName = getAgentHomeForMainAgent().getTransferChatWindow().getCurrentAgentAssignment();
        String expectedAgentNAme = Tenants.getPrimaryAgentInfoForTenant(Tenants.getTenantUnderTestOrgName()).get("fullName");
        SoftAssert soft = new SoftAssert();
        soft.assertEquals(AgentName, expectedAgentNAme + "- current chat assignment", "message in drop down menu not as expected");
        soft.assertTrue(getAgentHomeForMainAgent().getTransferChatWindow().isAssignedAgentDisabledToSelect(), "Current chat assignment should be disabled for selection");
        soft.assertAll();
    }

    @Then("Agent sees error message 'Notes are required when specific agent is selected.'")
    public void verifyRequiredNotesField(){
        SoftAssert soft = new SoftAssert();
        soft.assertEquals(getAgentHomeForMainAgent().getTransferChatWindow().getNoteInputErrorText(),
                "Agent notes can not be empty",
                "Error about required notes is not as expected");
        soft.assertEquals(getAgentHomeForMainAgent().getTransferChatWindow().getNoteInputColor(),
                "rgb(194, 51, 19)", "Note input: expected Notes boarder color is not as expected" );
        soft.assertAll();
    }

    @When("^(.*) complete 'Note' field$")
    public void sentNotesTransferChatPopup(String agent) {
        getAgentHomePage(agent).getTransferChatWindow().sentNote();
    }

    @When("^Click on 'Transfer' button in pop-up$")
    public void clickOnTransferButtonInPopUp() {
        getAgentHomeForMainAgent().getTransferChatWindow().clickTransferChatButton();
    }

    @When("^Button 'Transfer chat' is not active$")
    public void isTransferChatActive() {
        Assert.assertFalse(getAgentHomeForMainAgent().getTransferChatWindow().isTransferChatEnabled(),
                "Transfer chat is active");
    }

    @Then("^(.*) has not see incoming transfer pop-up$")
    public void secondAgentHasNotSeeIncomingTransferPopUp(String agent) {
        Assert.assertTrue(getIncomingTransferWindow(agent).isTransferWindowHeaderNotShown(),
                "Transfer chat header is shown for "+ agent + " agent");
    }

    @Then("^(.*) receives incoming transfer with \"(.*)\" header$")
    public void verifyIncomingTransferHeader(String agent, String expectedHeader){
        Assert.assertEquals(getIncomingTransferWindow(agent).getTransferWindowHeader(), expectedHeader,
                "Header in incoming transfer window is not as expected");
    }

    @Then("^(.*) receives incoming transfer with \"(.*)\" note from the another agent$")
    public void verifyIncomingTransferReceived(String agent, String notes){
        Assert.assertEquals(getIncomingTransferWindow(agent).getTransferNotes(), notes,
                "Notes in incoming transfer window is not as added by the first agent");
    }

    @Given("^(.*) receives a few conversation requests$")
    public void create2DotControlChats(String agent){
        SoftAssert soft = new SoftAssert();
        DotControlSteps dotControlSteps = new DotControlSteps();
        dotControlSteps.createIntegration(Tenants.getTenantUnderTestOrgName(), "fbmsg");
        createdChatsViaDotControl.add(dotControlSteps.createOfferToDotControl("connect to agent"));
        DotControlSteps.cleanUPDotControlRequestMessage();
        createdChatsViaDotControl.add(dotControlSteps.createOfferToDotControl("chat to support"));

        soft.assertTrue(getLeftMenu(agent)
                        .isNewConversationIsShown(
                                createdChatsViaDotControl.get(0).getInitContext().getFullName(),30),
                "There is no new conversation request on Agent Desk (Client name: "+createdChatsViaDotControl.get(0).getClientId()+")");
        soft.assertTrue(getLeftMenu(agent)
                        .isNewConversationIsShown(
                                createdChatsViaDotControl.get(1).getInitContext().getFullName(),40),
                "There is no new conversation request on Agent Desk (Client name: "+createdChatsViaDotControl.get(1).getClientId()+")");
        soft.assertAll();
    }

    @Then("^(.*) can see transferring agent name, (.*) and following user's message: '(.*)'$")
    public void verifyIncomingTransferDetails(String agent, String user, String userMessage) {
            SoftAssert soft = new SoftAssert();
            String expectedUserName = getUserName(user);
            if (ConfigManager.getSuite().equalsIgnoreCase("twitter")) {
                userMessage = TwitterSteps.getCurrentConnectToAgentTweetText();
            }
            if(ConfigManager.getSuite().equalsIgnoreCase("facebook")) {
                userMessage = FacebookSteps.getCurrentUserMessageText();
            }
            String expectedAgentNAme = Tenants.getPrimaryAgentInfoForTenant(Tenants.getTenantUnderTestOrgName()).get("fullName");

            soft.assertEquals(getIncomingTransferWindow(agent).getClientName(), expectedUserName,
                    "User name in Incoming transfer window is not as expected");
            soft.assertEquals(getIncomingTransferWindow(agent).getClientMessage(), userMessage,
                    "User message in Incoming transfer window is not as expected");
            soft.assertEquals(getIncomingTransferWindow(agent).getFromAgentName(), expectedAgentNAme,
                    "Transferring agent name in Incoming transfer window is not as expected");
            soft.assertAll();
    }

    @Then("^(.*) receives incoming transfer on the right side of the screen with user's profile picture, channel and sentiment for (.*)")
    public void verifyPictureChannelSentimentTransferWindow(String agent, String integration) {
        SoftAssert softAssert = new SoftAssert();
        String userName = getUserName(integration);
        String firstLetters = Arrays.stream(userName.split(" ")).map(s -> s.substring(0, 1)).reduce("", String::concat);
        softAssert.assertTrue(getIncomingTransferWindow(agent).isValidImgTransferPicture(firstLetters),
                "User picture as not expected");
        softAssert.assertTrue(getIncomingTransferWindow(agent).isSentimentShown(),
                format("Sentiment is not shown for on Transfer Window"));
        softAssert.assertTrue(getIncomingTransferWindow(agent).isRigthSideTransferChatWindow(),
                "Transfered chat window not on the right side of the screen");
        softAssert.assertTrue(getIncomingTransferWindow(agent).isValidImTransferChannel("touchTransfer"),
                "Channel picture as not expected");
        softAssert.assertAll();
    }

    @When("^(.*) receives incoming transfer notification with \"Transfer waiting\" header and collapsed view$")
    public void verifyTransferredChatsCollapsed(String agent){
        Assert.assertEquals(getAgentHomePage(agent).getCollapsedTransfers().size(), 2,
                "Not all expected collapsed transferred chats shown");
    }

    @When("^(.*) click on \"Transfer waiting\" header$")
    public void expandFirstOfCollapsedTransfer(String agent){
        getAgentHomePage(agent).getCollapsedTransfers().get(0).click();
    }

    @Then("^Correct Rejected by field is shown for (.*)$")
    public void verifyRejectedByField(String agent){
        Assert.assertEquals(getIncomingTransferWindow(agent).getRejectedBy(),
                "Rejected by: " + secondAgentName,
                "Header in incoming transfer window is not as expected");
    }

    @Then("^(.*) click \"Accept transfer\" button$")
    public void acceptIncomingTransfer(String agent){
        getIncomingTransferWindow(agent).acceptTransfer();
    }

    @Then("^(.*) click \"Close transfer\" button$")
    public void closeIncomingTransfer(String agent){
        getIncomingTransferWindow(agent).closeTransfer();
    }

    @Then("^(.*) click \"Reject transfer\" button$")
    public void rejectIncomingTransfer(String agent){
        getIncomingTransferWindow(agent).rejectTransfer();
    }

    @Then("^(.*) click \"Accept\" button$")
    public void acceptRejectedTransfer(String agent){
        getIncomingTransferWindow(agent).acceptRejectTransfer();
    }

    @Given("Transfer timeout for (.*) tenant is set to (.*) seconds")
    public void updateAgentInactivityTimeout(String tenantOrgName, Integer timeout){
        Tenants.setTenantUnderTestNames(tenantOrgName);
        TenantChatPreferences tenantChatPreferences = ApiHelper.getTenantChatPreferences();
        tenantChatPreferences.setAgentInactivityTimeoutSec(timeout);
        Response resp = ApiHelper.updateTenantConfig(tenantOrgName, tenantChatPreferences);
        Assert.assertEquals(resp.statusCode(), 200,
                "Changing agentInactivityTimeoutSec was not successful for '"+Tenants.getTenantUnderTestName()+"' tenant \n" +
                        "Response: " + resp.getBody().asString());
    }

    @Then("^(.*) should not see (.*) in a transfer pop-up agents dropdown$")
    public void secondAgentShouldSeeNoAvailableAgentsInATransferPopUp(String agent, String missingAgent) {
        String missingAgentName = getAgentName(missingAgent);
        List<String> availableAgents = getAgentHomePage(agent).getTransferChatWindow().getAvailableAgentsFromDropdown();
        Assert.assertFalse(availableAgents.contains(missingAgentName),
                String.format("Agent %s is displayed in a transfer pop-up agents dropdown", missingAgentName));
    }

    @When("^(.*) select an (.*) in 'Transfer to' drop down$")
    public void selectAgentTransferToDropDown(String agent, String agentTransferTo) {
        getAgentHomePage(agent).getTransferChatWindow().selectAgent(getAgentName(agentTransferTo));
    }

    @Then("^(.*) can see (.*) in a transfer pop-up agents dropdown$")
    public void agentIsAvailableAgentsInATransferPopUp(String agent, String agentToTransferChat) {
        String agentForTransferringChatTo = getAgentName(agentToTransferChat);
        List<String> availableAgents = getAgentHomePage(agent).getTransferChatWindow().getOnlineAgentsFromDropdown();

        assertThat(getAgentName(agentToTransferChat))
                .as(String.format("Agent %s is displayed in a transfer pop-up agents dropdown", agentForTransferringChatTo))
                .isIn(availableAgents);
    }

    @Then("^Close Transferring window for (.*)$")
    public void closeTransferringWindow(String agent) {
        getAgentHomePage(agent).getTransferChatWindow().clickCloseButton();
    }

    @Then("^(.*) can see 'Transferring chat...' message$")
    public void verifyChatTransferringMassage(String agent) {
        getLeftMenu(agent).verifyChatTransferringShown();
    }

    @When("^(.*) checks \"transfer chat\" icon (.*) on the chat desk$")
    public void cancelTransferChat(String agent, String transferButtonVisibility) {
        if (transferButtonVisibility.equalsIgnoreCase("appeared")) {
            Assert.assertTrue(getAgentHomePage(agent).getChatHeader().isTransferButtonDisplayed()
                    , "Transfer button is not displayed");
        } else if (transferButtonVisibility.equalsIgnoreCase("disappeared")) {
            Assert.assertTrue(getAgentHomePage(agent).getChatHeader().isTransferButtonNotDisplayed()
                    , "Transfer button is displayed");
        }
    }

    private static IncomingTransferWindow getIncomingTransferWindow(String agent) {
        return getAgentHomePage(agent).getIncomingTransferWindow();
    }
}
