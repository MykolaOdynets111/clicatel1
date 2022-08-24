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
                Path.NEW_TENANT_STARTER + "02SuggestionsAndFeedbackLimitationForStarter.feature",
                Path.NEW_TENANT_STARTER + "02TryingToLogin2AgentInStarterPlan.feature",
                Path.NEW_TENANT_STARTER + "02AddingPaymentMethod.feature",
                Path.NEW_TENANT_STARTER + "02EditingAgentRole.feature",
                Path.NEW_TENANT_STARTER + "02ResettingPassword.feature",

        },
        glue ="steps")
public class RunTouchGoStarter02 extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }

}
