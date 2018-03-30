package steps;

import cucumber.api.java.en.Given;
import dataprovider.Tenants;
import driverManager.URLs;
import facebook.FBHomePage;
import twitter.TwitterHomePage;

public class TwitterSteps {

    @Given("^Open twitter page of (.*)$")
    public void openTwitterPage(String tenantOrgName){
        TwitterHomePage.openTenantPage(URLs.getTwitterURL(tenantOrgName));
        if(tenantOrgName.equals("General Bank Demo")){
            Tenants.setTenantUnderTest("generalbank");
        }
    }
}
