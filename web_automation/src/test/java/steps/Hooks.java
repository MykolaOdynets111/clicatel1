package steps;

import agent_side_pages.AgentHomePage;
import agent_side_pages.AgentLoginPage;
import api_helper.ApiHelper;
import api_helper.TwitterAPI;
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
import ru.yandex.qatools.allure.annotations.Attachment;
import steps.tie_steps.BaseTieSteps;
import steps.tie_steps.TIEApiSteps;
import touch_pages.pages.MainPage;
import touch_pages.pages.Widget;
import twitter.TwitterLoginPage;
import twitter.TwitterTenantPage;
import twitter.uielements.DMWindow;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;

import static io.restassured.RestAssured.given;

public class Hooks implements JSHelper{

    @Before
    public void beforeScenario(Scenario scenario){
            if (!scenario.getSourceTagNames().equals(Arrays.asList("@tie")) &&
                    !scenario.getSourceTagNames().contains("@facebook") &&
                    !scenario.getSourceTagNames().contains("@twitter")) {

                if (scenario.getSourceTagNames().equals(Arrays.asList("@agent_to_user_conversation"))) {
                    DriverFactory.getSecondDriverInstance();
                }
                DriverFactory.openUrl();
                // Setting up coordinates of Lviv, Ukraine into browser
                if (scenario.getSourceTagNames().equals(Arrays.asList("@widget_visibility"))) {
                    setUpGeolocation("49.8397", "24.0297");
                }
            }
            if (scenario.getSourceTagNames().contains("@facebook")) {
                FBLoginPage.openFacebookLoginPage().loginUser();
                if (scenario.getSourceTagNames().contains("@agent_to_user_conversation")){
                    DriverFactory.getSecondDriverInstance();
                }
            }
            if (scenario.getSourceTagNames().contains("@twitter")) {
                TwitterLoginPage.openTwitterLoginPage().loginUser();
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
                !scenario.getSourceTagNames().contains("@facebook") &&
                !scenario.getSourceTagNames().contains("@twitter")){


            finishAgentFlowIfExists();
            takeScreenshot();
            endTouchFlow(scenario);
        }
        if(scenario.getSourceTagNames().equals(Arrays.asList("@widget_visibility"))) {
            takeScreenshot();
            finishVisibilityFlow();
        }
        if(scenario.getSourceTagNames().contains("@facebook")){
            finishAgentFlowIfExists();
            takeScreenshot();
            endFacebookFlow(scenario);
            DriverFactory.closeBrowser();
        }

        if(scenario.getSourceTagNames().contains("@twitter")){
            takeScreenshot();
            endTwitterFlow();
            takeScreenshot();
        }

        if(scenario.getSourceTagNames().contains("@tie")){
            endTieFlow();
        }
        closeMainBrowserIfOpened();

    }


    @Attachment(value = "Screenshot")
    private byte[] takeScreenshot() {
        return ((TakesScreenshot) DriverFactory.getInstance()).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "Screenshot")
    private byte[] takeScreenshotFromSecondDriver() {
        return ((TakesScreenshot) DriverFactory.getSecondDriverInstance()).getScreenshotAs(OutputType.BYTES);
    }

    private void endTouchFlow(Scenario scenario) {

        if(scenario.getSourceTagNames().equals(Arrays.asList("@collapsing"))) {
            new MainPage().openWidget();
        }
        try {
            if (scenario.isFailed()) {
                Widget widget = new Widget();
                widget.getWidgetFooter().enterMessage("end").sendMessage();
                widget.getWidgetConversationArea().isTextResponseShownFor("end", 5);
            }
        }catch (WebDriverException e) { }
        ApiHelper.deleteUserProfile(Tenants.getTenantUnderTest(), getUserNameFromLocalStorage());
    }

    private void logoutAgent() {
        try {
            new AgentHomePage().getHeader().logOut();
            new AgentLoginPage().waitForLoginPageToOpen();
        } catch (WebDriverException e) { }
    }

    private void finishAgentFlowIfExists() {
        if (DriverFactory.isSecondDriverExists()) {
            takeScreenshotFromSecondDriver();
            logoutAgent();
            DriverFactory.closeSecondBrowser();
        }
    }

    private void finishVisibilityFlow() {
        ApiHelper.deleteUserProfile(Tenants.getTenantUnderTest(), getUserNameFromLocalStorage());
        ApiHelper.setWidgetVisibilityDaysAndHours(Tenants.getTenantUnderTestOrgName(), "all week", "00:00", "23:59");
        ApiHelper.setAvailableForAllTerritories(Tenants.getTenantUnderTestOrgName());
    }

    private void endFacebookFlow(Scenario scenario) {
        try {
            new FBTenantPage().getMessengerWindow().deleteConversation();
        } catch (WebDriverException e) { }
    }


    private void closeMainBrowserIfOpened() {
        if (DriverFactory.isDriverExists()) {
            DriverFactory.closeBrowser();
        }
    }

    private void endTwitterFlow() {
        try {
            DMWindow dmWindow = new TwitterTenantPage().getDmWindow();
            dmWindow.deleteConversation();
        } catch (WebDriverException e) {

        }
        TwitterAPI.deleteToTestUserTweets();
    }

    private void endTieFlow() {
        if (TIEApiSteps.getNewTenantNames() != null) {
            for (String tenant : TIEApiSteps.getNewTenantNames().values()) {
                String url = String.format(Endpoints.BASE_TIE_URL, ConfigManager.getEnv()) +
                        String.format(Endpoints.TIE_DELETE_TENANT, tenant);
                given().delete(url);
            }
        logRequest(BaseTieSteps.request);
        logResponse(BaseTieSteps.response);
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
}
