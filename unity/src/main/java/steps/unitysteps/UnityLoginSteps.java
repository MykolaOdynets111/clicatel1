package steps.unitysteps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import unitypages.UnityLandingPage;

public class UnityLoginSteps extends AbstractUnitySteps {

    @When("I login to Unity with email {string} and password {string}")
    public void loginToUnity(String email, String password){
       AbstractUnitySteps.getLoginForUnity().loginToUnity(email, password);
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

