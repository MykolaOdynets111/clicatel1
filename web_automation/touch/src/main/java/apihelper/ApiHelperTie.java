package apihelper;

import datamanager.Tenants;
import datamanager.jacksonschemas.Intent;
import datamanager.jacksonschemas.tie.CreateSlotBody;
import datamanager.jacksonschemas.tie.TIEIntentPerCategory;
import driverfactory.URLs;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import mc2api.auth.PortalAuthToken;
import org.testng.Assert;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.get;

public class ApiHelperTie {

    public static List<Intent> getListOfIntentsOnUserMessage(String userMessage) {
        Response resp = RestAssured
                .given()
                .header("Authorization",
                        PortalAuthToken.getAccessTokenForPortalUser(Tenants.getTenantUnderTestName(), "main"))
                .get(URLs.getTieURL(Tenants.getTenantUnderTestName(), userMessage));
        return resp.jsonPath().getList("intents_result.intents", Intent.class).stream()
                .sorted(Comparator.comparing(Intent::getConfidence).reversed()).collect(Collectors.toList());
    }

    public static Response getRespWithIntentsOnUserMessageWithAutorization(String userMessage) {
         return RestAssured.given()
                .header("Authorization", PortalAuthToken.getAccessTokenForPortalUser(Tenants.getTenantUnderTestName(), "main"))
                .get(URLs.getTieURL(Tenants.getTenantUnderTestName(), userMessage));
         }

    public static String getExpectedMessageOnIntent(String intent) {
        Response resp  = RestAssured.given()
                .header("Authorization",
                        PortalAuthToken.getAccessTokenForPortalUser(Tenants.getTenantUnderTestName(), "main"))
                .get(URLs.getTIEURLForAnswers(Tenants.getTenantUnderTestName(), intent));
        String answer = resp.jsonPath().get("text");
        if(answer == null) Assert.fail("Empty answer on '" + intent + "' intent\n"
                                                                + resp.getBody().asString());
        return answer;
    }

    public static String getExpectedMessageOnIntent(String tenantOrgName, String intent) {
        return RestAssured
                .get(URLs.getTIEURLForAnswers(Tenants.getTenantNameByTenantOrgName(tenantOrgName), intent))
                .jsonPath().get("text");
    }

    public static String getTenantConfig(String tenantName, String configName){
        String url = String.format(Endpoints.TIE_CONFIG, tenantName);
        Response resp = RestAssured.get(url);
        if(resp.statusCode()!=200) Assert.fail("Tie call was not successful\n " + resp.getBody().asString());
        return resp.jsonPath().get(configName).toString();
    }

    public static double getIntentConfidenceOnUserMessage(String userMessage) {
        Response resp = RestAssured.get(URLs.getTieURL(Tenants.getTenantUnderTestName(), userMessage));
        return resp.jsonPath().getList("intents_result.intents", Intent.class).get(0).getConfidence();
    }

    public static List<String> getLIstOfAllFAGCategories(){
        String url = String.format(Endpoints.TIE_ANSWER_BY_CATEGORY_URL, Tenants.getTenantUnderTestName(), "all");
        Response resp = get(url);
        if(resp.statusCode()!=200) Assert.fail("Tie call was not successful\n " + resp.getBody().asString());
        return resp.getBody().jsonPath().getList("categories");
    }

    public static List<TIEIntentPerCategory> getListOfIntentsForCategory(String category){
        String url = String.format(Endpoints.TIE_ANSWER_BY_CATEGORY_URL, Tenants.getTenantUnderTestName(), category);
        return get(url).getBody().jsonPath().getList("data", TIEIntentPerCategory.class);
    }

    public static String getTIESentimentOnMessage(String userMessage){
        Response resp = RestAssured.given()
                .header("Authorization", PortalAuthToken.getAccessTokenForPortalUser(Tenants.getTenantUnderTestName(), "main"))
                .get(URLs.getTieURL(Tenants.getTenantUnderTestName(), userMessage));
        return resp.jsonPath().get("sentiment_verdict");
    }

    public static Response createNewIntent(String tenantOrgName, String newIntent, String category, String answer, String type){
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", PortalAuthToken.getAccessTokenForPortalUser(tenantOrgName, "main"))
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
                .contentType(ContentType.JSON)
                .header("Authorization", PortalAuthToken.getAccessTokenForPortalUser(tenantOrgName, "main"))
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
                .contentType(ContentType.JSON)
                .header("Authorization", PortalAuthToken.getAccessTokenForPortalUser(Tenants.getTenantUnderTestOrgName(), "main"))
                .post(String.format(Endpoints.TIE_TRAINING, Tenants.getTenantUnderTestName()));
    }

    public static Response getModels(){
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", PortalAuthToken.getAccessTokenForPortalUser(Tenants.getTenantUnderTestOrgName(), "main"))
                .get(String.format(Endpoints.TIE_MODELS, Tenants.getTenantUnderTestName()));
    }

    public static Response deleteModel(String model){
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", PortalAuthToken.getAccessTokenForPortalUser(Tenants.getTenantUnderTestOrgName(), "main"))
                .delete(String.format(Endpoints.TIE_MODELS, Tenants.getTenantUnderTestName()) + "/" + model);
    }

