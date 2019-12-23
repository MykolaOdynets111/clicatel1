package apihelper;

import com.github.javafaker.Faker;
import datamanager.Customer360PersonalInfo;
import datamanager.MC2Account;
import datamanager.Tenants;
import datamanager.Territories;
import datamanager.jacksonschemas.*;
import datamanager.jacksonschemas.chathistory.ChatHistory;
import datamanager.jacksonschemas.departments.Department;
import datamanager.jacksonschemas.tenantaddress.TenantAddress;
import datamanager.jacksonschemas.usersessioninfo.ClientProfile;
import datamanager.jacksonschemas.usersessioninfo.UserSession;
import dbmanager.DBConnector;
import drivermanager.ConfigManager;
import interfaces.DateTimeHelper;
import interfaces.VerificationHelper;
import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.path.json.exception.JsonPathException;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import mc2api.auth.PortalAuthToken;
import org.testng.Assert;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ApiHelper implements DateTimeHelper, VerificationHelper {

    private static  List<HashMap> tenantsInfo=null;
    private static List<TafMessage> tenantMessages=null;

    public static String getInternalTenantConfig(String tenantName, String config){
        String url = String.format(Endpoints.INTERNAL_TENANT_CONFIG, tenantName);
        return RestAssured.get(url).jsonPath().get(config).toString();
    }

    public static Response getTenantConfig(String tenantOrgName){
        String tenantId = ApiHelper.getTenantInfoMap(tenantOrgName).get("id");
        return RestAssured.given()
                .header("Authorization", PortalAuthToken.getAccessTokenForPortalUser(tenantOrgName, "main"))
                .get(String.format(Endpoints.TENANT_CONFIG, tenantId));
    }

    public static Map<String, String> getAllTenantsInfoMap(String theValue) {
        Map<String, String> tenantsMap = new HashMap<>();
        List<HashMap> tenants = getAllTenantsInfo();
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
            Assert.fail( "Failed to get tenant info\n"+
            "URL: " + Endpoints.INTERNAL_TENANTS + Tenants.getTenantUnderTestName() + "\n" +
            "resp status code:" + resp.statusCode() + "\n"+
            "resp body: " + resp.getBody().asString());
        }
        return tenantInf;
    }

    public static Response getTenantInfo(String tenantOrgName) {
        Response resp =  RestAssured.given()
                .header("Authorization", PortalAuthToken.getAccessTokenForPortalUser(tenantOrgName, "main"))
                .get(Endpoints.TENANT_INFO);
        Assert.assertEquals(resp.statusCode(),200, "Failed to get tenant info\n"+
                "URL: " + Endpoints.TENANT_INFO + "\n" +
                "resp status code:" + resp.statusCode() + "\n"+
                "resp body: " + resp.getBody().asString());
        return resp;
    }

    public static Response createUserProfile(String tenantName, String clientID) {
        Response resp;
        String tenantId = ApiHelper.getTenantInfoMap(Tenants.getTenantUnderTestOrgName()).get("id");

        resp = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
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
        Assert.assertEquals(resp.statusCode(), 200,
                "Creating of user profile was not successful\n" +
                "resp body: " + resp.getBody().asString());
        return resp;
    }

    public static void createUserProfileWithPhone(String clientID, String phoneNumber){
        Response resp;
        String tenantId = ApiHelper.getTenantInfoMap(Tenants.getTenantUnderTestOrgName()).get("id");

        resp = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("{ " +
                        "\"tenantId\": \"" + tenantId + "\"," +
                        "\"type\": \"TOUCH\"," +
                        "\"clientId\": \"" + clientID + "\"," +
                        "\"attributes\": {" +
                        "\"firstName\": \"" + clientID + "\"," +
                        "\"email\": \"aqa_test@gmail.com\"," +
                        "\"phone\": \"" + phoneNumber + "\"" +
                        "}" +
                        "}")
                .post(Endpoints.INTERNAL_CREATE_USER_PROFILE_ENDPOINT);
        Assert.assertEquals(resp.statusCode(), 200,
                "Creating of user profile was not successful\n" +
                        "resp body: " + resp.getBody().asString());
    }

    public static void deleteUserProfile(String tenantName, String clientID) {
        if(!clientID.isEmpty()) {
            String url = String.format(Endpoints.INTERNAL_DELETE_USER_PROFILE_ENDPOINT, tenantName, clientID);
            RestAssured.given()
                    .accept(ContentType.JSON)
                    .delete(url);
        }
    }

    public static synchronized List<HashMap> getAllTenantsInfo() {
        Response resp = RestAssured.given(RequestScpec.getRequestSpecification()).get(Endpoints.GET_ALL_TENANTS_ENDPOINT);
            try {
                if (tenantsInfo == null) {
                    tenantsInfo = resp.jsonPath().get("tenants");
                }
            } catch (JsonPathException e) {
                Assert.fail("Unexpected JSON response: \n" +
                        "Status code " + resp.statusCode() + "\n" +
                        "Body " + resp.getBody().asString() +"\n");
            }
        return tenantsInfo;
    }

    public static List<TafMessage> getTafMessages() {
        String url = String.format(Endpoints.TAF_MESSAGES, Tenants.getTenantUnderTestName());
        Response resp = RestAssured.given()
                .contentType(ContentType.JSON)
                .get(url);
        try {
            tenantMessages = resp.jsonPath().getList("tafResponses", TafMessage.class);
        }catch (JsonPathException e){
            Assert.fail("Getting taf response was not successful \n" +
                            "Resp code: " + resp.statusCode() + "\n" +
                            "Resp body: " + resp.getBody().asString() + "\n");
        }
        return tenantMessages;
    }

    public static List<TafMessage> getDefaultTafMessages() {
        String url = String.format(Endpoints.TAF_MESSAGES, "null");
        tenantMessages = RestAssured.given()
                .contentType(ContentType.JSON)
                .get(url)
                .jsonPath().getList("tafResponses", TafMessage.class);
        return tenantMessages;
    }

    public static void updateTafMessage(TafMessage tafMessage){
        String url = String.format(Endpoints.TAF_MESSAGES, Tenants.getTenantUnderTestName());
        RestAssured.given().log().all()
                    .contentType(ContentType.JSON)
                    .body(Arrays.asList(tafMessage))
                    .put(url);
    }

    public static String getTenantMessageText(String id) {
        return getTafMessages().stream().filter(e -> e.getId().equals(id)).findFirst().get().getText();
    }

    public static String getDefaultTenantMessageText(String id) {
        return getDefaultTafMessages().stream().filter(e -> e.getId().equals(id)).findFirst().get().getText();
    }

    public static void setWidgetVisibilityDaysAndHours(String tenantOrgName, String day, String startTime,  String endTime) {
        String body = createPutBodyForHours(day, startTime, endTime);
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", PortalAuthToken.getAccessTokenForPortalUser(tenantOrgName, "main"))
                .body(body)
                .put(String.format(Endpoints.WIDGET_VISIBILITY_HOURS, ApiHelper.getTenantInfoMap(tenantOrgName).get("id")));
    }

    public static Response setAgentSupportDaysAndHours(String tenantOrgName, String day, String startTime,  String endTime) {
        String body = createPutBodyForHours(day, startTime, endTime);
        return RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", PortalAuthToken.getAccessTokenForPortalUser(tenantOrgName, "main"))
                .body(body)
                .put(String.format(Endpoints.AGENT_SUPPORT_HOURS, ApiHelper.getTenantInfoMap(tenantOrgName).get("id")));
    }

    public static List<SupportHoursItem> getAgentSupportDaysAndHours(String tenantOrgName) {
        Response resp = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", PortalAuthToken.getAccessTokenForPortalUser(tenantOrgName, "main"))
                .get(String.format(Endpoints.AGENT_SUPPORT_HOURS, ApiHelper.getTenantInfoMap(tenantOrgName).get("id")));
        try {
            return resp.getBody().jsonPath().getList("", SupportHoursItem.class);
        } catch(ClassCastException e){
            Assert.fail("Incorrect body on updating support hours \n"
            +"Status code: " +resp.statusCode() + "\n"
                    +"tenantOrgName: "+ tenantOrgName
                    + "Authorization :"  + PortalAuthToken.getAccessTokenForPortalUser(tenantOrgName, "main") + "\n"
          + resp.getBody().asString()  );
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
                .contentType(ContentType.JSON)
                .header("Authorization", PortalAuthToken.getAccessTokenForPortalUser(tenantOrgName, "main"))
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
                .contentType(ContentType.JSON)
                .header("Authorization", PortalAuthToken.getAccessTokenForPortalUser(tenantOrgName, "main"))
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
                .contentType(ContentType.JSON)
                .header("Authorization", PortalAuthToken.getAccessTokenForPortalUser(tenantOrgName, "main"))
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
                .accept(ContentType.JSON)
                .get(String.format(Endpoints.INTERNAL_TENANT_ADDRESS, tenantName))
                .jsonPath().getList("addresses", TenantAddress.class);
    }


    public static UserSession getLastUserSession(String userID, String tenant){
        return RestAssured.given()
                .accept(ContentType.JSON)
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
                .header("Authorization", PortalAuthToken.getAccessTokenForPortalUser(tenantOrgName, "main"))
                .get(Endpoints.FEATURE);
        boolean featureStatus = false;
        try{
            featureStatus = resp.getBody().jsonPath().getBoolean(FEATURE);
        } catch(NullPointerException e){
            Assert.fail("Failed to get feature status from GET " +Endpoints.FEATURE+ " response\n"+
            "statusCode" + resp.statusCode() + "\n" +
            "respBody" + resp.getBody().asString());
        }
        return featureStatus;
    }

    public static int getNumberOfLoggedInAgents(){
        String url = String.format(Endpoints.INTERNAL_COUNT_OF_LOGGED_IN_AGENTS, Tenants.getTenantUnderTestName());
        return (int) RestAssured.get(url).getBody().jsonPath().get("loggedInAgentsCount");
    }

    public static Response getAgentInfo(String tenantOrgName, String agent) {
        String token = PortalAuthToken.getAccessTokenForPortalUser(tenantOrgName, agent);
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .get(Endpoints.AGENT_INFO_ME);
    }

    public static void logoutTheAgent(String tenantOrgName) {
        String tenantID = ApiHelper.getTenantInfoMap(tenantOrgName).get("id");
        RestAssured.given().accept(ContentType.JSON)
               .get(String.format(Endpoints.INTERNAL_LOGOUT_AGENT, tenantID));
    }

    public static void decreaseTouchGoPLan(String tenantOrgName){
        MC2Account targetAccount = MC2Account.getAccountByOrgName(ConfigManager.getEnv(), tenantOrgName);
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "  \"accountId\": \""+targetAccount.getAccountID()+"\",\n" +
                        "  \"messageType\": \"TOUCH_STARTED\"" +
                        "}")
                .put(Endpoints.INTERNAL_DECREASING_TOUCHGO_PLAN);
    }

    public static String getChannelID(String tenantOrgName, String integrationChanel){
        List<IntegrationChannel> existedChannels = RestAssured.given()
                                                            .header("Authorization", PortalAuthToken.getAccessTokenForPortalUser(tenantOrgName, "main"))
                                                            .get(Endpoints.INTEGRATION_EXISTING_CHANNELS)
                                                            .getBody().jsonPath().getList("", IntegrationChannel.class);
    return  existedChannels.stream().filter(e -> e.getChannelType().equalsIgnoreCase(integrationChanel))
            .findFirst()
            .orElseThrow(() -> new AssertionError("Integration channel '" +integrationChanel +"' is absent for " + tenantOrgName + " tenant"))
            .getId();
    }

    public static void setIntegrationStatus(String tenantOrgName, String integration, boolean integrationStatus){
        RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", PortalAuthToken.getAccessTokenForPortalUser(tenantOrgName, "main"))
                .body("{\n" +
                        "  \"channelId\": \""+getChannelID(tenantOrgName, integration)+"\",\n" +
                        "  \"enable\": "+ integrationStatus + "\n" +
                        "}")
                .put(Endpoints.INTEGRATIONS_ENABLING_DISABLING);
    }

    public static ResponseBody getInfoAboutFBIntegration(String tenantOrgName){
        return RestAssured.given()
                .header("Authorization", PortalAuthToken.getAccessTokenForPortalUser(tenantOrgName, "main"))
                .get(Endpoints.FACEBOOK_INTEGRATION)
                .getBody();
    }

    public static ResponseBody getInfoAboutTwitterIntegration(String tenantOrgName){
        return RestAssured.given()
                .header("Authorization", PortalAuthToken.getAccessTokenForPortalUser(tenantOrgName, "main"))
                .get(Endpoints.TWITTER_INTEGRATION)
                .getBody();
    }

    public static String getInfoAboutTwitterIntegration(String tenantOrgName,String parametr){
        return RestAssured.given()
                .header("Authorization", PortalAuthToken.getAccessTokenForPortalUser(tenantOrgName, "main"))
                .get(Endpoints.TWITTER_INTEGRATION)
                .getBody().jsonPath().getString(parametr);
    }

    public static ResponseBody delinkTwitterIntegration(String tenantOrgName){
        return RestAssured.given()
                .header("Authorization", PortalAuthToken.getAccessTokenForPortalUser(tenantOrgName, "main"))
                .delete(Endpoints.TWITTER_INTEGRATION)
                .getBody();
    }

    public static void delinkFBIntegration(String tenantOrgName){
        RestAssured.given()
                .header("Authorization", PortalAuthToken.getAccessTokenForPortalUser(tenantOrgName, "main"))
                .delete(Endpoints.FACEBOOK_INTEGRATION)
                .getBody();
    }

    public static void setStatusForWelcomeMesage(String tenantName, String messageStatus){
        RestAssured.given()
                .contentType(ContentType.JSON)
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
                .header("Authorization", PortalAuthToken.getAccessTokenForPortalUser(tenantOrgName, "main"))
                .get(Endpoints.INTEGRATIONS)
                .getBody().jsonPath().getList("", Integration.class)
                .stream()
                .filter(e -> e.getType().equalsIgnoreCase(integrationType))
                .findFirst().get();
    }

    public static List<ChatHistoryItem> getChatHistory(String tenantOrgName, String sessionId){
        return RestAssured.given()
                .header("Authorization", PortalAuthToken.getAccessTokenForPortalUser(tenantOrgName, "main"))
                .get(String.format(Endpoints.CHAT_HISTORY, sessionId))
                .getBody().jsonPath().getList("records", ChatHistoryItem.class);
    }

    public static Response updateSessionCapacity(String tenantOrgName, int availableChats){
        PortalAuthToken.clearAccessTokenForPortalUser();
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", PortalAuthToken.getAccessTokenForPortalUser(tenantOrgName, "main"))
                .put(Endpoints.SESSION_CAPACITY + availableChats);
    }

    public static void processTickets(List<OvernightTicket> tickets, String agentId){
        for(OvernightTicket ticket : tickets){
            String body = "{\n" +
                    "  \"chatId\": \"" + ticket.getId() + "\",\n" +
                    "  \"state\": \"PROCESSED\",\n" +
                    "  \"agentId\": \"" + agentId + "\"\n" +
                    "}";
            RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(body)
                    .post(Endpoints.INTERNAL_PROCESS_TICKET);
        }
    }

    public static void closeAllOvernightTickets(String tenantOrgName, String ordinalAgentNumber){
        try {
            String agentId = ApiHelper.getAgentInfo(tenantOrgName, ordinalAgentNumber).getBody().jsonPath().get("id");
            List<OvernightTicket> allAssignedTickets = getAssignedOvernightTickets(tenantOrgName, ordinalAgentNumber);
            List<OvernightTicket> allUnassignedTickets = getUnassignedOvernightTickets(tenantOrgName);

            List<OvernightTicket> fullList = new ArrayList<>();
            fullList.addAll(allAssignedTickets);
            fullList.addAll(allUnassignedTickets);
            if (fullList.size() > 0) processTickets(fullList, agentId);
        }catch(NullPointerException e){}
        catch (JsonPathException e){
            Assert.fail("Unable to close overnight tickets");
        }
    }

    public static List<OvernightTicket> getAssignedOvernightTickets(String tenantOrgName, String ordinalAgentNumber){
        List<OvernightTicket> tickets = new ArrayList<>();
        Response resp =  RestAssured.given().log().all()
                .header("Authorization", PortalAuthToken.getAccessTokenForPortalUser(tenantOrgName, ordinalAgentNumber))
                .get(Endpoints.AGENT_ASSIGNED_TICKETS);
        try {
            tickets = resp.getBody().jsonPath().getList("", OvernightTicket.class);
        }catch (ClassCastException|JsonPathException e){
            Assert.fail("Getting overnight tickets by URL "+
                    Endpoints.AGENT_ASSIGNED_TICKETS + " was not successful \n" +
                    "Resp body: " + resp.getBody().asString());
        }
        return tickets;
    }


    public static List<OvernightTicket> getUnassignedOvernightTickets(String tenantOrgName){
        String tenantID = ApiHelper.getTenantInfoMap(tenantOrgName).get("id");
        List<OvernightTicket> tickets = new ArrayList<>();
        Response resp =  RestAssured.given().log().all()
                .get(String.format(Endpoints.INTERNAL_GET_TICKETS,tenantID, 0));
        try{

            List<OvernightTicket> baseTickets = resp.jsonPath().getList("content", OvernightTicket.class);
            tickets.addAll(baseTickets);
            int pageSize = resp.jsonPath().getInt("pageable.pageSize");
            int unassignedTickets = resp.jsonPath().getInt("totalElements");
            int pagesNumber = unassignedTickets / pageSize;
            if(pagesNumber > 1){
                for (int i=1; i<pageSize; i++){
                    resp =  RestAssured.given().log().all()
                        .get(String.format(Endpoints.INTERNAL_GET_TICKETS, tenantID, i));
                    List<OvernightTicket> allUnassignedTickets = resp.jsonPath().getList("content", OvernightTicket.class);
                    tickets.addAll(allUnassignedTickets);
                }
            }
        } catch (ClassCastException|JsonPathException e){
            Assert.fail("Getting Unassighned overnight tickets by internal URL "+
                   " was not successful \n" +
                    "Resp body: " + resp.getBody().asString());
        }
        return tickets;
    }

    public static List<Department> getDepartments(String tenantOrgName){
        Response resp =  RestAssured.given().header("Authorization", PortalAuthToken.getAccessTokenForPortalUser(tenantOrgName, "")).get(Endpoints.DEPARTMENTS);
        List<Department> departments = resp.jsonPath().getList("", Department.class);
        return departments;
    }

    public static void deleteDepartmentsById(String tenantOrgName) {

        List<Department> departments = getDepartments(tenantOrgName);
        for (Department department : departments) {
            if (department.getName().contains("Auto")) {
                String departmentId = department.getId();
                 RestAssured.given()
                        .header("Authorization", PortalAuthToken.getAccessTokenForPortalUser(tenantOrgName, ""))
                        .delete(Endpoints.DEPARTMENTS + "/" + department.getId());
            }
        }
    }


    public static void createDepartment(String name, String description, String agent ){
        String agentId = getAgentInfo(Tenants.getTenantUnderTestOrgName(), agent).getBody().jsonPath().get("id");
        String tenantId = ApiHelper.getTenantInfoMap(Tenants.getTenantUnderTestOrgName()).get("id");
        Response resp;
        resp = RestAssured.given()
                .header("Authorization", PortalAuthToken.getAccessTokenForPortalUser(Tenants.getTenantUnderTestOrgName(), "main"))
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("{ " +
                        "\"tenantId\": \"" + tenantId + "\"," +
                        "\"name\": \"" + name + "\"," +
                        "\"description\": \"" + description + "\"," +
                        "\"agentIds\": [\"" + agentId +
                        "\"]" +
                        "}")
                .post(Endpoints.DEPARTMENTS);
        Assert.assertEquals(resp.statusCode(), 201,
                "Creating of department was not successful\n" +
                        "resp body: " + resp.getBody().asString());
    }

    public static Map getActiveSessionByClientId(String clientId){
        String tenantID = ApiHelper.getTenantInfoMap(Tenants.getTenantUnderTestOrgName()).get("id");
        String url = String.format(Endpoints.INTERNAL_CHAT_BY_CLIENT, tenantID, clientId);
        Response resp =  RestAssured.get(url);
        Map activeSession = null;
        try{
            activeSession = resp.getBody().jsonPath().getList("content.sessions[0]")
                    .stream()
                    .map(e -> (Map) e)
                    .filter(map -> map.get("state").equals("ACTIVE"))
                    .findFirst().get();
        }catch(JsonPathException e){
            Assert.fail("Failed to get session Id\n"+
                    "resp status: " + resp.statusCode() + "\n" +
            "resp body:" + resp.getBody().asString() + "\n");
        }
        return activeSession;
    }

    public static Customer360PersonalInfo getCustomer360PersonalInfo(String tenantOrgName, String clineId, String integrationType){
        JsonPath respJSON = getCustomerView(tenantOrgName, clineId);

        String fullName = "";
        if(respJSON.getString("personalDetails.firstName") == null &&
                respJSON.getString("personalDetails.lastName") == null){
            fullName = respJSON.getString("clientProfiles.clientId[0]");
        } else {
            String lastName =respJSON.getString("personalDetails.lastName") == null ? "" : respJSON.getString("personalDetails.lastName");
            fullName = respJSON.getString("personalDetails.firstName") + " " + lastName;
        }
        String location = respJSON.getString("personalDetails.location") == null ? "Unknown location" : respJSON.getString("personalDetails.location");
        if(location.isEmpty()) location = "Unknown location";
        String customerSince = getCustomerSince(respJSON);

        String channelUsername = "";
        try {
            channelUsername = respJSON.getString("personalDetails.channelUsername").isEmpty() ? "Unknown" : respJSON.getString("personalDetails.channelUsername");
        }catch (NullPointerException e){
            channelUsername = respJSON.getString("personalDetails.channelUsername") == null ? "Unknown" : respJSON.getString("personalDetails.channelUsername");
        }
        if(channelUsername.contains("_")) channelUsername = "@" + channelUsername;
        String phone =  (respJSON.getString("clientProfiles.attributes.phone[0]")==null || respJSON.getString("clientProfiles.attributes.phone[0]").isEmpty()) ? "Unknown" : respJSON.getString("clientProfiles.attributes.phone[0]");
        String email = (respJSON.getString("personalDetails.email")==null || respJSON.getString("personalDetails.email").isEmpty()) ? "Unknown" : respJSON.getString("personalDetails.email");

        return new Customer360PersonalInfo(fullName.trim(), location,
                "Customer since: " + customerSince, email,
                channelUsername, phone.replaceAll(" ", "") );
    }

    public static void updateClientProfileAttribute(String attributeName, String attribute, String clientId){
        Response resp = RestAssured.given().log().all()
                .header("Authorization", PortalAuthToken.getAccessTokenForPortalUser(Tenants.getTenantUnderTestOrgName(), "main"))
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("{" +
                        "  \"type\": \"HTTP\",\n" +
                        "  \"clientId\": \""+ clientId +"\",\n" +
                        "  \"attributes\": {\n" +
                        "  \""+ attributeName +"\": \""+ attribute+"\"" +
                        "  }"+
                        "}")
                .post(Endpoints.CLIENT_PROFILE_ATTRIBUTES);
        if(!(resp.statusCode()==200)) {
            Assert.fail("Attribute with " + attributeName + " was not updated with " + attribute + " status code = " + resp.statusCode()+
                    "\n Body: " + resp.getBody().asString());
        }
    }

    public static JsonPath getCustomerView(String tenantOrgName, String clineId){
        String sessionId = (String) getActiveSessionByClientId(clineId).get("sessionId");
        return RestAssured.given()
                .header("Authorization", PortalAuthToken.getAccessTokenForPortalUser(tenantOrgName, "main"))
                .get(Endpoints.CUSTOMER_VIEW + sessionId)
                .getBody().jsonPath();

    }

    public static String getCustomerSince(JsonPath respJSON){
        String customerSinceFullDate  = respJSON.getString("personalDetails.customerSince");
        ZoneId zoneId =  TimeZone.getDefault().toZoneId();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        return LocalDateTime.parse(customerSinceFullDate, formatter).atZone(ZoneId.of("UTC"))
                .withZoneSameInstant(zoneId).toLocalDateTime()
                .format(DateTimeFormatter.ofPattern("d MMM yyyy"));
    }

    public static void deleteAgentPhotoForMainAQAAgent(String tenantOrgName){
        String agentId = getAgentInfo(tenantOrgName, "main").getBody().jsonPath().get("id");
        RestAssured.given()
                .header("Authorization", PortalAuthToken.getAccessTokenForPortalUser(tenantOrgName, "main"))
                .delete(String.format(Endpoints.DELETE_AGENT_IMAGE, agentId));
    }

    public static void deleteTenantBrandImage(String tenantOrgName){
        String tenantID  = ApiHelper.getTenantInfoMap(tenantOrgName).get("id");
        RestAssured.given()
                .header("Authorization", PortalAuthToken.getAccessTokenForPortalUser(tenantOrgName, "main"))
                .delete(String.format(Endpoints.TENANT_BRAND_LOGO, tenantID));

        RestAssured.given()
                .header("Authorization", PortalAuthToken.getAccessTokenForPortalUser(tenantOrgName, "main"))
                .delete(String.format(Endpoints.TENANT_BRAND_LOGO_TRANS, tenantID));
    }

    public static Response getTenantBrandImage(String tenantOrgName){
        String tenantID  = ApiHelper.getTenantInfoMap(tenantOrgName).get("id");
        return RestAssured.given()
                .header("Authorization", PortalAuthToken.getAccessTokenForPortalUser(tenantOrgName, "main"))
                .get(String.format(Endpoints.TENANT_BRAND_LOGO, tenantID));
    }

    public static Response getTenantBrandImageTrans(String tenantOrgName){
        String tenantID  = ApiHelper.getTenantInfoMap(tenantOrgName).get("id");
        return RestAssured.given()
                .header("Authorization", PortalAuthToken.getAccessTokenForPortalUser(tenantOrgName, "main"))
                .get(String.format(Endpoints.TENANT_BRAND_LOGO_TRANS, tenantID));
    }

    public static Response getSessionDetails(String clientID){
        return RestAssured.get(String.format(Endpoints.INTERNAL_SESSION_DETAILS, Tenants.getTenantUnderTestName(), clientID));
    }

    public static Response getFinishedChatsByLoggedInAgentAgent(String tenantOrgName, int page, int size){
        String agentId = getAgentInfo(tenantOrgName, "main").getBody().jsonPath().get("id");
        String url = String.format(Endpoints.INTERNAL_GET_CHATS_FINISHED_BY_AGENT, agentId, page, size);
        return RestAssured.get(url);
    }

    public static Response getActiveChatsByAgent(String agent){
        return RestAssured.given()
                .header("Authorization",
                        PortalAuthToken.getAccessTokenForPortalUser(Tenants.getTenantUnderTestOrgName(), agent))
                .get(String.format(Endpoints.ACTIVE_CHATS_BY_AGENT, ConfigManager.getEnv()));
    }

    public static void closeActiveChats(String agent){
        List<String> conversationIds = getActiveChatsByAgent(agent).getBody().jsonPath().getList("content.id");
        for(String conversationId : conversationIds){
            RestAssured.given()
                    .accept(ContentType.JSON)
                    .header("Authorization",
                            PortalAuthToken.getAccessTokenForPortalUser(Tenants.getTenantUnderTestOrgName(), agent))
                    .put(String.format(Endpoints.CLOSE_ACTIVE_CHAT, ConfigManager.getEnv(), conversationId));
        }
    }

    public static String getClientProfileId(String clientID){
        return getSessionDetails(clientID).getBody().jsonPath().getString("data.clientProfileId[0]");
    }

    public static List<CRMTicket> getCRMTickets(String clientID, String type){
        String clientProfileId = DBConnector.getClientProfileID(ConfigManager.getEnv(), clientID, type, 0);
        return RestAssured.given()
                .header("Authorization", PortalAuthToken.getAccessTokenForPortalUser(Tenants.getTenantUnderTestOrgName(), "main"))
                .get(String.format(Endpoints.CRM_TICKET, clientProfileId))
                .getBody().jsonPath().getList("", CRMTicket.class);
    }

    public static List<String> getTagsForCRMTicket(String sessionID){
        return RestAssured.given()
                .get(String.format(Endpoints.INTERNAL_TAGS_FROM_CRM_TICKET, sessionID)).getBody().jsonPath().getList("");
    }

    public static List<String> getAllTags(){
        return RestAssured.given()
                .header("Authorization", PortalAuthToken.getAccessTokenForPortalUser(Tenants.getTenantUnderTestOrgName(), "main"))
                .get(Endpoints.TAGS_FOR_CRM_TICKET).getBody().jsonPath().getList("");
    }




    public static Response createCRMTicket(String clientID, Map<String, String> ticketInfo){
        return RestAssured.given().log().all()
                .header("Authorization", PortalAuthToken.getAccessTokenForPortalUser(Tenants.getTenantUnderTestOrgName(), "main"))
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("{" +
                        "  \"conversationId\": \""+ ticketInfo.get("conversationId") +"\",\n" +
                        "  \"sessionId\": \""+ ticketInfo.get("sessionId") +"\",\n" +
                        "  \"link\": \"" + ticketInfo.get("link") + "\",\n" +
                        "  \"ticketNumber\": \""+ ticketInfo.get("ticketNumber") +"\",\n" +
                        "  \"agentNote\": \"" + ticketInfo.get("agentNote") + "\"\n" +
                        "}")
                .post(String.format(Endpoints.CRM_TICKET, ticketInfo.get("clientProfileId")));
    }

    public static void deleteCRMTicket(String crmTicketId){
        RestAssured.given().log().all()
                .header("Authorization", PortalAuthToken.getAccessTokenForPortalUser(Tenants.getTenantUnderTestOrgName(), "main"))
                .accept(ContentType.JSON)
                .delete(Endpoints.DELETE_CRM_TICKET + crmTicketId);
    }


    public static Response updateTenantConfig(String tenantOrgName, String config, String configValue){
        String tenantId = ApiHelper.getTenantInfoMap(tenantOrgName).get("id");
        return RestAssured.given().log().all()
                .header("Authorization", PortalAuthToken.getAccessTokenForPortalUser(Tenants.getTenantUnderTestOrgName(), "main"))
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body("{ \"configAttributes\": " +
                        "{\""+config+"\": "+configValue+" }" +
                      "}")
                .put(Endpoints.INTERNAL_CONFIG_ATTRIBUTES + tenantId);
    }

