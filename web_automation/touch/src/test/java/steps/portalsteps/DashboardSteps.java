package steps.portalsteps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.util.List;


public class DashboardSteps extends AbstractPortalSteps {


    @And("^Admin click on Customers Overview dashboard tab$")
    public void agentClickOnCustomersOverviewDashboardTab() {
        getDashboardPage().clickOnCustomersOverviewTab();
    }

    @And("^Admin click on Activity Overview dashboard tab$")
    public void adminClickOnActivityOverviewDashboardTab() {
        getDashboardPage().clickOnCustomersOverviewTab();
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
            if(graph.contains("Customer Satisfaction"))
                graph = "Customer Satisfaction";
            if(graph.contains("Net Promoter Score"))
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
}
