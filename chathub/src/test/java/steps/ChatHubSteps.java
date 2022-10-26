package steps;

import datamanager.Agents;
import datamanager.Tenants;
import datamanager.model.user.Permission;
import driverfactory.DriverFactory;
import driverfactory.URLs;
import drivermanager.ConfigManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import portalpages.PortalMainPage;
import java.util.ArrayList;
import java.util.List;

public class ChatHubSteps {

/*
    @Given("^I login as (.*) of (.*)")
    public void loginAsAgentForTenant(String ordinalAgentNumber, String tenantOrgName){
        Tenants.setTenantUnderTestNames(tenantOrgName);
        if (ConfigManager.isMc2())
        {
            loginToPortalAndOpenChatdesk(ordinalAgentNumber, tenantOrgName);
        } else
        {
            AbstractAgentSteps.getAgentLoginPage(ordinalAgentNumber).openPortalLoginPage();
            AbstractAgentSteps.getAgentLoginPage(ordinalAgentNumber).selectTenant(Tenants.getTenantUnderTestName())
                    .selectAgent(ordinalAgentNumber).clickAuthenticateButton();
            AbstractAgentSteps.getAgentLoginPage(ordinalAgentNumber).getCurrentDriver().get(URLs.getUrlByNameOfPage("Agent Desk"));
        }

        Assert.assertTrue(getAgentHomePage(ordinalAgentNumber).isAgentSuccessfullyLoggedIn(),
                "Agent is not logged in.");
    }

    private void loginToPortalAndOpenChatdesk(String ordinalAgentNumber, String tenantOrgName){
        Agents agent = Agents.getAgentFromCurrentEnvByTenantOrgName(tenantOrgName, ordinalAgentNumber);
        List<Permission> permissions = new ArrayList<>();
//        ApiHelper.getAgentInfo(tenantOrgName, ordinalAgentNumber)
//                .getBody().jsonPath().getList("roles", UserRole.class).stream().forEach(e -> permissions.addAll(e.getPermissions()));
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

        Assert.assertTrue(getAgentHomePage(ordinalAgentNumber).isAgentSuccessfullyLoggedIn(),
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
        Assert.assertTrue(getAgentHomePage("second agent").isAgentSuccessfullyLoggedIn(),
                "Agent is not logged in.");
    }

    @Then("^In the first browser Connection Error should be shown$")
    public void verifyAgentIsDisconnected(){
        Assert.assertEquals("Chat Desk supports only one " +
                        "active tab with the app. The data or changes might be lost. Please reload this page to continue using " +
                        "this tab or close it and switch to the active tab.", getAgentHomePage("first agent").isConnectionErrorShown(),
                "Error message text is not the same");
    }


    @Then("^(.*) is logged in chat desk$")
    public void verifyAgentLoggedIn(String ordinalAgentNumber){
        boolean result = getAgentHomePage(ordinalAgentNumber).isAgentSuccessfullyLoggedIn();
        if(result) ConfigManager.setIsSecondCreated("true");
        Assert.assertTrue(result, "Agent is not logged in chat desk.");
    }

    @Then("^(.*) is not redirected to chatdesk$")
    public void verifyAgentNotLoggedIn(String ordinalAgentNumber){
        Assert.assertFalse(getAgentHomePage(ordinalAgentNumber).isAgentSuccessfullyLoggedIn(),
                "Agent is redirected to chat desk.");
    }
    */

}
