package steps;

import agent_side_pages.AgentHomePage;
import agent_side_pages.AgentLoginPage;
import api_helper.ApiHelper;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import dataprovider.Tenants;
import driverManager.DriverFactory;
import interfaces.JSHelper;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import org.testng.IAnnotationTransformer;
import org.testng.SkipException;
import org.testng.annotations.ITestAnnotation;
import ru.yandex.qatools.allure.annotations.Attachment;
import touch_pages.pages.Widget;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;

public class Hooks implements JSHelper{

    @Before
    public void beforeScenario(Scenario scenario){
            if (!scenario.getSourceTagNames().equals(Arrays.asList("@tie"))) {
                if (scenario.getSourceTagNames().equals(Arrays.asList("@agent_to_user_conversation"))) {
                    DriverFactory.getSecondDriverInstance();
                }
                DriverFactory.openUrl();
            }
    }

    @After()
    public void afterScenario(Scenario scenario){
        if(!scenario.getSourceTagNames().equals(Arrays.asList("@tie"))) {

            if (DriverFactory.isSecondDriverExists()) {
                takeScreenshotFromSecondDriver();
                logoutAgent();
//                DriverFactory.closeSecondBrowser();
            }
            takeScreenshot();
            endWidgetFlow();
            ApiHelper.deleteUserProfile(Tenants.getTenantUnderTest(), getUserNameFromLocalStorage());
            DriverFactory.closeBrowser();
            DriverFactory.closeSecondBrowser();
        }
    }

    @Attachment(value = "Screenshot")
    private byte[] takeScreenshot() {
        return ((TakesScreenshot) DriverFactory.getInstance()).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "Screenshot")
    private byte[] takeScreenshotFromSecondDriver() {
        return ((TakesScreenshot) DriverFactory.getSecondDriverInstance()).getScreenshotAs(OutputType.BYTES);
    }

    private void endWidgetFlow() {
        Widget widget = new Widget();
        widget.getWidgetFooter().enterMessage("end").sendMessage();
        widget.getWidgetConversationArea().isTextResponseShownFor("end", 5);
    }

    private void logoutAgent() {
        try {
            new AgentHomePage().getHeader().logOut();
            new AgentLoginPage().waitForLoginPageToOpen();
        } catch (WebDriverException e) { }
    }
}
