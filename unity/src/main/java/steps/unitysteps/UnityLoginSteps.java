package steps.unitysteps;

import datamanager.UnityClients;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;

public class UnityLoginSteps extends AbstractUnitySteps {

    @When("I login to Unity as (.*)$")
    public void loginToUnity(String unityClient){
        UnityClients unityUser = UnityClients.valueOf(unityClient);
        AbstractUnitySteps.getLoginForUnity().loginToUnity(unityUser.getUnityClientEmail(), unityUser.getUnityClientPass());
    }

    @And("User clicks on My Workspace page link")
    public void openMyWorkspacePage(){

        getUnityLandingPage().clickOnMyWorkspace();
    }

    @And("User clicks on Products & Services page link")
    public void openProductsAndServicesPage(){

        getUnityLandingPage().clickOnProductsAndServices();
    }


}

