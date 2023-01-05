package steps.portalsteps;

import agentpages.tickets.TicketsTable;
import apihelper.ApiHelper;
import dbmanager.DBConnector;
import drivermanager.ConfigManager;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import steps.agentsteps.AbstractAgentSteps;
import steps.dotcontrol.DotControlSteps;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TicketsSteps extends AbstractAgentSteps {

    private List<String> shownUsers = new ArrayList<>();

    private static int initialTicketsCount;

    private static int initialTicketsCountTicketsTable;

    private TicketsTable getTicketsTable(String agent) {
        return getTicketsPage(agent).getTicketsTable();
    }

    @Then("^Message Customer Window is opened$")
    public void verifyMessageCustomerWindowIsOpened() {
        Assert.assertEquals(getTicketsPage("main").getMessageCustomerWindow().getHeader(), "Message Customer", "incorrect header Was shown for   Message Customer Window");
    }

    @Then("^Select (.*) ticket checkbox$")
    public void clickThreeDotsButton(String channel) {
        getTicketsTable("main").selectTicketCheckbox(getUserName(channel));
    }

    @When("^Agent select (.*) ticket$")
    public void selectTicket(String chanel) {
        getTicketsTable("main").getTicketByUserName(getUserName(chanel)).clickOnUserName();
    }

    @When("^Click on Message Customer button for (.*)$")
    public void clickOnMessageCustomer(String chanel) {
        getTicketsTable("main").clickAssignOpenTicketButton(getUserName(chanel));
        getTicketsPage("main").getSupervisorTicketClosedChatView().clickOnMessageCustomerOrStartChatButton();
    }

    @When("^(.*) checks chat view for closed chat is displayed$")
    public void verifyClosedChatView(String chanel) {
        Assert.assertTrue(getTicketsPage("main").getSupervisorTicketClosedChatView().isDisplayed(),
                "Chat view is not visible");
    }

    @When("^Click 'Route to scheduler' button$")
    public void clickRouteToScheduler() {
        getTicketsTable("main").clickRouteToSchedulerButton();
    }

    @When("^Click 'Assign manually' button for (.*)$")
    public void clickAssignManually(String chanel) {
        getTicketsTable("main").clickAssignManuallyButton(getUserName(chanel));
    }

    @When("^Check checkbox status for ticket rows for (.*) channel is (.*)$")
    public void checkTicketsCheckboxStatus(String channel, boolean expectedCheckboxStatus) {
        Assert.assertTrue(getTicketsTable("main").getCheckboxStatus() == expectedCheckboxStatus,
                "Checkbox status is not correct");
    }

    @Then("^Assign button is not displayed in the closed ticket tab for (.*)$")
    public void assignManuallyTopPanelButtonNotVisible(String chanel) {
        Assert.assertTrue(getTicketsTable("main").assignManuallyButtonTopPanelVisibility(getUserName(chanel)),
                "Assign button is visible");
    }

    @Then("^Hover to one of the ticket And Assign button is not displayed$")
    public void assignManuallyButtonRowNotVisible() {
        Assert.assertTrue(getTicketsTable("main").assignManuallyButtonFirstRowHoverVisibility(),
                "Assign button is visible");
    }

    @Then("^Select all checkbox is not displayed in the closed ticket tab$")
    public void verifySelectAllCheckboxNotShown() {
        Assert.assertTrue(getSupervisorAndTicketsHeader("main").isSelectAllCheckboxNotShown(),
                "'Select All checkbox' is shown.");
    }

    @When("^(.*) closed ticket for (.*)$")
    public void clickCloseButtonFor(String agent, String chanel) {
        getTicketsTable("main")
                .selectTicketCheckbox(getUserName(chanel))
                .getTicketByUserName(getUserName(chanel))
                .clickCloseButton(getUserName(chanel));
        waitFor(1000);

        if (getAgentHomePage(agent).getAgentFeedbackWindow().isAgentFeedbackWindowShown()) {
            getAgentHomePage(agent).getAgentFeedbackWindow().waitForLoadingData().clickCloseButtonInCloseChatPopup();
        }
    }

    @When("^(.*) clicks closed ticket button for (.*)$")
    public void clickCloseButton(String agent, String chanel) {
        getTicketsTable(agent)
                .selectTicketCheckbox(getUserName(chanel))
                .getTicketByUserName(getUserName(chanel))
                .clickCloseButton(getUserName(chanel));
    }

    @When("^(.*) closes ticket manually$")
    public void closeTicketManually(String agent) {
        getTicketsPage("main").getSupervisorTicketClosedChatView().clickOnCloseTicketButton();

        waitFor(1000);

        if (getAgentHomePage(agent).getAgentFeedbackWindow().isAgentFeedbackWindowShown()) {
            getAgentHomePage(agent).getAgentFeedbackWindow().waitForLoadingData().clickCloseButtonInCloseChatPopup();
        }
    }

    @When("^(.*) accept ticket for (.*)$")
    public void clickAcceptButtonFor(String chanel) {
        getTicketsTable("main")
                .clickAcceptButton(getUserName(chanel));
    }

    @When("^Supervisor is able to view the columns in the tickets tab$")
    public void checkTicketColumnsVisibility(List<String> expectedColumnNames) {
        Assert.assertEquals(getTicketsPage("main").getTicketsColumnHeaders(), expectedColumnNames,
                "Closed chat column is not visible");
    }

    @When("^(.*) checks closed ticket is (.*)")
    public void checkCloseButtonStatus(String agent, String buttonStatus) {
        if (buttonStatus.equalsIgnoreCase("disabled")) {
            Assert.assertTrue(getTicketsTable(agent).closeButtonStatus(), "Close ticket button is enabled");
        } else if (buttonStatus.equalsIgnoreCase("enabled")) {
            Assert.assertFalse(getTicketsTable(agent).closeButtonStatus(), "Close ticket button is disabled");
        }
    }

    @Then("^(.*) hover to the close ticket button and see (.*) message$")
    public void hoverCloseTicketButton(String agent, String toolTipMessage) {
        getTicketsPage("main").getSupervisorTicketClosedChatView().hoverCloseTicket();
        Assert.assertEquals(getTicketsPage(agent).getToolTipText(), toolTipMessage, "Closed ticket tool tip message is wrong");
    }

    @When("^(.*) checks closed ticket button in quick action bar is disabled$")
    public void checkCloseButtonStatusQuickActionBar(String agent) {
        Assert.assertTrue(getTicketsPage(agent).getTicketsQuickActionBar().closeButtonStatusQuickAction(),
                "Close ticket button in quick action bar is enabled");
    }

    @When("^Supervisor clicks on first ticket$")
    public void openFirstTicket() {
        getTicketsTable("main").openFirstTicket();
    }

    @When("^(.*) accepts (.*) unassigned tickets$")
    public void acceptTickets(String agent, String numberOfTickets) {
        getTicketsPage(agent).getTicketsQuickActionBar().inputNumberOfTicketsForAccept(numberOfTickets);
        getTicketsPage(agent).getTicketsQuickActionBar().clickAcceptButtonInHeader();
    }

    @When("^(.*) inputs (.*) unassigned tickets for acceptance in custom bar$")
    public void manualAcceptTicketsEntry(String agent, String numberOfTickets) {
        getTicketsPage(agent).getTicketsQuickActionBar().inputNumberOfTicketsForAccept(numberOfTickets);
    }

    @Then("^Verify that only \"(.*)\" tickets chats are shown$")
    public void verifyTicketsChatsChannelsFilter(String channelName) {
        Assert.assertTrue(getTicketsTable("main").verifyChanelOfTheTicketsIsPresent(channelName),
                channelName + " channel name should be shown.");
    }

    @Then("^Verify that only \"(.*)\" channel tickets chats are shown$")
    public void verifyTicketsChatsChannelsFilterUsingAttribute(String channelName) {
        Assert.assertTrue(getTicketsTable("main").verifyCurrentChanelOfTheTickets(channelName),
                channelName + " channel name is not shown.");
    }

    @Then("^Verify that only \"(.*)\" date tickets are shown in (.*) column$")
    public void verifyTicketsChatsStartDatesFilter(String dateText, String columnType) {
        Assert.assertTrue(getTicketsTable("main").verifyCurrentDatesOfTheTickets(columnType, dateText),
                dateText + " open date is not shown.");
    }

    @Given("^Supervisor scroll Tickets page to the bottom$")
    public void scrollTicketsDown() {
        getTicketsTable("main").scrollTicketsToTheButtom()
                .waitForMoreTicketsAreLoading(2, 5);
    }

    @Then("^(.*) is the current agent of (.*) ticket$")
    public void verifyCurrentAgentOfTicket(String agentName, String chanelName) {
        Assert.assertEquals(getTicketsTable("main").getTicketByUserName(getUserName(chanelName)).getCurrentAgent().substring(3), agentName,
                "The current agent of the ticket is not as expected");
    }

    @Then("^(.*) see tickets from (.*) on (?:Unassigned|Assigned|Closed) filter page$")
    public void verifyTicketPresent(String agent, String channel) {
        getTicketsTable(agent).getTicketByUserName(getUserName(channel));
    }

    @Then("^Verify that only (.*) ticket is shown$")
    public void verifyChatsChannelsFilter(int tickets) {
        Assert.assertEquals(getTicketsTable("main").getUsersNames().size(), tickets,
                "Only " + tickets + " ticket(s) number should be present on Supervisor Tickets page" +
                        "Could be because of TPLAT-5959");
        //todo uncomment step in the feature when search with spaces will be fixed
    }

    @Then("^Ticket from (.*) is not present on Supervisor Desk$")
    public void verifyTicketPresent(String channel) {
        String userName = getUserName(channel);
        boolean isTicketShown = true;
        try {
            getTicketsTable("main").getTicketByUserName(userName);
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

    @Given("^Save shown tickets$")
    public void saveChats() {
        shownUsers = getTicketsTable("main").getUsersNames();
    }

    @Given("^More tickets are loaded$")
    public void moreRecordsAreShown() {
        SoftAssert soft = new SoftAssert();
        boolean result = areNewChatsLoaded(shownUsers.size(), 4);
        soft.assertTrue(result, "New chats are not loaded\n");
        soft.assertAll();
    }

    @Then("^Tickets are sorted in (.*) order$")
    public void verifyTicketsSorting(String order) {
        List<LocalDateTime> listOfDates = getTicketsTable("main").getTicketsStartDates();
        Assert.assertTrue(isDateSorted(order, listOfDates), "Tickets are not sorted in " + order + " order");
    }

    @Then("^Verify ticket is present for (.*) for (.*) seconds$")
    public void verifyTicketIsPresentFor(String chanel, int wait) {
        boolean isTicketPresent = false;

        for (int i = 0; i < wait; i++) {
            if (!getTicketsTable("main").isTicketPresent(getUserName(chanel)))
                waitFor(1000);
            else {
                isTicketPresent = true;
            }
        }
        Assert.assertTrue(isTicketPresent, "Ticket should be present");
    }

    @Then("^Verify first closed ticket date are fitted by filter$")
    public void verifyFirstClosedTicketDateAreFittedByFilter() {
        LocalDate startDate = getSupervisorAndTicketsHeader("main").getStartDateFilterValue();
        LocalDate endDate = getSupervisorAndTicketsHeader("main").getEndDateFilterValue();
        LocalDateTime firstTicketStartDate = getTicketsTable("main").getFirstTicketOpenDates();
        LocalDateTime firstTicketEndDate = getTicketsTable("main").getFirstTicketEndDates();
        verifyDateTimeIsInRangeOfTwoDates(firstTicketStartDate, startDate, endDate);
        verifyDateTimeIsInRangeOfTwoDates(firstTicketEndDate, startDate, endDate);
    }

    @And("^Agent click on the arrow of Ticket End Date$")
    public void agentClickOnTheArrowOfTicketEndDate() {
        getTicketsTable("main").clickAscendingArrowOfEndDateColumn();
    }

    @Then("^(.*) checks quick & custom assign options on the page are (.*)$")
    public void verifyQuickActionsBarTicket(String agent, String quickBarVisibility) {
        Assert.assertTrue(getTicketsPage(agent).quickActionBarVisibility(quickBarVisibility),
                "Visibility of quick action bar is incorrect or incorrect visibility check done");
    }

    @Then("^(.*) checks ticket assigned toast message on the page appears and disappears$")
    public void verifyTicketAssignedToastMessage(String agent) {
        getTicketsPage(agent).toastMessageVisibility();
    }

    @Then("^(.*) hover over question info button and see (.*) message$")
    public void quickActionToolTipMessage(String agent, String toolTipMessage) {
        getTicketsPage(agent).getTicketsQuickActionBar().hoverQuickActionBar();
        Assert.assertEquals(getTicketsPage(agent).getToolTipText(), toolTipMessage, "Quick Action hover message is wrong");
    }

    @Then("^(.*) sees toast message with (.*) text")
    public void verifyToastMessage(String agent, String toastMessageText) {
        Assert.assertEquals(getTicketsPage(agent).getToastMessageText(), toastMessageText, "Toast message is wrong");
    }

    @Then("^(.*) sees closed ticket toast message for (.*) channel")
    public void verifyClosedTicketToastMessage(String agent, String channel) {
        Assert.assertEquals(getTicketsPage(agent).getToastMessageText(),
                "Ticket with " + getUserName(channel) + " has been closed.", "Toast message is wrong");
    }

    @Then("^(.*) checks initial ticket count is displayed in the (.*) ticket tab on (.*)$")
    public void getInitialTicketsCount(String agent, String ticketType, String platformType) {
        initialTicketsCount = getTicketsCountLeftMenu(agent, ticketType, platformType);
    }

    @Then("^(.*) checks final ticket count value in the (.*) ticket tab on (.*)$")
    public void verifyFinalTicketsCount(String agent, String ticketType, String platformType) {
        int finalTicketsCount = getTicketsCountLeftMenu(agent, ticketType, platformType);
        Assert.assertEquals(finalTicketsCount, (initialTicketsCount + 1),
                "Final ticket count is incorrect");
    }

    @Then("^(.*) checks ticket count value in the (.*) ticket tab is (.*) on (.*)$")
    public void verifyTicketsCount(String agent, String ticketType, int expectedTicketCount, String platformType) {
        int ticketsCount = getTicketsCountLeftMenu(agent, ticketType, platformType);
        Assert.assertEquals(ticketsCount, expectedTicketCount,
                "Ticket count is incorrect");
    }

    @Then("^(.*) checks ticket count value greater than 1 in (.*) ticket tab on (.*)$")
    public void verifyTicketsCount(String agent, String ticketType, String platformType) {
        int ticketsCount = getLeftMenu(agent).getSupervisorAndTicketsPart().getTicketsCount(platformType, ticketType.toLowerCase());
        Assert.assertTrue(ticketsCount > 1,
                "Ticket count is incorrect in left tab");
    }

    @Then("^(.*) checks ticket count greater than 1 in tickets table$")
    public void verifyTicketsCountGreaterThan1InTicketsTable(String agent) {
        Assert.assertTrue(getTicketsTable(agent).getTicketsCount() > 1,
                "Ticket count in middle pane is incorrect");
    }

    @Then("^(.*) checks initial ticket count in the ticket table$")
    public int getInitialTicketsCountTicketsTable(String agent) {
        return initialTicketsCountTicketsTable = getTicketsTable(agent).getTicketsCount();
    }

    @Then("^(.*) checks final ticket count value in the tickets table is (.*) than initial ticket count$")
    public void verifyFinalTicketsCountTicketsTable(String agent, String finalTicketsCountParameter) {
        waitFor(1500);
        int finalTicketsCount = getTicketsTable(agent).getTicketsCount();
        if (finalTicketsCountParameter.equalsIgnoreCase("less")) {
            Assert.assertTrue(finalTicketsCount < initialTicketsCountTicketsTable,
                    "Final ticket count is incorrect");
        } else if (finalTicketsCountParameter.equalsIgnoreCase("more")) {
            Assert.assertTrue(finalTicketsCount > initialTicketsCountTicketsTable,
                    "Final ticket count is incorrect");
        }
    }

    @Then("^(.*) checks ticket count value (.*) in tickets table$")
    public void verifyTicketsCountEquals1InTicketsTable(String agent, int expectedTicketCount) {
        Assert.assertEquals(getTicketsTable(agent).getTicketsCount(), expectedTicketCount,
                "Ticket count in middle pane is incorrect");
    }

    public int getTicketsCountLeftMenu(String agent, String platformType, String ticketType) {
        return getLeftMenu(agent).getSupervisorAndTicketsPart().getTicketsCount(platformType, ticketType);
    }

    private boolean areNewChatsLoaded(int previousChats, int wait) {
        for (int i = 0; i < wait * 2; i++) {
            if (getTicketsTable("main").getUsersNames().size() > previousChats) return true;
            else waitFor(500);
        }
        return false;
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
