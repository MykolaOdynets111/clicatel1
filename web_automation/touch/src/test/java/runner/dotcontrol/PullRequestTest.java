package runner.dotcontrol;


import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static runner.dotcontrol.Path.PATH;

@Test(groups = "General Bank agent UI tests")
@CucumberOptions(
         plugin={"pretty", "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        },
        monochrome = true,
        features ={
                PATH + "DotControlCreatingIntegration.feature",
                PATH + "DotControlInvalidInitCall.feature",
                PATH + "DotControlMessaging.feature"
        },
        glue ="steps")
public class PullRequestTest extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }

}
