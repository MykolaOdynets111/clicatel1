package api_helper;

import dataManager.Tenants;
import dataManager.jackson_schemas.Intent;
import dataManager.jackson_schemas.TIE.TIEIntentPerCategory;
import driverManager.URLs;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.get;

public class ApiHelperTie {

    public static List<Intent> getListOfIntentsOnUserMessage(String userMessage) {
        Response resp = RestAssured.get(URLs.getTieURL(Tenants.getTenantUnderTestOrgName(), userMessage));
        return resp.jsonPath().getList("intents_result.intents", Intent.class).stream()
                .sorted(Comparator.comparing(Intent::getConfidence).reversed()).collect(Collectors.toList());
    }


    public static String getExpectedMessageOnIntent(String intent) {
        return RestAssured.get(URLs.getTIEURLForAnswers(Tenants.getTenantUnderTestOrgName(), intent)).jsonPath().get("text");

    }

    public static String getExpectedMessageOnIntent(String tenantOrgName, String intent) {
        return RestAssured.get(URLs.getTIEURLForAnswers(tenantOrgName, intent)).jsonPath().get("text");
    }

    public static String getTenantConfig(String tenantName, String configName){
        String url = String.format(Endpoints.TIE_CONFIG, tenantName);
        return RestAssured.get(url).jsonPath().get(configName).toString();
    }

    public static double getIntentConfidenceOnUserMessage(String userMessage) {
        Response resp = RestAssured.get(URLs.getTieURL(Tenants.getTenantUnderTestOrgName(), userMessage));
        return resp.jsonPath().getList("intents_result.intents", Intent.class).get(0).getConfidence();
    }

    public static List<String> getLIstOfAllFAGCategories(){
        String url = String.format(Endpoints.TIE_ANSWER_BY_CATEGORY_URL, Tenants.getTenantUnderTest(), "all");
        return get(url).getBody().jsonPath().getList("categories");
    }

    public static List<TIEIntentPerCategory> getListOfIntentsForCategory(String category){
        String url = String.format(Endpoints.TIE_ANSWER_BY_CATEGORY_URL, Tenants.getTenantUnderTest(), category);
        return get(url).getBody().jsonPath().getList("data", TIEIntentPerCategory.class);
    }
}
