package steps;

import agent_side_pages.UIElements.SuggestedGroup;
import api_helper.ApiHelperPlatform;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import dataManager.Agents;
import dataManager.Tenants;
import dbManager.DBConnector;
import driverManager.ConfigManager;
import driverManager.DriverFactory;
import org.testng.Assert;
import portal_pages.PortalLoginPage;
import portal_pages.PortalMainPage;
import portal_pages.uielements.LeftMenu;

public class BasePortalSteps {

    private static String agentEmail;
    private String agentPass = "p@$$w0rd4te$t";
    private PortalLoginPage portalLoginPage;
    private LeftMenu leftMenu;
    private PortalMainPage portalMainPage;


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
    public void loginToPortal(String ordinalAgentNumber, String account){
        Agents portalAdmin = Agents.getAgentFromCurrentEnvByTenantOrgName(account, ordinalAgentNumber);
        portalLoginPage.login(portalAdmin.getAgentName(), portalAdmin.getAgentPass());
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
    public void verifyTouchGoPlanNamePresence(String expectedTocuhGo){
        Assert.assertEquals(getPortalMainPage().getPageHeader().getTouchGoPlanName(), expectedTocuhGo,
                "Shown Touch go plan is not as expected.");
    }

    @Then("^Not see \"Add Agent seats\"$")
    public void verifyAddAgentsSeatsNotShown(){
        Assert.assertFalse(getPortalMainPage().getPageHeader().getTextOfUpgradeButton().equals("Add Agent seats"),
                "'Add Agent seats' button is shown for Starter Touch Go tenant");
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
}
