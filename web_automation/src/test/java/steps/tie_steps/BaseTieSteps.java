package steps.tie_steps;

import cucumber.api.Scenario;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import driverManager.URLs;
import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.path.json.exception.JsonPathException;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;

import static io.restassured.RestAssured.given;

public class BaseTieSteps {

    public static ByteArrayOutputStream request;
    public static ByteArrayOutputStream response;

    @Given("Listener for logging request and response is ready")
    public void logRequests(){
//        PrintStream requestVar = new PrintStream(request, true);
//        PrintStream responseVar = new PrintStream(response, true);
//        RestAssured.filters(new ResponseLoggingFilter(LogDetail.ALL, responseVar),
//                new RequestLoggingFilter(LogDetail.ALL, requestVar));
    }

    @Then("^TIE sentiment is (.*) when I send '(.*)' for (.*) tenant$")
    public void  verifyTIESentimentVerdict(String expectedSentiment, String userMessage, String tenant) {
        SoftAssert soft = new SoftAssert();
        Response resp = RestAssured.get(URLs.getTieURL(tenant, userMessage));
        if (resp.getBody().asString().contains("502 Bad Gateway")||!(resp.statusCode()==200)) {
            Assert.assertTrue(false, "TIE is not responding. \n" + resp.getBody().asString());
        }
        String actualSentiment = resp.getBody().jsonPath().get("sentiment_verdict");
        List<HashMap<String, String>> intentsList = resp.getBody().jsonPath().get("intents_result.intents");
        if(expectedSentiment.toLowerCase().contains("or")){
            List<String> sentiments = Arrays.asList(expectedSentiment.toLowerCase().split(" or "));
            soft.assertTrue(actualSentiment.equalsIgnoreCase(sentiments.get(0))||actualSentiment.equalsIgnoreCase(sentiments.get(1)),
            "Sentiment for \""+userMessage+"\" message is not as expected: '"+expectedSentiment+"'. But found: "+actualSentiment+"");
            soft.assertTrue(intentsList.size()==1, "There are more than 1 intent on '"+userMessage+"' user message");
        soft.assertAll();
        } else{
            Assert.assertEquals(actualSentiment, expectedSentiment,
                    "Sentiment for \""+userMessage+"\" message is not as expected");
        }
    }

    @Then("^TIE returns (.*) intent: \"(.*)\" on '(.*)' for (.*) tenant$")
    public void verifyConnectAgentIntent(int numberOfIntents, String expectedIntent, String userMessage, String tenant){
        Response resp = RestAssured.get(URLs.getTieURL(tenant, userMessage));
        if (resp.getBody().asString().contains("502 Bad Gateway")||!(resp.statusCode()==200)) {
            Assert.assertTrue(false, "TIE is not responding. \n" + resp.getBody().asString());
        }
        try {
            List<HashMap<String, String>> intentsList = resp.getBody().jsonPath().get("intents_result.intents");
            try {
                String intent = intentsList.get(0).get("intent");

                SoftAssert soft = new SoftAssert();
                soft.assertEquals(intentsList.size(), numberOfIntents,
                        "Number of intents for '" + userMessage + "' user message is not as expected");
                soft.assertEquals(intent, expectedIntent, "Intent in TIE response is not as expected");
                soft.assertAll();
            } catch (IndexOutOfBoundsException e) {
                Assert.assertTrue(false, "There are no intents at all in TIE response for '" + userMessage + "' user message");
            }
        } catch (JsonPathException e) {
            Assert.assertTrue(false, "Failed parsing JSON response. The response body: "+ resp.getBody().asString());
        }
    }

    @Then("^TIE response should have correct top intent: \"(.*)\" on '(.*)' for (.*) tenant$")
    public void verifyIntent(String expectedIntent, String userMessage, String tenant){
        Response resp = RestAssured.get(URLs.getTieURL(tenant, userMessage));
        if (resp.getBody().asString().contains("502 Bad Gateway")||!(resp.statusCode()==200)) {
            Assert.assertTrue(false, "TIE is not responding. \n" + resp.getBody().asString());
        }
        try {
            List<HashMap<String, String>> intentsList = resp.getBody().jsonPath().get("intents_result.intents");
            try {
                String intent = intentsList.get(0).get("intent");

                SoftAssert soft = new SoftAssert();
                soft.assertEquals(intent, expectedIntent, "Intent in TIE response is not as expected");
                soft.assertAll();
            } catch (IndexOutOfBoundsException e) {
                Assert.assertTrue(false, "There are no intents at all in TIE response for '" + userMessage + "' user message");
            }
        } catch (JsonPathException e) {
            Assert.assertTrue(false, "Failed parsing JSON response. The response body: "+ resp.getBody().asString());
        }
    }

    @When("^If I send a \"(.*)\" to (.*) tenant TIE should return \"(.*)\" entity$")
    public void verifyTieEntity(String message, String tenant, String expectedEntity){
        Response resp = RestAssured.get(URLs.getBaseTieChatURL(tenant)+message+"&sentiment=true");
        if (resp.getBody().asString().contains("502 Bad Gateway")||!(resp.statusCode()==200)) {
            Assert.assertTrue(false, "TIE is not responding. \n" + resp.getBody().asString());
        }
        Map<String, String> entities = resp.jsonPath().getMap("intents_result.entities");
        Assert.assertTrue(entities.size()==1 && entities.containsKey(expectedEntity),
                "Actual entity is not as expected.\n Expected "+expectedEntity+", but found "+entities.keySet()+"");

    }
}
