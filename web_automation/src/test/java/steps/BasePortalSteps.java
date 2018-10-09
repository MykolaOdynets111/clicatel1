package steps;

import api_helper.ApiHelperPlatform;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import dataManager.Agents;
import dataManager.Tenants;
import dbManager.DBConnector;
import driverManager.ConfigManager;
import portal_pages.PortalLoginPage;

public class BasePortalSteps {

    private static String agentEmail;
    private String agentPass = "p@$$w0rd4te$t";
    private PortalLoginPage portalLoginPage;

    @Given("^New (.*) agent is created$")
    public void createNewAgent(String tenantOrgName){
        Tenants.setTenantUnderTestNames(tenantOrgName);
        agentEmail = "aqa_"+System.currentTimeMillis()+"@aqa.com";
        ApiHelperPlatform.sendNewAgentInvitation(tenantOrgName, agentEmail);
        String invitationID = DBConnector.getInvitationIdForCreatedUserFromMC2DB(ConfigManager.getEnv(), agentEmail);
        ApiHelperPlatform.acceptInvitation(tenantOrgName, invitationID, agentPass);
    }

    @Given("^Delete user$")
    public static void deleteAgent(){
        String userID = ApiHelperPlatform.getUserID(Tenants.getTenantUnderTestOrgName(), agentEmail);
        ApiHelperPlatform.deleteUser(Tenants.getTenantUnderTestOrgName(), userID);
//        ApiHelperPlatform.deleteUser("General Bank Demo", "ff808081619df1000161bd8981060003");
    }

    @When("^I open portal$")
    public void openPortal(){
        portalLoginPage = PortalLoginPage.openPortalLoginPage();
    }

    @When("^Login as newly created agent$")
    public void loginAsCreatedAgent(){
        portalLoginPage.login(agentEmail, agentPass);
    }

    @When("^Login into portal as an (.*) of (.*) account$")
    public void loginToPortal(String ordinalAgentNumber, String account){
        Agents portalAdmin = Agents.getAgentFromCurrentEnvByTenantOrgName(account, ordinalAgentNumber);
        portalLoginPage.login(portalAdmin.getAgentName(), portalAdmin.getAgentPass());
    }

    public static boolean isNewUserWasCreated(){
        return agentEmail != null;
    }
}
