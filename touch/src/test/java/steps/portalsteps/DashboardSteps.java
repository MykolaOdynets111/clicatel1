package steps.portalsteps;

import agentpages.AgentHomePage;
import agentpages.dashboard.uielements.CustomersHistory;
import agentpages.dashboard.uielements.LiveAgentRowDashboard;
import agentpages.dashboard.uielements.LiveAgentsCustomerRow;
import apihelper.ApiCustomerHistoryHelper;
import apihelper.ApiHelper;
import apihelper.ApiHelperTie;
import datamanager.Agents;
import datamanager.Tenants;
import datamanager.jacksonschemas.AvailableAgent;
import driverfactory.DriverFactory;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DashboardSteps extends AbstractPortalSteps {

    private final ThreadLocal<String> channel = new ThreadLocal<>();
    private final ThreadLocal<String> period = new ThreadLocal<>();
    private final ThreadLocal<Integer> npsPassivesPercentage = new ThreadLocal<>();
    private final ThreadLocal<Double> actualCustomerSatisfactionScoreOld = new ThreadLocal<>();
    private final ThreadLocal<Double> actualCustomerSatisfactionScoreNew = new ThreadLocal<>();
    @And("^Admin click on Customers Overview dashboard tab$")
    public void agentClickOnCustomersOverviewDashboardTab() {
        getDashboardPage().clickOnCustomersOverviewTab();
    }

    @And("^Admin click on Agents Performance dashboard tab$")
    public void adminClickOnAgentsPerformanceDashboardTab() {
        getDashboardPage().clickOnAgentsPerformanceTab();
    }

    @And("^Admin click on Customers History on dashboard$")
    public void agentClickOnCustomersHistory() {
        getDashboardPage().getCustomersOverviewTab().clickOnCustomersHistory();
    }

    @And("^Admin click on Live Customers on dashboard$")
    public void agentClickOnLiveCustomers() {
        getDashboardPage().getCustomersOverviewTab().clickOnLiveCustomers();
    }

    @Then("^Admin is able to see (.*) graphs$")
    public void adminIsAbleToSeeNPSLiveChatsByChannelPastSentimentGraphs(String graphs) {
        String[] myArray = graphs.split(",");

        for (String graph : myArray) {
            if (graph.trim().equalsIgnoreCase("Net Promoter Score")) {
                softAssert.assertTrue(getDashboardPage().getNetPromoterScoreSection().isPromoterScoreBarsDisplayed(),
                        String.format("Promoter Score Bars is not displayed for section %s", graph));
                softAssert.assertTrue(getDashboardPage().getNetPromoterScoreSection().isPromoterScorePieDisplayed(),
                        String.format("Promoter Score Pie is not displayed for section %s", graph));
            }
            if (graph.trim().equalsIgnoreCase("Customer Satisfaction")) {
                softAssert.assertTrue(getDashboardPage().getCustomerSatisfactionSection()
                                .isCustomerSatisfactionScoreDisplayed(),
                        String.format("Customer Satisfaction Score is not displayed for section %s", graph));
            }
            softAssert.assertTrue(getCustomersHistory().isGraphDisplayed(graph),
                    String.format("%s Graph is not Displayed", graph));
            softAssert.assertTrue(getCustomersHistory().isNoDataRemovedForGraph(graph),
                    String.format("No data is displayed for %s Graph", graph));
        }
        softAssert.assertAll();
    }

    @And("^Admin filter Customers History by channel and period$")
    public void adminFilterCustomersHistoryByWebchatAndPastDay(DataTable datatable) {
        List<List<String>> data = datatable.cells();
        for (List<String> a : data) {
//            System.out.println(a);
            this.channel.set(a.get(0));
            this.period.set(a.get(1));
            getDashboardPage().getCustomersOverviewTab().selectChannelForReport(channel.get());
            getDashboardPage().getCustomersOverviewTab().selectPeriodForReport(period.get());
            SoftAssert softAssert = new SoftAssert();
            softAssert.assertTrue(getCustomersHistory().isGraphFilteredBy(channel.get(), period.get()),
                    String.format("Graph is not filtered by %s channel and %s period", channel, period));
            softAssert.assertAll();
        }
    }

    @And("^Admin filter Customers History by (?!.*period and)(.*) channel$")
    public void adminFilterCustomersHistoryByChannel(String channel) {
        this.channel.set(channel);
        getDashboardPage().getCustomersOverviewTab().selectChannelForReport(channel);
    }

    @And("^Admin filter Customers History by (?!.*channel and)(.*) period$")
    public void adminFilterCustomersHistoryByPeriod(String period) {
        this.period.set(period);
        getDashboardPage().getCustomersOverviewTab().selectPeriodForReport(period);
    }

    @Then("^Admin see all graphs filtered by (.*) channel and (.*) period$")
    public void adminSeeAllGraphsFilteredByWebchatChannelAndPastDayPeriod(String channel, String period) {
        softAssert.assertTrue(getCustomersHistory().isGraphFilteredBy(channel, period),
                String.format("Graph is not filtered by %s channel and %s period", channel, period));
        softAssert.assertAll();
    }

    @Then("^Admin should see no live chats message in Live Chats by Channel$")
    public void adminShouldSeeNoLiveChatsMessageInLiveChatsByChannel() {
        Assert.assertEquals(getDashboardPage().getLiveChatsByChannel().getNoLiveChatsDisplayedText(), "There is no recent live chat engagements",
                "No live chats message is not displayed in Live Chats By Channel or has other text");
    }

    @Then("^Admin should see Web Chat chart in Live Chats by Channel$")
    public void adminShouldSeeWebChatChartInLiveChatsByChannel() {
        Assert.assertTrue(getDashboardPage().getLiveChatsByChannel().isWebChatChartIsDisplayed(),
                "Web Chat chart is not displayed in Live Chats By Channel");
    }

    @Then("^Admin should see Apple Business Chat chart in Live Chats by Channel$")
    public void adminShouldSeeAbcChartInLiveChatsByChannel() {
        Assert.assertTrue(getDashboardPage().getLiveChatsByChannel().isAbcChartIsDisplayed(),
                "ABC chart is not displayed in Live Chats By Channel");
    }

    @Then("^Admin should see WhatsApp chart in Live Chats by Channel$")
    public void adminShouldSeeWhatsAppChartInLiveChatsByChannel() {
        Assert.assertTrue(getDashboardPage().getLiveChatsByChannel().isWhatsAppChartIsDisplayed(),
                "WhatsApp chart is not displayed in Live Chats By Channel");
    }

    @Then("^Admin should see (.*) charts in General sentiment per channel$")
    public void adminShouldSeeAbcChartInGeneralSentimentPerChannel(String channel) {
        Assert.assertTrue(getDashboardPage().getGeneralSentimentPerChannel().isChartsForChannelShown(channel),
                String.format("%s chart is not displayed in General sentiment per channel", channel));
    }

    @Then("^Admin should see (.*) charts in Attended vs. Unattended Chats$")
    public void adminShouldSeeAbcChartInAttendedVsUnattendedChats(String channel) {
        Assert.assertTrue(getDashboardPage().getAttendedVsUnattendedChats().isChartsForChannelShown(channel),
                String.format("%s chart is not displayed in Attended vs. Unattended Chats", channel));
    }

    @Then("^'No Active agents' on Agents Performance tab shown if there is no online agent$")
    public void noActiveAgentsOnAgentsPerformanceTabShownIfThereIsNoOnlineAgent() {
        if (ApiHelper.getNumberOfLoggedInAgents() == 0) {
            //need to be discussed why there's active agents which API doesn't see
            Assert.assertTrue(getDashboardPage().getAgentPerformanceTab().isNoActiveAgentsMessageDisplayed(),
                    "'No active agents' are not shown while there is no logged in agents. " +
                            "Could be affected by TPLAT-5990");
        }
    }

    @And("^Admin see all information about the (.*) is filled under active agent tab$")
    public void adminSeeAllInformationAboutTheSecondAgentIsFilledUnderActiveAgentTab(String agent) {
        AvailableAgent availableAgent = getAvailableAgent(agent);
        LiveAgentRowDashboard agentRow = getDashboardPage().getAgentsTableDashboard()
                .getTargetAgentRow(availableAgent.getName(), availableAgent.getSurname());

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(agentRow.isLiveChatsNumberShown(),
                String.format("Live chats number is not shown for agent %s", availableAgent.getAgentFullName()));
        softAssert.assertTrue(agentRow.isChannelsNumberShown(),
                String.format("Channels number is not shown for agent %s", availableAgent.getAgentFullName()));
        softAssert.assertTrue(agentRow.isSentimentsShown(),
                String.format("Sentiments are not shown for agent %s", availableAgent.getAgentFullName()));
        softAssert.assertTrue(agentRow.isAverageDurationShown(),
                String.format("Average duration is not shown for agent %s", availableAgent.getAgentFullName()));
        softAssert.assertAll();
    }

    @Then("^Correct number of active chats shown for (.*)$")
    public void verifyChatConsoleAgentsContainsChats(String agent) {
        AvailableAgent availableAgent = getAvailableAgent(agent);
        int activeChatsFromChatdesk = new AgentHomePage("second agent").getLeftMenuWithChats().getNewChatsCount();
        Assert.assertEquals(getDashboardPage().getAgentsTableDashboard()
                        .getTargetAgentRow(availableAgent.getName(), availableAgent.getSurname())
                        .getLiveChatsNumber(),
                activeChatsFromChatdesk,
                availableAgent.getAgentFullName() + " icon has incorrect number of active chats");

    }

    @When("^Admin clicks expand arrow for (.*)$")
    public void expandAgentsRowInChatConsole(String agent) {
        AvailableAgent availableAgent = getAvailableAgent(agent);
        getDashboardPage().getAgentsTableDashboard()
                .getTargetAgentRow(availableAgent.getName(), availableAgent.getSurname())
                .clickExpandButton();
    }

    private AvailableAgent getAvailableAgent(String agent) {
        return ApiHelper.getAvailableAgents().stream()
                .filter(e -> e.getEmail().equalsIgnoreCase(
                        Agents.getAgentFromCurrentEnvByTenantOrgName(Tenants.getTenantUnderTestOrgName(), agent).getAgentEmail()))
                .findFirst().get();
    }

    @When("^Admin clicks expand agents performance table arrow for (.*) department$")
    public void adminClicksExpandAgentsPerformanceTableArrow(String agent) {
        AvailableAgent availableAgent = getAvailableAgent(agent);
        String department;
        if (availableAgent.getDepartments().isEmpty())
            department = "Agents without department";
        else
            department = (String) availableAgent.getDepartments().get(0);
        getDashboardPage().getAgentPerformanceTab().clickExpandAgentsTableForDepartmentButton(department);
    }

    @Then("^Admin see all chats info including intent on user message (.*)$")
    public void adminSeeAllChatsInfoIncludingIntentOnUserMessageConnectToAgent(String userMessage) {
        SoftAssert softAssert = new SoftAssert();

        String userId = getUserNameFromLocalStorage(DriverFactory.getTouchDriverInstance());
        String sentiment = ApiHelperTie.getTIESentimentOnMessage(userMessage);
        String intent = ApiHelperTie.getListOfIntentsOnUserMessage(userMessage).get(0).getIntent();

        LiveAgentsCustomerRow customerRow = getDashboardPage().getAgentsTableDashboard().getOpenCustomersRow(userId);

        softAssert.assertEquals(sentiment, customerRow.getSentiment().toUpperCase(),
                String.format("Sentiment is wrong for %s user", userId));
        //need to be investigated
        softAssert.assertEquals(customerRow.getIntent(), intent,
                String.format("Intent is wrong for %s user", userId));

        softAssert.assertAll();

    }

    @Then("^Admin can see 'Welcome to the Chat Desk Dashboard'$")
    public void adminCanSeeWelcomeToTheChatDeskDashboard() {
        Assert.assertTrue(getDashboardPage().isWelcomeToTheChatDeskDashboardDisplayed(),
                "'Welcome to the Chat Desk Dashboard' is not displayed");
    }

    @When("^Admin click on Launch Supervisor Desk button$")
    public void adminClickOnLaunchSupervisorDeskButton() {
        getDashboardPage().clickLaunchSupervisor();
        List<String> windowHandles = new ArrayList<>(DriverFactory.getDriverForAgent("main").getWindowHandles());
        DriverFactory.getDriverForAgent("main").switchTo().window(windowHandles.get(windowHandles.size() - 1));
    }

    @When("^Admin click on Launch Agent Desk button$")
    public void adminClickOnLaunchAgentDeskButton() {
        getDashboardPage().clickLaunchAgentDesk();
        List<String> windowHandles = new ArrayList<>(DriverFactory.getDriverForAgent("main").getWindowHandles());
        DriverFactory.getDriverForAgent("main").switchTo().window(windowHandles.get(windowHandles.size() - 1));
    }

    @Then("^Admin see the message no data for Past Sentiment graph if there is no available data$")
    public void adminSeeTheMessageNoDataToReportAtTheMomentForPastSentimentGraphIfThereIsNoAvailableData() {
        if (ApiCustomerHistoryHelper.getPastSentimentReport(Tenants.getTenantUnderTestOrgName(), period.get(), channel.get()).isEmpty()) {
            Assert.assertTrue(getCustomersHistory().isNoDataDisplayedForGraph("Past Sentiment"),
                    "No data is not displayed for Past Sentiment Graph");
        }
    }

    @Then("^Admin see the message no data for Customer Satisfaction graph if there is no available data$")
    public void adminSeeTheMessageNoDataForCustomerSatisfactionGraphIfThereIsNoAvailableData() {
        if (ApiCustomerHistoryHelper.getCustomerSatisfactionReport(Tenants.getTenantUnderTestOrgName(), period.get(), channel.get()).isEmpty()) {
            Assert.assertTrue(getCustomersHistory().isNoDataDisplayedForGraph("Customer Satisfaction"),
                    "No data is displayed for Past Sentiment Graph");
        }
    }

    @Then("^Admin see the message no data for Customer Satisfaction gauge if there is no available data$")
    public void adminSeeTheMessageNoDataForCustomerSatisfactionGaugeIfThereIsNoAvailableData() {
        if (ApiCustomerHistoryHelper.getCustomerSatisfactionReport(Tenants.getTenantUnderTestOrgName(), period.get(), channel.get()).isEmpty()) {
            Assert.assertTrue(getDashboardPage().getCustomersHistory().isNoGaugeDisplayedForGraph("Customer Satisfaction"),
                    "No data is displayed for Past Sentiment Graph");
        }
    }

    @Then("^Admin can see Settings page with options Business Profile, Chat tags, Auto Responders, Preferences, Surveys$")
    public void adminCanSeeSettingsPageWithOptionsBusinessProfileChatTagsAutoRespondersPreferencesSurveys() {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(getDashboardSettingsPage().isBusinessProfileTabShown(),
                "Business Profile tab is not displayed on Settings page");
        softAssert.assertTrue(getDashboardSettingsPage().isChatTagsTabShown(),
                "Chat Tags tab is not displayed on Settings page");
        softAssert.assertTrue(getDashboardSettingsPage().isAutoRespondersTabShown(),
                "Auto Responders tab is not displayed on Settings page");
        softAssert.assertTrue(getDashboardSettingsPage().isPreferencesTabShown(),
                "Preferences tab is not displayed on Settings page");
        softAssert.assertTrue(getDashboardSettingsPage().isSurveysTabShown(),
                "Surveys tab is not displayed on Settings page");
        softAssert.assertAll();
    }

    @Then("^Admin is able to see the CSAT scale having down scale as (.*) and upscale as (.*)$")
    public void adminIsAbleToSeeTheAverageCSATSurveyScaleHavingUpAndDownScale(String downScale, String upScale) {
        Assert.assertTrue(getCustomersHistory().isGraphContainsScale(downScale, upScale), "Down and up scale parameters are not present");
    }

    @Then("^Admin is able to see the y axis CSAT scale having down scale as (.*) and upscale as (.*)$")
    public void adminIsAbleToSeeYAxisCSATSurveyScaleHavingUpAndDownScale(String downScale, String upScale) {
        Assert.assertTrue(getDashboardPage().getCustomersHistory().isYAxisContainsScale(downScale, upScale), "Down and up scale parameters are not present");
    }

    @Then("^Admin is able to see the average CSAT survey response converted to (\\d+)-(\\d+)$")
    public void adminIsAbleToSeeTheAverageCSATSurveyResponseConvertedTo(double from, double to) {
        if (getCustomersHistory().isNoDataDisplayedForGraph("Customer Satisfaction")) {
            actualCustomerSatisfactionScoreOld.set(0.0);
        } else {
            actualCustomerSatisfactionScoreOld.set(getDashboardPage().getCustomerSatisfactionSection()
                    .getCustomerSatisfactionScore());
        }
        softAssert.assertTrue(actualCustomerSatisfactionScoreOld.get() >= from, "Customer Satisfaction Score is less then " + from);
        softAssert.assertTrue(actualCustomerSatisfactionScoreOld.get() <= to, "Customer Satisfaction Score is more then " + to);
        softAssert.assertAll();
    }

    @Then("^Admin is able to see (.*) in the (.*) against the agent$")
    public void adminIsAbleToSeeTheNoDataShowAlertText(String expectedText, String graphName) {
        try {
            Assert.assertTrue(getDashboardPage().getCustomersHistory()
                    .isNoDataAlertMessageText(graphName).contains(expectedText), "Alert message doesn't get required text");
        } catch (NoSuchElementException | TimeoutException e) {
            adminIsAbleToSeeNPSLiveChatsByChannelPastSentimentGraphs(graphName);
        }
    }

    //Use this step to fetch after CSAT ratings to compare before and after CSAT rating

    @Then("^Admin is able to see the new average CSAT survey response converted to (\\d+)-(\\d+)$")
    public void adminIsAbleToSeeTheNewAverageCSATSurveyResponseConvertedTo(double from, double to) {
        if (getDashboardPage().getCustomersHistory().isNoDataDisplayedForGraph("Customer Satisfaction")) {
            System.out.println("Since no scores are given yet, CSAT score is not there");
            actualCustomerSatisfactionScoreNew.set(0.0);
        } else {
            actualCustomerSatisfactionScoreNew.set(getDashboardPage().getCustomerSatisfactionSection()
                    .getCustomerSatisfactionScore());
        }
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(actualCustomerSatisfactionScoreNew.get() >= from, "Customer Satisfaction Score is less then " + from);
        softAssert.assertTrue(actualCustomerSatisfactionScoreNew.get() <= to, "Customer Satisfaction Score is more then " + to);
        softAssert.assertAll();
    }
    //Use this step to compare before and after CSAT rating

    @Then("^Admin is able to see the same average CSAT rating for NPS response$")
    public void adminIsAbleToSameCSATRating() {
        Assert.assertTrue(actualCustomerSatisfactionScoreOld.get().equals(actualCustomerSatisfactionScoreNew.get()), "Customer Satisfaction Score are different: actual: " + actualCustomerSatisfactionScoreOld + " expected: " + actualCustomerSatisfactionScoreNew);
    }
    //Use this step to compare before and after CSAT rating

    @Then("^Admin is able to see the different average CSAT rating for CSAT response$")
    public void adminIsAbleToDifferentCSATRating() {
        Assert.assertTrue(actualCustomerSatisfactionScoreOld.get() <= actualCustomerSatisfactionScoreNew.get(), "Customer Satisfaction Score are different: actual: " + actualCustomerSatisfactionScoreOld + " expected: " + actualCustomerSatisfactionScoreNew);
    }
    @Then("^Admin see the Net Promoter Score as negative$")
    public void adminSeeTheNetPromoterScoreAsNegative() {
        Assert.assertTrue(getDashboardPage().getNetPromoterScoreSection().getNetPromoterScore() < 0,
                "Net Promoter Score is positive");
    }

    @Then("^Verify admin can see number of sentiments when hover over web chat under General sentiment per channel$")
    public void verifyAdminCanSeeNumberOfSentimentsWhenHoverOverWebChatUnderGeneralSentimentPerChannel() {
        Assert.assertTrue(getDashboardPage()
                        .getGeneralSentimentPerChannel()
                        .isNumberOfSentimentsShownForAllSentimentsCharts(),
                "Number of sentiments is not shown for all general sentiments charts");
    }

    @Then("^Verify admin can see number of positive sentiment chats when hover over (.*) channel$")
    public void verifyAdminCanSeeNumberOfPositiveSentimentChatsWhenHoverOverChannel(String channel) {
        Assert.assertTrue(getDashboardPage()
                        .getGeneralSentimentPerChannel()
                        .isNumberOfSentimentsShownForAllSentimentsCharts(channel),
                String.format("Number of positive sentiment chats is not shown after hovering on %s chart", channel));
    }

    @Then("^Verify admin can see number of neutral sentiment chats when hover over (.*) channel$")
    public void verifyAdminCanSeeNumberOfNeutralSentimentChatsWhenHoverOverChannel(String channel) {
        Assert.assertTrue(getDashboardPage()
                        .getGeneralSentimentPerChannel()
                        .isNumberOfSentimentsShownForAllSentimentsCharts(channel),
                String.format("Number of positive sentiment chats is not shown after hovering on %s chart", channel));
    }

    @Then("^Admin should see live customers section$")
    public void adminShouldSeeLiveCustomersSection() {
        Assert.assertTrue(getDashboardPage().getLiveCustomersTab().isLiveCustomersTabOpened(),
                "Live Customers tab is not opened");
    }

    @Then("^Admin should see customer history section$")
    public void adminShouldSeeCustomerHistorySection() {
        Assert.assertTrue(getCustomersHistory().isCustomerHistoryTabOpened(),
                "Customer History tab is not opened");
    }

    @When("^Admin click on Departments Management button$")
    public void adminClickOnDepartmentsManagementButton() {
        getDashboardPage().clickDepartmentsManagement();
    }

    @And("^Admin save the percentage for passives from NPS$")
    public void adminSaveThePercentageForPassivesFromNPS() {
        npsPassivesPercentage.set(getDashboardPage().getNetPromoterScoreSection().getPassivePercentage());
    }

    @And("^Admin see the percentage for passives from NPS is increased$")
    public void adminSeeThePercentageForPassivesFromNPSIsIncreased() {
        int actualNpsPassivePercentage = getDashboardPage().getNetPromoterScoreSection().getPassivePercentage();
        Assert.assertTrue(actualNpsPassivePercentage > npsPassivesPercentage.get(),
                "The percentage for passives from NPS is not increased");
        npsPassivesPercentage.remove();
    }

    @Then("^Verify admin can see number of attended vs unattended chats when hover over web chat$")
    public void verifyAdminCanSeeNumberOfAttendedVsUnattendedChatsWhenHoverOverWebChat() {
        Assert.assertTrue(getDashboardPage()
                        .getAttendedVsUnattendedChats()
                        .isNumberOfAttendedVsUnattendedChatsDisplayed(),
                "Number of attended vs Unattended chats is not shown after hovering on chart");
    }

    @Then("^Verify admin can see number of attended chats when hover over (.*) channel$")
    public void verifyAdminCanSeeNumberOfAttendedVChatsWhenHoverOverChannel(String channel) {
        Assert.assertTrue(getDashboardPage()
                        .getAttendedVsUnattendedChats()
                        .isNumberOfAttendedChatsDisplayedForChannel(channel),
                String.format("Number of attended chats is not shown after hovering on %s chart", channel));
    }

    @And("^Verify admin can see number of live chats per channel when hover over web chat$")
    public void verifyAdminCanSeeNumberOfLiveChatsPerChannelWhenHoverOverWebChat() {
        Assert.assertTrue(getDashboardPage().getLiveChatsByChannel().isNumberOfLiveChatsShownForWebChatChart(),
                "Number of live chats per channel is not shown after hovering on web chat chart");
    }

    @And("^Verify admin can see number of live chats per channel when hover over abc$")
    public void verifyAdminCanSeeNumberOfLiveChatsPerChannelWhenHoverOverABC() {
        Assert.assertTrue(getDashboardPage().getLiveChatsByChannel().isNumberOfLiveChatsShownForAbcChart(),
                "Number of live chats per channel is not shown after hovering on abc chart");
    }

    @And("^Verify admin can see number of live chats per channel when hover over WhatsApp$")
    public void verifyAdminCanSeeNumberOfLiveChatsPerChannelWhenHoverOverWA() {
        Assert.assertTrue(getDashboardPage().getLiveChatsByChannel().isNumberOfLiveChatsShownForWAChart(),
                "Number of live chats per channel is not shown after hovering on abc chart");
    }

    @Then("^All reports in graphs should be breakdown hourly$")
    public void allReportsInGraphsShouldBeBreakdownHourly() {
        SoftAssert softAssert = new SoftAssert();
        for (List<String> graphTimelines : getCustomersHistory().getGraphsTimelines()) {
            softAssert.assertTrue(isTimelinesShownInHours(graphTimelines),
                    String.format("Timelines %s is not shown hourly", graphTimelines));
        }
        softAssert.assertAll();
    }

    @Then("^Verify vertical (.*) graph scale starts from: (.*) and ends: (.*)$")
    public void verifyVerticalGraphScaleIsBetween(String graphName, String from, String to) {
        List<String> scales = getCustomersHistory().getVerticalLineValuesForGraph(graphName);

        assertThat(scales.get(0)).as("First element should be :" + from).isEqualTo(from);
        assertThat(scales.get(scales.size() - 1)).as("Last element should be :" + to).isEqualTo(to);
    }

    private boolean isTimelinesShownInHours(List<String> timelines) {
        return timelines.stream().allMatch(timeline -> timeline.matches("^([0-1][0-9]|[2][0-3]):([0-5][0-9])$"));
    }

    private static CustomersHistory getCustomersHistory() {
        return getDashboardPage().getCustomersHistory();
    }
}
