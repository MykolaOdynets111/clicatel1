package steps;

import agentpages.AgentHomePage;
import apihelper.ApiHelper;
import apihelper.ApiHelperPlatform;
import apihelper.ApiHelperTie;
import apihelper.Endpoints;
import com.github.javafaker.Faker;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import datamanager.Agents;
import datamanager.FacebookUsers;
import datamanager.Tenants;
import datamanager.jacksonschemas.AvailableAgent;
import datamanager.jacksonschemas.Intent;
import dbmanager.DBConnector;
import drivermanager.ConfigManager;
import drivermanager.DriverFactory;
import interfaces.JSHelper;
import io.restassured.path.json.exception.JsonPathException;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import portalpages.*;
import portalpages.uielements.AgentRowChatConsole;
import portalpages.uielements.LeftMenu;
import touchpages.pages.MainPage;
import touchpages.pages.Widget;

import java.util.*;

public class BasePortalSteps implements JSHelper {

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
    public static final String EMAIL_FOR_NEW_ACCOUNT_SIGN_UP = "signup_account@aqa.test";
    public static final String PASS_FOR_NEW_ACCOUNT_SIGN_UP = "p@$$w0rd4te$t";
  //  public static final String ACCOUNT_NAME_FOR_NEW_ACCOUNT_SIGN_UP = "automationtest2m15";
    public static final String FIRST_AND_LAST_NAME = "Taras Aqa";
    public static String AGENT_FIRST_NAME;
    public static String AGENT_LAST_NAME;
    private static String AGENT_EMAIL;
    private String AGENT_PASS = "p@$$w0rd4te$t";
    private Map<String, String> updatedAgentInfo;
    public static Map billingInfo = new HashMap();
    private String activationAccountID;
    private static Map<String, String> tenantInfo = new HashMap();
    private Map<String, Integer> chatConsolePretestValue = new HashMap<>();
    private MainPage mainPage;
    private AgentHomePage agentHomePage;
    private AgentHomePage secondAgentHomePage;
    private Widget widget;
    int activeChatsFromChatdesk;
    private String secondAgentNameForChatConsoleTests = "";


    public static Map<String, String> getTenantInfoMap(){
        return  tenantInfo;
    }

