package steps;

import agentpages.AgentHomePage;
import agentpages.AgentLoginPage;
import agentpages.uielements.PageHeader;
import apihelper.*;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import datamanager.Tenants;
import datamanager.jacksonschemas.CRMTicket;
import drivermanager.ConfigManager;
import drivermanager.DriverFactory;
import drivermanager.URLs;
import facebook.FBLoginPage;
import facebook.FBTenantPage;
import interfaces.JSHelper;
import javaserver.Server;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import ru.yandex.qatools.allure.annotations.Attachment;
import steps.dotcontrol.DotControlSteps;
import steps.tiesteps.BaseTieSteps;
import steps.tiesteps.TIEApiSteps;
import touchpages.pages.MainPage;
import touchpages.pages.Widget;
import twitter.TweetsSection;
import twitter.TwitterLoginPage;
import twitter.TwitterTenantPage;
import twitter.uielements.DMWindow;

import java.io.ByteArrayOutputStream;
import java.util.*;

import static io.restassured.RestAssured.given;

public class Hooks implements JSHelper{

    @Before
    public void beforeScenario(Scenario scenario){

        clearAllSessionData();

        if(scenario.getSourceTagNames().contains("@skip_for_demo1")&ConfigManager.getEnv().equalsIgnoreCase("demo1")){
                    throw new cucumber.api.PendingException("Not valid for demo1 env because for agent creation" +
                            " connection to DB is used and demo1 DB located in different network than other DBs");
            }

        if(scenario.getSourceTagNames().contains("@testing_env_only")&!ConfigManager.getEnv().equalsIgnoreCase("testing")){
            throw new cucumber.api.PendingException("Designed to run only on testing env. " +
                    "On other envs the test may break the limit of sent activation emails and cause " +
                    "Clickatell to be billed for that");
        }

        if (scenario.getSourceTagNames().contains("@facebook")) {
                ApiHelper.closeAllOvernightTickets("General Bank Demo");
                FBLoginPage.openFacebookLoginPage().loginUser();
                if (scenario.getSourceTagNames().contains("@agent_to_user_conversation")){
                    DriverFactory.getAgentDriverInstance();
                }
        }

        if (scenario.getSourceTagNames().contains("@twitter")) {
                ApiHelper.closeAllOvernightTickets("General Bank Demo");
                TwitterLoginPage.openTwitterLoginPage().loginUser().clickNotificationsButton();
                if (scenario.getSourceTagNames().contains("@agent_to_user_conversation")){
                    DriverFactory.getAgentDriverInstance();
                }
        }

        if(scenario.getSourceTagNames().contains("@tie")){
                BaseTieSteps.request = new ByteArrayOutputStream();
                BaseTieSteps.response = new ByteArrayOutputStream();
        }

        if(scenario.getSourceTagNames().contains("@start_server")){
            new Server().startServer();
            APIHelperDotControl.waitForServerToBeReady();
        }
    }

    @After()
    public void afterScenario(Scenario scenario){

        makeScreenshotAndConsoleOutputFromChatdesk(scenario);

        if(!scenario.getSourceTagNames().equals(Arrays.asList("@tie")) &&
                !scenario.getSourceTagNames().equals(Arrays.asList("@widget_visibility")) &&
                !scenario.getSourceTagNames().contains("@no_widget") &&
                !scenario.getSourceTagNames().contains("@facebook") &&
                !scenario.getSourceTagNames().contains("@twitter") &&
                !scenario.getSourceTagNames().contains("@healthcheck") &&
                !scenario.getSourceTagNames().contains("@camunda")){

            takeScreenshot();
            endTouchFlow(scenario, true);
            ApiHelper.deleteUserProfile(Tenants.getTenantUnderTestName(), getUserNameFromLocalStorage());
        }

        if(scenario.getSourceTagNames().contains("@agent_support_hours")){
            ApiHelper.setAgentSupportDaysAndHours(Tenants.getTenantUnderTestOrgName(), "all week", "00:00", "23:59");
            ApiHelper.closeAllOvernightTickets(Tenants.getTenantUnderTestOrgName());
        }

        if(scenario.getSourceTagNames().contains("@agent_session_capacity")){
            ApiHelper.updateSessionCapacity(Tenants.getTenantUnderTestOrgName(), 100);
        }

        finishAgentFlowIfExists(scenario);

        if(scenario.getSourceTagNames().equals(Arrays.asList("@widget_visibility"))) {
            takeScreenshot();
            finishVisibilityFlow();
        }

        if(scenario.getSourceTagNames().contains("@facebook")){
            takeScreenshot();
            endFacebookFlow(scenario);
        }

        if(scenario.getSourceTagNames().contains("@twitter")){
            takeScreenshot();
            endTwitterFlow(scenario);
        }

        if(scenario.getSourceTagNames().contains("@tie")){
            endTieFlow(scenario);
        }
        if(scenario.getSourceTagNames().contains("@newagent")){
            if(BasePortalSteps.isNewUserWasCreated()) BasePortalSteps.deleteAgent();
        }

        if(scenario.getSourceTagNames().contains("@healthcheck")){
            takeScreenshot();
            endTouchFlow(scenario, true);
        }

        if(scenario.getSourceTagNames().contains("@dilinking_account")&&scenario.isFailed()){
            ApiHelper.delinkFBIntegration(Tenants.getTenantUnderTestOrgName());
        }

        if(scenario.getSourceTagNames().contains("@dot_control")){
            DotControlSteps.cleanUPMessagesInfo();
            APIHelperDotControl.deleteHTTPIntegrations(Tenants.getTenantUnderTestOrgName());
            Server.stopServer();
            APIHelperDotControl.waitForServerToBeClosed();
        }

        if(scenario.getSourceTagNames().contains("@camunda")){
            takeScreenshot();
            endTouchFlow(scenario, true);
            ApiHelper.deleteUserProfile(Tenants.getTenantUnderTestName(), getUserNameFromLocalStorage());
        }

        if(scenario.getSourceTagNames().contains("@creating_intent")){
            TIEApiSteps.clearCreatedIntentAndSample();
        }

        if(scenario.getSourceTagNames().contains("@slot_management")){
            TIEApiSteps.clearCreatedSlots();
        }

        closeMainBrowserIfOpened();
        clearAllSessionData();
    }


