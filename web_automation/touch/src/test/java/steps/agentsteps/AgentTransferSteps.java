package steps.agentsteps;

import apihelper.ApiHelper;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import datamanager.Tenants;
import datamanager.jacksonschemas.dotcontrol.DotControlRequestMessage;
import drivermanager.ConfigManager;
import io.restassured.response.Response;
import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import steps.FacebookSteps;
import steps.TwitterSteps;
import steps.dotcontrol.DotControlSteps;

public class AgentTransferSteps extends AbstractAgentSteps {

    private String secondAgentName;

    @When("^(.*) click on 'Transfer' chat$")
    public void agentClickOnTransferChat(String agent) {
        getAgentHomeForMainAgent().getChatHeader().clickTransferButton();
    }

    @Then("^Transfer chat pop up appears$")
    public void transferChatPopUpAppears() {
        Assert.assertTrue(getAgentHomeForMainAgent().getTransferChatWindow().isTransferChatShown(),"Transfer chat pop up is not appears");
    }

    @When("^(.*) click 'Cancel transfer' button$")
    public void cancelTransferChat(String agent){
        getAgentHomePage(agent).getChatHeader().clickCancelTransferButton();
    }

    @When("^(.*) transfers chat$")
    public void transferChat(String agent){
        getAgentHomePage(agent).getChatHeader().clickTransferButton();
        secondAgentName = getAgentHomePage(agent).getTransferChatWindow().transferChat();
    }

    @And("^(.*) transfers overnight ticket$")
    public void agentTransfersOvernightTicket(String agent) {
        getAgentHomePage(agent).getChatHeader().clickTransferButton();
        secondAgentName = getAgentHomePage(agent).getTransferChatWindow().transferOvernightTicket();
    }

    @When("^(.*) transfer a few chats$")
    public void transferFewDotControlChats(String agent){
        for(DotControlRequestMessage chat : createdChatsViaDotControl) {
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
            getLeftMenu(agent).openNewFromSocialConversationRequest(chat.getClientId());
            transferChat(agent);
            getAgentHomePage(agent).waitForModalWindowToDisappear();

        }
    }

    @Then("^(.*) can not click '(.*)' button$")
    public void agentCanNotClickTransferChatButton(String agent, String transferButton) {
        Assert.assertFalse(getAgentHomePage(agent).getChatHeader().isButtonEnabled(transferButton),
                "Transfer chat button is enabled ");
    }

    @When("^Select 'Transfer to' drop down$")
    public void selectTransferToDropDown() {
        getAgentHomeForMainAgent().getTransferChatWindow().openDropDownAgent();
    }

    @When("^(.*) select an agent in 'Transfer to' drop down$")
    public void selectAgentTransferToDropDown(String agent) {
        getAgentHomePage(agent).getTransferChatWindow().selectDropDownAgent();
    }

    @Then("^Agent notes field is appeared$")
    public void verifyNotesFieldAppears(){
        Assert.assertTrue(getAgentHomePage("first").getTransferChatWindow().isNoteShown(),
                "'Note' input is not shown after selecting agent");
    }

    @Then("^Agent sees '(.*)'$")
    public void agentSeesCurrentlyThereSNoAgentsAvailable(String message) {
        Assert.assertEquals(getAgentHomeForMainAgent().getTransferChatWindow().getTextDropDownMessage(), message, "message in drop down menu not as expected");
    }

    @Then("Agent sees error message 'Notes are required when specific agent is selected.'")
    public void verifyRequiredNotesField(){
        SoftAssert soft = new SoftAssert();
        soft.assertEquals(getAgentHomeForMainAgent().getTransferChatWindow().getNoteInputErrorText(),
                "Notes are required when specific agent is selected.",
                "Error about required notes is not as expected");
        soft.assertEquals(getAgentHomeForMainAgent().getTransferChatWindow().getNoteInputColor(),
                "rgb(242, 105, 33)", "Note input: expected Notes boarder color is not as expected" );
        soft.assertAll();
    }

    @When("^Complete 'Note' field$")
    public void sentNotesTransferChatPopup() {
        getAgentHomeForMainAgent().getTransferChatWindow().sentNote();
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
        Assert.assertTrue(
                getAgentHomePage(agent).getIncomingTransferWindow().isTransferWindowHeaderNotShown(),
                "Transfer chat header is shown for "+ agent + " agent");

    }

    @Then("(.*) receives incoming transfer with \"(.*)\" header")
    public void verifyIncomingTransferHeader(String agent, String expectedHeader){
        Assert.assertEquals(getAgentHomePage(agent).getIncomingTransferWindow().getTransferWindowHeader(),
                expectedHeader,
                "Header in incoming transfer window is not as expected");
    }

    @Then("(.*) receives incoming transfer with \"(.*)\" note from the another agent")
    public void verifyIncomingTransferReceived(String agent, String notes){
        Assert.assertEquals(getAgentHomePage(agent).getIncomingTransferWindow().getTransferNotes(), notes,
                "Notes in incoming transfer window is not as added by the first agent");
    }