    @Given("^New (.*) agent is created$")
    public void createNewAgent(String tenantOrgName){
        Tenants.setTenantUnderTestNames(tenantOrgName);
        AGENT_FIRST_NAME = faker.name().firstName();
        AGENT_LAST_NAME =  faker.name().lastName();
        AGENT_EMAIL = "aqa_"+System.currentTimeMillis()+"@aqa.com";
        Response resp = ApiHelperPlatform.sendNewAgentInvitation(tenantOrgName, AGENT_EMAIL, AGENT_FIRST_NAME, AGENT_LAST_NAME);
        // added wait for new agent to be successfully saved in touch DB before further actions with this agent
        if(resp.statusCode()!=200){
            Assert.assertTrue(false, "Sending new invitation was not successful \n"+
            "Resp status code: " + resp.statusCode() + "\n" +
                    "Resp body: " + resp.getBody().asString());
        }
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
        Tenants.setAccountNameForNewAccountSignUp();
        Tenants.setTenantUnderTestName(Tenants.getAccountNameForNewAccountSignUp());
        getPortalSignUpPage().signUp(FIRST_AND_LAST_NAME, Tenants.getAccountNameForNewAccountSignUp(),
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
        ApiHelperPlatform.closeAccount(Tenants.getAccountNameForNewAccountSignUp(),
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
                Tenants.getAccountNameForNewAccountSignUp());
        for(int i=0; i<4; i++){
            if (activationAccountID==null){
                getPortalSignUpPage().waitFor(1000);
                activationAccountID = DBConnector.getAccountActivationIdFromMC2DB(ConfigManager.getEnv(),
                        Tenants.getAccountNameForNewAccountSignUp());
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
        portalLoginPage.get().enterAdminCreds(AGENT_EMAIL, AGENT_PASS);
        Assert.assertEquals(portalLoginPage.get().getNotificationAlertText(),
                "Username or password is invalid",
                "Error about invalid credentials is not shown");
    }

    @When("^Login into portal as an (.*) of (.*) account$")
    public void loginToPortal(String ordinalAgentNumber, String tenantOrgName){
        Agents portalAdmin = Agents.getAgentFromCurrentEnvByTenantOrgName(tenantOrgName, ordinalAgentNumber);
        portalMainPage.set(
                portalLoginPage.get().login(portalAdmin.getAgentEmail(), portalAdmin.getAgentPass())
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
        portalLoginPage.get().enterAdminCreds(EMAIL_FOR_NEW_ACCOUNT_SIGN_UP, PASS_FOR_NEW_ACCOUNT_SIGN_UP);
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
        getPortalMainPage().waitWhileProcessing(2,5);
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
        try {
            chatConsolePretestValue.put(widgetName, Integer.valueOf(getPortalChatConsolePage().getWidgetValue(widgetName)));
        } catch (NumberFormatException e){
            Assert.assertTrue(false, "Cannot read value from " +widgetName + "chat console widget");
        }
    }

    @Then("^(.*) widget shows correct number$")
    public void checkTotalAgentOnlineValue(String widgetName){
        int actualActiveAgentsCount = Integer.valueOf(getPortalChatConsolePage().getWidgetValue(widgetName));
        chatConsolePretestValue.put(widgetName, actualActiveAgentsCount);
        int loggedInAgentsCountFromBackend = ApiHelper.getNumberOfLoggedInAgents();
        Assert.assertEquals(actualActiveAgentsCount, loggedInAgentsCountFromBackend,
                widgetName + " counter differs from agent online count on backend");
    }

    @Then("^(.*) widget value (?:increased on|set to) (.*)$")
    public void verifyWidgetValue(String widgetName, int incrementer){
        int expectedValue = 0;
        if(incrementer!=0) expectedValue = chatConsolePretestValue.get(widgetName) + incrementer;
        Assert.assertTrue(checkLiveCounterValue(widgetName, expectedValue),
                "'"+widgetName+"' widget value is not updated\n" +
                "Expected value: " + expectedValue);
    }

    @Then("^(.*) counter shows correct live chats number$")
    public void verifyChatConsoleActiveChats(String widgetName){
        activeChatsFromChatdesk = ApiHelper.getActiveChatsBySecondAgent()
                .getBody().jsonPath().getList("content.id").size();
                new AgentHomePage("second agent").getLeftMenuWithChats().getNewChatsCount();
        Assert.assertTrue(checkLiveCounterValue(widgetName, activeChatsFromChatdesk),
                "'"+widgetName+"' widget value is not updated to " + activeChatsFromChatdesk +" expected value \n");
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
        for (int i=0; i<45; i++){
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

    @When("^Wait for auto responders page to load$")
    public void waitForAutoRespondersToLoad(){
        getPortalTouchPrefencesPage().getAutoRespondersWindow().waitToBeLoaded();
        getPortalTouchPrefencesPage().getAutoRespondersWindow().waitForAutoRespondersToLoad();
    }

    @When("^Change chats per agent:\"(.*)\"$")
    public void changeChatPerAgent(String chats){
        getPortalTouchPrefencesPage().getChatDeskWindow().setChatsAvailable(chats);
    }

    @When("^Click \"(.*)\" button (.*) times chats per agent became:\"(.*)\"$")
    public void changeChatPerAgentPlusMinus(String sign, int add, String result){
        if (sign.equals("+")){
            getPortalTouchPrefencesPage().getChatDeskWindow().clickChatsPlus(add);
            Assert.assertEquals(getPortalTouchPrefencesPage().getChatDeskWindow().getChatsAvailable(),result,
                    "Number of available chat was changed not correctly");
        } else if (sign.equals("-")) {
            getPortalTouchPrefencesPage().getChatDeskWindow().clickChatsMinus(add);
            Assert.assertEquals(getPortalTouchPrefencesPage().getChatDeskWindow().getChatsAvailable(),result,
                    "Number of available chat was changed not correctly");
        } else {
            Assert.assertTrue(false,
                    "Unexpected sign. Expected \"\\+\" or \"\\-\"");
        }

    }

    @When("^Error message is shown$")
    public void errorIsShownInWindow(){
        Assert.assertTrue(getPortalTouchPrefencesPage().getChatDeskWindow().isErrorMessageShown(),
                "Error message is not shown");
    }

    @When("^Click off/on  Chat Conclusion$")
    public void clickOffOnChatConclusion(){
        getPortalTouchPrefencesPage().getChatDeskWindow().clickOnOffChatConclusion();
    }


    @When("^Agent click 'Save changes' button$")
    public void agentClickSaveChangesButton() {
        getPortalTouchPrefencesPage().clickSaveButton();
        getPortalTouchPrefencesPage().waitWhileProcessing();
        getPortalTouchPrefencesPage().waitForNotificationAlertToBeProcessed(5,5);
    }


    @When("^Agent click expand arrow for (.*) auto responder$")
    public void clickExpandArrowForAutoResponder(String autoresponder){
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

    @When("Admin click BACK button in left menu")
    public void clickBackButton(){
        getLeftMenu().clickBackButton();
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

    @When("^(.*) the (.*) integration$")
    public void changeIntegrationStateTo(String switchTo, String integration){
        getPortalIntegrationsPage().switchToggleStateTo(integration, switchTo);
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

    @Then("^Billing details is saved on backend (.*)$")
    public void verifyBillingDetails(String testOrgName ){
        SoftAssert soft = new SoftAssert();
        Response resp = ApiHelperPlatform.getAccountBillingInfo(testOrgName);
   //     Response resp = ApiHelperPlatform.getAccountBillingInfo(Tenants.getTenantUnderTestOrgName());
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
        getPortalMainPage().clickPageNavButton(navName);
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
        getPortalTouchPrefencesPage().getConfigureBrandWindow().clickuploadButton();
    }

    @When("^Admin clicks 'Edit user roles'$")
    public void clickEditRoles(){
        portalUserProfileEditingThreadLocal.get().clickEditUserRolesButton();
    }

    @When("^Admin clicks Delete user button$")
    public void deleteAgentUser(){
        portalUserProfileEditingThreadLocal.get().clickDeleteButton();
        portalUserProfileEditingThreadLocal.get().waitForNotificationAlertToBeProcessed(6,5);
    }

    @Then("^User is removed from Manage agent users page$")
    public void verifyAgentDeletedManageAgentsPage(){
        String fullName = AGENT_FIRST_NAME + " " + AGENT_LAST_NAME;
        Assert.assertFalse(getPortalManagingUsersPage().isUserShown(fullName, 800),
                fullName + " agent is not removed from Manage agent users page after deleting");
    }

    @Then("^(.*) is removed from User management page$")
    public void verifyAgentDeleted(String user){
        String fullName = AGENT_FIRST_NAME + " " + AGENT_LAST_NAME;
        Assert.assertFalse(getPortalUserManagementPage().isUserShown(fullName, 800),
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
        portalUserProfileEditingThreadLocal.get().waitForNotificationAlertToBeProcessed(5,5);
    }

    @When("^Upload (.*)")
    public void uploadPhoto(String photoStrategy){
        portalUserProfileEditingThreadLocal.get().uploadPhoto(System.getProperty("user.dir") + "/src/test/resources/agentphoto/agent_photo.png");
        portalUserProfileEditingThreadLocal.get().waitForNotificationAlertToBeProcessed(3,6);
    }


    @When("^Upload: photo for tenant$")
    public void uploadPhotoForTenant() {
        getPortalTouchPrefencesPage().getConfigureBrandWindow().uploadPhoto(System.getProperty("user.dir") + "/src/test/resources/agentphoto/tenant.png");
    }

    @Then("^Change secondary color for tenant$")
    public void changeSecondaryColorForTenant() {
        tenantInfo.put("color", getPortalTouchPrefencesPage().getConfigureBrandWindow().getSecondaryColor());
        tenantInfo.put("newColor", getPortalTouchPrefencesPage().getConfigureBrandWindow().setRandomSecondaryColor(tenantInfo.get("color")));
        getPortalTouchPrefencesPage().clickSaveButton();
        getPortalTouchPrefencesPage().waitWhileProcessing();
    }

    @Then("^I check secondary color for tenant in widget$")
    public void iCheckSecondaryColorForTenantInWidget() {
        Assert.assertEquals(getMainPage().getTenantNameColor(), tenantInfo.get("newColor"), "Color for tenant name in widget window is not correct");
    }

    public Widget clickChatIcon() {
        widget = getMainPage().openWidget();
//        widgetConversationArea = widget.getWidgetConversationArea();
//        widgetConversationArea.waitForSalutation();
        return widget;
    }


    @Then("^I check primary color for tenant in widget$")
    public void iCheckPrimaryColorForTenantInWidget() {
        SoftAssert soft = new SoftAssert();
        soft.assertEquals(getMainPage().getChatIconColor(), tenantInfo.get("newColor"), "Color for tenant open widget button is not correct");
        soft.assertEquals(getMainPage().getHeaderColor(), tenantInfo.get("newColor"), "Color for tenant header in widget window is not correct");
        soft.assertAll();
    }


    @Then("^I check primary color for tenant in agent desk$")
    public void iCheckPrimaryColorForTenantInAgentDesk() {
        SoftAssert soft = new SoftAssert();
        soft.assertEquals(getAgentHomePage("second agent").getCustomer360ButtonColor(), tenantInfo.get("newColor"), "Color for tenant 'Costomer' is not correct");
        soft.assertEquals(getAgentHomePage("second agent").getLeftMenuWithChats().getExpandFilterButtonColor(), tenantInfo.get("newColor"), "Color for tenant dropdown button is not correct");
        soft.assertEquals(getAgentHomePage("second agent").getTouchButtonColor(), tenantInfo.get("newColor"), "Color for tenant chat button is not correct");
        soft.assertAll();
    }


    @Then("^I check secondary color for tenant in agent desk$")
    public void iCheckSecondaryColorForTenantInAgentDesk() {
        SoftAssert soft = new SoftAssert();
        soft.assertEquals(getAgentHomePage("second agent").getPageHeader().getTenantNameColor(), tenantInfo.get("newColor"), "Color for tenant name in agent desk window is not correct");
        soft.assertEquals(getAgentHomePage("second agent").getPageHeader().getTenantLogoBorderColor(), tenantInfo.get("newColor"), "Color for tenant logo border in agent desk window is not correct");
        soft.assertAll();
    }

    @Then("^Check primary color for incoming chat and 360Container$")
    public void checkPrimaryColorForIncomingChatAndContainer() {
        SoftAssert soft = new SoftAssert();
        soft.assertEquals(getAgentHomePage("second agent").getLeftMenuWithChats().getUserMsgCountColor(), tenantInfo.get("newColor"), "Color for tenant logo border in agent desk window is not correct");
        soft.assertEquals(getAgentHomePage("second agent").getLeftMenuWithChats().getUserPictureColor(), tenantInfo.get("newColor"), "Color for User Picture in agent desk window is not correct");
        soft.assertEquals(getAgentHomePage("second agent").getCustomer360Container().getUserPictureColor(), tenantInfo.get("newColor"), "Color for User Picture in 360container in agent desk window is not correct");
        soft.assertEquals(getAgentHomePage("second agent").getCustomer360Container().getSaveEditButtonColor(), tenantInfo.get("newColor"), "Color for Edit button in 360container in agent desk window is not correct");
        soft.assertEquals(getAgentHomePage("second agent").getCustomer360Container().getMailColor(), tenantInfo.get("newColor"), "Color for Email in 360container in agent desk window is not correct");
        soft.assertEquals(getAgentHomePage("second agent").getChatHeader().getPinChatButtonColor(), tenantInfo.get("newColor"), "Color for Pin chat button in agent desk window is not correct");
        soft.assertEquals(getAgentHomePage("second agent").getChatHeader().getTransferButtonColor(), tenantInfo.get("newColor"), "Color for Transfer chat button in agent desk window is not correct");
        soft.assertEquals(getAgentHomePage("second agent").getChatHeader().getEndChatButtonColor(), tenantInfo.get("newColor"), "Color for End chat button in agent desk window is not correct");
        soft.assertEquals(getAgentHomePage("second agent").getChatForm().getSubmitMessageButton(), tenantInfo.get("newColor"), "Color for Send button in agent desk window is not correct");
        soft.assertAll();
    }

    @Then("^Change primary color for tenant$")
    public void changePrimaryColorForTenant() {
        tenantInfo.put("color", getPortalTouchPrefencesPage().getConfigureBrandWindow().getPrimaryColor());
        tenantInfo.put("newColor", getPortalTouchPrefencesPage().getConfigureBrandWindow().setRandomPrimaryColor(tenantInfo.get("color")));
        getPortalTouchPrefencesPage().clickSaveButton();
        getPortalTouchPrefencesPage().waitWhileProcessing();
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
        portalUserProfileEditingThreadLocal.get().waitForNotificationAlertToBeProcessed(5, 9);
    }

    @Given("^Agent of (.*) tenant has no photo uploaded$")
    public void deleteAgentPhoto(String tenantOrgName){
        Tenants.setTenantUnderTestNames(tenantOrgName);
        ApiHelper.deleteAgentPhotoForMainAQAAgent(Tenants.getTenantUnderTestOrgName());
    }

    @Given("^(.*) tenant has no brand image$")
    public void deleteTenantBrandImage(String tenantOrgName){
        Tenants.setTenantUnderTestNames(tenantOrgName);
        ApiHelper.deleteTenantBrandImage(Tenants.getTenantUnderTestOrgName());
        String fileType = ApiHelper.getTenantBrandImage(Tenants.getTenantUnderTestOrgName()).contentType();
        String fileTypeTrans = ApiHelper.getTenantBrandImageTrans(Tenants.getTenantUnderTestOrgName()).contentType();
        SoftAssert soft = new SoftAssert();
        soft.assertFalse(fileType.contains("image"),
                "Image was not deleted on backend");
        soft.assertFalse(fileTypeTrans.contains("image"),
                "Image for chat desk (tenant_logo_trans) was not deleted on backend");
        soft.assertAll();
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

    @Then("^New brand image is saved on backend for (.*) tenant$")
    public void verifyBrandImageSaveOnPortal(String tenantOrgName){
        Tenants.setTenantUnderTestNames(tenantOrgName);
        String fileType = ApiHelper.getTenantBrandImage(Tenants.getTenantUnderTestOrgName()).contentType();
        String fileTypeTrans = ApiHelper.getTenantBrandImageTrans(Tenants.getTenantUnderTestOrgName()).contentType();
        SoftAssert soft = new SoftAssert();
        soft.assertTrue(fileType.contains("image"),
                "Image is not saved on backend");
        soft.assertTrue(fileTypeTrans.contains("image"),
                "Image for chat desk (tenant_logo_trans) is not saved on backend");
        soft.assertAll();
    }

    @Then("^Return secondary color for tenant$")
    public void returnSecondaryColorForTenant() {
        DriverFactory.getDriverForAgent("main").navigate().refresh();
        if (!tenantInfo.get("color").contains(getPortalTouchPrefencesPage().getConfigureBrandWindow().getSecondaryColor())) {
            getPortalTouchPrefencesPage().getConfigureBrandWindow().setSecondaryColor(tenantInfo.get("color"));
            getPortalTouchPrefencesPage().clickSaveButton();
            getPortalTouchPrefencesPage().waitWhileProcessing();
        }
    }

    @Then("^Return primary color for tenant$")
    public void returnPrimaryColorForTenant() {
        DriverFactory.getDriverForAgent("main").navigate().refresh();
        if (!tenantInfo.get("color").contains(getPortalTouchPrefencesPage().getConfigureBrandWindow().getPrimaryColor())) {
            getPortalTouchPrefencesPage().getConfigureBrandWindow().setPrimaryColor(tenantInfo.get("color"));
            getPortalTouchPrefencesPage().clickSaveButton();
            getPortalTouchPrefencesPage().waitWhileProcessing();
        }
    }

    @And("^Change business details$")
    public void changeBusinessDetails() {
        tenantInfo.put("companyName", "New company name "+faker.lorem().word());
        tenantInfo.put("companyCity", "San Francisco "+faker.lorem().word());
        tenantInfo.put("companyIndustry", getPortalTouchPrefencesPage().getAboutYourBusinessWindow().selectRandomIndastry());
        tenantInfo.put("companyCountry", getPortalTouchPrefencesPage().getAboutYourBusinessWindow().selectRandomCountry());
        getPortalTouchPrefencesPage().getAboutYourBusinessWindow().setCompanyName(tenantInfo.get("companyName"));
        getPortalTouchPrefencesPage().getAboutYourBusinessWindow().setCompanyCity(tenantInfo.get("companyCity"));
        getPortalTouchPrefencesPage().clickSaveButton();
        getPortalTouchPrefencesPage().waitWhileProcessing();
    }

    @And("^Refresh page and verify business details was changed for (.*)$")
    public void refreshPageAndVerifyItWasChanged(String tenantOrgName) {
        SoftAssert soft = new SoftAssert();
        getPortalTouchPrefencesPage().getAboutYourBusinessWindow().getCompanyCountry();
        Response resp = ApiHelper.getTenantInfo(tenantOrgName);
        DriverFactory.getDriverForAgent("main").navigate().refresh();
        String country = DBConnector.getCountryName(ConfigManager.getEnv(),resp.jsonPath().getList("tenantAddresses.country").get(0).toString());
        soft.assertEquals(getPortalTouchPrefencesPage().getAboutYourBusinessWindow().getCompanyName(),tenantInfo.get("companyName"), "Company name was not changed");
        soft.assertEquals(resp.jsonPath().get("tenantOrgName"),tenantInfo.get("companyName"), "Company name was not changed on backend");
        soft.assertEquals(getPortalTouchPrefencesPage().getAboutYourBusinessWindow().getCompanyCity(),tenantInfo.get("companyCity"), "Company city was not changed");
        soft.assertEquals(resp.jsonPath().getList("tenantAddresses.city").get(0).toString(),tenantInfo.get("companyCity"), "Company city was not changed on backend");
        soft.assertEquals(getPortalTouchPrefencesPage().getAboutYourBusinessWindow().getCompanyIndustry(),tenantInfo.get("companyIndustry"), "Company industry was not changed");
        soft.assertEquals(resp.jsonPath().get("category"),tenantInfo.get("companyIndustry"), "Company industry was not changed on backend");
        soft.assertEquals(getPortalTouchPrefencesPage().getAboutYourBusinessWindow().getCompanyCountry(),tenantInfo.get("companyCountry"), "Company country was not changed");
        soft.assertEquals(country,tenantInfo.get("companyCountry"), "Company country was not changed on backend");
        getPortalTouchPrefencesPage().getAboutYourBusinessWindow().setCompanyName("Automation Bot");
        getPortalTouchPrefencesPage().clickSaveButton();
        getPortalTouchPrefencesPage().waitWhileProcessing();
        soft.assertAll();
    }

    @Then("'No agents online' on Agents tab shown if there is no online agent")
    public void verifyNoAgentsOnline(){
        if(ApiHelper.getNumberOfLoggedInAgents()==0) {
            Assert.assertTrue(getPortalChatConsolePage().isNoAgentsOnlineShown(),
                    "'No agents online' are not shown while there is no logged in agents");
        }
    }

    @Then("^(.*) is marked with a green dot in chat console$")
    public void verifyAgentMarkedWithAGreenDot(String agent){
        secondAgentNameForChatConsoleTests =  ApiHelper.getAvailableAgents().stream()
                .filter(e -> e.getEmail().equalsIgnoreCase(
                        Agents.getAgentFromCurrentEnvByTenantOrgName(Tenants.getTenantUnderTestOrgName(), agent).getAgentEmail()))
                .findFirst().get().getAgentFullName();
        Assert.assertTrue(getPortalChatConsolePage().getAgentsTableChatConsole()
                        .getTargetAgentRow(secondAgentNameForChatConsoleTests).isActiveChatsIconShown(40),
                secondAgentNameForChatConsoleTests + " agent is not marked with green dot after receiving new chat in chatdesk");
    }

    @Then("^(.*) is marked with a yellow dot in chat console$")
    public void verifyAgentMarkedWithAYellowDot(String agent){
        secondAgentNameForChatConsoleTests =  ApiHelper.getAvailableAgents().stream()
                .filter(e -> e.getEmail().equalsIgnoreCase(
                        Agents.getAgentFromCurrentEnvByTenantOrgName(Tenants.getTenantUnderTestOrgName(), agent).getAgentEmail()))
                .findFirst().get().getAgentFullName();
        Assert.assertTrue(getPortalChatConsolePage().getAgentsTableChatConsole()
                        .getTargetAgentRow(secondAgentNameForChatConsoleTests).isNoActiveChatsIconShown(40),
                secondAgentNameForChatConsoleTests + " agent is not marked with green dot after receiving new chat in chatdesk");

    }

    @Then("^Correct number of active chats shown for (.*)$")
    public void verifyChatConsoleAgentsContainsChats(String agent){
        int activeChatsFromChatdesk = new AgentHomePage("second agent").getLeftMenuWithChats().getNewChatsCount();
        Assert.assertEquals(getPortalChatConsolePage().getAgentsTableChatConsole()
                        .getTargetAgentRow(secondAgentNameForChatConsoleTests).getActiveChatsNumber(),
                activeChatsFromChatdesk,
                secondAgentNameForChatConsoleTests + " icon has incorrect number of active chats");

    }

    @When("^Admin clicks expand dot for (.*)$")
    public void expandAgentsRowInChatConsole(String agent){
        getPortalChatConsolePage().getAgentsTableChatConsole()
                .getTargetAgentRow(secondAgentNameForChatConsoleTests)
                .clickExpandButton();
    }

    @Then("Logged in agents shown in Agents chat console tab")
    public void verifySecondAgentAppearsInAgentsTab(){
        SoftAssert soft = new SoftAssert();
        List<AvailableAgent> agents = ApiHelper.getAvailableAgents();
        for(AvailableAgent agent : agents){
            soft.assertTrue(getPortalChatConsolePage().getAgentsTableChatConsole().isAgentShown(agent.getAgentFullName(), 35),
                    agent.getAgentFullName() + " agent is not shown in online agents table on chat console");
        }
        soft.assertAll();
    }


    @Then("^All chats info are shown for (.*) including intent on user message (.*)$")
    public void verifyActiveChatInfoOnChatConsole(String agent, String userMessage){
        SoftAssert soft = new SoftAssert();

        String userId = getUserNameFromLocalStorage();
        String sentiment = ApiHelperTie.getTIESentimentOnMessage(userMessage);
        String intent = ApiHelperTie.getListOfIntentsOnUserMessage(userMessage).get(0).getIntent();

        AgentRowChatConsole agentUnderTest = getPortalChatConsolePage().getAgentsTableChatConsole()
                .getTargetAgentRow(secondAgentNameForChatConsoleTests);

        if(!agentUnderTest.isChatShownFromUserShown(userId, 40)){
            Assert.fail("Chat from '" + userId + "' user is not shown in chat console for " +
                    secondAgentNameForChatConsoleTests + " agent");
        }
        List<String> clientIdsWithActiveChatsForTargetAgent = agentUnderTest.getChattingTo();

        int ordinalChatNumber = clientIdsWithActiveChatsForTargetAgent.indexOf(userId);

        soft.assertEquals(agentUnderTest.getChannels().get(ordinalChatNumber),
                "Touch Web chat");
        soft.assertEquals(agentUnderTest.getSentiments().get(ordinalChatNumber),
                sentiment.toLowerCase(), "Sentiment is not correct for "+ userId +" chat ");
        soft.assertEquals(agentUnderTest.getIntents().get(ordinalChatNumber),
                intent, "Intent for "+ userId +" user chat is not correct");
        soft.assertAll();
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

    private MainPage getMainPage() {
        if (mainPage==null) {
            mainPage = new MainPage();
            return mainPage;
        } else{
            return mainPage;
        }
    }

    private AgentHomePage getAgentHomePage(String ordinalAgentNumber){
        if (ordinalAgentNumber.equalsIgnoreCase("second agent")){
            return getAgentHomeForSecondAgent();
        } else {
            return getAgentHomeForMainAgent();
        }
    }

    private AgentHomePage getAgentHomeForSecondAgent(){
        if (secondAgentHomePage==null) {
            secondAgentHomePage = new AgentHomePage("second agent");
            return secondAgentHomePage;
        } else{
            return secondAgentHomePage;
        }
    }

    private AgentHomePage getAgentHomeForMainAgent(){
        if (agentHomePage==null) {
            agentHomePage = new AgentHomePage("main agent");
            return agentHomePage;
        } else{
            return agentHomePage;
        }
    }


}
