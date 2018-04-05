package steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import dataprovider.Tenants;
import driverManager.URLs;
import facebook.FBHomePage;
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
