package steps.portalsteps;


import apihelper.ApiHelper;
import com.github.javafaker.Faker;
import datamanager.Agents;
import datamanager.MC2Account;
import datamanager.Tenants;
import datamanager.TopUpBalanceLimits;
import datamanager.jacksonschemas.TenantChatPreferences;
import datamanager.model.PaymentMethod;
import dbmanager.DBConnector;
import driverfactory.DriverFactory;
import driverfactory.URLs;
import drivermanager.ConfigManager;
import emailhelper.CheckEmail;
import emailhelper.GmailConnector;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.exception.JsonPathException;
import io.restassured.response.Response;
import mc2api.ApiHelperPlatform;
import mc2api.auth.PortalAuthToken;
import mc2api.endpoints.EndpointsPlatform;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.asserts.SoftAssert;
import portalpages.PaymentMethodPage;
import portalpages.PortalLoginPage;
import portalpages.PortalSignUpPage;
import portalpages.PortalUserEditingPage;
import socialaccounts.FacebookUsers;
import socialaccounts.TwitterUsers;
import steps.CamundaFlowsSteps;
import steps.agentsteps.AbstractAgentSteps;
import steps.agentsteps.AgentCRMTicketsSteps;
import touchpages.pages.MainPage;

import java.util.*;
import java.util.stream.Collectors;

public class BasePortalSteps extends AbstractPortalSteps {

    public static final String FIRST_AND_LAST_NAME = "Clickatell Test";
    public static String AGENT_FIRST_NAME;
    public static String AGENT_LAST_NAME;
    public static String AGENT_EMAIL;
    private String AGENT_PASS = Agents.TOUCH_GO_SECOND_AGENT.getAgentPass();
    private Map<String, String> updatedAgentInfo;
    public static Map billingInfo = new HashMap();
    private String activationAccountID;
    private static final Map<String, String> tenantInfo = new HashMap<>();
    private final Map<String, Integer> chatConsolePretestValue = new HashMap<>();
    private MainPage mainPage;
    int activeChatsFromChatdesk;
    private final Map<String, Double> topUpBalance = new HashMap<>();
    private String accountCurrency;
    private String confirmationURL;
    public static String tagname;

    public static Map<String, String> getTenantInfoMap() {
        return tenantInfo;
    }

    @Given("^(.*) New (.*) agent is created$")
    public void createNewAgent(String agentEmail, String tenantOrgName) {
        if (agentEmail.equalsIgnoreCase("brand")) {
            AGENT_EMAIL = "aqa_" + System.currentTimeMillis() + "@aqa.com";
        } else {
            AGENT_EMAIL = generatePredefinedAgentEmail();
        }
        Agents.TOUCH_GO_SECOND_AGENT.setEmail(AGENT_EMAIL);
        Tenants.setTenantUnderTestNames(tenantOrgName);
        AGENT_FIRST_NAME = faker.name().firstName();
        AGENT_LAST_NAME = faker.name().lastName();
        AGENT_EMAIL = Agents.TOUCH_GO_SECOND_AGENT.getAgentEmail();

        AbstractAgentSteps.getListOfCreatedAgents().add(new HashMap<String, String>() {{
            put("name", AGENT_FIRST_NAME + " " + AGENT_LAST_NAME);
            put("mail", AGENT_EMAIL);
        }});

//        System.out.println("Adding the agent to the list = " +  AGENT_FIRST_NAME + " " + AGENT_LAST_NAME + " mail: " + AGENT_EMAIL);
//        System.out.println("Number of agents in the list after adding = " + AbstractAgentSteps.getListOfCreatedAgents().size());
//        for (Map<String, String> map : AbstractAgentSteps.getListOfCreatedAgents()){
//            for (String key: map.keySet()){
//                System.out.println("Agent in the list after adding"  + map.get("name") + " and mail: " + map.get("mail"));
//            }
//        }

        Response resp = ApiHelperPlatform.sendNewAgentInvitation(tenantOrgName, AGENT_EMAIL, AGENT_FIRST_NAME, AGENT_LAST_NAME);
        // added wait for new agent to be successfully saved in touch DB before further actions with this agent
        if (resp.statusCode() != 200) {
            Assert.fail("Sending new invitation was not successful \n" +
                    "Resp status code: " + resp.statusCode() + "\n" +
                    "Resp body: " + resp.getBody().asString());
        }

        waitFor(1500);

        String invitationID = DBConnector.getInvitationIdForCreatedUserFromMC2DB(ConfigManager.getEnv(), AGENT_EMAIL);
        ApiHelperPlatform.acceptInvitation(tenantOrgName, invitationID, AGENT_PASS);
    }

    private String generatePredefinedAgentEmail() {
        String[] email = Agents.TOUCH_GO_SECOND_AGENT.getOriginalEmail().split("@");
        return email[0] + "+" + System.currentTimeMillis() + "@gmail.com";
    }

    @When("^Create new Agent$")
    public void createNewAgent() {
        AGENT_FIRST_NAME = faker.name().firstName();
        AGENT_LAST_NAME = faker.name().lastName();
        AGENT_EMAIL = generatePredefinedAgentEmail();

        Agents.TOUCH_GO_SECOND_AGENT.setEnv(ConfigManager.getEnv());
        Agents.TOUCH_GO_SECOND_AGENT.setTenant(MC2Account.TOUCH_GO_NEW_ACCOUNT.getTenantOrgName());
        Agents.TOUCH_GO_SECOND_AGENT.setEmail(AGENT_EMAIL);

        AbstractAgentSteps.getListOfCreatedAgents().add(new HashMap<String, String>() {{
            put("name", AGENT_FIRST_NAME + " " + AGENT_LAST_NAME);
            put("mail", AGENT_EMAIL);
        }});

        getPortalManagingUsersPage().getAddNewAgentWindow()
                .createNewAgent(AGENT_FIRST_NAME, AGENT_LAST_NAME, AGENT_EMAIL);
        getPortalManagingUsersPage().waitWhileProcessing(2, 3);
        getPortalManagingUsersPage().waitForNotificationAlertToBeProcessed(2, 3);
    }

    private boolean checkThatEmailFromSenderArrives(String sender, int wait) {
        boolean result = false;
        if (ConfigManager.getEnv().equals("testing"))
            result = DBConnector.isAgentCreatedInDB(ConfigManager.getEnv(), Agents.TOUCH_GO_SECOND_AGENT.getAgentEmail());
        else {
            GmailConnector.loginAndGetInboxFolder(Agents.TOUCH_GO_SECOND_AGENT.getAgentEmail(), Agents.TOUCH_GO_SECOND_AGENT.getAgentPass());
            String confirmationEmail = CheckEmail
                    .getConfirmationURL(sender, wait);
            result = !confirmationEmail.equalsIgnoreCase("") ||
                    !confirmationEmail.equalsIgnoreCase("none");
            if (result) {
                try {
                    confirmationURL = confirmationEmail.split("\\[")[1].replace("]", "").trim();
                } catch (ArrayIndexOutOfBoundsException e) {
                    Assert.fail("Unexpected confirmationEmail \n" + confirmationEmail);
                }
            }
        }
        return result;
    }

    @Given("^There is no new emails in target email box$")
    public void cleanUpEmailBox() {
        GmailConnector.loginAndGetInboxFolder(Agents.TOUCH_GO_SECOND_AGENT.getOriginalEmail(), Agents.TOUCH_GO_SECOND_AGENT.getAgentPass());
        CheckEmail.clearEmailInbox();
    }

    @Then("^Confirmation Email arrives$")
    public void verifyConfirmationEmail() {
        boolean result = false;
        if (ConfigManager.getEnv().equals("testing")) {
            String invitationID = DBConnector.getInvitationIdForCreatedUserFromMC2DB(
                    ConfigManager.getEnv(), Agents.TOUCH_GO_SECOND_AGENT.getAgentEmail());
            confirmationURL = EndpointsPlatform.PORTAL_NEW_AGENT_ACTIVATION + invitationID;
            if (!(invitationID == null)) result = true;
        } else {
            result = checkThatEmailFromSenderArrives("Clickatell <mc2-devs@clickatell.com>", 200);
        }
        Assert.assertTrue(result,
                "Confirmation email about creating new agent is not delivered after 200 seconds wait");
    }

    @Then("^Confirmation reset password Email arrives$")
    public void verifyResetPassEmailArrives() {
        boolean result = false;
        if (ConfigManager.getEnv().equals("testing")) {
            String resetPassID = DBConnector.getResetPassId(ConfigManager.getEnv(),
                    Agents.TOUCH_GO_SECOND_AGENT.getOriginalEmail());
            if (resetPassID.equals("none")) {
                getAdminPortalMainPage().waitFor(1500);
                resetPassID = DBConnector.getResetPassId(ConfigManager.getEnv(),
                        Agents.TOUCH_GO_SECOND_AGENT.getOriginalEmail());
            }else{
                result = true;
            }
            confirmationURL = EndpointsPlatform.PORTAL_RESET_PASS_URL + resetPassID;
        }else{
            result = checkThatEmailFromSenderArrives("Clickatell <no-reply@clickatell.com>", 200);
        }
        Assert.assertTrue(result,
                "Confirmation email about resetting agent password is not delivered after 200 seconds wait");

    }

