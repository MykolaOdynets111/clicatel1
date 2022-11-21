package steps.agentsteps;


import ch.qos.logback.core.CoreConstants;
import datamanager.Tenants;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AgentInfoSteps extends AbstractAgentSteps{

    @Then("^Icon and (.*) first name of (.*) should be present$")
    public void verifyUserInitials(String agent, String tenantOrgName){
        String agentName = Tenants.getPrimaryAgentInfoForTenant(tenantOrgName).get("fullName");

        SoftAssert soft = new SoftAssert();
        soft.assertEquals(getPageHeader(agent).getAgentFirstNameFromIcon(), agentName, "Agent first name is not as expected");
        soft.assertTrue(getPageHeader(agent).isIconPresent(), "Icon is not displayed");
        soft.assertAll();
    }

    @When("^I click icon with (.*) initials$")
    public void clickIconWithInitials(String agent){
        getAgentHomePage(agent).getPageHeader().clickIcon();
    }



    @Then("^I see (.*) of (.*) info$")
    public void verifyAgentInfoInInfoPopup(String agent, String tenantOrgName){
        SoftAssert soft = new SoftAssert();
        Map <String, String> agentInfoResp = Tenants.getPrimaryAgentInfoForTenant(tenantOrgName);
        String agentName = agentInfoResp.get("fullName");
        soft.assertEquals(getPageHeader(agent).getAgentName(), agentName,
                "Agent name is not as expected");
        soft.assertEquals(getPageHeader(agent).getAgentEmail(), agentInfoResp.get("email"),
                "Agent name is not as expected");
        soft.assertAll();
    }

    @When("^(.*) clicks \"Profile Settings\" button$")
    public void clickProfileSettingsButton(String agent){
        getAgentHomePage(agent).getPageHeader().clickProfileSettingsButton();
    }

    @When("^(.*) clicks \"Reset Password\" button$")
    public void clickResetPasswordButton(String agent){
        getAgentHomePage(agent).getProfileWindow().clickResetPasswordButton();
    }

    @Then("^(.*) views (.*) message in Profile Settings$")
    public void verifyResetPasswordSuccessMessage(String agent, String message){
        Assert.assertEquals(getAgentHomePage(agent).getProfileWindow().getResetPasswordSuccessMessage(),
                message, "Password reset success message does not match");
    }

    @Then("^(.*) of (.*) info details is shown in profile window$")
    public void verifyAgentInfoInProfileWindow(String agent, String tenantOrgName){
        Map<String, String> agentInfoResp = Tenants.getPrimaryAgentInfoForTenant(tenantOrgName);
        SoftAssert soft = new SoftAssert();
        List<String> expected=new ArrayList<>();
//        agentInfoResp.getBody().jsonPath().getList("roles", Map.class).stream().map(e -> e).forEach(e -> {expected.add(
//                e.get("name").toString().toUpperCase() + " ("+
//                        e.get("solution").toString().toUpperCase() +        ")"
//        );});
//        soft.assertEquals(getProfileWindow("agent").getFirstName(), agentInfoResp.getBody().jsonPath().get("firstName"),
//                "Agent first name is not shown in profile window");
//        soft.assertEquals(getProfileWindow("agent").getLastName() ,agentInfoResp.getBody().jsonPath().get("lastName"),
//                "Agent last name is not shown in profile window");
//        soft.assertEquals(getProfileWindow("agent").getMail(), agentInfoResp.getBody().jsonPath().get("email"),
//                "Agent email is not shown in profile window");
        soft.assertEquals(getProfileWindow("agent").getListOfRoles(), expected,
                "Agent roles listed in Profile window are not as expected");
        soft.assertAll();
    }

    @And("^(.*) should see (.*) icon in active chat header$")
    public void agentShouldSeeAppleChatIconInHeader(String agent, String adapter) {
      //  System.out.println("Hello in the code" );
     //   System.out.println(getAgentHomePage(agent).getChatHeader());
      //  System.out.println(adapter);
        Assert.assertTrue(getAgentHomePage(agent).getChatHeader().isValidChannelImg(adapter),
                "Image in chat header for " + adapter + " adapter as not expected. \n");
    }
}
