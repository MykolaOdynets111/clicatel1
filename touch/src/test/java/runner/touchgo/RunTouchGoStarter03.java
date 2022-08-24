package runner.touchgo;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Test(groups = {"touchgo"})
@CucumberOptions(
         plugin={"pretty", "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        },
        monochrome = true,
        features = {
                Path.NEW_TENANT_STARTER + "03TouchGoPlanUpgradingWithoutAcceptigTerms.feature",
                Path.NEW_TENANT_STARTER + "03EditingAgentDetails.feature"
        },
        glue ="steps")
public class RunTouchGoStarter03 extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }

}
