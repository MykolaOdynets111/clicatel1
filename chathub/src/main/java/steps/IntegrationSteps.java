package steps;

import clients.Endpoints;
import datamanager.Credentials;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import org.junit.Assert;
import pojoClasses.Providers.GetProviders;
import restHandler.repo.Authentication;
import steps.unitysteps.AbstractUnitySteps;
import restHandler.RestHandler_ChatHub;

import static abstractclasses.IntegrationsAbstractSteps.getIntegrationsPage;

public class IntegrationSteps extends AbstractUnitySteps {

    RestHandler_ChatHub rh = new RestHandler_ChatHub();
    Authentication auth = new Authentication();
    Endpoints baseapi = new Endpoints();
    Credentials creds;

    @And("I click on Zendesk Integrations Card")
    public void openIntegrationsCard() {
        getIntegrationsPage().clickOnZendeskSupport();
    }

    @Then("Number of available integrations is displayed on integrations card")
    public void numberOfIntegrationsIsDisplayed() {
        Assert.assertTrue(getIntegrationsPage().availableIntegrationsDisplayed());
    }

    @Then("Integrations card is displayed as first card")
    public void integrationsCardIsDisplayedAsFirstCard() {
        String firstCard = getIntegrationsPage().integrationsIsFirstCard();
        Assert.assertEquals("Integrations", firstCard,
                "Integrations is First Card");
    }

    @Given("User is able to GET providers in API response")
    public void GETProviderAPI() {
        String token = auth.getAuthToken(String.format("%s/auth/accounts",baseapi.getUnityURL()), creds.Demo_Chat2PayUser.getUsername(), creds.Demo_Chat2PayUser.getPassword());
        Response response= rh.executeGetProviders(token, String.format("%s/admin/providers",baseapi.getbaseUrl()));
        response.then().assertThat().statusCode(200);

        GetProviders[] getProviders = null;
        // Performing deserialization
        getProviders = response.getBody().as(GetProviders[].class);

        //Move it to step definiation
        org.testng.Assert.assertNotNull(getProviders[0].getId());
        org.testng.Assert.assertEquals(getProviders[0].getName(), "Zendesk Support");
    }
}
