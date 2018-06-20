package steps;

import agent_side_pages.AgentHomePage;
import agent_side_pages.AgentLoginPage;
import api_helper.ApiHelper;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import dataprovider.Tenants;
import io.restassured.response.Response;
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

    @Then("^Icon should contain (.*) agent's initials$")
    public void verifyUserInitials(String tenantOrgName){
        Response agentInfoResp = Tenants.getPrimaryAgentInfoForTenant(tenantOrgName);
        String expectedInitials = Character.toString(agentInfoResp.getBody().jsonPath().get("firstName").toString().charAt(0)) +
                Character.toString(agentInfoResp.getBody().jsonPath().get("lastName").toString().charAt(0));

        Assert.assertEquals(agentHomePage.getHeader().getTextFromIcon(), expectedInitials, "Agent initials is not as expected");
    }

    @When("^I click icon with initials$")
    public void clickIconWithInitials(){
        agentHomePage.getHeader().clickIconWithInitials();
    }
}
