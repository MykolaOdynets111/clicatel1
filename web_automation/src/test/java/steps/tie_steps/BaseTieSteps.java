package steps.tie_steps;

import cucumber.api.java.en.When;
import driverManager.URLs;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;

public class BaseTieSteps {

    @When("^TIE sentiment is (.*) when I send '(.*)' for (.*) tenant$")
    public void verifyTIESentimentVerdict(String expectedSentiment, String userMessage, String tenant) {
        Response resp = RestAssured.get(URLs.getTieURL(tenant, userMessage));
        String actualSentiment = resp.getBody().jsonPath().get("sentiment_verdict");
        Assert.assertEquals(actualSentiment, expectedSentiment,
                "Sentiment for \""+userMessage+"\" message is not as expected");
    }
}
