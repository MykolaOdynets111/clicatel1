package runner.plainTestNgTests;

import driverManager.DriverFactory;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import steps.DefaultTouchUserSteps;
import steps.Hooks;
import testNGtest_helper.PlainTestNGConfigs;

import java.io.File;
import java.io.IOException;

public class WidgetAndBotHealthCheckTest {

    private DefaultTouchUserSteps defaultTouchUserSteps;
    private Hooks hooks;

    @BeforeTest
    private void setUp() {
        defaultTouchUserSteps = new DefaultTouchUserSteps();
        hooks =new Hooks();
    }

    @Test(alwaysRun = true)
    public void testWidgetConnectionAndBotResponding() {
        defaultTouchUserSteps.openPageForDynamicallyPassedTenant()
                                .clickChatIcon()
                                .verifyIfWidgetIsConnected()
                                .enterText("hi")
                                .verifyResponse("Hello", "hi");
    }

    @AfterTest
    public void tearDown(){
        File src= ((TakesScreenshot)DriverFactory.getTouchDriverInstance()).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(src, new File(PlainTestNGConfigs.getTestOutPutDir() +"/"+
                    PlainTestNGConfigs.getTestSuiteName() +"/bot_connection.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        hooks.closeWidgetSession();
        DriverFactory.closeTouchBrowser();
    }

}
