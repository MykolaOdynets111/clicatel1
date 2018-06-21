package steps;

import agent_side_pages.AgentHomePage;
import agent_side_pages.AgentLoginPage;
import agent_side_pages.UIElements.ProfileWindow;
import api_helper.ApiHelper;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import dataprovider.Tenants;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DefaultAgentSteps {
    private AgentHomePage agentHomePage;
    private ProfileWindow profileWindow;

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

    @Then("^I see (.*) agent's info$")
    public void verifyAgentInfoInInfoPopup(String tenantOrgName){
        SoftAssert soft = new SoftAssert();
        Response agentInfoResp = Tenants.getPrimaryAgentInfoForTenant(tenantOrgName);
        String agentName = agentInfoResp.getBody().jsonPath().get("firstName") + " "
                + agentInfoResp.getBody().jsonPath().get("lastName");
        soft.assertEquals(agentHomePage.getHeader().getAgentName(), agentName,
                "Agent name is not as expected");
        soft.assertEquals(agentHomePage.getHeader().getAgentEmail(), agentInfoResp.getBody().jsonPath().get("email"),
                "Agent name is not as expected");
        soft.assertAll();
    }

    @When("^I click \"Profile Settings\" button$")
    public void clickProfileSettingsButton(){
        agentHomePage.getHeader().clickProfileSettingsButton();
    }


    @Then("^(.*) Agent's info derails is shown in profile window$")
    public void verifyAgentInfoInProfileWindow(String tenantOrgName){
        Response agentInfoResp = Tenants.getPrimaryAgentInfoForTenant(tenantOrgName);
        SoftAssert soft = new SoftAssert();
        List<String> expected=new ArrayList<>();
        agentInfoResp.getBody().jsonPath().getList("roles", Map.class).stream().map(e -> ((Map) e)).forEach(e -> {expected.add(
                e.get("name").toString().toUpperCase() + " ("+
                        e.get("solution").toString().toUpperCase() +        ")"
        );});
        soft.assertTrue(getProfileWindow().isAgentInfoShown(agentInfoResp.getBody().jsonPath().get("firstName")),
                "Agent first name is not shown in profile window");
        soft.assertTrue(getProfileWindow().isAgentInfoShown(agentInfoResp.getBody().jsonPath().get("lastName")),
                "Agent last name is not shown in profile window");
        soft.assertTrue(getProfileWindow().isAgentInfoShown(agentInfoResp.getBody().jsonPath().get("email")),
                "Agent email is not shown in profile window");
        soft.assertEquals(getProfileWindow().getListOfRoles(), expected,
                "Agent roles listed in Profile window are not as expected");
        soft.assertAll();
    }


    private ProfileWindow getProfileWindow(){
        if (profileWindow==null) {
            profileWindow = agentHomePage.getProfileWindow();
            return profileWindow;
        } else{
            return profileWindow;
        }
    }
}
