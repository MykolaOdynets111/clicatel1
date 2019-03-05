package apihelper;

import datamanager.Tenants;
import datamanager.jacksonschemas.Intent;
import datamanager.jacksonschemas.tie.TIEIntentPerCategory;
import drivermanager.URLs;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.get;

public class ApiHelperTie {

    public static List<Intent> getListOfIntentsOnUserMessage(String userMessage) {
        Response resp = RestAssured.get(URLs.getTieURL(Tenants.getTenantUnderTestName(), userMessage));
        return resp.jsonPath().getList("intents_result.intents", Intent.class).stream()
                .sorted(Comparator.comparing(Intent::getConfidence).reversed()).collect(Collectors.toList());
    }


    public static String getExpectedMessageOnIntent(String intent) {
        return RestAssured.get(URLs.getTIEURLForAnswers(Tenants.getTenantUnderTestName(), intent)).jsonPath().get("text");

    }

    public static String getExpectedMessageOnIntent(String tenantOrgName, String intent) {
        return RestAssured.get(URLs.getTIEURLForAnswers(Tenants.getTenantNameByTenantOrgName(tenantOrgName), intent)).jsonPath().get("text");
    }

    public static String getTenantConfig(String tenantName, String configName){
        String url = String.format(Endpoints.TIE_CONFIG, tenantName);
        return RestAssured.get(url).jsonPath().get(configName).toString();
    }

    public static double getIntentConfidenceOnUserMessage(String userMessage) {
        Response resp = RestAssured.get(URLs.getTieURL(Tenants.getTenantUnderTestName(), userMessage));
        return resp.jsonPath().getList("intents_result.intents", Intent.class).get(0).getConfidence();
    }

    public static List<String> getLIstOfAllFAGCategories(){
        String url = String.format(Endpoints.TIE_ANSWER_BY_CATEGORY_URL, Tenants.getTenantUnderTestName(), "all");
        return get(url).getBody().jsonPath().getList("categories");
    }

    public static List<TIEIntentPerCategory> getListOfIntentsForCategory(String category){
        String url = String.format(Endpoints.TIE_ANSWER_BY_CATEGORY_URL, Tenants.getTenantUnderTestName(), category);
        return get(url).getBody().jsonPath().getList("data", TIEIntentPerCategory.class);
    }

    public static String getTIESentimentOnMessage(String userMessage){
        Response resp = RestAssured.get(URLs.getTieURL(Tenants.getTenantUnderTestName(), userMessage));
        return resp.jsonPath().get("sentiment_verdict");
    }

    public static Response createNewIntent(String tenantOrgName, String newIntent, String category, String answer, String type){
        return RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", RequestSpec.getAccessTokenForPortalUser(tenantOrgName))
                .body("{" +
                            "\"intent\":\""+newIntent+"\"," +
                            "\"category\":\"" +category+ "\"," +
                            "\"type\":\"" +type+ "\"," +
                            "\"answer\":\""+ answer +"\"," +
                            "\"answer_url\":\"\"," +
                            "\"tenant\":\""+Tenants.getTenantUnderTestName()+"\"" +
                        "}")
                .post(String.format(Endpoints.TIE_NEW_INTENT_MANAGEMENT, Tenants.getTenantUnderTestName()));
    }

    public static Response addNewSample(String tenantOrgName, String intent, String sample){
        return RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", RequestSpec.getAccessTokenForPortalUser(tenantOrgName))
                .body("{" +
                            "\"intent\":\"" +intent+ "\"," +
                            "\"category\":\"Some FAQ\"," +
                            "\"type\":\"FAQ\"," +
                            "\"text\":\"" +sample+ "\"," +
                            "\"tags\":\"\"," +
                            "\"tenant\":\"" +Tenants.getTenantUnderTestName()+ "\"" +
                        "}")
                .post(String.format(Endpoints.TIE_SAMPLES, Tenants.getTenantUnderTestName()));
    }

    public static Response scheduleTraining(){
        return RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", RequestSpec.getAccessTokenForPortalUser(Tenants.getTenantUnderTestOrgName()))
                .get(String.format(Endpoints.TIE_TRAINING, Tenants.getTenantUnderTestName()));
    }

    public static Response getModels(){
        return RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", RequestSpec.getAccessTokenForPortalUser(Tenants.getTenantUnderTestOrgName()))
                .get(String.format(Endpoints.TIE_MODELS, Tenants.getTenantUnderTestName()));
    }

    public static Response getAllIntents(){
        return RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", RequestSpec.getAccessTokenForPortalUser(Tenants.getTenantUnderTestOrgName()))
                .get(String.format(Endpoints.TIE_ALL_INTENTS, Tenants.getTenantUnderTestName()));
    }

    public static Response deleteIntent(String intentId){
        return RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", RequestSpec.getAccessTokenForPortalUser(Tenants.getTenantUnderTestOrgName()))
                .body("{\"ids\":[\"" +intentId+ "\"]}")
                .delete(String.format(Endpoints.TIE_NEW_INTENT_MANAGEMENT, Tenants.getTenantUnderTestName()));
    }


    public static Response getTrainData(){
        return RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", RequestSpec.getAccessTokenForPortalUser(Tenants.getTenantUnderTestOrgName()))
                .get(String.format(Endpoints.TIE_SAMPLES, Tenants.getTenantUnderTestName()));
    }

    public static Response deleteSample(String sampleId){
        return RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", RequestSpec.getAccessTokenForPortalUser(Tenants.getTenantUnderTestOrgName()))
                .body("{\"ids\":[\"" +sampleId+ "\"]}")
                .delete(String.format(Endpoints.TIE_SAMPLES, Tenants.getTenantUnderTestName()));

    }

}
