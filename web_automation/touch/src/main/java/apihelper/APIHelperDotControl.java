package apihelper;

import datamanager.dotcontrol.DotControlCreateIntegrationInfo;
import datamanager.jacksonschemas.dotcontrol.DotControlInitRequest;
import datamanager.jacksonschemas.dotcontrol.DotControlRequestIntegrationChanel;
import datamanager.jacksonschemas.dotcontrol.DotControlRequestMessage;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import javaserver.Server;
import mc2api.PortalAuthToken;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.TimeZone;

public class APIHelperDotControl {

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
        PortalAuthToken.clearAccessTokenForPortalUser();
        return RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", PortalAuthToken.getAccessTokenForPortalUser(tenantOrgName, "main"))
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
    }

    public static Response createIntegrationForAdapters(String adapters, String tenantOrgName, DotControlCreateIntegrationInfo newIntegrationInfo){
        PortalAuthToken.clearAccessTokenForPortalUser();
        String url =  newIntegrationInfo.getCallBackURL();
        String bodyAdapters = getBodyAdaptersChannels(adapters,url);
        return RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", PortalAuthToken.getAccessTokenForPortalUser(tenantOrgName, "main"))
                .body("{\n" +
                        "  \"channels\":" + bodyAdapters +
                        "}")
                .post(Endpoints.DOT_CONTROL_HTTP_INTEGRATION);
    }

    public static String getBodyAdaptersChannels(String adapters, String url){
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
                    .contentType(ContentType.JSON)
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
                .header("Authorization", PortalAuthToken.getAccessTokenForPortalUser(tenantOrgName, "main"))
                .delete(Endpoints.DOT_CONTROL_HTTP_INTEGRATION);
    }

    public static Response sendInitCallWithWait(String tenantOrgName, DotControlInitRequest initRequest){
//        Response resp = sendInitCall(tenantOrgName, apiToken, clientId, messageId);
        Response resp = sendInitCall(tenantOrgName, initRequest);

        for (int i =0; i<15; i++){
            if (resp.statusCode() != 401) break;
            else{
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                resp = sendInitCall(tenantOrgName, initRequest);
            }

        }
        return resp;
    }


    private static Response sendInitCall(String tenantOrgName, DotControlInitRequest initRequest){
        ObjectMapper mapper = new ObjectMapper();
        Response resp = null;
        try {
            resp = RestAssured.given().log().all()
                    .header("Authorization", PortalAuthToken.getAccessTokenForPortalUser(tenantOrgName, "main"))
                    .contentType(ContentType.JSON)
                    .body(mapper.writeValueAsString(initRequest))
                    .contentType(ContentType.JSON)
                    .body(initRequest)
                    .post(Endpoints.DOT_CONTROL_INIT_MESSAGE);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return resp;
    }

}
