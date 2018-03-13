package dataprovider;


import api_helper.Endpoints;
import api_helper.RequestSpec;
import dataprovider.jackson_schemas.Territory;
import driverManager.ConfigManager;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.HashMap;
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
                .get(String.format(Endpoints.BASE_TOUCH_ENDPOINT, ConfigManager.getEnv()) +
                        Endpoints.WIDGET_VISIBILITY_TERRITORIES);
        List<Territory> terr = resp.jsonPath().getList("territory", Territory.class);
        return terr.stream().filter(e -> e.getName().equalsIgnoreCase(terrName)).findFirst().get();
    }
}
