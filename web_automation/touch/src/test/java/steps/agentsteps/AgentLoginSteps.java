package steps.agentsteps;


import datamanager.Agents;
import datamanager.Tenants;
import driverfactory.DriverFactory;
import driverfactory.URLs;
import drivermanager.ConfigManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import portalpages.PortalMainPage;


public class AgentLoginSteps extends AbstractAgentSteps {

    @Given("^I login as (.*) of (.*)")
    public void loginAsAgentForTenant(String ordinalAgentNumber, String tenantOrgName){
        Tenants.setTenantUnderTestNames(tenantOrgName);
        if (ConfigManager.isMc2()) {
            //ToDo Unity login should be added
        }else {
            AbstractAgentSteps.getAgentLoginPage(ordinalAgentNumber).openPortalLoginPage();
            AbstractAgentSteps.getAgentLoginPage(ordinalAgentNumber).selectTenant(Tenants.getTenantUnderTestName())
                    .selectAgent(ordinalAgentNumber).clickAuthenticateButton();
            AbstractAgentSteps.getAgentLoginPage(ordinalAgentNumber).getCurrentDriver().get(URLs.getUrlByNameOfPage("Agent Desk"));
        }

        Assert.assertTrue(getAgentHomePage(ordinalAgentNumber).isAgentSuccessfullyLoggedIn(),
                "Agent is not logged in.");
    }



    @Then("^(.*) of (.*) is logged in")
    public void verifyAgentLoggedIn(String ordinalAgentNumber, String tenantOrgName){

        Assert.assertTrue(getAgentHomePage(ordinalAgentNumber).isAgentSuccessfullyLoggedIn(),
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
}
