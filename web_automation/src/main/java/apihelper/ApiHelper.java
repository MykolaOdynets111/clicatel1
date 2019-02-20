package apihelper;

import datamanager.Customer360PersonalInfo;
import datamanager.MC2Account;
import datamanager.Tenants;
import datamanager.Territories;
import datamanager.jacksonschemas.*;
import datamanager.jacksonschemas.tenantaddress.TenantAddress;
import datamanager.jacksonschemas.usersessioninfo.UserSession;
import drivermanager.ConfigManager;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.path.json.exception.JsonPathException;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.Assert;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class ApiHelper {

    private static  List<HashMap> tenantsInfo=null;
//    private static List<HashMap> tenantMessages=null;
    private static List<TafMessage> tenantMessages=null;



    public static String getInternalTenantConfig(String tenantName, String config){
        String url = String.format(Endpoints.INTERNAL_TENANT_CONFIG, tenantName);
        return RestAssured.get(url).jsonPath().get(config);
    }

    public static void getTenantConfig(String tenantOrgName){
        RestAssured.given()
                .header("Authorization", RequestSpec.getAccessTokenForPortalUser(tenantOrgName))
                .get(Endpoints.TENANT_CONFIG);
    }

    public static Map<String, String> getTenantInfoMap(String theValue) {
        Map<String, String> tenantsMap = new HashMap<>();
        List<HashMap> tenants = getAllTenantInfo();
        tenants.forEach(e-> tenantsMap.put(((String) e.get("tenantOrgName")).toLowerCase(),
                (String) e.get(theValue)));
        return tenantsMap;
    }

    public static void createUserProfile(String tenantName, String clientID) {
        RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .body("{ "+
                        "\"tenantName\": \""+tenantName+"\","+
                        "\"type\": \"TOUCH\"," +
                        "\"clientId\": \""+clientID+"\","+
                        "\"attributes\": {" +
                            "\"firstName\": \""+clientID+"\","+
                            "\"email\": \"aqa_test@gmail.com\"" +
                            "}" +
                        "}")
                .post(Endpoints.INTERNAL_CREATE_USER_PROFILE_ENDPOINT);
    }

    public static void deleteUserProfile(String tenantName, String clientID) {
        if(!clientID.isEmpty()) {
            String url = String.format(Endpoints.INTERNAL_DELETE_USER_PROFILE_ENDPOINT, tenantName, clientID);
            RestAssured.given()
                    .header("Accept", "application/json")
                    .delete(url);
        }
    }

    private static synchronized List<HashMap> getAllTenantInfo() {
        Response resp = RestAssured.given(RequestSpec.getRequestSpecification()).get(Endpoints.GET_ALL_TENANTS_ENDPOINT);
            try {
                if (tenantsInfo == null) {
                    tenantsInfo = resp.jsonPath().get("tenants");
                }
            } catch (JsonPathException e) {
                Assert.assertTrue(false, "Unexpected JSON response: \n" +
                        "Status code " + resp.statusCode() + "\n" +
                        "Body " + resp.getBody().asString() +"\n");
            }
        return tenantsInfo;
    }

    public static List<TafMessage> getTafMessages() {
            String url = String.format(Endpoints.TAF_MESSAGES, Tenants.getTenantUnderTestName());
            tenantMessages = RestAssured.given().get(url)
                    .jsonPath().getList("tafResponses", TafMessage.class);
        return tenantMessages;
    }

    public static void updateTafMessage(TafMessage tafMessage){
        ObjectMapper mapper = new ObjectMapper();
        String url = String.format(Endpoints.TAF_MESSAGES, Tenants.getTenantUnderTestName());
        try {
            RestAssured.given().log().all()
                    .header("Content-Type", "application/json")
                    .body("[" + mapper.writeValueAsString(tafMessage) + "]")
                    .put(url);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public static String getTenantMessageText(String id) {
        return getTafMessages().stream().filter(e -> e.getId().equals(id)).findFirst().get().getText();
    }

    public static void setWidgetVisibilityDaysAndHours(String tenantOrgName, String day, String startTime,  String endTime) {
        String body = createPutBodyForHours(day, startTime, endTime);
        RestAssured.given().log().all()
                .header("Content-Type", "application/json")
                .header("Authorization", RequestSpec.getAccessTokenForPortalUser(tenantOrgName))
                .body(body)
                .put(String.format(Endpoints.WIDGET_VISIBILITY_HOURS, getTenantInfoMap("id").get(tenantOrgName.toLowerCase())));
    }

    public static void setAgentSupportDaysAndHours(String tenantOrgName, String day, String startTime,  String endTime) {
        String body = createPutBodyForHours(day, startTime, endTime);
        Response resp = RestAssured.given().log().all()
                .header("Content-Type", "application/json")
                .header("Authorization", RequestSpec.getAccessTokenForPortalUser(tenantOrgName))
                .body(body)
                .put(String.format(Endpoints.AGENT_SUPPORT_HOURS, getTenantInfoMap("id").get(tenantOrgName.toLowerCase())));
        resp.getBody().asString();
    }

    public static List<SupportHoursItem> getAgentSupportDaysAndHours(String tenantOrgName) {
        Response resp = RestAssured.given().log().all()
                .header("Content-Type", "application/json")
                .header("Authorization", RequestSpec.getAccessTokenForPortalUser(tenantOrgName))
                .get(String.format(Endpoints.AGENT_SUPPORT_HOURS, getTenantInfoMap("id").get(tenantOrgName.toLowerCase())));
        try {
            return resp.getBody().jsonPath().getList("", SupportHoursItem.class);
        } catch(java.lang.ClassCastException e){
            Assert.assertTrue(false, "Incorrect body on updating support hours \n"
            +"Status code: " +resp.statusCode() + "\n"
                    +"tenantOrgName: "+ tenantOrgName
                    + "Authorization :"  + RequestSpec.getAccessTokenForPortalUser(tenantOrgName) + "\n"
          + resp.getBody().asString()  );
//            System.out.println(resp.getBody().asString());
            return null;
        }
    }

    private static String createPutBodyForHours(String day, String startTime, String endTime) {
        String body;
        if (day.equalsIgnoreCase("all week")) {
            body = "[\n" +
                    "  {\n" +
                    "    \"dayOfWeek\": \"MONDAY\",\n" +
                    "    \"startWorkTime\": \"00:00\",\n" +
                    "    \"endWorkTime\": \"23:59\"\n" +
                    "  },\n" +
                    "{\n" +
                    "    \"dayOfWeek\": \"TUESDAY\",\n" +
                    "    \"startWorkTime\": \"00:00\",\n" +
                    "    \"endWorkTime\": \"23:59\"\n" +
                    "  },\n" +
                    "{\n" +
                    "    \"dayOfWeek\": \"WEDNESDAY\",\n" +
                    "    \"startWorkTime\": \"00:00\",\n" +
                    "    \"endWorkTime\": \"23:59\"\n" +
                    "  },\n" +
                    "{\n" +
                    "    \"dayOfWeek\": \"THURSDAY\",\n" +
                    "    \"startWorkTime\": \"00:00\",\n" +
                    "    \"endWorkTime\": \"23:59\"\n" +
                    "  },\n" +
                    "{\n" +
                    "    \"dayOfWeek\": \"FRIDAY\",\n" +
                    "    \"startWorkTime\": \"00:00\",\n" +
                    "    \"endWorkTime\": \"23:59\"\n" +
                    "  },\n" +
                    "{\n" +
                    "    \"dayOfWeek\": \"SATURDAY\",\n" +
                    "    \"startWorkTime\": \"00:00\",\n" +
                    "    \"endWorkTime\": \"23:59\"\n" +
                    "  },\n" +
                    "{\n" +
                    "    \"dayOfWeek\": \"SUNDAY\",\n" +
                    "    \"startWorkTime\": \"00:00\",\n" +
                    "    \"endWorkTime\": \"23:59\"\n" +
                    "  }\n" +
                    "]";
        } else{
            body = "[{\n" +
                    "  \"dayOfWeek\": \"" + day.toUpperCase() + "\",\n" +
                    "  \"startWorkTime\": \"" + startTime + "\",\n" +
                    "  \"endWorkTime\": \"" + endTime + "\"\n" +
                    "}]";
        }
        return body;
    }

    public static void setAvailableForAllTerritories(String tenantOrgName){
        RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", RequestSpec.getAccessTokenForPortalUser(tenantOrgName))
                .body("{\n" +
                        "  \"availability\": \"AVAILABLE\"\n" +
                        "\n" +
                        "}")
                .post(Endpoints.WIDGET_VISIBILITY_TERRITORIES);
    }


    public static void setAvailabilityForTerritoryAndCountry(String tenantOrgName, String terrName, boolean terrAvailability,
                                                             String countryName, boolean countryAvailability){
        Territory targetTerr =  Territories.getTargetTerr(tenantOrgName, terrName);
        String territoryID = targetTerr.getTerritoryId();
        Country targetCountry = targetTerr.getCountry().stream().filter(e -> e.getName().equalsIgnoreCase(countryName))
                .findFirst().get();
        String countryID = targetCountry.getCountryId();
        RestAssured.given().log().all()
                .header("Content-Type", "application/json")
                .header("Authorization", RequestSpec.getAccessTokenForPortalUser(tenantOrgName))
                .body("{\n" +
                        "  \"availability\": \"LIMITED\",\n" +
                        "  \"territory\": [\n" +
                        "    {\n" +
                        "      \"territoryId\": \""+territoryID+"\",\n" +
                        "      \"available\": "+terrAvailability+",\n" +
                        "      \"country\": [\n" +
                        "        {\n" +
                        "          \"countryId\": \""+countryID+"\",\n" +
                        "          \"available\": "+countryAvailability+"\n" +
                        "        }\n" +
                        "      ]\n" +
                        "    }" +
                        "]" +
                        "}")
                .post(Endpoints.WIDGET_VISIBILITY_TERRITORIES);
    }

    public static void setAvailabilityForTerritory(String tenantOrgName, String terrName, boolean terrAvailability){
        Territory targetTerr =  Territories.getTargetTerr(tenantOrgName, terrName);
        String territoryID = targetTerr.getTerritoryId();
        RestAssured.given().log().all()
                .header("Content-Type", "application/json")
                .header("Authorization", RequestSpec.getAccessTokenForPortalUser(tenantOrgName))
                .body("{\n" +
                        "  \"availability\": \"LIMITED\",\n" +
                        "  \"territory\": [\n" +
                        "    {\n" +
                        "      \"territoryId\": \""+territoryID+"\",\n" +
                        "      \"available\": "+terrAvailability+"\n" +
                        "    }" +
                        "]" +
                        "}")
                .post(Endpoints.WIDGET_VISIBILITY_TERRITORIES);
    }


    public static List<TenantAddress> getTenantAddressInfo(String tenantName) {
        return RestAssured.given()
                .header("Accept", "application/json")
                .get(String.format(Endpoints.INTERNAL_TENANT_ADDRESS, tenantName))
                .jsonPath().getList("addresses", TenantAddress.class);
    }


    public static UserSession getLastUserSession(String userID, String tenant){
        return RestAssured.given()
                .header("Accept", "application/json")
                .get(String.format(Endpoints.INTERNAL_LAST_CLIENT_SESSION, tenant, userID))
                .getBody().as(UserSession.class);

    }


    public static void updateFeatureStatus(String tenantOrgName, String feature, String status){
        String tenantID = Tenants.getTenantInfo(tenantOrgName, "id");
        String url = String.format(Endpoints.INTERNAL_FEATURE_STATE,tenantID, feature, status);
        RestAssured.put(url);
    }

    public static boolean getFeatureStatus(String tenantOrgName, String FEATURE){
        Response resp = RestAssured.given().log().all()
                .header("Authorization", RequestSpec.getAccessTokenForPortalUser(tenantOrgName))
                .get(Endpoints.FEATURE);
        boolean featureStatus = false;
        try{
            featureStatus = resp.getBody().jsonPath().getBoolean(FEATURE);
        } catch(NullPointerException e){
            Assert.assertTrue(false, "Failed to get feature status from GET " +Endpoints.FEATURE+ " response\n"+
            "statusCode" + resp.statusCode() + "\n" +
            "respBody" + resp.getBody().asString());
        }
        return featureStatus;
    }

    public static int getNumberOfLoggedInAgents(){
        String url = String.format(Endpoints.INTERNAL_COUNT_OF_LOGGED_IN_AGENTS, Tenants.getTenantUnderTestName());
        return (int) RestAssured.get(url).getBody().jsonPath().get("loggedInAgentsCount");
    }

    public static Response getAgentInfo(String tenantOrgName) {
        String token = RequestSpec.getAccessTokenForPortalUser(tenantOrgName);
        return RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", token)
                .get(String.format(Endpoints.AGENT_INFO, token));
    }

    public static void logoutTheAgent(String tenantOrgName) {
        String tenantID = Tenants.getTenantInfo(tenantOrgName, "id");
        RestAssured.given().header("Accept", "application/json")
               .get(String.format(Endpoints.INTERNAL_LOGOUT_AGENT, tenantID));
    }

    public static void decreaseTouchGoPLan(String tenantOrgName){
        MC2Account targetAccount = MC2Account.getAccountByOrgName(ConfigManager.getEnv(), tenantOrgName);
        RestAssured.given().log().all()
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "  \"accountId\": \""+targetAccount.getAccountID()+"\",\n" +
                        "  \"messageType\": \"TOUCH_STARTED\"" +
                        "}")
                .put(Endpoints.INTERNAL_DECREASING_TOUCHGO_PLAN);
    }

    public static String getChannelID(String tenantOrgName, String integrationChanel){
        List<IntegrationChannel> existedChannels = RestAssured.given()
                                                            .header("Authorization", RequestSpec.getAccessTokenForPortalUser(tenantOrgName))
                                                            .get(Endpoints.INTEGRATION_EXISTING_CHANNELS)
                                                            .getBody().jsonPath().getList("", IntegrationChannel.class);
    return  existedChannels.stream().filter(e -> e.getChannelType().equalsIgnoreCase(integrationChanel))
            .findFirst().get().getId();
    }

    public static void setIntegrationStatus(String tenantOrgName, String integration, boolean integrationStatus){
        RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", RequestSpec.getAccessTokenForPortalUser(tenantOrgName))
                .body("{\n" +
                        "  \"channelId\": \""+getChannelID(tenantOrgName, integration)+"\",\n" +
                        "  \"enable\": true\n" +
                        "}")
                .put(Endpoints.INTEGRATIONS_ENABLING_DISABLING);
    }

    public static ResponseBody getInfoAboutFBIntegration(String tenantOrgName){
        return RestAssured.given()
                .header("Authorization", RequestSpec.getAccessTokenForPortalUser(tenantOrgName))
                .get(Endpoints.FACEBOOK_INTEGRATION)
                .getBody();
    }

    public static void delinkFBIntegration(String tenantOrgName){
        RestAssured.given()
                .header("Authorization", RequestSpec.getAccessTokenForPortalUser(tenantOrgName))
                .delete(Endpoints.FACEBOOK_INTEGRATION)
                .getBody();
    }

    public static void setStatusForWelcomeMesage(String tenantName, String messageStatus){
        RestAssured.given()
                .header("Content-Type", "application/json")
                .body("[\n" +
                        "  {\n" +
                        "    \"id\": \"welcome_message\",\n" +
                        "    \"category\": \"greeting\",\n" +
                        "    \"text\": \"Thank you for reaching out. How can we help you?\",\n" +
                        "    \"description\": \"welcome flow\",\n" +
                        "    \"enabled\": true,\n" +
                        "    \"type\": \"TEXT\"\n" +
                        "  }\n" +
                        "]")
                .put(Endpoints.INTEGRATIONS_ENABLING_DISABLING);
    }

    public static Integration getIntegration(String tenantOrgName, String integrationType){
        return RestAssured.given()
                .header("Authorization", RequestSpec.getAccessTokenForPortalUser(tenantOrgName))
                .get(Endpoints.INTEGRATIONS)
                .getBody().jsonPath().getList("", Integration.class)
                .stream()
                .filter(e -> e.getType().equalsIgnoreCase(integrationType))
                .findFirst().get();
    }

    public static List<ChatHistoryItem> getChatHistory(String tenantOrgName, String sessionId){
        return RestAssured.given()
                .header("Authorization", RequestSpec.getAccessTokenForPortalUser(tenantOrgName))
                .get(String.format(Endpoints.CHAT_HISTORY, sessionId))
                .getBody().jsonPath().getList("records", ChatHistoryItem.class);
    }

    public static void updateSessionCapacity(String tenantOrgName, int availableChats){
        RequestSpec.clearAccessTokenForPortalUser();
        RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", RequestSpec.getAccessTokenForPortalUser(tenantOrgName))
                .put(Endpoints.SESSION_CAPACITY + availableChats);
    }

    public static List<OvernightTicket> getOvernightTicketsByStatus(String tenantOrgName, String ticketStatus){
        return RestAssured.given()
                .header("Authorization", RequestSpec.getAccessTokenForPortalUser(tenantOrgName))
                .get(Endpoints.AGENT_OVERNIGHT_TICKETS + ticketStatus.toUpperCase())
                .getBody().jsonPath().getList("", OvernightTicket.class);
    }

    public static void closeAllOvernightTickets(String tenantOrgName){
        List<OvernightTicket> allTicketsByStatus = getOvernightTicketsByStatus(tenantOrgName, "ASSIGNED");
        for(OvernightTicket ticket : allTicketsByStatus){
            RestAssured.given()
                    .header("Authorization", RequestSpec.getAccessTokenForPortalUser(tenantOrgName))
                    .post(Endpoints.PROCESS_OVERNIGHT_TICKET + ticket.getId());
        }
    }

    public static String getActiveSessionIdByClientId(String tenantName, String clineId, String integrationType){
        String url = String.format(Endpoints.INTERNAL_ACTIVE_SESSIONS, tenantName, clineId, integrationType);
        String sessionId = "";
        ResponseBody respBody =  RestAssured.get(url).getBody();
        try{
            sessionId = respBody.jsonPath().get("sessionId");
        }catch(JsonPathException e){
            Assert.assertTrue(false, "Failed to get session Id\n"+
            "resp body:" + respBody.asString());
        }
        return sessionId;
    }

    public static Customer360PersonalInfo getCustomer360PersonalInfo(String tenantOrgName, String clineId, String integrationType){
        String sessionId = getActiveSessionIdByClientId(Tenants.getTenantUnderTestName(), clineId, integrationType);
        JsonPath respJSON = RestAssured.given()
                .header("Authorization", RequestSpec.getAccessTokenForPortalUser(tenantOrgName))
                .get(Endpoints.CUSTOMER_VIEW + sessionId)
                .getBody().jsonPath();

        String fullName = "";
        if(respJSON.getString("personalDetails.firstName") == null &&
                respJSON.getString("personalDetails.lastName") == null){
            fullName = respJSON.getString("clientProfiles.clientId[0]");
        } else {
            String lastName =respJSON.getString("personalDetails.lastName") == null ? "" : respJSON.getString("personalDetails.lastName");
            fullName = respJSON.getString("personalDetails.firstName") + " " + lastName;
        }
        String location = respJSON.getString("personalDetails.location") == null ? "Unknown location" : respJSON.getString("personalDetails.location");

        long customerSinceTimestamp  = respJSON.getLong("personalDetails.customerSince");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM yyyy");
        String customerSince =  LocalDateTime.ofInstant(Instant.ofEpochMilli(customerSinceTimestamp),
                                TimeZone.getDefault().toZoneId()).
                format(formatter);

        String channelUsername = "";
        try {
            channelUsername = respJSON.getString("personalDetails.channelUsername").isEmpty() ? "Unknown" : respJSON.getString("personalDetails.channelUsername");
        }catch (NullPointerException e){
            channelUsername = respJSON.getString("personalDetails.channelUsername") == null ? "Unknown" : respJSON.getString("personalDetails.channelUsername");

        }
        String phone =  (respJSON.getString("clientProfiles.attributes.phone[0]")==null || respJSON.getString("clientProfiles.attributes.phone[0]").isEmpty()) ? "Unknown" : respJSON.getString("clientProfiles.attributes.phone[0]");
        String email = (respJSON.getString("personalDetails.email")==null || respJSON.getString("personalDetails.email").isEmpty()) ? "Unknown" : respJSON.getString("personalDetails.email");

        return new Customer360PersonalInfo(fullName, location,
                "Customer since: " + customerSince, email,
                channelUsername, phone.replaceAll(" ", "") );
    }

    public static void deleteAgentPhotoForMainAQAAgent(String tenantOrgName){
        String agentId = getAgentInfo(tenantOrgName).getBody().jsonPath().get("id");
        RestAssured.given()
                .header("Authorization", RequestSpec.getAccessTokenForPortalUser(tenantOrgName))
                .delete(String.format(Endpoints.DELETE_AGENT_IMAGE, agentId));
    }

}
