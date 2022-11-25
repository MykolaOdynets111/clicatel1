package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import steps.unitysteps.AbstractUnitySteps;
import restHandler.RestHandlerChatHub;

import static abstractclasses.IntegrationsAbstractSteps.getIntegrationsPage;

public class IntegrationSteps extends AbstractUnitySteps {

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

    @Given("User is able to execute GET provider API")
    public void userIsAbleToExecuteGETProviderAPI() {
        RestHandlerChatHub RH = new RestHandlerChatHub();
        RH.get("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ7XCJpZFwiOlwiMzFhZTQ1ODQ5MWVlNDhkNjhiNDVhMmVlNzMyNDlkNTJcIixcImlzQWRtaW5cIjpmYWxzZX0iLCJleHAiOjE2NzA2MTM1OTQsImlhdCI6MTY2OTQwMzk5NH0.acduce65og8Jg13qt5gL-qt1rVscIgg_tcSgdMz6PLg","https://demo-chathub-config-manager.int-eks-dev.shared-dev.eu-west-1.aws.clickatell.com/api/providers");
    }
}
