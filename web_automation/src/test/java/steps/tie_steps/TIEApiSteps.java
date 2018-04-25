package steps.tie_steps;

import api_helper.Endpoints;
import com.github.javafaker.Faker;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import driverManager.ConfigManager;
import driverManager.URLs;
import static io.restassured.RestAssured.*;

import io.restassured.RestAssured;
import io.restassured.matcher.RestAssuredMatchers.*;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import javax.xml.ws.Endpoint;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.*;

public class TIEApiSteps {

    private static Map<Long, String> NEW_TENANT_NAMES = new HashMap<>();

    public static Map<Long, String> getNewTenantNames() {
        return NEW_TENANT_NAMES;
    }

    private static String createNewTenantName() {
        return "testing"+ System.currentTimeMillis();
    }

    // ======================= Chats ======================== //

    @When("^I send \"(.*)\" for (.*) tenant then response code is 200 and intents are not empty$")
    //API: GET /tenants/<tenant_name>/chats/?q=<user input>
    public void checkResponseStatusForIntentOnlyRequest(String userMessage, String tenant){
        String url = String.format(Endpoints.TIE_INTENT_WITHOUT_SENTIMENT_URL, ConfigManager.getEnv(),
                tenant, userMessage);
        when()
                .get(url).
        then()
                .statusCode(200)
                .body("intents_result.intents", hasSize(greaterThan(0)));
    }

    @When("^I send \"(.*)\" for (.*) tenant including tie_sentiment then response code is 200 and intents are not empty$")
    //API: GET /tenants/<tenant_name>/chats/?q=<user input>&tie_sentiment=True
    public void checkResponseStatusForIntentWIthTieSentimentOnlyRequest(String userMessage, String tenant){
        String url = String.format(Endpoints.TIE_INTENT_WITH_TIE_SENTIMENT_URL, ConfigManager.getEnv(),
                tenant, userMessage);
        when()
                .get(url).
                then()
                .statusCode(200)
                .body("intents_result.intents", hasSize(greaterThan(0)));
    }

    // ======================= Intents ======================== //

    @When("^I send \"(.*)\" intent for (.*) tenant then response code is 200 and intents are not empty$")
    // API: GET /tenants/<tenant_name>/intents/<intent_text>
    public void checkResponseStatusForsPECYFINGIntentRequest(String intentText, String tenant){
        String url = String.format(Endpoints.TIE_INTENT_SPECIFYING_SENTIMENT_URL, ConfigManager.getEnv(),
                tenant, intentText);
        when()
                .get(url).
        then()
                .statusCode(200)
                .body("intents_result.intents", hasSize(greaterThan(0)));
    }

    @When("^I add to (.*) intent (.*) sample text for created tenant status code is 200$")
    public void addSampleTextForIntent(String intent, String sampleText){
        String url = String.format(Endpoints.BASE_TIE_URL, ConfigManager.getEnv())+
                String.format(Endpoints.TIE_ADDING_INTENT_SAMPLE_TEXT_TO_TRAINING,
                        NEW_TENANT_NAMES.get(Thread.currentThread().getId()),intent,sampleText);
        when()
                .get(url).
        then()
                .statusCode(200);
    }

    // ======================= Sentiments ======================== //

    @When("^I send \"(.*)\" for (.*) tenant then sentiments response code is 200$")
    //API: GET /tenants/<tenant_name>/sentiment/?q=<user input>
    public void checkResponseStatusForSentiments(String userMessage, String tenant){
        String url = String.format(Endpoints.TIE_SENTIMENTS, ConfigManager.getEnv(),
                tenant, userMessage);
        when()
                .get(url).
        then()
                .statusCode(200)
                .body("text", equalTo(userMessage))
                .body("sentiment_score", notNullValue())
                .body("tie_sentiment_score", notNullValue());
    }


    // ======================= Intent Answers ======================== //

    @When("^I send (.*) for (.*) tenant then response code is 200 and list of answers is shown$")
    public void checkListOfAnswers(List<String> intents, String tenant){
        String url = String.format(Endpoints.TIE_ANSWERS_LIST, ConfigManager.getEnv(),
                tenant, intents.get(0).replace(" ", "%20")+","+intents.get(1)).replace(" ", "%20");
        String[] targetArray = intents.toArray(new String[intents.size()]);
        given()
                .urlEncodingEnabled(false).
        when()
                .get(url).
        then()
                .statusCode(200)
                .body("size()", is(intents.size()))
                .body("intent", hasItems(targetArray));
    }

