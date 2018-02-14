package steps;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import driverManager.DriverFactory;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import ru.yandex.qatools.allure.annotations.Attachment;
import java.util.Arrays;

public class Hooks {

    @Before
    public void beforeScenario(Scenario scenario){
        if (scenario.getSourceTagNames().equals(Arrays.asList("@agent_to_user_conversation"))) {
            DriverFactory.startNewSecondDriverInstance();
        }
        DriverFactory.startBrowser();
    }

    @After()
    public void afterScenario(){
        takeScreenshot();
//        System.out.println("!!!!! URL !!!!!!!!!" + DriverFactory.getInstance().getCurrentUrl());
        DriverFactory.closeBrowser();
        DriverFactory.closeSecondBrowser();
    }

    @Attachment(value = "Screenshot")
    private byte[] takeScreenshot() {
        return ((TakesScreenshot) DriverFactory.getInstance()).getScreenshotAs(OutputType.BYTES);
    }

}
