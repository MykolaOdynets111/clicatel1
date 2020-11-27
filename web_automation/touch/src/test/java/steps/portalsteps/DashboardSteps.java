package steps.portalsteps;

import agentpages.AgentHomePage;
import agentpages.dashboard.uielements.LiveAgentRowDashboard;
import agentpages.dashboard.uielements.LiveAgentsCustomerRow;
import apihelper.ApiHelper;
import apihelper.ApiHelperTie;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import datamanager.Agents;
import datamanager.Tenants;
import datamanager.jacksonschemas.AvailableAgent;
import driverfactory.DriverFactory;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.util.List;


public class DashboardSteps extends AbstractPortalSteps {

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
    public void adminIsAbleToSeeNPSLiveChatsByChannelPastSentimentGraphs(List<String> graphs) {
        SoftAssert softAssert = new SoftAssert();
        for (String graph : graphs) {
            if (graph.equalsIgnoreCase("Net Promoter Score")) {
                softAssert.assertTrue(getDashboardPage().getNetPromoterScoreSection().isPromoterScoreBarsDisplayed(),
                        String.format("Promoter Score Bars is not displayed for section %s", graph));
                softAssert.assertTrue(getDashboardPage().getNetPromoterScoreSection().isPromoterScorePieDisplayed(),
                        String.format("Promoter Score Pie is not displayed for section %s", graph));
                softAssert.assertTrue(getDashboardPage().getNetPromoterScoreSection().isNoDataAlertRemoved(),
                        String.format("No data alert is displayed for section %s", graph));
            }
            if (graph.equalsIgnoreCase("Customer Satisfaction")) {
                softAssert.assertTrue(getDashboardPage().getCustomerSatisfactionSection()
                                .isCustomerSatisfactionScoreDisplayed(),
                        String.format("Customer Satisfaction Score is not displayed for section %s", graph));
                softAssert.assertTrue(getDashboardPage().getCustomerSatisfactionSection()
                                .isNoDataAlertRemoved(),
                        String.format("No data alert is displayed for section %s", graph));
            }
            softAssert.assertTrue(getDashboardPage().getCustomersHistory().isGraphDisplayed(graph),
                    String.format("%s Graph is not Displayed", graph));
            softAssert.assertTrue(getDashboardPage().getCustomersHistory().isNoDataRemovedForGraph(graph),
                    String.format("No data is displayed for %s Graph", graph));
        }
        softAssert.assertAll();
    }

    @And("^Admin filter Customers History by (.*) channel and (.*) period$")
    public void adminFilterCustomersHistoryByWebchatAndPastDay(String channel, String period) {
        getDashboardPage().getCustomersOverviewTab().selectChannelForReport(channel);
        getDashboardPage().getCustomersOverviewTab().selectPeriodForReport(period);
    }

    @Then("^Admin see all graphs filtered by (.*) channel and (.*) period$")
    public void adminSeeAllGraphsFilteredByWebchatChannelAndPastDayPeriod(String channel, String period) {
        SoftAssert softAssert = new SoftAssert();
        for (String graph : getDashboardPage().getCustomersHistory().getAllGraphs()) {
            //this if's will be removed when https://jira.clickatell.com/browse/TPORT-69991 done
            if (graph.contains("Customer Satisfaction"))
                graph = "Customer Satisfaction";
            if (graph.contains("Net Promoter Score"))
                graph = "Net Promoter Score";
            softAssert.assertTrue(getDashboardPage().getCustomersHistory().isGraphFilteredBy(graph, channel, period),
                    String.format("Graph %s is not filtered by %s channel and %s period", graph, channel, period));
        }
        softAssert.assertAll();
    }

    @Then("^Admin should see no live chats message in Live Chats by Channel$")
    public void adminShouldSeeNoLiveChatsMessageInLiveChatsByChannel() {
        Assert.assertTrue(getDashboardPage().getLiveChatsByChannel().isNoLiveChatsDisplayed(),
                "No live chats message is not displayed in Live Chats By Channel");
    }

    @Then("^Admin should see Web Chat chart in Live Chats by Channel$")
    public void adminShouldSeeWebChatChartInLiveChatsByChannel() {
        Assert.assertTrue(getDashboardPage().getLiveChatsByChannel().isWebChatChartIsDisplayed(),
                "Web Chat chart is not displayed in Live Chats By Channel");
    }

    @Then("^'No Active agents' on Agents Performance tab shown if there is no online agent$")
    public void noActiveAgentsOnAgentsPerformanceTabShownIfThereIsNoOnlineAgent() {
        if (ApiHelper.getNumberOfLoggedInAgents() == 0) {
            //need to be discussed why there's active agents which API doesn't see
            Assert.assertTrue(getDashboardPage().getAgentPerformanceTab().isNoActiveAgentsMessageDisplayed(),
                    "'No active agents' are not shown while there is no logged in agents");
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

        softAssert.assertTrue(sentiment.equalsIgnoreCase(customerRow.getSentiment()),
                String.format("Sentiment is wrong for %s user", userId));
        //need to be investigated
        softAssert.assertEquals(customerRow.getIntent(), intent,
                String.format("Intent is wrong for %s user", userId));

        softAssert.assertAll();

    }
}
