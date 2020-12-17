package steps.agentsteps;

import cucumber.api.java.en.And;
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

    @Then("^Icon and (.*) first name of (.*) should be present$")
    public void verifyUserInitials(String agent, String tenantOrgName){
        Response agentInfoResp = Tenants.getPrimaryAgentInfoForTenant(tenantOrgName);
        String agentName = agentInfoResp.getBody().jsonPath().get("firstName").toString();

        SoftAssert soft = new SoftAssert();
        soft.assertEquals(getPageHeader(agent).getAgentFirstNameFromIcon(), agentName, "Agent first name is not as expected");
        soft.assertTrue(getPageHeader(agent).isIconPresent(), "Icon is not displayed");
        soft.assertAll();
    }

    @When("^I click icon with (.*) initials$")
    public void clickIconWithInitials(String agent){ getPageHeader(agent).clickIcon();
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
        soft.assertEquals(getProfileWindow("agent").getFirstName(), agentInfoResp.getBody().jsonPath().get("firstName"),
                "Agent first name is not shown in profile window");
        soft.assertEquals(getProfileWindow("agent").getLastName() ,agentInfoResp.getBody().jsonPath().get("lastName"),
                "Agent last name is not shown in profile window");
        soft.assertEquals(getProfileWindow("agent").getMail(), agentInfoResp.getBody().jsonPath().get("email"),
                "Agent email is not shown in profile window");
        soft.assertEquals(getProfileWindow("agent").getListOfRoles(), expected,
                "Agent roles listed in Profile window are not as expected");
        soft.assertAll();
    }

    @And("^(.*) should see (.*) icon in active chat header$")
    public void agentShouldSeeAppleChatIconInHeader(String agent, String adapter) {
        Assert.assertTrue(getAgentHomePage(agent).getChatHeader().isValidChannelImg(adapter),
                "Image in chat header for " + adapter + " adapter as not expected. \n");
    }
}
