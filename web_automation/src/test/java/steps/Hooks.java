package steps;

import agent_side_pages.AgentHomePage;
import agent_side_pages.AgentLoginPage;
import api_helper.ApiHelper;
import api_helper.TwitterAPI;
import api_helper.Endpoints;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import dataManager.Tenants;
import driverManager.ConfigManager;
import driverManager.DriverFactory;
import facebook.FBLoginPage;
import facebook.FBTenantPage;
import interfaces.JSHelper;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import ru.yandex.qatools.allure.annotations.Attachment;
import steps.tie_steps.BaseTieSteps;
import steps.tie_steps.TIEApiSteps;
import touch_pages.pages.MainPage;
import touch_pages.pages.Widget;
import twitter.TwitterLoginPage;
import twitter.TwitterTenantPage;
import twitter.uielements.DMWindow;

import java.io.ByteArrayOutputStream;
import java.util.*;

import static io.restassured.RestAssured.given;

public class Hooks implements JSHelper{

    @Before
    public void beforeScenario(Scenario scenario){

            if(scenario.getSourceTagNames().contains("@skip_for_demo1")&ConfigManager.getEnv().equalsIgnoreCase("demo1")){
                    throw new cucumber.api.PendingException("Not valid for demo1 env because for agent creation" +
                            " connection to DB is used and demo1 DB located in different network than other DBs");
            }

            if(scenario.getSourceTagNames().contains("@lodash")&ConfigManager.getEnv().equalsIgnoreCase("integration")){
                throw new cucumber.api.PendingException("Integration tweb should be updated for this lodash test");
            }

            if (!scenario.getSourceTagNames().equals(Arrays.asList("@tie")) &&
                    !scenario.getSourceTagNames().contains("@facebook") &&
                    !scenario.getSourceTagNames().contains("@twitter") &&
                    !scenario.getSourceTagNames().contains("@predefined_user")){

                if (scenario.getSourceTagNames().equals(Arrays.asList("@agent_to_user_conversation"))) {
                    DriverFactory.getAgentDriverInstance();
                }
                DriverFactory.openUrl();
                // Setting up coordinates of Lviv, Ukraine into browser
//                if (scenario.getSourceTagNames().equals(Arrays.asList("@widget_visibility"))) {
//                    setUpGeolocation("49.8397", "24.0297");
//                }
            }

            if (scenario.getSourceTagNames().contains("@predefined_user")) {
                DriverFactory.openTouchUrlWithPredifinedUserID("testing_aqaTestUser");
            }

            if (scenario.getSourceTagNames().contains("@facebook")) {
                FBLoginPage.openFacebookLoginPage().loginUser();
                if (scenario.getSourceTagNames().contains("@agent_to_user_conversation")){
                    DriverFactory.getAgentDriverInstance();
                }
            }

            if (scenario.getSourceTagNames().contains("@twitter")) {
                TwitterLoginPage.openTwitterLoginPage().loginUser();
                if (scenario.getSourceTagNames().contains("@agent_to_user_conversation")){
                    DriverFactory.getAgentDriverInstance();
                }
            }
            if(scenario.getSourceTagNames().contains("@tie")){
                BaseTieSteps.request = new ByteArrayOutputStream();
                BaseTieSteps.response = new ByteArrayOutputStream();
            }
    }

