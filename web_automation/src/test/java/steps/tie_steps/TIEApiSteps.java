package steps.tie_steps;

import api_helper.Endpoints;
import cucumber.api.java.en.When;
import driverManager.ConfigManager;
import driverManager.URLs;
import static io.restassured.RestAssured.*;
import io.restassured.matcher.RestAssuredMatchers.*;

import java.util.List;

import static org.hamcrest.Matchers.*;

public class TIEApiSteps {

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
}