    @Attachment(value = "Screenshot")
    private byte[] takeScreenshot() {
        if (DriverFactory.isTouchDriverExists()) {
            return ((TakesScreenshot) DriverFactory.getTouchDriverInstance()).getScreenshotAs(OutputType.BYTES);
        } else{
            return null;
        }    }

    @Attachment(value = "Screenshot")
    private byte[] takeScreenshotFromSecondDriver() {
        return ((TakesScreenshot) DriverFactory.getAgentDriverInstance()).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "Screenshot")
    private byte[] takeScreenshotFromThirdDriverIfExists() {
        return ((TakesScreenshot) DriverFactory.getSecondAgentDriverInstance()).getScreenshotAs(OutputType.BYTES);
    }

    private void makeScreenshotAndConsoleOutputFromChatdesk(Scenario scenario){
        if (DriverFactory.isAgentDriverExists()) {
            takeScreenshotFromSecondDriver();
            if (scenario.isFailed()) {
                chatDeskConsoleOutput();
            }
        }
        if (DriverFactory.isSecondAgentDriverExists()) {
                if (scenario.isFailed()) {
                    secondAgentChatDeskConsoleOutput();
                }
                takeScreenshotFromThirdDriverIfExists();
        }
    }


    private void finishAgentFlowIfExists(Scenario scenario) {
        if (DriverFactory.isAgentDriverExists()) {
            if (scenario.getSourceTagNames().contains("@agent_info")) {
                new AgentHomePage("main").getProfileWindow().closeIfOpened();
            }
            if (scenario.getSourceTagNames().contains("@inactivity_timeout")) {
                ApiHelper.updateTenantConfig(Tenants.getTenantUnderTestOrgName(), "agentInactivityTimeoutSec", "30");
            }


            if (scenario.getSourceTagNames().contains("@agent_availability")&&scenario.isFailed()){
                //ToDo: replace with API call if appropriate exists
                    AgentHomePage agentHomePage = new AgentHomePage("main agent");
                    agentHomePage.getPageHeader().clickIconWithInitials();
                    agentHomePage.getPageHeader().selectStatus("available");
                    agentHomePage.getPageHeader().clickIconWithInitials();
            }
            if(!scenario.getSourceTagNames().contains("@no_chatdesk")) closePopupsIfOpenedEndChatAndlogoutAgent("main agent");

            if(scenario.getSourceTagNames().contains("@updating_touchgo")) {
                DriverFactory.closeAgentBrowser();
                ApiHelper.decreaseTouchGoPLan(Tenants.getTenantUnderTestOrgName());
                List<Integer> subscriptionIDs = ApiHelperPlatform.getListOfActiveSubscriptions(Tenants.getTenantUnderTestOrgName());
                subscriptionIDs.forEach(e ->
                        ApiHelperPlatform.deactivateSubscription(Tenants.getTenantUnderTestOrgName(), e));
            }

            if(scenario.getSourceTagNames().contains("@adding_payment_method")) {
                List<String> ids = ApiHelperPlatform.getListOfActivePaymentMethods(Tenants.getTenantUnderTestOrgName(), "CREDIT_CARD");
                ids.forEach(e -> ApiHelperPlatform.deletePaymentMethod(Tenants.getTenantUnderTestOrgName(), e));
            }

            if (scenario.getSourceTagNames().contains("@suggestions")){
                boolean pretestFeatureStatus = DefaultAgentSteps.getPreTestFeatureStatus("AGENT_ASSISTANT");
                if(pretestFeatureStatus != DefaultAgentSteps.getTestFeatureStatusChanging("AGENT_ASSISTANT")) {
                    ApiHelper.updateFeatureStatus(Tenants.getTenantUnderTestOrgName(), "AGENT_ASSISTANT", Boolean.toString(pretestFeatureStatus));
                }
            }

            if (scenario.getSourceTagNames().contains("@agent_feedback")){
                try{
                    boolean pretestFeatureStatus = DefaultAgentSteps.getPreTestFeatureStatus("AGENT_FEEDBACK");
                    if (pretestFeatureStatus != DefaultAgentSteps.getTestFeatureStatusChanging("AGENT_FEEDBACK")) {
                        ApiHelper.updateFeatureStatus(Tenants.getTenantUnderTestOrgName(), "AGENT_FEEDBACK", Boolean.toString(pretestFeatureStatus));
                    }
                }catch(NullPointerException e){
                    //no feature status interaction
                }
                if(DefaultAgentSteps.getCreatedCRMTicket()!=null){
                    ApiHelper.deleteCRMTicket(DefaultAgentSteps.getCreatedCRMTicket().getId());
                }
                if(DefaultAgentSteps.getCreatedCRMTicketsList()!=null){
                    for(CRMTicket ticket: DefaultAgentSteps.getCreatedCRMTicketsList()){
                        ApiHelper.deleteCRMTicket(ticket.getId());
                    }
                }
            }

            if (scenario.getSourceTagNames().contains("@widget_disabling")){
                ApiHelper.setIntegrationStatus(Tenants.getTenantUnderTestOrgName(), "webchat", true);

            }
            DriverFactory.getAgentDriverInstance().manage().deleteAllCookies();
            DriverFactory.closeAgentBrowser();

            if(scenario.isFailed()&&scenario.getSourceTagNames().contains("@closing_account")){
                ApiHelperPlatform.closeAccount(BasePortalSteps.ACCOUNT_NAME_FOR_NEW_ACCOUNT_SIGN_UP,
                                                    BasePortalSteps.EMAIL_FOR_NEW_ACCOUNT_SIGN_UP,
                                                    BasePortalSteps.PASS_FOR_NEW_ACCOUNT_SIGN_UP);
            }
            if (scenario.getSourceTagNames().contains("@creating_new_tenant  ")){
                String url = String.format(Endpoints.TIE_DELETE_TENANT, BasePortalSteps.ACCOUNT_NAME_FOR_NEW_ACCOUNT_SIGN_UP);
                given().delete(url);
            }
        }
        if (DriverFactory.isSecondAgentDriverExists()) {
            closePopupsIfOpenedEndChatAndlogoutAgent("second agent");
            DriverFactory.getSecondAgentDriverInstance().manage().deleteAllCookies();
            DriverFactory.closeSecondAgentBrowser();
        }
    }

