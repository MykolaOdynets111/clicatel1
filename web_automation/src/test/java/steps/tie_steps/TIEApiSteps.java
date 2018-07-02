package steps.tie_steps;

import api_helper.Endpoints;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import dataprovider.jackson_schemas.TIE.TieNERItem;
import dataprovider.jackson_schemas.TIE.Entity;

import driverManager.ConfigManager;
import driverManager.URLs;
import static io.restassured.RestAssured.*;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.path.json.exception.JsonPathException;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.hamcrest.Matchers.*;

public class TIEApiSteps {

    private static Map<Long, String> NEW_TENANT_NAMES = new ConcurrentHashMap<>();

    public static Map<Long, String> getNewTenantNames() {
        return NEW_TENANT_NAMES;
    }

    private static String createNewTenantName() {
        return "testing"+ System.currentTimeMillis();
    }

    private static TieNERItem NER_DATA_SET = createNERDataSet();

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
                .body("sentiment_score", notNullValue());
//                .body("tie_sentiment_score", notNullValue());
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
        if(tenant.equalsIgnoreCase("existed")){
            String urlToGetAllTenants = String.format(Endpoints.BASE_TIE_URL, ConfigManager.getEnv())+
                    String.format(Endpoints.TIE_TRAININGS, "all");
            Response resp = get(urlToGetAllTenants);
            Map<String, String> trainings = resp.getBody().jsonPath().getMap("");
            String existedTenants = new ArrayList<>(trainings.keySet()).get(0);

            String url = String.format(Endpoints.BASE_TIE_URL, ConfigManager.getEnv())+
                    String.format(Endpoints.TIE_TRAININGS, existedTenants);
            when()
                    .get(url).
            then()
                    .log().all()
                    .statusCode(200)
                    .body("isEmpty()", is(false));
        } else{
        String url = String.format(Endpoints.BASE_TIE_URL, ConfigManager.getEnv())+
                String.format(Endpoints.TIE_TRAININGS, tenant);
        when()
                .get(url).
        then()
                .log().all()
                .statusCode(200)
                .body("isEmpty()", is(false));
        }
    }


    @Then("^All trainings should contain newly added tenant training$")
    public void verifyTenantAddedToTrainings(){
        String tenant = NEW_TENANT_NAMES.get(Thread.currentThread().getId());
        String url = String.format(Endpoints.BASE_TIE_URL, ConfigManager.getEnv())+
                String.format(Endpoints.TIE_TRAININGS,"all");
        when()
                .get(url).
        then()
                .statusCode(200)
                .body(tenant, equalTo("scheduled"));
    }

    @Then("^Training for new tenant is scheduled$")
    public void verifyTenantTrainingScheduled(){
        String tenant = NEW_TENANT_NAMES.get(Thread.currentThread().getId());
        String url = String.format(Endpoints.BASE_TIE_URL, ConfigManager.getEnv())+
                String.format(Endpoints.TIE_TRAININGS, tenant);
        when()
                .get(url).
        then()
                .statusCode(200)
                .body(tenant, equalTo("scheduled"));
    }

    @When("^I schedule training for a new tenant$")
    public void scheduleTenantTraining(){
        String tenant = NEW_TENANT_NAMES.get(Thread.currentThread().getId());
        String url = String.format(Endpoints.BASE_TIE_URL, ConfigManager.getEnv())+
                String.format(Endpoints.TIE_TRAININGS, tenant);
        given().log().all().
        when()
                .post(url).
        then()
                .statusCode(200);
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

    @When("^I try to create tenant with the same name I should receive 404 response code$")
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
            Thread.sleep(70000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // ======================= Data set and config management  ======================== //

    @When("^I make a request to see (.*) trainset I receive response with 200 code and not empty body$")
    public void getTenantTrainset(String tenant){
        SoftAssert soft = new SoftAssert();
        String url = String.format(Endpoints.BASE_TIE_URL, ConfigManager.getEnv())+
                String.format(Endpoints.TIE_GET_TRAINSET, tenant);
        Response resp = RestAssured.get(url);
        soft.assertTrue(resp.statusCode()==200,
                "Status code is not '200' when trying to get trainset for '"+tenant+ "' tenant" );
        soft.assertTrue(!resp.getBody().asString().isEmpty(),
                "Body is empty  when trying to get trainset for '"+tenant+ "' tenant");
        soft.assertAll();
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

    @When("^I send test trainset for newly created tenant status code is 200$")
    public void addTrainingSetForNewTenant(){
        String newTenant = NEW_TENANT_NAMES.get(Thread.currentThread().getId());
        given()
                .body("{\"rasa_nlu_data\": {\"entity_examples\": [], \"intent_examples\": [{\"category\":\"touch button\",\"text\":\"HO-HO-HO\",\"intent\":\"SANTA\"}]}}").
        when()
                .post(String.format(Endpoints.BASE_TIE_URL, ConfigManager.getEnv())+
                        String.format(Endpoints.TIE_POST_TRAINSET, newTenant)).
        then()
                .statusCode(200);
        waitFor(2500);
    }

    @Then("^Trainset is added for newly created tenant$")
    public void checkTrainsetIsAddedForNewTennant(){
        String newTenant = NEW_TENANT_NAMES.get(Thread.currentThread().getId());
        waitFor(1000);
        given()
                .body("{\"rasa_nlu_data\": {\"entity_examples\": [], \"intent_examples\": [{\"category\":\"touch button\",\"text\":\"HO-HO-HO\",\"intent\":\"SANTA\"}]}}").
        when()
                .get(String.format(Endpoints.BASE_TIE_URL, ConfigManager.getEnv())+
                        String.format(Endpoints.TIE_GET_TRAINSET, newTenant)).
        then()
                .statusCode(200)
                .body("intent_trainset.tenant[0]", equalTo(newTenant))
                .body("intent_trainset.intent[0]", equalTo("SANTA"));
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

    @Then("^Added trainset is removed$")
    public void checkTrainsetIsRemoved(){
        String newTenant = NEW_TENANT_NAMES.get(Thread.currentThread().getId());
        String url = String.format(Endpoints.BASE_TIE_URL, ConfigManager.getEnv())+
                String.format(Endpoints.TIE_GET_TRAINSET, newTenant);
       when()
                .get(url).
        then()
                .statusCode(200)
                .body("intent_trainset", everyItem(isEmptyOrNullString()));
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
        try{
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
        } catch(JsonPathException e){
            Assert.assertTrue(false, "invalid JSON response. New Tetant: "+NEW_TENANT_NAMES.get(Thread.currentThread().getId())+"\n"
                    +sourceTenantResp.getBody().asString()+" original tenant TIE response \n" +
                resp.getBody().asString()+" created tenant TIE response"
            );
        }
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
        given().log().all().
                urlEncodingEnabled(false).
        when()
                .post(url).
        then()
                .statusCode(200);
        waitFor(5000);
    }

    // ============================ NER ============================ //

    private static TieNERItem createNERDataSet(){
        List<Entity> entities = new ArrayList<>();
        entities.add(new Entity().setStart(0).setEnd(13).setType("PERSON"));
        return new TieNERItem().setText("AQA test "+System.currentTimeMillis()+"").setEntities(entities);
    }

    @When("^I try to add some trainset response status code should be 200$")
    public void addNERDataSet(){
        String url = String.format(Endpoints.BASE_TIE_URL, ConfigManager.getEnv())+ Endpoints.TIE_NER;
        given()
                .body("{\"NER_trainset\": ["+NER_DATA_SET.toString()+"]}").
        when()
                .post(url).
        then()
                .statusCode(200);
    }

    @Then("^GET request should return created trainset$")
    public void getNERDataSet(){
        String url = String.format(Endpoints.BASE_TIE_URL, ConfigManager.getEnv())+ Endpoints.TIE_NER;
        when()
                .get(url).
        then()
                .statusCode(200)
                .body("NER_trainset.text", hasItems(NER_DATA_SET.getText()));
    }

    @When("^Trying to delete a trainset status code is 200$")
    public void deleteNER(){
        String getUrl = String.format(Endpoints.BASE_TIE_URL, ConfigManager.getEnv())+ Endpoints.TIE_NER;
        String id = (String) ((HashMap) get(getUrl)
                .jsonPath().getList("NER_trainset").stream()
                .filter(e -> ((HashMap) e).get("text").equals(NER_DATA_SET.getText()))
                .findFirst().get()).get("id");
        String url = String.format(Endpoints.BASE_TIE_URL, ConfigManager.getEnv())+
                String.format(Endpoints.TIE_NER_DELETE, id);
        when()
                .delete(url).
        then()
                .statusCode(200);
    }

    @Then("Trainset should be deleted")
    public void trainsetIsDeleted(){
        String url = String.format(Endpoints.BASE_TIE_URL, ConfigManager.getEnv())+ Endpoints.TIE_NER;
        when()
                .get(url).
                then()
                .statusCode(200)
                .body("NER_trainset.text", not(hasItems(NER_DATA_SET.getText())));
    }

    public static List<Map> getListOfNERs(){
        String getUrl = String.format(Endpoints.BASE_TIE_URL, ConfigManager.getEnv())+ Endpoints.TIE_NER;

        return get(getUrl).jsonPath().getList("NER_trainset");
    }

    private void waitFor(int wait){
        try {
            Thread.sleep(wait);
        } catch (InterruptedException e) {        }
    }

}
