package steps;

import agent_side_pages.AgentHomePage;
import agent_side_pages.AgentLoginPage;
import api_helper.ApiHelper;
import cucumber.api.java.en.Given;
import dataprovider.Tenants;
import org.testng.Assert;

public class DefaultAgentSteps {
    private AgentHomePage agentHomePage;


    @Given("^I login as agent of (.*)")
    public void loginAsAgentForTenant(String tenantOrhName){
        agentHomePage = AgentLoginPage.openAgentLoginPage(tenantOrhName).loginAsAgentOf(tenantOrhName);
        Assert.assertTrue(agentHomePage.isAgentSuccessfullyLoggedIn(), "Agent is not logged in.");
    }

    @Given("^(.*) tenant feature is set to (.*) for (.*)$")
    public void setFeatureStatus(String feature, String status, String tenantOrgName){
        ApiHelper.updateFeatureStatus(tenantOrgName, feature, status);
    }

}
