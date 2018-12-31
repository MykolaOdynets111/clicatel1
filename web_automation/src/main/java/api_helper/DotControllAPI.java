package api_helper;

import dataManager.dot_control.DotControlCreateIntegrationInfo;
import dataManager.jackson_schemas.dot_control.DotControlRequestMessage;
import io.restassured.RestAssured;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

public class DotControllAPI {


    public static void createIntegration(String tenantOrgName, DotControlCreateIntegrationInfo newIntegrationInfo){
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
    }

    public static void sendMessage(DotControlRequestMessage requestMessage){
        ObjectMapper mapper = new ObjectMapper();
        try {
            RestAssured.given().log().all()
                    .header("Content-Type", "application/json")
                    .body(mapper.writeValueAsString(requestMessage))
                    .post(Endpoints.CREATE_DOT_CONTROL_INTEGRATION);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }


}
