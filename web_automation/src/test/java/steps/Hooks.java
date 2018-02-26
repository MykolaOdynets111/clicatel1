package steps;

import agent_side_pages.AgentHomePage;
import agent_side_pages.AgentLoginPage;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import driverManager.DriverFactory;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import ru.yandex.qatools.allure.annotations.Attachment;
import touch_pages.pages.Widget;
import touch_pages.uielements.WidgetConversationArea;

import java.util.Arrays;

public class Hooks {

    @Before
    public void beforeScenario(Scenario scenario){
        if(!scenario.getSourceTagNames().equals(Arrays.asList("@tie"))) {
            if (scenario.getSourceTagNames().equals(Arrays.asList("@agent_to_user_conversation"))) {
                DriverFactory.startNewSecondDriverInstance();
            }
            DriverFactory.openUrl();
        }
    }

    @After()
    public void afterScenario(Scenario scenario){
        if(!scenario.getSourceTagNames().equals(Arrays.asList("@tie"))) {

            if (DriverFactory.isSecondDriverExists()) {
                takeScreenshotFromSecondDriver();
                new AgentHomePage().getHeader().logOut();
                new AgentLoginPage().waitForLoginPageToOpen();
//                DriverFactory.closeSecondBrowser();
            }
            takeScreenshot();
            new Widget().getWidgetFooter().enterMessage("end chat").sendMessage();
//        System.out.println("!!!!! URL !!!!!!!!!" + DriverFactory.getInstance().getCurrentUrl());
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
}
