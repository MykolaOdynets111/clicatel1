package api_helper;

import dataManager.MC2Account;
import dataManager.Tenants;
import dataManager.Territories;
import dataManager.jackson_schemas.Country;
import dataManager.jackson_schemas.Territory;
import dataManager.jackson_schemas.tenant_address.TenantAddress;
import dataManager.jackson_schemas.user_session_info.UserSession;
import driverManager.ConfigManager;
import io.restassured.RestAssured;
import io.restassured.path.json.exception.JsonPathException;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApiHelper {

    private static  List<HashMap> tenantsInfo=null;
    private static List<HashMap> tenantMessages=null;

    public static String getTenantConfig(String tenantName, String config){
        String url = String.format(Endpoints.INTERNAL_TENANT_CONFIG, tenantName);
        return RestAssured.get(url).jsonPath().get(config);
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
        String url = String.format(Endpoints.INTERNAL_DELETE_USER_PROFILE_ENDPOINT, tenantName, clientID);
        RestAssured.given()
                .header("Accept", "application/json")
                .delete(url);
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

    private static List<HashMap> getTenantMessagesInfo() {
        if(tenantMessages==null) {
            String url = String.format(Endpoints.INTERNAL_TENANT_CONFIGURED_MESSAGES, Tenants.getTenantUnderTest());
            tenantMessages = RestAssured.given().get(url)
                    .jsonPath().get("");
        }
        return tenantMessages;
    }

    public static String getTenantMessageText(String id) {
        return (String) getTenantMessagesInfo().stream().filter(e -> e.get("id").equals(id)).findFirst().get().get("text");
    }

    public static void setWidgetVisibilityDaysAndHours(String tenantOrgName, String day, String startTime,  String endTime) {
        String body = createPutBodyForWidgetDisplayHours(day, startTime, endTime);
        RestAssured.given().log().all()
                .header("Content-Type", "application/json")
                .header("Authorization", RequestSpec.getAccessTokenForPortalUser(tenantOrgName))
                .body(body)
                .put(String.format(Endpoints.WIDGET_VISIBILITY_HOURS, getTenantInfoMap("id").get(tenantOrgName.toLowerCase())));
    }

    private static String createPutBodyForWidgetDisplayHours(String day, String startTime,  String endTime) {
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
        return resp.getBody().jsonPath().getBoolean(FEATURE);
    }

    public static int getNumberOfLoggedInAgents(){
        String url = String.format(Endpoints.INTERNAL_COUNT_OF_LOGGED_IN_AGENTS, Tenants.getTenantUnderTest());
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

    public static void setIntegrationStatus(String tenantOrgName, String integration, boolean integrationStatus){
        RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", RequestSpec.getAccessTokenForPortalUser(tenantOrgName))
                .body("{\n" +
                        "  \"integrationType\": \""+ integration.toUpperCase() +"\",\n" +
                        "  \"channelType\": \"webchat\",\n" +
                        "  \"enable\": "+ integrationStatus + "" +
                        "}")
                .put(Endpoints.INTEGRATIONS_ENABLING_DISABLING);
    }
}
