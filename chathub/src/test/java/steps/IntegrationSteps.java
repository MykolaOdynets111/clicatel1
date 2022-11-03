package steps;

import io.cucumber.java.en.And;
import steps.unitysteps.AbstractUnitySteps;

import static abstractclasses.IntegrationsAbstractSteps.getIntegrationsPage;

public class IntegrationSteps extends AbstractUnitySteps{

        @And("I click on Zendesk Integrations Card")
        public void openIntegrationsCard(){

            getIntegrationsPage().clickOnZendeskSupport();
        }

}
