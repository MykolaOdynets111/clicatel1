package steps;

import agent_side_pages.AgentHomePage;
import agent_side_pages.AgentLoginPage;
import api_helper.ApiHelper;
import api_helper.Endpoints;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import dataprovider.Tenants;
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

import java.io.ByteArrayOutputStream;
import java.util.*;

import static io.restassured.RestAssured.delete;
import static io.restassured.RestAssured.given;

public class Hooks implements JSHelper{

    @Before
    public void beforeScenario(Scenario scenario){
            if (!scenario.getSourceTagNames().equals(Arrays.asList("@tie")) &&
                    !scenario.getSourceTagNames().equals(Arrays.asList("@portal")) &&
                    !scenario.getSourceTagNames().equals(Arrays.asList("@facebook"))) {

                if (scenario.getSourceTagNames().equals(Arrays.asList("@agent_to_user_conversation"))) {
                    DriverFactory.getAgentDriverInstance();
                }
                DriverFactory.openUrl();
                // Setting up coordinates of Lviv, Ukraine into browser
                if (scenario.getSourceTagNames().equals(Arrays.asList("@widget_visibility"))) {
                    setUpGeolocation("49.8397", "24.0297");
                }
            }
            if (scenario.getSourceTagNames().equals(Arrays.asList("@facebook"))) {
                FBLoginPage.openFacebookLoginPage().loginUser();
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
                !scenario.getSourceTagNames().equals(Arrays.asList("@portal")) &&
                !scenario.getSourceTagNames().equals(Arrays.asList("@facebook"))) {

            finishAgentFlowIfExists(scenario);
            takeScreenshot();
            endTouchFlow(scenario);
        }
        if(scenario.getSourceTagNames().equals(Arrays.asList("@widget_visibility"))) {
            takeScreenshot();
            finishVisibilityFlow();
        }
        if(scenario.getSourceTagNames().equals(Arrays.asList("@facebook"))){
            takeScreenshot();
            endFacebookFlow();
        }
        if(scenario.getSourceTagNames().contains("@tie")){
            endTieFlow();
        }
        if(scenario.getSourceTagNames().contains("@portal")){
            if(BasePortalSteps.isNewUserWasCreated()) BasePortalSteps.deleteAgent();
            finishAgentFlowIfExists(scenario);
        }

        closeMainBrowserIfOpened();
    }


    @Attachment(value = "Screenshot")
    private byte[] takeScreenshot() {
        return ((TakesScreenshot) DriverFactory.getTouchDriverInstance()).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "Screenshot")
    private byte[] takeScreenshotFromSecondDriver() {
        return ((TakesScreenshot) DriverFactory.getAgentDriverInstance()).getScreenshotAs(OutputType.BYTES);
    }

    private void finishAgentFlowIfExists(Scenario scenario) {
        if (DriverFactory.isAgentDriverExists()) {
            if(scenario.isFailed()){
                chatDeskConsoleOutput();
            }
            takeScreenshotFromSecondDriver();
            if(scenario.getSourceTagNames().contains("@portal")){
                logoutAgent();
            } else{
                closePopupsIfOpenedEndChatAndlogoutAgent();

            }
            if (scenario.getSourceTagNames().contains("@suggestions")){
                ApiHelper.updateFeatureStatus(Tenants.getTenantUnderTestOrgName(), "AGENT_ASSISTANT", "false");
            }
            DriverFactory.closeAgentBrowser();
        }
        if(DriverFactory.isSecondAgentDriverExists()){
            DriverFactory.closeSecondAgentBrowser();
        }
    }


    private void endTouchFlow(Scenario scenario) {
        if(scenario.getSourceTagNames().equals(Arrays.asList("@collapsing"))) {
            new MainPage().openWidget();
        }
        try {
            if (scenario.isFailed()) {
                touchConsoleOutput();
                Widget widget = new Widget();
                widget.getWidgetFooter().enterMessage("end").sendMessage();
//                widget.getWidgetConversationArea().isTextResponseShownFor("end", 3);
            }
        }catch (WebDriverException e) { }
        ApiHelper.deleteUserProfile(Tenants.getTenantUnderTest(), getUserNameFromLocalStorage());
    }

    private void finishVisibilityFlow() {
        ApiHelper.deleteUserProfile(Tenants.getTenantUnderTest(), getUserNameFromLocalStorage());
        ApiHelper.setWidgetVisibilityDaysAndHours(Tenants.getTenantUnderTestOrgName(), "all week", "00:00", "23:59");
        ApiHelper.setAvailableForAllTerritories(Tenants.getTenantUnderTestOrgName());
    }

    private void closePopupsIfOpenedEndChatAndlogoutAgent() {
        try {
            AgentHomePage agentHomePage =  new AgentHomePage("main agent");
            if(agentHomePage.isProfileWindowOpened()){
                agentHomePage.getProfileWindow().closeProfileWindow();
            }
            agentHomePage.endChat();
            agentHomePage.getHeader().logOut();
            new AgentLoginPage("one agent").waitForLoginPageToOpen();
        } catch (WebDriverException e) { }
    }

    private void logoutAgent() {
        try {
            AgentHomePage agentHomePage =  new AgentHomePage("main agent");
            agentHomePage.getHeader().logOut();
            new AgentLoginPage("one agent").waitForLoginPageToOpen();
        } catch (WebDriverException e) { }
    }

    private void endFacebookFlow() {
        try {
            new FBTenantPage().getMessengerWindow().deleteConversation();
        } catch (WebDriverException e) { }
    }

    private void endTieFlow() {
        if (!TIEApiSteps.getNewTenantNames().isEmpty()) {
            for(long thread : TIEApiSteps.getNewTenantNames().keySet()){
                if (thread==Thread.currentThread().getId()){
                    String url = String.format(Endpoints.BASE_TIE_URL, ConfigManager.getEnv()) +
                            String.format(Endpoints.TIE_DELETE_TENANT, TIEApiSteps.getNewTenantNames().get(thread));
                    given().delete(url);
                    TIEApiSteps.getNewTenantNames().remove(thread);
                }
            }
        }
//        logRequest(BaseTieSteps.request);
//        logResponse(BaseTieSteps.response);

    }


    private void closeMainBrowserIfOpened() {
        if (DriverFactory.isTouchDriverExists()) {
            DriverFactory.closeTouchBrowser();
        }
    }

    @Attachment(value = "request")
    public byte[] logRequest(ByteArrayOutputStream stream) {
        return attach(stream);
    }

    @Attachment(value = "response")
    public byte[] logResponse(ByteArrayOutputStream stream) {
        return attach(stream);
    }

    public byte[] attach(ByteArrayOutputStream log) {
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

}
