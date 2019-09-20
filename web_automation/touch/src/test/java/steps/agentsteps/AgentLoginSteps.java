package steps.agentsteps;

import agentpages.AgentLoginPage;
import apihelper.ApiHelper;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import datamanager.Tenants;
import drivermanager.ConfigManager;
import org.testng.Assert;

public class AgentLoginSteps extends AbstractAgentSteps {

    @Given("^I login as (.*) of (.*)")
    public void loginAsAgentForTenant(String ordinalAgentNumber, String tenantOrgName){
        Tenants.setTenantUnderTestNames(tenantOrgName);
        ApiHelper.closeAllOvernightTickets(Tenants.getTenantUnderTestOrgName(), ordinalAgentNumber);
//        if(!ordinalAgentNumber.contains("second")) ApiHelper.logoutTheAgent(Tenants.getTenantUnderTestOrgName());
        AgentLoginPage loginPage = AgentLoginPage.openAgentLoginPage(ordinalAgentNumber, tenantOrgName)
                .loginAsAgentOf(tenantOrgName, ordinalAgentNumber);
        setAgentLoginPage(ordinalAgentNumber, loginPage);
        Assert.assertTrue(getAgentHomePage(ordinalAgentNumber).isAgentSuccessfullyLoggedIn(ordinalAgentNumber),
                "Agent is not logged in.");
    }

    @Given("^Try to login as (.*) of (.*)")
    public void tryToLoginAsAgentForTenant(String ordinalAgentNumber, String tenantOrgName){
        Tenants.setTenantUnderTestNames(tenantOrgName);
        AgentLoginPage loginPage = AgentLoginPage.openAgentLoginPage(ordinalAgentNumber, tenantOrgName)
                .loginAsAgentOf(tenantOrgName, ordinalAgentNumber);
        setAgentLoginPage(ordinalAgentNumber, loginPage);
    }


    @Then("^(.*) of (.*) is logged in")
    public void verifyAgentLoggedIn(String ordinalAgentNumber, String tenantOrgName){

        Assert.assertTrue(getAgentHomePage(ordinalAgentNumber).isAgentSuccessfullyLoggedIn(ordinalAgentNumber),
                "Agent is not logged in.");
    }


    @When("I login with the same credentials in another browser as an agent of (.*)")
    public void loginWithTheSameCreds(String tenantOrgName){
        AgentLoginPage loginPage = AgentLoginPage.openAgentLoginPage("second agent", tenantOrgName)
                .loginAsAgentOf(tenantOrgName, "main agent");
        setAgentLoginPage("second agent", loginPage);

        Assert.assertTrue(getAgentHomePage("second agent").isAgentSuccessfullyLoggedIn("second agent"),
                "Agent is not logged in.");
    }

    @When("I open browser to log in in chat desk as an agent of (.*)")
    public void openBrowserToLogin(String tenantOrgName){
        setCurrentLoginPage(AgentLoginPage.openAgentLoginPage("second agent", tenantOrgName));
    }

    @When("I login in another browser as an agent of (.*)")
    public void loginWithTheSameAnotherBrowser(String tenantOrgName){
        getCurrentLoginPage().loginAsAgentOf(tenantOrgName, "main agent");
        Assert.assertTrue(getAgentHomePage("second agent").isAgentSuccessfullyLoggedIn("second agent"),
                "Agent is not logged in.");
    }

    @Then("^In the first browser Connection Error should be shown$")
    public void verifyAgentIsDisconnected(){
        Assert.assertTrue(getAgentHomePage("first agent").isConnectionErrorShown("first agent"),
                "Agent in the first browser is not disconnected");
    }


    @Then("^(.*) is logged in chat desk$")
    public void verifyAgentLoggedIn(String ordinalAgentNumber){
        boolean result = getAgentHomePage(ordinalAgentNumber).isAgentSuccessfullyLoggedIn(ordinalAgentNumber);
        if(result) ConfigManager.setIsSecondCreated("true");
        Assert.assertTrue(result, "Agent is not logged in chat desk.");
    }

    @Then("^(.*) is not redirected to chatdesk$")
    public void verifyAgentNotLoggedIn(String ordinalAgentNumber){
        Assert.assertFalse(getAgentHomePage(ordinalAgentNumber).isAgentSuccessfullyLoggedIn(ordinalAgentNumber),
                "Agent is redirected to chat desk.");
    }
}