    private void endTouchFlow(Scenario scenario, boolean typeEndInWidget) {
        if (DriverFactory.isTouchDriverExists()) {
            userName();
            if(scenario.getSourceTagNames().equals(Arrays.asList("@collapsing"))) {
                new MainPage().openWidget();
            }
            try {
                if (scenario.isFailed()) {
                    touchConsoleOutput();
                }
//                if (scenario.isFailed()&&DriverFactory.isAgentDriverExists()) {
//                    closeWidgetSession();
//                }
        }catch (WebDriverException e) { }
        }
    }

    public void closeWidgetSession(){
        Widget widget = new Widget("withoutWait");
        widget.getWidgetFooter().tryToCloseSession();
    }

    private void closePopupsIfOpenedEndChatAndlogoutAgent(String agent) {
        try {
            AgentHomePage agentHomePage = new AgentHomePage(agent);
            ApiHelper.closeActiveChats();
//            ApiHelper.logoutTheAgent(Tenants.getTenantUnderTestOrgName()); commented out because API not working now
            agentHomePage.getPageHeader().logOut(agent);
            new AgentLoginPage(agent).waitForLoginPageToOpen(agent);
        } catch(WebDriverException|AssertionError e){
        }
    }

    private void finishVisibilityFlow() {
        ApiHelper.deleteUserProfile(Tenants.getTenantUnderTestName(), getUserNameFromLocalStorage());
        ApiHelper.setWidgetVisibilityDaysAndHours(Tenants.getTenantUnderTestOrgName(), "all week", "00:00", "23:59");
        ApiHelper.setAvailableForAllTerritories(Tenants.getTenantUnderTestOrgName());
    }

