package api_helper;

import dataprovider.Accounts;
import dataprovider.Tenants;
import driverManager.ConfigManager;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApiHelper {

    private static  List<HashMap> tenantsInfo=null;
    private static List<HashMap> tenantMessages=null;


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
}
