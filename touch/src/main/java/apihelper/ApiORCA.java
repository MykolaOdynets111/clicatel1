package apihelper;

import com.github.javafaker.Faker;
import datamanager.Tenants;
import datamanager.jacksonschemas.orca.OrcaEvent;
import driverfactory.URLs;
import interfaces.DateTimeHelper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;

import java.time.Instant;

public class ApiORCA extends ApiHelper{

     public static String createIntegration(String channel, String callBackUrl){
        Response resp = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(prepareIntegrationCAllData(callBackUrl))
                .post(String.format(Endpoints.CREATE_ORCA_INTEGRATION, channel, Tenants.getTenantId()));
        return validateIntegrationResponse(resp, "Create");
    }

    public static String updateIntegration(String channel, String callBackUrl, String orcaId){
        Response resp = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(prepareIntegrationCAllData(callBackUrl))
                .put(String.format(Endpoints.UPDATE_ORCA_INTEGRATION, channel, Tenants.getTenantId(),orcaId));
        return validateIntegrationResponse(resp, "Update");
    }

    private static String prepareIntegrationCAllData(String callBackUrl){
        return  "{\n" +
                "  \"enabled\": true,\n" +
                "  \"config\": {\n" +
                "    \"businessId\": \"cam_flow\",\n" +
                "    \"apiToken\": \"AQAApiToken"+ Instant.now().getEpochSecond() +"\",\n" +
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
        String token = resp.getBody().jsonPath().get("config.apiToken");
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

    public static String createNewUser(OrcaEvent orcaEvent, String channelId){
        Response resp = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "  \"tenantId\": \"" + Tenants.getTenantId() + "\",\n" +
                        "  \"fullName\": \"" + orcaEvent.getUserInfo().getUserName() + "\",\n" +
                        "  \"createdDate\": \"2022-08-10T11:47:16.272Z\",\n" +
                        "  \"modifiedDate\": \"2022-08-10T11:47:16.272Z\",\n" +
                        "  \"whatsapp\": {\n" +
                        "    \"channelId\": \"" + channelId + "\",\n" +
                        "    \"phoneNumber\": \"" + orcaEvent.getSourceId() + "\",\n" +
                        "    \"userName\": \"" + orcaEvent.getUserInfo().getUserName() + "\"\n" +
                        "  }\n" +
                        "}")
                .post(Endpoints.NEW_USER);
        if(!(resp.statusCode()==200)) {
            Assert.fail(String.format("New user was not created\nStatus code %s\n Body: %s",
                    resp.statusCode(), resp.getBody().asString()));
        }
        return resp.getBody().jsonPath().get("id");
    }

    public static void createClosedChat(String agent, int time, OrcaEvent orcaEvent, String channelId){
        Response resp = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "  \"chatId\": \"" + new Faker().numerify("aqaChatId###########") +"\",\n" +
                        "  \"tenantId\": \"" + Tenants.getTenantId() + "\",\n" +
                        "  \"mc2AccountId\": \"" + Tenants.getMC2Id() + "\",\n" +
                        "  \"channel\": {\n" +
                        "    \"id\": \"" + channelId + "\",\n" +
                        "    \"type\": \"WHATSAPP\"\n" +
                        "  },\n" +
                        "  \"transportType\": \"ORCA\",\n" +
                        "  \"endUserId\": \"" + createNewUser(orcaEvent, channelId) + "\",\n" +
                        "  \"assignedToAgentId\": \"" + ApiHelper.getAgentId(Tenants.getTenantUnderTestOrgName(), agent) + "\",\n" +
                        "  \"initialMessage\": \"" + orcaEvent.getContent().getEvent().getText() + "\",\n" +
                        "  \"createdTime\": \"" + DateTimeHelper.getDateTimeWithHoursShift(time) + "\"\n" +
                        "}")
                .post(Endpoints.CLOSED_CHATS);
        if(!(resp.statusCode()==200)) {
            Assert.fail(String.format("Closed chat was not created\nStatus code %s\n Body: %s",
                    resp.statusCode(), resp.getBody().asString()));
        };
    }


}
