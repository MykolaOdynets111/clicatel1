package steps;

import api_helper.ApiHelper;
import api_helper.ApiHelperPlatform;
import cucumber.api.java.en.Given;
import dbManager.DBConnector;
import driverManager.ConfigManager;

public class BasePortalSteps {

    private static String agentEmail;

    @Given("^New (.*) agent is created$")
    public void createNewAgent(String tenantOrgName){
        agentEmail = "aqa_"+System.currentTimeMillis()+"@aqa.com";
        ApiHelperPlatform.sendNewAgentInvitation(tenantOrgName, agentEmail);
        String invitationID = DBConnector.getInvitationIdForCreatedUserFromMC2DB(ConfigManager.getEnv(), agentEmail);
        ApiHelperPlatform.acceptInvitation(tenantOrgName, invitationID);
//        ApiHelperPlatform.getIdsOfRoles(tenantOrgName, "Touch agent role");
    }
}
