package api_helper;

import dataprovider.Tenants;
import dataprovider.jackson_schemas.Intent;
import driverManager.URLs;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.List;

public class ApiHelperTie {

    public static List<Intent> getListOfIntentsOnUserMessage(String userMessage) {
        Response resp = RestAssured.get(URLs.getTieURL(Tenants.getTenantUnderTestOrgName(), userMessage));
        return resp.jsonPath().getList("intents_result.intents", Intent.class);
    }


    public static String getExpectedMessageOnIntent(String intent) {
        return RestAssured.get(URLs.getTIEURLForAnswers(Tenants.getTenantUnderTestOrgName(), intent)).jsonPath().get("text");

    }
}
