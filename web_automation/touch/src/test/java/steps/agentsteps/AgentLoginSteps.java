package steps.agentsteps;

import agentpages.AgentLoginPage;
import apihelper.ApiHelper;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import datamanager.Agents;
import datamanager.Tenants;
import datamanager.model.user.Permission;
import datamanager.model.user.UserRole;
import driverfactory.DriverFactory;
import drivermanager.ConfigManager;
import org.testng.Assert;
import portalpages.PortalMainPage;

import java.util.ArrayList;
import java.util.List;

public class AgentLoginSteps extends AbstractAgentSteps {


    @Given("^I login as (.*) of (.*)")
    public void loginAsAgentForTenant(String ordinalAgentNumber, String tenantOrgName){
        Tenants.setTenantUnderTestNames(tenantOrgName);
        ApiHelper.closeAllOvernightTickets(Tenants.getTenantUnderTestOrgName(), ordinalAgentNumber);
        loginToPortalAndOpenChatdesk(ordinalAgentNumber, tenantOrgName);

        Assert.assertTrue(getAgentHomePage(ordinalAgentNumber).isAgentSuccessfullyLoggedIn(ordinalAgentNumber),
                "Agent is not logged in.");

    }

    private void loginToPortalAndOpenChatdesk(String ordinalAgentNumber, String tenantOrgName){
        Agents agent = Agents.getAgentFromCurrentEnvByTenantOrgName(tenantOrgName, ordinalAgentNumber);
        List<Permission> permissions = new ArrayList<>();
        ApiHelper.getAgentInfo(tenantOrgName, ordinalAgentNumber)
                .getBody().jsonPath().getList("roles", UserRole.class).stream().forEach(e -> permissions.addAll(e.getPermissions()));
        getPortalLoginPage(ordinalAgentNumber).openLoginPage(DriverFactory.getDriverForAgent(ordinalAgentNumber));
        getPortalLoginPage(ordinalAgentNumber).login(agent.getAgentEmail(), agent.getAgentPass());

        if(permissions.stream().anyMatch(e -> e.getSolution().equalsIgnoreCase("PLATFORM"))){
            Assert.assertTrue(getPortalMainPage(ordinalAgentNumber).isPortalPageOpened(),
                    "User is not logged in to portal");
            getPortalMainPage(ordinalAgentNumber).launchChatDesk();
        }
    }

    @Given("^Try to login as (.*) of (.*)")
    public void tryToLoginAsAgentForTenant(String ordinalAgentNumber, String tenantOrgName){
        Tenants.setTenantUnderTestNames(tenantOrgName);
        loginToPortalAndOpenChatdesk(ordinalAgentNumber, tenantOrgName);
    }


    @Then("^(.*) of (.*) is logged in")
    public void verifyAgentLoggedIn(String ordinalAgentNumber, String tenantOrgName){

        Assert.assertTrue(getAgentHomePage(ordinalAgentNumber).isAgentSuccessfullyLoggedIn(ordinalAgentNumber),
                "Agent is not logged in.");
    }


    @When("I login with the same credentials in another browser as an agent of (.*)")
    public void loginWithTheSameCreds(String tenantOrgName){
        Agents agent = Agents.getAgentFromCurrentEnvByTenantOrgName(tenantOrgName, "main");

        getPortalLoginPage("second agent").openLoginPage(DriverFactory.getDriverForAgent("second agent"));
        PortalMainPage mainPage = getPortalLoginPage("second agent").login(agent.getAgentEmail(), agent.getAgentPass());
        Assert.assertTrue(mainPage.isPortalPageOpened(),
                "User is not logged in to portal");
        mainPage.launchChatDesk();

    }

    @When("I open browser to log in in chat desk as an agent of (.*)")
    public void openBrowserToLogin(String tenantOrgName){
        setCurrentLoginPage(AgentLoginPage.openAgentLoginPage("second agent", tenantOrgName));
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
