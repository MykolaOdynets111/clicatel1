package steps.portalsteps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import org.testng.asserts.SoftAssert;

import java.util.List;


public class DashboardSteps extends AbstractPortalSteps {


    @And("^Admin click on Customers Overview dashboard tab$")
    public void agentClickOnCustomersOverviewDashboardTab() {
        getDashboardPage().clickOnCustomersOverviewTab();
    }

    @And("^Admin click on Customers History on dashboard")
    public void agentClickOnCustomersHistory() {
        getDashboardPage().getCustomersOverviewTab().clickOnCustomersHistory();
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
}
