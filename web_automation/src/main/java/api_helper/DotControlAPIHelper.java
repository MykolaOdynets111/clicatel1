package api_helper;

import dataManager.dot_control.DotControlCreateIntegrationInfo;
import dataManager.jackson_schemas.dot_control.DotControlRequestMessage;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import java_server.Server;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.Assert;

public class DotControlAPIHelper {

    public static void waitForServerToBeReady(){
        for(int i = 0; i<10; i++){
            if(RestAssured.get(Server.getServerURL()).statusCode()==200){
                break;
            }else{
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void createIntegration(String tenantOrgName, DotControlCreateIntegrationInfo newIntegrationInfo){
        Response resp =
        RestAssured.given().log().all()
                .header("Content-Type", "application/json")
                .header("Authorization", RequestSpec.getAccessTokenForPortalUser(tenantOrgName))
                .body("{\n" +
                        "  \"channels\": [\n" +
                        "    {\n" +
                        "      \"name\": \"" + newIntegrationInfo.getName() + "\",\n" +
                        "      \"enabled\": " + newIntegrationInfo.getIsEnabled() + ",\n" +
                        "      \"apiToken\": \"" + newIntegrationInfo.getApiToken() + "\",\n" +
                        "      \"url\": \"" + newIntegrationInfo.getCallBackURL() + "\",\n" +
                        "      \"config\": {}\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}")
                .post(Endpoints.CREATE_DOT_CONTROL_INTEGRATION);
        if(!(resp.statusCode()==200)) {
            Assert.assertTrue(false, "Integration creating was not successful\n" +
            "Status code " + resp.statusCode()+
            "\n Body: " + resp.getBody().asString());
        }
    }

    public static void sendMessage(DotControlRequestMessage requestMessage){
        ObjectMapper mapper = new ObjectMapper();
        Response resp = null;
        try {
            resp = RestAssured.given().log().all()
                    .header("Content-Type", "application/json")
                    .body(mapper.writeValueAsString(requestMessage))
                    .post(Endpoints.DOT_CONTROL_TO_BOT_MESSAGE);
            if(!(resp.statusCode()==200)) {
                Assert.assertTrue(false, "Integration creating was not successful\n" +
                        "Status code " + resp.statusCode()+
                        "\n Body: " + resp.getBody().asString());
            }

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        int a = 2;
    }


}
