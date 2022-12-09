package steps.portalsteps;

import agentpages.tickets.TicketsTable;
import apihelper.ApiHelper;
import dbmanager.DBConnector;
import drivermanager.ConfigManager;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import steps.dotcontrol.DotControlSteps;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static steps.agentsteps.AbstractAgentSteps.*;

public class TicketsSteps extends AbstractPortalSteps{

    private List<String> shownUsers = new ArrayList<>();

    private TicketsTable getTicketsTable(String agent){
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

    @When("^Click 'Route to scheduler' button$")
    public void clickRouteToScheduler() {
        getTicketsTable("main").clickRouteToSchedulerButton();
    }

    @When("^Click 'Assign manually' button for (.*)$")
    public void clickAssignManually(String chanel) {
        getTicketsTable("main").clickAssignManuallyButton(getUserName(chanel));
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

    @When("^(.*) closed ticket for (.*)$")
    public void clickCloseButtonFor(String agent, String chanel) {
        getTicketsTable("main")
                .selectTicketCheckbox(getUserName(chanel))
                .clickCloseButton(getUserName(chanel));
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

    @When("^(.*) checks closed ticket is disabled$")
    public void checkCloseButtonStatus(String agent) {
        Assert.assertTrue(Boolean.parseBoolean(getTicketsTable(agent).closeButtonStatus()), "Close ticket button is enabled");
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

    @Then("^Verify that only \"(.*)\" tickets chats are shown$")
    public void verifyTicketsChatsChannelsFilter(String channelName) {
        Assert.assertTrue(getTicketsTable("main").verifyChanelOfTheTicketsIsPresent(channelName),
                channelName + " channel name should be shown.");
    }

    @Given("^Supervisor scroll Tickets page to the bottom$")
    public void scrollTicketsDown() {
        getTicketsTable("main").scrollTicketsToTheButtom()
                .waitForMoreTicketsAreLoading(2,5);
    }

    @Then("^(.*) is the current agent of (.*) ticket$")
    public void verifyCurrentAgentOfTicket(String agentName, String chanelName) {
        Assert.assertEquals(getTicketsTable("main").getTicketByUserName(getUserName(chanelName)).getCurrentAgent().substring(3),agentName,
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

    private boolean areNewChatsLoaded(int previousChats, int wait){
        for(int i = 0; i< wait*2; i++){
            if(getTicketsTable("main").getUsersNames().size() > previousChats) return true;
            else waitFor(500);
        }
        return false;
    }

    @Then("^Tickets are sorted in (.*) order$")
    public void verifyTicketsSorting(String order) {
        List<LocalDateTime> listOfDates = getTicketsTable("main").getTicketsStartDates();
        Assert.assertTrue(isDateSorted(order, listOfDates), "Tickets are not sorted in " + order + " order");
    }

    @Then("^Verify ticket is present for (.*) for (.*) seconds$")
    public void verifyTicketIsPresent(String chanel, int wait) {
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
        Assert.assertEquals(getTicketsPage(agent).getQuickActionToolTipText(),toolTipMessage,"Quick Action hover message is wrong");
    }

    @Then("^(.*) sees toast message with (.*) text")
    public void verifyToastMessage(String agent, String toastMessageText) {
        Assert.assertEquals(getTicketsPage(agent).getToastMessageText(),toastMessageText,"Toast message is wrong");
    }
}