    public static void deleteAllModels(){
        List<Map> models = ApiHelperTie.getModels().getBody().jsonPath().getList("intent");
        for(Map model : models){
            deleteModel((String) model.get("name"));
        }
    }

    public static Response getAllIntents(){
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", PortalAuthToken.getAccessTokenForPortalUser(Tenants.getTenantUnderTestOrgName(), "main"))
                .get(String.format(Endpoints.TIE_ALL_INTENTS, Tenants.getTenantUnderTestName()));
    }

    public static Response getAllIntentsWithIDs(){
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", PortalAuthToken.getAccessTokenForPortalUser(Tenants.getTenantUnderTestOrgName(), "main"))
                .get(String.format(Endpoints.TIE_NEW_INTENT_MANAGEMENT, Tenants.getTenantUnderTestName()));
    }


    public static Response deleteIntent(String intentId){
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", PortalAuthToken.getAccessTokenForPortalUser(Tenants.getTenantUnderTestOrgName(), "main"))
                .body("{\"ids\":[\"" +intentId+ "\"]}")
                .delete(String.format(Endpoints.TIE_NEW_INTENT_MANAGEMENT, Tenants.getTenantUnderTestName()));
    }

    public static void deleteAllIntents(){
        List<String> faqIntents = ApiHelperTie.getAllIntentsWithIDs().getBody().jsonPath().getList("data").stream()
                .map(e -> (List) e)
                .filter(e -> ((String) e.get(2)).equals("faq"))
                .map(e -> (String) e.get(6))
                .collect(Collectors.toList());
        for(String id : faqIntents) {
            ApiHelperTie.deleteIntent(id);
        }
    }


    public static Response getTrainData(){
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", PortalAuthToken.getAccessTokenForPortalUser(Tenants.getTenantUnderTestOrgName(), "main"))
                .get(String.format(Endpoints.TIE_SAMPLES, Tenants.getTenantUnderTestName()));
    }

    public static Response deleteSample(String sampleId){
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", PortalAuthToken.getAccessTokenForPortalUser(Tenants.getTenantUnderTestOrgName(), "main"))
                .body("{\"ids\":[\"" +sampleId+ "\"]}")
                .delete(String.format(Endpoints.TIE_SAMPLES, Tenants.getTenantUnderTestName()));

    }

    public static Response createNewSlot(CreateSlotBody createSlotBody){
        return RestAssured.given()
                    .contentType(ContentType.JSON)
                    .header("Authorization", PortalAuthToken.getAccessTokenForPortalUser(Tenants.getTenantUnderTestOrgName(), "main"))
                    .body(createSlotBody)
                    .post(String.format(Endpoints.TIE_SLOTS_MANAGEMENT, Tenants.getTenantUnderTestName()));

    }

    public static Response updateSlot(CreateSlotBody createSlotBody, String slotId){
        return RestAssured.given()
                    .contentType(ContentType.JSON)
                    .header("Authorization", PortalAuthToken.getAccessTokenForPortalUser(Tenants.getTenantUnderTestOrgName(), "main"))
                    .body(createSlotBody)
                    .put(String.format(Endpoints.TIE_SLOTS_MANAGEMENT, Tenants.getTenantUnderTestName()) + "/" + slotId);
    }


    public static void deleteSlot(String slotId){
        RestAssured.given()
                    .header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                    .header("Authorization", PortalAuthToken.getAccessTokenForPortalUser(Tenants.getTenantUnderTestOrgName(), "main"))
                    .body("{\"ids\":[\"" +slotId+ "\"]}")
                    .delete(String.format(Endpoints.TIE_SLOTS_MANAGEMENT, Tenants.getTenantUnderTestName()));
    }

    public static Response getAllSlots(){
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", PortalAuthToken.getAccessTokenForPortalUser(Tenants.getTenantUnderTestOrgName(), "main"))
                .get(String.format(Endpoints.TIE_SLOTS_MANAGEMENT, Tenants.getTenantUnderTestName()));
    }



    public static Response getTrainings(){
        Response resp =  RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", PortalAuthToken.getAccessTokenForPortalUser(Tenants.getTenantUnderTestOrgName(), "main"))
                .get(String.format(Endpoints.TIE_TRAINING, Tenants.getTenantUnderTestName()));
        if(resp.statusCode()!=200){
            Assert.fail("Getting tie training was not successful\n"+
            "Resp code: " + resp.statusCode() + "\n" +
            "Resp body: " + resp.getBody().asString() + "\n" +
            "Resp URL: " + String.format(Endpoints.TIE_TRAINING, Tenants.getTenantUnderTestName()));
        }
        return resp;
    }


    public static Response publishModel(String model){
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", PortalAuthToken.getAccessTokenForPortalUser(Tenants.getTenantUnderTestOrgName(), "main"))
                .body("{\"model\":\"" + model + "\"}")
                .post(String.format(Endpoints.TIE_MODELS, Tenants.getTenantUnderTestName()));
    }
}
