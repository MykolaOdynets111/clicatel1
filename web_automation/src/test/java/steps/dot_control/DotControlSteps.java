package steps.dot_control;

import api_helper.DotControllAPI;
import cucumber.api.java.en.Given;
import dataManager.Tenants;
import dataManager.dot_control.DotControlCreateIntegrationInfo;
import dataManager.jackson_schemas.dot_control.DotControlRequestMessage;
import driverManager.ConfigManager;
import java_server.Server;

public class DotControlSteps {

    private ThreadLocal<DotControlCreateIntegrationInfo> infoForCreatingIntegration = new ThreadLocal<>();


    @Given("Create . Control integration for (.*) tenant")
    public void createIntegration(String tenantOrgName){
        Tenants.setTenantUnderTestNames(tenantOrgName);
        String url = null;
        if(ConfigManager.isRemote()){
            url = Server.INTERNAL_CI_IP + ":" + Server.SERVER_PORT;
        }else{
            // to provide local ngrok url
            url = "http://89e3ef8d.ngrok.io";
        }

        DotControllAPI.createIntegration(tenantOrgName, generateInfoForCreatingIntegration(url).get());
    }

    private ThreadLocal<DotControlCreateIntegrationInfo> generateInfoForCreatingIntegration(String callBackURL){
        infoForCreatingIntegration.set(new DotControlCreateIntegrationInfo(true, callBackURL));
        return infoForCreatingIntegration;
    }

    private void createRequestMessage(String apiKey, String message){
        new DotControlRequestMessage(apiKey, message);
    }
}
