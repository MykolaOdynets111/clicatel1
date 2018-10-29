package steps;

import api_helper.ApiHelper;
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
import org.testng.asserts.SoftAssert;
import portal_pages.PortalBillingDetailsPage;
import portal_pages.PortalIntegrationsPage;
import portal_pages.PortalLoginPage;
import portal_pages.PortalMainPage;
import portal_pages.uielements.LeftMenu;

import java.util.List;

public class BasePortalSteps {

    private static String agentEmail;
    private String agentPass = "p@$$w0rd4te$t";
    private ThreadLocal<PortalLoginPage> portalLoginPage = new ThreadLocal<>();
    private ThreadLocal<LeftMenu> leftMenu = new ThreadLocal<>();
    private ThreadLocal<PortalMainPage> portalMainPage = new ThreadLocal<>();
    private ThreadLocal<PortalIntegrationsPage> portalIntegrationsPage = new ThreadLocal<>();
    private ThreadLocal<PortalBillingDetailsPage> portalBillingDetailsPage = new ThreadLocal<>();


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
        portalLoginPage.set(PortalLoginPage.openPortalLoginPage());
    }

    @Given("^Tenant (.*) has no Payment Methods$")
    public void clearPaymentMethods(String tenantOrgName){
        List<String> ids = ApiHelperPlatform.getListOfActivePaymentMethods(tenantOrgName, "CREDIT_CARD");
        ids.forEach(e -> ApiHelperPlatform.deletePaymentMethod(tenantOrgName, e));
    }

    @When("^Login as newly created agent$")
    public void loginAsCreatedAgent(){
        portalLoginPage.get().login(agentEmail, agentPass);
    }

    @When("^Login into portal as an (.*) of (.*) account$")
    public void loginToPortal(String ordinalAgentNumber, String tenantOrgName){
        Agents portalAdmin = Agents.getAgentFromCurrentEnvByTenantOrgName(tenantOrgName, ordinalAgentNumber);
        portalLoginPage.get().login(portalAdmin.getAgentName(), portalAdmin.getAgentPass());
        Tenants.setTenantUnderTestNames(tenantOrgName);
    }

    public static boolean isNewUserWasCreated(){
        return agentEmail != null;
    }

    @When("^I select (.*) in left menu and (.*) in submenu$")
    public void navigateInLeftMenu(String menuItem, String submenu){
        String currentWindow = DriverFactory.getDriverForAgent("main").getWindowHandle();
        getLeftMenu().navigateINLeftMenu(menuItem, submenu);

        if(DriverFactory.getDriverForAgent("main").getWindowHandles().size()>1) {
            for (String winHandle : DriverFactory.getDriverForAgent("main").getWindowHandles()) {
                if (!winHandle.equals(currentWindow)) {
                    DriverFactory.getDriverForAgent("main").switchTo().window(winHandle);
                }
            }
        }
    }

    @When("^I try to upgrade and buy (.*) agent seats$")
    public void upgradeTouchGoPlan(int agentSeats){
        getPortalMainPage().upgradePlan(agentSeats);
    }


    @When("^Admin clicks 'Upgrade' button$")
    public void clickUpgradeTouchGoPlanButton(){
        getPortalMainPage().getPageHeader().clickUpgradeButton();
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
    public void verifyTouchGoPlanUpdatingInTenantConfig(String expectedTouchGoPlan, String tenantOrgName){
        String actualType = ApiHelper.getTenantConfig(Tenants.getTenantUnderTest(), "touchGoType");
        for(int i=0; i<60; i++){
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


    @Then("^Touch Go plan is updated to \"(.*)\" in portal page$")
    public void verifyPlanUpdatingOnPortalPage(String expectedTouchGo){
        DriverFactory.getAgentDriverInstance().navigate().refresh();
        Assert.assertEquals(getPortalMainPage().getPageHeader().getTouchGoPlanName(), expectedTouchGo,
                "Shown Touch go plan is not as expected.");
    }

    @Then("^'Billing Not Setup' pop up is shown$")
    public void verifyBillingNotSetUpPopupShown(){
        Assert.assertTrue(getPortalMainPage().isBillingNotSetUpPopupShown(5),
                "'Billing Not Setup' pop up is not shown");
    }

    @When("^Admin clicks 'Setup Billing' button$")
    public void clickSetupBillingButton(){
        portalBillingDetailsPage.set(getPortalMainPage().clickSetupBillingButton());
    }

    @Then("^Billing Details page is opened$")
    public void verifyBillingDetailsPageOpened(){
        Assert.assertTrue(getPortalBillingDetailsPage().isPageOpened(5),
                "Admin is not redirected to billing details page");
    }

    @When("^Select '(.*)' in nav menu$")
    public void clickNavItemOnBillingDetailsPage(String navName){
        getPortalBillingDetailsPage().clickNavItem(navName);
    }


    @Then("^'Add a payment method now\\?' button is shown$")
    public void verifyAddPaymentButtonShown(){
        Assert.assertTrue(getPortalBillingDetailsPage().isAddPaymentMethodButtonShown(5),
                "'Add a payment method now?' is not shown");
    }

    @Then("^Admin clicks 'Add a payment method now\\?' button$")
    public void clickAddPaymentButton(){
        getPortalBillingDetailsPage().clickAddPaymentButton();
    }

    @Then("^'Add Payment Method' window is opened$")
    public void verifyAddPaymentMethodWindowOpened(){
        Assert.assertTrue(getPortalBillingDetailsPage().isAddPaymentMethodWindowShown(5),
                "'Add Payment Method' is not opened");
    }

    @When("^Admin tries to add new card$")
    public void addNewCard(){
        getPortalBillingDetailsPage().getAddPaymentMethodWindow().addTestCardAsANewPayment();
    }

    @When("^Admin provides all card info$")
    public void fillInNewCardInfo(){
        getPortalBillingDetailsPage().getAddPaymentMethodWindow().fillInNewCardInfo();
    }

    @When("^Selects all checkboxes for adding new payment$")
    public void checkAllCheckBoxesForAddindNewPayment(){
        getPortalBillingDetailsPage().getAddPaymentMethodWindow().checkAllCheckboxesForAddingNewPayment();
    }

    @When("^Admin selects (.*) terms checkbox$")
    public void selectCheckBoxForAddingNewPayment(int checkboxNumber){
        getPortalBillingDetailsPage().getAddPaymentMethodWindow().clickCheckBox(checkboxNumber);
    }

    @When("^Admin clicks (?:'Add payment method'|'Next') button$")
    public void clickAddPaymentButtonInAddPaymentWindow(){
        getPortalBillingDetailsPage().getAddPaymentMethodWindow().clickAddPaymentButton();
        getPortalBillingDetailsPage().getAddPaymentMethodWindow().waitForAddingNewPaymentConfirmationPopup();
    }

    @Then("^New card is shown in Payment methods tab$")
    public void verifyNewPaymentAdded(){
        SoftAssert soft = new SoftAssert();
        soft.assertTrue(getPortalBillingDetailsPage().isNewPaymentAdded(),
                "New payment is not shown in 'Billing & payments' page");
        soft.assertTrue(getPortalBillingDetailsPage().getPaymentMethodDetails().contains("AQA Test"),
                "Cardholder name of added card is not as expected");
        soft.assertAll();
    }

    @Then("^Payment method is not shown in Payment methods tab$")
    public void paymentMethodIsNotShown(){
        Assert.assertFalse(getPortalBillingDetailsPage().isNewPaymentAdded(),
                "New payment is not shown in 'Billing & payments' page");
    }

    @When("^Admin clicks Manage -> Remove payment method$")
    public void deletePaymentMethod(){
        getPortalBillingDetailsPage().deletePaymentMethod();
    }

    @When("^Admin adds to cart (.*) agents$")
    public void addAgentsToTheCart(int agentsNumber){
        getPortalMainPage().addAgentSeatsIntoCart(agentsNumber);
    }

    @When("^Admin opens Confirm Details window$")
    public void openConfirmDetailsPage(){
        getPortalMainPage().openAgentsPurchasingConfirmationWindow();
    }

    @Then("Added payment method is able to be selected")
    public void verifyCardIsAvailableToSelecting(){
        Assert.assertTrue(getPortalMainPage().getCartPage().getConfirmPaymentDetailsWindow().getTestVisaCardToPayDetails().contains("1111"),
                "Added payment method is not available for purchasing");
    }

    @When("^Click Next button on Details tab$")
    public void clickNextButtonOnConfirmDetailsWindow(){
        getPortalMainPage().getCartPage().getConfirmPaymentDetailsWindow().clickNexButton();
    }

    @When("^\"(.*)\" shown in payment methods dropdown$")
    public void verifyPresencePaymentOptionForBuyingAgentSeats(String option){
        getPortalMainPage().getCartPage().getConfirmPaymentDetailsWindow().clickSelectPaymentField();
        Assert.assertTrue(getPortalMainPage().getCartPage().getConfirmPaymentDetailsWindow().isPaymentOptionshown(option),
                "'"+option+"' is not shown in payment options");
    }

    @When("^Admin selects \"(.*)\" in payment methods dropdown$")
    public void selectPaymentOptioWhileBuyingAgents(String option){
        getPortalMainPage().getCartPage().getConfirmPaymentDetailsWindow().selectPaymentMethod(option);
    }


    @Then("^Payment review tab is opened$")
    public void verifyPaymentReviewTabIsOpened(){
        Assert.assertTrue(getPortalMainPage().getCartPage().getConfirmPaymentDetailsWindow().isPaymentReviewTabOpened(),
                "Admin is not redirected to PaymentReview tab after adding new card.");

    }

    private LeftMenu getLeftMenu() {
        if (leftMenu.get()==null) {
            leftMenu.set(getPortalMainPage().getLeftMenu());
            return leftMenu.get();
        } else{
            return leftMenu.get();
        }
    }

    private PortalMainPage getPortalMainPage() {
        if (portalMainPage.get()==null) {
            portalMainPage.set(new PortalMainPage());
            return portalMainPage.get();
        } else{
            return portalMainPage.get();
        }
    }

    private PortalIntegrationsPage getPortalIntegrationsPage(){
        if (portalIntegrationsPage.get()==null) {
            portalIntegrationsPage.set(new PortalIntegrationsPage());
            return portalIntegrationsPage.get();
        } else{
            return portalIntegrationsPage.get();
        }
    }

    private PortalBillingDetailsPage getPortalBillingDetailsPage(){
        if (portalBillingDetailsPage.get()==null) {
            portalBillingDetailsPage.set(new PortalBillingDetailsPage());
            return portalBillingDetailsPage.get();
        } else{
            return portalBillingDetailsPage.get();
        }
    }
}
