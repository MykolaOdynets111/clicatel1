package apihelper;

import datamanager.Tenants;
import datamanager.jacksonschemas.orca.OrcaEvent;
import driverfactory.URLs;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import mc2api.auth.PortalAuthToken;
import org.testng.Assert;

public class ApiORCA {

     public static String createIntegration(String callBackUrl){
        Response resp = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(prepareIntegrationCAllData(callBackUrl))
                .post(String.format(Endpoints.CREATE_ORCA_INTEGRATION, Tenants.getTenantId()));
        return validateIntegrationResponse(resp, "Create");
    }

    public static String updateIntegration(String callBackUrl, String orcaId){
        Response resp = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(prepareIntegrationCAllData(callBackUrl))
                .put(String.format(Endpoints.UPDATE_ORCA_INTEGRATION, Tenants.getTenantId(),orcaId));
        return validateIntegrationResponse(resp, "Update");
    }

    private static String prepareIntegrationCAllData(String callBackUrl){
        return  "{\n" +
                "  \"enabled\": true,\n" +
                "  \"config\": {\n" +
                "    \"businessId\": \"cam_flow\",\n" +
                "    \"callbackUrl\": \""+ callBackUrl +"\",\n" +
                "    \"location\": true,\n" +
                "    \"media\": true\n" +
                "  }\n" +
                "}";
    }

    private static String validateIntegrationResponse(Response resp, String method){
        if(!(resp.statusCode()==200)) {
            Assert.fail("ORCA integration "+method+" was not successful\n" + "Status code " + resp.statusCode()+
                    "\n Body: " + resp.getBody().asString());
        }
        String token = resp.getBody().jsonPath().get("id");
        System.out.println("!! Api token from "+method+" ORCA integration: " + token);
        return token;
    }

    public static Response getORCAIntegrationsList(){
        Response resp =  RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .get(String.format(Endpoints.ORCA_INTEGRATIONS_LIST, Tenants.getTenantId()));
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