    @When("^I send only (.*) for (.*) tenant then response code is 404$")
    public void checkListOfAnswers(String intent, String tenant){
        String url = String.format(Endpoints.TIE_ANSWERS_LIST, ConfigManager.getEnv(),
                tenant, intent);
        when()
                .get(url).
        then()
                .statusCode(404);
    }

    @When("^I want to get all categories for (.*) response has status 200$")
    public void getAllCategories(String tenant){
        String url = String.format(Endpoints.TIE_ANSWER_BY_CATEGORY_URL, ConfigManager.getEnv(),
                tenant, "all");
        when()
                .get(url).
        then()
                .statusCode(200);
    }

    @When("^I want to get all answers of (.*) category for (.*) response has status 200$")
    public void getAllCategoryAnswer(String category, String tenant){
        String url = String.format(Endpoints.TIE_ANSWER_BY_CATEGORY_URL, ConfigManager.getEnv(),
                tenant, category);
        when()
                .get(url).
        then()
                .statusCode(200)
                .body("size()", greaterThan(0))
                .body("category", everyItem(equalTo(category)));
    }

    // ======================= TIE trainings ======================== //

    @When("^I want to get trainings for (.*) (?:tenant|tenants) response status should be 200 and body is not empty$")
    public void getAllTrainings(String tenant){
        String url = String.format(Endpoints.BASE_TIE_URL, ConfigManager.getEnv())+
                String.format(Endpoints.TIE_TRAININGS, tenant);
        when()
                .get(url).
        then()
                .log().all()
                .statusCode(200)
                .body("isEmpty()", is(false));
    }

    @When("^I create new tenant with TIE API$")
    public void createNewTenant(){
        String newTenantName = createNewTenantName();
        NEW_TENANT_NAMES.put(Thread.currentThread().getId(), newTenantName);
        given()
                .log().all()
                .body("tenant="+newTenantName+"").
        when()
                .put(String.format(Endpoints.BASE_TIE_URL, ConfigManager.getEnv()))
        .then()
                .statusCode(200);
//                .body(contains(newTenantName));
    }

    @When("^I try to create tenant with the same ame I should receive 404 response code$")
    public void createDuplicatedTenant(){
        given()
                .log().all()
                .body("tenant="+NEW_TENANT_NAMES.get(Thread.currentThread().getId())+"").
        when()
                .put(String.format(Endpoints.BASE_TIE_URL, ConfigManager.getEnv()))
                .then()
                .statusCode(404);
    }

    @When("^I create a clone of (.*) tenant with TIE API$")
    public void createNewTenantClone(String sourceTenant){
        String newTenantName = createNewTenantName();
        NEW_TENANT_NAMES.put(Thread.currentThread().getId(), newTenantName);
        given()
                .log().all()
                .body("tenant="+newTenantName+"&source_tenant="+sourceTenant+"").
        when()
                .put(String.format(Endpoints.BASE_TIE_URL, ConfigManager.getEnv()))
        .then()
                .statusCode(200);
    }


