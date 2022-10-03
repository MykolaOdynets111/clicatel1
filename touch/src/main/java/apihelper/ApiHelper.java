package apihelper;

import com.github.javafaker.Faker;
import datamanager.*;
import datamanager.jacksonschemas.*;
import datamanager.jacksonschemas.chathistory.ChatHistory;
import datamanager.jacksonschemas.chatusers.UserInfo;
import datamanager.jacksonschemas.departments.Department;
import datamanager.jacksonschemas.tenantaddress.TenantAddress;
import datamanager.jacksonschemas.ChatPreferenceSettings;
import datamanager.jacksonschemas.usersessioninfo.ClientProfile;
import datamanager.jacksonschemas.usersessioninfo.UserSession;
import drivermanager.ConfigManager;
import interfaces.VerificationHelper;
import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.path.json.exception.JsonPathException;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import mc2api.auth.PortalAuthToken;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.util.StringUtils;
import org.testng.Assert;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static io.restassured.RestAssured.given;
import static java.lang.String.format;
import static org.junit.Assert.fail;

public class ApiHelper implements VerificationHelper {

    private static List<HashMap> tenantsInfo = null;
    public static ThreadLocal<String> clientProfileId = new ThreadLocal<>();

    public static ThreadLocal<String> featureName = new ThreadLocal<>();

    public static String getInternalTenantConfig(String tenantName, String config) {
        String url = format(Endpoints.CHAT_PREFERENCES, tenantName);
        return RestAssured.get(url).jsonPath().get(config).toString();
    }

    public static Response getTenantConfig(String tenantOrgName) {
        String tenantId = getTenant(tenantOrgName).get("id");
        return RestAssured.given()
                .header("Authorization", getAccessToken(tenantOrgName, "main"))
                .log().all()
                .get(format(Endpoints.CHAT_PREFERENCES, tenantId));
    }

    public static Map<String, String> getAllTenantsInfoMap(String theValue) {
        Map<String, String> tenantsMap = new HashMap<>();
        List<HashMap> tenants = getAllTenantsInfo();
        tenants.forEach(e -> tenantsMap.put(((String) e.get("tenantOrgName")).toLowerCase(),
                (String) e.get(theValue)));
        return tenantsMap;
    }

    public static Map<String, String> getTenantInfoMap(String tenantOrgName) {
        Tenants.setTenantUnderTestNames(tenantOrgName);
        Response resp = RestAssured.given().log().all()
                .get(Endpoints.INTERNAL_TENANTS);
        List<Map> tenants = new ArrayList<>();
        Assert.assertEquals(resp.statusCode(), 200,
                "Get Tenant Info Map was not successful\n" +
                        "resp body: " + resp.getBody().asString());
        try {
            tenants = resp.getBody().jsonPath().getList("");
        } catch (JsonPathException e) {
            Assert.fail("Failed to get tenant info\n" +
                    "URL: " + Endpoints.INTERNAL_TENANTS + "\n" +
                    "resp status code:" + resp.statusCode() + "\n" +
                    "resp body: " + resp.getBody().asString());
        }
        return tenants.stream().filter(e -> e.get("orgName").equals(tenantOrgName)).findFirst()
                .orElseThrow(() -> new AssertionError(
                        "No Tenants with " + tenantOrgName + " tenantOrgName, env: " + ConfigManager.getEnv()));
    }

    public static Response getTenantInfo(String tenantOrgName) {
        Response resp = RestAssured.given()
                .header("Authorization", getAccessToken(tenantOrgName, "main"))
                .get(Endpoints.TENANT_INFO);
        Assert.assertEquals(resp.statusCode(), 200, "Failed to get tenant info\n" +
                "URL: " + Endpoints.TENANT_INFO + "\n" +
                "resp status code:" + resp.statusCode() + "\n" +
                "resp body: " + resp.getBody().asString());
        return resp;
    }

    public static Response createUserProfile(String clientID) {
        Response resp;
        String tenantId = getTenant(Tenants.getTenantUnderTestOrgName()).get("id");

        resp = RestAssured.given().log().all()
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
        clientProfileId.set(resp.jsonPath().getString("id"));
        return resp;
    }

