package steps.tiesteps;

import apihelper.ApiHelperTie;
import apihelper.Endpoints;
import com.github.javafaker.Faker;
import datamanager.Tenants;
import datamanager.jacksonschemas.Intent;
import datamanager.jacksonschemas.tie.*;
import datetimeutils.DateTimeHelper;
import driverfactory.URLs;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.path.json.exception.JsonPathException;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class TIEApiSteps {

    Faker faker = new Faker();

    private static Map<Long, String> NEW_TENANT_NAMES = new ConcurrentHashMap<>();

    public static Map<Long, String> getNewTenantNames() {
        return NEW_TENANT_NAMES;
    }

    public static void clearTenantNames(){
        NEW_TENANT_NAMES.clear();
    }

    private static Map mapForCreatedIntent = new HashMap();

    private static String createNewTenantName() {
        Faker faker = new Faker();
        return "testing"+ Thread.currentThread().getId() + "_" + System.currentTimeMillis();
    }

    private static TieNERItem NER_DATA_SET = createNERDataSet();

    private CreateSlotBody createSlotBody;
    private SlotInTieResponse expectedSlotInTieResponse;
    private static List<String> createdSlotIds =new ArrayList<>();

    public static Map getMapForCreatedIntent(){
        return  mapForCreatedIntent;
    }

    // ======================= Chats ======================== //

    @When("^I send \"(.*)\" for (.*) tenant then response code is 200 and intents are not empty$")
    //API: GET /tenants/<tenant_name>/chats/?q=<user input>
    public void checkResponseStatusForIntentOnlyRequest(String userMessage, String tenant){
        if(tenant.contains("new")){
            tenant =  NEW_TENANT_NAMES.get(Thread.currentThread().getId());
        }
        String url = String.format(Endpoints.TIE_INTENT_WITHOUT_SENTIMENT_URL, tenant, userMessage);
        when()
                .get(url).
                then()
                .statusCode(200)
                .body("intents_result.intents", hasSize(greaterThan(0)));
    }

    @When("^I send \"(.*)\" for (.*) tenant then response code is 200$")
    //API: GET /tenants/<tenant_name>/chats/?q=<user input>
    public void checkResponseStatus(String userMessage, String tenant){
        if(tenant.contains("new")){
            tenant =  NEW_TENANT_NAMES.get(Thread.currentThread().getId());
        }
        String url = String.format(Endpoints.TIE_INTENT_WITHOUT_SENTIMENT_URL, tenant, userMessage);
        when()
                .get(url).
                then()
                .statusCode(200);
    }

    @When("^I send \"(.*)\" for (.*) tenant including tie_sentiment then response code is 200 and intents are not empty$")
    //API: GET /tenants/<tenant_name>/chats/?q=<user input>&tie_sentiment=True
    public void checkResponseStatusForIntentWIthTieSentimentOnlyRequest(String userMessage, String tenant){
        String url = String.format(Endpoints.TIE_INTENT_WITH_TIE_SENTIMENT_URL, tenant, userMessage);
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
        String url = String.format(Endpoints.TIE_INTENT_SPECIFYING_SENTIMENT_URL, tenant, intentText);
        when()
                .get(url).
                then()
                .statusCode(200)
                .body("intents_result.intents", hasSize(greaterThan(0)));
    }

    @When("^I add to (.*) intent (.*) sample text for created tenant status code is 200$")
    public void addSampleTextForIntent(String intent, String sampleText){
        String url = String.format(Endpoints.TIE_ADDING_INTENT_SAMPLE_TEXT_TO_TRAINING,
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
        String url = String.format(Endpoints.TIE_SENTIMENTS, tenant, userMessage);
        when()
                .get(url).
                then()
                .statusCode(200)
                .body("text", equalTo(userMessage))
                .body("sentiment_score", notNullValue());
//                .body("tie_sentiment_score", notNullValue());
    }


    // ======================= CreatedIntent Answers ======================== //

    @When("^I send (.*) for (.*) tenant then response code is 200 and list of answers is shown$")
    public void checkListOfAnswers(List<String> intents, String tenant){
        String url = String.format(Endpoints.TIE_ANSWERS_LIST, tenant, intents.get(0).replace(" ", "%20")+","+intents.get(1)).replace(" ", "%20");
        String[] targetArray = intents.toArray(new String[intents.size()]);
        given()
                .urlEncodingEnabled(false).
                when()
                .get(url).
                then()
                .statusCode(200)
                .body("data.size()", is(intents.size()))
                .body("data.intent", hasItems(targetArray));
    }

    @When("^I send only (.*) for (.*) tenant then response code is 404$")
    public void checkListOfAnswers(String intent, String tenant){
        String url = String.format(Endpoints.TIE_ANSWERS_LIST, tenant, intent);
        when()
                .get(url).
                then()
                .statusCode(400);
    }

    @When("^I want to get all categories for (.*) response has status 200$")
    public void getAllCategories(String tenant){
        String url = String.format(Endpoints.TIE_ANSWER_BY_CATEGORY_URL, tenant, "all");
        when()
                .get(url).
                then()
                .statusCode(200);
    }

    @When("^I want to get all answers of (.*) category for (.*) response has status 200$")
    public void getAllCategoryAnswer(String category, String tenant){
        String url = String.format(Endpoints.TIE_ANSWER_BY_CATEGORY_URL, tenant, category);
        Response resp = get(url);
        List<String> returnedListOfCategories = resp.getBody().jsonPath().getList("data.category");
        returnedListOfCategories.removeIf(Objects::isNull);

        SoftAssert soft = new SoftAssert();

        soft.assertEquals(resp.statusCode(), 200,
                "Status code is not 200 after trying to get all answers for '"+tenant+"' tenant, category'"+category+"'\n"+resp.getBody().asString());
        soft.assertTrue(returnedListOfCategories.stream().allMatch(e -> e.equals(category)),
                "Not all categories in returned answers are as expected: '"+category+"'\n"+resp.getBody().asString());
    }

    @When("^I Create new mapping for intent-answer pare: (.*)$")
    public void createIntentAnswerTraining(List<String> info){
        Response resp = RestAssured.put(formURLForCreatingNewIntentAnswer(info));
        waitFor(5000);
        Assert.assertTrue(resp.statusCode()==200, "" +
                "Creating new intent was not successfull\n "
                +resp.getBody().asString());
    }


    @Then("^I am not able to create duplicated intent: (.*)")
    public void verifyCreatingDuplicatedIntent(List<String> info){
        SoftAssert soft = new SoftAssert();
        Response resp = put(formURLForCreatingNewIntentAnswer(info));
        soft.assertEquals(resp.statusCode(), 404,
                "Status code is not 404 after trying to create duplicated intent\n");
        soft.assertEquals(resp.getBody().jsonPath().get("error"), "Such intent already exists",
                "Body does not contain expected message about already created intent\n"+
                        resp.getBody().asString());
        soft.assertAll();
    }

    private String formURLForCreatingNewIntentAnswer(List<String> info){
        String creatingURL = Endpoints.TIE_BASE_INTENT_ANSWER_CREATING;
        String tenantID = NEW_TENANT_NAMES.get(Thread.currentThread().getId());
        switch (info.size()){
            case 1:
                creatingURL = String.format(creatingURL, tenantID, info.get(0));
                break;
            case 2:
                creatingURL = String.format(creatingURL, tenantID, info.get(0)) + "&answer="+info.get(1);
                break;
            case 3:
                creatingURL = String.format(creatingURL, tenantID, info.get(0))+ "&answer="+info.get(1)+"&answer_url="+info.get(2);
                break;
            case 4:
                creatingURL = String.format(creatingURL, tenantID, info.get(0))+ "&answer="+info.get(1)+"&answer_url="+info.get(2)+"&category="+info.get(3);
                break;
            case 5:
                creatingURL = String.format(creatingURL, tenantID, info.get(0))+ "&answer="+info.get(1)+"&answer_url="+info.get(2)+"&category="+info.get(3)+"&type="+info.get(4);
                break;
        }
        return creatingURL;
    }


    @Then("^Intent (.*) with the following details: (.*) is created$")
    public void verifyIntentInfo(String intent, List<String> info){
        SoftAssert soft = new SoftAssert();
        String tenantID = NEW_TENANT_NAMES.get(Thread.currentThread().getId());
        String url = String.format(Endpoints.TIE_ANSWER_URL, tenantID, intent);
        Response resp = RestAssured.get(url);
        try {
            if (info.size()==1) {
                Assert.assertEquals(resp.getBody().jsonPath().get("title"), info.get(0),
                        "Title of created intent is not as expected");
            } else {
                soft.assertEquals(resp.getBody().jsonPath().get("title"), info.get(0),
                        "Title of created intent is not as expected");
                soft.assertEquals(resp.getBody().jsonPath().get("text"), info.get(1),
                        "Answer of created intent is not as expected");
                soft.assertEquals(resp.getBody().jsonPath().get("url"), info.get(2),
                        "Url of created intent is not as expected");
                soft.assertEquals(resp.getBody().jsonPath().get("category"), info.get(3),
                        "Category of created intent is not as expected");
                soft.assertEquals(resp.getBody().jsonPath().get("type"), info.get(4));
                soft.assertAll();
            }
        } catch (JsonPathException e){
            Assert.assertTrue(false, "JSON response is not as expected. For created intent "+intent+"\n"+
                    resp.getBody().asString());
        }
    }

    @When("^I update (.*) intent's (?:answer and URL|answer) to (.*)$")
    public void updateIntentAnswer(String intent, List<String> newIntentInfo){
        String tenantID = NEW_TENANT_NAMES.get(Thread.currentThread().getId());
        if(newIntentInfo.size()==1) {
            String url = String.format(Endpoints.TIE_ANSWER_URL, tenantID, intent) + "&answer=" + newIntentInfo.get(0) + "";
            when().
                    post(url).
                    then().
                    statusCode(200);
        }else{
            String url = String.format(Endpoints.TIE_ANSWER_URL, tenantID, intent) + "&answer=" + newIntentInfo.get(0) + "&answer_url="+newIntentInfo.get(1)+"";
            when().
                    post(url).
                    then().
                    statusCode(200);
        }
    }


    @When("^404 status code for updating not existed intent$")
    public void updateIntentAnswerAndURL(){
        String tenantID = NEW_TENANT_NAMES.get(Thread.currentThread().getId());
        String url = String.format(Endpoints.TIE_ANSWER_URL, tenantID, "notexisted_intent")+"&answer=newAnswer";
        when().
                post(url).
                then().
                statusCode(404);
    }

    @When("^I delete created intent (.*)$")
    public void deleteIntent(String intent){
        String tenantID = NEW_TENANT_NAMES.get(Thread.currentThread().getId());
        String url = String.format(Endpoints.TIE_ANSWER_URL, tenantID, intent);
        when().
                delete(url).
                then().
                statusCode(200);
        waitFor(4000);
    }

    @When("^Intent (.*) is deleted$")
    public void verifyIntentDeleted(String intent){
        String tenantID = NEW_TENANT_NAMES.get(Thread.currentThread().getId());
        String url = String.format(Endpoints.TIE_ANSWER_URL, tenantID, intent);
        when().
                get(url).
                then().
                statusCode(404);
    }
    // ======================= tie trainings ======================== //

    @When("^I want to get trainings for (.*) (?:tenant|tenants) response status should be 200 and body is not empty$")
    public void getAllTrainings(String tenant){
        Response resp;
        SoftAssert soft = new SoftAssert();
        if(tenant.equalsIgnoreCase("existed")){
            String urlToGetAllTenants = String.format(Endpoints.TIE_TRAININGS, "all");
            Response respToGetExistedTenant = get(urlToGetAllTenants);
            Map<String, String> trainings = respToGetExistedTenant.getBody().jsonPath().getMap("train");
            String existedTenants = null;
            try {
                existedTenants = new ArrayList<>(trainings.keySet()).get(0);
            } catch(IndexOutOfBoundsException e){
                Assert.assertTrue(false, "There is no existed tie training scheduled." +
                        " \nResponse:" +respToGetExistedTenant.getBody().asString()+"");
            }
            String url = String.format(Endpoints.TIE_TRAININGS, existedTenants);
            resp = get(url);
        } else{
            String url = String.format(Endpoints.TIE_TRAININGS, tenant);
            resp = get(url);
        }

        soft.assertEquals(resp.statusCode(), 200,
                "Status code for getting training is not 200\n" +resp.getBody().asString()+"");
        soft.assertFalse(resp.getBody().asString().equals("{}"),
                "Response body on getting training is empty\n" +resp.getBody().asString()+"");
        soft.assertEquals(resp.getBody().jsonPath().get("error"), "",
                "Response body on getting training contains error\n" +resp.getBody().asString()+"");
        soft.assertAll();
    }


    @Then("^All trainings should contain newly added tenant training$")
    public void verifyTenantAddedToTrainings(){
        String tenant = NEW_TENANT_NAMES.get(Thread.currentThread().getId());
        String url = String.format(Endpoints.TIE_TRAININGS,"all");
        when()
                .get(url).
                then()
                .statusCode(200)
                .body("train."+ tenant, equalTo("scheduled"));
    }

    @Then("^Training for new tenant is scheduled$")
    public void verifyTenantTrainingScheduled(){
        String tenant = NEW_TENANT_NAMES.get(Thread.currentThread().getId());
        String url = String.format(Endpoints.TIE_TRAININGS, tenant);
        when()
                .get(url).
                then()
                .statusCode(200)
                .body("train." + tenant, equalTo("scheduled"));
    }

    @When("^I schedule training for a new tenant$")
    public void scheduleTenantTraining(){
        String tenant = NEW_TENANT_NAMES.get(Thread.currentThread().getId());
        String url = String.format(Endpoints.TIE_TRAININGS, tenant);
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
        Response resp = given()
                .log().all()
                .body("tenant="+newTenantName+"")
                .put(URLs.getBaseTieURL());
        Assert.assertEquals(resp.statusCode(), 200,
                "Status code is not 200 after creating "+newTenantName+" tenant\n"+resp.prettyPrint()+"");

    }

    @When("^I make a request with '(.*)' user input and '(.*)' type for (.*) tenant then response contains 1 correct intent: (.*)$")
    public void verifyAppropriateModelUsing(String userInput, String modelType, String tenant, String intent){
        String url = String.format(Endpoints.TIE_INTENT_WITH_TIE_TYPE_URL, tenant, userInput) + modelType;
        Response resp = get(url);
        SoftAssert soft = new SoftAssert();
        soft.assertEquals(resp.getBody().jsonPath().getList("intents_result.intents").size(), 1,
                "More than 1 intent is returned in the response for the direct model '"+modelType+"' on user message '"+userInput+"' for '"+tenant+"' tenant" +
                        "\n"+resp.getBody().asString()+"\n");
        soft.assertEquals(resp.getBody().jsonPath().get("intents_result.intents[0].type"), modelType,
                "Incorrect model type is returned in the response for direct model '"+modelType+"' on user message '"+userInput+"' for '"+tenant+"' tenant" +
                        "\n"+resp.getBody().asString()+"\n");
        soft.assertEquals(resp.getBody().jsonPath().get("intents_result.intents[0].intent"), intent,
                "Incorrect intent is returned in the response for direct model '"+modelType+"' on user message '"+userInput+"' for '"+tenant+"' tenant" +
                        "\n"+resp.getBody().asString()+"\n");
        soft.assertAll();
    }

    @When("^I make a request with '(.*)' user input and '(.*)' type for (.*) tenant then response contains list of intents and does not contain '(.*)' intent")
    public void verifyNorRelatedModelUsing(String userInput, String modelType, String tenant, String intent){
        String url = String.format(Endpoints.TIE_INTENT_WITH_TIE_TYPE_URL, tenant, userInput) + modelType;
        Response resp = get(url);
        List intents = resp.getBody().jsonPath().getList("intents_result.intents");
        if(!intents.isEmpty()){
            Assert.assertFalse(intents.contains(intent),
                    "CreatedIntent from another model is returned in the response for not related model '"+modelType+"' on user message '"+userInput+"' for '"+tenant+"' tenant" +
                            "\n"+resp.getBody().asString()+"\n");
        }
    }


    @When("^I try to create tenant with the same name I should receive 404 response code$")
    public void createDuplicatedTenant(){
        given()
                .log().all()
                .body("tenant="+NEW_TENANT_NAMES.get(Thread.currentThread().getId())+"").
                when()
                .put(URLs.getBaseTieURL())
                .then()
                .statusCode(404);
    }

    @When("^I create a clone of (.*) tenant with TIE API$")
    public void createNewTenantClone(String sourceTenant){
        String newTenantName = createNewTenantName();
        NEW_TENANT_NAMES.put(Thread.currentThread().getId(), newTenantName);
        Response resp = given()
                .log().all()
                .body("tenant="+newTenantName+"&source_tenant="+sourceTenant+"")
                .put(URLs.getBaseTieURL());
        Assert.assertEquals(resp.statusCode(), 200,
                "Status code is not 200 after cloning "+sourceTenant+" tenant\n"+resp.prettyPrint()+"");
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
        String url = String.format(Endpoints.TIE_GET_TRAINSET, tenant);
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
                .get(String.format(Endpoints.TIE_CONFIG, newTenant)).
                then()
                .statusCode(200)
                .body("tenant", equalTo(newTenant));
    }

    @When("^I add (.*) field (.*) value to the new tenant config$")
    public void updateConfig(String field, String value){
        String newTenant = NEW_TENANT_NAMES.get(Thread.currentThread().getId());
        given().header("Content-Type", "application/json").log().all()
                .body("{\""+field+"\":\""+value+"\"}").
                when()
                .post(String.format(Endpoints.TIE_CONFIG, newTenant)).
                then()
                .statusCode(200);
    }

    @When("^Status code is 404 when I add (.*) field (.*) value to the new tenant config$")
    public void invalidUpdateConfig(String field, String value){
        String newTenant = NEW_TENANT_NAMES.get(Thread.currentThread().getId());
        given().log().all()
                .body("{\""+field+"\":\""+value+"\"}").
                when()
                .post(String.format(Endpoints.TIE_CONFIG, newTenant)).
                then()
                .statusCode(404);
    }

    @Then("^(.*) field with (.*) value is added to tenant config$")
    public void verifyAddingNewItemToConfig(String field, String value){
        SoftAssert soft = new SoftAssert();
        String newTenant = NEW_TENANT_NAMES.get(Thread.currentThread().getId());
        Response resp = null;
        boolean updateResult=false;
        for (int i=0; i<40 ; i++){
            resp = get(String.format(Endpoints.TIE_CONFIG, newTenant));
            if (resp.statusCode()!= 200) Assert.assertTrue(false, "Response status code is not 200\n"+resp.getBody().asString());
            if (resp.getBody().jsonPath().get(field).toString().equals(value)){
                updateResult=true;
                break;
            } else{
                waitFor(500);
                updateResult=false;
            }
        }
        soft.assertEquals(resp.statusCode(), 200,
                "Status code is not 200 after getting configs for tenant "+newTenant+"");
        soft.assertEquals(resp.getBody().jsonPath().get("tenant"), newTenant,
                "Tenant is missing in configs\n" +resp.getBody().asString()+"");
        soft.assertTrue(updateResult,
                "Config \""+field+ "\" with \""+value+"\" value is not added for "+newTenant+" tenant\n"+resp.getBody().asString());
        soft.assertAll();

    }

    @When("^I send test trainset for newly created tenant status code is 200$")
    public void addTrainingSetForNewTenant(){
        String newTenant = NEW_TENANT_NAMES.get(Thread.currentThread().getId());
        Response resp =
                given().header("Content-Type", "application/json").log().all()
                        .body("{\"category\":\"touch button\",\"text\":\"HO-HO-HO\",\"intent\":\"SANTA\",\"type\":\"AQA\", \"tags\":[\"AQA\"]}").
                        when()
                        .post(String.format(Endpoints.TIE_POST_TRAINSET, newTenant));
        SoftAssert soft = new SoftAssert();
        soft.assertEquals(resp.statusCode(), 200,
                "Status code is not 200 after adding new trainset \n" + resp.getBody().asString());
        waitFor(2500);
        soft.assertAll();
    }

    @Then("^Trainset is added for newly created tenant$")
    public void checkTrainsetIsAddedForNewTenant(){
        String newTenant = NEW_TENANT_NAMES.get(Thread.currentThread().getId());
        waitFor(1000);
        when()
                .get(String.format(Endpoints.TIE_GET_TRAINSET, newTenant)).
                then()
                .statusCode(200)
                .body("intent_trainset.tenant[0]", equalTo(newTenant))
                .body("intent_trainset.intent[0]", equalTo("SANTA"))
                .body("intent_trainset.text[0]", equalTo("HO-HO-HO"));
    }

    @Then("^(.*) field with (.*) value is removed from tenant config$")
    public void verifyRemovingItemFromConfig(String field, String value){
        boolean isFieldPresent;
        SoftAssert soft = new SoftAssert();
        String newTenant = NEW_TENANT_NAMES.get(Thread.currentThread().getId());
        Response resp = when()
                .get(String.format(Endpoints.TIE_CONFIG, newTenant));
        try {
            resp.body().jsonPath().get(field);
            isFieldPresent = true;
        } catch (NullPointerException e){
            isFieldPresent = false;
        }
        soft.assertEquals(resp.statusCode(), 200,
                "Status code is not 200 after trying to clear "+newTenant+" tenant configs");
        soft.assertEquals(resp.getBody().jsonPath().get("tenant"), newTenant,
                "Tenant "+newTenant+" is not shown in the response after trying to delete tenant configs\n"+resp.getBody().asString());
        soft.assertFalse(isFieldPresent,
                "Config "+field+ " is not cleared for "+newTenant+" tenant\n"+resp.getBody().asString());
        soft.assertAll();
    }

    @Then("^Added trainset is removed$")
    public void checkTrainsetIsRemoved(){
        String newTenant = NEW_TENANT_NAMES.get(Thread.currentThread().getId());
        String url = String.format(Endpoints.TIE_GET_TRAINSET, newTenant);
        when()
                .get(url).
                then()
                .statusCode(200)
                .body("intent_trainset", everyItem(isEmptyOrNullString()));
    }

    @Then("^I receives response on my input (.*)$")
    public void verifyNewCreatedTenantResponds(String userMessage){
        String tenantName = NEW_TENANT_NAMES.get(Thread.currentThread().getId());
        SoftAssert soft = new SoftAssert();
        Response resp = RestAssured.get(URLs.getTieURL(tenantName, userMessage));
        soft.assertTrue(resp.statusCode()==200,
                "Status code is not '200' when trying to get intents on message '"+userMessage+"' for newly created "+tenantName+ " tenant" );
        soft.assertTrue(!resp.getBody().asString().isEmpty(),
                "Body is empty  when trying to get intents on message '"+userMessage+"' for newly created "+tenantName+ " tenant");
        soft.assertAll();
    }

    @Then("^Config of cloned intent is the same as for (.*)")
    public void verifyClonedTenantResponds(String sourceTenant){

        Response sourceTenantResp = get(String.format(Endpoints.TIE_CONFIG, sourceTenant));
        Response resp = get(String.format(Endpoints.TIE_CONFIG, NEW_TENANT_NAMES.get(Thread.currentThread().getId())));
        try{
            JsonPath json = resp.getBody().jsonPath();
            JsonPath jsonSource = sourceTenantResp.getBody().jsonPath();
            if(json==null || jsonSource==null){
                Assert.assertTrue(false, "JSON response after geting training config is missing.\n" +
                        "Response for getting config of newly created tenant: "+resp.getBody().asString()+"\n"+
                        "Response for getting config of source tenant: " +sourceTenantResp.getBody().asString()+"");
            } else{
                Assert.assertTrue(json.get("stopwords_file").equals(jsonSource.get("stopwords_file"))&
                                json.get("normalizer_features").equals(jsonSource.get("normalizer_features"))&
                                json.get("fields").equals(jsonSource.get("fields"))&
                                json.get("excluded_types").equals(jsonSource.get("excluded_types"))&
                                json.get("separate_models").equals(jsonSource.get("separate_models"))&
                                json.get("add_synonyms").equals(jsonSource.get("add_synonyms"))&
                                json.get("trusted_types").equals(jsonSource.get("trusted_types"))&
                                json.get("intent_confidence_threshold").equals(jsonSource.get("intent_confidence_threshold")),
                        "Config of source tenant was not applied to the new one.");
            }
        } catch(JsonPathException e){
            Assert.assertTrue(false, "invalid JSON response. New Tetant: "+NEW_TENANT_NAMES.get(Thread.currentThread().getId())+"\n"
                    +sourceTenantResp.getBody().asString()+" original tenant tie response \n" +
                    resp.getBody().asString()+" created tenant tie response"
            );
        }
    }

    @When("^I delete created tenant$")
    public void deleteTenant(){
        String url = String.format(Endpoints.TIE_DELETE_TENANT, NEW_TENANT_NAMES.get(Thread.currentThread().getId()));
        when()
                .delete(url).
                then()
                .statusCode(200);
    }

    @Then("^I am not receiving the response for this tenant on (.*)$")
    public void verifyTenantDeleted(String userMessage){
        SoftAssert soft = new SoftAssert();
        Response resp = RestAssured.get(URLs.getTieURL(NEW_TENANT_NAMES.get(Thread.currentThread().getId()), userMessage));
        soft.assertTrue(resp.statusCode()!= 200,
                "Status code is not 200 after GET request on deleted tenant"+resp.getBody().asString());
        soft.assertAll();
    }

    @Then("I clear tenant data")
    public void clearTenantData(){
        String url = String.format(Endpoints.TIE_CLEARING_CONFIGS, NEW_TENANT_NAMES.get(Thread.currentThread().getId()));
        Response resp = given().log().all().
                urlEncodingEnabled(false).
                when()
                .post(url);
        Assert.assertEquals(resp.getStatusCode(), 200,
                "Status code is not 200.\nResponse body: "+resp.getBody().asString()+"\nUrl: "+url+"");

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
        String newTenant = NEW_TENANT_NAMES.get(Thread.currentThread().getId());
        given()
                .body("{\"NER_trainset\": ["+NER_DATA_SET.toString()+"]}").
                when()
                .post(String.format(Endpoints.TIE_NER,newTenant)).
                then()
                .statusCode(200);
    }

    @Then("^GET request should return created trainset$")
    public void getNERDataSet(){
        String newTenant = NEW_TENANT_NAMES.get(Thread.currentThread().getId());
        when()
                .get(String.format(Endpoints.TIE_NER,newTenant)).
                then()
                .statusCode(200)
                .body("NER_trainset.text", hasItems(NER_DATA_SET.getText()));
    }

    @When("^Trying to delete a trainset status code is 200$")
    public void deleteNER(){
        String newTenant = NEW_TENANT_NAMES.get(Thread.currentThread().getId());
        String id = (String) ((HashMap) get(String.format(Endpoints.TIE_NER,newTenant))
                .jsonPath().getList("NER_trainset").stream()
                .filter(e -> ((HashMap) e).get("text").equals(NER_DATA_SET.getText()))
                .findFirst().get()).get("id");
        String url = String.format(Endpoints.TIE_NER_DELETE, newTenant, id);
        when()
                .delete(url).
                then()
                .statusCode(200);
    }

    @Then("Trainset should be deleted")
    public void trainsetIsDeleted(){
        String newTenant = NEW_TENANT_NAMES.get(Thread.currentThread().getId());
        when()
                .get(String.format(Endpoints.TIE_NER,newTenant)).
                then()
                .statusCode(200)
                .body("NER_trainset.text", not(hasItems(NER_DATA_SET.getText())));
    }

    public static List<Map> getListOfNERs(){
        return get(Endpoints.TIE_NER).jsonPath().getList("NER_trainset");
    }

    private void waitFor(int wait){
        try {
            Thread.sleep(wait);
        } catch (InterruptedException e) {
            // Nothing to do
        }
    }

    // ============================ SEMANTIC ============================ //
    @When("^I make post request with semantic candidates$")
    public void verifyPostSemantic(){
        String newTenant = NEW_TENANT_NAMES.get(Thread.currentThread().getId());
        SoftAssert soft = new SoftAssert();
        Response resp1 = given().header("Content-Type", "application/json").log().all()
                .body("{\"semantic_candidates\": [\"semantic\", \"test\"]}")
                .post(String.format(Endpoints.TIE_POST_SEMANTIC,newTenant, "semantic"));
        Response resp2 = given().header("Content-Type", "text/plain").log().all()
                .body("{\"semantic_candidates\": [\"semantic\", \"test\"]}")
                .post(String.format(Endpoints.TIE_POST_SEMANTIC,newTenant, "semantic"));

        soft.assertEquals(resp1.getBody().jsonPath().get("intents_result.intents.intent[0]"), "semantic",
                "CreatedIntent in the response is not as expected after using application/json Content Type\n"+resp1.getBody().asString());
        soft.assertEquals(resp2.getBody().jsonPath().get("intents_result.intents.intent[0]"), "semantic",
                "CreatedIntent in the response is not as expected after using text/plain Content Type\n"+resp2.getBody().asString());
        soft.assertAll();
    }


    // ============================ USER  INPUT============================ //

    @When("^I make user statistic request it returns empty response$")
    public void verifyEmptyUserInputCall(){
        String newTenant = NEW_TENANT_NAMES.get(Thread.currentThread().getId());
        String url =  String.format(Endpoints.TIE_USER_INPUT, newTenant);
        Response resp = get(url);
        Assert.assertTrue(resp.getBody().jsonPath().getList("data").isEmpty(),
                "User info API returns not empty body for just created tenant\n"+resp.getBody().asString());
    }


    @When("^User statistic call returns '(.*)' user's inputs$")
    public void verifyUserInfo(List<String> expectedUserInputs){
        String newTenant = NEW_TENANT_NAMES.get(Thread.currentThread().getId());
        String url =  String.format(Endpoints.TIE_USER_INPUT, newTenant);
        Response resp = get(url);
        List<String> userInputs = resp.getBody().jsonPath().getList("data.user_input");
        SoftAssert soft = new SoftAssert();
        for (int i=0; i<60; i++){
            if(userInputs.size()!=expectedUserInputs.size()){
                waitFor(500);
                resp = get(url);
                try {
                    userInputs = resp.getBody().jsonPath().getList("data.user_input");
                } catch(JsonPathException e){ }
            }else{break;}
        }
        for (String expectedInput : expectedUserInputs){
            soft.assertTrue(userInputs.contains(expectedInput),
                    "Api did not return '"+expectedInput+"' expected user input\n"+resp.getBody().asString());
        }
        soft.assertAll();
    }

    @When("^I filter by (.*) only records after the date is returned$")
    public void verifyByDateFiltering(String dateFilter){
        String newTenant = NEW_TENANT_NAMES.get(Thread.currentThread().getId());
        String targetDate = getListOfUserInPutElements().get(0).get("date");
        targetDate = targetDate.substring(0, targetDate.indexOf("+"));
        LocalDateTime targetDateTime = LocalDateTime.parse(targetDate);

        String url =  String.format(Endpoints.TIE_USER_INPUT, newTenant)+"?"+dateFilter+"="+targetDate;
        Response resp = get(url);
        List<Map<String, String>> returnedInputs = resp.getBody().jsonPath().getList("data");
        List<LocalDateTime> itemsDateTimes = returnedInputs.stream().map(e -> {
            String a = e.get("date");
            a = a.substring(0,a.indexOf("+"));
            return  LocalDateTime.parse(a);
        }).collect(Collectors.toList());
        switch(dateFilter) {
            case "start_date":
                Assert.assertTrue(itemsDateTimes.stream().allMatch(e -> e.isEqual(targetDateTime)|e.isAfter(targetDateTime)),
                        "Incorrect items are shown after applying the following filters: '"+dateFilter+"="+targetDate+"'");
                break;
            case "end_date":
                Assert.assertTrue(itemsDateTimes.stream().allMatch(e -> e.isEqual(targetDateTime)|e.isBefore(targetDateTime)),
                        "Incorrect items are shown after applying the following filters: '"+dateFilter+"="+targetDate+"'");
                break;
            default:
                Assert.assertTrue(false, "Incorrect filter value is used for filtering by date");
        }
    }

    @When("^I filter by '(.*)' text only records with appropriate user input text are shown$")
    public void verifyFilteringByText(String text){
        String newTenant = NEW_TENANT_NAMES.get(Thread.currentThread().getId());
        String url =  String.format(Endpoints.TIE_USER_INPUT, newTenant)+"?text="+text;
        String urlForBaseResponse = String.format(Endpoints.TIE_USER_INPUT, newTenant);
        Response resp = get(url);
        Response respWithOuFilterring = get(urlForBaseResponse);
        List<String> userInputs = resp.getBody().jsonPath().getList("data.user_input");
        SoftAssert soft = new SoftAssert();
        soft.assertEquals(userInputs.size(), 1,
                "Incorrect number of user input in the response are shown after filtering by text '"+text+"'\nurl:" + url + "\n"+resp.getBody().asString()
                        +"\n response without filterring:\n" + respWithOuFilterring.getBody().asString());
        soft.assertTrue(userInputs.stream().allMatch(e -> e.contains(text)),
                "Unexpected user inputs are shown after filtering by text '"+text+"'\n" + url + "\nurl:"+resp.getBody().asString()+
                        "\n response without filterring:\n" + respWithOuFilterring.getBody().asString());
        soft.assertAll();
    }

    private List<Map<String, String>> getListOfUserInPutElements(){
        String newTenant = NEW_TENANT_NAMES.get(Thread.currentThread().getId());
        String url =  String.format(Endpoints.TIE_USER_INPUT, newTenant);
        return get(url).getBody().jsonPath().getList("data");
    }

    @When("^I filter by '(.*)' (.*) text only records with appropriate user input text are shown$")
    public void filterByConfidence(String filterType, Float filterValue){
        String newTenant = NEW_TENANT_NAMES.get(Thread.currentThread().getId());
        String url =  String.format(Endpoints.TIE_USER_INPUT, newTenant)+"?"+filterType+"="+filterValue;
        Response resp = get(url);
        List<Float> userInputsConfidence = resp.getBody().jsonPath().getList("data.top_confidence");
        switch (filterType){
            case "max_top_conf":
                Assert.assertTrue(userInputsConfidence.stream().allMatch(e-> e<=filterValue),
                        "Incorrect values after filtering by confidence are shown.\n" +
                                "Expected: '"+filterType+"' = "+filterValue +"\n" +
                                "Found: " + resp.getBody().asString());
                break;
            case "min_top_conf":
                Assert.assertTrue(userInputsConfidence.stream().allMatch(e-> e>=filterValue),
                        "Incorrect values after filtering by confidence are shown.\n" +
                                "Expected: '"+filterType+"' = "+filterValue +"\n" +
                                "Found: " + resp.getBody().asString());
                break;
            default:
                Assert.assertTrue(false, "Unsupported filter name by confidence provided:'"+filterType+"'");
        }
    }


    @When("^I apply (.*) sorting then all elements are correctly sorted$")
    public void verifyUserInputSorting(String sortCriteria){
        String newTenant = NEW_TENANT_NAMES.get(Thread.currentThread().getId());
        String url =  String.format(Endpoints.TIE_USER_INPUT, newTenant)+"?sort="+sortCriteria;
        Response resp = get(url);
        List<Float> valuesInTheResponse = get(url).jsonPath().getList("data.top_confidence");
//                                                .stream().map(e -> ((Float) e)).collect(Collectors.toList());
        ArrayList<Float> sortedList = new ArrayList<Float>(valuesInTheResponse);
        switch (sortCriteria){
            case "asc":
                Collections.sort(sortedList);
                Assert.assertTrue(valuesInTheResponse.equals(sortedList),
                        "Incorrect values after sorting by '"+sortCriteria+"' are shown.\n" +
                                "Expected: '"+sortedList +"\n" +
                                "Found: " + resp.getBody().asString());
                break;
            case "desc":
                sortedList.sort(Collections.reverseOrder());
                Assert.assertTrue(valuesInTheResponse.equals(sortedList),
                        "Incorrect values after sorting by '"+sortCriteria+"'  are shown.\n" +
                                "Expected: '"+ sortedList +"\n" +
                                "Found: " + resp.getBody().asString());
                break;
            default:
                Assert.assertTrue(false, "Unsupported sorting criteria provided:'"+sortCriteria+"'");
        }
    }

    @When("^I apply pagination (.*) (?:from|to) (.*) correct response is shown$")
    public void verifyPagination(String paginationDirection, int boundary){
        List<Map<String, String>> allValues =  getListOfUserInPutElements();
        String newTenant = NEW_TENANT_NAMES.get(Thread.currentThread().getId());
        String url =  String.format(Endpoints.TIE_USER_INPUT, newTenant)+"?"+paginationDirection+"="+boundary;
        List<Map<String, String>> actualResults = get(url).getBody().jsonPath().getList("data");
        List<Map<String, String>> expectedResults;
        switch(paginationDirection){
            case "start":
                expectedResults = allValues.subList(boundary, allValues.size());
                Assert.assertEquals(actualResults, expectedResults);
                break;
            case "end":
                expectedResults = allValues.subList(0, boundary);
                Assert.assertEquals(actualResults, expectedResults);
                break;
        }
    }

    @When("^Create new intent for (.*) tenant$")
    public void createNewIntent(String tenantOrgName){
        Tenants.setTenantUnderTestNames(tenantOrgName);
        ApiHelperTie.deleteAllIntents();
        formDataForIntentCreationTest();
        Response resp = ApiHelperTie.createNewIntent(Tenants.getTenantUnderTestOrgName(),
                (String) mapForCreatedIntent.get("intent"),
                (String) mapForCreatedIntent.get("category"),
                (String) mapForCreatedIntent.get("answer"),
                (String) mapForCreatedIntent.get("type"));
        mapForCreatedIntent.put("intent_id", resp.getBody().jsonPath().get("id"));
        Assert.assertTrue(resp.statusCode() == 200,
                "Creating new intent for '" + tenantOrgName + "' intent was not successful\n"+
                        "resp status code: " + resp.statusCode() + "\n" +
                        "resp body: " + resp.getBody().asString());
    }

    private Map formDataForIntentCreationTest(){
        String book = faker.book().title().replaceAll("\\p{Punct}", "");
        mapForCreatedIntent.put("raw_intent", book);
        mapForCreatedIntent.put("intent", book.toLowerCase());
        mapForCreatedIntent.put("answer", "With our goods you may always expect the high quality of paper and ink. " +
                "For more info please schedule a call with out manager.");
        mapForCreatedIntent.put("type", "faq");
        mapForCreatedIntent.put("category", "faq");

        return mapForCreatedIntent;
    }

    @When("^New intent is created$")
    public void verifyNewIntentAdding(){
        waitFor(500);
        List<CreatedIntent> allIntents = ApiHelperTie.getAllIntents().getBody().jsonPath().getList("",
                CreatedIntent.class);
        Assert.assertTrue(allIntents.stream().anyMatch(e -> e.getIntent()
                        .equals(mapForCreatedIntent.get("intent").toString())),
                "Created '"+ mapForCreatedIntent.get("intent")+"'intent is not returned");
    }

    @When("^Adding a few samples for created intent$")
    public void addNewSamples(){
        SoftAssert soft = new SoftAssert();
        List<String> samples = Arrays.asList("How much costs '"+mapForCreatedIntent.get("intent")+"' book?",
                "Do you provide any discounts if I but 500 of '"+mapForCreatedIntent.get("intent")+ "' books?'");
        List<String> sampleIds = new ArrayList<>();
        mapForCreatedIntent.put("samples", samples);
        for (String sample : samples) {
            Response resp = ApiHelperTie.addNewSample(Tenants.getTenantUnderTestOrgName(),
                    (String) mapForCreatedIntent.get("intent"), sample);
            sampleIds.add(resp.getBody().jsonPath().get("id"));
            soft.assertTrue(resp.statusCode()==200,
                    "Adding '"+sample+"' sample for '" + mapForCreatedIntent.get("intent")+ "' was not successful\n" +
                            "status code: " + resp.statusCode() + "\n" +
                            "resp body: " + resp.statusCode());
        }
        mapForCreatedIntent.put("sample_ids", sampleIds);
        soft.assertAll();
    }

    @When("^Samples are saved$")
    public void verifyAddedIntentIsSaved(){
        List samplesOnIntent = ApiHelperTie.getTrainData().getBody().jsonPath().getList("data")
                .stream()
                .map(e -> ((List) e))
                .filter(e1 -> e1.contains(mapForCreatedIntent.get("intent")))
                .collect(Collectors.toList());
        Assert.assertTrue( ((List) mapForCreatedIntent.get("samples")).stream().allMatch(e -> samplesOnIntent.toString().contains(e.toString())),
                "Created samples are not returned \n" +
                        "mapForCreatedIntent: " +  mapForCreatedIntent.toString());
    }

    @When("^Schedule new training$")
    public void scheduleNewTraining(){
        Response resp = ApiHelperTie.scheduleTraining();
        Assert.assertEquals(resp.statusCode(), 200, "Training is not scheduled\n" +
                "Resp: " + resp.getBody().asString());
    }

    @Then("^New model is ready after (.*) minutes wait$")
    public void getModels(int minutes){
        boolean isTrained = false;
        String createdModelName = getExpectedModelName();
        int timeout = (minutes*60)/15;
        Response resp = null;
        for(int i = 0; i < timeout; i++){
            if(!isTrained){
                waitFor(15000);
                try {
                    resp = ApiHelperTie.getModels();
                    isTrained = resp.getBody().jsonPath().getList("intent")
                            .stream().map(e -> (Map) e)
                            .filter(e -> e.get("name").equals(createdModelName))
                            .anyMatch(e -> e.get("status").equals("finished"));
                }catch (JsonPathException e){
                    Assert.fail("Unable to get trained models \n" + "resp: status code " + resp.statusCode() + "\n"
                            + "resp body: " + resp.getBody().asString());
                }
            } else{
                break;
            }
        }
        mapForCreatedIntent.put("model", createdModelName);
        Assert.assertTrue(isTrained, "New model is not trained\n" +
                ApiHelperTie.getModels().getBody().asString());
    }

    public String getExpectedModelName(){
        LocalDateTime now = LocalDateTime.now(ZoneId.of("UTC"));
        String createdModelName = "";
        ResponseBody respBody = ApiHelperTie.getModels().getBody();
        for(int i = 0; i < 30; i++){
            if(!createdModelName.equals("")) break;
            else {
                int incrementor = i;
                respBody = ApiHelperTie.getModels().getBody();
                createdModelName = respBody.jsonPath().getList("intent")
                        .stream().map(e -> (Map) e).map(e -> (String) e.get("name"))
                        .filter(e -> DateTimeHelper.convertLocalDateTimeToMillis(getModelDateTime(e), ZoneId.of("UTC"))
                                >
                                DateTimeHelper.convertLocalDateTimeToMillis(now.minusMinutes(3+incrementor), ZoneId.of("UTC")))
                        .findFirst().orElse("");
            }
        }
        if(createdModelName.equals("")){
            Assert.fail("Expected created model '" + createdModelName + "' is not present in get models response\n" +
                    "Resp: " + respBody.asString());
        }
        return createdModelName;
    }

    @When("^I publish new model$")
    public void publishNewModel(){
        Assert.assertEquals(ApiHelperTie.publishModel((String) mapForCreatedIntent.get("model")).statusCode(), 200, "Publishing '" + mapForCreatedIntent.get("model") + "' model was not successful");
    }

    private LocalDateTime getModelDateTime(String model){
        //model_20190315-131248 - model name example
        String modelDate = model.split("_")[1];
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");
        return LocalDateTime.parse(modelDate, formatter);
    }

    @Then("^Tie returns new intent$")
    public void verifyNewIntent(){
        waitForAMinute();
        System.out.println("Verifying new intent");
        String sample = (String) ((List) mapForCreatedIntent.get("samples")).get(0);
        String expectedIntent = (String) mapForCreatedIntent.get("intent");

        boolean result = false;
        Response resp = null;
        List<String> actualIntents = new ArrayList<>();
        for (int i = 0; i<30; i++){
            if(!result){
                waitFor(200);
                resp =ApiHelperTie.getRespWithIntentsOnUserMessageWithAutorization(sample);
                actualIntents = resp.jsonPath().getList("intents_result.intents", Intent.class).stream()
                        .sorted(Comparator.comparing(Intent::getConfidence).reversed())
                        .map(e -> e.getIntent())
                        .collect(Collectors.toList());
                result = actualIntents.contains(expectedIntent);
            }

        }

        Assert.assertTrue(result,
                "Expected '"+expectedIntent+"' intent is not returned on the sample '" + sample +"'\n" +
                        "Resp body: " + resp.getBody().asString() + "\n" +
                        "Published model: " + mapForCreatedIntent.get("model"));
    }


    @When("^I create (.*) type slot for \"(.*)\" intent of (.*) tenant$")
    public void createNewSlot(String type, String intent, String tenantOrgName){
        formNewSlotValues(intent, type, null);
        Response resp = ApiHelperTie.createNewSlot(createSlotBody);
        Assert.assertEquals(resp.statusCode(), 200, "Creating new slot was not successful \n" +
                "Create slot body: " + createSlotBody.toString());
        createdSlotIds.add(resp.getBody().jsonPath().get("id"));
    }

    private void formNewSlotValues(String intent, String type, String expectedValue){
        switch (type){
            case "MONEY":
                createSlotBody = new CreateSlotBody().setIntent(intent).setName("Money slot").setEntity_type("MONEY")
                        .setPrompt("Hey, automation test is working.").setConfirm("Let's go!")
                        .setTenant(Tenants.getTenantUnderTestName());
                break;
            case "DATE":
                createSlotBody = new CreateSlotBody().setIntent(intent).setName("DATE slot").setEntity_type("DATE")
                        .setPrompt("Hey, automation test is working.").setConfirm("Let's go!")
                        .setTenant(Tenants.getTenantUnderTestName());
                break;
        }
        expectedSlotInTieResponse = new SlotInTieResponse().setPrompt(createSlotBody.getPrompt()).setName(createSlotBody.getName())
                .setValue(expectedValue).setConfirm(createSlotBody.getConfirm());
    }

    @When("^Created slot is saved$")
    public void verifySlotIsSaved(){
        String allSlotsInfo = ApiHelperTie.getAllSlots().getBody().asString();
        boolean isSlotAdded = false;
        for(int i=0; i<4; i++){
            if(allSlotsInfo.contains(createdSlotIds.get(0))){
                isSlotAdded = true;
                break;
            } else{
                waitFor(1000);
                allSlotsInfo = ApiHelperTie.getAllSlots().getBody().asString();
            }
        }
        Assert.assertTrue(isSlotAdded, "Slot is not added after 4 secs wait\n"+
                "Slot info: " + createSlotBody.toString() + "\n" +
                "Created slot id: " + createdSlotIds.toString());
    }

    @Given("All slots for (.*) tenant are cleared")
    public void deleteAllSlots(String tenantOrgName){
        Tenants.setTenantUnderTestNames(tenantOrgName);
        List<String> ids = ApiHelperTie.getAllSlots().getBody().
                jsonPath().getList("data")
                .stream().map(e -> (ArrayList<String>) e)
                .map(e -> e.get(5))
                .collect(Collectors.toList());
        for(String id : ids){
            ApiHelperTie.deleteSlot(id);
        }
    }

    @Then("^New slot is returned in TIE response on (.*) message$")
    public void verifySlotReturnedInTieResponse(String userMessage){
        Response resp = RestAssured.get(URLs.getTieURL(Tenants.getTenantUnderTestName(), userMessage));
        List<SlotInTieResponse> slotInTieResponse = resp.getBody().jsonPath().getList("intents_result.slots", SlotInTieResponse.class);
        Assert.assertTrue(slotInTieResponse.contains(expectedSlotInTieResponse),
                "Expected slot is not returned in tie response on '"+userMessage+"' user message \n" +
                        "Created Slot info: " + createSlotBody.toString() + "\n" +
                        "Created slot id: " + createdSlotIds.toString() + "\n" +
                        "Received resp from tie" + resp.getBody().asString()
        );
    }

    @When("I update slot")
    public void updateSlot(){
        formNewSlotValues("trading hours", "DATE", "Monday");
        ApiHelperTie.updateSlot(createSlotBody, createdSlotIds.get(0));
    }

    @Then("^Slot for \"(.*)\" message is not returning anymore$")
    public void verifySlotRemoved(String userMessage){
        Response resp = RestAssured.get(URLs.getTieURL(Tenants.getTenantUnderTestName(), userMessage));
        List<SlotInTieResponse> slotInTieResponse = resp.getBody().jsonPath().getList("intents_result.slots", SlotInTieResponse.class);
        Assert.assertFalse(slotInTieResponse.contains(expectedSlotInTieResponse),
                "Expected slot is not removed in tie response on '"+userMessage+"' user message \n" +
                        "Created Slot info: " + createSlotBody.toString() + "\n" +
                        "Created slot id: " + createdSlotIds.toString() + "\n" +
                        "Received resp from tie" + resp.getBody().asString()
        );
    }

    @Then("^I delete slot$")
    public void deleteCreatedSlots() {
        for (String slotId : createdSlotIds) {
            ApiHelperTie.deleteSlot(slotId);
        }
    }

    public static void clearCreatedIntentAndSample(){
        List<String> sampleIds = (List<String>) mapForCreatedIntent.get("sample_ids");
        if(sampleIds!=null) {
            for (String sampleId : sampleIds) {
                ApiHelperTie.deleteSample(sampleId);
            }
        }
        ApiHelperTie.deleteAllIntents();
        mapForCreatedIntent.clear();
        ApiHelperTie.deleteAllModels();
    }

    public static void clearCreatedSlots(){
        for(String slotId : createdSlotIds){
            ApiHelperTie.deleteSlot(slotId);
        }
        createdSlotIds.clear();
    }


}
