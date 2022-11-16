package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import org.testng.asserts.SoftAssert;
import steps.unitysteps.AbstractUnitySteps;

import static abstractclasses.IntegrationsAbstractSteps.getIntegrationsPage;

public class IntegrationSteps extends AbstractUnitySteps{

        @And("I click on Zendesk Integrations Card")
        public void openIntegrationsCard(){

            getIntegrationsPage().clickOnZendeskSupport();
        }

        @Then("Number of available integrations is displayed on integrations card")
        public void numberOfIntegrationsIsDisplayed() {

            Assert.assertTrue(getIntegrationsPage().availableIntegrationsDisplayed());
        }

        @Then("Integrations card is displayed as first card")
        public void integrationsCardIsDisplayedAsFirstCard() {

            SoftAssert soft = new SoftAssert();
            String firstCard = getIntegrationsPage().integrationsIsFirstCard();
            soft.assertEquals("Integrations", firstCard,
                "Integrations is First Card");
        }
}
