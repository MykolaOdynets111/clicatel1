package steps.tiesteps;

import apihelper.ApiHelperTie;
import apihelper.Endpoints;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import datamanager.Tenants;
import datamanager.jacksonschemas.tie.CreateSlotBody;
import datamanager.jacksonschemas.tie.SlotInTieResponse;
import drivermanager.URLs;
import io.restassured.RestAssured;
import io.restassured.path.json.exception.JsonPathException;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.io.ByteArrayOutputStream;
import java.util.*;
import java.util.stream.Collectors;

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
        Tenants.setTenantUnderTestNames(tenant);
        Response resp = RestAssured.get(URLs.getTieURL(Tenants.getTenantUnderTestName(), userMessage));
        if (resp.getBody().asString().contains("502 Bad Gateway")||!(resp.statusCode()==200)) {
            Assert.assertTrue(false, "tie is not responding. \n" + resp.getBody().asString());
        }
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
        Tenants.setTenantUnderTestNames(tenant);
        Response resp = RestAssured.get(URLs.getTieURL(Tenants.getTenantUnderTestName(), userMessage));
        if (resp.getBody().asString().contains("502 Bad Gateway")||!(resp.statusCode()==200)) {
            Assert.assertTrue(false, "tie is not responding. \n" + resp.getBody().asString());
        }
        try {
            List<HashMap<String, String>> intentsList = resp.getBody().jsonPath().get("intents_result.intents");
            try {
                String intent = intentsList.get(0).get("intent");

                SoftAssert soft = new SoftAssert();
                soft.assertEquals(intentsList.size(), numberOfIntents,
                        "Number of intents for '" + userMessage + "' user message is not as expected");
                soft.assertEquals(intent, expectedIntent, "Intent in tie response is not as expected");
                soft.assertAll();
            } catch (IndexOutOfBoundsException e) {
                Assert.assertTrue(false, "There are no intents at all in tie response for '" + userMessage + "' user message");
            }
        } catch (JsonPathException e) {
            Assert.assertTrue(false, "Failed parsing JSON response. The response body: "+ resp.getBody().asString());
        }
    }


    @Then("TIE response on '(.*)' for (.*) tenant contains '(.*)' intent")
    public void verifyTieResponseContainExpectedIntent(String userMessage,String tenant, String expectedIntent){
        Tenants.setTenantUnderTestNames(tenant);
        Response resp = RestAssured.get(URLs.getTieURL(Tenants.getTenantUnderTestName(), userMessage));
        if (resp.getBody().asString().contains("502 Bad Gateway")||!(resp.statusCode()==200)) {
            Assert.assertTrue(false, "tie is not responding. \n" + resp.getBody().asString());
        }
        try {
            List<String> intentsList = resp.getBody().jsonPath().getList("intents_result.intents.intent");
            Assert.assertTrue(intentsList.contains(expectedIntent),
                    "tie response does not contain '"+expectedIntent+"' expected intent on '"+userMessage+"' user message\n"
                                +resp.getBody().asString());
        } catch (JsonPathException e) {
            Assert.assertTrue(false, "Failed parsing JSON response. The response body: "+ resp.getBody().asString());
        }
    }

    @Then("^TIE response should have correct top intent: \"(.*)\" on '(.*)' for (.*) tenant$")
    public void verifyIntent(String expectedIntent, String userMessage, String tenant){
        Tenants.setTenantUnderTestNames(tenant);
        Response resp = RestAssured.get(URLs.getTieURL(Tenants.getTenantUnderTestName(), userMessage));
        if (resp.getBody().asString().contains("502 Bad Gateway")||!(resp.statusCode()==200)) {
            Assert.assertTrue(false, "tie is not responding. \n" + resp.getBody().asString());
        }
        try {
            List<HashMap<String, String>> intentsList = resp.getBody().jsonPath().get("intents_result.intents");
            try {
                String intent = intentsList.get(0).get("intent");

                SoftAssert soft = new SoftAssert();
                soft.assertEquals(intent, expectedIntent, "Intent in tie response is not as expected");
                soft.assertAll();
            } catch (IndexOutOfBoundsException e) {
                Assert.assertTrue(false, "There are no intents at all in tie response for '" + userMessage + "' user message");
            }
        } catch (JsonPathException e) {
            Assert.assertTrue(false, "Failed parsing JSON response. The response body: "+ resp.getBody().asString());
        }
    }

    @When("^If I send a \"(.*)\" to (.*) tenant TIE should return \"(.*)\" entity$")
    public void verifyTieEntity(String message, String tenant, String expectedEntity){
        Response resp = RestAssured.get(String.format(Endpoints.TIE_CHAT_URL, tenant)+message+"&sentiment=true&entity=true");
        if (resp.getBody().asString().contains("502 Bad Gateway")||!(resp.statusCode()==200)) {
            Assert.assertTrue(false, "tie is not responding. \n" + resp.getBody().asString());
        }
        Map<String, String> entities = resp.jsonPath().getMap("intents_result.entities");
        Assert.assertTrue(entities.size()==1 && entities.containsKey(expectedEntity),
                "Actual entity is not as expected.\n Expected "+expectedEntity+", but found "+entities.keySet()+"");

    }

    @Then("^TIE returns (.*) intents: \"(.*)\" on '(.*)' for (.*) tenant$")
    public void verifyIntentsList(int numberOfIntents, List<String> expectedIntents, String userMessage, String tenant){
        Tenants.setTenantUnderTestNames(tenant);
        Response resp = RestAssured.get(URLs.getTieURL(Tenants.getTenantUnderTestName(), userMessage));
        if (resp.getBody().asString().contains("502 Bad Gateway")||!(resp.statusCode()==200)) {
            Assert.assertTrue(false, "tie is not responding. \n" + resp.getBody().asString());
        }
        try {
            List<HashMap<String, String>> intentsMap = resp.getBody().jsonPath().get("intents_result.intents");
            try {

                List<String> intents = intentsMap.stream().map(e -> e.get("intent")).collect(Collectors.toList());
                Collections.sort(intents);
                Collections.sort(expectedIntents);
                SoftAssert soft = new SoftAssert();
                soft.assertEquals(intents.size(), numberOfIntents,
                        "Number of intents for '" + userMessage + "' user message is not as expected");
                soft.assertEquals(intents, expectedIntents, "Intent in tie response is not as expected");
                soft.assertAll();
            } catch (IndexOutOfBoundsException e) {
                Assert.assertTrue(false, "There are no intents at all in tie response for '" + userMessage + "' user message");
            }
        } catch (JsonPathException e) {
            Assert.assertTrue(false, "Failed parsing JSON response. The response body: "+ resp.getBody().asString());
        }
    }

    @Then("^TIE returns (.*) answer for (.*) tenant (.*) intent$")
    public void verifyTieAnswer(String expectedAnswer, String tenant, String intent){
        Assert.assertEquals(ApiHelperTie.getExpectedMessageOnIntent(tenant, intent), expectedAnswer,
                "From tie answer on \""+intent+"\" intent is not as expected\n");
    }


}
