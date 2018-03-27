package steps.tie_steps;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import driverManager.URLs;
import io.restassured.RestAssured;
import io.restassured.path.json.exception.JsonPathException;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class BaseTieSteps {

    @Then("^TIE sentiment is (.*) when I send '(.*)' for (.*) tenant$")
    public void verifyTIESentimentVerdict(String expectedSentiment, String userMessage, String tenant) {
        Response resp = RestAssured.get(URLs.getTieURL(tenant, userMessage));
        String actualSentiment = resp.getBody().jsonPath().get("sentiment_verdict");
        if(expectedSentiment.toLowerCase().contains("or")){
            List<String> sentiments = Arrays.asList(expectedSentiment.toLowerCase().split(" or "));
            Assert.assertTrue(actualSentiment.equalsIgnoreCase(sentiments.get(0))||actualSentiment.equalsIgnoreCase(sentiments.get(1)),
            "Sentiment for \""+userMessage+"\" message is not as expected: '"+expectedSentiment+"'. But found: "+actualSentiment+"");
        } else{
            Assert.assertEquals(actualSentiment, expectedSentiment,
                    "Sentiment for \""+userMessage+"\" message is not as expected");
        }
    }

    @Then("^TIE returns (.*) intent: \"(.*)\" on '(.*)' for (.*) tenant$")
    public void verifyConnectAgentIntent(int numberOfIntents, String expectedIntent, String userMessage, String tenant){
        Response resp = RestAssured.get(URLs.getTieURL(tenant, userMessage));
        if (resp.getBody().asString().contains("502 Bad Gateway")) {
            Assert.assertTrue(false, "TIE is down." + resp.getBody().asString());
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
        if (resp.getBody().asString().contains("502 Bad Gateway")) {
            Assert.assertTrue(false, "TIE is down." + resp.getBody().asString());
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
}
