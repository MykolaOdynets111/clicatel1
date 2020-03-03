package steps;

import apihelper.ApiHelper;
import cucumber.api.java.en.Then;
import datamanager.SurveyManagement;
import steps.portalsteps.AbstractPortalSteps;

import java.util.Map;

public class SurveyManagementSteps extends AbstractPortalSteps {

    @Then("^Update survey management chanel (.*) settings by ip for (.*)")
    public void updateSurveyManagementSettings(String chanel, String tenantOrgName, Map<String, String> map){
        String channelID = ApiHelper.getChannelID(tenantOrgName, chanel);
        SurveyManagement configuration = ApiHelper.getSurveyManagementAttributes(channelID);
            for(String key : map.keySet()){
                configuration.updateSomeValueByMethodName(key, map.get(key));
            }
        ApiHelper.updateSurveyManagement(tenantOrgName, configuration, channelID);
    }
}
