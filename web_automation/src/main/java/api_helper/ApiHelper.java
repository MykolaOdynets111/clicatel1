package api_helper;

import dataprovider.Accounts;
import dataprovider.Tenants;
import dataprovider.Territories;
import dataprovider.jackson_schemas.Country;
import dataprovider.jackson_schemas.Territory;
import dataprovider.jackson_schemas.tenant_address.TenantAddress;
import dataprovider.jackson_schemas.user_session_info.UserSession;
import driverManager.ConfigManager;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApiHelper {

    private static  List<HashMap> tenantsInfo=null;
    private static List<HashMap> tenantMessages=null;

    public static String getTenantConfig(String tenantID, String config){
        String url = String.format(Endpoints.BASE_INTERNAL_ENDPOINT, ConfigManager.getEnv())+
                String.format(Endpoints.INTERNAL_TENANT_CONFIG, tenantID);
        return RestAssured.get(url).jsonPath().get(config);
    }

    public static Map<String, String> getTenantInfoMap(String theValue) {
        Map<String, String> tenantsMap = new HashMap<>();
        List<HashMap> tenants = getAllTenantInfo();
        tenants.stream().forEach(e-> tenantsMap.put(((String) e.get("tenantOrgName")).toLowerCase(),
                (String) e.get(theValue)));
        return tenantsMap;
    }

    public static void createUserProfile(String tenantName, String clientID, String keyName, String keyValue) {
        String url = String.format(Endpoints.BASE_INTERNAL_ENDPOINT, ConfigManager.getEnv())+
                String.format(Endpoints.CREATE_USER_PROFILE_ENDPOINT, tenantName, clientID, keyName, keyValue);
        RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .post(url);
    }

    public static void deleteUserProfile(String tenantName, String clientID) {
        String url = String.format(Endpoints.BASE_INTERNAL_ENDPOINT, ConfigManager.getEnv())+
                String.format(Endpoints.DELETE_USER_PROFILE_ENDPOINT, tenantName, clientID);
        RestAssured.given()
                .header("Accept", "application/json")
                .delete(url);
    }

    private static List<HashMap> getAllTenantInfo() {
        if(tenantsInfo==null) {
            tenantsInfo = RestAssured.given(RequestSpec.getRequestSpecification()).get(Endpoints.GET_ALL_TENANTS_ENDPOINT)
                    .jsonPath().get("tenants");
        }
        return tenantsInfo;
    }

    private static List<HashMap> getTenantMessagesInfo() {
        if(tenantMessages==null) {
            String url = String.format(Endpoints.BASE_INTERNAL_ENDPOINT, ConfigManager.getEnv())+
                    String.format(Endpoints.TENANT_CONFIGURED_MESSAGES, Tenants.getTenantUnderTest());
            tenantMessages = RestAssured.given().get(url)
                    .jsonPath().get("tafResponses");
        }
        return tenantMessages;
    }

    public static String getTenantMessageText(String id) {
        return (String) getTenantMessagesInfo().stream().filter(e -> e.get("id").equals(id)).findFirst().get().get("text");
    }

    public static void setWidgetVisibilityDaysAndHours(String tenantOrgName, String day, String startTime,  String endTime) {
        String body = createPutBodyForWidgetDisplayHours(day, startTime, endTime);
        RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", RequestSpec.getAccessTokenForPortalUser(tenantOrgName))
                .body(body)
                .put(String.format(Endpoints.BASE_TOUCH_ENDPOINT, ConfigManager.getEnv()) +
                        String.format(Endpoints.WIDGET_VISIBILITY_HOURS, getTenantInfoMap("id").get(tenantOrgName.toLowerCase())));
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
                .post(String.format(Endpoints.BASE_TOUCH_ENDPOINT, ConfigManager.getEnv()) +
                        Endpoints.WIDGET_VISIBILITY_TERRITORIES);
    }


    public static void setAvailabilityForTerritoryAndCountry(String tenantOrgName, String terrName, boolean terrAvailability,
                                                             String countryName, boolean countryAvailability){
        Territory targetTerr =  Territories.getTargetTerr(tenantOrgName, terrName);
        String territoryID = targetTerr.getTerritoryId();
        Country targetCountry = targetTerr.getCountry().stream().filter(e -> e.getName().equalsIgnoreCase(countryName))
                .findFirst().get();
        String countryID = targetCountry.getCountryId();
        RestAssured.given()
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
                .post(String.format(Endpoints.BASE_TOUCH_ENDPOINT, ConfigManager.getEnv()) +
                        Endpoints.WIDGET_VISIBILITY_TERRITORIES);
    }

    public static void setAvailabilityForTerritory(String tenantOrgName, String terrName, boolean terrAvailability){
        Territory targetTerr =  Territories.getTargetTerr(tenantOrgName, terrName);
        String territoryID = targetTerr.getTerritoryId();
        RestAssured.given()
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
                .post(String.format(Endpoints.BASE_TOUCH_ENDPOINT, ConfigManager.getEnv()) +
                        Endpoints.WIDGET_VISIBILITY_TERRITORIES);
    }


    public static List<TenantAddress> getTenantAddressInfo(String tenantName) {
        return RestAssured.given()
                .header("Accept", "application/json")
                .get(String.format(Endpoints.BASE_INTERNAL_ENDPOINT, ConfigManager.getEnv())+
                     String.format(Endpoints.INTERNAL_TENANT_ADDRESS, tenantName))
                .jsonPath().getList("addresses", TenantAddress.class);
    }


    public static UserSession getLastUserSession(String userID, String tenant){
        return RestAssured.given()
                .header("Accept", "application/json")
                .get(String.format(Endpoints.BASE_INTERNAL_ENDPOINT, ConfigManager.getEnv()) +
                String.format(Endpoints.INTERNAL_LAST_CLIENT_SESSION, tenant, userID))
                .getBody().as(UserSession.class);

    }


    public static void updateFeatureStatus(String tenantOrgName, String feature, String status){
        String tenantID = Tenants.getTenantInfo(tenantOrgName, "id");
        String url = Endpoints.BASE_INTERNAL_ENDPOINT +
                String.format(Endpoints.INTERNAL_FEATURE_STATE,tenantID, feature, status);
    }
}