    @After()
    public void afterScenario(Scenario scenario){
        if(!scenario.getSourceTagNames().equals(Arrays.asList("@tie")) &&
                !scenario.getSourceTagNames().equals(Arrays.asList("@widget_visibility")) &&
//                !scenario.getSourceTagNames().equals(Arrays.asList("@portal")) &&
                !scenario.getSourceTagNames().contains("@facebook") &&
                !scenario.getSourceTagNames().contains("@twitter")){

            takeScreenshot();
            finishAgentFlowIfExists(scenario);
            endTouchFlow(scenario);
        }
        if(scenario.getSourceTagNames().equals(Arrays.asList("@widget_visibility"))) {
            takeScreenshot();
            finishVisibilityFlow();
        }
        if(scenario.getSourceTagNames().contains("@facebook")){
            takeScreenshot();
            finishAgentFlowIfExists(scenario);
            endFacebookFlow(scenario);
        }

        if(scenario.getSourceTagNames().contains("@twitter")){
            takeScreenshot();
            finishAgentFlowIfExists(scenario);
            endTwitterFlow();
        }

        if(scenario.getSourceTagNames().contains("@tie")){
            endTieFlow(scenario);
        }
        if(scenario.getSourceTagNames().contains("@portal")){
            if(BasePortalSteps.isNewUserWasCreated()) BasePortalSteps.deleteAgent();
        }

        closeMainBrowserIfOpened();
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

    private void finishAgentFlowIfExists(Scenario scenario) {
        if (DriverFactory.isAgentDriverExists()) {
            takeScreenshotFromSecondDriver();
            if(scenario.isFailed()){
                chatDeskConsoleOutput();
            }
            if (DriverFactory.isSecondAgentDriverExists()) {
                if (scenario.isFailed()) {
                    secondAgentChatDeskConsoleOutput();
                }
                takeScreenshotFromThirdDriverIfExists();
            }
            if (scenario.getSourceTagNames().contains("@agent_availability")&&scenario.isFailed()){
                    AgentHomePage agentHomePage = new AgentHomePage("main agent");
                    agentHomePage.getHeader().clickIconWithInitials();
                    agentHomePage.getHeader().selectStatus("available");
                    agentHomePage.getHeader().clickIconWithInitials();
            }

            closePopupsIfOpenedEndChatAndlogoutAgent("main agent");

            if (scenario.getSourceTagNames().contains("@suggestions")){
                boolean pretestFeatureStatus = DefaultAgentSteps.getPreTestFeatureStatus("AGENT_ASSISTANT");
                if(pretestFeatureStatus != DefaultAgentSteps.getTestFeatureStatusChanging("AGENT_ASSISTANT")) {
                    ApiHelper.updateFeatureStatus(Tenants.getTenantUnderTestOrgName(), "AGENT_ASSISTANT", Boolean.toString(pretestFeatureStatus));
                }
            }
            DriverFactory.closeAgentBrowser();
        }
        if (DriverFactory.isSecondAgentDriverExists()) {
            closePopupsIfOpenedEndChatAndlogoutAgent("second agent");
            DriverFactory.closeSecondAgentBrowser();
        }
    }

    private void endTouchFlow(Scenario scenario) {
        if (DriverFactory.isTouchDriverExists()) {

            if(scenario.getSourceTagNames().equals(Arrays.asList("@collapsing"))) {
                new MainPage().openWidget();
            }
            try {
                if (scenario.isFailed()) {
                    touchConsoleOutput();
                    Widget widget = new Widget();
                    widget.getWidgetFooter().enterMessage("end").sendMessage();
                }
        }catch (WebDriverException e) { }
        ApiHelper.deleteUserProfile(Tenants.getTenantUnderTest(), getUserNameFromLocalStorage());
        }
    }

    private void closePopupsIfOpenedEndChatAndlogoutAgent(String agent) {
        try {
            AgentHomePage agentHomePage = new AgentHomePage(agent);
            agentHomePage.endChat(agent);
            ApiHelper.logoutTheAgent(Tenants.getTenantUnderTestOrgName());
            new AgentLoginPage(agent).waitForLoginPageToOpen(agent);
        } catch (WebDriverException e) { }
    }

    private void finishVisibilityFlow() {
        ApiHelper.deleteUserProfile(Tenants.getTenantUnderTest(), getUserNameFromLocalStorage());
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

    private void endTwitterFlow() {
        TwitterAPI.deleteToTestUserTweets();
        TwitterAPI.deleteTweetsFromTestUser();
        try {
            DMWindow dmWindow = new TwitterTenantPage().getDmWindow();
            dmWindow.sendUserMessage("end");
            dmWindow.deleteConversation();
        } catch (WebDriverException e) {

        }
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
        StringBuilder result = new StringBuilder();
        LogEntries logEntries = DriverFactory.getAgentDriverInstance().manage().logs().get(LogType.BROWSER);
        for (LogEntry entry : logEntries) {
            result.append(new Date(entry.getTimestamp())).append(", ").append(entry.getLevel()).append(", ").append(entry.getMessage()).append(";  \n");
        }
        return  result.toString();
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
