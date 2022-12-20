package steps.unitysteps;

import io.cucumber.java.en.And;

public class ProductsAndServicesSteps extends AbstractUnitySteps{

    @And("I open Integrations product")
    public void openIntegrationsProduct(){

        getProductsAndServicesPage().clickOnIntegrations();
    }
}
