package apihelper;

import com.github.javafaker.Faker;
import datamanager.dotcontrol.DotControlCreateIntegrationInfo;
import datamanager.jacksonschemas.dotcontrol.DotControlRequestIntegrationChanel;
import datamanager.jacksonschemas.dotcontrol.DotControlRequestMessage;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import javaserver.Server;

import java.lang.reflect.Array;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

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
                        "      \"adapter\": \"fbmsg\",\n" +
                        "      \"config\": {}\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}")
                .post(Endpoints.DOT_CONTROL_HTTP_INTEGRATION);

        return resp;
    }

    public static Response createIntegrationForAdapters(String adapters, String tenantOrgName, DotControlCreateIntegrationInfo newIntegrationInfo){
        RequestSpec.clearAccessTokenForPortalUser();
        String url =  newIntegrationInfo.getCallBackURL();
        String bodyAdapters = getBodyAdaptersCanels(adapters,url);
        Response resp = RestAssured.given().log().all()
                .header("Content-Type", "application/json")
                .header("Authorization", RequestSpec.getAccessTokenForPortalUser(tenantOrgName))
                .body("{\n" +
                        "  \"channels\":" + bodyAdapters +
                        "}")
                .post(Endpoints.DOT_CONTROL_HTTP_INTEGRATION);

        return resp;
    }

    public static String getBodyAdaptersCanels(String adapters, String url){
        String[] arrayAdapters = adapters.split(",");
        String result = "[";
        for (int i=0; i<arrayAdapters.length; i++){
            result = result + (new DotControlRequestIntegrationChanel(url,arrayAdapters[i])).toString() +",";
        }
        return result.substring(0 ,result.length()-1) + "]";
    }



    public static Response sendMessage(DotControlRequestMessage requestMessage){
        ZoneId zoneId = TimeZone.getDefault().toZoneId();
        LocalDateTime ldt = LocalDateTime.now(zoneId);
        ZonedDateTime zdt = ldt.atZone(zoneId);
        long timestamp = zdt.toInstant().toEpochMilli();
        return RestAssured.given().log().all()
                    .header("Content-Type", "application/json")
                    .body("{\n" +
                            "  \"apiToken\": \""+ requestMessage.getApiToken() +"\",\n" +
                            "  \"clientId\": \"" +requestMessage.getClientId()+ "\",\n" +
                            "  \"context\": {},\n" +
                            "  \"message\": \""+ requestMessage.getMessage() +"\",\n" +
                            "  \"messageId\": null,\n" +
                            "  \"messageType\": \"PLAIN\",\n" +
                            "  \"referenceId\": \"string\",\n" +
                            "  \"subscriptionSpecificId\": \"string\",\n" +
                            "  \"timestamp\": " + timestamp +  "\n" +
                            "}")
                    .post(Endpoints.DOT_CONTROL_TO_BOT_MESSAGE);
    }

    public static Response sendMessageWithWait(DotControlRequestMessage requestMessage){
        Response resp = sendMessage(requestMessage);
        for (int i =0; i<13; i++){
            if (resp.statusCode() != 401) break;
            else{
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                resp = sendMessage(requestMessage);
            }

        }
        return resp;
    }

    public static Response deleteHTTPIntegrations(String tenantOrgName){
        return RestAssured.given().log().all()
                .header("Authorization", RequestSpec.getAccessTokenForPortalUser(tenantOrgName))
                .delete(Endpoints.DOT_CONTROL_HTTP_INTEGRATION);
    }

    public static Response sendInitCallWithWait(String tenantOrgName, String apiToken, String clientId, String messageId){
        Response resp = sendInitCall(tenantOrgName, apiToken, clientId, messageId);
        for (int i =0; i<15; i++){
            if (resp.statusCode() != 401) break;
            else{
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                resp = sendInitCall(tenantOrgName, apiToken, clientId, messageId);
            }

        }
        return resp;
    }

    private static Response sendInitCall(String tenantOrgName, String apiToken, String clientId, String messageId){

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
