package apihelper;

import datamanager.*;
import datamanager.jacksonschemas.*;
import datamanager.jacksonschemas.tenantaddress.TenantAddress;
import datamanager.jacksonschemas.usersessioninfo.UserSession;
import dbmanager.DBConnector;
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
import java.time.ZoneId;
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

    public static Map<String, String> getAllTenantsInfoMap(String theValue) {
        Map<String, String> tenantsMap = new HashMap<>();
        List<HashMap> tenants = getAllTenantInfo();
        tenants.forEach(e-> tenantsMap.put(((String) e.get("tenantOrgName")).toLowerCase(),
                (String) e.get(theValue)));
        return tenantsMap;
    }

    public static Map<String, String> getTenantInfoMap(String tenantOrgName) {
        Tenants.setTenantUnderTestNames(tenantOrgName);
        Response resp = RestAssured.given().log().all()
                .get(Endpoints.INTERNAL_TENANTS + Tenants.getTenantUnderTestName());
        Map<String, String> tenantInf = new HashMap<>();
        try{
             tenantInf = resp.getBody().jsonPath().getMap("");
        } catch (JsonPathException e){
            Assert.assertTrue(false, "Failed to get tenant info\n"+
            "URL: " + Endpoints.INTERNAL_TENANTS + Tenants.getTenantUnderTestName() + "\n" +
            "resp status code:" + resp.statusCode() + "\n"+
            "resp body: " + resp.getBody().asString());
        }
        return tenantInf;
    }


    public static void createUserProfile(String tenantName, String clientID) {
        Response resp;
        String tenantId = ApiHelper.getTenantInfoMap(Tenants.getTenantUnderTestOrgName()).get("id");

        resp = RestAssured.given()
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .body("{ " +
                            "\"tenantId\": \"" + tenantId + "\"," +
                            "\"type\": \"TOUCH\"," +
                            "\"clientId\": \"" + clientID + "\"," +
                            "\"attributes\": {" +
                            "\"firstName\": \"" + clientID + "\"," +
                            "\"email\": \"aqa_test@gmail.com\"" +
                            "}" +
                            "}")
                    .post(Endpoints.INTERNAL_CREATE_USER_PROFILE_ENDPOINT);
        Assert.assertTrue(resp.statusCode()==200,
                "Creating of user profile was not successful\n" +
        "resp status code: " + resp.statusCode() + "\n" +
        "resp body: " + resp.getBody().asString());
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
        tenantMessages = RestAssured.given()
                .header("Content-Type", "application/json")
                .get(url)
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
                .put(String.format(Endpoints.WIDGET_VISIBILITY_HOURS, ApiHelper.getTenantInfoMap(tenantOrgName).get("id")));
    }

    public static void setAgentSupportDaysAndHours(String tenantOrgName, String day, String startTime,  String endTime) {
        String body = createPutBodyForHours(day, startTime, endTime);
        Response resp = RestAssured.given().log().all()
                .header("Content-Type", "application/json")
                .header("Authorization", RequestSpec.getAccessTokenForPortalUser(tenantOrgName))
                .body(body)
                .put(String.format(Endpoints.AGENT_SUPPORT_HOURS, ApiHelper.getTenantInfoMap(tenantOrgName).get("id")));
        resp.getBody().asString();
    }

    public static List<SupportHoursItem> getAgentSupportDaysAndHours(String tenantOrgName) {
        Response resp = RestAssured.given().log().all()
                .header("Content-Type", "application/json")
                .header("Authorization", RequestSpec.getAccessTokenForPortalUser(tenantOrgName))
                .get(String.format(Endpoints.AGENT_SUPPORT_HOURS, ApiHelper.getTenantInfoMap(tenantOrgName).get("id")));
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
        String tenantID = ApiHelper.getTenantInfoMap(tenantOrgName).get("id");
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
                .get(Endpoints.AGENT_INFO_ME);
    }

    public static void logoutTheAgent(String tenantOrgName) {
        String tenantID = ApiHelper.getTenantInfoMap(tenantOrgName).get("id");
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
        return RestAssured.given().log().all()
                .header("Authorization", RequestSpec.getAccessTokenForPortalUser(tenantOrgName))
                .get(Endpoints.AGENT_OVERNIGHT_TICKETS + ticketStatus.toUpperCase())
                .getBody().jsonPath().getList("", OvernightTicket.class);
    }

    public static void closeAllOvernightTickets(String tenantOrgName) {
        List<OvernightTicket> allTicketsByStatus = getOvernightTicketsByStatus(tenantOrgName, "ASSIGNED");
        for(OvernightTicket ticket : allTicketsByStatus){
            RestAssured.given()
                    .header("Authorization", RequestSpec.getAccessTokenForPortalUser(tenantOrgName))
                    .post(Endpoints.PROCESS_OVERNIGHT_TICKET + ticket.getId());
        }
    }

    public static String getActiveSessionIdByClientId(String tenantName, String clientId, String integrationType){
        String tenantID = ApiHelper.getTenantInfoMap(Tenants.getTenantUnderTestOrgName()).get("id");
        String url = String.format(Endpoints.INTERNAL_CHAT_BY_CLIENT, tenantID, clientId);
        String sessionId = "";
        Response resp =  RestAssured.get(url);
        try{
            sessionId = (String) ((Map) resp.getBody().jsonPath().getList("content.sessions[0]")
                    .stream()
                    .map(e -> (Map) e)
                    .filter(map -> map.get("state").equals("ACTIVE"))
                    .findFirst().get()).get("sessionId");
        }catch(JsonPathException e){
            Assert.assertTrue(false, "Failed to get session Id\n"+
                    "resp status: " + resp.statusCode() + "\n" +
            "resp body:" + resp.getBody().asString() + "\n");
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

        String customerSinceFullDate  = respJSON.getString("personalDetails.customerSince");
        ZoneId zoneId =  TimeZone.getDefault().toZoneId();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        String customerSince =  LocalDateTime.parse(customerSinceFullDate, formatter).atZone(ZoneId.of("UTC"))
                                                        .withZoneSameInstant(zoneId).toLocalDateTime()
                                                        .format(DateTimeFormatter.ofPattern("d MMM yyyy"));

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

    public static Response getSessionDetails(String clientID){
        return RestAssured.get(String.format(Endpoints.INTERNAL_SESSION_DETAILS, Tenants.getTenantUnderTestName(), clientID));
    }

    public static Response getFinishedChatsByLoggedInAgentAgent(String tenantOrgName, int page, int size){
        String agentId = getAgentInfo(tenantOrgName).getBody().jsonPath().get("id");
        String url = String.format(Endpoints.INTERNAL_GET_CHATS_FINISHED_BY_AGENT, agentId, page, size);
        return RestAssured.get(url);
    }

    public static Response getActiveChatByAgent(){
        return RestAssured.given()
                .header("Authorization", RequestSpec.getAccessTokenForPortalUser(Tenants.getTenantUnderTestOrgName()))
                .get(String.format(Endpoints.ACTIVE_CHATS_BY_AGENT, ConfigManager.getEnv()));
    }

    public static void closeActiveChats(){
        List<String> conversationIds = getActiveChatByAgent().getBody().jsonPath().getList("content.sessions.conversationId");
        for(String conversationId : conversationIds){
            RestAssured.given()
                    .header("Authorization", RequestSpec.getAccessTokenForPortalUser(Tenants.getTenantUnderTestOrgName()))
                    .delete(Endpoints.CLOSE_ACTIVE_CHAT + conversationId);
            }
    }

    public static String getClientProfileId(String clientID){
        return getSessionDetails(clientID).getBody().jsonPath().getString("data.clientProfileId[0]");
    }

    public static List<CRMTicket> getCRMTicket(String clientID, String type){
        String clientProfileId = DBConnector.getClientProfileID(ConfigManager.getEnv(), clientID, type, 0);
//        String clientProfileId = getClientProfileId(clientID);
        return RestAssured.given()
                .header("Authorization", RequestSpec.getAccessTokenForPortalUser(Tenants.getTenantUnderTestOrgName()))
                .get(String.format(Endpoints.CRM_TICKET, clientProfileId))
                .getBody().jsonPath().getList("", CRMTicket.class);
    }

    public static void createCRMTicket(String clientID){
        String clientProfileId = getClientProfileId(clientID);
        RestAssured.given()
                .header("Authorization", RequestSpec.getAccessTokenForPortalUser(Tenants.getTenantUnderTestOrgName()))
                .post(String.format(Endpoints.CRM_TICKET, clientProfileId))
                .getBody().jsonPath().getList("", CRMTicket.class);
    }

}
