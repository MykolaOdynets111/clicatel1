package steps;

import api_helper.ApiHelper;
import api_helper.ApiHelperPlatform;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import dataManager.Agents;
import dataManager.MC2Account;
import dataManager.Tenants;
import dbManager.DBConnector;
import driverManager.ConfigManager;
import driverManager.DriverFactory;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import portal_pages.PortalIntegrationsPage;
import portal_pages.PortalLoginPage;
import portal_pages.PortalMainPage;
import portal_pages.uielements.LeftMenu;

import java.util.List;

public class BasePortalSteps {

    private static String agentEmail;
    private String agentPass = "p@$$w0rd4te$t";
    private PortalLoginPage portalLoginPage;
    private LeftMenu leftMenu;
    private PortalMainPage portalMainPage;
    private PortalIntegrationsPage portalIntegrationsPage;


    @Given("^New (.*) agent is created$")
    public void createNewAgent(String tenantOrgName){
        Tenants.setTenantUnderTestNames(tenantOrgName);
        agentEmail = "aqa_"+System.currentTimeMillis()+"@aqa.com";
        ApiHelperPlatform.sendNewAgentInvitation(tenantOrgName, agentEmail);
        String invitationID = DBConnector.getInvitationIdForCreatedUserFromMC2DB(ConfigManager.getEnv(), agentEmail);
        ApiHelperPlatform.acceptInvitation(tenantOrgName, invitationID, agentPass);
    }

    @Given("^Delete user$")
    public static void deleteAgent(){
        String userID = ApiHelperPlatform.getUserID(Tenants.getTenantUnderTestOrgName(), agentEmail);
        ApiHelperPlatform.deleteUser(Tenants.getTenantUnderTestOrgName(), userID);
//        ApiHelperPlatform.deleteUser("General Bank Demo", "ff808081619df1000161bd8981060003");
    }

    @When("^I open portal$")
    public void openPortal(){
        portalLoginPage = PortalLoginPage.openPortalLoginPage();
    }

    @When("^Login as newly created agent$")
    public void loginAsCreatedAgent(){
        portalLoginPage.login(agentEmail, agentPass);
    }

    @When("^Login into portal as an (.*) of (.*) account$")
    public void loginToPortal(String ordinalAgentNumber, String tenantOrgName){
        Agents portalAdmin = Agents.getAgentFromCurrentEnvByTenantOrgName(tenantOrgName, ordinalAgentNumber);
        portalLoginPage.login(portalAdmin.getAgentName(), portalAdmin.getAgentPass());
        Tenants.setTenantUnderTestNames(tenantOrgName);
    }

    public static boolean isNewUserWasCreated(){
        return agentEmail != null;
    }

    @When("^I select (.*) in left menu and (.*) in submenu$")
    public void navigateInLeftMenu(String menuItem, String submenu){
        String currentWindow = DriverFactory.getDriverForAgent("main").getWindowHandle();
        getLeftMenu().navigateINLeftMenu(menuItem, submenu);

        for(String winHandle : DriverFactory.getDriverForAgent("main").getWindowHandles()){
            if(!winHandle.equals(currentWindow)){
                DriverFactory.getDriverForAgent("main").switchTo().window(winHandle);
            }
        }
    }

    @When("^I try to upgrade and buy (.*) agent seats$")
    public void upgradeTouchGoPlan(int agentSeats){
        getPortalMainPage().upgradePlan(agentSeats);
    }

    @Then("^I see \"Payment Successful\" message$")
    public void verifyPaymentSuccessfulMessage(){
        Assert.assertEquals(getPortalMainPage().getCartPage().getConfirmPaymentDetailsWindow().getSuccessMessageMessage(),
                "Payment Successful", "Payment successful message was not shown");
    }

    @Then("^Admin should see \"(.*)\" in the page header$")
    public void verifyTouchGoPlanNamePresence(String expectedTouchGo){
        Assert.assertEquals(getPortalMainPage().getPageHeader().getTouchGoPlanName(), expectedTouchGo,
                "Shown Touch go plan is not as expected.");
    }

    @Then("^Not see \"(.*)\" button$")
    public void verifyNotShowingUpgradeButtonText(String textNotToBeShown){
        Assert.assertFalse(getPortalMainPage().getPageHeader().getTextOfUpgradeButton().equals(textNotToBeShown),
                "'Add Agent seats' button is shown for Starter Touch Go tenant");
    }

    @Then("^See \"(.*)\" button$")
    public void verifyNShowingUpgradeButtonText(String textToBeShown){
        Assert.assertTrue(getPortalMainPage().getPageHeader().getTextOfUpgradeButton().equals(textToBeShown),
                "'Add Agent seats' button is shown for Starter Touch Go tenant");
    }

    @When("^Disable the (.*)$")
    public void disableTheIntegration(String integration){
        getPortalIntegrationsPage().clickToggleFor(integration);
    }

    @Then("^Status of (.*) is changed to \"(.*)\"$")
    public void verifyTheIntegrationStatus(String integration, String expectedStatus){
        SoftAssert soft = new SoftAssert();
        soft.assertTrue(getPortalIntegrationsPage().getIntegrationRowStatus(integration).equalsIgnoreCase(expectedStatus));
        soft.assertTrue(getPortalIntegrationsPage().getIntegrationCardStatus(integration).equalsIgnoreCase(expectedStatus));
    }

    @Then("^Touch Go plan is updated to \"(.*)\" in (.*) tenant configs$")
    public void verifyTouchGoPlanUpdatingInTenantConfig(String tenantOrgName, String expectedTouchGoPlan){
        String actualType = ApiHelper.getTenantConfig(Tenants.getTenantUnderTest(), "touchGoType");
        for(int i=0; i<41; i++){
            if (!actualType.equalsIgnoreCase(expectedTouchGoPlan)){
                getPortalMainPage().waitFor(15000);
                actualType = ApiHelper.getTenantConfig(Tenants.getTenantUnderTest(), "touchGoType");
            } else{
                break;
            }
        }
        Assert.assertTrue(actualType.equalsIgnoreCase(expectedTouchGoPlan),
                "TouchGo plan is not updated in tenant configs for '"+tenantOrgName+"' tenant \n"+
                        "Expected: " + expectedTouchGoPlan + "\n" +
                        "Found:" + actualType
        );
    }


    @Then("^Touch Go PLan is updated to (.*) in portal page$")
    public void verifyPlanUpdatingOnPortalPage(String expectedTouchGo){
        DriverFactory.getAgentDriverInstance();
        Assert.assertEquals(getPortalMainPage().getPageHeader().getTouchGoPlanName(), expectedTouchGo,
                "Shown Touch go plan is not as expected.");
    }

    private LeftMenu getLeftMenu() {
        if (leftMenu==null) {
            leftMenu =  getPortalMainPage().getLeftMenu();
            return leftMenu;
        } else{
            return leftMenu;
        }
    }

    private PortalMainPage getPortalMainPage() {
        if (portalMainPage==null) {
            portalMainPage =  new PortalMainPage();
            return portalMainPage;
        } else{
            return portalMainPage;
        }
    }

    private PortalIntegrationsPage getPortalIntegrationsPage(){
        if (portalIntegrationsPage==null) {
            portalIntegrationsPage =  new PortalIntegrationsPage();
            return portalIntegrationsPage;
        } else{
            return portalIntegrationsPage;
        }
    }

}
