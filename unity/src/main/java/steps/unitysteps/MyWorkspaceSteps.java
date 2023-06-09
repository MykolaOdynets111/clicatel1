package steps.unitysteps;

import driverfactory.DriverFactory;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.testng.asserts.SoftAssert;

public class MyWorkspaceSteps extends AbstractUnitySteps{

    @And("User clicks on Integrations Tab")
    public void clickOnIntegrationsTab(){

        getMyWorkspacePage().clickOnIntegrationsTab();
    }

    @Then("Application redirects to (.*) page$")
    public void verifyApplicationIsRedirectedToPage(String page) {

        waitFor(2000);
        String pageUrl = DriverFactory.getTouchDriverInstance().getCurrentUrl();

        SoftAssert softAssert = new SoftAssert();
        if(page.equalsIgnoreCase("integrations")) {
            softAssert.assertTrue(pageUrl.contains("products/integrations"),
                    String.format("User is not redirected to %s", page));
        } else if(page.equalsIgnoreCase("Long Numbers & Short Codes")) {
            softAssert.assertTrue(pageUrl.contains("my-workspace/numbers"),
                    String.format("User is not redirected to %s", page));
        } else if(page.equalsIgnoreCase("Test Phones")) {
            softAssert.assertTrue(pageUrl.contains("my-workspace/sms/test-phones"),
                    String.format("User is not redirected to %s", page));
        }
        softAssert.assertAll();
    }
}
