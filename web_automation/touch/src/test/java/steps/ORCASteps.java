package steps;

import apihelper.ApiORCA;
import cucumber.api.java.en.Given;
import io.restassured.response.Response;
import datamanager.jacksonschemas.orca.OrcaMessage;
import javaserver.Server;

import java.util.List;

public class ORCASteps {

    private static ThreadLocal<OrcaMessage> orcaMessageCallBody = new ThreadLocal<>();
    private static ThreadLocal<String> apiToken = new ThreadLocal<>();
    private static ThreadLocal<String> clientId = new ThreadLocal<>();

    @Given("^Send (.*) message by ORCA$")
    public void sendOrcaMessage(String message){
        if (orcaMessageCallBody.get()==null) {
            createRequestMessage(apiToken.get(), message);
            clientId.set(orcaMessageCallBody.get().getSourceId());
        } else{
            orcaMessageCallBody.get().getContent().getEvent().setText(message);
        }
        ApiORCA.sendMessageToAgent(orcaMessageCallBody.get());

    }

    @Given("^Setup ORCA integration for (.*) tenant$")
    public void createOrUpdateOrcaIntegration(String tenantName){
        String action = getIntegrationCreationMethod(tenantName);
        if (action.equalsIgnoreCase("create")){
            apiToken.set(ApiORCA.createIntegration(tenantName, Server.getServerURL()));
        }else if(action.equalsIgnoreCase("update")){
            apiToken.set(ApiORCA.updateIntegration(tenantName, Server.getServerURL()));
        }
    }

    public String getIntegrationCreationMethod(String tenantName){
        Response response = ApiORCA.getORCAIntegrationsList(tenantName);
        if(!(response.getBody().jsonPath().getList("").size() == 0)) {
            List<String> types = response.getBody().jsonPath().getList("transport.type");
            for (String integrationType : types) {
                if (integrationType.equalsIgnoreCase("orca")){
                    return "update";
                }
            }
        }
        return"create";
    }

    private ThreadLocal<OrcaMessage> createRequestMessage(String apiKey, String message){
        orcaMessageCallBody.set(new OrcaMessage(apiKey, message));
        return orcaMessageCallBody;
    }

    public static String getClient(){
        return clientId.get();
    }
    public static void cleanUPORCAData(){
        orcaMessageCallBody.remove();
        apiToken.remove();
        clientId.remove();
    }

}