//    Response resp = ApiHelper.createFBChat(FacebookPages.getFBPageFromCurrentEnvByTenantOrgName(tenantOrgName).getFBPageId(), 1912835872122481l, "to agent the last");
    public static Response createFBChat(long linkedFBPageId, long fbUserId, String message){
        Faker faker = new Faker();
        String mid = faker.code().isbn13(true) + "-" + faker.lorem().characters(3,6, true);
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = LocalDateTime.now().atZone(zoneId);
        long timestamp =  zdt.toInstant().toEpochMilli();
        String requestBody1 = "{\n" +
                "  \"entry\": [\n" +
                "    {\n" +
                "      \"changes\": [],\n" +
                "      \"id\": \""+linkedFBPageId+"\",\n" +
                "      \"messaging\": [\n" +
                "        {\n" +
                "          \"message\": {\n" +
                "            \"mid\": \""+mid+"\",\n" +
                "            \"seq\": 31478,\n" +
                "            \"text\": \""+message+"\"\n" +
                "          },\n" +
                "          \"recipient\": {\n" +
                "            \"id\": \""+linkedFBPageId+"\"\n" +
                "          },\n" +
                "          \"sender\": {\n" +
                "            \"id\": \""+fbUserId+"\"\n" +
                "          },\n" +
                "          \"timestamp\": "+timestamp+"\n" +
                "        }\n" +
                "      ],\n" +
                "      \"time\": "+timestamp+"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"object\": \"page\",\n" +
                "  \"tenant\": null\n" +
                "}";
        return RestAssured.given().log().all()
                .accept(ContentType.ANY)
                .contentType(ContentType.JSON)
                .config(RestAssured.config().encoderConfig(EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false)))
                .body(requestBody1)
                .post(Endpoints.SOCIAL_FACEBOOK_HOOKS);
        }


    public static List<AvailableAgent> getAvailableAgents(){
        return RestAssured.given().log().all()
                .header("Authorization", PortalAuthToken.getAccessTokenForPortalUser(Tenants.getTenantUnderTestOrgName(), "main"))
                .get(Endpoints.TENANT_AVAILABLE_AGENTS)
                .getBody().jsonPath().getList("agents", AvailableAgent.class);
    }

    public static ClientProfile getClientAttributes(String clientProfileID){
        return RestAssured.given().log().all()
                .header("Authorization", PortalAuthToken.getAccessTokenForPortalUser(Tenants.getTenantUnderTestOrgName(), "main"))
                .get(String.format(Endpoints.INTERNAL_CLIENT_PROFILE_ATTRIBUTES_ENDPOINT, clientProfileID))
                .getBody().as(ClientProfile.class);
    }

    public static Response createChatHistory(ChatHistory history){
        return RestAssured.given().log().all()
                .accept(ContentType.ANY)
                .contentType(ContentType.JSON)
                .body(history)
                .post(Endpoints.INTERNAL_CREATE_HISTORY);
    }
}
