package api_helper;

import com.github.javafaker.Faker;
import dataManager.dot_control.DotControlCreateIntegrationInfo;
import dataManager.jackson_schemas.dot_control.DotControlRequestMessage;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import java_server.Server;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.Assert;

import java.util.Map;

public class APIHelperDotControl {

    static Faker faker = new Faker();

    public static void waitForServerToBeReady(){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for(int i = 0; i<10; i++){
                if (RestAssured.get(Server.getServerURL()).statusCode() == 200) {
                    break;
                } else {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        }
    }

    public static void waitForServerToBeClosed() {
        for(int i = 0; i<10; i++){
            if(RestAssured.get(Server.getServerURL()).statusCode()==502){
                break;
            }else{
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static Response createIntegration(String tenantOrgName, DotControlCreateIntegrationInfo newIntegrationInfo){
        RequestSpec.clearAccessTokenForPortalUser();
        Response resp = RestAssured.given().log().all()
                .header("Content-Type", "application/json")
                .header("Authorization", RequestSpec.getAccessTokenForPortalUser(tenantOrgName))
                .body("{\n" +
                        "  \"channels\": [\n" +
                        "    {\n" +
                        "      \"name\": \"" + newIntegrationInfo.getName() + "\",\n" +
                        "      \"enabled\": " + newIntegrationInfo.getIsEnabled() + ",\n" +
                        "      \"url\": \"" + newIntegrationInfo.getCallBackURL() + "\",\n" +
                        "      \"config\": {}\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}")
                .post(Endpoints.DOT_CONTROL_HTTP_INTEGRATION);
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            //Wait for integration apiKey to be processed
        }

        return resp;
    }

    public static Response sendMessage(DotControlRequestMessage requestMessage){
        ObjectMapper mapper = new ObjectMapper();
        Response resp = null;
        try {
            resp = RestAssured.given().log().all()
                    .header("Content-Type", "application/json")
                    .body(mapper.writeValueAsString(requestMessage))
                    .post(Endpoints.DOT_CONTROL_TO_BOT_MESSAGE);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return resp;
    }

    public static Response deleteHTTPIntegrations(String tenantOrgName){
        return RestAssured.given().log().all()
                .header("Authorization", RequestSpec.getAccessTokenForPortalUser(tenantOrgName))
                .delete(Endpoints.DOT_CONTROL_HTTP_INTEGRATION);
    }

    public static Response sendInitCall(String tenantOrgName, String apiToken, String clientId, String messageId){

        return RestAssured.given().log().all()
                .header("Authorization", RequestSpec.getAccessTokenForPortalUser(tenantOrgName))
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "  \"apiToken\": \""+apiToken+"\",\n" +
                        "  \"clientId\": \""+clientId+"\",\n" +
                        "  \"context\": {},\n" +
                        "  \"conversationId\": \"string\",\n" +
                        "  \"history\": [\n" +
                        "    {\n" +
                        "      \"message\": \"string\",\n" +
                        "      \"messageId\": \""+ messageId + "\",\n" +
                        "      \"messageType\": \"PLAIN\",\n" +
                        "      \"source\": \"AGENT\",\n" +
                        "      \"timestamp\": 0\n" +
                        "    }\n" +
                        "  ],\n" +
                        "  \"referenceId\": \"string\",\n" +
                        "  \"tenantMode\": \"BOT\"\n" +
                        "}")
                .post(Endpoints.DOT_CONTROL_INIT_MESSAGE);
    }

}