    private void endFacebookFlow(Scenario scenario) {
        FBTenantPage fbTenantPage = new FBTenantPage();
            try {
                if(scenario.getSourceTagNames().contains("@fb_dm")) {
                    fbTenantPage.getMessengerWindow().deleteConversation();
                }
                if(scenario.getSourceTagNames().contains("@fb_post")){
                    fbTenantPage.getPostFeed().endSessionIfPostFeedIsShown();
                    fbTenantPage.getFBYourPostPage().deletePost();
                }

            } catch (WebDriverException e) { }
        }

    private void closeMainBrowserIfOpened() {
        if (DriverFactory.isTouchDriverExists()) {
            DriverFactory.closeTouchBrowser();
        }
    }

    private void endTwitterFlow(Scenario scenario) {
        TwitterTenantPage twitterTenantPage = new TwitterTenantPage();
        try {
            if(scenario.isFailed()){
                TweetsSection tweetsSection =  new TweetsSection();
                if(tweetsSection.getOpenedTweet().isDisplayed()){
//                    tweetsSection.sendReplyForTweet("", "end");
                    tweetsSection.getOpenedTweet().clickSendReplyButton();
                }
            }
        } catch (WebDriverException e) {}
        TwitterAPI.deleteToTestUserTweets();
        TwitterAPI.deleteTweetsFromTestUser();
        try {
            if(twitterTenantPage.isDMWindowOpened()) {
                DMWindow dmWindow = twitterTenantPage.getDmWindow();
//                dmWindow.sendUserMessage("end");
                dmWindow.deleteConversation();
            }
        } catch (WebDriverException e) {}
    }

    private void endTieFlow(Scenario scenario) {
            if (!TIEApiSteps.getNewTenantNames().isEmpty()) {
                for (long thread : TIEApiSteps.getNewTenantNames().keySet()) {
                    if (thread == Thread.currentThread().getId()) {
                        String url = String.format(Endpoints.TIE_DELETE_TENANT, TIEApiSteps.getNewTenantNames().get(thread));
                        given().delete(url);
                        TIEApiSteps.getNewTenantNames().remove(thread);
                    }
                }
            }
//        TIEApiSteps.clearTenantNames();
//        logRequest(BaseTieSteps.request);
//        logResponse(BaseTieSteps.response);
    }

    private void clearAllSessionData(){
        Tenants.clearTenantUnderTest();
        RequestSpec.clearAccessTokenForPortalUser();
        URLs.clearFinalAgentURL();
    }

    @Attachment(value = "request")
    public byte[] logRequest(ByteArrayOutputStream stream) {
        return attach(stream);
    }

    @Attachment(value = "response")
    public byte[] logResponse(ByteArrayOutputStream stream) {
        return attach(stream);
    }

    private byte[] attach(ByteArrayOutputStream log) {
        byte[] array = log.toByteArray();
        log.reset();
        return array;
    }

    @Attachment
    private String userName(){
        return getUserNameFromLocalStorage();
    }

    @Attachment
    private String touchConsoleOutput(){
        StringBuilder result = new StringBuilder();
        LogEntries logEntries = DriverFactory.getTouchDriverInstance().manage().logs().get(LogType.BROWSER);
        for (LogEntry entry : logEntries) {
            result.append(new Date(entry.getTimestamp())).append(", ").append(entry.getLevel()).append(", ").append(entry.getMessage()).append(";  \n");
        }
        return  result.toString();
    }

    @Attachment
    private String chatDeskConsoleOutput(){
        StringBuffer buffer = new StringBuffer();
        LogEntries logEntries = DriverFactory.getAgentDriverInstance().manage().logs().get(LogType.BROWSER);
        for (LogEntry entry : logEntries) {
            buffer.append(new Date(entry.getTimestamp())).append(", ").append(entry.getLevel()).append(", ").append(entry.getMessage()).append(";  \n");
        }
        return  buffer.toString();
    }

    @Attachment
    private String secondAgentChatDeskConsoleOutput(){
        StringBuilder result = new StringBuilder();
        LogEntries logEntries = DriverFactory.getSecondAgentDriverInstance().manage().logs().get(LogType.BROWSER);
        for (LogEntry entry : logEntries) {
            result.append(new Date(entry.getTimestamp())).append(", ").append(entry.getLevel()).append(", ").append(entry.getMessage()).append(";  \n");
        }
        return  result.toString();
    }


}
