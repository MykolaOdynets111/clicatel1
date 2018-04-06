package steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import dataprovider.Tenants;
import driverManager.URLs;
import facebook.FBHomePage;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import touch_pages.uielements.WidgetHeader;
import twitter.TwitterHomePage;
import twitter.TwitterTenantPage;
import twitter.uielements.DMWindow;

public class TwitterSteps {

    private TwitterTenantPage twitterTenantPage;
    private DMWindow dmWindow;

    @Given("^Open twitter page of (.*)$")
    public void openTwitterPage(String tenantOrgName){
        TwitterHomePage.openTenantPage(URLs.getTwitterURL(tenantOrgName));
        if(tenantOrgName.equals("General Bank Demo")){
            Tenants.setTenantUnderTest("generalbank");
        }
    }

    @Given("^Open direct message channel$")
    public void openDirectMessage() {
        getTwitterTenantPage().openDMWindow();
    }

    @When("^User sends twitter direct message \"(.*)\"$")
    public void sendTwitterDM(String userMessage){
        getDmWindow().sendUserMessage(userMessage);
    }

    @Then("^User have to receive correct response \"(.*)\" on his message \"(.*)\"$")
    public void verifyDMTwitterResponse(String expectedResponse, String userMessage){
        SoftAssert soft = new SoftAssert();
        soft.assertTrue(getDmWindow().isTextResponseForUserMessageShown(userMessage),
                "There is no response on "+userMessage+" user message");
        soft.assertEquals(expectedResponse, getDmWindow().getToUserResponse(userMessage),
                "To user response is not as expected");
        soft.assertAll();
    }


    // =======================  Private Class Members =========================== //

    private TwitterTenantPage getTwitterTenantPage() {
        if (twitterTenantPage==null) {
            twitterTenantPage = new TwitterTenantPage();
            return twitterTenantPage;
        } else{
            return twitterTenantPage;
        }
    }

    private DMWindow getDmWindow() {
        if (dmWindow==null) {
            dmWindow = getTwitterTenantPage().getDmWindow();
            return dmWindow;
        } else{
            return dmWindow;
        }
    }
}