    @Given("^(.*) receives a few conversation requests$")
    public void create2DotControlChats(String agent){
        SoftAssert soft = new SoftAssert();
        DotControlSteps dotControlSteps = new DotControlSteps();
        dotControlSteps.createIntegration(Tenants.getTenantUnderTestOrgName());
        createdChatsViaDotControl.add(dotControlSteps.preparePayloadForDotControl("connect to agent'"));
        DotControlSteps.cleanUPDotControlRequestMessage();
        createdChatsViaDotControl.add(dotControlSteps.preparePayloadForDotControl("chat to support"));

        soft.assertTrue(getLeftMenu(agent)
                        .isNewConversationRequestFromSocialIsShown(
                                createdChatsViaDotControl.get(0).getClientId(),20),
                "There is no new conversation request on Agent Desk (Client name: "+createdChatsViaDotControl.get(0).getClientId()+")");
        soft.assertTrue(getLeftMenu(agent)
                        .isNewConversationRequestFromSocialIsShown(
                                createdChatsViaDotControl.get(1).getClientId(),20),
                "There is no new conversation request on Agent Desk (Client name: "+createdChatsViaDotControl.get(1).getClientId()+")");
        soft.assertAll();
    }

    @Then("(.*) can see transferring agent name, (.*) and following user's message: '(.*)'")
    public void verifyIncomingTransferDetails(String agent, String user, String userMessage) {
        try {
            SoftAssert soft = new SoftAssert();
            String expectedUserName = getUserName(user);
            if (ConfigManager.getSuite().equalsIgnoreCase("twitter")) {
                userMessage = TwitterSteps.getCurrentConnectToAgentTweetText();
            }
            if(ConfigManager.getSuite().equalsIgnoreCase("facebook")) {
                userMessage = FacebookSteps.getCurrentUserMessageText();
            }
            Response agentInfoResp = Tenants.getPrimaryAgentInfoForTenant(Tenants.getTenantUnderTestOrgName());
            String expectedAgentNAme = agentInfoResp.getBody().jsonPath().get("firstName") + " " +
                    agentInfoResp.getBody().jsonPath().get("lastName");

            soft.assertEquals(getAgentHomePage(agent).getIncomingTransferWindow().getClientName(), expectedUserName,
                    "User name in Incoming transfer window is not as expected");
            soft.assertEquals(getAgentHomePage(agent).getIncomingTransferWindow().getClientMessage(), userMessage,
                    "User message in Incoming transfer window is not as expected");
            soft.assertEquals(getAgentHomePage(agent).getIncomingTransferWindow().getFromAgentName(), expectedAgentNAme,
                    "Transferring agent name in Incoming transfer window is not as expected");
            soft.assertAll();
        } catch (NoSuchElementException e){
            Assert.fail("Incoming transfer window is not shown.\n Please see the screenshot");
        }
    }

    @Then("^(.*) receives incoming transfer on the right side of the screen with user's profile picture, channel and sentiment$")
    public void secondAgentReceivesIncomingTransferOnTheRightSideOfTheScreenWithUserSProfilePicturePriorityChannelAndSentiment(String agent) {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(getAgentHomePage(agent).getIncomingTransferWindow().isValidImgTransferPicture(),
                "User picture as not expected");
        softAssert.assertTrue(getAgentHomePage(agent).getIncomingTransferWindow().isValidImTransferChannel(),
                "Channel picture as not expected");
        softAssert.assertTrue(getAgentHomePage(agent).getIncomingTransferWindow().isValidImgTransferSentiment(),
                "Sentiment picture as not expected");
        softAssert.assertTrue(getAgentHomePage(agent).getIncomingTransferWindow().isRigthSideTransferChatWindow(),
                "Transfered chat window not on the right side of the screen");

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
        Assert.assertEquals(getAgentHomePage(agent).getIncomingTransferWindow().getRejectedBy(),
                "Rejected by:\n" + secondAgentName,
                "Header in incoming transfer window is not as expected");
    }

    @Then("^(.*) click \"Accept transfer\" button$")
    public void acceptIncomingTransfer(String agent){
        getAgentHomePage(agent).getIncomingTransferWindow().acceptTransfer();
    }

    @Then("^Second agent click \"Reject transfer\" button$")
    public void rejectIncomingTransfer(){
        getAgentHomeForSecondAgent().getIncomingTransferWindow().rejectTransfer();
    }

    @Then("^(.*) click \"Accept\" button$")
    public void acceptRejectedTransfer(String agent){
        getAgentHomePage(agent).getIncomingTransferWindow().acceptRejectTransfer();
    }

    @Given("Transfer timeout for (.*) tenant is set to (.*) seconds")
    public void updateAgentInactivityTimeout(String tenantOrgName, String timeout){
        Tenants.setTenantUnderTestNames(tenantOrgName);
        Response resp = ApiHelper.updateTenantConfig(tenantOrgName, "agentInactivityTimeoutSec", timeout);
        Assert.assertEquals(resp.statusCode(), 200,
                "Changing agentInactivityTimeoutSec was not successful for '"+Tenants.getTenantUnderTestName()+"' tenant \n" +
                        "Response: " + resp.getBody().asString());
    }
}
