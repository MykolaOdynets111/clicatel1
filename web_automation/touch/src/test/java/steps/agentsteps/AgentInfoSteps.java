package steps.agentsteps;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import datamanager.Tenants;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AgentInfoSteps extends AbstractAgentSteps{

    @Then("^Icon should contain (.*) initials of (.*)$")
    public void verifyUserInitials(String agent, String tenantOrgName){
        Response agentInfoResp = Tenants.getPrimaryAgentInfoForTenant(tenantOrgName);
        String expectedInitials = Character.toString(agentInfoResp.getBody().jsonPath().get("firstName").toString().charAt(0)) +
                agentInfoResp.getBody().jsonPath().get("lastName").toString().charAt(0);

        Assert.assertEquals(getPageHeader(agent).getTextFromIcon(), expectedInitials, "Agent initials is not as expected");
    }

    @When("^I click icon with (.*) initials$")
    public void clickIconWithInitials(String agent){ getPageHeader(agent).clickIconWithInitials();
    }

    @Then("^I see (.*) of (.*) info$")
    public void verifyAgentInfoInInfoPopup(String agent, String tenantOrgName){
        SoftAssert soft = new SoftAssert();
        Response agentInfoResp = Tenants.getPrimaryAgentInfoForTenant(tenantOrgName);
        String agentName = agentInfoResp.getBody().jsonPath().get("firstName") + " "
                + agentInfoResp.getBody().jsonPath().get("lastName");
        soft.assertEquals(getPageHeader(agent).getAgentName(), agentName,
                "Agent name is not as expected");
        soft.assertEquals(getPageHeader(agent).getAgentEmail(), agentInfoResp.getBody().jsonPath().get("email"),
                "Agent name is not as expected");
        soft.assertAll();
    }

    @When("^(.*) clicks \"Profile Settings\" button$")
    public void clickProfileSettingsButton(String agent){
        getPageHeader(agent).clickProfileSettingsButton();
    }

    @Then("^(.*) of (.*) info details is shown in profile window$")
    public void verifyAgentInfoInProfileWindow(String agent, String tenantOrgName){
        Response agentInfoResp = Tenants.getPrimaryAgentInfoForTenant(tenantOrgName);
        SoftAssert soft = new SoftAssert();
        List<String> expected=new ArrayList<>();
        agentInfoResp.getBody().jsonPath().getList("roles", Map.class).stream().map(e -> e).forEach(e -> {expected.add(
                e.get("name").toString().toUpperCase() + " ("+
                        e.get("solution").toString().toUpperCase() +        ")"
        );});
        soft.assertTrue(getProfileWindow("agent").isAgentInfoShown(agentInfoResp.getBody().jsonPath().get("firstName")),
                "Agent first name is not shown in profile window");
        soft.assertTrue(getProfileWindow("agent").isAgentInfoShown(agentInfoResp.getBody().jsonPath().get("lastName")),
                "Agent last name is not shown in profile window");
        soft.assertTrue(getProfileWindow("agent").isAgentInfoShown(agentInfoResp.getBody().jsonPath().get("email")),
                "Agent email is not shown in profile window");
        soft.assertEquals(getProfileWindow("agent").getListOfRoles(), expected,
                "Agent roles listed in Profile window are not as expected");
        soft.assertAll();
    }
}
