package steps.portalsteps;

import apihelper.ApiHelper;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import datamanager.Tenants;
import io.restassured.response.Response;
import org.testng.Assert;
import steps.dotcontrol.DotControlSteps;

public class PortalInboxSteps extends AbstractPortalSteps {


    @Given("^autoSchedulingEnabled is set to false$")
    public void turnOffAutoScheduling(){
        ApiHelper.updateTenantConfig(Tenants.getTenantUnderTestOrgName(), "autoSchedulingEnabled", "false");
    }

    @Then("^Filter \"(.*)\" is selected by default$")
    public void filterIsSelectedByDefault(String filterName) {
        Assert.assertEquals(getChatConsoleInboxPage().getFilterByDefault(),filterName,
                "Filter name by default does not match expected");
    }

    @Then("^Click three dots for dot control ticket$")
    public void clickThreeDotsButton(){
        getChatConsoleInboxPage().clickThreeDotsButton(DotControlSteps.getClient());

    }

    @When("Click 'Route to scheduler' button")
    public void clickRouteToScheduler(){
        getChatConsoleInboxPage().clickRouteToSchedulerButton();
    }

    @Then("^(.*) is set as 'current agent' for dot control ticket$")
    public void verifyCurrentAgent(String agent){
        Response rest = ApiHelper.getAgentInfo(Tenants.getTenantUnderTestOrgName(), agent);
        String agentName = rest.jsonPath().get("firstName") + " " + rest.jsonPath().get("lastName");
        boolean result = false;
        for (int i = 0; i < 8; i++){
            if(agentName.equals(getChatConsoleInboxPage().getCurrentAgentOfTheChat(DotControlSteps.getClient()))){
                result = true;
                break;
            }
            else waitFor(1000);
        }
        Assert.assertTrue(result, "Agent " +agentName+ " is not set up as 'Current agent'");
    }

}