    @Then("^Agent opens confirmation URL$")
    public void clickPasswordRestLink(){
        DriverFactory.getDriverForAgent("admin").get(confirmationURL);
    }

    @When("^Second agent opens confirmation URL$")
    public void openConfirmationURL() {
        DriverFactory.getSecondAgentDriverInstance().get(confirmationURL);
    }

    @Then("^Login screen with new (.*) name opened$")
    public void verifyLoginScreenWithGreeting(String agent){
        String agentFullName = AGENT_FIRST_NAME +" "+ AGENT_LAST_NAME;
        SoftAssert soft = new SoftAssert();
        soft.assertTrue(getPortalLoginPage(agent).getAccountForm().getWelcomeMessage(6).contains(agentFullName),
                        "Welcome message does not contaion "+ agentFullName +"user name");
        soft.assertTrue(getPortalLoginPage(agent).getAccountForm().areCreatePasswordInputsShown(2),
                "There are no 2 input fields for creating agent's password");
        soft.assertAll();

    }

    @Then("^(.*) redirected to the \"(.*)\" page$")
    public void verifySetNewPasswordScreenShown(String agent, String pageName){
        SoftAssert soft = new SoftAssert();
        String pageLabel = getPortalLoginPage(agent).getAccountForm().getNewPasswordLabel();
        soft.assertEquals(pageLabel, pageName,
                "Set new password label is not as expected");
        soft.assertTrue(getPortalLoginPage(agent).getAccountForm().areCreatePasswordInputsShown(2),
                "There are no 2 input fields for creating agent's password");
        soft.assertAll();
    }

    @When("(.*) provides (.*) password and click Login")
    public void createPassword(String agent, String pass){
        if(pass.equals("new")) Agents.TOUCH_GO_SECOND_AGENT.setPass("newp@ssw0rd");
        AGENT_PASS =  Agents.TOUCH_GO_SECOND_AGENT.getAgentPass();
        getPortalLoginPage(agent).getAccountForm().createNewPass(AGENT_PASS)
                                    .clickLogin();
    }

    @Then("^Newly created agent is deleted in DB$")
    public void verifyAgentDelete(){

    }

    @Then("^Agent of (.*) should have all permissions to manage CRM tickets$")
    public void verifyAgentCRMPermissions(String tenantOrgName){
        Tenants.setTenantUnderTestNames(tenantOrgName);
        List<String> expectedPermissions = Arrays.asList("delete-user-profile", "create-user-profile", "update-user-profile", "read-user-profile");
        List<String> permissions = ApiHelperPlatform.getAllRolePermission(tenantOrgName, "Touch agent role");
        Assert.assertTrue(permissions.containsAll(expectedPermissions),
                "Not all CRM permissions are present for Agent role\n" +
                        "Expected permitions: " + expectedPermissions + "\n" +
                        "Full permitions list: " + permissions);
    }

    @Then("^New agent is added into touch database$")
    public void verifyThatNewAgentAddedToDatabase(){
        Assert.assertTrue(DBConnector.isAgentCreatedInDB(ConfigManager.getEnv(), Agents.TOUCH_GO_SECOND_AGENT.getAgentEmail()),
                "Agent with '" + AGENT_EMAIL + "' Email is not created in touch DB after 10 seconds wait.");
    }

    @Given("^Delete user$")
    public static void deleteAgent(){
        ApiHelperPlatform.getListOfAutogeneratedUsersIds(Tenants.getTenantUnderTestOrgName());
        for (String userID: ApiHelperPlatform.getListOfAutogeneratedUsersIds(Tenants.getTenantUnderTestOrgName())){
            ApiHelperPlatform.deleteUser(Tenants.getTenantUnderTestOrgName(), userID);
        }
        AbstractAgentSteps.getListOfCreatedAgents().clear();
    }

    @Given("^Second agent of (.*) account does not exist$")
    public void deleteSecondAgent(String account){
        Tenants.setTenantUnderTestNames(account);
        try{
        deleteAgent();
        }catch (NoSuchElementException e){
            //agent doesn't exists
        }
    }