    @When("^Wait for a minute$")
    public void waitForAMinute(){
        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @When("^I make request to see tenant config I receive response with tenant's config$")
    public void makeGetTenantConfigVerification(){
        String newTenant = NEW_TENANT_NAMES.get(Thread.currentThread().getId());
        when()
                .get(String.format(Endpoints.BASE_TIE_URL, ConfigManager.getEnv())+
                            String.format(Endpoints.TIE_CONFIG, newTenant)).
        then()
                .statusCode(200)
                .body("tenant", equalTo(newTenant));
    }

    @When("^I add (.*) field (.*) value to the new tenant config$")
    public void updateConfig(String field, String value){
        String newTenant = NEW_TENANT_NAMES.get(Thread.currentThread().getId());
        given()
                .body("{\""+field+"\":\""+value+"\"}").
        when()
                .post(String.format(Endpoints.BASE_TIE_URL, ConfigManager.getEnv())+
                        String.format(Endpoints.TIE_CONFIG, newTenant)).
        then()
                .statusCode(200);
    }

    @Then("^New (.*) field with (.*) value is added to tenant config$")
    public void verifyAddingNewItemToConfig(String field, String value){
        String newTenant = NEW_TENANT_NAMES.get(Thread.currentThread().getId());
        when()
                .get(String.format(Endpoints.BASE_TIE_URL, ConfigManager.getEnv())+
                        String.format(Endpoints.TIE_CONFIG, newTenant)).
        then()
                .statusCode(200)
                .body("tenant", equalTo(newTenant))
                .body(field, equalTo(value));
    }

    @Then("^(.*) field with (.*) value is removed from tenant config$")
    public void verifyRemovingItemFromConfig(String field, String value){
        String newTenant = NEW_TENANT_NAMES.get(Thread.currentThread().getId());
        when()
                .get(String.format(Endpoints.BASE_TIE_URL, ConfigManager.getEnv())+
                        String.format(Endpoints.TIE_CONFIG, newTenant)).
        then()
                .statusCode(200)
                .body("tenant", equalTo(newTenant))
                .body("$", not(hasKey(field)));
    }

    @Then("^I receives response on my input (.*)$")
    public void verifyNewCreatedTenantResponds(String userMessage){
        String tenant = NEW_TENANT_NAMES.get(Thread.currentThread().getId());
        SoftAssert soft = new SoftAssert();
        Response resp = RestAssured.get(URLs.getTieURL(tenant, userMessage));
        soft.assertTrue(resp.statusCode()==200,
                "Status code is not '200' when trying to get intents on message '"+userMessage+"' for newly created "+tenant+ " tenant" );
        soft.assertTrue(!resp.getBody().asString().isEmpty(),
                "Body is empty  when trying to get intents on message '"+userMessage+"' for newly created "+tenant+ " tenant");
        soft.assertAll();
    }

    @Then("^Config of cloned intent is the same as for (.*)")
    public void verifyClonedTenantResponds(String sourceTenant){
        Response sourceTenantResp = get(String.format(Endpoints.BASE_TIE_URL, ConfigManager.getEnv())+
                String.format(Endpoints.TIE_CONFIG, sourceTenant));
        Response resp = get(String.format(Endpoints.BASE_TIE_URL, ConfigManager.getEnv())+
                String.format(Endpoints.TIE_CONFIG, NEW_TENANT_NAMES.get(Thread.currentThread().getId())));
        JsonPath json = resp.getBody().jsonPath();
        JsonPath jsonSource = sourceTenantResp.getBody().jsonPath();

        Assert.assertTrue(json.get("stopwords_file").equals(jsonSource.get("stopwords_file"))&
                json.get("normalizer_features").equals(jsonSource.get("normalizer_features"))&
                json.get("fields").equals(jsonSource.get("fields"))&
                json.get("excluded_types").equals(jsonSource.get("excluded_types"))&
                json.get("separate_models").equals(jsonSource.get("separate_models"))&
                json.get("add_synonyms").equals(jsonSource.get("add_synonyms"))&
                json.get("trusted_types").equals(jsonSource.get("trusted_types"))&
                json.get("intent_confidence_threshold").equals(jsonSource.get("intent_confidence_threshold")),
        "Config of source tenant was not applied to the new one."
        );
    }

    @When("^I delete created tenant$")
    public void deleteTenant(){
        String url = String.format(Endpoints.BASE_TIE_URL, ConfigManager.getEnv())+
                String.format(Endpoints.TIE_DELETE_TENANT, NEW_TENANT_NAMES.get(Thread.currentThread().getId()));
        when()
                .delete(url).
        then()
                .statusCode(200);
    }

    @Then("^I am not receiving the response for this tenant on (.*)$")
    public void verifyTenantDeleted(String userMessage){
        SoftAssert soft = new SoftAssert();
        Response resp = RestAssured.get(URLs.getTieURL(NEW_TENANT_NAMES.get(Thread.currentThread().getId()), userMessage));
        soft.assertTrue(resp.statusCode()!=200);
        soft.assertAll();
    }

    @Then("I clear tenant data")
    public void clearTenantData(){
        String url = String.format(Endpoints.BASE_TIE_URL, ConfigManager.getEnv())+
                String.format(Endpoints.TIE_CLEARING_CONFIGS, NEW_TENANT_NAMES.get(Thread.currentThread().getId()));
        given().log().all()
                .header("Content-Type", "multipart/form-data")
                .header("Cache-Control", "no-cache").
        when()
                .post(url).
        then()
                .statusCode(200);
    }
}