    public static void createUserProfileWithPhone(String clientID, String phoneNumber) {
        Response resp;
        String tenantId = getTenant(Tenants.getTenantUnderTestOrgName()).get("id");

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
        if (!clientID.isEmpty()) {
            String url = format(Endpoints.INTERNAL_DELETE_USER_PROFILE_ENDPOINT, tenantName, clientID);
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
                    "Body " + resp.getBody().asString() + "\n");
        }
        return tenantsInfo;
    }

    public static String getAutoResponderMessageText(String title) {
        return getAutoResponderMessage(title).getText().trim();
    }

    public static AutoResponderMessage getAutoResponderMessage(String title) {
        return getAutoRespondersList().stream()
                .filter(e -> e.getTitle().equalsIgnoreCase(title))
                .findFirst().orElseThrow(() ->
                        new NoSuchElementException(format("There is no message with title: %s",  title)));
    }

    public static List<AutoResponderMessage> getAutoRespondersList() {
        String tenantOrgName = Tenants.getTenantUnderTestOrgName();
        String endpoint = Endpoints.INTERNAL_AUTORESPONDER_CONTROLLER;

        return Objects.requireNonNull(getQueryFor(tenantOrgName, endpoint))
                .jsonPath().getList("", AutoResponderMessage.class);
    }

    public static void updateAutoresponderMessage(AutoResponderMessage tafMessage, String autoResponderId) {
        String url = format(Endpoints.AUTORESPONDER_CONTROLLER, tafMessage.getId());
        Response resp = RestAssured.given().log().all()
                .header("Authorization", getAccessToken(Tenants.getTenantUnderTestOrgName(), "main"))
                .contentType(ContentType.JSON)
                .param("autoresponderId", tafMessage.getId())
                .param("enabled", tafMessage.getEnabled())
                .param("text", tafMessage.getText())
                .put(url);
        if (!(resp.getStatusCode() == 200)) {
            Assert.fail("Update Taf failed \n"
                    + "Status code: " + resp.statusCode() + "\n"
                    + "Error message: " + resp.getBody().asString());
        }
    }

    public static Response setAgentSupportDaysAndHours(String tenantOrgName, String day,
                                                       String startTime, String endTime) {
        String body = createPutBodyForHours(day, startTime, endTime);
        return postQuery(tenantOrgName, body, Endpoints.AGENT_SUPPORT_HOURS);
    }

    public static SupportHoursItem setAgentSupportDaysAndHours(String tenantOrgName, List<String> days,
                                                               String startTime, String endTime) {
        String body = getAgentSupportHoursBody(days, startTime, endTime);

        return Objects.requireNonNull(getPostQueryFor(tenantOrgName, Endpoints.AGENT_SUPPORT_HOURS, body))
                .as(SupportHoursItem.class);
    }

    public static SupportHoursItem setAgentSupportDaysAndHours(String tenantOrgName, List<String> days) {
        return setAgentSupportDaysAndHours(tenantOrgName, days, "00:00", "23:59");
    }

    private static SupportHoursItem getAgentSupportDaysAndHours(String tenantOrgName, String agent) {
        Response response = getQuery(tenantOrgName, Endpoints.AGENT_SUPPORT_HOURS, agent);

        if (response.getStatusCode() != 200) {
            fail("Couldn't get 'Agent Support Hours' \n"
                    + "Status code: " + response.statusCode() + "\n"
                    + "TenantOrgName: " + tenantOrgName + "\n"
                    + "Error message: " + response.getBody().asString());
            return null;
        } else {
            return response.getBody().as(SupportHoursItem.class);
        }
    }

    public static SupportHoursItem getAgentSupportDaysAndHoursForMainAgent(String tenantOrgName) {
        return getAgentSupportDaysAndHours(tenantOrgName, "main");
    }

    private static String createPutBodyForHours(String day, String startTime, String endTime) {
        String body;
        if (day.equalsIgnoreCase("all week")) {
            body = "{\n" +
                    "  \"agentSupportHours\": [\n" +
                    "    {\n" +
                    "      \"startWorkTime\": \"00:00\",\n" +
                    "      \"endWorkTime\": \"23:59\",\n" +
                    "      \"days\": [\n" +
                    "        \"FRIDAY\",\n" +
                    "        \"MONDAY\",\n" +
                    "        \"SATURDAY\",\n" +
                    "        \"SUNDAY\",\n" +
                    "        \"THURSDAY\",\n" +
                    "        \"TUESDAY\",\n" +
                    "        \"WEDNESDAY\"\n" +
                    "      ]\n" +
                    "    }\n" +
                    "  ],\n" +
                    "  \"supportHoursByDepartment\": []\n" +
                    "}";
        } else {
            body = getAgentSupportHoursBody(Collections.singletonList(day), startTime, endTime);
        }
        return body;
    }

    @NotNull
    private static String getAgentSupportHoursBody(List<String> days, String startTime, String endTime) {
        String body;
        String sequenceOfDays = StringUtils.collectionToDelimitedString(days, ",", "\"", "\"");
        body = "{\n" +
                "  \"agentSupportHours\": [\n" +
                "    {\n" +
                "      \"startWorkTime\": \"" + startTime + "\",\n" +
                "      \"endWorkTime\": \"" + endTime + "\",\n" +
                "      \"days\": [\n" + sequenceOfDays +
                "      ]\n" +
                "    }\n" +
                "  ],\n" +
                "  \"supportHoursByDepartment\": []\n" +
                "}";
        return body;
    }

    public static void setAvailableForAllTerritories(String tenantOrgName) {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", getAccessToken(tenantOrgName, "main"))
                .body("{\n" +
                        "  \"availability\": \"AVAILABLE\"\n" +
                        "\n" +
                        "}")
                .post(Endpoints.WIDGET_VISIBILITY_TERRITORIES);
    }


    public static void setAvailabilityForTerritoryAndCountry(String tenantOrgName, String terrName, boolean terrAvailability,
                                                             String countryName, boolean countryAvailability) {
        Territory targetTerr = Territories.getTargetTerr(tenantOrgName, terrName);
        String territoryID = targetTerr.getTerritoryId();
        Country targetCountry = targetTerr.getCountry().stream().filter(e -> e.getName().equalsIgnoreCase(countryName))
                .findFirst().get();
        String countryID = targetCountry.getCountryId();
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", getAccessToken(tenantOrgName, "main"))
                .body("{\n" +
                        "  \"availability\": \"LIMITED\",\n" +
                        "  \"territory\": [\n" +
                        "    {\n" +
                        "      \"territoryId\": \"" + territoryID + "\",\n" +
                        "      \"available\": " + terrAvailability + ",\n" +
                        "      \"country\": [\n" +
                        "        {\n" +
                        "          \"countryId\": \"" + countryID + "\",\n" +
                        "          \"available\": " + countryAvailability + "\n" +
                        "        }\n" +
                        "      ]\n" +
                        "    }" +
                        "]" +
                        "}")
                .post(Endpoints.WIDGET_VISIBILITY_TERRITORIES);
    }

    public static void setAvailabilityForTerritory(String tenantOrgName, String terrName, boolean terrAvailability) {
        Territory targetTerr = Territories.getTargetTerr(tenantOrgName, terrName);
        String territoryID = targetTerr.getTerritoryId();
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", getAccessToken(tenantOrgName, "main"))
                .body("{\n" +
                        "  \"availability\": \"LIMITED\",\n" +
                        "  \"territory\": [\n" +
                        "    {\n" +
                        "      \"territoryId\": \"" + territoryID + "\",\n" +
                        "      \"available\": " + terrAvailability + "\n" +
                        "    }" +
                        "]" +
                        "}")
                .post(Endpoints.WIDGET_VISIBILITY_TERRITORIES);
    }


    public static List<TenantAddress> getTenantAddressInfo(String tenantName) {
        return RestAssured.given()
                .accept(ContentType.JSON)
                .get(format(Endpoints.INTERNAL_TENANT_ADDRESS, tenantName))
                .jsonPath().getList("addresses", TenantAddress.class);
    }


    public static UserSession getLastUserSession(String userID, String tenant) {
        return RestAssured.given()
                .accept(ContentType.JSON)
                .get(format(Endpoints.INTERNAL_LAST_CLIENT_SESSION, tenant, userID))
                .getBody().as(UserSession.class);

    }

    public static void updateFeatureStatus(String tenantOrgName, ChatPreferenceSettings chatPreferenceSettings) {
        String url = Endpoints.CHAT_PREFERENCES;

        Response resp = RestAssured.given().log().all().header("Authorization", getAccessToken(Tenants.getTenantUnderTestOrgName(), "main"))
                .accept(ContentType.ANY)
                .contentType(ContentType.JSON)
                .body(chatPreferenceSettings)
                .put(url);
        Assert.assertEquals(resp.statusCode(), 200,
                "Status code is not 200 and body value is \n: " + chatPreferenceSettings.toString() + "\n error message is: " + resp.getBody().asString());
    }

    public static boolean getFeatureStatus(String tenantOrgName, String FEATURE) {
        Response resp = RestAssured.given().log().all()
                .header("Authorization", getAccessToken(tenantOrgName, "main"))
                .get(format(Endpoints.FEATURE, Tenants.getTenantId()));
        boolean featureStatus = false;
        try {
            featureStatus = resp.getBody().jsonPath().getBoolean(FEATURE);
        } catch (NullPointerException e) {
            Assert.fail("Failed to get feature status from GET " + Endpoints.FEATURE + " response\n" +
                    "statusCode" + resp.statusCode() + "\n" +
                    "respBody" + resp.getBody().asString());
        }
        return featureStatus;
    }

    public static int getNumberOfLoggedInAgents() {
        String url = format(Endpoints.INTERNAL_COUNT_OF_LOGGED_IN_AGENTS, Tenants.getTenantUnderTestName());
        return RestAssured.get(url).getBody().jsonPath().get("loggedInAgentsCount");
    }

    public static Map<String, String> getAgentInfo(String tenantOrgName, String agent) {
        Agents user = Agents.getAgentFromCurrentEnvByTenantOrgName(tenantOrgName.toLowerCase(), agent);
        Response resp = RestAssured.given()
                .contentType(ContentType.JSON)
                .get(format(Endpoints.AGENT_INFO_ME, Tenants.getTenantId()));
        Assert.assertEquals(resp.getStatusCode(), 200, format("Agent is not return status code is : %s and Body is: %s", resp.getStatusCode(), resp.getBody().asString()));
        List<Map> agents = resp.getBody().jsonPath().getList("");

        Map<String, String> agentInfoMap = agents.stream().filter(e -> e.get("email").equals(user.getAgentEmail())).findFirst()
                .orElseThrow(() -> new AssertionError(
                        "No Agents with " + user.getAgentEmail() + " email, env: " + ConfigManager.getEnv()));
        return agentInfoMap;
    }

    public static List<Map> getAgentsInfo(String tenantOrgName) {
        Response resp = RestAssured.given()
                .contentType(ContentType.JSON)
                .get(format(Endpoints.AGENT_INFO_ME, Tenants.getTenantId()));
        Assert.assertEquals(resp.getStatusCode(), 200, format("Agent is not return status code is : %s and Body is: %s", resp.getStatusCode(), resp.getBody().asString()));
        List<Map> agents = resp.getBody().jsonPath().getList("");
        return agents;
    }


    public static String getAgentId(String tenantOrgName, String agent) {
        return getAgentInfo(tenantOrgName, agent).get("id");
    }

    public static String getJWTToken(String tenantOrgName, String agent) {
        Response resp = RestAssured.given().log().all()
                .param("agentId", getAgentId(tenantOrgName, agent))
                .param("tenantId", Tenants.getTenantId())
                .get(format(Endpoints.TOUCH_AUTH, Tenants.getTenantId()));
        return resp.getBody().jsonPath().get("jwt");
    }

    public static void logoutTheAgent(String tenantOrgName) {
        String tenantID = getTenant(tenantOrgName).get("id");
        RestAssured.given().accept(ContentType.JSON)
                .get(format(Endpoints.INTERNAL_LOGOUT_AGENT, tenantID));
    }

    public static void decreaseTouchGoPLan(String tenantOrgName) {
        MC2Account targetAccount = MC2Account.getAccountByOrgName(ConfigManager.getEnv(), tenantOrgName);
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "  \"accountId\": \"" + targetAccount.getAccountID() + "\",\n" +
                        "  \"messageType\": \"TOUCH_STARTED\"" +
                        "}")
                .put(Endpoints.INTERNAL_DECREASING_TOUCHGO_PLAN);
    }

    public static SurveyManagement getSurveyManagementAttributes(String channelId) {
        Response resp = RestAssured.given().log().all()
                .header("Authorization", getAccessToken(Tenants.getTenantUnderTestOrgName(), "main"))
                .get(format(Endpoints.SURVEY_MANAGEMENT, channelId));
        if (!(resp.statusCode() == 200)) {
            Assert.fail("Failed to get survey configuration ifo, status code = " + resp.statusCode() +
                    "\n Body: " + resp.getBody().asString());
        }
        return resp.getBody().jsonPath().getObject("chatSurveyConfig", SurveyManagement.class);

    }


    public static void ratingEnabling(String tenantOrgName, Boolean ratingEnabled, String chanell) {
        String channelID = getChannelID(tenantOrgName, chanell);
        SurveyManagement currentConfiguration = getSurveyManagementAttributes(channelID);
        if (!currentConfiguration.getRatingEnabled().equals(ratingEnabled)) {
            currentConfiguration.setRatingEnabled(ratingEnabled);
            updateSurveyManagement(tenantOrgName, currentConfiguration, channelID, chanell);
        }
    }

    public static void updateSurveyManagement(String tenantOrgName, SurveyManagement configuration, String channelID, String channelName) {
        Response resp = RestAssured.given().log().all().header("Authorization", getAccessToken(Tenants.getTenantUnderTestOrgName(), "main"))
                .accept(ContentType.ANY)
                .contentType(ContentType.JSON).body(configuration)
                .put(format(Endpoints.UPDATE_SURVEY_MANAGEMENT, channelName, channelID));
        if (!(resp.statusCode() == 200)) {
            Assert.fail("Failed to update survey, status code = " + resp.statusCode() +
                    "\n Body: " + resp.getBody().asString());
        }
    }

    public static String getChannelID(String tenantOrgName, String integrationChanel) {
        List<IntegrationChannel> existedChannels = RestAssured.given()
                .header("Authorization", getAccessToken(tenantOrgName, "main"))
                .get(Endpoints.INTEGRATION_EXISTING_CHANNELS)
                .getBody().jsonPath().getList("", IntegrationChannel.class);
        return existedChannels.stream().filter(e -> e.getChannelType().equalsIgnoreCase(integrationChanel))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Integration channel '" + integrationChanel + "' is absent for " + tenantOrgName + " tenant"))
                .getId();
    }

    public static UserInfo getUserProfile(String integrationChanel, String clientId) {
        String tenantOrgName = Tenants.getTenantUnderTestOrgName();
        String chanelId = getChannelID(tenantOrgName, integrationChanel);
        String tenantID = getTenant(tenantOrgName).get("id");
        format(Endpoints.INTERNAL_CHAT_USER_BY_ID, tenantID, clientId, chanelId);
        return RestAssured.given().log().all()
                .header("Authorization", getAccessToken(tenantOrgName, "main"))
                .get(format(Endpoints.INTERNAL_CHAT_USER_BY_ID, tenantID, clientId, chanelId))
                .getBody().as(UserInfo.class);
    }

    public static void updateUserProfile(UserInfo userInfo) {
        Response resp = RestAssured.given().log().all().header("Authorization", getAccessToken(Tenants.getTenantUnderTestOrgName(), "main"))
                .accept(ContentType.ANY)
                .contentType(ContentType.JSON).body(userInfo)
                .put(Endpoints.INTERNAL_CHAT_USERS);
        if (!(resp.statusCode() == 200)) {
            Assert.fail("Failed to update internal chat user info, status code = " + resp.statusCode() +
                    "\n Body: " + resp.getBody().asString());
        }
    }

    public static void setIntegrationStatus(String tenantOrgName, String integration, boolean integrationStatus) {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", getAccessToken(tenantOrgName, "main"))
                .body("{\n" +
                        "  \"channelId\": \"" + getChannelID(tenantOrgName, integration) + "\",\n" +
                        "  \"enable\": " + integrationStatus + "\n" +
                        "}")
                .put(Endpoints.INTEGRATIONS_ENABLING_DISABLING);
    }

    public static ResponseBody getInfoAboutFBIntegration(String tenantOrgName) {
        return RestAssured.given()
                .header("Authorization", getAccessToken(tenantOrgName, "main"))
                .get(Endpoints.FACEBOOK_INTEGRATION)
                .getBody();
    }

    public static ResponseBody getInfoAboutTwitterIntegration(String tenantOrgName) {
        return RestAssured.given()
                .header("Authorization", getAccessToken(tenantOrgName, "main"))
                .get(Endpoints.TWITTER_INTEGRATION)
                .getBody();
    }

    public static String getInfoAboutTwitterIntegration(String tenantOrgName, String parametr) {
        return RestAssured.given()
                .header("Authorization", getAccessToken(tenantOrgName, "main"))
                .get(Endpoints.TWITTER_INTEGRATION)
                .getBody().jsonPath().getString(parametr);
    }

    public static ResponseBody delinkTwitterIntegration(String tenantOrgName) {
        return RestAssured.given()
                .header("Authorization", getAccessToken(tenantOrgName, "main"))
                .delete(Endpoints.TWITTER_INTEGRATION)
                .getBody();
    }

    public static void delinkFBIntegration(String tenantOrgName) {
        RestAssured.given()
                .header("Authorization", getAccessToken(tenantOrgName, "main"))
                .delete(Endpoints.FACEBOOK_INTEGRATION)
                .getBody();
    }

    public static void setStatusForWelcomeMesage(String tenantName, String messageStatus) {
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

    public static Integration getIntegration(String tenantOrgName, String integrationType) {
        return RestAssured.given()
                .header("Authorization", getAccessToken(tenantOrgName, "main"))
                .get(Endpoints.INTEGRATIONS)
                .getBody().jsonPath().getList("", Integration.class)
                .stream()
                .filter(e -> e.getType().equalsIgnoreCase(integrationType))
                .findFirst().get();
    }

    public static List<ChatHistoryItem> getChatHistory(String tenantOrgName, String sessionId) {
        return RestAssured.given()
                .header("Authorization", getAccessToken(tenantOrgName, "main"))
                .get(format(Endpoints.CHAT_HISTORY, sessionId))
                .getBody().jsonPath().getList("records", ChatHistoryItem.class);
    }

    public static Response updateSessionCapacity(String tenantOrgName, int availableChats) {
        PortalAuthToken.clearAccessTokenForPortalUser();
        return RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", getAccessToken(tenantOrgName, "main"))
                .put(Endpoints.SESSION_CAPACITY + availableChats);
    }

    public static void processTickets(List<OvernightTicket> tickets, String agentId) {
        for (OvernightTicket ticket : tickets) {
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

    public static void closeAllOvernightTickets(String tenantOrgName, String ordinalAgentNumber) {
        try {
            String agentId = ApiHelper.getAgentInfo(tenantOrgName, ordinalAgentNumber).get("id");
            List<OvernightTicket> allAssignedTickets = getAssignedOvernightTickets(tenantOrgName, ordinalAgentNumber);
            List<OvernightTicket> allUnassignedTickets = getUnassignedOvernightTickets(tenantOrgName);

            List<OvernightTicket> fullList = new ArrayList<>();
            fullList.addAll(allAssignedTickets);
            fullList.addAll(allUnassignedTickets);
            if (fullList.size() > 0) processTickets(fullList, agentId);
        } catch (NullPointerException e) {
        } catch (JsonPathException e) {
            Assert.fail("Unable to close overnight tickets");
        }
    }

    public static List<OvernightTicket> getAssignedOvernightTickets(String tenantOrgName, String ordinalAgentNumber) {
        List<OvernightTicket> tickets = new ArrayList<>();
        Response resp = RestAssured.given().log().all()
                .header("Authorization", getAccessToken(tenantOrgName, ordinalAgentNumber))
                .get(Endpoints.AGENT_ASSIGNED_TICKETS);
        try {
            tickets = resp.getBody().jsonPath().getList("", OvernightTicket.class);
        } catch (ClassCastException | JsonPathException e) {
            Assert.fail("Getting overnight tickets by URL " +
                    Endpoints.AGENT_ASSIGNED_TICKETS + " was not successful \n" +
                    "Resp body: " + resp.getBody().asString());
        }
        return tickets;
    }


    public static List<OvernightTicket> getUnassignedOvernightTickets(String tenantOrgName) {
        String tenantID = getTenant(tenantOrgName).get("id");
        List<OvernightTicket> tickets = new ArrayList<>();
        Response resp = RestAssured.given().log().all()
                .get(format(Endpoints.INTERNAL_GET_TICKETS, tenantID, 0));
        try {

            List<OvernightTicket> baseTickets = resp.jsonPath().getList("content", OvernightTicket.class);
            tickets.addAll(baseTickets);
            int pageSize = resp.jsonPath().getInt("pageable.pageSize");
            int unassignedTickets = resp.jsonPath().getInt("totalElements");
            int pagesNumber = unassignedTickets / pageSize;
            if (pagesNumber > 1) {
                for (int i = 1; i < pageSize; i++) {
                    resp = RestAssured.given().log().all()
                            .get(format(Endpoints.INTERNAL_GET_TICKETS, tenantID, i));
                    List<OvernightTicket> allUnassignedTickets = resp.jsonPath().getList("content", OvernightTicket.class);
                    tickets.addAll(allUnassignedTickets);
                }
            }
        } catch (ClassCastException | JsonPathException e) {
            Assert.fail("Getting Unassighned overnight tickets by internal URL " +
                    " was not successful \n" +
                    "Resp body: " + resp.getBody().asString());
        }
        return tickets;
    }

    public static List<Department> getDepartments(String tenantOrgName) {
        Response resp = RestAssured.given().header("Authorization", getAccessToken(tenantOrgName, "main")).get(Endpoints.DEPARTMENTS);
        List<Department> departments = resp.jsonPath().getList("", Department.class);
        return departments;
    }

    public static void deleteDepartmentsById(String tenantOrgName) {
        List<Department> departments = getDepartments(tenantOrgName);
        for (Department department : departments) {
            if (department.getName().contains("Auto")) {
                RestAssured.given()
                        .header("Authorization", getAccessToken(tenantOrgName, ""))
                        .delete(Endpoints.DEPARTMENTS + "/" + department.getId());
            }
        }
    }


    public static void createDepartment(String name, String description, String agent) {
        String agentId = getAgentInfo(Tenants.getTenantUnderTestOrgName(), agent).get("id");
        Response resp;
        resp = RestAssured.given().log().all()
                .header("Authorization", getAccessToken(Tenants.getTenantUnderTestOrgName(), "main"))
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("{ " +
                        "\"name\": \"" + name + "\"," +
                        "\"description\": \"" + description + "\"," +
                        "\"agentIds\": [\"" + agentId +
                        "\"]" +
                        "}")
                .post(Endpoints.DEPARTMENTS);
        Assert.assertEquals(resp.statusCode(), 200,
                "Creating of department was not successful\n" +
                        "resp body: " + resp.getBody().asString());
    }

    public static Map getActiveSessionByClientId(String clientId) {
        String tenantID = getTenant(Tenants.getTenantUnderTestOrgName()).get("id");
        String url = format(Endpoints.INTERNAL_CHAT_BY_CLIENT, tenantID, clientId);
        Response resp = RestAssured.get(url);
        Map activeSession = null;
        try {
            activeSession = resp.getBody().jsonPath().getList("content.sessions[0]")
                    .stream()
                    .map(e -> (Map) e)
                    .filter(map -> map.get("state").equals("ACTIVE"))
                    .findFirst().get();
        } catch (JsonPathException e) {
            Assert.fail("Failed to get session Id\n" +
                    "resp status: " + resp.statusCode() + "\n" +
                    "resp body:" + resp.getBody().asString() + "\n");
        }
        return activeSession;
    }

    public static UserPersonalInfo getUserPersonalInfo(String tenantOrgName, String clineId, String integrationType) {
        JsonPath respJSON = getCustomerView(tenantOrgName, clineId);
        List<ClientProfileOld> clientProfiles = respJSON.getList("clientProfiles", ClientProfileOld.class);
//        List<ClientProfileOld> clientProfilesList = new ArrayList<ClientProfileOld>();
//        for (String object: objects){
//            ClientProfileOld cp = null;
//            try {
//                cp = new ObjectMapper().readValue(object, ClientProfileOld.class);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            clientProfilesList.add(cp);
//        }
        ClientProfileOld clientProfile = null;
        for (ClientProfileOld profile : clientProfiles) {
            if (profile.getType().equalsIgnoreCase("TENANT")) clientProfile = profile;
        }

        String fullName = clientProfile.getAttributes().getFirstName();
        String lastName = clientProfile.getAttributes().getLastName();
        fullName = (lastName == null || lastName.isEmpty()) ? fullName : fullName + " " + lastName;
        String location = clientProfile.getAttributes().getLocation();
//        String fullName = "";
//        if(respJSON.getString("personalDetails.firstName") == null &&
//                respJSON.getString("personalDetails.lastName") == null){
//            fullName = respJSON.getString("clientProfiles.clientId[0]");
//        } else {
//            String lastName =respJSON.getString("personalDetails.lastName") == null ? "" : respJSON.getString("personalDetails.lastName");
//            fullName = respJSON.getString("personalDetails.firstName") + " " + lastName;
//        }
//        String location = respJSON.getString("personalDetails.location") == null ? "Unknown location" : respJSON.getString("personalDetails.location");
        if (location == null || location.isEmpty()) location = "Unknown location";
        String customerSince = getCustomerSince(clientProfile.getCreatedDate());

        String channelUsername = "";
        try {
            channelUsername = respJSON.getString("personalDetails.channelUsername").isEmpty() ? "Unknown" : respJSON.getString("personalDetails.channelUsername");
        } catch (NullPointerException e) {
            channelUsername = respJSON.getString("personalDetails.channelUsername") == null ? "Unknown" : respJSON.getString("personalDetails.channelUsername");
        }

        String phone = clientProfile.getAttributes().getPhone();
        phone = (phone == null || phone.isEmpty()) ? "Unknown" : phone;
        String email = clientProfile.getAttributes().getEmail();
        email = (email == null || email.isEmpty()) ? "Unknown" : email;

//        String phone =  (respJSON.getString("clientProfiles.attributes.phone[0]")==null || respJSON.getString("clientProfiles.attributes.phone[0]").isEmpty()) ? "Unknown" : respJSON.getString("clientProfiles.attributes.phone[0]");
//        String email = (respJSON.getString("personalDetails.email")==null || respJSON.getString("personalDetails.email").isEmpty()) ? "Unknown" : respJSON.getString("personalDetails.email");

        return new UserPersonalInfo(fullName.trim(), location,
                "Customer since: " + customerSince, email,
                channelUsername, phone.replaceAll(" ", ""));
    }

    public static void updateClientProfileAttribute(String attributeName, String attribute, String clientId) {
        Response resp = RestAssured.given().log().all()
                .header("Authorization", getAccessToken(Tenants.getTenantUnderTestOrgName(), "main"))
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("{" +
                        "  \"type\": \"HTTP\",\n" +
                        "  \"clientId\": \"" + clientId + "\",\n" +
                        "  \"attributes\": {\n" +
                        "  \"" + attributeName + "\": \"" + attribute + "\"" +
                        "  }" +
                        "}")
                .post(Endpoints.CLIENT_PROFILE_ATTRIBUTES);
        if (!(resp.statusCode() == 200)) {
            Assert.fail("Attribute with " + attributeName + " was not updated with " + attribute + " status code = " + resp.statusCode() +
                    "\n Body: " + resp.getBody().asString());
        }
    }

    public static JsonPath getCustomerView(String tenantOrgName, String clineId) {
        String sessionId = (String) getActiveSessionByClientId(clineId).get("sessionId");
        return RestAssured.given()
                .header("Authorization", getAccessToken(tenantOrgName, "main"))
                .get(Endpoints.CUSTOMER_VIEW + sessionId)
                .getBody().jsonPath();

    }

    public static String getCustomerSince(String customerSinceFullDate) {
        //String customerSinceFullDate  = respJSON.getString("personalDetails.customerSince");
        ZoneId zoneId = TimeZone.getDefault().toZoneId();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        return LocalDateTime.parse(customerSinceFullDate, formatter).atZone(ZoneId.of("UTC"))
                .withZoneSameInstant(zoneId).toLocalDateTime()
                .format(DateTimeFormatter.ofPattern("dd MMM yyyy"));
    }

    public static void deleteAgentPhotoForMainAQAAgent(String tenantOrgName) {
        String agentId = getAgentInfo(tenantOrgName, "main").get("id");
        Response resp = RestAssured.given()
                .header("Authorization", getAccessToken(tenantOrgName, "main"))
                .delete(format(Endpoints.DELETE_AGENT_IMAGE, agentId));
        if (!(resp.getStatusCode() == 200)) {
            Assert.fail("Agent image was not successful" + " status code = " + resp.statusCode() +
                    "\n Body: " + resp.getBody().asString());
        }
    }

    public static void deleteTenantBrandImage(String tenantOrgName) {
        String tenantID = getTenant(tenantOrgName).get("id");
        RestAssured.given()
                .header("Authorization", getAccessToken(tenantOrgName, "main"))
                .delete(format(Endpoints.TENANT_BRAND_LOGO, tenantID));

        RestAssured.given()
                .header("Authorization", getAccessToken(tenantOrgName, "main"))
                .delete(format(Endpoints.TENANT_BRAND_LOGO_TRANS, tenantID));
    }

    public static Response getTenantBrandImage(String tenantOrgName) {
        String tenantID = getTenant(tenantOrgName).get("id");
        return RestAssured.given()
                .header("Authorization", getAccessToken(tenantOrgName, "main"))
                .get(format(Endpoints.TENANT_BRAND_LOGO, tenantID));
    }

    public static Response getTenantBrandImageTrans(String tenantOrgName) {
        String tenantID = getTenant(tenantOrgName).get("id");
        return RestAssured.given()
                .header("Authorization", getAccessToken(tenantOrgName, "main"))
                .get(format(Endpoints.TENANT_BRAND_LOGO_TRANS, tenantID));
    }

    public static Response getSessionDetails(String clientID) {
        return RestAssured.get(format(Endpoints.INTERNAL_SESSION_DETAILS, Tenants.getTenantUnderTestName(), clientID));
    }

    public static Response getFinishedChatsByLoggedInAgentAgent(String tenantOrgName, int page, int size) {
        String agentId = getAgentInfo(tenantOrgName, "main").get("id");
        String url = format(Endpoints.INTERNAL_GET_CHATS_FINISHED_BY_AGENT, agentId, page, size);
        return RestAssured.get(url);
    }

    public static Response getActiveChatsByAgent(String agent) {
        return RestAssured.given().contentType(ContentType.JSON).log().all()
                .header("Authorization",
                        getAccessToken(Tenants.getTenantUnderTestOrgName(), agent))
                .body("{\n" +
                        "  \"chatStates\": [\n" +
                        "    \"LIVE_IN_SCHEDULER_QUEUE\",\n" +
                        "    \"LIVE_ASSIGNED_TO_AGENT\"\n" +
                        "  ],\n" +
                        "\"agentSearchModel\": {\n" +
                        "    \"id\": \"" + getAgentId(Tenants.getTenantUnderTestOrgName(), agent) + "\"\n" +
                        "  }\n" +
                        "}")
                .post(format(Endpoints.ACTIVE_CHATS_BY_AGENT, ConfigManager.getEnv()));
    }

    public static void closeActiveChats(String agent) {
        Response resp = getActiveChatsByAgent(agent);
        List<String> conversationIds = resp.getBody().jsonPath().getList("content.chatId");
        for (String conversationId : conversationIds) {
            Response r = RestAssured.given()
                    .accept(ContentType.JSON).log().all()
                    .header("Authorization",
                            getAccessToken(Tenants.getTenantUnderTestOrgName(), agent))
                    .get(format(Endpoints.CLOSE_ACTIVE_CHAT, conversationId));
            System.out.println(r.getBody().asString());
        }
    }

    public static Map getElasticSearchModel(String chatId) {
        String tenantOrgName = Tenants.getTenantUnderTestOrgName();
        String tenantID = getTenant(tenantOrgName).get("id");
        Response resp = RestAssured.given().log().all()
                .header("Authorization", getAccessToken(tenantOrgName, "main"))
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "  \"page\": 0,\n" +
                        "  \"size\": 1,\n" +
                        "  \"searchModel\": {\n" +
                        "    \"id\": \"" + chatId + "\",\n" +
                        "    \"tenantId\": \"" + tenantID + "\"\n" +
                        "  }\n" +
                        "}")
                .post(Endpoints.INTERNAL_ELASTIC_CHAT_SEARCH);
        return (Map) resp.getBody().jsonPath().getList("model").get(0);
    }

    public static void updateElasticSearchModel(Map elasticSearchModel) {
        Response resp = RestAssured.given().log().all().header("Authorization", getAccessToken(Tenants.getTenantUnderTestOrgName(), "main"))
                .accept(ContentType.ANY)
                .contentType(ContentType.JSON).body(elasticSearchModel)
                .put(Endpoints.INTERNAL_ELASTIC_CHAT_INDEX);
        if (!(resp.statusCode() == 200)) {
            Assert.fail("Failed to update internal chat elastic search model = " + resp.statusCode() +
                    "\n Body: " + resp.getBody().asString());
        }
    }


    public static String getClientProfileId(String clientID) {
        return getSessionDetails(clientID).getBody().jsonPath().getString("data.clientProfileId[0]");
    }

    public static List<CRMTicket> getCRMTickets() {
        return RestAssured.given().log().all()
                .header("Authorization", getAccessToken(Tenants.getTenantUnderTestOrgName(), "main"))
                .get(format(Endpoints.CRM_TICKET, clientProfileId.get()))
                .getBody().jsonPath().getList("", CRMTicket.class);
    }

    public static List<String> getTagsForCRMTicket(String chatId) {
        return RestAssured.given().header("Authorization", getAccessToken(Tenants.getTenantUnderTestOrgName(), "main"))
                .get(format(Endpoints.CHATS_INFO, chatId)).getBody().jsonPath().getList("tags.value");
    }

    public static List<String> getAllTags() {
        return RestAssured.given()
                .header("Authorization", getAccessToken(Tenants.getTenantUnderTestOrgName(), "main"))
                .get(Endpoints.TAGS_FOR_CRM_TICKET).getBody().jsonPath().getList("value");
    }


    public static Response createCRMTicket(String clientID, Map<String, String> ticketInfo) {
        return RestAssured.given().log().all()
                .header("Authorization", getAccessToken(Tenants.getTenantUnderTestOrgName(), "main"))
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("{" +
                        "  \"conversationId\": \"" + ticketInfo.get("conversationId") + "\",\n" +
                        "  \"sessionId\": \"" + ticketInfo.get("sessionId") + "\",\n" +
                        "  \"link\": \"" + ticketInfo.get("link") + "\",\n" +
                        "  \"ticketNumber\": \"" + ticketInfo.get("ticketNumber") + "\",\n" +
                        "  \"agentNote\": \"" + ticketInfo.get("agentNote") + "\"\n" +
                        "}")
                .post(format(Endpoints.CRM_TICKET, ticketInfo.get("clientProfileId")));
    }

    public static void deleteCRMTicket(String crmTicketId) {
        RestAssured.given().log().all()
                .header("Authorization", getAccessToken(Tenants.getTenantUnderTestOrgName(), "main"))
                .accept(ContentType.JSON)
                .delete(Endpoints.DELETE_CRM_TICKET + crmTicketId);
    }


    public static Response updateTenantConfig(String tenantOrgName, TenantChatPreferences body) {
        String tenantId = getTenant(tenantOrgName).get("id");
        return RestAssured.given().log().all()
                .header("Authorization", getAccessToken(Tenants.getTenantUnderTestOrgName(), "main"))
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(TenantChatPreferences.class)
                .put(Endpoints.TENANT_CHAT_PREFERENCES);
    }

    public static TenantChatPreferences getTenantChatPreferences() {
        return RestAssured.given().log().all()
                .header("Authorization", getAccessToken(Tenants.getTenantUnderTestOrgName(), "main"))
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON).get(Endpoints.TENANT_CHAT_PREFERENCES).getBody().as(TenantChatPreferences.class);
    }

    //    Response resp = ApiHelper.createFBChat(FacebookPages.getFBPageFromCurrentEnvByTenantOrgName(tenantOrgName).getFBPageId(), 1912835872122481l, "to agent the last");

    public static Response createFBChat(long linkedFBPageId, long fbUserId, String message) {
        Faker faker = new Faker();
        String mid = faker.code().isbn13(true) + "-" + faker.lorem().characters(3, 6, true);
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = LocalDateTime.now().atZone(zoneId);
        long timestamp = zdt.toInstant().toEpochMilli();
        String requestBody1 = "{\n" +
                "  \"entry\": [\n" +
                "    {\n" +
                "      \"changes\": [],\n" +
                "      \"id\": \"" + linkedFBPageId + "\",\n" +
                "      \"messaging\": [\n" +
                "        {\n" +
                "          \"message\": {\n" +
                "            \"mid\": \"" + mid + "\",\n" +
                "            \"seq\": 31478,\n" +
                "            \"text\": \"" + message + "\"\n" +
                "          },\n" +
                "          \"recipient\": {\n" +
                "            \"id\": \"" + linkedFBPageId + "\"\n" +
                "          },\n" +
                "          \"sender\": {\n" +
                "            \"id\": \"" + fbUserId + "\"\n" +
                "          },\n" +
                "          \"timestamp\": " + timestamp + "\n" +
                "        }\n" +
                "      ],\n" +
                "      \"time\": " + timestamp + "\n" +
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

    public static List<AvailableAgent> getAvailableAgents() {
        return RestAssured.given().log().all()
                .header("Authorization", getAccessToken(Tenants.getTenantUnderTestOrgName(), "main"))
                .get(Endpoints.TENANT_AVAILABLE_AGENTS)
                .getBody().jsonPath().getList("agents", AvailableAgent.class);
    }

    public static ClientProfile getClientAttributes(String clientProfileID) {
        return RestAssured.given().log().all()
                .header("Authorization", getAccessToken(Tenants.getTenantUnderTestOrgName(), "main"))
                .get(format(Endpoints.INTERNAL_CLIENT_PROFILE_ATTRIBUTES_ENDPOINT, clientProfileID))
                .getBody().as(ClientProfile.class);
    }

    public static Response createChatHistory(ChatHistory history) {
        return RestAssured.given().log().all()
                .accept(ContentType.ANY)
                .contentType(ContentType.JSON)
                .body(history)
                .post(Endpoints.INTERNAL_CREATE_HISTORY);
    }

    @Nullable
    private static ResponseBody getPostQueryFor(String tenantOrgName, String endpoint, String body) {
        Response response = postQuery(tenantOrgName, body, endpoint);

        if (response.getStatusCode() != 200) {
            fail("Couldn't get 'Agent Support Hours' \n"
                    + "Status code: " + response.statusCode() + "\n"
                    + "TenantOrgName: " + tenantOrgName + "\n"
                    + "Error message: " + response.getBody().asString());
            return null;
        } else {
            return response.getBody();
        }
    }

    @Nullable
    private static ResponseBody getQueryFor(String tenantOrgName, String endpoint) {
        Response response = getQuery(tenantOrgName, endpoint, "main");

        if (response.getStatusCode() != 200) {
            fail("Couldn't get 'Agent Support Hours' \n"
                    + "Status code: " + response.statusCode() + "\n"
                    + "TenantOrgName: " + tenantOrgName + "\n"
                    + "Error message: " + response.getBody().asString());
            return null;
        } else {
            return response.getBody();
        }
    }

    private static Response postQuery(String tenantOrgName, String body, String endpoint) {
        return RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", getAccessToken(tenantOrgName, "main"))
                .body(body)
                .post(format(endpoint, getTenant(tenantOrgName).get("id")));
    }

    private static Response getQuery(String tenantOrgName, String endpoint, String agent) {
        return RestAssured.given().log().all()
                .accept(ContentType.JSON)
                .header("Authorization", getAccessToken(tenantOrgName, agent))
                .get(format(endpoint, tenantOrgName));
    }

    private static Map<String, String> getTenant(String tenantOrgName) {
        return ApiHelper.getTenantInfoMap(tenantOrgName);
    }

    private static String getAccessToken(String tenantOrgName, String main) {
        return TouchAuthToken.getAccessTokenForTouchUser(tenantOrgName, main);
    }
}