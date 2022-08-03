package steps.agentsteps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class AgentFilteringSteps extends AbstractAgentSteps {
    private final ThreadLocal<LocalDate> startDate = new ThreadLocal<>();
    private final ThreadLocal<LocalDate> endDate = new ThreadLocal<>();

    @Then("^Verify filtered tickets dates are fitted by filter for (.*)$")
    public void verifyTicketsDatesAreFittedByFilter(String agent) {
        List<String> foundUserNamesTicketsChats = getAgentHomePage(agent).getLeftMenuWithChats().getAllFoundChatsUserNames();
        for(String userName : foundUserNamesTicketsChats) {
            getAgentHomePage(agent).getLeftMenuWithChats().openChatByUserName(userName);
            LocalDateTime currentTicketChatDateTime = getAgentHomePage(agent).getChatHeader().getDateTime();
            verifyDateTimeIsInRangeOfTwoDates(currentTicketChatDateTime, this.startDate.get(), this.endDate.get());
        }
    }

    private void verifyDateTimeIsInRangeOfTwoDates(LocalDateTime dateTime, LocalDate startDate, LocalDate endDate) {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(startDate.compareTo(dateTime.toLocalDate()) <= 0,
                String.format("One of the tickets was reported before start date. Expected: after %s, Found: %s",
                        startDate, dateTime));
        softAssert.assertTrue(endDate.compareTo(dateTime.toLocalDate()) >= 0,
                String.format("One of the tickets was reported after end date. Expected: before %s, Found: %s",
                        endDate, dateTime));
        softAssert.assertAll();
    }

    @And("^(?!Admin)(.*) filter by (\\d+) year (\\d+) month and (\\d+) days ago start date and today's end date$")
    public void agentFilterByYearMonthAndDaysAgoStartDateAndTodaySEndDate(String agent, int year, int month, int day) {
        LocalDate startDate = LocalDate.now().minusYears(year).minusMonths(month).minusDays(day);
        LocalDate endDate = LocalDate.now();
        getAgentHomePage(agent).getLeftMenuWithChats().applyTicketsFilters("no", "no", startDate, endDate);
        this.startDate.set(startDate);
        this.endDate.set(endDate);
    }

    @And("^Check value of date filter for (?!Admin)(.*) should be empty for start date filter (\\d+) year (\\d+) month and (\\d+) days ago$")
    public void agentStartDateFilterEmptyCheck(String agent, int year, int month, int day) {
        getAgentHomePage(agent).getLeftMenuWithChats().checkStartDateFilterEmpty();
    }

    @And("^Check for (?!Admin)(.*) that back button is (.*) in calendar for (.*) filter 3 months ago$")
    public void backButtonDisability(String agent, String visibility, String filterType) {
        getAgentHomePage(agent).getLeftMenuWithChats().checkBackButtonNotVisibleThreeMonthsBack(filterType);
    }

    @When("^(.*) filter closed chats with (.*) channel, (.*) sentiment and flagged is (.*)$")
    public void setLiveChatsFilter(String agent, String channel, String sentiment, boolean flagged){
        getLeftMenu(agent).applyTicketsFilters(channel.trim(), sentiment.trim(), flagged);
    }

    @Then("^(.*) see only (.*) chats in left menu$")
    public void agentSeeOnlyAppleBusinessChatChatsInLeftMenu(String agent, String channelName) {
        Assert.assertTrue( getLeftMenu(agent).verifyChanelOfTheChatsIsPresent(channelName),
                channelName + " channel name should be shown.");

    }
}
