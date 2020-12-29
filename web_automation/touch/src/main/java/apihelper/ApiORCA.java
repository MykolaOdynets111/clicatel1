package apihelper;

import datamanager.jacksonschemas.orca.OrcaEvent;
import driverfactory.URLs;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import mc2api.auth.PortalAuthToken;
import org.testng.Assert;

public class ApiORCA {

     public static String createIntegration(String tenantOrgName, String callBackUrl){
        String tenantId = ApiHelper.getTenantInfoMap(tenantOrgName).get("id");
        Response resp = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", PortalAuthToken.getAccessTokenForPortalUser(tenantOrgName, "main"))
                .body(prepareIntegrationCAllData(tenantOrgName, callBackUrl))
                .post(Endpoints.CREATE_UPDATE_ORCA_INTEGRATION);
        return validateIntegrationResponse(resp, "Create");
    }

    public static String updateIntegration(String tenantOrgName, String callBackUrl){
        String tenantId = ApiHelper.getTenantInfoMap(tenantOrgName).get("id");
        Response resp = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", PortalAuthToken.getAccessTokenForPortalUser(tenantOrgName, "main"))
                .body(prepareIntegrationCAllData(tenantOrgName, callBackUrl))
                .put(Endpoints.CREATE_UPDATE_ORCA_INTEGRATION);
        return validateIntegrationResponse(resp, "Update");
    }

    private static String prepareIntegrationCAllData(String tenantOrgName, String callBackUrl){
        String tenantId = ApiHelper.getTenantInfoMap(tenantOrgName).get("id");
        return "{\n" +
                "  \"channel\": {\n" +
                "    \"type\":\"abc\"\n" +
                "  },\n" +
                "  \"enabled\": true,\n" +
                " \"tenantId\":\"" + tenantId + "\",\n" +
                " \"transport\": {\n" +
                "    \"apiToken\": null,\n" +
                "    \"type\": \"orca\",\n" +
                "    \"callbackUrl\": \"" + callBackUrl + "\",\n" +
                "    \"businessId\": \"cam_flow\",\n" +
                "    \"s3AttachmentBucketName\": \"stage-abc-adapter-media\",\n" +
                "    \"s3AdditionalGrantee\": []" +
                " }\n" +
                "}";
    }

    private static String validateIntegrationResponse(Response resp, String method){
        if(!(resp.statusCode()==200)) {
            Assert.fail("ORCA integration "+method+" was not successful\n" + "Status code " + resp.statusCode()+
                    "\n Body: " + resp.getBody().asString());
        }
        String token = resp.getBody().jsonPath().get("transport.apiToken");
        System.out.println("!! Api token from "+method+" ORCA integration: " + token);
        return token;
    }

    public static Response getORCAIntegrationsList(String tenantOrgName){
        Response resp =  RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", PortalAuthToken.getAccessTokenForPortalUser(tenantOrgName, "main")).get(Endpoints.ORCA_INTEGRATIONS_LIST);
        return resp;
    }

    public static void sendMessageToAgent(OrcaEvent messageBody) {
        Response resp = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(messageBody).post(URLs.getORCAMessageURL());
        if(!(resp.statusCode()==200)) {
            Assert.fail(String.format("ORCA message was not send\nStatus code %s\n Body: %s\nOrcaEvent: %s",
                    resp.statusCode(), resp.getBody().asString(), messageBody));
        }
    }

}
