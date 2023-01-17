package steps.portalsteps;

import agentpages.uielements.ChatBody;
import apihelper.ApiHelper;
import datamanager.Tenants;
import datamanager.jacksonschemas.TenantChatPreferences;
import driverfactory.DriverFactory;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import steps.agentsteps.AgentConversationSteps;
import steps.dotcontrol.DotControlSteps;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static steps.agentsteps.AbstractAgentSteps.*;

public class SupervisorDeskSteps extends AbstractPortalSteps {

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

    @When("^(.*) send (.*) to agent trough (.*) chanel$")
    public void sendTicketMessageToCustomer(String agent, String message, String chanel) {
        getAgentHomePage(agent).getMessageCustomerWindow().selectChanel(chanel).setMessageText(message).clickSubmitButton();
    }

    @When("^(.*) is unable to send (.*) HSM on Tickets for (.*) chat$")
    public void hsmChannelNotShownOnParticularChannel(String agent, String channel, String originalChannel) {
        Assert.assertFalse(getAgentHomePage(agent).getMessageCustomerWindow().isChanelShown(channel),
                channel + " HSM is present in: " + originalChannel);
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

    @When("^Supervisor opens closed chat$")
    public void openFirstClosedChat() {
        getSupervisorDeskPage().getSupervisorClosedChatsTable().openFirstClosedChat();
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
        Assert.assertEquals(getSupervisorDeskPage().getChatHeader().getAgentName(), agentName,
                "Agent should not be assigned");
    }

    @Then("Supervisor Desk Live chat header display {string} Agent name")
    public void VerifyAgentNameDisplayed(String agentName){
        Assert.assertEquals(getSupervisorDeskPage().getChatHeader().getAgentName(),agentName,"Agent name not displayed");
    }

    @And("Supervisor Desk Live chat header display date")
    public void VerifyChatDateDisplayed(){
        Assert.assertEquals(getSupervisorDeskPage().getChatHeader().getChatDateText(),LocalDate.now().format(DateTimeFormatter.ofPattern("dd MMM yyyy")),"Date not displayed");
    }

    @Then("^Supervisor Desk Live chat Profile is displayed$")
    public void profileFormIsShown() {
        Assert.assertTrue(getSupervisorDeskPage().getProfile().isProfilePageDisplayed(),
                "Profile form is not shown");
    }

    @Then("^(.*) is the current agent of the chat$")
    public void verifyCurrentAgentOfChat(String agentName) {
        Assert.assertEquals(getSupervisorDeskPage().getChatHeader().getAgentName(),agentName,
                "The current agent of the ticket is not as expected");
    }

    @Then("^'Assign chat' window is opened$")
    public void assignChatWindowOpened() {
        Assert.assertTrue(getSupervisorDeskPage().isAssignChatWindowOpened()
                , "'Assign chat' window is not opened");
    }

    @When("^I assign chat on (.*) for (.*) dropdown$")
    public void assignChatManually(String agent, String assignmentType) {
        String agentName = null;
        if(assignmentType.equalsIgnoreCase("Agent")) {
            agentName = getAgentName(agent);
        }
        else {
            agentName = agent;
        }
        getSupervisorDeskPage().assignChat(assignmentType, agentName);
    }

    @Then("^Verify Chat has pending icon in the Chat (.*)$")
    public void verifyChatHasPendingIcon(String value) {
        switch (value){
            case "List":
                 Assert.assertTrue(getSupervisorDeskPage().isChatPendingIconShown(), "Yellow Pending Icon is not shown in chat list");
                 break;
            case "View":
                Assert.assertTrue(getSupervisorDeskPage().isChatPendingOnShown(), "Yellow Pending On is not shown in chat view");
                break;
            default:
                Assert.fail("Incorrect string value");
        }
    }

    @Then("^Supervisor can see (.*) live chat with (.*) message to agent$")
    public void verifyLiveChatPresent(String channel, String message) {
        Assert.assertTrue(getSupervisorDeskPage().openInboxChatBody(getUserName(channel)).isUserMessageShown(message), "Messages is not the same");
    }

    @Then("^Supervisor can see (.*) live chat with (.*) message from agent$")
    public void verifyLiveChatPresentFromAgent(String channel, String message) {
        Assert.assertTrue(getSupervisorDeskPage().openInboxChatBody(getUserName(channel)).isToUserMessageShownWithWait(message, 5), "Messages is not the same");
    }

    @Then("^Supervisor can see (.*) ticket with (.*) message from agent$")
    public void verifyTicketMessagePresent(String channel, String message) {
        Assert.assertTrue(getSupervisorDeskPage().getTicketChatBody().isToUserMessageShownWithWait(message,8), "Messages is not the same");
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

    @Then("^Verify that closed chats have Message Customer button$")
    public void verifyMessageCustomerButtonForClosedChats() {
        getSupervisorDeskPage().getSupervisorClosedChatsTable().openFirstClosedChat();
        Assert.assertTrue(getTicketsPage("main").getSupervisorTicketClosedChatView().isMessageCustomerButtonOrStartChatPresent(),
                "Closed chat does not have Message Customer button");
    }

    @Then("^Verify that \"Message Customer\" button should not be present$")
    public void verifyMessageCustomerButtonVisibility() {
        Assert.assertFalse(getTicketsPage("main").getSupervisorTicketClosedChatView().isMessageCustomerButtonOrStartChatPresent(),
                "CHat view is having Message Customer button");
    }

    @Then("^WA chat show the name of the user$")
    public void verifyUserName() {
        Assert.assertEquals(getSupervisorDeskPage().getSupervisorClosedChatsTable().getUserName(), DotControlSteps.getClient(),
                "User name is not correct");
    }

    @Then("^Chats Ended are sorted in (.*) order$")
    public void verifyClosedChatsSorting(String order) {
        List<LocalDateTime> listOfDates = getSupervisorDeskPage().getSupervisorClosedChatsTable().getClosedChatsDates();
        Assert.assertTrue(isDateSorted(order, listOfDates), "Closed chats are not sorted in " + order + " order");
    }

    @And("^Chat from (.*) channel is present for (.*)$")
    public void verifyChatIsPresent(String channel, String agent) {
        boolean channelNamePresent = getChatDeskPage(agent)
                .getSupervisorDeskLiveRow(getUserName(channel))
                .isChannelNamePresent();

        assertThat(channelNamePresent)
                .as("Chat should be present!")
                .isTrue();
    }

    @When("^Agent click on the arrow of Chat Ended$")
    public void clickOnTheArrowOfChatEnded() {
        getSupervisorDeskPage().getSupervisorClosedChatsTable().clickAscendingArrowOfChatEndedColumn();
    }

    @And("^Supervisor put a check mark on \"Flagged Only\" and click \"Apply Filters\" button$")
    public void filterFlaggedChats() {
        getSupervisorAndTicketsHeader("main").clickFlaggedOnlyCheckbox().clickApplyFilterButton();
        getSupervisorDeskPage().waitForLoadingResultsDisappear(2, 6);
    }

    @And("^Agent select \"(.*)\" in Chanel container and click \"Apply filters\" button$")
    public void selectChanelFilter(String name) {
        getSupervisorAndTicketsHeader("main").selectChanel(name).clickApplyFilterButton();
        getSupervisorDeskPage().waitForLoadingResultsDisappear(2, 6);
    }

    @Then("^Agent select my closed chats checkbox in container and click \"Apply filters\" button$")
    public void filterUsingMyClosedChatsCheckbox() {
        getSupervisorAndTicketsHeader("main").clickMyClosedChatsCheckbox().clickApplyFilterButton();
        getSupervisorDeskPage().waitForLoadingResultsDisappear(2, 6);
    }

    @And("^Agent filter by \"(.*)\" channel and \"(.*)\" sentiment$")
    public void selectChanelAndSentimentFilter(String name, String sentiment) {
        getSupervisorAndTicketsHeader("main").selectChanel(name).selectSentiment(sentiment).clickApplyFilterButton();
        getSupervisorDeskPage().waitForLoadingResultsDisappear(2, 6);
    }

    @And("^(.*) search chat (.*) on Supervisor desk$")
    public void filterByChatName(String agent, String chatName) {
        String userName = null;
        try {
            userName = getUserName(chatName);
        } catch (AssertionError e) {
            userName = chatName;
        }
        getSupervisorAndTicketsHeader(agent).setSearchInput(userName).clickApplyFilterButton();
        getSupervisorDeskPage().waitForLoadingResultsDisappear(2, 6);
    }

    @And("^Agent clears search field and filters on Supervisor desk$")
    public void clearSearchFieldAndFilter() {
        getSupervisorAndTicketsHeader("main").clearSearchFieldBox().clickApplyFilterButton();
        getSupervisorDeskPage().waitForLoadingResultsDisappear(2, 6);
    }

    @Then("^Error \"(.*)\" message is displayed$")
    public void verifyNoChatsErrorMessage(String errorMessage) {
        String message = getSupervisorDeskPage().getNoChatsErrorMessage();
        if (message.contains("\n")){
            message = message.replace("\n", "");
        }
        assertThat(errorMessage)
                .as("Correct error message should be shown")
                .isIn(message);
    }

    @Then("^\"All Channels\" and \"All Sentiments\" selected as default$")
    public void defaultLiveChatsFilters() {
        SoftAssert soft = new SoftAssert();
        soft.assertEquals(getSupervisorAndTicketsHeader("main").clickFlaggedOnlyCheckbox().getChannelFilterValue(), "All Channels", "Incorrect default Channels filter is set by default");
        soft.assertEquals(getSupervisorAndTicketsHeader("main").clickFlaggedOnlyCheckbox().getSentimentsFilterValue(), "All Sentiments", "Incorrect default Sentiments filter is set by default");
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
                format("Chat with user %s is flagged", userName));
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

    @And("^Admin filter by (.) year (.) month and (.*) days ago start date and (.) year (.) month and (.*) days ago end date$")
    public void agentFilterByMonthBeforeStartDateAndTodaySEndDate(int year, int month, int day,
                                                                  int year1, int month1, int day1) {
        LocalDate startDate = LocalDate.now().minusYears(year).minusMonths(month).minusDays(day);
        LocalDate endDate = LocalDate.now().minusYears(year1).minusMonths(month1).minusDays(day1);

        getSupervisorAndTicketsHeader("main")
                .selectStartDate(startDate)
                .selectEndDate(endDate)
                .clickApplyFilterButton();
        getSupervisorDeskPage().waitForLoadingResultsDisappear(2, 6);
    }

    @And("^Admin checks value of start date filter is empty for (\\d+) year (\\d+) month and (\\d+) days ago$")
    public void agentStartDateFilterEmptyCheck(int year, int month, int day) {
        LocalDate startDate = LocalDate.now().minusYears(year).minusMonths(month).minusDays(day);
        LocalDate endDate = LocalDate.now();

        getSupervisorAndTicketsHeader("main")
                .selectStartDate(startDate)
                .selectEndDate(endDate);
        Assert.assertTrue(getSupervisorAndTicketsHeader("main").checkStartDateFilterIsEmpty().isEmpty(), "Start date filter is not empty");
    }

    @Then("^Verify first closed chat date are fitted by filter$")
    public void verifyFirstClosedChatDateAreFittedByFilter() {
        LocalDate startDate = getSupervisorAndTicketsHeader("main").getStartDateFilterValue();
        LocalDate endDate = getSupervisorAndTicketsHeader("main").getEndDateFilterValue();
        LocalDateTime firstClosedChatDate = getSupervisorDeskPage().getSupervisorClosedChatsTable().getFirstClosedChatDate();
        verifyDateTimeIsInRangeOfTwoDates(firstClosedChatDate, startDate, endDate);
    }

    @Then("^The oldest visible chat is not more than (.*) days old$")
    public void verifyDateForOldestChat(int days){
        LocalDateTime expectedDate = LocalDateTime.now().minusDays(days);
        LocalDateTime firstClosedChatDate = getSupervisorDeskPage().getSupervisorClosedChatsTable().getFirstClosedChatDate();
        Assert.assertTrue(firstClosedChatDate.isAfter(expectedDate), "Latest chat has date:" + firstClosedChatDate
                + " which is older than: " + expectedDate);
    }

    @And("Agent can see whatsapp profile name")
    public void agentCanSeeWhatsappProfileName() {
        getSupervisorDeskPage().isUpdatedProfileNameShown();
    }

    @And("Agent click on three dot vertical menu and click on assign button")
    public void agentClickAssignButton() {
        getSupervisorDeskPage().getChatHeader().clickOnAssignButton();
    }

    @When("Assign chat modal is opened")
    public void assignChatModalOpened() {
        Assert.assertTrue(getSupervisorDeskPage().getAssignChatWindow().isAssignWindowShown());
    }

    @Then("Agent cannot initiate a payment")
    public void agentCannotInitiateAPayment() {
        Assert.assertFalse(getSupervisorDeskPage().isC2pButtonPresent(),"Supervisor Can Initiate Payment");
    }

    @Then("Supervisor does not see any Chat Transfer alert")
    public void verifyChatTransferAlertNotPresent() {
        Assert.assertFalse(getSupervisorDeskPage().verifyChatAlertIsPresent(5), "Chat alert is present");
    }

    @And("Supervisor adds a note {string}, Jira link {string} and Ticket Number {string}")
    public void addNewNote(String note, String jiraLink, String ticketNumber){
        getSupervisorDeskPage().getSupervisorRightPanel().clickOnNotesTab()
                .clickOnNewNoteButton()
                .addTextToNote(note)
                .addJiraLinkToNote(jiraLink)
                .addTicketNumberToNote(ticketNumber)
                .clickOnCreateNoteButton();
    }

    @Then("Supervisor sees note {string}, Jira link {string} and Ticket Number {string}")
    public void verifyNoteDetails(String note, String jiraLink, String ticketNumber) {
        getSupervisorDeskPage().getSupervisorRightPanel().clickOnNotesTab();

        SoftAssert soft = new SoftAssert();
        soft.assertEquals(getSupervisorDeskPage().getSupervisorRightPanel().getNoteCardText(),note,"Text inside note does not match");
        soft.assertEquals(getSupervisorDeskPage().getSupervisorRightPanel().getNoteCardJiraLink(),jiraLink,"JIRA Link does not match");
        soft.assertEquals(getSupervisorDeskPage().getSupervisorRightPanel().getNoteCardTicketNumber(),ticketNumber,"Ticket Number does not match");
        soft.assertAll();
    }

    @Then("Agent is able to close the assign chat window")
    public void agentClickCloseAssignWindow() {
        getSupervisorDeskPage().getAssignChatWindow().clickOnCloseAssignWindow();
        Assert.assertFalse(getSupervisorDeskPage().getAssignChatWindow().isAssignWindowShown(),"Assign Chat Window is closed");
    }

    @Then ("^Agent see channel and dates as filter options for tickets$")
    public void verifyChannelOptionsAndDates(List<String> channels){
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(getSupervisorAndTicketsHeader("main").expandChannels().getDropdownOptions(),  channels,
                "Channel dropdown has incorrect options");
            softAssert.assertTrue(getSupervisorAndTicketsHeader("main").isStartDateIsPresent(),"Start day option should be present in tickets");
            softAssert.assertTrue(getSupervisorAndTicketsHeader("main").isEndDateIsPresent(),"End day option should be present in tickets");
        softAssert.assertAll();
    }

    @Then ("^Agent see sentiments as filter options for tickets$")
    public void verifyFilterOptions(List<String> sentiments) {
        Assert.assertEquals(getSupervisorAndTicketsHeader("main").expandSentiments().getDropdownOptions(), sentiments,
                "Sentiment dropdown has incorrect options");
    }

    @When("^Verify if (.*) autoresponder message is shown")
    public void verifyMessagePresent(String autoresponder) {
        String actualMessage = ApiHelper.getAutoResponderMessageText(autoresponder);
        List<String> chatMessages = getSupervisorDeskPage().getTicketChatBody().getAllMessages();

        assertThat(actualMessage)
                .as(format("Verify %s message is present", autoresponder))
                .isIn(chatMessages);
    }

    private void verifyDateTimeIsInRangeOfTwoDates(LocalDateTime dateTime, LocalDate startDate, LocalDate endDate) {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(startDate.compareTo(dateTime.toLocalDate()) <= 0,
                format("One of the chats was started before filtered value. Expected: after %s, Found: %s",
                        startDate, dateTime));
        softAssert.assertTrue(endDate.compareTo(dateTime.toLocalDate()) >= 0,
                format("One of the chats was ended before filtered value. Expected: before %s, Found: %s",
                        endDate, dateTime));
        softAssert.assertAll();
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
}
