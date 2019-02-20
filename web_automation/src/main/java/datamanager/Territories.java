package datamanager;


import apihelper.Endpoints;
import apihelper.RequestSpec;
import datamanager.jacksonschemas.Territory;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.List;

public class Territories {

    public static Territory getTargetTerr(String tenantOrgName, String terrName) {
        Response resp =        RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", RequestSpec.getAccessTokenForPortalUser(tenantOrgName))
                .body("{\n" +
                        "  \"availability\": \"AVAILABLE\"\n" +
                        "\n" +
                        "}")
                .get(Endpoints.WIDGET_VISIBILITY_TERRITORIES);
        List<Territory> terr = resp.jsonPath().getList("territory", Territory.class);
        return terr.stream().filter(e -> e.getName().equalsIgnoreCase(terrName)).findFirst().get();
    }
}
