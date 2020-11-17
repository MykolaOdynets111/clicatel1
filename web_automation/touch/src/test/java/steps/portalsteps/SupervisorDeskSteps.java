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

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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
        Assert.assertEquals(getSupervisorDeskPage().getSupervisorLeftPanel().getFilterByDefaultName(), filterName,
                "Filter name by default does not match expected");
    }

    @Then("^Select dot control ticket checkbox$")
    public void clickThreeDotsButton(){
        getSupervisorDeskPage().getSupervisorTicketsTable().selectTicketCheckbox(DotControlSteps.getClient());
    }

    @When("^Agent select (.*) ticket$")
    public void selectTicket(String chanel){
        getSupervisorDeskPage().getSupervisorTicketsTable().getTicketByUserName(getUserName(chanel)).clickOnUserName();
    }

    @When("^Click on Massage Customer button$")
    public void clickOnMassageCustomer(){
        getSupervisorDeskPage().getSupervisorTicketChatView().clickOnMessageCustomerButton();;
    }

    @Then("^Message Customer Window is opened$")
    public void verifyMessageCustomerWindowIsOpened(){
        Assert.assertEquals(getSupervisorDeskPage().getMessageCustomerWindow().getHeader(), "Message Customer", "incorrect header Was shown for   Message Customer Window");
    }

    @When("^Supervisor send (.*) to agent trough (.*) chanel$")
    public void sendTicketMessageToCustomer(String message, String chanel){
        getSupervisorDeskPage().getMessageCustomerWindow().selectChanel(chanel).setMessageText(message).clickSubmitButton();
    }

    @When("Click 'Route to scheduler' button")
    public void clickRouteToScheduler(){
        getSupervisorDeskPage().getSupervisorTicketsTable().clickRouteToSchedulerButton();
    }

    @When("Click 'Assign manually' button")
    public void clickAssignManually(){
        getSupervisorDeskPage().getSupervisorTicketsTable().clickAssignManuallyButton();
    }

    @Then("^Supervisor Desk Live has new conversation (.*) request$")
    public void verifySupervisorDeskHasRequestFormSocialUser(String channel){
        String userName = getUserName(channel);
        Assert.assertTrue(getSupervisorDeskPage().isLiveChatShownInSD(userName, 5),
                "There is no Live chat on Supervisor Desk Live (Client ID: "+userName+")");
    }

    @Then("^Supervisor Desk Live dos not have conversation (.*) request$")
    public void verifySupervisorDeskDoesNotNaveRequestFormSocialUser(String channel){
        String userName = getUserName(channel);
        Assert.assertFalse(getSupervisorDeskPage().isLiveChatShownInSD(userName, 5),
                "There should not be chat on Supervisor Desk Live (Client ID: "+userName+")");
    }

    @Then("^Verify that only (.*) chats are shown$")
    public void verifyChatsChannelsFilter(String channelName){
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(getSupervisorDeskPage().verifyChanelOfTheChatIsPresent(channelName), channelName + " channel name should be shown.");
        softAssert.assertTrue(getSupervisorDeskPage().verifyChanelFilter(),
                "Should be only "+ channelName +" channel chats");
    }

    @And("Agent click On Live Supervisor Desk chat from (.*) channel")
    public void clickOnLiveChat(String channel){
        getSupervisorDeskPage().getSupervisorDeskLiveRow(getUserName(channel)).clickOnUserName();
    }

    @Then("Supervisor Desk Live chat container header has (.*) User photo, name and (.*) channel")
    public void verifyCorrectHeaderInfo(String channel, String image){
        String userName = getUserName(channel);
        SoftAssert soft =new SoftAssert();
        soft.assertTrue(getSupervisorDeskPage().getChatHeader().isAvatarContainUserNameFirstLetter(userName),
                "Header Avatar does not contain first letter of the user name: " + userName);
        soft.assertEquals(getSupervisorDeskPage().getChatHeader().getChatHeaderText(), userName,
                "Incorrect Name was shown in chat header");
        soft.assertTrue(getSupervisorDeskPage().getChatHeader().isValidChannelImg(image),
                "Icon for channel in chat header as not expected");
        soft.assertAll();
    }

    @Then("Supervisor Desk Live chat Profile is displayed")
    public void profileFormIsShown(){
        Assert.assertTrue(getSupervisorDeskPage().getProfile().isProfilePageDisplayed(),
                "Profile form is not shown");
    }

    @Then("^(.*) is set as 'current agent' for dot control ticket$")
    public void verifyCurrentAgent(String agent){
        Response rest = ApiHelper.getAgentInfo(Tenants.getTenantUnderTestOrgName(), agent);
        String agentName = rest.jsonPath().get("firstName") + " " + rest.jsonPath().get("lastName");
        getSupervisorDeskPage().getCurrentDriver().navigate().refresh();
        getSupervisorDeskPage().waitForConnectingDisappear(2,5);
        boolean result = false;
        for (int i = 0; i < 8; i++){
            if(agentName.equals(getSupervisorDeskPage().getCurrentAgentOfTheChat(DotControlSteps.getClient()))){
                result = true;
                break;
            }
            else waitFor(1000);
        }
        Assert.assertTrue(result, "Agent " +agentName+ " is not set up as 'Current agent'");
    }

    @Then("Ticket from (.*) is present on (.*) filter page")
    public void verifyUnassignedType(String channel, String status){
        String userName = getUserName(channel);
        if (status.equalsIgnoreCase("Unassigned")) {
            Assert.assertTrue(getSupervisorDeskPage().getCurrentAgentOfTheChat(userName).equalsIgnoreCase("No current Agent"),
                    "Unassigned ticket should be present");
        } else if (status.equalsIgnoreCase("Assigned") || status.equalsIgnoreCase("Overdue")){
            Response rest = ApiHelper.getAgentInfo(Tenants.getTenantUnderTestOrgName(), "agent");
            String agentName = rest.jsonPath().get("firstName") + " " + rest.jsonPath().get("lastName");
            Assert.assertTrue(agentName.equals(getSupervisorDeskPage().getCurrentAgentOfTheChat(userName)),
                    "Ticket should be present on " + status + " filter page");
        } else if(status.equalsIgnoreCase("All tickets")){
            getSupervisorDeskPage().getSupervisorTicketsTable().getTicketByUserName(userName);
        }
    }


    @Then("^Verify that only (.*) ticket is shown$")
    public void verifyChatsChannelsFilter(int tickets){
        Assert.assertEquals(getSupervisorDeskPage().getSupervisorTicketsTable().getUsersNames().size(), tickets,
                "Only "+tickets+" ticket(s) number should be present on Supervisor Tickets page");
    }

    @Then("Ticket from (.*) is not present on Supervisor Desk")
    public void verifyUnassignedType(String channel){
        String userName = getUserName(channel);
        boolean isTicketShown = true;
        try {
            getSupervisorDeskPage().getSupervisorTicketsTable().getTicketByUserName(userName);
        } catch ( AssertionError e){
            isTicketShown = false;
        }
        Assert.assertFalse(isTicketShown, "Ticket should not be shown");
    }

    @Then("Update ticket with (.*) status")
    public void updateTicketStatus(String status){
        String chatId = DBConnector.getchatId(ConfigManager.getEnv(), DotControlSteps.getClientId());
        DBConnector.updateAgentHistoryTicketStatus(ConfigManager.getEnv(), status, chatId);
        Map elasticSearchModel = ApiHelper.getElasticSearchModel(chatId);
        elasticSearchModel.replace("ticketState", status);
        ApiHelper.updateElasticSearchModel(elasticSearchModel);
    }

    @Then("^'Assign chat' window is opened$")
    public void assignChatWindowOpened(){
        Assert.assertTrue(getSupervisorDeskPage().isAssignChatWindowOpened()
                , "'Assign chat' window is not opened");
    }

    @When("^I assign chat on (.*)$")
    public void assignChatManually(String agent){
        String agentName = getAgentName(agent);
        getSupervisorDeskPage().assignChatOnAgent(agentName);
    }

    @Given("^Save shown tickets$")
    public void saveChats() {
        shownUsers = getSupervisorDeskPage().getSupervisorTicketsTable().getUsersNames();
    }

    @Given("^Supervisor scroll page to the bottom$")
    public void scrollTicketsDown(){
        getSupervisorDeskPage().scrollTicketsDown();
    }

    @Given("^More tickets are loaded$")
    public void moreRecordsAreShown(){
        SoftAssert soft = new SoftAssert();
        boolean result = getSupervisorDeskPage().areNewChatsLoaded(shownUsers.size(), 4);
        soft.assertTrue(result, "New chats are not loaded\n");
        soft.assertAll();
    }

    private String getAgentName(String agent){
        Response rest = ApiHelper.getAgentInfo(Tenants.getTenantUnderTestOrgName(), agent);
        return rest.jsonPath().get("firstName") + " " + rest.jsonPath().get("lastName");
    }

    @Then("Verify (.*) ticket types are available")
    public void verifyTicketTypes(List<String> ticketTypes){
        //ToDo add numbers of tickets verification
        Assert.assertEquals(getSupervisorDeskPage().getTicketTypes(), ticketTypes, "Ticket types are different");
    }

    @When("User select (.*) ticket type")
    public void selectTicketType(String type){
        getSupervisorDeskPage().selectTicketType(type);
    }

    @Then("Verify that live chat is displayed with (.*) message to agent")
    public void verifyLiveChatPresent(String message){
        Assert.assertTrue(getSupervisorDeskPage().openInboxChatBody(DotControlSteps.getClient()).isUserMessageShown(message), "Messages is not the same");
    }

    @When("Verify that correct messages and timestamps are shown on Chat View")
    public void openChatView(){
        SoftAssert soft = new SoftAssert();
        List<String> messagesFromChatBody = AgentConversationSteps.getMessagesFromChatBody().get();
        ChatBody inboxChatBody = getSupervisorDeskPage().openInboxChatBody(DotControlSteps.getClient());
        messagesFromChatBody.removeAll(Collections.singleton(""));
        soft.assertEquals(inboxChatBody.getAllMessages(), messagesFromChatBody
                , "Incorrect messages with times were shown on Chat view");
        AgentConversationSteps.getMessagesFromChatBody().remove();
        soft.assertAll();
    }

    @When("Verify that closed chats have Send email button")
    public void verifyButtonForClosedChats() {
        getSupervisorDeskPage().supervisorClosedChatsTable().openFirstClosedChat();
        Assert.assertTrue(getSupervisorDeskPage().supervisorOpenedClosedChatsList().isClosedChatsHaveSendEmailButton(),
                "Closed chats doesn't have email button");
    }

    @Then ("^Live chats (.*) filter has correct name and correct chats number$")
    public void verifyAgentNameOnLiveChatFilter(String agent){
        String agentName = getAgentName(agent);
        int numberOfChats = ApiHelper.getActiveChatsByAgent(agent).getBody().jsonPath().getList("content").size();
        Assert.assertEquals(getSupervisorDeskPage().getSupervisorLeftPanel().getLiveChatsNumberForAgent(agentName), numberOfChats,"Number of chats on Supervisor desk is different from Agent desk chats");
    }

    @Then("^Chats Ended are sorted in (.*) order$")
    public void verifyClosedChatsSorting(String order){
        List<LocalDateTime> listOfDates = getSupervisorDeskPage().supervisorClosedChatsTable().getClosedChatsDates();
        Assert.assertTrue(isDateSorted(order, listOfDates), "Closed chats are not sorted in "+order +" order");
    }

    @Then("^Tickets are sorted in (.*) order$")
    public void verifyTicketsSorting(String order){
        List<LocalDateTime> listOfDates = getSupervisorDeskPage().getSupervisorTicketsTable().getTicketsStartDates();
        Assert.assertTrue(isDateSorted(order, listOfDates), "Tickets are not sorted in "+order +" order");
    }

    private boolean isDateSorted(String order, List<LocalDateTime> listOfDates){
        boolean sortedStatus;
        if (order.contains("desc")){
            sortedStatus = listOfDates.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList()).equals(listOfDates);
        } else if(order.contains("asc")){
            sortedStatus = listOfDates.stream().sorted().collect(Collectors.toList()).equals(listOfDates);
        } else{
            throw new AssertionError("Incorrect order type was provided");
        }
        return sortedStatus;
    }

    @When("^Agent click on the arrow of Chat Ended$")
    public void clickOnTheArrowOfChatEnded(){
        getSupervisorDeskPage().supervisorClosedChatsTable().clickAscendingArrowOfChatEndedColumn();
    }

    @And ("^Supervisor put a check mark on \"Flagged Only\" and click \"Apply Filters\" button$")
    public void filterFlaggedChats(){
        getSupervisorDeskPage().supervisorDeskHeader().clickFlaggedOnlyCheckbox().clickApplyFilterButton();
        getSupervisorDeskPage().waitForLoadingResultsDisappear(2,6);
    }

    @And("^Agent select \"(.*)\" in Chanel container and click \"Apply filters\" button$")
    public void selectChanelFilter(String name){
        getSupervisorDeskPage().supervisorDeskHeader().selectChanel(name).clickApplyFilterButton();
        getSupervisorDeskPage().waitForLoadingResultsDisappear(2,6);
    }

    @And("^Agent filter by \"(.*)\" channel and \"(.*)\" sentiment$")
    public void selectChanelAndSentimentFilter(String name, String sentiment){
        getSupervisorDeskPage().supervisorDeskHeader().selectChanel(name).selectSentiment(sentiment).clickApplyFilterButton();
        getSupervisorDeskPage().waitForLoadingResultsDisappear(2,6);
    }

    @And("^Agent filter by \"(.*)\" chat, by \"(.*)\" channel and \"(.*)\" sentiment")
    public void filterBySetValues(String chatName, String chanellName, String sentimentName){
        getSupervisorDeskPage().supervisorDeskHeader().filterByOptions(chatName, chanellName, sentimentName);
        getSupervisorDeskPage().waitForLoadingResultsDisappear(2,6);
    }

    @And("^Agent search chat (.*) on Supervisor desk$")
    public void filterByChatName(String chatName){
        String userName = null;
        try {
            userName = getUserName(chatName);
        } catch (AssertionError e){
            userName = chatName;
        }
        getSupervisorDeskPage().supervisorDeskHeader().setSearchInput(userName).clickApplyFilterButton();
        getSupervisorDeskPage().waitForLoadingResultsDisappear(2,6);
    }

    @Then("Error \"(.*)\" message is displayed")
    public void verifyNoChatsErrorMessage(String errorMessage){
        Assert.assertEquals(getSupervisorDeskPage().getNoChatsErrorMessage(), errorMessage,
                "incorrect erorr message is shown");
    }

    @Then("^\"All Channels\" and \"All Sentiments\" selected as default$")
    public void  defaultLiveChatsFilters(){
        SoftAssert soft = new SoftAssert();
        soft.assertEquals(getSupervisorDeskPage().supervisorDeskHeader().clickFlaggedOnlyCheckbox().getChannelFilterValue() , "All Channels", "Incorrect default Channels filter is set by default");
        soft.assertEquals(getSupervisorDeskPage().supervisorDeskHeader().clickFlaggedOnlyCheckbox().getSentimentsFilterValue() , "All Sentiments", "Incorrect default Sentiments filter is set by default");
        soft.assertAll();
    }

    @Then("^Supervisor Desk Live chat have 'flag on' button$")
    public void supervisorDeskLiveChatHaveFlagOnIcon() {
        Assert.assertTrue(getSupervisorDeskPage().getChatHeader().isFlagOnButtonDisplayed(),
                "Flag on button is not displayed");
    }

    @Then("^Supervisor Desk Live chat from (.*) channel is unflagged$")
    public void supervisorDeskLiveChatFromTouchChannelIsUnflagged(String channel) {
        String userName = getUserName(channel);
        Assert.assertTrue(getSupervisorDeskPage().getSupervisorDeskLiveRow(userName).isFlagIconRemoved(),
                String.format("Chat with user %s is flagged", userName));
    }
}
