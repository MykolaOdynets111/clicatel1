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

    @And("I open My Workspace page")
    public void openMyWorkspacePage(){

        getUnityLandingPage().clickOnMyWorkspace();
    }

    @And("I open Products & Services page")
    public void openProductsAndServicesPage(){

        getUnityLandingPage().clickOnProductsAndServices();
    }


}

