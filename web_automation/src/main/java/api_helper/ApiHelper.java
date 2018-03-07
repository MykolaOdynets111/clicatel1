package api_helper;

import dataprovider.Tenants;
import driverManager.ConfigManager;
import io.restassured.RestAssured;

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
}
