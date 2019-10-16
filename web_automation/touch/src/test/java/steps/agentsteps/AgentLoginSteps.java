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
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import portalpages.PortalMainPage;

import java.util.ArrayList;
import java.util.List;

public class AgentLoginSteps extends AbstractAgentSteps {


    private static ThreadLocal<org.slf4j.Logger> log = new ThreadLocal<>();


    @Given("^I login as (.*) of (.*)")
    public void loginAsAgentForTenant(String ordinalAgentNumber, String tenantOrgName){
        log.set(LoggerFactory.getLogger(AgentLoginSteps.class));
        log.get().info("{} tenant login  started. Time: ", tenantOrgName);
        Tenants.setTenantUnderTestNames(tenantOrgName);
        ApiHelper.closeAllOvernightTickets(Tenants.getTenantUnderTestOrgName(), ordinalAgentNumber);
        log.get().info(" {} tenant finished closing overnight ticket.\n" +
                "Before login to portal. Time: ", tenantOrgName);

        loginToPortalAndOpenChatdesk(ordinalAgentNumber, tenantOrgName);
        Assert.assertTrue(getAgentHomePage(ordinalAgentNumber).isAgentSuccessfullyLoggedIn(ordinalAgentNumber),
                "Agent is not logged in.");
    }

    private void loginToPortalAndOpenChatdesk(String ordinalAgentNumber, String tenantOrgName){
        Agents agent = Agents.getAgentFromCurrentEnvByTenantOrgName(tenantOrgName, ordinalAgentNumber);
        List<Permission> permissions = new ArrayList<>();
        log.get().info(tenantOrgName + " before getting agent roles. Time: " + System.currentTimeMillis());
        ApiHelper.getAgentInfo(tenantOrgName, ordinalAgentNumber)
                .getBody().jsonPath().getList("roles", UserRole.class).stream().forEach(e -> permissions.addAll(e.getPermissions()));
        log.get().info(tenantOrgName + " before opening login page. Time: " + System.currentTimeMillis());
        getPortalLoginPage(ordinalAgentNumber).openLoginPage(DriverFactory.getDriverForAgent(ordinalAgentNumber));
        log.get().info(tenantOrgName + " before login agent. Time: " + System.currentTimeMillis());
        getPortalLoginPage(ordinalAgentNumber).login(agent.getAgentEmail(), agent.getAgentPass());
        log.get().info(tenantOrgName + " after login agent\n" +
                "Before permission check. Time: " + System.currentTimeMillis());

        if(permissions.stream().anyMatch(e -> e.getSolution().equalsIgnoreCase("PLATFORM"))){
            log.get().info(tenantOrgName + " Before check that isPortalPageOpened. Time: " + System.currentTimeMillis());
            Assert.assertTrue(getPortalMainPage(ordinalAgentNumber).isPortalPageOpened(),
                    "User is not logged in to portal");
            log.get().info(tenantOrgName + " After check that isPortalPageOpened\n" +
                    "Before opening chatdesk . Time: " + System.currentTimeMillis());
            getPortalMainPage(ordinalAgentNumber).launchChatDesk();
            log.get().info(tenantOrgName + " After cchatdesk");
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
