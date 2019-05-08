package steps;

import agentpages.AgentHomePage;
import apihelper.ApiHelper;
import apihelper.ApiHelperPlatform;
import apihelper.Endpoints;
import com.github.javafaker.Faker;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import datamanager.Agents;
import datamanager.FacebookUsers;
import datamanager.Tenants;
import dbmanager.DBConnector;
import drivermanager.ConfigManager;
import drivermanager.DriverFactory;
import io.restassured.path.json.exception.JsonPathException;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import portalpages.*;
import portalpages.uielements.LeftMenu;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BasePortalSteps {

    private Faker faker = new Faker();
    private ThreadLocal<PortalLoginPage> portalLoginPage = new ThreadLocal<>();
    private ThreadLocal<LeftMenu> leftMenu = new ThreadLocal<>();
    private ThreadLocal<PortalMainPage> portalMainPage = new ThreadLocal<>();
    private ThreadLocal<PortalIntegrationsPage> portalIntegrationsPage = new ThreadLocal<>();
    private ThreadLocal<PortalBillingDetailsPage> portalBillingDetailsPage = new ThreadLocal<>();
    private ThreadLocal<PortalSignUpPage> portalSignUpPage = new ThreadLocal<>();
    private ThreadLocal<PortalAccountDetailsPage> portalAccountDetailsPageThreadLocal = new ThreadLocal<>();
    private ThreadLocal<PortalFBIntegrationPage> portalFBIntegrationPageThreadLocal = new ThreadLocal<>();
    private ThreadLocal<PortalManageAgentUsersPage> portalManagingUsersThreadLocal = new ThreadLocal<>();
    private ThreadLocal<PortalUserEditingPage> portalUserProfileEditingThreadLocal = new ThreadLocal<>();
    private ThreadLocal<PortalTouchPrefencesPage> portalTouchPrefencesPageThreadLocal = new ThreadLocal<>();
    private ThreadLocal<PortalUserManagementPage> portalUserManagementPageThreadLocal = new ThreadLocal<>();
    private ThreadLocal<PortalChatConsolePage> portalChatConsolePage = new ThreadLocal<>();
    private ThreadLocal<String> autoresponseMessageThreadLocal = new ThreadLocal<>();
    public static final String EMAIL_FOR_NEW_ACCOUNT_SIGN_UP = "account_signup@aqa.test";
    public static final String PASS_FOR_NEW_ACCOUNT_SIGN_UP = "p@$$w0rd4te$t";
    public static final String ACCOUNT_NAME_FOR_NEW_ACCOUNT_SIGN_UP = "automationtest";
    public static final String FIRST_AND_LAST_NAME = "Taras Aqa";
    public static String AGENT_FIRST_NAME;
    public static String AGENT_LAST_NAME;
    private static String AGENT_EMAIL;
    private String AGENT_PASS = "p@$$w0rd4te$t";
    private Map<String, String> updatedAgentInfo;
    public static Map billingInfo = new HashMap();
    private String activationAccountID;
    private static String COLOR;
    private Map<String, Integer> chatConsolePretestValue = new HashMap<>();
    int activeChatsFromChatdesk;

    @Given("^New (.*) agent is created$")
    public void createNewAgent(String tenantOrgName){
        Tenants.setTenantUnderTestNames(tenantOrgName);
        AGENT_FIRST_NAME = faker.name().firstName();
        AGENT_LAST_NAME =  faker.name().lastName();
        AGENT_EMAIL = "aqa_"+System.currentTimeMillis()+"@aqa.com";
        ApiHelperPlatform.sendNewAgentInvitation(tenantOrgName, AGENT_EMAIL, AGENT_FIRST_NAME, AGENT_LAST_NAME);
        // added wait for new agent to be successfully saved in touch DB before further actions with this agent
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String invitationID = DBConnector.getInvitationIdForCreatedUserFromMC2DB(ConfigManager.getEnv(), AGENT_EMAIL);
        ApiHelperPlatform.acceptInvitation(tenantOrgName, invitationID, AGENT_PASS);
    }

    @Then("^Newly created agent is deleted in DB$")
    public void verifyAgentDelete(){

    }

    @Then("^Agent of (.*) should have all permissions to manage CRM tickets$")
    public void verifyAgentCRMPermissions(String tenantOrgName){
        Tenants.setTenantUnderTestNames(tenantOrgName);
        List<String> expectedPermissions = Arrays.asList("delete-user-profile", "create-user-profile", "update-user-profile", "read-user-profile");
        List<String> permissions = ApiHelperPlatform.getAllRolePermission(tenantOrgName, "Touch agent role");
        Assert.assertTrue(expectedPermissions.stream().allMatch(e -> permissions.contains(e)),
                "Not all CRM permissions are present for Agent role\n" +
                        "Expected permitions: " + expectedPermissions.toString() + "\n" +
                        "Full permitions list: " + permissions.toString());
    }

    @Then("^New agent is added into touch database$")
    public void verifyThatNewAgentAddedToDatabase(){
        Assert.assertTrue(DBConnector.isAgentCreatedInDB(ConfigManager.getEnv(), AGENT_EMAIL),
                "Agent with '" + AGENT_EMAIL + "' Email is not created in touch DB after 10 seconds wait.");
    }

    @Given("^Delete user$")
    public static void deleteAgent(){
        String userID = ApiHelperPlatform.getUserID(Tenants.getTenantUnderTestOrgName(), AGENT_EMAIL);
        ApiHelperPlatform.deleteUser(Tenants.getTenantUnderTestOrgName(), userID);
    }

    @When("^I provide all info about new account and click 'Sign Up' button$")
    public void fillInFormWithInfoAboutNewAccount(){
        getPortalSignUpPage().signUp(FIRST_AND_LAST_NAME, ACCOUNT_NAME_FOR_NEW_ACCOUNT_SIGN_UP,
                                        EMAIL_FOR_NEW_ACCOUNT_SIGN_UP, PASS_FOR_NEW_ACCOUNT_SIGN_UP);
    }

    @When("^I try to create new account with following data: (.*), (.*), (.*), (.*)$")
    public void createNewAccountWithData(String names, String accountName, String email, String pass){
        getPortalSignUpPage().signUp(names, accountName, email, pass);
    }

    @Then("^Required error should be shown$")
    public void verifyRequiredErrorsShown(){
        Assert.assertTrue(getPortalSignUpPage().areRequiredErrorsShown(),
                "'Required' error not shown");
    }

    @Then("^Error popup with text (.*) is shown$")
    public void verifyVerificationMessage(String expectedMessage){
        Assert.assertEquals(getPortalSignUpPage().getNotificationAlertText().trim(), expectedMessage.trim(),
                "Field verification is not working.");
    }

    @When("^I open portal$")
    public void openPortal(){
        portalLoginPage.set(PortalLoginPage.openPortalLoginPage());
    }


    @When("(.*) test accounts is closed")
    public void closeAllTestAccount(String tenantOrgName){
        Tenants.setTenantUnderTestNames(tenantOrgName);
        ApiHelperPlatform.closeAccount(BasePortalSteps.ACCOUNT_NAME_FOR_NEW_ACCOUNT_SIGN_UP,
                BasePortalSteps.EMAIL_FOR_NEW_ACCOUNT_SIGN_UP,
                BasePortalSteps.PASS_FOR_NEW_ACCOUNT_SIGN_UP);
    }

    @When("Portal Sign Up page is opened")
    public void openPortalSignUpPage(){
        portalSignUpPage.set(PortalSignUpPage.openPortalSignUpPage());
    }

    @When("I use activation ID and opens activation page")
    public void openActivationAccountPage(){
        String activationURL = String.format(Endpoints.PORTAL_ACCOUNT_ACTIVATION, activationAccountID);
        DriverFactory.getAgentDriverInstance().get(activationURL);
    }

    @Then("^Page with a message \"Your account has successfully been created!\" is shown$")
    public void verifySuccessMessageIsShown(){
        Assert.assertTrue(getPortalSignUpPage().isSuccessSignUpMessageShown(),
                "Success sign up message is not shown");
    }

    @Then("^Activation ID record is created in DB$")
    public void verifyActivationIDIsCreatedInDB(){
        activationAccountID = DBConnector.getAccountActivationIdFromMC2DB(ConfigManager.getEnv(),
                ACCOUNT_NAME_FOR_NEW_ACCOUNT_SIGN_UP);
        for(int i=0; i<4; i++){
            if (activationAccountID==null){
                getPortalSignUpPage().waitFor(1000);
                activationAccountID = DBConnector.getAccountActivationIdFromMC2DB(ConfigManager.getEnv(),
                        ACCOUNT_NAME_FOR_NEW_ACCOUNT_SIGN_UP);
            }
        }
        Assert.assertFalse(activationAccountID ==null,
        "Record with new activation ID is not created in mc2 DB after submitting sign up form");
    }


    @Then("^Login page is opened with a message that activation email has been sent$")
    public void verifyMassageThatConfirmationEmailSent(){
        String expectedMessageAboutSentEmail = "A confirmation email has been sent to "+EMAIL_FOR_NEW_ACCOUNT_SIGN_UP+"" +
                " to complete your sign up process";
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(getPortalLoginPage().isMessageAboutConfirmationMailSentShown(),
                "Message that confirmation email was sent is not shown");
        softAssert.assertEquals(getPortalLoginPage().getMessageAboutSendingConfirmationEmail(), expectedMessageAboutSentEmail,
                "Message about sent confirmation email is not as expected");
        softAssert.assertAll();
    }

    @Given("Widget is enabled for (.*) tenant")
    public void enableWidget(String tenantOrgNAme){
        ApiHelper.setIntegrationStatus(tenantOrgNAme, "webchat", true);
    }

    @Given("^(.*) tenant has Starter Touch Go PLan and no active subscription$")
    public void downgradeTouchGoPlan(String tenantOrgName){
            Tenants.setTenantUnderTestNames(tenantOrgName);
            DriverFactory.closeAgentBrowser();
            ApiHelper.decreaseTouchGoPLan(tenantOrgName);
            List<Integer> subscriptionIDs = ApiHelperPlatform.getListOfActiveSubscriptions(tenantOrgName);
            subscriptionIDs.forEach(e ->
                    ApiHelperPlatform.deactivateSubscription(Tenants.getTenantUnderTestOrgName(), e));
    }

    @Given("^Tenant (.*) has no Payment Methods$")
    public void clearPaymentMethods(String tenantOrgName){
        List<String> ids = ApiHelperPlatform.getListOfActivePaymentMethods(tenantOrgName, "CREDIT_CARD");
        ids.forEach(e -> ApiHelperPlatform.deletePaymentMethod(tenantOrgName, e));
    }

    @When("^Login as (.*) agent$")
    public void loginAsCreatedAgent(String agent){
        String email = AGENT_EMAIL;
        if(agent.equalsIgnoreCase("updated")){
            email = updatedAgentInfo.get("email");
        }
        portalLoginPage.get().login(email, AGENT_PASS);
    }

    @Then("^Deleted agent is not able to log in portal$")
    public void verifyDeletedAgentIsNotLoggedIn(){
        portalLoginPage.get().login(AGENT_EMAIL, AGENT_PASS);
        Assert.assertEquals(portalLoginPage.get().getNotificationAlertText(),
                "Username or password is invalid",
                "Error about invalid credentials is not shown");
    }

    @When("^Login into portal as an (.*) of (.*) account$")
    public void loginToPortal(String ordinalAgentNumber, String tenantOrgName){
        Agents portalAdmin = Agents.getAgentFromCurrentEnvByTenantOrgName(tenantOrgName, ordinalAgentNumber);
        portalMainPage.set(
                portalLoginPage.get().login(portalAdmin.getAgentName(), portalAdmin.getAgentPass())
        );
        Tenants.setTenantUnderTestNames(tenantOrgName);
    }

    @When("^I click Launchpad button$")
    public void clickLaunchpadButton(){
        getPortalMainPage().clickLaunchpadButton();
    }

    @When("^Click 'Close account' button$")
    public void clickCloseAccount(){
        getPortalAccountDetailsPage().clickCloseAccountButton();
    }

    @Then("^'Close account\\?' popup is shown$")
    public void verifyClosingConfirmationPopupShown(){
        Assert.assertTrue(getPortalAccountDetailsPage().isClosingConfirmationPopupShown(),
        "Closing account confirmation window is not shown.");
    }

    @When("^Admin clicks 'Close account' button in confirmation popup$")
    public void confirmClosingAccount(){
        getPortalAccountDetailsPage().confirmClosingAccount();
    }

    @When("^Admin clicks 'Cancel' button in confirmation popup$")
    public void cancelClosingAccount(){
        getPortalAccountDetailsPage().cancelClosingAccount();
    }

    @Then("^Account details page is opened$")
    public void verifyAccountDetailsPageOpened(){
        Assert.assertTrue(getPortalAccountDetailsPage().isAccountDetailsPageOpened(),
                "Account details page is not shown after canceling account closing");
    }

    @Then("^'Account confirmation' popup is shown$")
    public void verifyAccountConfirmationPopupShown(){
        Assert.assertTrue(getPortalAccountDetailsPage().isAccountConfirmationPopupShown(),
                "Account confirmation popup is not shown");
    }

    @When("^Admin confirms account to close$")
    public void confirmAccountToCLosing(){
        getPortalAccountDetailsPage().confirmAccount(EMAIL_FOR_NEW_ACCOUNT_SIGN_UP,
                PASS_FOR_NEW_ACCOUNT_SIGN_UP);
    }

    @When("Admin provides '(.*)' email and '(.*)' pass for account confirmation")
    public void enterConfirmationAccountDetails(String email, String account){
        getPortalAccountDetailsPage().confirmAccount(email, account);
    }

    @Then("^Admin is not able to login into portal with deleted account$")
    public void verifyAdminCannotLoginToPortal(){
        portalLoginPage.set(PortalLoginPage.openPortalLoginPage());
        if (!portalLoginPage.get().isLoginPageOpened(1)){
            portalLoginPage.get().waitFor(200);
            portalLoginPage.set(PortalLoginPage.openPortalLoginPage());
        }
        portalLoginPage.get().login(EMAIL_FOR_NEW_ACCOUNT_SIGN_UP, PASS_FOR_NEW_ACCOUNT_SIGN_UP);
        Assert.assertEquals(portalLoginPage.get().getNotificationAlertText(),
                "Username or password is invalid",
                "Error about invalid credentials is not shown");
    }

    @Then("^Error about invalid credentials is shown$")
    public void verifyInvalidCredsError(){
        Assert.assertEquals(portalAccountDetailsPageThreadLocal.get().getNotificationAlertText(),
                "Invalid username or password.",
                "Error about invalid credentials is not shown");
    }

    @Then("^Portal Page is opened$")
    public void verifyPortalPageOpened(){
        Assert.assertTrue(getPortalMainPage().isPortalPageOpened(),
                "User is not logged in Portal");
    }

    @Then("^\"Update policy\" pop up is shown$")
    public void verifyUpdatePolicyPopupShown(){
        Assert.assertTrue(getPortalMainPage().isUpdatePolicyPopUpOpened(),
                "User is not logged in Portal");
    }

    @When("^Accept \"Update policy\" popup$")
    public void acceptUpdatedPolicyPopup(){
        getPortalMainPage().closeUpdatePolicyPopup();
    }

    @Then("^Main portal page with welcome message is shown$")
    public void verifyMainPageWithWelcomeMessageShown(){
        Assert.assertEquals(getPortalMainPage().getGreetingMessage(), "Welcome, "+ FIRST_AND_LAST_NAME.split(" ")[0] +
                ". Thanks for signing up.", "Welcome message is not shown.");
    }

    @Then("^\"Get started with Touch\" button is shown$")
    public void verifyGetStartedWithTouchButtonShown(){
        Assert.assertTrue(getPortalMainPage().isGetStartedWithTouchButtonIsShown(),
                "'Get started with Touch' is not shown");
    }

    @When("^Click \"Get started with Touch\" button$")
    public void clickGetStartedWithTouchButton(){
        getPortalMainPage().clickGetStartedWithTouchButton();
    }

    @When("^\"Get started with Touch Go\" window is opened$")
    public void verifyStartedWithTouchGoWindowOpened(){
        Assert.assertTrue(getPortalMainPage().isConfigureTouchWindowOpened(),
                "\"Get started with Touch Go\" window is not opened");
    }

    @When("^I try to create new tenant$")
    public void createNewTenant(){
        getPortalMainPage().getConfigureTouchWindow().createNewTenant("SignedUp AQA", EMAIL_FOR_NEW_ACCOUNT_SIGN_UP);
    }

    public static boolean isNewUserWasCreated(){
        return AGENT_EMAIL != null;
    }

    @When("^(?:I|Admin) select (.*) in left menu and (.*) in submenu$")
    public void navigateInLeftMenu(String menuItem, String submenu){
        String currentWindow = DriverFactory.getDriverForAgent("main").getWindowHandle();
        getLeftMenu().navigateINLeftMenuWithSubmenu(menuItem, submenu);

        if(DriverFactory.getDriverForAgent("main").getWindowHandles().size()>1) {
            for (String winHandle : DriverFactory.getDriverForAgent("main").getWindowHandles()) {
                if (!winHandle.equals(currentWindow)) {
                    DriverFactory.getDriverForAgent("main").switchTo().window(winHandle);
                }
            }
        }
    }

    @When("^Save (.*) pre-test widget value$")
    public void savePreTestValue(String widgetName){
        chatConsolePretestValue.put(widgetName, Integer.valueOf(getPortalChatConsolePage().getWidgetValue(widgetName)));
    }

    @Then("^(.*) widget shows correct number$")
    public void checkTotalAgentOnlineValue(String widgetName){
        int actualActiveAgentsCount = Integer.valueOf(getPortalChatConsolePage().getWidgetValue(widgetName));
        chatConsolePretestValue.put(widgetName, actualActiveAgentsCount);
        int loggedInAgentsCountFromBackend = ApiHelper.getNumberOfLoggedInAgents();
        Assert.assertEquals(actualActiveAgentsCount, loggedInAgentsCountFromBackend,
                widgetName + " counter differs from agent online count on backend");
    }

    @Then("^(.*) widget value increased on (.*)$")
    public void verifyWidgetValue(String widgetName, int incrementor){
        int expectedValue = chatConsolePretestValue.get(widgetName) + incrementor;
        Assert.assertTrue(checkLiveCounterValue(widgetName, expectedValue),
                "'"+widgetName+"' widget value is not updated");
    }

    @Then("^(.*) counter shows correct live chats number$")
    public void verifyChatConsoleActiveChats(String widgetName){
        activeChatsFromChatdesk = new AgentHomePage("second agent").getLeftMenuWithChats().getNewChatsCount();
        Assert.assertTrue(checkLiveCounterValue(widgetName, activeChatsFromChatdesk),
                "'"+widgetName+"' widget value is not updated");
    }

    @Then("^Average chats per Agent is correct$")
    public void verifyAverageChatsPerAgent(){
       int actualAverageChats = Integer.valueOf(getPortalChatConsolePage().getAverageChatsPerAgent());
       int expectedAverageChats = activeChatsFromChatdesk /  ApiHelper.getNumberOfLoggedInAgents();
       Assert.assertEquals(actualAverageChats, expectedAverageChats,
               "Number of Average chats per Agent is not as expected");
    }

    private boolean checkLiveCounterValue(String widgetName, int expectedValue){
        int actualValue = Integer.valueOf(getPortalChatConsolePage().getWidgetValue(widgetName));
        boolean result = false;
        for (int i=0; i<30; i++){
            if(expectedValue!=actualValue){
                getPortalChatConsolePage().waitFor(1000);
                actualValue = Integer.valueOf(getPortalChatConsolePage().getWidgetValue(widgetName));
            } else {
                result =true;
                break;
            }

        }
        return result;
    }

    @When("^Click \"(.*)\" nav button$")
    public void clickNavButton(String navButton){
        getPortalTouchPrefencesPage().clickPageNavButton(navButton);
    }

    @When("^Agent click 'Save changes' button$")
    public void agentClickSaveChangesButton() {
        getPortalTouchPrefencesPage().clickSaveButton();
        getPortalTouchPrefencesPage().waitWhileProcessing();
    }


    @When("^Agent click expand arrow for (.*) auto responder$")
    public void clickExpandArrowForAutoResponder(String autoresponder){
        getPortalTouchPrefencesPage().getAutoRespondersWindow().waitToBeLoaded();
        getPortalTouchPrefencesPage().getAutoRespondersWindow()
                                                            .clickExpandArrowForMessage(autoresponder);
    }

    @When("^Agent click On/Off button for (.*) auto responder$")
    public void clickOnOffForAutoResponder(String autoresponder){
        getPortalTouchPrefencesPage().getAutoRespondersWindow().waitToBeLoaded();
        getPortalTouchPrefencesPage().getAutoRespondersWindow()
                .clickOnOffForMessage(autoresponder);
    }

    @When("^Click \"Reset to default\" button for (.*) auto responder$")
    public void clickResetToDefaultButton(String autoresponder){
        getPortalTouchPrefencesPage().getAutoRespondersWindow().clickResetToDefaultForMessage(autoresponder);
        getPortalTouchPrefencesPage().waitWhileProcessing();
    }

    @When("^Type new message: (.*) to (.*) message field$")
    public void typeNewMessage(String message, String autoresponder){
        getPortalTouchPrefencesPage().getAutoRespondersWindow().waitToBeLoaded();
        if (!getPortalTouchPrefencesPage().getAutoRespondersWindow().getTargetAutoResponderItem(autoresponder).isMessageShown()) {
            getPortalTouchPrefencesPage().getAutoRespondersWindow()
                    .clickExpandArrowForMessage(autoresponder);
        }
        getPortalTouchPrefencesPage().getAutoRespondersWindow().getTargetAutoResponderItem(autoresponder).typeMessage(message);
//        autoresponseMessageThreadLocal.set(message);
//        autoresponseMessageThreadLocal.get();
//       // getPortalTouchPrefencesPage().waitWhileProcessing();
    }

    @Then("^(.*) on backend corresponds to (.*) on frontend$")
    public void messageWasUpdatedOnBackend(String tafMessageId, String messageName) {
        String messageOnfrontend = getPortalTouchPrefencesPage().getAutoRespondersWindow().getTargetAutoResponderItem(messageName).getMessage();
        String actualMessage = ApiHelper.getTenantMessageText(tafMessageId);
        Assert.assertEquals(actualMessage, messageOnfrontend,
                messageName + " message is not updated on backend");

    }

    @Then("^(.*) is reset on backend$")
    public void verifyTafMessageIsReset(String tafMessageId){
        String actualMessage = ApiHelper.getTenantMessageText(tafMessageId);
        String defaultMessage = ApiHelper.getDefaultTenantMessageText(tafMessageId);
        Assert.assertEquals(actualMessage, defaultMessage,
                tafMessageId + " message is not reset to default");
    }

    @When("^(?:I|Admin) select (.*) in left menu$")
    public void navigateInLeftMenu(String menuItem){
        String currentWindow = DriverFactory.getDriverForAgent("main").getWindowHandle();
        getLeftMenu().navigateINLeftMenu(menuItem);

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

    @When("^I try to upgrade and buy (.*) agent seats without accept Clickatell's Terms and Conditions$")
    public void upgradeTouchGoPlanWithoutTerms(int agentSeats){
        getPortalMainPage().upgradePlanWithoutTerms(agentSeats);
    }

    @Then("^Payment is not proceeded and Payment Summary tab is still opened$")
    public void verifyPaymentSummaryTabOpened(){
        Assert.assertTrue(getPortalMainPage().getCartPage().getConfirmPaymentDetailsWindow().isPaymnentSummaryTabOPened(),
                "'Payment Summary' tab is not still opened when admin tries to proceed without accepting Terms and Conditions");
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
        getPortalIntegrationsPage().waitWhileProcessing();
    }

    @Then("^Status of (.*) integration is changed to \"(.*)\"$")
    public void verifyTheIntegrationStatus(String integration, String expectedStatus){
        SoftAssert soft = new SoftAssert();
        soft.assertEquals(getPortalIntegrationsPage().getIntegrationRowStatus(integration).toLowerCase(), expectedStatus.toLowerCase(),
                "'"+integration+"' integration status in integrations table is not as expected");
        soft.assertEquals(getPortalIntegrationsPage().getIntegrationCardStatus(integration).toLowerCase(), expectedStatus.toLowerCase(),
                "'"+integration+"' integration status in integrations table is not as expected");
        soft.assertAll();
    }

    @Then("^Status of (.*) integration is \"(.*)\" in integration card$")
    public void verifyTheIntegrationStatusInTheCard(String integration, String expectedStatus){
        String actualStatus = getPortalIntegrationsPage().getIntegrationCardStatus(integration).toLowerCase();
        if(!actualStatus.equalsIgnoreCase(expectedStatus)) {
            actualStatus = getPortalIntegrationsPage().getIntegrationCardStatus(integration).toLowerCase();
        }
        Assert.assertEquals(actualStatus, expectedStatus.toLowerCase(),
                "'"+integration+"' has unexpected status.");
    }

    @Then("^Fb integration is saved on the backend$")
    public void verifyFBIntegrationSaved(){
        Response fbIntegrationInfo = (Response) ApiHelper.getInfoAboutFBIntegration(Tenants.getTenantUnderTestOrgName());
        String userName = "";
        try {
            userName = fbIntegrationInfo.getBody().jsonPath().getString("firstName");
        } catch (JsonPathException e){
            Assert.assertTrue(false, "FB integration not created");
        }
        Assert.assertEquals(userName, FacebookUsers.USER_FOR_INTEGRATION.getFBUserName(),
                "Incorrect user name in fb integration");
    }

    @Then("^Facebook integration is deleted$")
    public void verifyFBIntegrationDeleted(){
        Response fbIntegrationInfo = (Response) ApiHelper.getInfoAboutFBIntegration(Tenants.getTenantUnderTestOrgName());
        try {
            fbIntegrationInfo.getBody().jsonPath().getString("errorMessage");
        } catch (JsonPathException e){
            Assert.assertTrue(false, "FB integration is not deleted");
        }

    }

    @When("^Click '(.*)' button for (.*) integration$")
    public void clickButtonForIntegrationCard(String button, String integration){
        getPortalIntegrationsPage().clickActionButtonForIntegration(integration);
    }

    @When("^Add fb integration with (.*) fb page$")
    public void makeFBIntegration(String fbPage){
        getPortalIntegrationsPage().getCreateIntegrationWindow().setUpFBIntegration(fbPage);
    }

    @When("^Add twitter integration with (.*) fb page$")
    public void makeTwitterIntegration(String page){

    }

    @When("^Delink facebook account$")
    public void delinkFBAccount(){
        getFBPortalFBIntegrationPage().delinkFBAccount();
    }

    @Then("^Touch Go plan is updated to \"(.*)\" in (.*) tenant configs$")
    public void verifyTouchGoPlanUpdatingInTenantConfig(String expectedTouchGoPlan, String tenantOrgName){
        String actualType = ApiHelper.getInternalTenantConfig(Tenants.getTenantUnderTestName(), "touchGoType");
        for(int i=0; i<120; i++){
            if (!actualType.equalsIgnoreCase(expectedTouchGoPlan)){
                getPortalMainPage().waitFor(15000);
                DriverFactory.getAgentDriverInstance().navigate().refresh();
                actualType = ApiHelper.getInternalTenantConfig(Tenants.getTenantUnderTestName(), "touchGoType");
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

    @When("^Fill in Billing details$")
    public void fillInBillingDetails(){
        billingInfo = getPortalBillingDetailsPage().getBillingContactsDetails().fillInBillingDetailsForm();
    }

    @Then("^Billing details is saved on backend$")
    public void verifyBillingDetails(){
        SoftAssert soft = new SoftAssert();
        Response resp = ApiHelperPlatform.getAccountBillingInfo(Tenants.getTenantUnderTestOrgName());
        Map info = resp.jsonPath().getMap("");
        String billingAddress = resp.jsonPath().get("billingAddress.country.name") + ", " +
                                resp.jsonPath().get("billingAddress.city") + ", " +
                                resp.jsonPath().get("billingAddress.address1") + ", " +
                                resp.jsonPath().get("billingAddress.postalCode");
        soft.assertEquals(info.get("billingContact").toString(), billingInfo.get("billingContact"),
                "Billing contact is incorrectly saved");
        soft.assertEquals(info.get("accountTypeId").toString(), billingInfo.get("accountTypeId"),
                "accountTypeId is incorrectly saved");
        soft.assertEquals(info.get("companyName").toString(), billingInfo.get("companyName"),
                "Company Name is incorrectly saved");
        soft.assertEquals(billingAddress, billingInfo.get("billingAddress"),
                "Billing address is incorrectly saved");
        soft.assertAll();
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
        getPortalBillingDetailsPage().waitWhileProcessing();
        getPortalBillingDetailsPage().waitForNotificationAlertToDisappear();
    }

    @When("^Admin provides all card info$")
    public void fillInNewCardInfo(){
        getPortalBillingDetailsPage().getAddPaymentMethodWindow().fillInNewCardInfo();
    }

    @Then("^'Add payment method' button is disabled$")
    public void verifyAddPaymentDisabled(){
        Assert.assertFalse(getPortalBillingDetailsPage().getAddPaymentMethodWindow().isAddPaymentButtonEnabled(),
                "Add payment button is not disabled");
    }


    @When("^Selects all checkboxes for adding new payment$")
    public void checkAllCheckBoxesForAddindNewPayment(){
        getPortalBillingDetailsPage().getAddPaymentMethodWindow().checkAllCheckboxesForAddingNewPayment();
    }

    @When("^Admin selects (.*) terms checkbox$")
    public void selectCheckBoxForAddingNewPayment(int checkboxNumber){
        getPortalBillingDetailsPage().getAddPaymentMethodWindow().clickCheckBox(checkboxNumber);
    }

    @When("^Admin clicks 'Add payment method' button$")
    public void clickAddPaymentButtonInAddPaymentWindow(){
        getPortalBillingDetailsPage().getAddPaymentMethodWindow().clickAddPaymentButton();
        getPortalBillingDetailsPage().getAddPaymentMethodWindow().waitForAddingNewPaymentConfirmationPopup();
    }

    @When("^Admin clicks 'Next' button$")
    public void clickNextButtonInAddPaymentWindow(){
        getPortalBillingDetailsPage().getAddPaymentMethodWindow().clickNextButton();
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

    @When("^Admin clicks Manage -> Remove payment$")
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
        getPortalMainPage().getCartPage().getConfirmPaymentDetailsWindow().clickNexButtonOnDetailsTab();
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
        getPortalMainPage().waitForNotificationAlertToDisappear();
        Assert.assertTrue(getPortalMainPage().getCartPage().getConfirmPaymentDetailsWindow().isPaymentReviewTabOpened(),
                "Admin is not redirected to PaymentReview tab after adding new card.");

    }

    @When("^Admin closes Confirm details window$")
    public void closeConfirmDetailsWindow(){
        getPortalMainPage().getCartPage().getConfirmPaymentDetailsWindow().closeWindow();
    }

    @When("^Click 'Manage' button for (.*) user$")
    public void clickManageButtonForUser(String fullName){
        if(fullName.equalsIgnoreCase("created")){
            fullName =  AGENT_FIRST_NAME + " " + AGENT_LAST_NAME;
        }
        portalUserProfileEditingThreadLocal.set(
                getPortalManagingUsersPage().clickManageButtonForUser(fullName)
        );
    }

    @When("^Click 'Upload' button$")
    public void clickUploadButtonForUser(){
        portalUserProfileEditingThreadLocal.get().clickUploadPhotoButton();
    }

    @When("^Click 'Upload' button for tenant logo$")
    public void clickUploadButtonForTenantLogo(){
        getPortalTouchPrefencesPage().getconfigureBrandWindow().clickuploadButton();
    }

    @When("^Admin clicks 'Edit user roles'$")
    public void clickEditRoles(){
        portalUserProfileEditingThreadLocal.get().clickEditUserRolesButton();
    }

    @When("^Admin clicks Delete user button$")
    public void deleteAgentUser(){
        portalUserProfileEditingThreadLocal.get().clickDeleteButton();
        portalUserProfileEditingThreadLocal.get().waitWhileProcessing();
    }

    @Then("^User is removed from Manage agent users page$")
    public void verifyAgentDeletedManageAgentsPage(){
        String fullName = AGENT_FIRST_NAME + " " + AGENT_LAST_NAME;
        Assert.assertFalse(getPortalManagingUsersPage().isUserShown(fullName, 2000),
                fullName + " agent is not removed from Manage agent users page after deleting");
    }

    @Then("^(.*) is removed from User management page$")
    public void verifyAgentDeleted(String user){
        String fullName = AGENT_FIRST_NAME + " " + AGENT_LAST_NAME;
        Assert.assertFalse(getPortalUserManagementPage().isUserShown(fullName, 1200),
                fullName + " agent is not removed from User management page");
    }

    @Then("^(.*) added to User management page$")
    public void verifyAgentAdded(String user){
        String fullName = AGENT_FIRST_NAME + " " + AGENT_LAST_NAME;
        if(user.contains("Updated")) fullName = updatedAgentInfo.get("firstName") + " " + updatedAgentInfo.get("lastName");
        Assert.assertFalse(getPortalUserManagementPage().isUserShown(fullName, 2000),
                fullName + " agent is not removed from User management page after deleting");
    }

    @When("^Admin updates agent's personal details$")
    public void updateAgentDetails(){
        updatedAgentInfo = new HashMap<>();
        updatedAgentInfo.put("firstName", faker.name().firstName());
        updatedAgentInfo.put("lastName", faker.name().lastName());
        updatedAgentInfo.put("email", "aqa_"+System.currentTimeMillis()+"@aqa.com");

        portalUserProfileEditingThreadLocal.get().updateAgentPersonalDetails(updatedAgentInfo);
        portalUserProfileEditingThreadLocal.get().waitWhileProcessing();
    }

    @When("^Upload (.*)")
    public void uploadPhoto(String photoStrategy){
        portalUserProfileEditingThreadLocal.get().uploadPhoto(System.getProperty("user.dir") + "/src/test/resources/agentphoto/agent_photo.png");
    }


    @When("^Upload: foto for tenant$")
    public void uploadFotoForTenant() {
        getPortalTouchPrefencesPage().getconfigureBrandWindow().uploadPhoto(System.getProperty("user.dir") + "/src/test/resources/agentphoto/tenant.png");
    }

    @Then("^Change secondary color to '(.*)' for tenant$")
    public void changeSecondaryColorForTenant(String hex) {
        COLOR = getPortalTouchPrefencesPage().getconfigureBrandWindow().getSecondaryColor();
        if (!hex.contains(COLOR)) {
            getPortalTouchPrefencesPage().getconfigureBrandWindow().setSecondaryColor(hex);
            getPortalTouchPrefencesPage().clickSaveButton();
            getPortalTouchPrefencesPage().waitWhileProcessing();
        }
    }

    @Then("^Change primary color to '(.*)' for tenant$")
    public void changePrimaryColorForTenant(String hex) {
        COLOR = getPortalTouchPrefencesPage().getconfigureBrandWindow().getPrimaryColor();
        if (!hex.contains(COLOR)) {
            getPortalTouchPrefencesPage().getconfigureBrandWindow().setPrimaryColor(hex);
            getPortalTouchPrefencesPage().clickSaveButton();
            getPortalTouchPrefencesPage().waitWhileProcessing();
        }
    }

    @When("^Add new touch (.*) solution$")
    public void addNewTouchSolution(String touchRole){
        portalUserProfileEditingThreadLocal.get().getEditUserRolesWindow()
                                                .selectNewTouchRole(touchRole)
                                                .clickFinishButton();
        portalUserProfileEditingThreadLocal.get().waitWhileProcessing();
    }

    @When("^Add new platform (.*) solution$")
    public void addNewPlatformSolution(String role){
        portalUserProfileEditingThreadLocal.get().getEditUserRolesWindow()
                .selectNewPlatformRole(role)
                .clickFinishButton();
        portalUserProfileEditingThreadLocal.get().waitWhileProcessing();
    }

    @Given("^Agent of (.*) tenant has no photo uploaded$")
    public void deleteAgentPhoto(String tenantOrgName){
        Tenants.setTenantUnderTestNames(tenantOrgName);
        ApiHelper.deleteAgentPhotoForMainAQAAgent(Tenants.getTenantUnderTestOrgName());
    }

    @When("^Admin logs out from portal$")
    public void logoutFromPortal(){
        portalUserProfileEditingThreadLocal.get().getPageHeader().logoutAdmin();
    }

    @Then("^Newly created agent is (?:deleted|absent) on backend$")
    public void verifyUserDeleted(){
        Assert.assertFalse(ApiHelperPlatform.isActiveUserExists(Tenants.getTenantUnderTestOrgName(), AGENT_EMAIL),
                AGENT_EMAIL + " agent is not deleted on backend");
    }

    @Then("^Updated agent is present on backend$")
    public void verifyUserUpdated(){
        Assert.assertTrue(ApiHelperPlatform.isActiveUserExists(Tenants.getTenantUnderTestOrgName(), updatedAgentInfo.get("email")),
                updatedAgentInfo.get("email") + " agent is not present on backend");
    }


    @Then("^New image is saved on portal and backend$")
    public void verifyImageSaveOnPortal(){
        SoftAssert soft = new SoftAssert();
        String imageURLFromBackend = ApiHelper.getAgentInfo(Tenants.getTenantUnderTestOrgName()).jsonPath().get("imageUrl");
        soft.assertFalse(imageURLFromBackend==null,
                        "Agent photo is not saved on backend");
        soft.assertFalse(portalUserProfileEditingThreadLocal.get().getImageURL().isEmpty(),
                "Agent photo is not shown in portal");
        soft.assertAll();
    }

    @Then("^Return secondary color for tenant$")
    public void returnSecondaryColorForTenant() {
        DriverFactory.getDriverForAgent("main").navigate().refresh();
        if (!COLOR.contains(getPortalTouchPrefencesPage().getconfigureBrandWindow().getSecondaryColor())) {
            getPortalTouchPrefencesPage().getconfigureBrandWindow().setSecondaryColor(COLOR);
            getPortalTouchPrefencesPage().clickSaveButton();
            getPortalTouchPrefencesPage().waitWhileProcessing();
        }
    }

    @Then("^Return primary color for tenant$")
    public void returnPrimaryColorForTenant() {
        DriverFactory.getDriverForAgent("main").navigate().refresh();
        if (!COLOR.contains(getPortalTouchPrefencesPage().getconfigureBrandWindow().getPrimaryColor())) {
            getPortalTouchPrefencesPage().getconfigureBrandWindow().setPrimaryColor(COLOR);
            getPortalTouchPrefencesPage().clickSaveButton();
            getPortalTouchPrefencesPage().waitWhileProcessing();
        }
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

    private PortalSignUpPage getPortalSignUpPage(){
        if (portalSignUpPage.get()==null) {
            portalSignUpPage.set(new PortalSignUpPage());
            return portalSignUpPage.get();
        } else{
            return portalSignUpPage.get();
        }
    }

    private PortalLoginPage getPortalLoginPage(){
        if (portalLoginPage.get()==null) {
            portalLoginPage.set(new PortalLoginPage());
            return portalLoginPage.get();
        } else{
            return portalLoginPage.get();
        }
    }

    private PortalAccountDetailsPage getPortalAccountDetailsPage(){
        if (portalAccountDetailsPageThreadLocal.get()==null) {
            portalAccountDetailsPageThreadLocal.set(new PortalAccountDetailsPage());
            return portalAccountDetailsPageThreadLocal.get();
        } else{
            return portalAccountDetailsPageThreadLocal.get();
        }
    }

    private PortalFBIntegrationPage getFBPortalFBIntegrationPage(){
        if (portalFBIntegrationPageThreadLocal.get()==null) {
            portalFBIntegrationPageThreadLocal.set(new PortalFBIntegrationPage());
            return portalFBIntegrationPageThreadLocal.get();
        } else{
            return portalFBIntegrationPageThreadLocal.get();
        }
    }

    private PortalManageAgentUsersPage getPortalManagingUsersPage(){
        if (portalManagingUsersThreadLocal.get()==null) {
            portalManagingUsersThreadLocal.set(new PortalManageAgentUsersPage());
            return portalManagingUsersThreadLocal.get();
        } else{
            return portalManagingUsersThreadLocal.get();
        }
    }

    private PortalTouchPrefencesPage getPortalTouchPrefencesPage(){
        if (portalTouchPrefencesPageThreadLocal.get()==null) {
            portalTouchPrefencesPageThreadLocal.set(new PortalTouchPrefencesPage());
            return portalTouchPrefencesPageThreadLocal.get();
        } else{
            return portalTouchPrefencesPageThreadLocal.get();
        }
    }

    private PortalUserManagementPage getPortalUserManagementPage(){
        if (portalUserManagementPageThreadLocal.get()==null) {
            portalUserManagementPageThreadLocal.set(new PortalUserManagementPage());
            return portalUserManagementPageThreadLocal.get();
        } else{
            return portalUserManagementPageThreadLocal.get();
        }
    }

    private PortalChatConsolePage getPortalChatConsolePage(){
        if (portalChatConsolePage.get()==null) {
            portalChatConsolePage.set(new PortalChatConsolePage());
            return portalChatConsolePage.get();
        } else{
            return portalChatConsolePage.get();
        }
    }


}
