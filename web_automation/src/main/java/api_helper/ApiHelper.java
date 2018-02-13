package api_helper;

import io.restassured.RestAssured;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApiHelper {

    public static Map<String, String> getTenantIdMap() {
        Map<String, String> tenantsMap = new HashMap<>();
        List<HashMap> tenants = RestAssured.given(RequestSpec.getRequestSpecification()).get(Endpoints.GET_ALL_TENANTS_ENDPOINT)
                                                                                    .jsonPath().get("tenants");
        tenants.stream().forEach(e-> tenantsMap.put(((String) e.get("tenantOrgName")).toLowerCase(),
                (String) e.get("id")));
        return tenantsMap;
    }
}
