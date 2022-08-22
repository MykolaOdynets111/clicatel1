package steps.portalsteps;

import agentpages.uielements.ChatBody;
import agentpages.uielements.LeftMenuWithChats;
import apihelper.ApiHelper;
import datamanager.jacksonschemas.TenantChatPreferences;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import datamanager.Tenants;
import dbmanager.DBConnector;
import driverfactory.DriverFactory;
import drivermanager.ConfigManager;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import steps.agentsteps.AgentConversationSteps;
import steps.dotcontrol.DotControlSteps;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class SupervisorDeskSteps extends AbstractPortalSteps {

    private List<String> shownUsers = new ArrayList<>();

    @Given("^Turn (.*) tickets autoScheduling$")
    public void changeTicketsAutoScheduling(String status) {
        TenantChatPreferences tenantChatPreferences = ApiHelper.getTenantChatPreferences();
        if (status.equalsIgnoreCase("on")) {
            tenantChatPreferences.setAutoTicketScheduling(true);
        } else if (status.equalsIgnoreCase("off")) {
            tenantChatPreferences.setAutoTicketScheduling(false);
        } else {
            throw new AssertionError("Incorrect status was provided");
        }
        ApiHelper.updateTenantConfig(Tenants.getTenantUnderTestOrgName(), tenantChatPreferences);
    }

    @Then("^Filter \"(.*)\" is selected by default$")
    public void filterIsSelectedByDefault(String filterName) {
        Assert.assertEquals(getSupervisorDeskPage().getSupervisorLeftPanel().getFilterByDefaultName(), filterName,
                "Filter name by default does not match expected");
    }

    @Then("^Select (.*) ticket checkbox$")
    public void clickThreeDotsButton(String channel) {
        getSupervisorDeskPage().getSupervisorTicketsTable().selectTicketCheckbox(getUserName(channel));
    }

    @When("^Agent select (.*) ticket$")
    public void selectTicket(String chanel) {
        getSupervisorDeskPage().getSupervisorTicketsTable().getTicketByUserName(getUserName(chanel)).clickOnUserName();
    }

    @When("^Click on Massage Customer button$")
    public void clickOnMassageCustomer() {
        getSupervisorDeskPage().getSupervisorTicketChatView().clickOnMessageCustomerButton();
        ;
    }

    @Then("^Message Customer Window is opened$")
    public void verifyMessageCustomerWindowIsOpened() {
        Assert.assertEquals(getSupervisorDeskPage().getMessageCustomerWindow().getHeader(), "Message Customer", "incorrect header Was shown for   Message Customer Window");
    }

    @When("^Supervisor send (.*) to agent trough (.*) chanel$")
    public void sendTicketMessageToCustomer(String message, String chanel) {
        getSupervisorDeskPage().getMessageCustomerWindow().selectChanel(chanel).setMessageText(message).clickSubmitButton();
    }

    @When("^Click 'Route to scheduler' button$")
    public void clickRouteToScheduler() {
        getSupervisorDeskPage().getSupervisorTicketsTable().clickRouteToSchedulerButton();
    }

    @When("^Click 'Assign manually' button$")
    public void clickAssignManually() {
        getSupervisorDeskPage().getSupervisorTicketsTable().clickAssignManuallyButton();
    }

    @Then("^(.*) request is shown on Supervisor Desk Live page$")
    public void verifySupervisorDeskHasRequestFormSocialUser(String channel) {
        String userName = getUserName(channel);
        Assert.assertTrue(getSupervisorDeskPage().isLiveChatShownInSD(userName, 5),
                "There is no Live chat on Supervisor Desk Live (Client ID: " + userName + ")");
    }

    @Then("^Supervisor Desk Live dos not have conversation (.*) request$")
    public void verifySupervisorDeskDoesNotNaveRequestFormSocialUser(String channel) {
        String userName = getUserName(channel);
        Assert.assertFalse(getSupervisorDeskPage().isLiveChatShownInSD(userName, 5),
                "There should not be chat on Supervisor Desk Live (Client ID: " + userName + ")");
    }

    @Then("^Verify that only \"(.*)\" chats are shown$")
    public void verifyChatsChannelsFilter(String channelName) {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(getSupervisorDeskPage().verifyChanelOfTheChatIsPresent(channelName), channelName + " channel name should be shown.");
        softAssert.assertTrue(getSupervisorDeskPage().verifyChanelFilter(),
                "Should be only " + channelName + " channel chats");
        softAssert.assertAll();
    }

    @Then("^Verify that only \"(.*)\" closed chats are shown$")
    public void verifyClosedChatsChannelsFilter(String channelName) {
        Assert.assertTrue(getSupervisorDeskPage().getSupervisorClosedChatsTable().verifyChanelOfTheChatsIsPresent(channelName),
                channelName + " channel name should be shown.");
    }

    @Then("^Verify that only \"(.*)\" tickets chats are shown$")
    public void verifyTicketsChatsChannelsFilter(String channelName) {
        Assert.assertTrue(getSupervisorDeskPage().getSupervisorTicketsTable().verifyChanelOfTheTicketsIsPresent(channelName),
                channelName + " channel name should be shown.");
    }

    @And("^Agent click On Live Supervisor Desk chat from (.*) channel$")
    public void clickOnLiveChat(String channel) {
        getSupervisorDeskPage().getSupervisorDeskLiveRow(getUserName(channel)).clickOnUserName();
    }

    @Then("^Supervisor Desk Live chat container header has (.*) User photo, name and (.*) channel$")
    public void verifyCorrectHeaderInfo(String channel, String image) {
        String userName = getUserName(channel);
        SoftAssert soft = new SoftAssert();
        soft.assertTrue(getSupervisorDeskPage().getChatHeader().isAvatarContainUserNameFirstLetter(userName),
                "Header Avatar does not contain first letter of the user name: " + userName);
        soft.assertEquals(getSupervisorDeskPage().getChatHeader().getChatHeaderText(), userName,
                "Incorrect Name was shown in chat header");
        soft.assertTrue(getSupervisorDeskPage().getChatHeader().isValidChannelImg(image),
                "Icon for channel in chat header as not expected");
        soft.assertAll();
    }

    @Then("^Supervisor Desk Live chat container header display \"(.*)\" instead of agent name$")
    public void verifyNoAgentHeaderInfo(String agentName) {
        ;
        Assert.assertEquals(getSupervisorDeskPage().getChatHeader().getAgentName(), agentName,
                "Agent should not be assigned");
    }

    @Then("^Supervisor Desk Live chat Profile is displayed$")
    public void profileFormIsShown() {
        Assert.assertTrue(getSupervisorDeskPage().getProfile().isProfilePageDisplayed(),
                "Profile form is not shown");
    }

    @Then("^(.*) is set as 'current agent' for dot control ticket$")
    public void verifyCurrentAgent(String agent) {
        String agentName = ApiHelper.getAgentInfo(Tenants.getTenantUnderTestOrgName(), agent).get("fullName");
        getSupervisorDeskPage().getCurrentDriver().navigate().refresh();
        getSupervisorDeskPage().waitForConnectingDisappear(2, 5);
        boolean result = false;
        for (int i = 0; i < 8; i++) {
            if (agentName.equals(getSupervisorDeskPage().getCurrentAgentOfTheChat(DotControlSteps.getClient()))) {
                result = true;
                break;
            } else waitFor(1000);
        }
        Assert.assertTrue(result, "Agent " + agentName + " is not set up as 'Current agent'");
    }

    @Then("^Ticket from (.*) is present on (.*) filter page$")
    public void verifyUnassignedType(String channel, String status) {
        String userName = getUserName(channel);
        if (status.equalsIgnoreCase("Unassigned")) {
            Assert.assertTrue(getSupervisorDeskPage().getCurrentAgentOfTheChat(userName).equalsIgnoreCase("No current Agent"),
                    "Unassigned ticket should be present");
        } else if (status.equalsIgnoreCase("Assigned") || status.equalsIgnoreCase("Expired")) {
            String agentName = ApiHelper.getAgentInfo(Tenants.getTenantUnderTestOrgName(), "agent").get("fullName");
            Assert.assertTrue(agentName.equals(getSupervisorDeskPage().getCurrentAgentOfTheChat(userName)),
                    "Ticket should be present on " + status + " filter page");
        } else if (status.equalsIgnoreCase("All tickets")) {
            getSupervisorDeskPage().getSupervisorTicketsTable().getTicketByUserName(userName);
        }
    }

    @Then("^Verify that only (.*) ticket is shown$")
    public void verifyChatsChannelsFilter(int tickets) {
        Assert.assertEquals(getSupervisorDeskPage().getSupervisorTicketsTable().getUsersNames().size(), tickets,
                "Only " + tickets + " ticket(s) number should be present on Supervisor Tickets page" +
                        "Could be because of TPLAT-5959");
        //todo uncomment step in the feature when search with spaces will be fixed
    }

    @Then("^Ticket from (.*) is not present on Supervisor Desk$")
    public void verifyUnassignedType(String channel) {
        String userName = getUserName(channel);
        boolean isTicketShown = true;
        try {
            getSupervisorDeskPage().getSupervisorTicketsTable().getTicketByUserName(userName);
        } catch (AssertionError e) {
            isTicketShown = false;
        }
        Assert.assertFalse(isTicketShown, "Ticket should not be shown");
    }

    @Then("^Update ticket with (.*) status$")
    public void updateTicketStatus(String status) {
        String chatId = DBConnector.getchatId(ConfigManager.getEnv(), DotControlSteps.getClientId());
        DBConnector.updateAgentHistoryTicketStatus(ConfigManager.getEnv(), status, chatId);
        Map elasticSearchModel = ApiHelper.getElasticSearchModel(chatId);
        elasticSearchModel.replace("ticketState", status);
        ApiHelper.updateElasticSearchModel(elasticSearchModel);
    }

    @Then("^'Assign chat' window is opened$")
    public void assignChatWindowOpened() {
        Assert.assertTrue(getSupervisorDeskPage().isAssignChatWindowOpened()
                , "'Assign chat' window is not opened");
    }

    @When("^I assign chat on (.*)$")
    public void assignChatManually(String agent) {
        String agentName = getAgentName(agent);
        getSupervisorDeskPage().assignChatOnAgent(agentName);
    }

    @Given("^Save shown tickets$")
    public void saveChats() {
        shownUsers = getSupervisorDeskPage().getSupervisorTicketsTable().getUsersNames();
    }

    @Given("^Supervisor scroll page to the bottom$")
    public void scrollTicketsDown() {
        getSupervisorDeskPage().scrollTicketsDown();
    }

    @Given("^More tickets are loaded$")
    public void moreRecordsAreShown() {
        SoftAssert soft = new SoftAssert();
        boolean result = getSupervisorDeskPage().areNewChatsLoaded(shownUsers.size(), 4);
        soft.assertTrue(result, "New chats are not loaded\n");
        soft.assertAll();
    }

    @Then("^Verify (.*) ticket types are available$")
    public void verifyTicketTypes(List<String> ticketTypes) {
        //ToDo add numbers of tickets verification
        Assert.assertEquals(getSupervisorDeskPage().getTicketTypes(), ticketTypes, "Ticket types are different");
    }

    @When("^User select (.*) ticket type$")
    public void selectTicketType(String type) {
        getSupervisorDeskPage().selectTicketType(type);
    }

    @Then("^Verify that live chat is displayed with (.*) message to agent$")
    public void verifyLiveChatPresent(String message) {
        Assert.assertTrue(getSupervisorDeskPage().openInboxChatBody(DotControlSteps.getClient()).isUserMessageShown(message), "Messages is not the same");
    }

    @Then("^Supervisor can see (.*) live chat with (.*) message to agent$")
    public void verifyLiveChatPresent(String channel, String message) {
        Assert.assertTrue(getSupervisorDeskPage().openInboxChatBody(getUserName(channel)).isUserMessageShown(message), "Messages is not the same");
    }

    @When("^Verify that correct messages and timestamps are shown on Chat View$")
    public void openChatViewAndVerifyMessages() {
        List<String> messagesFromChatBody = AgentConversationSteps.getMessagesFromChatBody().get();
        ChatBody inboxChatBody = getSupervisorDeskPage().openInboxChatBody(DotControlSteps.getClient());
        messagesFromChatBody.removeAll(Collections.singleton(""));
        Assert.assertEquals(inboxChatBody.getAllMessages(), messagesFromChatBody
                , "Incorrect messages with times were shown on Chat view");
        AgentConversationSteps.getMessagesFromChatBody().remove();
    }

    @When("^Verify Agent Icon is shown on Supervisor Desk Chat View$")
    public void openChatViewAndCheckIcon() {
        ChatBody inboxChatBody = getSupervisorDeskPage().openInboxChatBody(DotControlSteps.getClient());
        Assert.assertTrue(inboxChatBody.isValidAgentAvatarIsShown(),
                "Incorrect agent picture shown");
    }

    @When("^Verify that closed chats have Send email button$")
    public void verifyButtonForClosedChats() {
        getSupervisorDeskPage().getSupervisorClosedChatsTable().openFirstClosedChat();
        Assert.assertTrue(getSupervisorDeskPage().getSupervisorOpenedClosedChatsList().isClosedChatsHaveSendEmailButton(),
                "Closed chats doesn't have email button");
    }

    @Then("^Verify that closed chats have Message Customer button$")
    public void verifyMessageCustomerButtonForClosedChats() {
        getSupervisorDeskPage().getSupervisorClosedChatsTable().openFirstClosedChat();
        Assert.assertTrue(getSupervisorDeskPage().getSupervisorOpenedClosedChatsList().isClosedChatsHaveMessageCustomerButton(),
                "Closed chat does not have Message Customer button");
    }

    @Then("^WA chat show the name of the user$")
    public void verifyUserName() {
        Assert.assertEquals(getSupervisorDeskPage().getSupervisorClosedChatsTable().getUserName(), DotControlSteps.getClient(),
                "User name is not correct");
    }

    @Then("^Live chats (.*) filter has correct name and correct chats number$")
    public void verifyAgentNameOnLiveChatFilter(String agent) {
        String agentName = getAgentName(agent);
        int numberOfChats = ApiHelper.getActiveChatsByAgent(agent).getBody().jsonPath().getList("content").size();
        Assert.assertEquals(getSupervisorDeskPage().getSupervisorLeftPanel().getLiveChatsNumberForAgent(agentName), numberOfChats, "Number of chats on Supervisor desk is different from Agent desk chats");
    }

    @Then("^Chats Ended are sorted in (.*) order$")
    public void verifyClosedChatsSorting(String order) {
        List<LocalDateTime> listOfDates = getSupervisorDeskPage().getSupervisorClosedChatsTable().getClosedChatsDates();
        Assert.assertTrue(isDateSorted(order, listOfDates), "Closed chats are not sorted in " + order + " order");
    }

    @Then("^Tickets are sorted in (.*) order$")
    public void verifyTicketsSorting(String order) {
        List<LocalDateTime> listOfDates = getSupervisorDeskPage().getSupervisorTicketsTable().getTicketsStartDates();
        Assert.assertTrue(isDateSorted(order, listOfDates), "Tickets are not sorted in " + order + " order");
    }

    private boolean isDateSorted(String order, List<LocalDateTime> listOfDates) {
        boolean sortedStatus;
        if (order.contains("desc")) {
            sortedStatus = listOfDates.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList()).equals(listOfDates);
        } else if (order.contains("asc")) {
            sortedStatus = listOfDates.stream().sorted().collect(Collectors.toList()).equals(listOfDates);
        } else {
            throw new AssertionError("Incorrect order type was provided");
        }
        return sortedStatus;
    }

    @When("^Agent click on the arrow of Chat Ended$")
    public void clickOnTheArrowOfChatEnded() {
        getSupervisorDeskPage().getSupervisorClosedChatsTable().clickAscendingArrowOfChatEndedColumn();
    }

    @And("^Supervisor put a check mark on \"Flagged Only\" and click \"Apply Filters\" button$")
    public void filterFlaggedChats() {
        getSupervisorDeskPage().getSupervisorDeskHeader().clickFlaggedOnlyCheckbox().clickApplyFilterButton();
        getSupervisorDeskPage().waitForLoadingResultsDisappear(2, 6);
    }

    @And("^Agent select \"(.*)\" in Chanel container and click \"Apply filters\" button$")
    public void selectChanelFilter(String name) {
        getSupervisorDeskPage().getSupervisorDeskHeader().selectChanel(name).clickApplyFilterButton();
        getSupervisorDeskPage().waitForLoadingResultsDisappear(2, 6);
    }

    @And("^Agent filter by \"(.*)\" channel and \"(.*)\" sentiment$")
    public void selectChanelAndSentimentFilter(String name, String sentiment) {
        getSupervisorDeskPage().getSupervisorDeskHeader().selectChanel(name).selectSentiment(sentiment).clickApplyFilterButton();
        getSupervisorDeskPage().waitForLoadingResultsDisappear(2, 6);
    }

    @And("^Agent filter by \"(.*)\" chat, by \"(.*)\" channel and \"(.*)\" sentiment")
    public void filterBySetValues(String chatName, String chanellName, String sentimentName) {
        getSupervisorDeskPage().getSupervisorDeskHeader().filterByOptions(chatName, chanellName, sentimentName);
        getSupervisorDeskPage().waitForLoadingResultsDisappear(2, 6);
    }

    @And("^Agent search chat (.*) on Supervisor desk$")
    public void filterByChatName(String chatName) {
        String userName = null;
        try {
            userName = getUserName(chatName);
        } catch (AssertionError e) {
            userName = chatName;
        }
        getSupervisorDeskPage().getSupervisorDeskHeader().setSearchInput(userName).clickApplyFilterButton();
        getSupervisorDeskPage().waitForLoadingResultsDisappear(2, 6);
    }

    @Then("^Error \"(.*)\" message is displayed$")
    public void verifyNoChatsErrorMessage(String errorMessage) {
        Assert.assertEquals(getSupervisorDeskPage().getNoChatsErrorMessage(), errorMessage,
                "incorrect erorr message is shown");
    }

    @Then("^\"All Channels\" and \"All Sentiments\" selected as default$")
    public void defaultLiveChatsFilters() {
        SoftAssert soft = new SoftAssert();
        soft.assertEquals(getSupervisorDeskPage().getSupervisorDeskHeader().clickFlaggedOnlyCheckbox().getChannelFilterValue(), "All Channels", "Incorrect default Channels filter is set by default");
        soft.assertEquals(getSupervisorDeskPage().getSupervisorDeskHeader().clickFlaggedOnlyCheckbox().getSentimentsFilterValue(), "All Sentiments", "Incorrect default Sentiments filter is set by default");
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

    @When("^Supervisor agent launch as agent$")
    public void supervisorAgentLaunchAsAgent() {
        getSupervisorDeskPage().clickOnLaunchAgent();
    }

    @Then("^Supervisor agent sees confirmation popup with \"(.*)\" message$")
    public void supervisorAgentSeesConfirmationPopupWithAvailableAsAgentMessage(String message) {
        Assert.assertEquals(getSupervisorDeskPage().getSupervisorAvailableAsAgentDialog().getFullMessage(),
                message, "Message in supervisor agent confirmation popup is different");
    }

    @And("^Supervisor agent click launch in confirmation popup$")
    public void supervisorAgentClickLaunchInConfirmationPopup() {
        getSupervisorDeskPage().getSupervisorAvailableAsAgentDialog().clickLaunch();
        List<String> windowHandles = new ArrayList<>(DriverFactory.getDriverForAgent("main").getWindowHandles());
        DriverFactory.getDriverForAgent("main").switchTo().window(windowHandles.get(windowHandles.size() - 1));
    }

    @And("^Admin filter by (.) year (.) month and (.*) days ago start date and today's end date$")
    public void agentFilterByMonthBeforeStartDateAndTodaySEndDate(int year, int month, int day) {
        LocalDate startDate = LocalDate.now().minusYears(year).minusDays(month).minusDays(day);
        LocalDate endDate = LocalDate.now();

        getSupervisorDeskPage().getSupervisorDeskHeader()
                .selectStartDate(startDate)
                .selectEndDate(endDate)
                .clickApplyFilterButton();
        getSupervisorDeskPage().waitForLoadingResultsDisappear(2, 6);
    }

    @Then("^Verify closed chats dates are fitted by filter$")
    public void verifyClosedChatsDatesAreFittedByFilter() {
        LocalDate startDate = getSupervisorDeskPage().getSupervisorDeskHeader().getStartDateFilterValue();
        LocalDate endDate = getSupervisorDeskPage().getSupervisorDeskHeader().getEndDateFilterValue();
        List<LocalDateTime> listOfDates = getSupervisorDeskPage().getSupervisorClosedChatsTable().getClosedChatsDates();

        SoftAssert softAssert = new SoftAssert();
        for (LocalDateTime localDateTime : listOfDates) {
            verifyDateTimeIsInRangeOfTwoDates(localDateTime, startDate, endDate);
        }
        softAssert.assertAll();
    }

    @Then("^Verify tickets dates are fitted by filter$")
    public void verifyTicketsDatesAreFittedByFilter() {
        LocalDate startDate = getSupervisorDeskPage().getSupervisorDeskHeader().getStartDateFilterValue();
        LocalDate endDate = getSupervisorDeskPage().getSupervisorDeskHeader().getEndDateFilterValue();
        List<LocalDateTime> listOfStartedDates = getSupervisorDeskPage().getSupervisorTicketsTable().getTicketsStartDates();
        List<LocalDateTime> listOfEndedDates = getSupervisorDeskPage().getSupervisorTicketsTable().getTicketsEndDates();

        SoftAssert softAssert = new SoftAssert();
        for (LocalDateTime localDateTime : listOfStartedDates) {
            verifyDateTimeIsInRangeOfTwoDates(localDateTime, startDate, endDate);
        }

        for (LocalDateTime localDateTime : listOfEndedDates) {
            verifyDateTimeIsInRangeOfTwoDates(localDateTime, startDate, endDate);
        }
        softAssert.assertAll();
    }

    @And("^Agent load all filtered tickets$")
    public void agentLoadAllFilteredTickets() {
        getSupervisorDeskPage().loadAllTickets();
    }

    @And("^Agent load all filtered closed chats$")
    public void agentLoadAllFilteredChats() {
        getSupervisorDeskPage().loadAllClosedChats();
    }

    @Then("^Verify first closed chat date are fitted by filter$")
    public void verifyFirstClosedChatDateAreFittedByFilter() {
        LocalDate startDate = getSupervisorDeskPage().getSupervisorDeskHeader().getStartDateFilterValue();
        LocalDate endDate = getSupervisorDeskPage().getSupervisorDeskHeader().getEndDateFilterValue();
        LocalDateTime firstClosedChatDate = getSupervisorDeskPage().getSupervisorClosedChatsTable().getFirstClosedChatDate();
        verifyDateTimeIsInRangeOfTwoDates(firstClosedChatDate, startDate, endDate);
    }

    @Then("^Verify first closed ticket date are fitted by filter$")
    public void verifyFirstClosedTicketDateAreFittedByFilter() {
        LocalDate startDate = getSupervisorDeskPage().getSupervisorDeskHeader().getStartDateFilterValue();
        LocalDate endDate = getSupervisorDeskPage().getSupervisorDeskHeader().getEndDateFilterValue();
        LocalDateTime firstTicketStartDate = getSupervisorDeskPage().getSupervisorTicketsTable().getFirstTicketStartDates();
        LocalDateTime firstTicketEndDate = getSupervisorDeskPage().getSupervisorTicketsTable().getFirstTicketEndDates();
        verifyDateTimeIsInRangeOfTwoDates(firstTicketStartDate, startDate, endDate);
        verifyDateTimeIsInRangeOfTwoDates(firstTicketEndDate, startDate, endDate);
    }

    private void verifyDateTimeIsInRangeOfTwoDates(LocalDateTime dateTime, LocalDate startDate, LocalDate endDate) {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(startDate.compareTo(dateTime.toLocalDate()) <= 0,
                String.format("One of the chats was started before filtered value. Expected: after %s, Found: %s",
                        startDate, dateTime));
        softAssert.assertTrue(endDate.compareTo(dateTime.toLocalDate()) >= 0,
                String.format("One of the chats was ended before filtered value. Expected: before %s, Found: %s",
                        endDate, dateTime));
        softAssert.assertAll();
    }

    @And("^Agent click on the arrow of Ticket End Date$")
    public void agentClickOnTheArrowOfTicketEndDate() {
        getSupervisorDeskPage().getSupervisorTicketsTable().clickAscendingArrowOfEndDateColumn();
    }

    @Then("Verify that Chats tab is displayed first")
    public void verifyThatChatsTabIsDisplayedFirst() {
        getSupervisorDeskPage().getSupervisorLeftPanel().getChatElement();
    }

    @When("Verify {string} display default")
    public void verifyDisplayDefault(String liveChats) {
        getSupervisorDeskPage().getSupervisorLeftPanel().verifyChatgroup(liveChats);
    }

    @Then("Verify that live chats available are shown")
    public void verifyThatLiveChatAvailableAreShown(){
        getSupervisorDeskPage().getSupervisorLeftPanel().verifyLiveChatInfo();
    }
}