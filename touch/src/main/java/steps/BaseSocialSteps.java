package steps;

import apihelper.Endpoints;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;



public class BaseSocialSteps {

    @When("^Make get request for social health check returns 200 status code$")
    public void verifySocialIsUp(){
        Response resp = RestAssured.given().header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8").when().get(Endpoints.SOCIAL_HEALTH_CHECK);
        Assert.assertEquals(resp.getStatusCode(), 200, "Social channel is down.");
    }
}