    @When("^I provide all info about new (.*) account and click 'Sign Up' button$")
    public void fillInFormWithInfoAboutNewAccount(String accountOrgName){
        MC2Account targetAcc;
        if (ConfigManager.debugTouchGo()){
            targetAcc =  MC2Account.TESTING_LOCAL_ACCOUNT;
        } else {
            Faker faker = new Faker();
            String accountName = (faker.name().firstName() + faker.number().randomNumber()).toLowerCase();
            String email = "aqa_" + faker.internet().emailAddress();
            MC2Account.TOUCH_GO_NEW_ACCOUNT.setEmail(email).setTenantOrgName(accountOrgName)
                    .setAccountName(accountName).setEnv(ConfigManager.getEnv());
            targetAcc = MC2Account.TOUCH_GO_NEW_ACCOUNT;
            Agents.TOUCH_GO_ADMIN.setEmail( MC2Account.TOUCH_GO_NEW_ACCOUNT.getEmail())
                    .setTenant(accountOrgName).setEnv(ConfigManager.getEnv());
        }

        Tenants.setTenantUnderTestName(targetAcc.getAccountName());
        Tenants.setTenantUnderTestOrgName(accountOrgName);
        getPortalSignUpPage().signUp(FIRST_AND_LAST_NAME,  targetAcc.getAccountName(),
                targetAcc.getEmail(), targetAcc.getPass());
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

    @Then("^(?:Error|Notification) popup with text (.*) is shown$")
    public void verifyVerificationMessage(String expectedMessage){
        String notificationAlert = getPortalSignUpPage().getNotificationAlertText().trim();
        boolean result = notificationAlert.equals(expectedMessage.trim()) ||
                notificationAlert.equals("Error while sign up");
        Assert.assertTrue(result,
                "Field verification is not working.\n " +
                        "Actual notification alert: " + notificationAlert +"\n" +
                "Expected alert: " + expectedMessage);
    }

    @Then("^(?:Error|Notification) popup with text (.*) is shown for (.*)$")
    public void verifyVerificationMessage(String expectedMessage, String agent){
        String notificationText = getPortalLoginPage(agent).getNotificationAlertText().trim();
        if(expectedMessage.contains("<email>")) {
            expectedMessage = expectedMessage.replace("<email>", Agents.TOUCH_GO_SECOND_AGENT.getAgentEmail());
        }
        getPortalLoginPage(agent).waitForNotificationAlertToBeProcessed(2,4);
        Assert.assertEquals(notificationText, expectedMessage.trim(),
                "Expected notification is not shown");
    }

    @When("^I open portal$")
    public void openPortal(){
        if (ConfigManager.isMc2()) {
            setCurrentPortalLoginPage(PortalLoginPage.openPortalLoginPage(DriverFactory.getDriverForAgent("admin")));
        }else {
            AbstractAgentSteps.getLoginForMainAgent().openPortalLoginPage();
        }
    }

    @When("(.*) test accounts is closed")
    public void closeAllTestAccount(String tenantOrgName){
        Tenants.setTenantUnderTestNames(tenantOrgName);
        MC2Account mc2Account = MC2Account.getAccountByOrgName(ConfigManager.getEnv(), tenantOrgName);
        ApiHelperPlatform.closeAccount(mc2Account.getAccountName(), mc2Account.getEmail(), mc2Account.getPass());
    }

    @When("Portal Sign Up page is opened")
    public void openPortalSignUpPage(){
        PortalSignUpPage.openPortalSignUpPage(DriverFactory.getDriverForAgent("admin"));
        setPortalSignUpPage(new PortalSignUpPage(DriverFactory.getDriverForAgent("admin")));
    }

    @When("I use activation ID and opens activation page")
    public void openActivationAccountPage(){
        String activationURL = String.format(EndpointsPlatform.PORTAL_ACCOUNT_ACTIVATION, activationAccountID);
        DriverFactory.getAgentDriverInstance().get(activationURL);
    }

    @Then("^Page with a message \"Your account has successfully been created!\" is shown$")
    public void verifySuccessMessageIsShown(){
        Assert.assertTrue(getPortalSignUpPage().isSuccessSignUpMessageShown(),
                "Success sign up message is not shown");
    }

    @Then("^Activation ID record is created in DB$")
    public void verifyActivationIDIsCreatedInDB(){
        String accountId = DBConnector.getAccountIdFromMC2DB(ConfigManager.getEnv(),
                MC2Account.getTouchGoAccount().getAccountName());
        activationAccountID = DBConnector.getAccountActivationIdFromMC2DB(ConfigManager.getEnv(),accountId);
        for(int i=0; i<4; i++){
            if (activationAccountID==null){
                getPortalSignUpPage().waitFor(1000);
                activationAccountID = DBConnector.getAccountActivationIdFromMC2DB(ConfigManager.getEnv(), accountId);
            }
        }
        Assert.assertNotNull(activationAccountID,
                "Record with new activation ID is not created in mc2 DB after submitting sign up form");
    }


    @Then("^Login page is opened with a message that activation email has been sent$")
    public void verifyMassageThatConfirmationEmailSent(){
        String targetAccEmail = MC2Account.getTouchGoAccount().getEmail();
        String expectedMessageAboutSentEmail = "A confirmation email has been sent to "+targetAccEmail+"" +
                " to complete your sign up process";
        SoftAssert softAssert = new SoftAssert();
        setCurrentPortalLoginPage(new PortalLoginPage(DriverFactory.getDriverForAgent("admin")));
        softAssert.assertTrue(getCurrentPortalLoginPage().getAccountForm().isMessageAboutConfirmationMailSentShown(),
                "Message that confirmation email was sent is not shown");
        softAssert.assertEquals(getCurrentPortalLoginPage().getAccountForm().getMessageAboutSendingConfirmationEmail(), expectedMessageAboutSentEmail,
                "Message about sent confirmation email is not as expected");
        softAssert.assertAll();
    }

    @Given("Widget is enabled for (.*) tenant")
    public void enableWidget(String tenantOrgName){
        ApiHelper.setIntegrationStatus(tenantOrgName, "webchat", true);
    }

    @Given("(.*) integration status is set to (.*) for (.*) tenant")
    public void changeIntegrationState(String integrationName, String status, String tenantOrgName){
        if (status.equalsIgnoreCase("enabled"))
            ApiHelper.setIntegrationStatus(tenantOrgName, getIntegrationType(integrationName),true);
        else if (status.equalsIgnoreCase("disabled"))
            ApiHelper.setIntegrationStatus(tenantOrgName, getIntegrationType(integrationName),false);
    }

    private String getIntegrationType(String integrationName){
        switch (integrationName.toLowerCase()){
            case "touch":
            case "web widget":
                return "webchat";
            case "facebook messenger":
                return "fbmsg";
            case "facebook posts":
                return "fbpost";
            case "twitter dm":
                return "twdm";
            case "twitter mention":
                return "twmention";
            case "sms":
                return "sms";
            case "whatsapp":
                return "whatsapp";

            default:
                throw new NoSuchElementException("Invalid integration name");
        }
    }

    @Given("^Tenant (.*) has no Payment Methods$")
    public void clearPaymentMethods(String tenantOrgName){
        String authToken = PortalAuthToken.getAccessTokenForPortalUser(tenantOrgName, "main");
        List<PaymentMethod> ids = ApiHelperPlatform.getAllNotDefaultPaymentMethods(authToken)
                .stream().filter(e -> e.getPaymentType().equals("CREDIT_CARD")).collect(Collectors.toList());
        if(ids.size()>0) ids.forEach(e -> ApiHelperPlatform.deletePaymentMethod(authToken, e.getId()));
    }

    @When("^Login as (.*)$")
    public void loginAsCreatedAgent(String agent){
        String email = Agents.TOUCH_GO_SECOND_AGENT.getAgentEmail();
        if(agent.equalsIgnoreCase("updated")) email = updatedAgentInfo.get("email");
        getPortalLoginPage(agent).login(email, Agents.TOUCH_GO_SECOND_AGENT.getAgentPass());
        getAdminPortalMainPage().closeUpdatePolicyPopup();
    }

    @Then("^Deleted agent is not able to log in portal$")
    public void verifyDeletedAgentIsNotLoggedIn(){
        getCurrentPortalLoginPage().getAccountForm().enterAdminCreds(AGENT_EMAIL, AGENT_PASS);
        getCurrentPortalLoginPage().getAccountForm().clickLogin();
        Assert.assertEquals(getCurrentPortalLoginPage().getNotificationAlertText(),
                "Username or password is invalid",
                "Error about invalid credentials is not shown");
    }

    @Then("^(.*) logs in successfully$")
    public void agentLoggsIn(String agent){
        SoftAssert soft = new SoftAssert();
        soft.assertNotEquals(getPortalLoginPage(agent).getNotificationAlertText(),
                "Username or password is invalid",
                "Agent login into portal was not successful\n"
                         + "Agent pass: " + Agents.TOUCH_GO_SECOND_AGENT.getAgentPass() + "\n");
        soft.assertFalse(getPortalLoginPage(agent).isLoginPageOpened(1),
                "Agent login into portal was not successful");
        soft.assertAll();
    }

    @When("^Login into portal as an (.*) of (.*) account$")
    public void loginToPortal(String ordinalAgentNumber, String tenantOrgName){
        Tenants.setTenantUnderTestNames(tenantOrgName);
        if (ConfigManager.isMc2()) {
            Agents portalAdmin = Agents.getAgentFromCurrentEnvByTenantOrgName(tenantOrgName, ordinalAgentNumber);
            setPortalMainPage(
                    getCurrentPortalLoginPage().login(portalAdmin.getAgentEmail(), portalAdmin.getAgentPass())
            );
            waitForAngularToBeReady(getCurrentPortalLoginPage().getCurrentDriver());
        }else {
            AbstractAgentSteps.getLoginForMainAgent().selectTenant(Tenants.getTenantUnderTestName())
                    .selectAgent("agent").clickAuthenticateButton();
        }
    }

    @When("^I click Launchpad button$")
    public void clickLaunchpadButton(){
        getAdminPortalMainPage().clickLaunchpadButton();
    }

    // page_action_to_remove
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

    @When("^Admin confirms (.*) account to close$")
    public void confirmAccountToCLosing(String tenantOrgName){
        MC2Account mc2Account = MC2Account.getAccountByOrgName(ConfigManager.getEnv(), tenantOrgName);
        getPortalAccountDetailsPage().confirmAccount(mc2Account.getEmail(), mc2Account.getPass());
    }

    @When("Admin provides '(.*)' email and '(.*)' pass for account confirmation")
    public void enterConfirmationAccountDetails(String email, String account){
        getPortalAccountDetailsPage().confirmAccount(email, account);
    }

    @Then("^(.*) is not able to login into portal with deleted (.*) account$")
    public void verifyAdminCannotLoginToPortal(String agent,String tenantOrgName){
        setCurrentPortalLoginPage(PortalLoginPage.openPortalLoginPage(DriverFactory.getDriverForAgent(agent)));
        if (!getCurrentPortalLoginPage().isLoginPageOpened(1)){
            getCurrentPortalLoginPage().waitFor(200);
            setCurrentPortalLoginPage(PortalLoginPage.openPortalLoginPage(DriverFactory.getDriverForAgent(agent)));
        }
        MC2Account mc2Account = MC2Account.getAccountByOrgName(ConfigManager.getEnv(), tenantOrgName);
        getCurrentPortalLoginPage().getAccountForm().enterAdminCreds(mc2Account.getEmail(), mc2Account.getPass());
        getCurrentPortalLoginPage().getAccountForm().clickLogin();
        Assert.assertEquals(getCurrentPortalLoginPage().getNotificationAlertText(),
                "Username or password is invalid",
                "Error about invalid credentials is not shown");
    }

    @Then("^Error about invalid credentials is shown$")
    public void verifyInvalidCredsError(){
        Assert.assertEquals(getPortalAccountDetailsPage().getNotificationAlertText(),
                "Invalid username or password.",
                "Error about invalid credentials is not shown");
    }

    @Then("^Portal Page is opened$")
    public void verifyPortalPageOpened(){
        boolean isPortalPageOpened = getAdminPortalMainPage().isPortalPageOpened();
        ConfigManager.setIsNewAccountCreated(String.valueOf(isPortalPageOpened));
        Assert.assertTrue(isPortalPageOpened, "User is not logged in Portal");
    }

    @Given("^New account is successfully created$")
    public void verifyAccountCreated(){
        if(!ConfigManager.isNewAccountCreated()){
            throw new SkipException("Sign up new account was not successful");
        }
    }

    @Then("^New (.*) tenant is created$")
    public void verifyNewTenantCreated(String tenant){
        List<HashMap> allTenants = ApiHelper.getAllTenantsInfo();
        boolean isTenantCreated = allTenants.stream().anyMatch(e -> e.get("tenantOrgName").equals(tenant));
        ConfigManager.setIsNewTenantCreated(String.valueOf(isTenantCreated));
        Assert.assertTrue(isTenantCreated,
                "New tenant is absent in API response tenants?state=ACTIVE");
    }

    @Given("^New tenant is successfully created$")
    public void verifyTenantCreated(){
        if(!ConfigManager.isNewTenantCreated()){
            throw new SkipException("Creating new tenant was not successful");
        }
    }

    @Given("^Payment method is added$")
    public void verifyPaymentAdded(){
        if(!ConfigManager.isPaymentAdded()){
            throw new SkipException("Adding new payment was not successful");
        }
    }

    @Given("^Second agent of (.*) is successfully created$")
    public void verifySecondAgentCreated(String tenant){
        if(!ConfigManager.isSecondAgentCreated()){
            createNewAgent("predefined mail", tenant);
            ConfigManager.setIsSecondCreated("true");
        }
    }

    @Given("^New tenant is successfully upgraded$")
    public void verifyTenantUpgraded(){
        if(System.getProperty("tenantUpgradeSuccessful", "false").equalsIgnoreCase("false")){
            throw new SkipException("Upgrading a new tenant was not successful");
        }
    }

    @Then("^\"Update policy\" pop up is shown$")
    public void verifyUpdatePolicyPopupShown(){
        Assert.assertTrue(getAdminPortalMainPage().isUpdatePolicyPopUpOpened(),
                "User is not logged in Portal");
    }

    @When("^Accept \"Update policy\" popup$")
    public void acceptUpdatedPolicyPopup(){
        getAdminPortalMainPage().closeUpdatePolicyPopup();
        getAdminPortalMainPage().waitWhileProcessing(2, 3);
    }

    @Then("^Landing pop up is shown$")
    public void verifyLandingPopupShown(){
        getAdminPortalMainPage().waitWhileProcessing(3,2);
        Assert.assertTrue(getAdminPortalMainPage().isGetStartedWindowShown(),
                "Landing popup is not shown");
    }

    @When("Close landing popup$")
    public void acceptLandingPopup(){
        getAdminPortalMainPage().closeGetStartedWindow();
    }

    @Then("^Main portal page with welcome message is shown$")
    public void verifyMainPageWithWelcomeMessageShown(){
        Assert.assertEquals(getAdminPortalMainPage().getGreetingMessage(), "Welcome, "+ FIRST_AND_LAST_NAME.split(" ")[0] +
                ". Get started with your Clickatell account.", "Welcome message is not shown.");
    }

    @Then("^\"Get started with Touch\" button is shown$")
    public void verifyGetStartedWithTouchButtonShown(){
        Assert.assertTrue(getAdminPortalMainPage().getLaunchpad().isGetStartedWithTouchButtonShown(),
                "'Get started with Touch' is not shown");
    }

    @When("^Click \"Get started with Touch\" button$")
    public void clickGetStartedWithTouchButton(){
        getAdminPortalMainPage().getLaunchpad().clickGetStartedWithTouchButton();
    }

    @When("^\"Get started with Touch Go\" window is opened$")
    public void verifyStartedWithTouchGoWindowOpened(){
        Assert.assertTrue(getAdminPortalMainPage().isConfigureTouchWindowOpened(),
                "\"Get started with Touch Go\" window is not opened");
    }

    @When("^I try to create new (.*) tenant$")
    public void createNewTenant(String tenantOrgName){
        getAdminPortalMainPage().getConfigureTouchWindow()
                .createNewTenant(tenantOrgName, MC2Account.getTouchGoAccount().getEmail());
    }

    public static boolean isNewUserWasCreated(){
        return AGENT_EMAIL != null;
    }

    @When("^(?:I|Admin) select (.*) in left menu and (.*) in submenu$")
    public void navigateInLeftMenu(String menuItem, String submenu){
        if(ConfigManager.isMc2()){
            navigateInLeftMenuMC2(menuItem,submenu);
        } else {
            AbstractAgentSteps.getLoginForMainAgent().getCurrentDriver().get(URLs.getUrlByNameOfPage(submenu));
        }
    }

    private void navigateInLeftMenuMC2(String menuItem, String submenu){
        getAdminPortalMainPage().waitWhileProcessing(1,5);
        String currentWindow = DriverFactory.getDriverForAgent("main").getWindowHandle();
        getAdminPortalMainPage().waitWhileProcessing(1,5);
        getLeftMenu().navigateINLeftMenuWithSubmenu(menuItem, submenu);

        if(DriverFactory.getDriverForAgent("main").getWindowHandles().size()>1) {
            for (String winHandle : DriverFactory.getDriverForAgent("main").getWindowHandles()) {
                if (!winHandle.equals(currentWindow)) {
                    DriverFactory.getDriverForAgent("main").switchTo().window(winHandle);
                }
            }
        }
    }

    @When("^Agent launch agent desk from portal$")
    public void launchChatdeskFromPortal(){
        getAdminPortalMainPage().launchChatDesk();
        AbstractAgentSteps.getAgentHomeForMainAgent().waitForLoadingInLeftMenuToDisappear(6, 10);
        AbstractAgentSteps.getLeftMenu("agent").waitForConnectingDisappear(6,10);
    }

    @When("^Save (.*) pre-test widget value$")
    public void savePreTestValue(String widgetName){
        try {
            chatConsolePretestValue.put(widgetName, Integer.valueOf(getDashboardPage().getWidgetValue(widgetName)));
        } catch (NumberFormatException e){
            Assert.fail("Cannot read value from " +widgetName + "chat console widget");
        }
    }

    @Then("^(.*) widget shows correct number$")
    public void checkTotalAgentOnlineValue(String widgetName){
        getDashboardPage().waitForConnectingDisappear(1, 5);
        int actualActiveAgentsCount = Integer.parseInt(getDashboardPage().getWidgetValue(widgetName));
        chatConsolePretestValue.put(widgetName, actualActiveAgentsCount);
        getDashboardPage().clickLaunchSupervisor();
        List<String> agentsList = AbstractAgentSteps.getPageHeader("agent").getAvailableAgents();
        int loggedInAgentsCountFromSuperVisorDesk = agentsList.size();
        Assert.assertEquals(actualActiveAgentsCount, loggedInAgentsCountFromSuperVisorDesk,
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
        activeChatsFromChatdesk = ApiHelper.getActiveChatsByAgent("Second agent")
                .getBody().jsonPath().getList("content.id").size();
//                new AgentHomePage("second agent").getLeftMenuWithChats().getNewChatsCount();
        Assert.assertTrue(checkLiveCounterValue(widgetName, activeChatsFromChatdesk),
                "'"+widgetName+"' widget value is not updated to " + activeChatsFromChatdesk +" expected value \n");
    }

    @Then("^Average chats per Agent is correct$")
    public void verifyAverageChatsPerAgent(){
       int actualAverageChats = Integer.valueOf(getDashboardPage().getAverageChatsPerAgent());
       int expectedAverageChats = activeChatsFromChatdesk /  ApiHelper.getNumberOfLoggedInAgents();
       Assert.assertEquals(actualAverageChats, expectedAverageChats,
               "Number of Average chats per Agent is not as expected");
    }

    private boolean checkLiveCounterValue(String widgetName, int expectedValue){
        int actualValue = Integer.valueOf(getDashboardPage().getWidgetValue(widgetName));
        boolean result = false;
        for (int i=0; i<45; i++){
            if(expectedValue!=actualValue){
                getDashboardPage().waitFor(1000);
                actualValue = Integer.valueOf(getDashboardPage().getWidgetValue(widgetName));
            } else {
                result =true;
                break;
            }

        }
        return result;
    }

    @When("^(?:Click|Select) \"(.*)\" (?:nav button|in nav menu)$")
    public void clickNavButton(String navButton){
        getAdminPortalMainPage().clickPageNavButton(navButton);
    }

    @When("^Navigate to (.*) page$")
    public void openSettingPage(String settingsName){
        getDashboardPage().openSettingsPage().openSettingsPage(settingsName);
    }

    @When("^I click \"(.*)\" page action button$")
    public void clickActionButton(String actionButton){
        getAdminPortalMainPage().clickPageActionButton(actionButton);
    }


    @When("^Wait for auto responders page to load$")
    public void waitForAutoRespondersToLoad(){
        getPortalTouchPreferencesPage().getAutoRespondersWindow().waitToBeLoaded();
        getPortalTouchPreferencesPage().getAutoRespondersWindow().waitForAutoRespondersToLoad();
    }

    @When("^Agent click 'Save changes' button$")
    public void agentClickSaveChangesButton() {
        getPortalTouchPreferencesPage().clickSaveButton();
        getPortalTouchPreferencesPage().waitForSaveMessage();
    }

    @When("^Agent click On/Off button for (.*) auto responder$")
    public void clickOnOffForAutoResponder(String autoresponder){
        getPortalTouchPreferencesPage().getAutoRespondersWindow().waitToBeLoaded();
        getPortalTouchPreferencesPage().getAutoRespondersWindow()
                .clickOnOffForMessage(autoresponder);
    }

    @When("^Type new message: (.*) to: (.*) message field$")
    public void typeNewMessage(String message, String autoresponder){
        getPortalTouchPreferencesPage().getAutoRespondersWindow().waitToBeLoaded();
        if (!getPortalTouchPreferencesPage().getAutoRespondersWindow().getTargetAutoResponderItem(autoresponder).isMessageShown()) {
            getPortalTouchPreferencesPage().getAutoRespondersWindow()
                    .clickExpandArrowForMessage(autoresponder);
        }
        getPortalTouchPreferencesPage().getAutoRespondersWindow().getTargetAutoResponderItem(autoresponder).typeMessage(message + faker.letterify("????")).clickSaveButton();
        getPortalTouchPreferencesPage().waitWhileProcessing(1, 4);
    }

    @Then("^(.*) on backend corresponds to (.*) on frontend$")
    public void messageWasUpdatedOnBackend(String tafMessageId, String messageName) {
        String messageOnfrontend = getPortalTouchPreferencesPage().getAutoRespondersWindow().getTargetAutoResponderItem(messageName).getMessage();
        String actualMessage = ApiHelper.getAutoResponderMessageText(tafMessageId);
        Assert.assertEquals(actualMessage, messageOnfrontend,
                messageName + " message is not updated on backend");
    }

    @Then("^(.*) is reset on backend$")
    public void verifyTafMessageIsReset(String autoresponderId){
        String actualMessage = ApiHelper.getAutoResponderMessageText(autoresponderId);
        String defaultMessage = CamundaFlowsSteps.defaultMessage.get();
        Assert.assertEquals(actualMessage, defaultMessage,
                autoresponderId + " message is not reset to default");
    }

    @When("Admin click BACK button in left menu")
    public void clickBackButton(){
        if(getLeftMenu().isBackButtonShown()) getLeftMenu().clickBackButton();
        getAdminPortalMainPage().waitWhileProcessing(2, 14);
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
        getAdminPortalMainPage().upgradePlan(agentSeats);
    }

    @When("^I try to upgrade and buy (.*) agent seats without accept Clickatell's Terms and Conditions$")
    public void upgradeTouchGoPlanWithoutTerms(int agentSeats){
        getAdminPortalMainPage().upgradePlanWithoutTerms(agentSeats);
    }

    @Then("^Payment is not proceeded and Payment Summary tab is still opened$")
    public void verifyPaymentSummaryTabOpened(){
        Assert.assertTrue(getAdminPortalMainPage().getCartPage().getConfirmPaymentDetailsWindow().isPaymentSummaryTabOpened(),
                "'Payment Summary' tab is not still opened when admin tries to proceed without accepting Terms and Conditions");
    }

    @When("^Admin clicks 'Upgrade' button$")
    public void clickUpgradeTouchGoPlanButton(){
        getAdminPortalMainPage().getPageHeader().clickUpgradeButton();
    }

    @Then("^I see \"Payment Successful\" message$")
    public void verifyPaymentSuccessfulMessage(){
        Assert.assertEquals(getAdminPortalMainPage().getCartPage().getConfirmPaymentDetailsWindow().getSuccessMessageMessage(),
                "Payment Successful", "Payment successful message was not shown");
    }

    @Then("^Admin should see \"(.*)\" in the page header$")
    public void verifyTouchGoPlanNamePresence(String expectedTouchGo){
        Assert.assertEquals(getAdminPortalMainPage().getPageHeader().getTouchGoPlanName(), expectedTouchGo,
                "Shown Touch go plan is not as expected.");
    }

    @Then("^Not see \"(.*)\" button$")
    public void verifyNotShowingUpgradeButtonText(String textNotToBeShown){
        Assert.assertNotEquals(getAdminPortalMainPage().getPageHeader().getTextFromBuyingAgentsButton(), textNotToBeShown, "'Add Agent seats' button is shown for Starter Touch Go tenant");
    }

    @Then("^See \"(.*)\" button$")
    public void verifyNShowingUpgradeButtonText(String textToBeShown){
        Assert.assertEquals(getAdminPortalMainPage().getPageHeader().getTextFromBuyingAgentsButton(), textToBeShown, "'Add Agent seats' button is shown for Starter Touch Go tenant");
    }

    @When("^(.*) the (.*) integration$")
    public void changeIntegrationStateTo(String switchTo, String integration){
        getPortalIntegrationsPage().switchToggleStateTo(integration, switchTo);
        getPortalIntegrationsPage().waitWhileProcessing(14, 20);
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
            Assert.fail("FB integration not created");
        }
        Assert.assertEquals(userName, FacebookUsers.USER_FOR_INTEGRATION.getFBUserName(),
                "Incorrect user name in fb integration");
    }

    @Then("^Twitter integration is saved on the backend$")
    public void verifyTwitterIntegrationSaved(){
        String twitterFirstName = ApiHelper.getInfoAboutTwitterIntegration(Tenants.getTenantUnderTestOrgName(),"fullName");
        Assert.assertEquals(twitterFirstName, TwitterUsers.TOUCHGO_USER.getTwitterUserName(),
                "Incorrect user name in Twitter integration");
    }


    @And("^Delete Twitter integration on the backend$")
    public void deleteTwitterIntegrationOnTheBackend() {
        Response deleteTwitterIntegration = (Response) ApiHelper.delinkTwitterIntegration(Tenants.getTenantUnderTestOrgName());
        Assert.assertEquals(deleteTwitterIntegration.statusCode(),200, "Twitter integration is not deleted");
    }

    @Then("^Facebook integration is deleted$")
    public void verifyFBIntegrationDeleted(){
        Response fbIntegrationInfo = (Response) ApiHelper.getInfoAboutFBIntegration(Tenants.getTenantUnderTestOrgName());
        try {
            fbIntegrationInfo.getBody().jsonPath().getString("errorMessage");
        } catch (JsonPathException e){
            Assert.fail("FB integration is not deleted");
        }

    }

    @When("^Click '(?:Configure|Manage|Pay now)' button for (.*) integration$")
    public void clickButtonForIntegrationCard(String integration){
        getPortalIntegrationsPage().clickActionButtonForIntegration(integration);
    }

    @When("^Add fb integration with (.*) fb page$")
    public void makeFBIntegration(String fbPage){
        getPortalIntegrationsPage().getCreateIntegrationWindow().setUpFBIntegration(fbPage);
    }

    @When("^Add twitter integration with twitter page$")
    public void makeTwitterIntegration(){
        getPortalIntegrationsPage().getCreateIntegrationWindow().setUpTwitterIntegration();
    }

    @When("^Delink facebook account$")
    public void delinkFBAccount(){
        getPortalFBIntegrationPage().delinkFBAccount();
    }

    @Then("^Touch Go plan is updated to \"(.*)\" in (.*) tenant configs$")
    public void verifyTouchGoPlanUpdatingInTenantConfig(String expectedTouchGoPlan, String tenantOrgName){
        String actualType = ApiHelper.getInternalTenantConfig(Tenants.getTenantUnderTestName(), "touchGoType");
        for(int i=0; i<120; i++){
            if (!actualType.equalsIgnoreCase(expectedTouchGoPlan)){
                getAdminPortalMainPage().waitFor(15000);
                DriverFactory.getAgentDriverInstance().navigate().refresh();
                actualType = ApiHelper.getInternalTenantConfig(Tenants.getTenantUnderTestName(), "touchGoType");
            } else{
                break;
            }
        }
        boolean isUpgraded = actualType.equalsIgnoreCase(expectedTouchGoPlan);
        System.setProperty("tenantUpgradeSuccessful", String.valueOf(isUpgraded));
        Assert.assertTrue(isUpgraded,
                "TouchGo plan is not updated in tenant configs for '"+tenantOrgName+"' tenant \n"+
                        "Expected: " + expectedTouchGoPlan + "\n" +
                        "Found:" + actualType
        );
    }


    @Then("^Touch Go plan is updated to \"(.*)\" in portal page$")
    public void verifyPlanUpdatingOnPortalPage(String expectedTouchGo){
        DriverFactory.getAgentDriverInstance().navigate().refresh();
        Assert.assertEquals(getAdminPortalMainPage().getPageHeader().getTouchGoPlanName(), expectedTouchGo,
                "Shown Touch go plan is not as expected.");
    }

    @Then("^'Billing Not Setup' pop up (.*) shown$")
    public void verifyBillingNotSetUpPopupShown(String isShown){
        if (isShown.contains("not"))
            Assert.assertFalse(getAdminPortalMainPage().isBillingNotSetUpPopupShown(2),
                    "'Billing Not Setup' pop up still shown");
        else
            Assert.assertTrue(getAdminPortalMainPage().isBillingNotSetUpPopupShown(5),
                "'Billing Not Setup' pop up is not shown");
    }

    @When("^Admin clicks 'Setup Billing' button$")
    public void clickSetupBillingButton(){
        setPortalBillingDetailsPage(
                getAdminPortalMainPage().clickSetupBillingButton()
        );
    }

    @When("Close 'Billing not setup' modal window")
    public void closeSetupBillingModal(){
        getAdminPortalMainPage().closeSetupBillingPopUpModal();
    }

    @Then("^Billing Details page is opened$")
    public void verifyBillingDetailsPageOpened(){
        Assert.assertTrue(getPortalBillingDetailsPage().isPageOpened(5),
                "Admin is not redirected to billing details page");
    }

    @When("^Fill in Billing details$")
    public void fillInBillingDetails(){
        billingInfo = getPortalBillingDetailsPage().getBillingContactsDetails()
                .fillInBillingDetailsForm();
        getPortalBillingDetailsPage().waitWhileProcessing(2,8);
        getPortalBillingDetailsPage().waitForNotificationAlertToBeProcessed(2,2);
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
        try {
            soft.assertEquals(info.get("billingContact").toString(), billingInfo.get("billingContact"),
                    "Billing contact is incorrectly saved");
            soft.assertEquals(info.get("accountTypeId").toString(), billingInfo.get("accountTypeId"),
                    "accountTypeId is incorrectly saved");
            soft.assertEquals(info.get("companyName").toString(), billingInfo.get("companyName"),
                    "Company Name is incorrectly saved");
            soft.assertEquals(billingAddress, billingInfo.get("billingAddress"),
                    "Billing address is incorrectly saved");
            soft.assertAll();
        }catch(NullPointerException e){
            String message = "NULL_POINTER!! \n";
            if(info!=null) {
                if (info.get("billingContact") != null) message = message + "Billing info from mc2 backend: \n"
                        + info.get("billingContact");
                else message = message + resp.getBody().asString();
            }
            if(billingInfo!=null) message = message + "\n\n" + "expected billing info: \n" + billingInfo;
            Assert.fail(message);
        }
    }

    @When("^Admin clicks Top up balance on Billing details$")
    public void clickTopUpOnBilling(){
        getPortalBillingDetailsPage().clickTopUPBalance();
    }

    @Then("^'Top up balance' window is opened$")
    public void verifyTopUpBalanceWindowOpened(){
        Assert.assertTrue(getPortalBillingDetailsPage().getTopUpBalanceWindow().isShown(),
                "'Top up balance' window is not opened");
    }

    @When("^Agent enter allowed top up sum$")
    public void enterNewBalanceAmount(){
        String token = PortalAuthToken.getAccessTokenForPortalUser(Tenants.getTenantUnderTestOrgName(), "main");
        topUpBalance.put("preTest", ApiHelperPlatform.getAccountBalance(token).getBalance());
        String minValue = getPortalBillingDetailsPage().getTopUpBalanceWindow().getMinLimit().trim();
        int addingSum = Integer.valueOf(minValue) + 1;
        double afterTest = topUpBalance.get("preTest") + addingSum;
        topUpBalance.put("afterTest", afterTest);
        getPortalBillingDetailsPage().getTopUpBalanceWindow().enterNewAmount(addingSum);
    }

    @When("^Agent enter (.*) top up amount$")
    public void enterMaxValueForTopUp(String value){
        String token = PortalAuthToken.getAccessTokenForPortalUser(Tenants.getTenantUnderTestOrgName(), "main");
        accountCurrency = ApiHelperPlatform.getAccountBalance(token).getCurrency().toUpperCase();
        int invalidSum;
        if(value.contains("max")){
            invalidSum = TopUpBalanceLimits.getMaxValueByCurrency(accountCurrency) +1 ;
        } else{
            invalidSum = TopUpBalanceLimits.getMinValueByCurrency(accountCurrency) - 1;
        }
        getPortalBillingDetailsPage().getTopUpBalanceWindow().enterNewAmount(invalidSum);
    }

    @Then("\"(.*)\" message is displayed")
    public void verifyMaximumPopup(String baseMessage){
        String expectedMessage;
        if(baseMessage.contains("max_value")){
            int maxValue = TopUpBalanceLimits.getMaxValueByCurrency(accountCurrency);
            expectedMessage = baseMessage.replace("max_value", maxValue + " " + accountCurrency);
        } else{
            int minValue = TopUpBalanceLimits.getMinValueByCurrency(accountCurrency);
            expectedMessage = baseMessage.replace("min_value", minValue + " " + accountCurrency);
        }
        Assert.assertEquals(getPortalBillingDetailsPage().getTopUpBalanceWindow().getErrorWhileAddingPopup(),
                expectedMessage, "Error massage about invalid top up sum is not as expected \n" );
    }

    @When("^Click 'Add to cart' button$")
    public void clickAddToCartButton(){
        getPortalBillingDetailsPage().getTopUpBalanceWindow().clickAddToCardButton();
        getPortalBillingDetailsPage().waitWhileProcessing(14, 20);
    }

    @When("^Make the balance top up payment$")
    public void buyTopUpBalance(){
        getAdminPortalMainPage().getCartPage().clickCheckoutButton();
        getAdminPortalMainPage().checkoutAndBuy(getAdminPortalMainPage().getCartPage());
    }

    @Then("^Top up balance updated up to (.*) minutes$")
    public void verifyTopUpUpdated(int mints){
        String token = PortalAuthToken.getAccessTokenForPortalUser(Tenants.getTenantUnderTestOrgName(), "main");

        Map outputMap = getAdminPortalMainPage().getPageHeader().isTopUpUpdated(token, mints);

        Assert.assertTrue((boolean) outputMap.get("result"), "Balance was not updated after top up\n" +
                "Balance from backend before test: " + topUpBalance.get("preTest") + "\n" +
                "Value to be set: " + topUpBalance.get("afterTest") + "\n" +
                "Balance from backend after test: " + outputMap.get("valueFromBackend") +"\n" +
                "Expected: " + outputMap.get("actualInfo") + "\n" +
                "Actual: " + outputMap.get("valueFromPortal"));
    }


    @Then("^'Add a payment method now\\?' button is shown$")
    public void verifyAddPaymentButtonNowShown(){
        Assert.assertTrue(getPortalBillingDetailsPage().isAddPaymentMethodButtonShown(5),
                "'Add a payment method now?' is not shown");
    }

    @Then("^'Add Payment Method' button is shown$")
    public void verifyAddPaymentButtonShown(){
        Assert.assertTrue(getPortalBillingDetailsPage().isPageActionButton("Add payment method"),
                "'Add a payment method' button is not shown");
    }

    @Then("^Admin clicks 'Add a payment method now\\?' button$")
    public void clickAddPaymentNowButton(){
        getPortalBillingDetailsPage().clickAddPaymentButton();
    }

    @Then("^Admin clicks 'Add Payment Method' button$")
    public void clickAddPaymentButton(){
        getPortalBillingDetailsPage().clickPageActionButton("Add payment method");
    }

    @Then("^'Add Payment Method' window is opened$")
    public void verifyAddPaymentMethodWindowOpened(){
        Assert.assertTrue(getPortalBillingDetailsPage().isAddPaymentMethodWindowShown(5),
                "'Add Payment Method' is not opened");
    }

    @When("^Admin tries to add new card for (.*)$")
    public void addNewCard(String cardHolder){
        String name = cardHolder.split(" ")[0];
        String lastName = cardHolder.split(" ")[1];

        getPortalBillingDetailsPage().getAddPaymentMethodWindow().addTestCardAsANewPayment(name, lastName);
        getPortalBillingDetailsPage().waitWhileProcessing(14, 20);
        getPortalBillingDetailsPage().waitForNotificationAlertToDisappear();
    }

    @When("^Admin provides all card info for (.*) cardholder$")
    public void fillInNewCardInfo(String cardHolder){
        String name = cardHolder.split(" ")[0];
        String lastName = cardHolder.split(" ")[1];
        getPortalBillingDetailsPage().getAddPaymentMethodWindow().fillInNewCardInfo(name, lastName);
    }

    @Then("^'Add payment method' button is disabled$")
    public void verifyAddPaymentDisabled(){
        Assert.assertFalse(getPortalBillingDetailsPage().getAddPaymentMethodWindow().isAddPaymentButtonEnabled(),
                "Add payment button is not disabled");
    }


    @When("^Selects all checkboxes for adding new payment$")
    public void checkAllCheckBoxesForAddingNewPayment(){
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

    @Then("^New card for (.*) is shown in Payment methods tab$")
    public void verifyNewPaymentAdded(String cardHolder){
        getPortalBillingDetailsPage().waitWhileProcessing(14, 20);
        getPortalBillingDetailsPage().waitForNotificationAlertToDisappear();
        boolean isPaymentAdded = getPortalBillingDetailsPage().isPaymentShown(cardHolder, 3);
        ConfigManager.setIsPaymentAdded(String.valueOf(isPaymentAdded));
        Assert.assertTrue(isPaymentAdded, "New payment is not shown in 'Billing & payments' page");
    }

    @Then("^Payment method for (.*) is not shown in Payment methods tab$")
    public void paymentMethodIsNotShown(String cardHolder){
        Assert.assertFalse(getPortalBillingDetailsPage().isPaymentShown(cardHolder, 2),
                "New payment is not shown in 'Billing & payments' page");
    }

    @When("^Admin clicks Manage -> Remove payment for (.*)$")
    public void deletePaymentMethod(String cardHolder){
        getPortalBillingDetailsPage().clickManageButton(cardHolder);
        new PaymentMethodPage(DriverFactory.getAgentDriverInstance()).deletePaymentMethod();
    }

    @When("^Admin adds to cart (.*) agents$")
    public void addAgentsToTheCart(int agentsNumber){
        getAdminPortalMainPage().addAgentSeatsIntoCart(agentsNumber);
    }

    @When("^Admin opens Confirm Details window$")
    public void openConfirmDetailsPage(){
        getAdminPortalMainPage().openAgentsPurchasingConfirmationWindow();
    }

    @Then("Added payment method is able to be selected")
    public void verifyCardIsAvailableToSelecting(){
        Assert.assertTrue(getAdminPortalMainPage().getCartPage().getConfirmPaymentDetailsWindow()
                        .getTestVisaCardToPayDetails().contains("1111"),
                "Added payment method is not available for purchasing");
    }

    @When("^Click Next button on Details tab$")
    public void clickNextButtonOnConfirmDetailsWindow(){
        getAdminPortalMainPage().getCartPage().getConfirmPaymentDetailsWindow().clickNexButtonOnDetailsTab();
    }

    @When("^\"(.*)\" shown in payment methods dropdown$")
    public void verifyPresencePaymentOptionForBuyingAgentSeats(String option){
        getAdminPortalMainPage().getCartPage().getConfirmPaymentDetailsWindow().clickSelectPaymentField();
        Assert.assertTrue(getAdminPortalMainPage().getCartPage().getConfirmPaymentDetailsWindow().isPaymentOptionShown(option),
                "'"+option+"' is not shown in payment options");
    }

    @When("^Admin selects \"(.*)\" in payment methods dropdown$")
    public void selectPaymentOptionWhileBuyingAgents(String option){
        getAdminPortalMainPage().getCartPage().getConfirmPaymentDetailsWindow().selectPaymentMethod(option);
    }

    @Then("^Payment review tab is opened$")
    public void verifyPaymentReviewTabIsOpened(){
        getAdminPortalMainPage().waitForNotificationAlertToDisappear();
        Assert.assertTrue(getAdminPortalMainPage().getCartPage().getConfirmPaymentDetailsWindow().isPaymentReviewTabOpened(),
                "Admin is not redirected to PaymentReview tab after adding new card.");
    }

    @When("^Admin closes Confirm details window$")
    public void closeConfirmDetailsWindow(){
        getAdminPortalMainPage().getCartPage().getConfirmPaymentDetailsWindow().closeWindow();
    }

    @When("^Click 'Manage' button for (.*) user$")
    public void clickManageButtonForUser(String fullName){
        if(fullName.equalsIgnoreCase("created")){
//            fullName = "Maurita Toy";
            fullName =  AGENT_FIRST_NAME + " " + AGENT_LAST_NAME;
        }
        if(fullName.equalsIgnoreCase("admin")){
            String email = Agents.getMainAgentFromCurrentEnvByTenantOrgName(
                    Tenants.getTenantUnderTestOrgName()).getAgentEmail();
            fullName = ApiHelperPlatform.getAccountUserFullName(Tenants.getTenantUnderTestOrgName(), email);
        }
        setPortalUserProfileEditingPage(
                getPortalManagingUsersPage().openUserManagementPage(fullName)
        );
    }
    @When("^Click on (.*) user from the table$")
    public void clickUser(String fullName){
        if(fullName.equalsIgnoreCase("created")){
            fullName =  AGENT_FIRST_NAME + " " + AGENT_LAST_NAME;
        }
        if(fullName.equalsIgnoreCase("admin")){
            String email = Agents.getMainAgentFromCurrentEnvByTenantOrgName(
                    Tenants.getTenantUnderTestOrgName()).getAgentEmail();
            fullName = ApiHelperPlatform.getAccountUserFullName(Tenants.getTenantUnderTestOrgName(), email);
        }
        try {
            getPortalManagingUsersPage().getTargetUserRow(fullName).clickOnUserName();
        }catch (NoSuchElementException e){
            Assert.fail(fullName + " user was not found");
        }
        setPortalUserProfileEditingPage(new PortalUserEditingPage(DriverFactory.getDriverForAgent("admin")));
    }

    @When("^Click 'Upload' button$")
    public void clickUploadButtonForUser(){
        getPortalUserProfileEditingPage().clickUploadPhotoButton();
    }

    @When("^Click 'Upload' button for tenant logo$")
    public void clickUploadButtonForTenantLogo(){
        getPortalTouchPreferencesPage().getBusinessProfileWindow().clickUploadButton();
    }

    // page_action_to_remove
    @When("^Admin clicks 'Edit user roles'$")
    public void clickEditRoles(){
        getPortalUserProfileEditingPage().clickEditUserRolesButton();
    }

    // page_action_to_remove
    @When("^Admin clicks Delete user button$")
    public void deleteAgentUser(){
        getPortalUserProfileEditingPage().clickDeleteButton();
        getPortalUserProfileEditingPage().waitForNotificationAlertToBeProcessed(6,5);
    }

    @When("^On the right corner of the page click \"Deactivate User\" button$")
    public void clickDeactivateUser(){
        getPortalUserProfileEditingPage().clickDeactivateButton();
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
        if(user.contains("Updated")) {
            fullName = updatedAgentInfo.get("firstName") + " " + updatedAgentInfo.get("lastName");
            boolean isUserUpdated = getPortalUserManagementPage().isUserShown(fullName, 2000);
            if(isUserUpdated){
                AGENT_FIRST_NAME = updatedAgentInfo.get("firstName");
                AGENT_LAST_NAME = updatedAgentInfo.get("lastName");
            }
        }

        Assert.assertTrue(getPortalUserManagementPage().isUserShown(fullName, 2000),
                fullName + " agent is not shown on User management page after deleting");
    }

    @When("^Admin updates agent's personal details$")
    public void updateAgentDetails(){
        updatedAgentInfo = new HashMap<>();
        updatedAgentInfo.put("firstName", faker.name().firstName());
        updatedAgentInfo.put("lastName", faker.name().lastName());
        updatedAgentInfo.put("email", "aqa_"+System.currentTimeMillis()+"@aqa.com");

        getPortalUserProfileEditingPage().updateAgentPersonalDetails(updatedAgentInfo);
        getPortalUserProfileEditingPage().waitWhileProcessing(14, 20);
        getPortalUserProfileEditingPage().waitForNotificationAlertToBeProcessed(5,5);
    }

    @When("^Upload (.*)")
    public void uploadPhoto(String photoStrategy){
        getPortalUserProfileEditingPage().uploadPhoto("touch/src/test/resources/agentphoto/agent_photo.png");
        getPortalUserProfileEditingPage().waitForNotificationAlertToBeProcessed(2, 5);
        getPortalUserProfileEditingPage().clickPageActionButton("Save changes");
        getPortalUserProfileEditingPage().waitForNotificationAlertToBeProcessed(3,6);
    }

    @Then("^I check secondary color for tenant in widget$")
    public void iCheckSecondaryColorForTenantInWidget() {
        Assert.assertEquals(getMainPage().getTenantNameColor(), tenantInfo.get("newColor"), "Color for tenant name in widget window is not correct");
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
        soft.assertEquals(AbstractAgentSteps.getAgentHomePage("agent").getUserProfileButtonColor(),
                tenantInfo.get("newColor"), "Color for tenant 'Costomer' is not correct");
//        soft.assertEquals(AbstractAgentSteps.getLeftMenu("agent").getExpandFilterButtonColor(),
//                tenantInfo.get("newColor"), "Color for tenant dropdown button is not correct");
        soft.assertAll();
    }


    @Then("^I check secondary color for tenant in agent desk$")
    public void iCheckSecondaryColorForTenantInAgentDesk() {
        SoftAssert soft = new SoftAssert();
        soft.assertEquals(AbstractAgentSteps.getPageHeader("agent").getTenantNameColor(),
                tenantInfo.get("newColor"), "Color for tenant name in agent desk window is not correct");
        soft.assertEquals(AbstractAgentSteps.getPageHeader("agent").getTenantLogoBorderColor(),
                tenantInfo.get("newColor"), "Color for tenant logo border in agent desk window is not correct");
        soft.assertAll();
    }

    @Then("^Check primary color for incoming chat and 360Container$")
    public void checkPrimaryColorForIncomingChatAndContainer() {
        SoftAssert soft = new SoftAssert();
        soft.assertEquals(AbstractAgentSteps.getLeftMenu("agent").getUserMsgCountColor(),
                            tenantInfo.get("newColor"), "Color for tenant logo border in agent desk window is not correct");
        soft.assertEquals(AbstractAgentSteps.getLeftMenu("agent").getUserPictureColor(),
                            tenantInfo.get("newColor"), "Color for User Picture in agent desk window is not correct");
        soft.assertEquals(AbstractAgentSteps.getUserProfileContainer("agent").getUserPictureColor(),
                            tenantInfo.get("newColor"), "Color for User Picture in 360container in agent desk window is not correct");
        soft.assertEquals(AbstractAgentSteps.getUserProfileContainer("agent").getSaveEditButtonColor(),
                            tenantInfo.get("newColor"), "Color for Edit button in 360container in agent desk window is not correct");
        soft.assertEquals(AbstractAgentSteps.getUserProfileContainer("agent").getMailColor(),
                            tenantInfo.get("newColor"), "Color for Email in 360container in agent desk window is not correct");
        soft.assertEquals(AbstractAgentSteps.getChatHeader("agent").getPinChatButtonColor(),
                            tenantInfo.get("newColor"), "Color for Pin chat button in agent desk window is not correct");
        soft.assertEquals(AbstractAgentSteps.getChatHeader("agent").getTransferButtonColor(),
                            tenantInfo.get("newColor"), "Color for Transfer chat button in agent desk window is not correct");
        soft.assertEquals(AbstractAgentSteps.getChatHeader("agent").getEndChatButtonColor(),
                            tenantInfo.get("newColor"), "Color for End chat button in agent desk window is not correct");
        soft.assertEquals(AbstractAgentSteps.getAgentHomePage("agent").getChatForm().getSubmitMessageButton(),
                            tenantInfo.get("newColor"), "Color for Send button in agent desk window is not correct");
        soft.assertAll();
    }

    @When("^Add new touch (.*) solution$")
    public void addNewTouchSolution(String touchRole){
        getPortalUserProfileEditingPage().getEditUserRolesWindow()
                                                .selectNewTouchRole(touchRole)
                                                .clickFinishButton();
        getPortalUserProfileEditingPage().waitWhileProcessing(14, 20);
    }

    @When("^Add new platform (.*) solution$")
    public void addNewPlatformSolution(String role){
        getPortalUserProfileEditingPage().getEditUserRolesWindow()
                .selectNewPlatformRole(role)
                .clickFinishButton();
        getPortalUserProfileEditingPage().waitWhileProcessing(14, 20);
        getPortalUserProfileEditingPage().waitForNotificationAlertToBeProcessed(5, 9);
    }

    @Given("^Agent of (.*) tenant has no photo uploaded$")
    public void deleteAgentPhoto(String tenantOrgName){
        Tenants.setTenantUnderTestNames(tenantOrgName);
        ApiHelper.deleteAgentPhotoForMainAQAAgent(Tenants.getTenantUnderTestOrgName());
    }

    @When("^Admin logs out from portal$")
    public void logoutFromPortal(){
        getPortalUserProfileEditingPage().getPageHeader().logoutAdmin();
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
//        String imageURLFromBackend = ApiHelper.getAgentInfo(Tenants.getTenantUnderTestOrgName(), "main")
//                .jsonPath().get("imageUrl");
//        soft.assertFalse(imageURLFromBackend==null,
//                        "Agent photo is not saved on backend");
        soft.assertFalse(getPortalUserProfileEditingPage().getImageURL().isEmpty(),
                "Agent photo is not shown in portal");
        soft.assertAll();
    }
    @And("^Change business details$")
    public void changeBusinessDetails() {
        tenantInfo.put("companyName", "New company name "+faker.lorem().word());
        tenantInfo.put("companyCity", "San Francisco "+faker.lorem().word());
        tenantInfo.put("companyIndustry", getPortalTouchPreferencesPage().getBusinessProfileWindow().selectRandomIndustry());
        tenantInfo.put("companyCountry", getPortalTouchPreferencesPage().getBusinessProfileWindow().selectRandomCountry());
        getPortalTouchPreferencesPage().getBusinessProfileWindow().setBusinessName(tenantInfo.get("companyName"));
        getPortalTouchPreferencesPage().getBusinessProfileWindow().setCompanyCity(tenantInfo.get("companyCity"));
        agentClickSaveChangesButton();
    }

    @And("^Refresh page and verify business details was changed for (.*)$")
    public void refreshPageAndVerifyItWasChanged(String tenantOrgName) {
        SoftAssert soft = new SoftAssert();
        DriverFactory.getDriverForAgent("main").navigate().refresh();
        soft.assertEquals(getPortalTouchPreferencesPage().getBusinessProfileWindow().getCompanyName(),tenantInfo.get("companyName"), "Company name was not changed");
        soft.assertEquals(getPortalTouchPreferencesPage().getBusinessProfileWindow().getCompanyCity(),tenantInfo.get("companyCity"), "Company city was not changed");
        soft.assertEquals(getPortalTouchPreferencesPage().getBusinessProfileWindow().getCompanyIndustry(),tenantInfo.get("companyIndustry"), "Company industry was not changed");
        soft.assertEquals(getPortalTouchPreferencesPage().getBusinessProfileWindow().getCompanyCountry(),tenantInfo.get("companyCountry"), "Company country was not changed");
        getPortalTouchPreferencesPage().getBusinessProfileWindow().setBusinessName("Automation Bot");
        getPortalTouchPreferencesPage().clickSaveButton();
        Response resp = ApiHelper.getTenantConfig(tenantOrgName);
        String country = resp.jsonPath().get("country").toString();
        soft.assertEquals(resp.jsonPath().get("orgName"),tenantOrgName, "Company name was not changed on backend");
        soft.assertEquals(resp.jsonPath().get("city").toString(),tenantInfo.get("companyCity"), "Company city was not changed on backend");
        soft.assertEquals(resp.jsonPath().get("category"),tenantInfo.get("companyIndustry"), "Company industry was not changed on backend");
        getPortalTouchPreferencesPage().waitWhileProcessing(14, 20);
        soft.assertAll();
    }

    @When("^Turn (.*) the Last Agent routing$")
    public void changeLastAgentRoting(String status){
        TenantChatPreferences tenantChatPreferences = ApiHelper.getTenantChatPreferences();
        if (status.equalsIgnoreCase("on")){
            tenantChatPreferences.setLastAgentMode(true);
        } else if(status.equalsIgnoreCase("off")){
            tenantChatPreferences.setLastAgentMode(false);
        }else{
            throw new AssertionError("Incorrect status was provided");
        }
        ApiHelper.updateTenantConfig(Tenants.getTenantUnderTestOrgName(), tenantChatPreferences);
    }

    @When("^Verify Last Agent routing is turned (.*) on backend$")
    public void verifyLastAgentRoting(String status) {
        boolean statusOnBackend = ApiHelper.getTenantConfig(Tenants.getTenantUnderTestOrgName()).getBody().jsonPath().get("lastAgentMode");
        if (status.equalsIgnoreCase("on")){
            Assert.assertTrue(statusOnBackend, "Last Agent Roting is not turned on");
        } else if(status.equalsIgnoreCase("off")){
            Assert.assertFalse(statusOnBackend, "Last Agent Roting is not turned off");
        }else{
            throw new AssertionError("Incorrect status was provided");
        }
    }

    @When("^Turn (.*) the Default department$")
    public void changeDepartmentPrimaryStatus(String status){
        TenantChatPreferences tenantChatPreferences = ApiHelper.getTenantChatPreferences();
        if (status.equalsIgnoreCase("on")){
            tenantChatPreferences.setDepartmentPrimaryStatus(true);
        } else if(status.equalsIgnoreCase("off")){
            tenantChatPreferences.setDepartmentPrimaryStatus(false);
        }else{
            throw new AssertionError("Incorrect status was provided");
        }
        ApiHelper.updateTenantConfig(Tenants.getTenantUnderTestOrgName(),tenantChatPreferences);
    }
    @When("^Create chat tag$")
    public void createChatTag(){
        tagname = faker.artist().name() + faker.numerify("#####");
        getPortalTouchPreferencesPage().getChatTagsWindow().clickAddChatTagButton().setTagName(tagname).clickSaveButton();
    }

    @When("^Update chat tag")
    public void updateTag(){
        getPortalTouchPreferencesPage().getChatTagsWindow().clickEditTagButton(tagname);
        tagname = faker.artist().name() + faker.numerify("#####");
        getPortalTouchPreferencesPage().getChatTagsWindow().setTagName(tagname).clickSaveButton();
        AgentCRMTicketsSteps.crmTicketInfoForUpdating.get().put("agentTags",  tagname);
    }
    @When("^Click the pencil icon to edit the tag")
    public void editTag() {
        getPortalTouchPreferencesPage().getChatTagsWindow().clickEditTagButton(tagname);
    }

    @When("^Cancel editing a tag")
    public void cancelEditingTag() {
        getPortalTouchPreferencesPage().getChatTagsWindow().setTagName(tagname).clickDeleteButton();
    }
    @When("^Existing TagName is not changed")
    public void verifyTagName() {
        String newTagName = getPortalTouchPreferencesPage().getChatTagsWindow().getTagName();
        Assert.assertTrue(tagname.equalsIgnoreCase(newTagName), "Tag name has not changed");
    }

    private MainPage getMainPage() {
        if (mainPage == null) {
            mainPage = new MainPage();
        }
        return mainPage;
    }
}
